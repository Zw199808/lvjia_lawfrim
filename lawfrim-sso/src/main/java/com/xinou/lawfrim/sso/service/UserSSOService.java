package com.xinou.lawfrim.sso.service;


import com.baomidou.mybatisplus.extension.service.IService;
import com.xinou.lawfrim.common.util.APIResponse;
import com.xinou.lawfrim.sso.entity.SYSUser;

/**
 * Created by zhangbo on 2017/10/24.
 * 系统用户
 */
public interface UserSSOService extends IService<SYSUser> {

    /**
     * 添加系统用户
     * @param account 账号
     * @param realName 真实姓名
     * @param mobile 手机号
     * @param roleIds 角色列表
     * @param appIds appId
     */
    APIResponse addUser(String account, String realName, String mobile, Integer[] roleIds, Integer[] appIds);


    APIResponse delUser(int id);

    /**
     * 修改系统用户
     * @param id id
     * @param realName  真实姓名
     * @param mobile  手机号
     * @param password 密码
     * @param roleIds 角色列表
     * @param isEnable 是否可用
     */
    APIResponse updateUser(int id, String realName, String password, String mobile, Integer[] roleIds, int isEnable);


    /**
     * 系统用户列表
     */
    APIResponse userList(int page, int pageSize, String selectValue, String searchValue);

    /**
     * 获取全部系统用户列表
     * @param sysUser
     * @return
     */
    APIResponse getAllList(SYSUser sysUser);

    /**
     * 通过角色获取用户列表
     * @param roleId
     * @return
     */
    APIResponse listByRole(Integer roleId);
}
