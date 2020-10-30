package com.xinou.lawfrim.web.vo.agreement;

import com.xinou.lawfrim.common.util.TimeChange;
import com.xinou.lawfrim.web.vo.lawyer.LawyerChangeVo;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

import java.io.Serializable;
import java.sql.Timestamp;
import java.util.List;

/**
 * <p>
 * 
 * </p>
 *
 * @author Wangxin
 * @since 2020-10-21
 */
@Data
@SuperBuilder
@NoArgsConstructor
public class LawyerAgreementListVo implements Serializable {

    @ApiModelProperty("合同id")
    private Integer agreeId;

    @ApiModelProperty("初审律师sysId")
    private Integer firstLawyerId;

    @ApiModelProperty("复审律师sysId")
    private Integer endLawyerId;

    @ApiModelProperty("初审律师姓名")
    private String firstLawyerName;

    @ApiModelProperty("复审律师姓名")
    private String endLawyerName;

    @ApiModelProperty("合同名称")
    private String agreeName;

    @ApiModelProperty("上传人姓名")
    private String customName;

    @ApiModelProperty("截止日期")
    private String endTime;

    @ApiModelProperty("上传日期")
    private String upTime;

    @ApiModelProperty("合同状态")
    private Integer agreeState;

    @ApiModelProperty("审批表Id")
    private Integer agreementAuditId;

    @ApiModelProperty("领取状态0：- 1：未接收  2：已接收")
    private Integer getState;


}