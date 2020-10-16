package com.xinou.lawfrim.sso.entity;


import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import lombok.experimental.Accessors;

import java.io.Serializable;
import java.sql.Timestamp;

/**
 * Created by xiao_XX on 2017/10/25.
 * 后台人员与 系统关系表
 */
@Data
@Accessors(chain = true)
@TableName("sys_re_role_permission")
public class ReRolePermission implements Serializable {

    @TableId(type = IdType.AUTO)
    private int id;                         //主键id
    private int roleId;                     //用户id
    private int permissionId;                     //appId
    private Timestamp gmtCreate;           //创建时间
    private Timestamp gmtModified;          //修改时间
    private int isDelete;                   //是否删除

}