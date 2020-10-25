package com.xinou.lawfrim.web.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import com.xinou.lawfrim.common.util.BaseEntity;
import com.baomidou.mybatisplus.annotation.TableField;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

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
@TableName("bus_lawyer")
@ApiModel(value="BusLawyer对象", description="")
public class BusLawyer extends BaseEntity {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "姓名")
    @TableField("name")
    private String name;

    @ApiModelProperty(value = "sys_user表id")
    @TableField("sys_user_id")
    private Integer sysUserId;

    @ApiModelProperty(value = "律师在线状态 0:- 1:在线 2:不在线")
    @TableField("state")
    private Integer state;


    @ApiModelProperty(value = "是否顾问法务 0：- 1：是")
    @TableField("adviser")
    private Integer adviser;


}
