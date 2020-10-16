package com.xinou.lawfrim.web.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.xinou.lawfrim.common.util.APIResponse;
import com.xinou.lawfrim.common.util.Config;
import com.xinou.lawfrim.common.util.MD5Util;
import com.xinou.lawfrim.sso.entity.ReSYSUserApp;
import com.xinou.lawfrim.sso.entity.ReSYSUserRole;
import com.xinou.lawfrim.sso.entity.SYSUser;
import com.xinou.lawfrim.sso.service.ReUserAppSSOService;
import com.xinou.lawfrim.sso.service.ReUserRoleSSOService;
import com.xinou.lawfrim.sso.service.UserSSOService;
import com.xinou.lawfrim.web.dto.BusLawyerDto;
import com.xinou.lawfrim.web.entity.BusLawyer;
import com.xinou.lawfrim.web.mapper.BusLawyerMapper;
import com.xinou.lawfrim.web.service.IBusLawyerService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author Wangxin
 * @since 2020-10-16
 */
@Service
@Primary
public class BusLawyerServiceImpl extends ServiceImpl<BusLawyerMapper, BusLawyer> implements IBusLawyerService {

    @Autowired
    private  BusLawyerMapper busLawyerMapper;

    @Autowired
    private UserSSOService userSSOService;

    @Autowired
    private ReUserRoleSSOService reUserRoleSSOService;

    @Autowired
    private ReUserAppSSOService reUserAppSSOService;

    @Override
    public APIResponse listLawyer(BusLawyerDto busLawyer) {
        return null;
    }

    @Override
    @Transactional(rollbackFor = RuntimeException.class)
    public boolean addBusLawyer(BusLawyerDto lawyer) {
        List<SYSUser> sysUserList = userSSOService.list(
                new QueryWrapper<SYSUser>().eq("is_delete", 0).eq("account", lawyer.getAccount())
        );
        if (sysUserList.size() != 0) {
            throw new RuntimeException(Config.RE_MSG_USER_EXIST);
        }

        //插入一个后台用户
        SYSUser user = new SYSUser();
        user.setAccount(lawyer.getAccount());
//        user.setPassword(MD5Util.MD5("123456").toLowerCase());
        user.setPassword(lawyer.getPassword());
        user.setRealName(lawyer.getName());
        user.setIsEnable(1);
        boolean res = userSSOService.save(user);
        if (!res) {
            throw new RuntimeException("新增后台用户失败");
        }

        //添加用户
        BusLawyer busLawyer = new BusLawyer();
        busLawyer.setName(lawyer.getName());
        busLawyer.setState(1);
        busLawyer.setSysUserId(user.getId());
        res = save(busLawyer);
        if (!res) {
            throw new RuntimeException("新增用户失败");
        }

        //角色与用户的关系
        ReSYSUserRole reSYSUserRole = new ReSYSUserRole();
        reSYSUserRole.setUserId(user.getId());
        reSYSUserRole.setRoleId(lawyer.getRoleId());
        res = reUserRoleSSOService.save(reSYSUserRole);
        if (!res) {
            throw new RuntimeException("添加用户角色失败");
        }

        //应用与用户的关系
        ReSYSUserApp reSYSUserApp = new ReSYSUserApp();
        reSYSUserApp.setUserId(user.getId());
        reSYSUserApp.setAppId(Config.ADMIN_APP_ID);
        res = reUserAppSSOService.save(reSYSUserApp);
        if (!res) {
            throw new RuntimeException("添加用户应用失败");
        }

        return true;
    }
}
