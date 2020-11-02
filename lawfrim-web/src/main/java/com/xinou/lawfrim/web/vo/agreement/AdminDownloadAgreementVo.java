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
 * @since 2020-11-2
 */
@Data
@SuperBuilder
@NoArgsConstructor
public class AdminDownloadAgreementVo implements Serializable {

    @ApiModelProperty("初始合同url")
    private String URL;

    @ApiModelProperty("初审合同url")
    private String firstURL;

    @ApiModelProperty("复审合同url")
    private String secondURL;
}