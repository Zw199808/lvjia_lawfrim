package com.xinou.lawfrim.web.vo.lawyer;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
public class LawyerSimpleVo {

    @ApiModelProperty("姓名")
    private String name;

    @ApiModelProperty("角色名称")
    private String roleName;
}