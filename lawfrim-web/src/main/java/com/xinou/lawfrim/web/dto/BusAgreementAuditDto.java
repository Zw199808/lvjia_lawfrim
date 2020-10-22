package com.xinou.lawfrim.web.dto;

import io.swagger.annotations.ApiModelProperty;
import io.swagger.models.auth.In;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.io.Serializable;
import java.util.Date;

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
public class BusAgreementAuditDto extends PageDto implements Serializable {

    @ApiModelProperty(value = "律师id")
    private Integer lawyerId;

    @ApiModelProperty(value = "合同id")
    private Integer agreementId;

    @ApiModelProperty(value = "合同类型")
    private Integer agreementType;

    @ApiModelProperty(value = "初审合同名")
    private String firstAgreementName;

    @ApiModelProperty(value = "复审合同名")
    private String secondAgreementName;

}
