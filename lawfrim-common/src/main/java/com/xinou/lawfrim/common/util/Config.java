package com.xinou.lawfrim.common.util;

/**
 * @author: XGLLHZ
 * @date: 2020/5/7 下午9:50
 * @description:
 */
public class Config {

    public static final String BASE_URL = "http://police.liufubingwan.cn";
    /**
     * xinouservice 相关
     */
    public static final String SERVICE_URL = BASE_URL + "/xcloud-api/service/";
    public static final String SERVICE_IMG_URL = SERVICE_URL + "file/get/";   //获取图片原图接口
    public static final String SERVICE_TH_IMG_URL = SERVICE_URL + "file/getThumbnail/";   //获取图片缩略图接口
    public static final String LOG_PATH = "/root/project/xinou-upload/";   //生成的用户头像路径

    // 删除字段
    public static final int IS_DELETE = 1;
    public static final int NOT_DELETE = 0;

    // 车辆状态
    public static final String CAR_STATUS_DISABLED = "使用中";
    public static final String CAR_STATUS_FREE = "空闲";

    //数据不存在
    public static final String RE_DATA_NOT_EXIST_ERROR_CODE = "3301";
    public static final String RE_DATA_NOT_EXIST_ERROR_MSG = "数据不存在！";

    public static final String RE_CODE_DATABASE_ERROR = "100";
    public static final String RE_MSG_DATABASE_ERROR = "网络连接异常";

    public static final String DATA_LIST = "dataList";
    public static final String DATA_INFO = "dataInfo";
    public static final String TOTAL = "total";
    public static final int LONGHU_APP = 1;

    public static final String RE_SUCCESS_CODE = "200";
    public static final String RE_SUCCESS_MSG = "请求成功！";

    public static final String RE_ERROR_CODE = "201";
    public static final String RE_ERROR_MSG = "请求失败！";

    public static final String RE_ACCOUNT_EXIST_CODE = "3301";
    public static final String RE_ACCOUNT_EXIST_MSG = "账号已存在！";

    public static final String RE_ACCOUNT_NOT_EXIST_CODE = "3302";
    public static final String RE_ACCOUNT_NOT_EXIST_MSG = "账号不存在！";

    public static final String RE_PASSWORD_ERROR_CODE = "3303";
    public static final String RE_PASSWORD_ERROR_MSG = "密码错误！";

    public static final String RE_LOGIN_ERROR_CODE = "3304";
    public static final String RE_LOGIN_ERROR_MSG = "普通用户不可登录管理系统！";

    public static final String RE_LOGIN_OVERDUE_CODE = "3305";
    public static final String RE_LOGIN_OVERDUE_MSG = "登录过期，请重新登录！";

    public static final String RE_DATA_NOT_EXIST_CODE = "3306";
    public static final String RE_DATA_NOT_EXIST_MSG = "数据不存在！";

    public static final String RES_LOGIN_ERROR_CODE = "3307";
    public static final String RES_LOGIN_ERROR_MSG = "管理员不可登录此应用！";

    public static final String RE_CURRENT_RECORD_CODE = "3308";
    public static final String RE_CURRENT_RECORD_MSG = "仅可删除当天记录！";

    public static final String RE_OLD_PASS_ERROR_CODE = "3309";
    public static final String RE_OLD_PASS_ERROR_MSG = "旧密码错误！";


    //申请用车
    public static final Integer RE_CAR_NOT_USE = 0;//空闲
    public static final Integer RE_CAR_IS_USE = 1;//使用中
    public static final String RE_ADD_CODE = "4002";
    public static final String RE_ADD_MSG = "添加数据失败！";
    public static final String RE_UPDATE_CODE = "4003";
    public static final String RE_UPDATE_MSG = "修改数据失败！";
    public static final String RE_DELETE_CODE = "4004";
    public static final String RE_DELETE_MSG = "删除失败";
    public static final String RE_REVERT_CODE = "4005";
    public static final String RE_REVERT_MSG = "恢复失败";
    public static final String RE_APPLY_CAR_CODE = "4006";
    public static final String RE_APPLY_CAR_MSG = "当前车辆已被使用";
    public static final String RE_STATISTIC_TIME_ISNULL_CODE = "4007";
    public static final String RE_STATISTIC_TIME_ISNULL_MSG = "请选择开始时间和结束时间";

    public static final Integer DISPATCH_CAR = 1;//记录表中-派车单

    public static final Integer DISPATCH_CAR_FIRST = 1; //1申请用车
    public static final Integer DISPATCH_CAR_TWO = 2; //2审核同意
    public static final Integer DISPATCH_CAR_THREE = 3; //3审核拒绝
    public static final Integer DISPATCH_CAR_FOUR = 4; //4终审同意
    public static final Integer DISPATCH_CAR_FIVE = 5; //5终审拒绝
    public static final Integer DISPATCH_CAR_SIX = 6; //6撤回作废
    public static final Integer DISPATCH_CAR_SEVEN = 7; //7拒绝发车
    public static final Integer DISPATCH_CAR_EIGHT = 8; //8同意发车
    public static final Integer DISPATCH_CAR_NINE = 9; //9拒绝收车
    public static final Integer DISPATCH_CAR_TEN = 10; //10同意收车

    public static final Integer DISPATCH_CAR_SEND_SECOND = 2; //派车记录-发车
    public static final Integer DISPATCH_CAR_SEND_NINE = 9; //派车记录-收车
    public static final Integer DISPATCH_CAR_SEND_ELEVEN = 11; //派车记录-撤回作废
    public static final Integer DISPATCH_CAR_AUDIT_SEVEN = 7; //派车记录-初审
    public static final Integer DISPATCH_CAR_AUDIT_EIGHT = 8; //派车记录-终审


    //调车相关
    public static final Integer STATE_APPLY_DEPLOY_CAR = 1; // 申请调车
    public static final Integer STATE_APPLY_REFUSE = 2; // 拒绝调车
    public static final Integer STATE_APPLY_Agree = 3; // 同意调车
    public static final Integer STATE_OUT_REFUSE = 4; // 调出拒绝
    public static final Integer STATE_OUT_Agree = 5; // 调出同意
    public static final Integer STATE_IN_REFUSE = 6; // 调入拒绝
    public static final Integer STATE_IN_Agree = 7; // 调入同意

    public static final String RE_NOT_DEPLOY_CAR_CODE = "5001";
    public static final String RE_NOT_DEPLOY_CAR_MSG = "提交申请调车失败！";
    public static final String RE_NOT_AUDIT_DEPLOY_CAR_CODE = "5002";
    public static final String RE_NOT_AUDIT_DEPLOY_CAR_MSG = "审核调车失败！";
    public static final String RE_NOT_UPDATE_DEPLOY_CAR_CODE = "5003";
    public static final String RE_NOT_UPDATE_DEPLOY_CAR_MSG = "修改申请调车失败！";
    public static final String RE_NOT_DISPATCH_DEPLOY_CAR_CODE = "5004";
    public static final String RE_NOT_DISPATCH_DEPLOY_CAR_MSG = "车辆使用中！";

    //member相关
    public static final String RE_CODE_USER_EXIST = "3002";          //用户已存在
    public static final String RE_MSG_USER_EXIST = "用户已存在";
    /**
     * 讲师角色ID
     */
    public static final int MEMBER_ROLE_ID = 2;
    /**
     * 后台应用ID
     */
    public static final int ADMIN_APP_ID = 1;

    //id数据不能为空或0
    public static final String ID_CANNOT_BE_EMPTY_OR_ZERO_ERROR_CODE = "104";
    public static final String ID_CANNOT_BE_EMPTY_OR_ZERO_ERROR_MESSAGE = "id数据不能为空或0！";

    //IC卡
    //数据已存在
    public static final String DATA_ALREADY_EXISTS_CODE = "101";
    public static final String DATA_ALREADY_EXISTS_MSG = "数据已存在！";
    //数据不存在
    public static final String DATA_DOES_NOT_EXISTS_CODE = "102";
    public static final String DATA_DOES_NOT_EXISTS_MSG = "数据不存在！";

    public static final String RE_CODE_ERROR = "100";
    public static final String RE_MSG_ERROR = "error";

    //规则
    public static final String RE_NOT_USE_RULE_CODE = "5005";
    public static final String RE_NOT_USE_RULE_MSG = "该时间段已有规则！";
    public static final String RE_NOT_USE_SPECIAL_RULE_CODE = "5006";
    public static final String RE_NOT_USE_SPECIAL_RULE_MSG = "该车未使用特殊规则！";
    public static final String RE_NOT_CAR_CODE = "5007";
    public static final String RE_NOT_CAR_MSG = "当前车辆不存在！";


//            public static final String EXCEL_PATH = "D:\\ideaProject\\";
    public static final String EXCEL_PATH = "/root/xinou-upload/";

}
