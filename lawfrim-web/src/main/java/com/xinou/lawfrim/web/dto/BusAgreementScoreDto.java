package com.xinou.lawfrim.web.dto;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.io.Serializable;

/**
 * <p>
 * 
 * </p>
 *
 * @author Wangxin
 * @since 2020-10-20
 */
@Data
@ToString
@NoArgsConstructor
public class BusAgreementScoreDto extends PageDto implements Serializable {

    @ApiModelProperty(value = "审批id")
    private Integer agreementAuditId;

    @ApiModelProperty(value = "评分")
    private double score;

    @ApiModelProperty(value = "律师id")
    private Integer lawyerId;

}
