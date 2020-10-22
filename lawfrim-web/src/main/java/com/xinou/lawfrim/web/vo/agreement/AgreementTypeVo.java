package com.xinou.lawfrim.web.vo.agreement;

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
 * @since 2020-10-22
 */
@Data
@SuperBuilder
@NoArgsConstructor
public class AgreementTypeVo implements Serializable {

    @ApiModelProperty("合同类型")
    private Integer type;

    @ApiModelProperty("合同数量")
    private Integer agreement;
}