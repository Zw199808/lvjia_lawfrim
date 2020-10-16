package com.xinou.lawfrim.sso.config;

/**
 * @Author: X_xx
 * @Date: 2020/3/11 下午4:22
 * @Description:
 **/
public class ConfigSSO {


    /**
     * shiro 缓存类反射地址
     */
    public static final String SHIRO_CACHE_PATH = "com.xinou.lawfrim.sso.shiro.SmartShiro.authorizationCache";

    public static final String DATA_LIST = "dataList";   //返回数据列表R

    // 删除字段
    public static final int IS_DELETE = 1;
    public static final int NOT_DELETE = 0;

    public static final String RE_CODE_ERROR = "100"; //用户相关错误返回码
    public static final String RE_CODE_USER_ERROR = "1001"; //用户相关错误返回码
    public static final String RE_CODE_USER_HAD = "1002"; //账号已存在
    public static final String RE_CODE_APP_NAME_HAD = "1003" ; //应用名已存在
    public static final String RE_CODE_APP_CODE_HAD = "1004" ; //应用名已存在
    public static final String RE_CODE_PERMISSION_NAME_HAD = "1005" ; //权限名已存在
    public static final String RE_CODE_ROLE_NAME_HAD = "1006" ; //角色名已存在
    public static final String RE_CODE_USER_NOT_ROLE = "1007"; //用户角色不能为空
    public static final String RE_CODE_USER_NOT_APP = "1008"; //用户应用不能为空

    public static final String RE_CODE_PERMISSION_ERROR = "9000";//权限不足
    public static final String RE_CODE_EXCEPTION_ERROR = "9900";//抛异常
    public static final String RE_CODE_ISSUBJECT_ERROR = "1111";


    public static final String RE_MSG_ROLE_ERROR = "权限管理操作失败";
    public static final String RE_MSG_APP_NAME_HAD = "应用名已存在";
    public static final String RE_MSG_APP_CODE_HAD = "应用编码已存在";
    public static final String RE_MSG_USER_HAD = "账号已存在";
    public static final String RE_MSG_PERMISSION_NAME_HAD = "权限名已存在";
    public static final String RE_MSG_ROLE_NAME_HAD = "角色名已存在";
    public static final String RE_MSG_USER_NOT_ROLE = "用户角色不能为空";
    public static final String RE_MSG_USER_NOT_APP = "用户应用不能为空";

    public static final String RE_MSG_PERMISSION_ERROR = "您的权限不足";//权限不足


    //管理员相关
    public static final String RE_MSG_PWD_NOSAME = "旧密码与当前密码不相符";

    public static final String RE_CODE_PWD_NOSAME = "1009"; //旧密码与当前密码不相符

    public static final String RE_CODE_ROLE_ERROR = "2001";//权限相关错误
    public static final String RE_MSG_ERROR = "error";
    public static final String RE_MSG_PARAMETER_ERROR = "参数错误";
    public static final String RE_MSG_ERROR_UPDATE = "修改失败";


    // icCard
    public static final String RE_CODE_IC_CARD_NOT_EXIST = "4001";
    public static final String RE_MSG_IC_CARD_NOT_EXIST = "IC卡号不存在";
    public static final String RE_CODE_IC_CARD_OVERDUE = "4002";
    public static final String RE_MSG_IC_CARD_OVERDUE = "IC卡已过期";
    public static final String RE_CODE_IC_CARD_USER_NOT_EXIST = "4003";
    public static final String RE_MSG_IC_CARD_USER_NOT_EXIST = "IC卡用户不存在";



}
