package com.xinou.lawfrim.common.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.io.Serializable;
import java.sql.Timestamp;

/**
 * All rights Reserved, Designed By 信鸥科技
 *
 * @author xiao_XX
 * @date 2019/10/22
 * 注意：本内容仅限于信鸥科技有限公司内部传阅，禁止外泄以及用于其他的商业目的
 * Description:
 */
@TableName("sys_msg_log")
@Data
public class LogEntity implements Serializable {
    @TableId(type = IdType.AUTO)
    private int id;
    /**
     * 用户id
     */
    private int userId;
    /**
     * 操作模块
     **/
    private String module;
    /**
     * 操作方法
     **/
    private String methods;
    /**
     * 操作时间
     **/
    private Timestamp operationTime;
    /**
     * 反应时间
     **/
    private String responseTime;
    /**
     * 1- 登录管理系统日志
     * 2- 登录移动端日志
     * 3 -系统日志
     * 4- 服务器通信异常日志
     * 5- 移动服务日志
     * 6- 移动异常日志
     */
    private Integer type;
    /**
     * 操作描述
     **/
    private String description;
    private Timestamp gmtCreate;
    private Timestamp gmtModified;
    private int isDelete;
    /**
     * 异常信息
     */
    private String exceptionMessage;
    /**
     * 异常所在行数
     */
    private String exceptionLine;
    /**
     * 异常所在类
     */
    private String exceptionClass;
    /**
     * 异常所在方法
     */
    private String exceptionMethod;
    /**
     * 异常参数
     */
    private String exceptionArgs;
    /**
     * 账号
     **/
    @TableField(exist = false)
    private String account;
    /**
     * 真是姓名
     **/
    @TableField(exist = false)
    private String realName;
    /**
     * 操作时间
     **/
    @TableField(exist = false)
    private String operationTimeStr;


}
