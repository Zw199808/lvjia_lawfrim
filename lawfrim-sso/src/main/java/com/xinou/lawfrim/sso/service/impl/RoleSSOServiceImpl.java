package com.xinou.lawfrim.sso.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.xinou.lawfrim.common.util.APIResponse;
import com.xinou.lawfrim.common.util.StringUtil;
import com.xinou.lawfrim.sso.config.ConfigSSO;
import com.xinou.lawfrim.sso.entity.ReRolePermission;
import com.xinou.lawfrim.sso.entity.ResReRolePermission;
import com.xinou.lawfrim.sso.entity.Role;
import com.xinou.lawfrim.sso.mapper.RoleSSOMapper;
import com.xinou.lawfrim.sso.service.ReRolePermissionSSOService;
import com.xinou.lawfrim.sso.service.RoleSSOService;
import com.xinou.lawfrim.sso.shiro.FilterChainDefinitionsService;
import com.xinou.lawfrim.sso.shiro.SmartShiro;
import org.apache.shiro.cache.MapCache;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by zhangbo on 2017/10/25.
 * 权限
 */
@Service("RoleSSOServiceImpl")
@Primary
public class RoleSSOServiceImpl extends ServiceImpl<RoleSSOMapper, Role> implements RoleSSOService {

    @Autowired
    ReRolePermissionSSOService reRPService;
    @Autowired
    FilterChainDefinitionsService chainService;
    @Autowired
    SmartShiro smartShiro;


    public Page<Role> selectPage(Page<Role> page) {
        page.setRecords(baseMapper.selectList(page));
        return page;
    }

    @Override
    @Transactional
    public APIResponse addRole(String name, String desc, int[] permissionIds, int appId, int grade_id, int grade_type, int is_grade) {

        //添加role表中的信息
        Role role = new Role();
        role.setName(name);
        role.setDescription(desc);
        role.setAppId(appId);
        role.setGradeId(grade_id);
        role.setGradeType(grade_type);
        role.setIsGrade(is_grade);
        role.setIsEnable(1);
        role.setType(1);


        //名字不能重复
        Role db_role = getOne(new QueryWrapper<Role>().eq("name", name).eq("is_delete",0));
        if (db_role != null) {
            return new APIResponse(ConfigSSO.RE_CODE_ROLE_NAME_HAD, ConfigSSO.RE_MSG_ROLE_NAME_HAD);
        }

        save(role);

        int roleId = role.getId();

        //permissionIds字段  来添加  关联表

        //批量插入 角色和权限关系表
        List<ReRolePermission> reList = new ArrayList<>();
        for (int info : permissionIds) {
            ReRolePermission reRP = new ReRolePermission();
            reRP.setRoleId(roleId);
            reRP.setPermissionId(info);
            reList.add(reRP);
        }

        boolean result = reRPService.saveBatch(reList);

        //删除  shiro缓存 重新加载
        chainService.reloadFilterChains();
        MapCache mc = (MapCache) smartShiro.getCacheManager().getCache(ConfigSSO.SHIRO_CACHE_PATH);
        mc.clear();


        if (result) {
            return new APIResponse();
        }

        return new APIResponse(ConfigSSO.RE_CODE_ROLE_ERROR, ConfigSSO.RE_MSG_ROLE_ERROR);
    }

    @Override
    @Transactional
    public APIResponse delRole(int id) {
        //删除 role表中数据
        Role role = getById(id);
        role.setId(id);
        role.setIsDelete(ConfigSSO.IS_DELETE);

        boolean result = updateById(role);

        //清除关系表中数据
        List<ReRolePermission> rrps = reRPService.list(new QueryWrapper<ReRolePermission>().eq("role_id",id).eq("is_delete",0));

        for (ReRolePermission info : rrps){
            info.setIsDelete(ConfigSSO.IS_DELETE);
        }

        boolean reResult = reRPService.updateBatchById(rrps);


        //删除  shiro缓存 重新加载
        chainService.reloadFilterChains();
        MapCache mc = (MapCache) smartShiro.getCacheManager().getCache(ConfigSSO.SHIRO_CACHE_PATH);
        mc.clear();

        if (result && reResult) {
            return new APIResponse();
        }
        return new APIResponse(ConfigSSO.RE_CODE_ROLE_ERROR, ConfigSSO.RE_MSG_ROLE_ERROR);
    }

    @Override
    @Transactional
    public APIResponse updateRole(int id, String name, String desc, int[] permissionIds, int isEnable, int sort) {

        Role role = getById(id);
        role.setId(id);
        role.setName(name);
        role.setDescription(desc);
        role.setIsEnable(isEnable);
        role.setSort(sort);


        //名字不能重复
        List<Role> db_role = list(new QueryWrapper<Role>().eq("name", name).eq("is_delete",0));
        if (db_role != null) {
            for (Role info : db_role) {
                int roleId = info.getId();
                if (id != roleId) {
                    return new APIResponse(ConfigSSO.RE_CODE_ROLE_NAME_HAD, ConfigSSO.RE_MSG_ROLE_NAME_HAD);
                }
            }
        }

        //修改角色表中信息
        updateById(role);

        //修改关系表中 关系    先删除  后添加
        List<ReRolePermission> list = reRPService.list(new QueryWrapper<ReRolePermission>().eq("role_id", id).eq("is_delete",0));


        for (ReRolePermission info : list) {
            info.setIsDelete(ConfigSSO.IS_DELETE);
        }

        reRPService.updateBatchById(list);

        //批量插入 关系表
        List<ReRolePermission> insertList = new ArrayList<>();
        for (int info : permissionIds) {
            ReRolePermission reUR = new ReRolePermission();
            reUR.setRoleId(id);
            reUR.setPermissionId(info);
            insertList.add(reUR);
        }

        boolean result = reRPService.saveBatch(insertList);


        chainService.reloadFilterChains();
        MapCache mc = (MapCache) smartShiro.getCacheManager().getCache(ConfigSSO.SHIRO_CACHE_PATH);
        mc.clear();

        if (result) {
            return new APIResponse();
        }

        return new APIResponse(ConfigSSO.RE_CODE_ROLE_ERROR, ConfigSSO.RE_MSG_ROLE_ERROR);
    }

    @Override
    public APIResponse roleList(int page, int pageSize, String selectValue, String searchValue) {

        int skip = (page-1)*pageSize;

        List<Role> list;
        int total = 0;
        if (StringUtil.isNullString(searchValue)) {
            list = list(
                    new QueryWrapper<Role>().eq("is_delete", 0).orderByDesc("gmt_create")
                            .last("limit "+skip+","+pageSize)
            );
            total = count(
                    new QueryWrapper<Role>().eq("is_delete", 0)
            );
        } else {
            list = list(
                    new QueryWrapper<Role>().eq("is_delete", 0).like(selectValue, searchValue).orderByDesc("gmt_create")
                            .last("limit "+skip+","+pageSize)
            );
            total = count(
                    new QueryWrapper<Role>().eq("is_delete", 0).like(selectValue, searchValue)
            );
        }

        //如果没有结果
        if (list == null || list.size() == 0){
            Map<String, Object> data = new HashMap<>();
            data.put("dataList", list);
            data.put("total", 0);
            return new APIResponse(data);
        }


        //根据roleId  查询对应的权限
        List<Integer> ids = new ArrayList<>();

        for (Role info : list) {
            int roleId = info.getId();
            ids.add(roleId);
        }

        List<ResReRolePermission> permissions = reRPService.getPermissionByRoleId(ids);

        for (Role role : list) {
            int id = role.getId();
            //匹配每个role的 权限列表
            List<Map<String,Object>> result = new ArrayList<>();
            for (ResReRolePermission info : permissions){
                Map<String,Object> param = new HashMap<>();
                param.put("id",info.getPermissionId());
                param.put("name",info.getPermissionName());
                param.put("appId",info.getAppId());
                result.add(param);
            }
            role.setPermissions(result);
        }


        Map<String, Object> data = new HashMap<>();
        data.put("dataList", list);
        data.put("total", total);


        return new APIResponse(data);

    }


}
