package com.xinou.lawfrim.web.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import com.xinou.lawfrim.common.util.BaseEntity;
import com.baomidou.mybatisplus.annotation.TableField;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import java.io.Serializable;

/**
 * <p>
 * 
 * </p>
 *
 * @author Wangxin
 * @since 2020-10-16
 */
@Data
@EqualsAndHashCode(callSuper = true)
@Accessors(chain = true)
@TableName("bus_custom")
@ApiModel(value="BusCustom对象", description="")
public class BusCustom extends BaseEntity implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "用户名")
    @TableField("name")
    private String name;

    @ApiModelProperty(value = "账户")
    @TableField("account")
    private String account;

    @ApiModelProperty(value = "密码（加密）")
    @TableField("password")
    private String password;


}
