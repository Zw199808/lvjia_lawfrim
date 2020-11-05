package com.xinou.lawfrim.web.vo.custom;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

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
@SuperBuilder
@NoArgsConstructor
public class CustomNumVo implements Serializable {

    @ApiModelProperty("处理中合同数")
    private Long notAuditAgreement;

    @ApiModelProperty("已处理合同数")
    private Long auditAgreement;

    @ApiModelProperty("合同总数")
    private Long agreeNum;

    @ApiModelProperty("处理中合同数-格式化")
    private String notAuditAgreementStr;

    @ApiModelProperty("已处理合同数-格式化")
    private String auditAgreementStr;

    @ApiModelProperty("合同总数-格式化")
    private String agreeNumStr;
}