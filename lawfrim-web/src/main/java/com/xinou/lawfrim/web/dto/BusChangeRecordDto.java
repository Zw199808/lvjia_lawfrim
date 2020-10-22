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
public class BusChangeRecordDto extends PageDto implements Serializable {

    @ApiModelProperty(value = "选择律师Id")
    private Integer lawyerId;

    @ApiModelProperty(value = "审批表Id")
    private Integer agreementAuditId;

    @ApiModelProperty(value = "当前登录人id")
    private Integer adminId;
}
