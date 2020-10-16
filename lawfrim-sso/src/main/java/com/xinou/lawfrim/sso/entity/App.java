package com.xinou.lawfrim.sso.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import lombok.experimental.Accessors;

import java.io.Serializable;
import java.sql.Timestamp;

/**
 * All rights Reserved, Designed By 信鸥科技
 * project : Innovation
 * Created by zhangbo on 17/10/23.
 * 注意：本内容仅限于信鸥科技有限公司内部传阅，禁止外泄以及用于其他的商业目的
 * Description:
 * 系统应用表
 */
@Data
@Accessors(chain = true)
@TableName("sys_app")
public class App extends BaseEntitySSO implements Serializable {

    @TableId(type = IdType.AUTO)
    private int id;
    private String name; // 名称
    private String code; // 编码
    private int sort; // 排序
    private int isEnable;  // 是否可用
    private Timestamp gmtCreate;        //创建时间
    @TableField(exist = false)
    private String gmtCreateStr;       //创建时间字符串
    private Timestamp gmtModified;      //修改时间
    private int isDelete;               //删除字段

}
