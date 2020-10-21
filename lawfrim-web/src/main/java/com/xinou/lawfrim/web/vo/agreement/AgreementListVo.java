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
 * @since 2020-10-21
 */
@Data
@SuperBuilder
@NoArgsConstructor
public class AgreementListVo implements Serializable {

    @ApiModelProperty("合同名称")
    private String agreeName;

    @ApiModelProperty("上传人姓名")
    private String customName;

    @ApiModelProperty("截止日期")
    private String endTime;

    @ApiModelProperty("合同状态")
    private Integer state;
}