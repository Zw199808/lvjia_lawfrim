package com.xinou.lawfrim.sso.controller.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.xinou.lawfrim.common.util.APIResponse;
import com.xinou.lawfrim.common.util.StringUtil;
import com.xinou.lawfrim.sso.config.ConfigSSO;
import com.xinou.lawfrim.sso.entity.Permission;
import com.xinou.lawfrim.sso.service.PermissionSSOService;
import com.xinou.lawfrim.sso.shiro.FilterChainDefinitionsService;
import com.xinou.lawfrim.sso.shiro.SmartShiro;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.apache.shiro.cache.MapCache;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by zhangbo on 2017/10/24.
 * 系统用户
 */
@Controller("permissionSSOControllerImpl")
@RequestMapping("/admin/permission")
public class PermissionSSOControllerImp  {

    @Autowired
    PermissionSSOService service;
    @Autowired
    FilterChainDefinitionsService chainService;
    @Autowired
    SmartShiro smartShiro;


    @RequestMapping("add")
    @ResponseBody
    @RequiresPermissions("/admin/permission/add")
    public APIResponse addPermission(@RequestBody Permission permission1) {


        Permission permission = new Permission();
        permission.setAppId(permission1.getAppId());
        permission.setIsMenu(permission1.getIsMenu());
        permission.setName(permission1.getName());
        permission.setUrl(permission1.getUrl());
        permission.setIsEnable(1);
        if (permission1.getIsMenu() == 0) {
            //不是菜单
            permission.setParentId(permission1.getParentId());
        }


        //权限名已存在
        Permission db_p = service.getOne(new QueryWrapper<Permission>().eq("name", permission1.getName()).eq("is_delete",0));
        if (db_p != null) {
            return new APIResponse(ConfigSSO.RE_CODE_PERMISSION_NAME_HAD, ConfigSSO.RE_MSG_PERMISSION_NAME_HAD);
        }

        boolean result = service.save(permission);


        chainService.reloadFilterChains();
        MapCache mc = (MapCache) smartShiro.getCacheManager().getCache(ConfigSSO.SHIRO_CACHE_PATH);
        mc.clear();


        if (result) {
            return new APIResponse();
        }

        return new APIResponse(ConfigSSO.RE_CODE_ROLE_ERROR, ConfigSSO.RE_MSG_ROLE_ERROR);
    }

    @RequestMapping("del")
    @ResponseBody
    @RequiresPermissions("/admin/permission/del")
    public APIResponse delPermission(@RequestBody Permission permission1) {


        Permission permission = service.getById(permission1.getId());

        permission.setId(permission1.getId());
        permission.setIsDelete(1);

        boolean result = service.updateById(permission);

        chainService.reloadFilterChains();
        MapCache mc = (MapCache) smartShiro.getCacheManager().getCache(ConfigSSO.SHIRO_CACHE_PATH);
        mc.clear();


        if (result) {
            return new APIResponse();
        }
        return new APIResponse(ConfigSSO.RE_CODE_ROLE_ERROR, ConfigSSO.RE_MSG_ROLE_ERROR);
    }

    @RequestMapping("update")
    @ResponseBody
    @RequiresPermissions("/admin/permission/update")
    public APIResponse updatePermission(@RequestBody Permission permission1) {

        Permission permission = service.getById(permission1.getId());
        permission.setName(permission1.getName());
        permission.setUrl(permission1.getUrl());
        permission.setIsMenu(permission1.getIsMenu());
        permission.setIsEnable(permission1.getIsEnable());
        permission.setSort(permission1.getSort());
        if (permission1.getIsMenu() == 0) {
            //不是菜单
            permission.setParentId(permission1.getParentId());
        }


        //权限名已存在
        List<Permission> db_p = service.list(new QueryWrapper<Permission>().eq("name", permission1.getName()).eq("is_delete",0));
        if (db_p != null) {
            for (Permission info : db_p) {
                int permissionId = info.getId();
                if (permission1.getId() != permissionId) {
                    return new APIResponse(ConfigSSO.RE_CODE_PERMISSION_NAME_HAD, ConfigSSO.RE_MSG_PERMISSION_NAME_HAD);
                }
            }
        }


        boolean result = service.updateById(permission);

        chainService.reloadFilterChains();
        MapCache mc = (MapCache) smartShiro.getCacheManager().getCache(ConfigSSO.SHIRO_CACHE_PATH);
        mc.clear();

        if (result) {
            return new APIResponse();
        }

        return new APIResponse(ConfigSSO.RE_CODE_ROLE_ERROR, ConfigSSO.RE_MSG_ROLE_ERROR);
    }

    @RequestMapping("list")
    @ResponseBody
    @RequiresPermissions("/admin/permission/list")
    public APIResponse permissionList(@RequestBody Permission permission1) {

        int skip = (permission1.getPage()-1)*permission1.getPageSize();

        List<Permission> list;
        int total = 0;
        if (StringUtil.isNullString(permission1.getSearchValue())) {
            list = service.list(
                    new QueryWrapper<Permission>().eq("is_delete", 0).orderByDesc("sort!=0")
                            .orderByAsc("sort").orderByDesc("gmt_create")
                            .last("limit "+skip+","+permission1.getPageSize())
            );
            total = service.count(
                    new QueryWrapper<Permission>().eq("is_delete", 0)
            );
        } else {
            list = service.list(
                    new QueryWrapper<Permission>().eq("is_delete", 0).like(permission1.getSelectValue(), permission1.getSearchValue()).orderByDesc("sort!=0")
                            .orderByAsc("sort").orderByDesc("gmt_create").last("limit "+skip+","+permission1.getPageSize())
            );
            total = service.count(
                    new QueryWrapper<Permission>().eq("is_delete", 0).like(permission1.getSelectValue(), permission1.getSearchValue())
            );
        }

        Map<String, Object> data = new HashMap<>();
        data.put("dataList", list);
        data.put("total", total);

        return new APIResponse(data);
    }

    @RequestMapping("get")
    @ResponseBody
//    @RequiresPermissions("/admin/permission/get")
    public APIResponse getPermissionById(@RequestBody Permission permission1) {
        Permission permission = service.getById(permission1.getId());

        Map<String, Object> data = new HashMap<>();
        data.put("permission", permission);

        return new APIResponse(data);
    }

    @RequestMapping("list/appId")
    @ResponseBody
//    @RequiresPermissions("/admin/permission/list/appId")
    public APIResponse getPermissionByAppId(@RequestBody Permission permission1) {

        List<Permission> permissions;

        if (permission1.getIsMenu() == 0 || permission1.getIsMenu() == 1){
            //是否为菜单
            permissions = service.list(new QueryWrapper<Permission>()
                    .eq("app_id", permission1.getId()).eq("is_menu",permission1.getIsMenu()).eq("is_delete",0));
        }else {
            //所有
            permissions = service.list(new QueryWrapper<Permission>()
                    .eq("app_id", permission1.getId()).eq("is_delete",0));
        }



        Map<String, Object> data = new HashMap<>();
        data.put("dataList", permissions);

        return new APIResponse(data);
    }

}
