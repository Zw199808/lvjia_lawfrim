package com.xinou.lawfrim.sso.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.xinou.lawfrim.common.util.APIResponse;
import com.xinou.lawfrim.common.util.MD5Util;
import com.xinou.lawfrim.common.util.StringUtil;
import com.xinou.lawfrim.sso.config.ConfigSSO;
import com.xinou.lawfrim.sso.entity.*;
import com.xinou.lawfrim.sso.mapper.ReUserRoleSSOMapper;
import com.xinou.lawfrim.sso.mapper.UserSSOMapper;
import com.xinou.lawfrim.sso.service.ReUserAppSSOService;
import com.xinou.lawfrim.sso.service.ReUserRoleSSOService;
import com.xinou.lawfrim.sso.service.UserSSOService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by zhangbo on 2017/10/24.
 * 系统用户
 */
@Service("UserSSOServiceImpl")
@Primary
public class UserSSOServiceImpl extends ServiceImpl<UserSSOMapper, SYSUser> implements UserSSOService {

    @Autowired
    ReUserRoleSSOService reURService;
    @Autowired
    ReUserRoleSSOMapper mapper;
    @Autowired
    ReUserAppSSOService reUAService;

    @Autowired
    UserSSOMapper userSSOMapper;


    @Override
    @Transactional
    public APIResponse addUser(String account, String realName, String mobile, Integer[] roleIds, Integer[] appIds) {
        SYSUser user = new SYSUser();
        user.setAccount(account);
        user.setPassword(MD5Util.MD5("123456"));
        user.setRealName(realName);
        user.setMobile(mobile);
        user.setIsEnable(1);

        //账号重复
        SYSUser db_user = getOne(new QueryWrapper<SYSUser>()
                .eq("account", account).eq("is_delete",0));

        if (db_user != null) {
            return new APIResponse(ConfigSSO.RE_CODE_USER_HAD, ConfigSSO.RE_MSG_USER_HAD);
        }

        //角色不能为空
        if (roleIds == null) {
            return new APIResponse(ConfigSSO.RE_CODE_USER_NOT_ROLE, ConfigSSO.RE_MSG_USER_NOT_ROLE);
        }

        //系统不能为空
        if (appIds == null) {
            return new APIResponse(ConfigSSO.RE_CODE_USER_NOT_APP, ConfigSSO.RE_MSG_USER_NOT_APP);
        }

        //插入操作
        save(user);

        int userId = user.getId();


        //批量插入 系统和用户关系表
        List<ReSYSUserApp> appUserList = new ArrayList<>();
        for (int info : appIds) {
            ReSYSUserApp reUA = new ReSYSUserApp();
            reUA.setUserId(userId);
            reUA.setAppId(info);
            appUserList.add(reUA);
        }

        boolean appUserResult = reUAService.saveBatch(appUserList);

        //批量插入 角色和用户关系表
        List<ReSYSUserRole> list = new ArrayList<>();
        for (int info : roleIds) {
            ReSYSUserRole reUR = new ReSYSUserRole();
            reUR.setUserId(userId);
            reUR.setRoleId(info);
            list.add(reUR);
        }

        boolean result = reURService.saveBatch(list);

        if (result && appUserResult) {
            return new APIResponse();
        }

        return new APIResponse(ConfigSSO.RE_CODE_ROLE_ERROR, ConfigSSO.RE_MSG_ROLE_ERROR);
    }

    @Override
    @Transactional
    public APIResponse delUser(int id) {
        //删除用户
        SYSUser user = getById(id);
        user.setId(id);
        user.setIsDelete(ConfigSSO.IS_DELETE);

        boolean result = updateById(user);


        //删除对应 app  role  两个关联表

        List<ReSYSUserApp> ruas = reUAService.list(new QueryWrapper<ReSYSUserApp>().eq("user_id", id).eq("is_delete",0));

        boolean reRPResult = true;

        if(ruas.size() != 0){
            for (ReSYSUserApp info : ruas) {
                info.setIsDelete(ConfigSSO.IS_DELETE);
            }
            reRPResult = reUAService.updateBatchById(ruas);
        }

        List<ReSYSUserRole> rurs = reURService.list(new QueryWrapper<ReSYSUserRole>().eq("user_id", id).eq("is_delete",0));

        boolean reURResult = true;
        if(rurs.size()!=0){
            for (ReSYSUserRole info : rurs) {
                info.setIsDelete(ConfigSSO.IS_DELETE);
            }

            reURResult = reURService.updateBatchById(rurs);
        }

        if (result && reRPResult && reURResult) {
            return new APIResponse();
        }

        return new APIResponse(ConfigSSO.RE_CODE_ROLE_ERROR, ConfigSSO.RE_MSG_ROLE_ERROR);
    }

    @Override
    @Transactional
    public APIResponse updateUser(int id, String realName, String password,String mobile, Integer[] roleIds, int isEnable) {

        SYSUser user = getById(id);
        user.setId(id);
        user.setRealName(realName);
        user.setPassword(password);
        user.setIsEnable(isEnable);


        //修改用户表中信息
        updateById(user);

        //修改关系表中 关系    先删除  后添加
        List<ReSYSUserRole> list = reURService.list(new QueryWrapper<ReSYSUserRole>().eq("user_id", id).eq("is_delete",0));


        for (ReSYSUserRole info : list) {
            info.setIsDelete(ConfigSSO.IS_DELETE);
        }

        reURService.updateBatchById(list);

        //批量插入 角色和用户关系表
        List<ReSYSUserRole> insertList = new ArrayList<>();
        for (int info : roleIds) {
            ReSYSUserRole reUR = new ReSYSUserRole();
            reUR.setUserId(id);
            reUR.setRoleId(info);
            insertList.add(reUR);
        }

        boolean result = reURService.saveBatch(insertList);

        if (result) {
            return new APIResponse();
        }

        return new APIResponse(ConfigSSO.RE_CODE_ROLE_ERROR, ConfigSSO.RE_MSG_ROLE_ERROR);
    }

    @Override
    public APIResponse userList(int page, int pageSize, String selectValue, String searchValue) {

        int skip = (page-1)*pageSize;
        String lastSql = "limit "+skip+","+pageSize;
        List<SYSUser> list;
        int total = 0;
        if (StringUtil.isNullString(searchValue)) {
            list = list(
                    new QueryWrapper<SYSUser>().eq("is_delete", 0).orderByDesc("gmt_create")
                            .last(lastSql)
            );
            total = count(
                    new QueryWrapper<SYSUser>().eq("is_delete", 0)
            );
        } else {
            list = list(
                    new QueryWrapper<SYSUser>().eq("is_delete", 0).like(selectValue, searchValue)
                            .orderByDesc("gmt_create").last(lastSql)
            );
            total = count(
                    new QueryWrapper<SYSUser>().eq("is_delete", 0).like(selectValue, searchValue)
            );
        }

        //如果没有结果
        if (list == null || list.size() == 0){
            Map<String, Object> data = new HashMap<>();
            data.put("dataList", list);
            data.put("total", 0);
            return new APIResponse(data);
        }


        //根据userId  查询对应的角色
        List<Integer> ids = new ArrayList<>();

        for (SYSUser info : list) {
            int userId = info.getId();
            ids.add(userId);
        }

        List<ResReSYSUserRole> roles = reURService.getRoleByUserId(ids);
        List<ResReSYSUserApp> apps = reUAService.getAppByUserId(ids);



        for (SYSUser user : list) {
            int id = user.getId();
            //匹配每个user的 角色列表
            List<String> userRole = new ArrayList<>();
            for (ResReSYSUserRole info : roles) {
                int userId = info.getUserId();
                if (id == userId) {
                    userRole.add(info.getRoleName());
                }
            }
            user.setRoles(userRole);
            //匹配每个user的 系统列表
            List<String> userApp = new ArrayList<>();
            for (ResReSYSUserApp info : apps) {
                int userId = info.getUserId();
                if (id == userId) {
                    userApp.add(info.getAppName());
                }
            }
            user.setApps(userApp);
        }


        Map<String, Object> data = new HashMap<>();
        data.put("dataList", list);
        data.put("total", total);


        return new APIResponse(data);

    }

    @Override
    public APIResponse getAllList(SYSUser sysUser) {
        List<SYSUser> list = null;
        if (sysUser.getRealName() == null || "".equals(sysUser.getRealName())) {
            list = userSSOMapper.selectList(new QueryWrapper<SYSUser>()
                    .eq("is_delete", ConfigSSO.NOT_DELETE));
        } else {
            list = userSSOMapper.selectList(new QueryWrapper<SYSUser>()
                    .eq("is_delete", ConfigSSO.NOT_DELETE)
                    .like("real_name", sysUser.getRealName()));
        }
        if (list == null) {
            list = new ArrayList<>();
        }
        Map<String, Object> map = new HashMap<>(1);
        map.put(ConfigSSO.DATA_LIST, list);
        return new APIResponse(map);
    }

    @Override
    public APIResponse listByRole(Integer roleId) {
        SYSUser sysUser = new SYSUser();
        List<SYSUser> list = userSSOMapper.listByRole(sysUser);

        Map<String, Object> data = new HashMap<>();
        if(list.size()!=0){
            for(SYSUser user : list){
                user.setPassword(null);
            }
        }

        data.put(ConfigSSO.DATA_LIST,list);
        return new APIResponse(data);
    }
}
