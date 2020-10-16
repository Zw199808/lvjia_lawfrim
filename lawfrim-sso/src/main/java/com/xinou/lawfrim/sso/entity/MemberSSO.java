package com.xinou.lawfrim.sso.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

import java.io.Serializable;
import java.sql.Timestamp;

/**
 * All rights Reserved, Designed By 信鸥科技
 * @author ZhuoYang_Shen
 * @date 2019/10/12
 * 注意：本内容仅限于信鸥科技有限公司内部传阅，禁止外泄以及用于其他的商业目的
 * Description: 用户
 */
@Data
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = false)
public class MemberSSO implements Serializable {


    private Integer id;
    /** 姓名 */
    private String name;
    /** 部门id */
    private Integer departmentId;
    /** sys_user表id */
    private Integer userId;
    /** 联系方式 */
    private String contract;
    /** 职务 */
    private String job;
    /** 备注 */
    private String remark;
    /** 单位角色 */
    private String departmentRole;
    /** 驾驶员 */
    private String driver;
    /** 创建时间 */
    private Timestamp createTime;

    /** 创建时间 */
    private Timestamp gmtCreate;
    /** 修改时间 */
    private Timestamp gmtModified;
    /** 删除字段 0：未删除 1：已删除 */
    private Integer isDelete;
    /** 永久删除字段 0：未删除 1：已删除 */
    private Integer permanentDelete;
}