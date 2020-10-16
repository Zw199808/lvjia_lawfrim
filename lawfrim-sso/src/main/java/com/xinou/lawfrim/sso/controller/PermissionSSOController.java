package com.xinou.lawfrim.sso.controller;


import com.xinou.lawfrim.common.util.APIResponse;
import com.xinou.lawfrim.sso.entity.Permission;

/**
 * Created by zhangbo on 2017/10/23.
 * 权限管理
 */
public interface PermissionSSOController {

    /**
     * 添加权限
     * @param permission
     */
    APIResponse addPermission(Permission permission);


    /**
     * 删除权限
     * @param permission
     */
    APIResponse delPermission(Permission permission);

    /**
     * 修改权限
     * @param permission
     */
    APIResponse updatePermission(Permission permission);


    /**
     * 权限列表
     * @param permission
     */
    APIResponse permissionList(Permission permission);


    /**
     * 权限信息
     * @param permission
     */
    APIResponse getPermissionById(Permission permission);


    /**
     * 根据appId获取角色列表
     * @param permission
     */
    APIResponse getPermissionByAppId(Permission permission);


}
