package com.xinou.lawfrim.sso.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.xinou.lawfrim.common.util.APIResponse;
import com.xinou.lawfrim.common.util.MD5Util;
import com.xinou.lawfrim.sso.config.ConfigSSO;
import com.xinou.lawfrim.sso.entity.AdminEntity;
import com.xinou.lawfrim.sso.entity.ReSYSUserRole;
import com.xinou.lawfrim.sso.entity.Role;
import com.xinou.lawfrim.sso.entity.SYSUser;
import com.xinou.lawfrim.sso.mapper.AdminSSOMapper;
import com.xinou.lawfrim.sso.service.AdminSSOService;
import com.xinou.lawfrim.sso.service.ReUserRoleSSOService;
import com.xinou.lawfrim.sso.service.RoleSSOService;
import com.xinou.lawfrim.sso.shiro.FilterChainDefinitionsService;
import com.xinou.lawfrim.sso.shiro.SmartShiro;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.*;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.cache.MapCache;
import org.apache.shiro.subject.Subject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Service;

import java.util.*;

/**
 * Created by zhangbo on 2017/10/18.
 * admin后台管理员相关
 */
@Service("AdminSSOServiceImpl")
@Primary
public class AdminSSOServiceImpl extends ServiceImpl<AdminSSOMapper, SYSUser> implements AdminSSOService {

    @Autowired
    AdminSSOMapper mapper;
    @Autowired
    SmartShiro smartShiro;
    @Autowired
    RoleSSOService roleService;
    @Autowired
    ReUserRoleSSOService reUserRoleService;
    @Autowired
    FilterChainDefinitionsService chainService;


    @Override
    public void login(String account, String ip) {


        mapper.login(account, ip);
    }

    @Override
    public APIResponse adminLogin(AdminEntity adminEntity) {

        int id = 0;

        //使用权限工具进行用户登录，登录成功后跳到shiro配置的successUrl中
        try {
            //登录,身份验证
            UsernamePasswordToken token = new UsernamePasswordToken(adminEntity.getAccount(), adminEntity.getPwd());
            Subject s = SecurityUtils.getSubject();
            s.login(token);

            //执行此代码才能存入缓存信息,业务逻辑上没有用, 为了能够存入缓存数据  "login" 随便写的
            s.isPermitted("login");


            //设置session不会过期失效
            s.getSession().setTimeout(-1000);

            //从缓存中获取  该用户的  角色  和   权限信息
            MapCache mc = (MapCache) smartShiro.getCacheManager().getCache(ConfigSSO.SHIRO_CACHE_PATH);

            SimpleAuthorizationInfo info = (SimpleAuthorizationInfo) mc.get(smartShiro.getAuthenticationInfo(token).getPrincipals());
            Set permissions = info.getStringPermissions();
            Set roles = info.getRoles();

            //角色与 角色等级匹配
            List<SYSUser> users = list(new QueryWrapper<SYSUser>().eq("account", adminEntity.getAccount()).eq("is_delete",0));

            List<Map<String,Object>> roleResult = new ArrayList<>();

            if (users != null && users.size() != 0) {
                SYSUser user = users.get(0);
                int userId = user.getId();

                id= userId;

                List<ReSYSUserRole> userRoles = reUserRoleService.list(new QueryWrapper<ReSYSUserRole>().eq("user_id", userId).eq("is_delete",0));

                List<Integer> roleIds = new ArrayList<>();
                for (ReSYSUserRole roleInfo : userRoles){
                    int roleId = roleInfo.getRoleId();
                    roleIds.add(roleId);
                }

                List<Role> roleList = (List<Role>) roleService.listByIds(roleIds);


                for (Role info1 : roleList){
                    int gradeId = info1.getGradeId();
                    int isGrade = info1.getIsGrade();
                    int gradeType = info1.getGradeType();
                    String name = info1.getName();
                    for(Object str : roles){
                        if (str.equals(name)){
                            Map<String,Object> rolesMap = new HashMap<>();
                            rolesMap.put("name",name);
                            rolesMap.put("gradeId",gradeId);
                            rolesMap.put("isGrade",isGrade);
                            rolesMap.put("gradeType",gradeType);
                            roleResult.add(rolesMap);
                        }
                    }
                }
            }

            //更新系统数据库字段
            login(adminEntity.getAccount(), adminEntity.getIp());

            //返回参数
            Map<String, Object> data = new HashMap<>();
            data.put("userId", id);
            data.put("roles", roleResult);
            data.put("permissions", permissions);
            data.put("token", MD5Util.MD5(token.toString()));

            return new APIResponse(data);
        } catch (AuthenticationException e) {
            //身份验证失败
            String exceptionClassName = e.getClass().getName();
            if (UnknownAccountException.class.getName().equals(exceptionClassName)) {
                //最终会抛给异常处理器
                return new APIResponse(ConfigSSO.RE_CODE_USER_ERROR, "账号不存在");
            } else if (IncorrectCredentialsException.class.getName().equals(exceptionClassName)) {
                return new APIResponse(ConfigSSO.RE_CODE_USER_ERROR, "用户名/密码错误");
            } else if ("randomCodeError".equals(exceptionClassName)) {
                return new APIResponse(ConfigSSO.RE_CODE_USER_ERROR, "验证码错误");
            } else if (LockedAccountException.class.getName().equals(exceptionClassName)) {
                return new APIResponse(ConfigSSO.RE_CODE_USER_ERROR, "锁定的账号");
            } else if (ExpiredCredentialsException.class.getName().equals(exceptionClassName)) {
                return new APIResponse(ConfigSSO.RE_CODE_USER_ERROR, "账号登录已过期,请重新登录");
            } else if (DisabledAccountException.class.getName().equals(exceptionClassName)) {
                return new APIResponse(ConfigSSO.RE_CODE_USER_ERROR, "该账号已被禁用");
            } else if (ExcessiveAttemptsException.class.getName().equals(exceptionClassName)) {
                return new APIResponse(ConfigSSO.RE_CODE_USER_ERROR, "登录失败次数过多");
            } else {
                return new APIResponse(ConfigSSO.RE_CODE_USER_ERROR, "请重新登录!");
            }
        }
    }
}
