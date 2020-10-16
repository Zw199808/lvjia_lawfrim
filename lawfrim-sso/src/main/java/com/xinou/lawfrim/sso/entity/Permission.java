package com.xinou.lawfrim.sso.entity;


import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.Serializable;
import java.sql.Timestamp;

/**
 * All rights Reserved, Designed By 信鸥科技
 * project : Innovation
 * Created by zhangbo on 17/10/18.
 * 注意：本内容仅限于信鸥科技有限公司内部传阅，禁止外泄以及用于其他的商业目的
 * Description:
 * 权限表
 */
@TableName("sys_permission")
@Data
@EqualsAndHashCode(callSuper = false)
public class Permission extends BaseEntitySSO implements Serializable {

    @TableId(type = IdType.AUTO)
    private int id;                         //主键id
    private int appId;                      //应用id
    private int parentId;                   //父id
    private String name;                   //名称
    private String icon;                    //头像
    private String url;                     //权限url
    private int sort;                       //排序
    private int isMenu;                     //是否为菜单
    private int isEnable;                   //是否开启
    private Timestamp gmtCreate;           //创建时间
    private Timestamp gmtModified;          //修改时间
    private int isDelete;                   //是否删除

}
