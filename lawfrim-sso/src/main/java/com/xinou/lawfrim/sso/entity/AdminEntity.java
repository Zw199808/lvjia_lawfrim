package com.xinou.lawfrim.sso.entity;


import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.Serializable;

/**
 * @author: XGLLHZ
 * @date: 2020/5/28 上午10:57
 * @description:
 */
@Data
@EqualsAndHashCode(callSuper = false)
public class AdminEntity extends BaseEntitySSO implements Serializable {

    @ApiModelProperty(value = "账号", example = "admin")
    private String account;

    @ApiModelProperty(value = "密码", example = "e10adc3949ba59abbe56e057f20f883e")
    private String pwd;

    @ApiModelProperty(value = "登录ip", example = "127.0.0.1")
    private String ip;

    @ApiModelProperty(value = "类型", example = "1")
    private Integer type;

    private String icNo;

}
