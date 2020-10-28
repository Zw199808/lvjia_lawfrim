package com.xinou.lawfrim.common.util;

import com.qiniu.storage.Region;

/**
 * @author: XGLLHZ
 * @date: 2020/5/7 下午9:50
 * @description:
 */
public class Config {

    public static final String BASE_URL = "http://qig05me3p.hn-bkt.clouddn.com/";
    /**
     * xinouservice 相关
     */
    public static final String SERVICE_URL = BASE_URL + "/xcloud-api/service/";
    public static final String SERVICE_IMG_URL = SERVICE_URL + "file/get/";   //获取图片原图接口
    public static final String SERVICE_TH_IMG_URL = SERVICE_URL + "file/getThumbnail/";   //获取图片缩略图接口
    public static final String LOG_PATH = "/root/project/xinou-upload/";   //生成的用户头像路径

    /**
     * 文件上传相关
     */
    public static final String ACCESSKEY_QN = "S3jdck06nn4poNaKyU6vE5ob8bOkuxPx6LLP6TVL";   //七牛秘钥
    public static final String SECRETKEY_QN = "TcQP6mQnyjx9xvnFYh4tD8u6GX0LKu-175MZQWXQ";   //七牛私钥
    public static final String BUCKET_NAME_QN = "lvjia"; //七牛空间名
    public static final Region BUCKET_ZONE_QN = Region.region2(); //七牛空间地区
    // 文件上传
    public static final String SERVICE_UPLOAD_URL = SERVICE_URL + "file/upload";

    // 删除字段
    public static final int IS_DELETE = 1;
    public static final int NOT_DELETE = 0;


    //数据不存在
    public static final String RE_DATA_NOT_EXIST_ERROR_CODE = "3301";
    public static final String RE_DATA_NOT_EXIST_ERROR_MSG = "数据不存在！";

    public static final String RE_CODE_DATABASE_ERROR = "100";
    public static final String RE_MSG_DATABASE_ERROR = "网络连接异常";

    public static final String RE_CODE_PARAM_ERROR = "102";
    public static final String RE_MSG_PARAM_ERROR = "参数错误";

    public static final String RE_CODE_NO_HAVE_TOKEN = "1003";
    public static final String RE_MSG_NO_HAVE_TOKEN = "请携带token";

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


    //基础
    public static final String RE_ADD_CODE = "4001";
    public static final String RE_ADD_MSG = "添加数据失败！";

    public static final String RE_OLD_PASSWORD_ERROR_CODE = "4002";
    public static final String RE_OLD_PASSWORD_ERROR_MSG = "旧密码输入错误！";




            public static final String EXCEL_PATH = "D:\\ideaProject\\";
//    public static final String EXCEL_PATH = "/root/xinou-upload/";

}
