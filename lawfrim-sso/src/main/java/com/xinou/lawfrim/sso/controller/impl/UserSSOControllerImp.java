package com.xinou.lawfrim.sso.controller.impl;


import com.xinou.lawfrim.common.util.APIResponse;
import com.xinou.lawfrim.sso.config.ConfigSSO;
import com.xinou.lawfrim.sso.entity.ResReSYSUserRole;
import com.xinou.lawfrim.sso.entity.SYSUser;
import com.xinou.lawfrim.sso.service.ReUserRoleSSOService;
import com.xinou.lawfrim.sso.service.UserSSOService;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import springfox.documentation.annotations.ApiIgnore;

import javax.servlet.http.HttpSession;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by zhangbo on 2017/10/24.
 * 系统用户
 */
@Controller("userSSOControllerImpl")
@RequestMapping("/admin/user")
@ApiIgnore
public class UserSSOControllerImp  {

    @Autowired
    UserSSOService service;
    @Autowired
    ReUserRoleSSOService reURService;

    @RequestMapping(value = "add")
    @ResponseBody
    @RequiresPermissions("/admin/user/add")
    public APIResponse addUser(@RequestBody SYSUser sysUser) {
        return service.addUser(sysUser.getAccount(), sysUser.getRealName(), sysUser.getMobile(), sysUser.getRoleIds(), sysUser.getAppIds());
    }

    @RequestMapping("del")
    @ResponseBody
    @RequiresPermissions("/admin/user/del")
    public APIResponse delUser(@RequestBody SYSUser sysUser) {
        return service.delUser(sysUser.getId());
    }

    @RequestMapping("update")
    @ResponseBody
    @RequiresPermissions("/admin/user/update")
    public APIResponse updateUser(@RequestBody SYSUser sysUser) {
        return service.updateUser(sysUser.getId(), sysUser.getRealName(), sysUser.getPassword(), sysUser.getMobile(), sysUser.getRoleIds(), sysUser.getIsEnable());
    }

    @RequestMapping("list")
    @ResponseBody
    @RequiresPermissions("/admin/user/list")
    public APIResponse userList(@RequestBody SYSUser sysUser) {
        return service.userList(sysUser.getPage(), sysUser.getPageSize(), sysUser.getSelectValue(), sysUser.getSearchValue());
    }

    @RequestMapping("get")
    @ResponseBody
//    @RequiresPermissions("/admin/user/get")
    public APIResponse getUserById(@RequestBody SYSUser sysUser) {

        SYSUser user = service.getById(sysUser.getId());

        //取出角色
        List<Integer> ids = new ArrayList<>();
        ids.add(sysUser.getId());

        List<ResReSYSUserRole> list = reURService.getRoleByUserId(ids);

        if (list != null && list.size() != 0) {
            List<Map<String, Object>> result = new ArrayList<>();
            for (ResReSYSUserRole info : list) {
                Map<String, Object> param = new HashMap<>();
                param.put("id", info.getRoleId());
                param.put("name", info.getRoleName());
                param.put("appId", info.getAppId());
                result.add(param);
            }
            user.setRoleAppList(result);
        }

        Map<String, Object> data = new HashMap<>();
        data.put("user", user);

        return new APIResponse(data);
    }

    @RequestMapping("updateAllPwd")
    @ResponseBody
    @RequiresPermissions("/admin/user/updateAllPwd")
    public APIResponse updateAllPwd(@RequestBody SYSUser sysUser1) {

        SYSUser sysUser = service.getById(sysUser1.getId());

        sysUser.setPassword(sysUser1.getNewPwd());


        service.updateById(sysUser);


        return new APIResponse();
    }


    @RequestMapping("updatePwd")
    @ResponseBody
    @RequiresPermissions("/admin/user/updatePwd")
    public APIResponse updatePwd(HttpSession session, @RequestBody SYSUser sysUser) {

        //1.查找当前登录的用户 看旧密码是否与当前密码相同
        int adminId = (int) session.getAttribute("sysUserId");

        SYSUser admin = service.getById(adminId);


        if (!admin.getPassword().equals(sysUser.getOldPwd())) {
            return new APIResponse(ConfigSSO.RE_CODE_PWD_NOSAME, ConfigSSO.RE_MSG_PWD_NOSAME);
        }

        //2.修改
        admin.setPassword(sysUser.getNewPwd());
        admin.setGmtModified(null);
        boolean res = service.updateById(admin);

        if (!res) {
            return new APIResponse(ConfigSSO.RE_CODE_ERROR, ConfigSSO.RE_MSG_ERROR_UPDATE);
        }

        return new APIResponse();
    }

    @ResponseBody
    @RequestMapping("/getall")
    public APIResponse getAllList(@RequestBody SYSUser sysUser) {
        return service.getAllList(sysUser);
    }

    @ResponseBody
    @RequestMapping("/listByRole")
    APIResponse listByRole(@RequestBody SYSUser sysUser) {
        return service.listByRole(sysUser.getRoleId());
    }

}
