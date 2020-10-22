package com.xinou.lawfrim.web.dto;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.io.Serializable;
import java.util.List;

/**
 * <p>
 * 
 * </p>
 *
 * @author Wangxin
 * @since 2020-10-22
 */
@Data
@ToString
@NoArgsConstructor
public class DownloadAgreementDto extends PageDto implements Serializable {

    @ApiModelProperty(value = "合同Id")
    private Integer agreeId;

    @ApiModelProperty(value = "复审合同名")
    private String endAgreeName;

    @ApiModelProperty(value = "初审合同名")
    private String firstAgreeName;
}
