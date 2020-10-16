package com.xinou.lawfrim.sso.controller.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.xinou.lawfrim.common.aspect.LogTypeEnum;
import com.xinou.lawfrim.common.aspect.SystemLog;
import com.xinou.lawfrim.common.util.APIResponse;
import com.xinou.lawfrim.sso.entity.ResReRolePermission;
import com.xinou.lawfrim.sso.entity.Role;
import com.xinou.lawfrim.sso.service.ReRolePermissionSSOService;
import com.xinou.lawfrim.sso.service.RoleSSOService;
import com.xinou.lawfrim.sso.shiro.FilterChainDefinitionsService;
import com.xinou.lawfrim.sso.shiro.SmartShiro;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import springfox.documentation.annotations.ApiIgnore;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by zhangbo on 2017/10/25.
 * 角色
 */
@Controller("roleSSOControllerImpl")
@RequestMapping("/admin/role")
@ApiIgnore

public class RoleSSOControllerImp{

    @Autowired
    RoleSSOService service;
    @Autowired
    FilterChainDefinitionsService chainService;
    @Autowired
    SmartShiro smartShiro;
    @Autowired
    ReRolePermissionSSOService reRPService;

    @RequestMapping("add")
    @ResponseBody
    @RequiresPermissions("/admin/role/add")
    @SystemLog(module = LogTypeEnum.ROLE_MODULE, methods = LogTypeEnum.ROLE_MODULE_ADD, name = "#{role.name}", type = "3")
    public APIResponse addRole(@RequestBody Role role) {
        return service.addRole(role.getName(), role.getDescription(), role.getPermissionIds(),
                role.getAppId(), role.getGradeId(), role.getGradeType(), role.getIsGrade());
    }

    @RequestMapping("del")
    @ResponseBody
    @RequiresPermissions("/admin/role/del")
    @SystemLog(module = LogTypeEnum.ROLE_MODULE, methods = LogTypeEnum.ROLE_MODULE_DELETE, name = "#{role.id}", type = "3")
    public APIResponse delRole(@RequestBody Role role) {
        return service.delRole(role.getId());
    }

    @RequestMapping("update")
    @ResponseBody
    @RequiresPermissions("/admin/role/update")
    @SystemLog(module = LogTypeEnum.ROLE_MODULE, methods = LogTypeEnum.ROLE_MODULE_UPDATE, name = "#{role.name}", type = "3")
    public APIResponse updateRole(@RequestBody Role role) {
        return service.updateRole(role.getId(), role.getName(), role.getDesc(), role.getPermissionIds(), role.getIsEnable(), role.getSort());
    }

    @RequestMapping("list")
    @ResponseBody
    @RequiresPermissions("/admin/role/list")
    public APIResponse roleList(@RequestBody Role role) {
        return service.roleList(role.getPage(), role.getPageSize(), role.getSelectValue(), role.getSearchValue());
    }

    @RequestMapping("getAll")
    @ResponseBody
    public APIResponse getAllRoleList() {
        List<Role> roleList = service.list(new QueryWrapper<Role>().eq("is_delete",0));
        Map<String,Object> data = new HashMap<>();
        data.put("dataList",roleList);
        return new APIResponse(data);
    }


    @RequestMapping("get")
    @ResponseBody
//    @RequiresPermissions("/admin/role/get")
    public APIResponse getRoleById(@RequestBody Role role1) {
        Role role = service.getById(role1.getId());

        //取出权限
        List<Integer> ids = new ArrayList<>();
        ids.add(role1.getId());

        List<ResReRolePermission> list = reRPService.getPermissionByRoleId(ids);

        if (list != null && list.size()!=0){
            List<Map<String,Object>> result = new ArrayList<>();
            for (ResReRolePermission info : list){
                Map<String,Object> param = new HashMap<>();
                param.put("id",info.getPermissionId());
                param.put("name",info.getPermissionName());
                param.put("appId",info.getAppId());
                result.add(param);
            }
            role.setPermissions(result);
        }


        Map<String, Object> data = new HashMap<>();
        data.put("role", role);


        return new APIResponse(data);
    }

    @RequestMapping("list/appId")
    @ResponseBody
//    @RequiresPermissions("/admin/role/list/appId")
    public APIResponse getRoleByAppId(@RequestBody Role role) {

        List<Role> roles = service.list(new QueryWrapper<Role>()
                .eq("app_id", role.getId()).eq("is_delete",0).ne("type",1));

        Map<String, Object> data = new HashMap<>();
        data.put("dataList", roles);


        return new APIResponse(data);
    }


    @RequestMapping("list/all")
    @ResponseBody
//    @RequiresPermissions("/admin/role/list/appId")
    public APIResponse roleListAll(@RequestBody Role role) {

        List<Role> roles = service.list(new QueryWrapper<Role>()
                .eq("is_delete",0).ne("type",1));

        Map<String, Object> data = new HashMap<>();
        data.put("dataList", roles);


        return new APIResponse(data);
    }

}
