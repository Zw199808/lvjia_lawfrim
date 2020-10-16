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
 * All rights Reserved, Designed By 信鸥科技
 * project : Innovation
 * Created by zhangbo on 17/10/18.
 * 注意：本内容仅限于信鸥科技有限公司内部传阅，禁止外泄以及用于其他的商业目的
 * Description:
 * 角色表
 */
@TableName("sys_role")
@Data
@Accessors(chain = true)
public class Role extends BaseEntitySSO implements Serializable{

    @TableId(type = IdType.AUTO)
    private int id;                         //主键id
    private int appId;                      //父id
    private String name;                    //名称
    private int sort;                       //排序
    private String description;             //描述
    private int isEnable;                   //是否开启
    private Timestamp gmtCreate;           //创建时间
    private Timestamp gmtModified;          //修改时间
    private int isDelete;                   //是否删除
    private int gradeId;
    private int gradeType;
    private int isGrade;
    private int type;
    @TableField(exist = false)
    private List<Map<String,Object>> permissions;             //权限列表

    @TableField(exist = false)
    private int[] permissionIds;

    @TableField(exist = false)
    private String desc;

}
