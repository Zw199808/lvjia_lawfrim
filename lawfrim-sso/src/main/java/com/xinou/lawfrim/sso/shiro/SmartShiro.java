package com.xinou.lawfrim.sso.shiro;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.xinou.lawfrim.sso.entity.MemberSSO;
import com.xinou.lawfrim.sso.entity.SYSUser;
import com.xinou.lawfrim.sso.mapper.AdminSSOMapper;
import org.apache.shiro.authc.*;
import org.apache.shiro.authz.AuthorizationException;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.servlet.http.HttpSession;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * All rights Reserved, Designed By 信鸥科技
 * project : Innovation
 * Created by zhangbo on 17/10/18.
 * 注意：本内容仅限于信鸥科技有限公司内部传阅，禁止外泄以及用于其他的商业目的
 * Description:
 */
@Service
@Transactional
public class SmartShiro extends AuthorizingRealm {

    @Autowired
    private AdminSSOMapper adminMapper;
    @Autowired
    HttpSession session;

    /**
     * 权限认证
     */
    @Override
    protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principalCollection) {

        if (principalCollection == null) {
            throw new AuthorizationException("Principal对象不能为空");
        }

        //获取登录时输入的用户名
        String loginName = (String) principalCollection.fromRealm(getName()).iterator().next();

        List<SYSUser> users = adminMapper.selectList(new QueryWrapper<SYSUser>().eq("account", loginName).eq("is_delete",0));

        int i=0;

        if (users != null && users.size() != 0) {
            SYSUser user = users.get(0);
            int userId = user.getId();
            List<String> rolesName = adminMapper.findRoleByUserId(userId);
            Set<String> roleNameSet = new HashSet<>();
            roleNameSet.addAll(rolesName);
            //权限信息对象info,用来存放查出的用户的所有的角色（role）及权限（permission）
            SimpleAuthorizationInfo info = new SimpleAuthorizationInfo();
            //用户的角色集合
            info.setRoles(roleNameSet);
            //用户的角色对应的所有权限，如果只使用角色定义访问权限，下面的四行可以不要
            List<String> permissionUrl = adminMapper.findPermissionUrlByUserId(userId);

            info.addStringPermissions(permissionUrl);

            info.getStringPermissions();

//            System.out.println("permissionUrl-----"+i+"-----"+permissionUrl);

            return info;
        }
        return null;
    }

    /**
     * 登录认证;
     */
    @Override
    protected AuthenticationInfo doGetAuthenticationInfo(
            AuthenticationToken authenticationToken) throws AuthenticationException {
        //UsernamePasswordToken对象用来存放提交的登录信息
        UsernamePasswordToken token = (UsernamePasswordToken) authenticationToken;

        String account = token.getUsername();

        if (account == null) {
            throw new AccountException("用户名不能为空");
        }
        //查出是否有此用户
        List<SYSUser> users = adminMapper.selectList(
                new QueryWrapper<SYSUser>()
                        .eq("account", account)
                        .eq("is_delete", 0)
        );

        if (users == null || users.size() == 0) {
            throw new UnknownAccountException("用户不存在");
        }

        //若存在，将此用户存放到登录认证info中
        SYSUser user = users.get(0);

        session.setAttribute("sysUserId",user.getId());

        return new SimpleAuthenticationInfo(user.getAccount(), user.getPassword(), getName());
    }


}
