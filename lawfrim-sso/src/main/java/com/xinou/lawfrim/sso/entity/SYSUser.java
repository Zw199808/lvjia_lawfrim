package com.xinou.lawfrim.sso.entity;


import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import lombok.experimental.Accessors;

import java.io.Serializable;
import java.sql.Timestamp;
import java.util.List;
import java.util.Map;

/**
 * Created by xiao_XX on 2017/10/17.
 * 后台管理系统人员
 */

@Data
@Accessors(chain = true)
@TableName("sys_user")
public class SYSUser extends BaseEntitySSO implements Serializable{

    @TableId(type = IdType.AUTO)
    private int id;                         //主键id
    private String account;                 //账户
    private String password;                //密码
    private String realName;                //真实姓名
    private String mobile;                  //电话号
    private String lastLoginIp;             //最后登录ip
    private Timestamp lastLoginTime;        //最后登录时间
    private int loginCount;                 //登录次数
    private Timestamp gmtCreate;           //创建时间
    private Timestamp gmtModified;          //修改时间
    private int isEnable;                   //是否启用
    private int isDelete;                   //是否删除
    @TableField(exist = false)
    private List<String> roles;             //角色列表
    @TableField(exist = false)
    private List<String> apps;             //系统列表
    @TableField(exist = false)
    private List<Map<String,Object>> roleAppList;  // 角色 系统关联列表
    @TableField(exist = false)
    private Integer teacherId;
    @TableField(exist = false)
    private Integer roleId;
    @TableField(exist = false)
    private int skip;
    @TableField(exist = false)
    private int size;

    @TableField(exist = false)
    private Integer[] roleIds;

    @TableField(exist = false)
    private Integer[] appIds;

    @TableField(exist = false)
    private String newPwd;

    @TableField(exist = false)
    private String oldPwd;

}