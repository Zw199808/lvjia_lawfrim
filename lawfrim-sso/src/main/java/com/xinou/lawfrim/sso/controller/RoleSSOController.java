package com.xinou.lawfrim.sso.controller;



import com.xinou.lawfrim.common.util.APIResponse;
import org.springframework.web.bind.annotation.RequestParam;

/**
 * Created by zhangbo on 2017/10/23.
 * 角色管理
 */
public interface RoleSSOController {

    /**
     * 添加角色
     * @param name 名称
     * @param desc 描述
     * @param permissionIds  权限id数组
     * @param appId 系统id
     * @param grade_id 等级id
     * @param grade_type 等级类型
     * @param is_grade 是否为等级管理员
     */
    APIResponse addRole(String name, String desc, int[] permissionIds, int appId, int grade_id, int grade_type, int is_grade);


    /**
     * 删除角色
     *
     * @param id id
     */
    APIResponse delRole(int id);

    /**
     * 修改角色
     *
     * @param id    id
     * @param name     名称
     * @param desc     编码
     * @param permissionIds  权限id数组
     * @param isEnable 是否可用  0:可用  1:不可用
     * @param sort     排序
     */
    APIResponse updateRole(int id, String name, String desc, Integer[] permissionIds, int isEnable, int sort);


    /**
     * 角色列表
     *
     * @param page        当前页数
     * @param pageSize    显示个数
     * @param selectValue 查询条件
     * @param searchValue 搜索值
     */
    APIResponse roleList(@RequestParam(required = false, defaultValue = "1") int page,
                         @RequestParam(required = false, defaultValue = "10") int pageSize,
                         @RequestParam(required = false, defaultValue = "") String selectValue,
                         @RequestParam(required = false, defaultValue = "") String searchValue);

    /**
     * 根据id获取详情
     *
     * @param id id
     */
    APIResponse getRoleById(int id);

    /**
     * 根据appId获取角色列表
     */
    APIResponse getRoleByAppId(int id);
}
