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
public class BusLawyerDto extends PageDto implements Serializable {

    @ApiModelProperty(value = "id")
    private Integer id;//id

    @ApiModelProperty(value = "律师在线状态0:- 1:在线 2:不在线")
    private Integer state;//在线状态

    @ApiModelProperty(value = "sysUserId")
    private Integer sysUserId;//sys表userId

    @ApiModelProperty(value = "姓名", required = true)
    private String name;//姓名

    @ApiModelProperty(value = "账户", required = true)
    private String account;//账户

    @ApiModelProperty(value = "旧密码")
    private String oldPassword;//密码

    @ApiModelProperty(value = "密码")
    private String password;//密码

    @ApiModelProperty(value = "角色")
    private Integer roleId;//角色
}
