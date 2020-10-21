package com.xinou.lawfrim.sso.controller.impl;


import com.github.xiaoymin.knife4j.annotations.ApiOperationSupport;
import com.xinou.lawfrim.common.aspect.LogTypeEnum;
import com.xinou.lawfrim.common.aspect.SystemLog;
import com.xinou.lawfrim.common.util.APIResponse;
import com.xinou.lawfrim.common.util.CountDays;
import com.xinou.lawfrim.sso.config.ConfigSSO;
import com.xinou.lawfrim.sso.controller.AdminSSOController;
import com.xinou.lawfrim.sso.entity.AdminEntity;
import com.xinou.lawfrim.sso.entity.ICCardSSO;
import com.xinou.lawfrim.sso.entity.MemberSSO;
import com.xinou.lawfrim.sso.entity.SYSUser;
import com.xinou.lawfrim.sso.mapper.AdminSSOMapper;
import com.xinou.lawfrim.sso.service.AdminSSOService;
import com.xinou.lawfrim.sso.service.ReUserRoleSSOService;
import com.xinou.lawfrim.sso.service.RoleSSOService;
import com.xinou.lawfrim.sso.shiro.FilterChainDefinitionsService;
import com.xinou.lawfrim.sso.shiro.SmartShiro;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.cache.MapCache;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletResponse;

/**
 * Created by zhangbo on 2017/10/18.
 * 用户sso登录
 */
@Controller("adminSSOControllerImpl")
@RequestMapping("/admin/")
@Api(tags = {"律师法务-登录接口"})
public class AdminSSOControllerImp implements AdminSSOController {

    @Autowired
    AdminSSOService adminService;
    @Autowired
    SmartShiro smartShiro;
    @Autowired
    RoleSSOService roleService;
    @Autowired
    ReUserRoleSSOService reUserRoleService;
    @Autowired
    FilterChainDefinitionsService chainService;
    @Autowired
    AdminSSOMapper adminSSOMapper;


    @Override
    @RequestMapping("login")
    @ResponseBody
    @ApiOperationSupport(includeParameters = {"adminEntity.account", "adminEntity.pwd", "adminEntity.ip", "adminEntity.type"})
    @ApiOperation(httpMethod = "POST", value = "管理员登录")
//    @SystemLog(module = LogTypeEnum.LOGIN_MODULE, methods = LogTypeEnum.LOGIN_MODULE_SYSTEM, name = "#{adminEntity.account,adminEntity.icNo}", type = "1",loginType = "#{adminEntity.type}")
    public APIResponse adminLogin(HttpServletResponse response, @RequestBody(required = false) AdminEntity adminEntity) {

        if (adminEntity.getType() == 1 || adminEntity.getType() == 3) {
            return adminService.adminLogin(adminEntity);
        }

        ICCardSSO icCard = adminSSOMapper.icInfo(adminEntity.getIcNo());

        if (icCard == null) {
            return new APIResponse(ConfigSSO.RE_CODE_IC_CARD_NOT_EXIST, ConfigSSO.RE_MSG_IC_CARD_NOT_EXIST);
        }

        if (!CountDays.SEBetween(icCard.getStartTime(), icCard.getEndTime())) {
            return new APIResponse(ConfigSSO.RE_CODE_IC_CARD_OVERDUE, ConfigSSO.RE_MSG_IC_CARD_OVERDUE);
        }

        MemberSSO member = adminSSOMapper.memberInfo(icCard.getMemberId());

        if (member == null || member.getUserId() == 0) {
            return new APIResponse(ConfigSSO.RE_CODE_IC_CARD_USER_NOT_EXIST, ConfigSSO.RE_MSG_IC_CARD_USER_NOT_EXIST);
        }

        SYSUser sysUser = adminService.getById(member.getUserId());

        if (sysUser == null) {
            return new APIResponse(ConfigSSO.RE_CODE_IC_CARD_USER_NOT_EXIST, ConfigSSO.RE_MSG_IC_CARD_USER_NOT_EXIST);
        }

        AdminEntity info = new AdminEntity();
        info.setAccount(sysUser.getAccount());
        info.setPwd(sysUser.getPassword());
        info.setIp(adminEntity.getIp());
        return adminService.adminLogin(info);
    }

    @Override
    @RequestMapping("logout")
    @ResponseBody
    @ApiOperation(httpMethod = "GET", value = "管理员退出登录")
    public APIResponse logout() {

        chainService.reloadFilterChains();
        MapCache mc = (MapCache) smartShiro.getCacheManager().getCache(ConfigSSO.SHIRO_CACHE_PATH);
        mc.clear();
        SecurityUtils.getSubject().logout();
        return new APIResponse();
    }

}
