package com.xinou.lawfrim.web.vo.lawyer;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.sql.Timestamp;

@Data
public class LawyerChangeVo {

    @ApiModelProperty("转移律师姓名")
    private String changeLawyerName;

    @ApiModelProperty("转移时间")
    private Timestamp changeTime;

    @ApiModelProperty("获得方式2：转移 3：分配")
    private Integer type;
}