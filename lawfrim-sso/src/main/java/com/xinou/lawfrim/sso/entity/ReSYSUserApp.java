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

@TableName("sys_re_user_app")
@Data
@Accessors(chain = true)
public class ReSYSUserApp implements Serializable{

    @TableId(type = IdType.AUTO)
    private int id;                         //主键id
    private int userId;                     //用户id
    private int appId;                     //appId
    private Timestamp gmtCreate;           //创建时间
    private Timestamp gmtModified;          //修改时间
    private int isDelete;                   //是否删除

}