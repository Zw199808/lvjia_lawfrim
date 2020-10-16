package com.xinou.lawfrim.sso.service;


import com.baomidou.mybatisplus.extension.service.IService;
import com.xinou.lawfrim.common.util.APIResponse;
import com.xinou.lawfrim.sso.entity.Role;


/**
 * Created by zhangbo on 2017/10/25.
 * 角色
 */
public interface RoleSSOService extends IService<Role> {


    /**
     * 添加角色
     * @param name 名称
     * @param desc 描述
     * @param permissionIds  权限id数组
     * @param appId  appId
     * @param grade_id 等级id
     * @param grade_type 等级类型
     * @param is_grade 是否为等级管理员
     */
    APIResponse addRole(String name, String desc, int[] permissionIds, int appId, int grade_id, int grade_type, int is_grade);


    APIResponse delRole(int id);

    /**
     * 更新角色
     */
    APIResponse updateRole(int id, String name, String desc, int[] permissionIds, int isEnable, int sort);


    APIResponse roleList(int page, int pageSize, String selectValue, String searchValue);

}
