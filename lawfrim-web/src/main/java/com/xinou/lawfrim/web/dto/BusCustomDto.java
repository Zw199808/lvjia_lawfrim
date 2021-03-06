package com.xinou.lawfrim.web.dto;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import com.xinou.lawfrim.common.util.BaseEntity;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import io.swagger.models.auth.In;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.ToString;
import lombok.experimental.Accessors;
import org.omg.PortableInterceptor.INACTIVE;

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
@ToString
@NoArgsConstructor
public class BusCustomDto extends PageDto implements Serializable {

    @ApiModelProperty(value = "id")
    private Integer id;

    @ApiModelProperty(value = "姓名")
    private String name;

    @ApiModelProperty(value = "账户")
    private String account;

    @ApiModelProperty(value = "密码")
    private String password;

    @ApiModelProperty(value = "旧密码")
    private String oldPassword;
}
