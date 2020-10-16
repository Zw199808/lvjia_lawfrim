package com.xinou.lawfrim.sso.entity;

import java.sql.Timestamp;

/**
 * All rights Reserved, Designed By 信鸥科技
 * @author YuJie_Jin
 * @date 2019/10/12
 * 注意：本内容仅限于信鸥科技有限公司内部传阅，禁止外泄以及用于其他的商业目的
 * Description: IC卡
 */
public class ICCardSSO {

    /** id主键 */
    private Integer id;
    /** ic卡号 */
    private String icNo;
    /** 用户id */
    private Integer memberId;
    /** 开始时间 */
    private Timestamp startTime;
    /** 结束时间 */
    private Timestamp endTime;
    /** 创建时间 */
    private Timestamp gmtCreate;
    /** 修改时间 */
    private Timestamp gmtModified;
    /** 是否删除 */
    private Integer isDelete;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getIcNo() {
        return icNo;
    }

    public void setIcNo(String icNo) {
        this.icNo = icNo;
    }

    public Integer getMemberId() {
        return memberId;
    }

    public void setMemberId(Integer memberId) {
        this.memberId = memberId;
    }

    public Timestamp getStartTime() {
        return startTime;
    }

    public void setStartTime(Timestamp startTime) {
        this.startTime = startTime;
    }

    public Timestamp getEndTime() {
        return endTime;
    }

    public void setEndTime(Timestamp endTime) {
        this.endTime = endTime;
    }

    public Timestamp getGmtCreate() {
        return gmtCreate;
    }

    public void setGmtCreate(Timestamp gmtCreate) {
        this.gmtCreate = gmtCreate;
    }

    public Timestamp getGmtModified() {
        return gmtModified;
    }

    public void setGmtModified(Timestamp gmtModified) {
        this.gmtModified = gmtModified;
    }

    public Integer getIsDelete() {
        return isDelete;
    }

    public void setIsDelete(Integer isDelete) {
        this.isDelete = isDelete;
    }

    @Override
    public String toString() {
        return "ICCard{" +
                "id=" + id +
                ", icNo='" + icNo + '\'' +
                ", memberId=" + memberId +
                ", startTime=" + startTime +
                ", endTime=" + endTime +
                ", gmtCreate=" + gmtCreate +
                ", gmtModified=" + gmtModified +
                ", isDelete=" + isDelete +
                '}';
    }
}

