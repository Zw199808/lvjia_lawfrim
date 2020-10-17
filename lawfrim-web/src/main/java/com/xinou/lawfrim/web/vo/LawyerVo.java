package com.xinou.lawfrim.web.vo;

import com.baomidou.mybatisplus.annotation.TableName;
import com.xinou.lawfrim.common.util.BaseEntity;
import com.baomidou.mybatisplus.annotation.TableField;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;
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
public class LawyerVo implements Serializable {

    @ApiModelProperty("主键")
    private Integer id;

    @ApiModelProperty("name")
    private String name;

//    private Integer sysUserId;

    @ApiModelProperty("state")
    private Integer state;

    @ApiModelProperty("账户")
    private String account;

    @ApiModelProperty("密码")
    private String password;

    @ApiModelProperty("角色id")
    private Integer roleId;

    @ApiModelProperty("角色名称")
    private String roleName;


}