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
 * @since 2020-10-17
 */
@Data
@SuperBuilder
@NoArgsConstructor
public class ExcelCustomVo implements Serializable {

    @ApiModelProperty("序号")
    private Integer index;

    @ApiModelProperty("姓名")
    private String name;

    @ApiModelProperty("账户")
    private String account;

    @ApiModelProperty("注册时间")
    private String createTime;

    @ApiModelProperty("上传合同数")
    private Integer agreeNum;
}