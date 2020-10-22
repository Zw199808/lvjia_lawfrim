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
 * @since 2020-10-20
 */
@Data
@SuperBuilder
@NoArgsConstructor
public class DownloadAgreementVo implements Serializable {

    @ApiModelProperty("初审合同url")
    private String firstURL;

    @ApiModelProperty("复审合同url")
    private String secondURL;
}