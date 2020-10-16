package com.xinou.lawfrim.common.aspect;

/**
 * All rights Reserved, Designed By 信鸥科技
 * Created by xiao_XX on 2019/10/22.
 * 注意：本内容仅限于信鸥科技有限公司内部传阅，禁止外泄以及用于其他的商业目的
 * Description:
 */
public class LogTypeEnum {

    public static final String CAR_MODULE = "车辆管理";
    public static final String CAR_MODULE_ADD = "车辆添加";
    public static final String CAR_MODULE_DELETE = "车辆删除";
    public static final String CAR_MODULE_THOROUGH_DELETE = "车辆彻底删除";
    public static final String CAR_MODULE_REVERT = "车辆回滚删除";
    public static final String CAR_MODULE_UPDATE = "车辆修改";
    public static final String CAR_MODULE_SELECT = "车辆查询";
    public static final String CAR_MODULE_EXCEL_IN = "车辆导入";
    public static final String CAR_MODULE_EXCEL_OUT = "车辆导出";


    public static final String DISPATCH_CAR_MODULE = "派车单管理";
    public static final String DISPATCH_CAR_MODULE_ADD = "申请用车";
    public static final String DISPATCH_CAR_MODULE_RECALL = "撤回作废";
    public static final String DISPATCH_CAR_MODULE_AUDIT_FIRST = "初审";
    public static final String DISPATCH_CAR_MODULE_AUDIT_SECOND = "终审";
    public static final String DISPATCH_CAR_MODULE_SEND = "发车";
    public static final String DISPATCH_CAR_MODULE_CALL = "收车";

    public static final String ISSUE_MODULE = "公告管理";
    public static final String ISSUE_LIMIT_MODULE_ADD = "添加限行公告";
    public static final String ISSUE_ADVISE_MODULE_ADD = "添加管理公告";
    public static final String ISSUE_LIMIT_MODULE_UPDATE = "修改限行公告";
    public static final String ISSUE_ADVISE_MODULE_UPDATE = "修改管理公告";
    public static final String ISSUE_LIMIT_MODULE_DELETE = "删除限行公告";
    public static final String ISSUE_ADVISE_MODULE_DELETE = "删除管理公告";

    public static final String DEPLOY_CAR_MODULE = "调车管理";
    public static final String DEPLOY_CAR_MODULE_ADD = "申请调车";
    public static final String DEPLOY_CAR_MODULE_AUDIT = "调车审核";
    public static final String DEPLOY_CAR_MODULE_AUDIT_OUT = "调出审核";
    public static final String DEPLOY_CAR_MODULE_AUDIT_IN = "调入审核";

    public static final String RULES_MODULE = "规则管理";
    public static final String RULES_ADD = "添加过夜/特殊规则";
    public static final String RULES_SYSTEM_ADD = "添加规则";
    public static final String VIOLATION_RULES_HANDLE = "违规处理";
    public static final String VIOLATION_RULES_AUDIT = "违规审核";
    public static final String SPECIAL_RULES_USE = "特殊规则使用/取消使用";

    public static final String USER_ID_CARD_MODULE = "身份证管理";
    public static final String USER_ID_CARD_ADD = "身份证信息添加";
    public static final String USER_ID_CARD_DELETE = "身份证信息删除";
    public static final String USER_ID_CARD_UPDATE = "身份证信息修改";

    public static final String GROUP_MODULE = "群组管理";
    public static final String GROUP_MODULE_ADD = "群组添加";
    public static final String GROUP_MODULE_DELETE = "群组删除";
    public static final String GROUP_MODULE_UPDATE = "群组修改";
    public static final String GROUP_MODULE_SELECT = "群组查询";

    public static final String IC_MODULE = "IC卡管理";
    public static final String IC_MODULE_ADD = "IC卡添加";
    public static final String IC_MODULE_DELETE = "IC卡删除";
    public static final String IC_MODULE_UPDATE = "IC卡修改";
    public static final String IC_MODULE_SELECT = "IC卡查询";
    public static final String IC_MODULE_EXCEL_IN = "IC卡导入";
    public static final String IC_MODULE_EXCEL_OUT = "IC卡导出";

    public static final String LOG_MODULE = "日志管理";
    public static final String LOG_MODULE_SELECT = "日志查看";
    public static final String LOG_MODULE_EXCEL_LOGIN = "登录日志导出";
    public static final String LOG_MODULE_EXCEL_SYSTEM = "系统日志导出";
    public static final String LOG_MODULE_EXCEL_EXCEPTION = "异常日志导出";
    public static final String LOG_MODULE_EXCEL_MOBILE = "移动日志导出";

    public static final String INSTRUCT_MODULE = "指令发布管理";
    public static final String INSTRUCT_MODULE_ADD = "指令发布添加";
    public static final String INSTRUCT_MODULE_SELECT = "指令发布查询";

    public static final String DEPARTMENT_MODULE = "单位管理";
    public static final String DEPARTMENT_MODULE_ADD = "单位添加";
    public static final String DEPARTMENT_MODULE_DELETE = "单位删除";
    public static final String DEPARTMENT_MODULE_UPDATE = "单位修改";
    public static final String DEPARTMENT_MODULE_EXCEL = "单位上传excel添加";
    public static final String DEPARTMENT_MODULE_EXCEL_DOWNLOAD = "单位下载excel信息";
    public static final String DEPARTMENT_MODULE_SELECT = "单位查询";

    public static final String MEMBER_MODULE = "用户管理";
    public static final String MEMBER_MODULE_ADD = "用户添加";
    public static final String MEMBER_MODULE_DELETE = "用户删除";
    public static final String MEMBER_MODULE_DELETE_ROLLBACK = "用户删除撤销";
    public static final String MEMBER_MODULE_UPDATE = "用户修改";
    public static final String MEMBER_MODULE_EXCEL = "用户上传excel添加";
    public static final String MEMBER_MODULE_EXCEL_DOWNLOAD = "用户下载excel信息";
    public static final String MEMBER_MODULE_SELECT = "用户查询";

    public static final String LOGIN_MODULE = "登录管理";
    public static final String LOGIN_MODULE_SYSTEM = "系统登录";
    public static final String LOGIN_MODULE_APP = "APP登录";

    public static final String APP_MODULE = "应用管理";
    public static final String APP_MODULE_ADD = "应用添加";
    public static final String APP_MODULE_UPDATE = "应用修改";
    public static final String APP_MODULE_DELETE = "应用删除";

    public static final String ROLE_MODULE = "角色管理";
    public static final String ROLE_MODULE_ADD = "角色添加";
    public static final String ROLE_MODULE_UPDATE = "角色修改";
    public static final String ROLE_MODULE_DELETE = "角色删除";

    public static final String PERMISSION_MODULE = "权限管理";
    public static final String PERMISSION_MODULE_ADD = "权限管理";
    public static final String PERMISSION_MODULE_UPDATE = "权限管理";
    public static final String PERMISSION_MODULE_DELETE = "权限管理";

    public static final String USER_MODULE = "用户管理";
    public static final String USER_MODULE_ADD = "用户管理";
    public static final String USER_MODULE_UPDATE = "用户管理";
    public static final String USER_MODULE_DELETE = "用户管理";
    public static final String USER_MODULE_UPDATE_PASSWORD = "用户管理";
}
