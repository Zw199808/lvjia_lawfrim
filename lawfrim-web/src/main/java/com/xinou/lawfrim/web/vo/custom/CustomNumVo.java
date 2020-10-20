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
    private Integer notAuditAgreement;

    @ApiModelProperty("已处理合同数")
    private Integer auditAgreement;

    @ApiModelProperty("合同总数")
    private Integer agreeNum;
}