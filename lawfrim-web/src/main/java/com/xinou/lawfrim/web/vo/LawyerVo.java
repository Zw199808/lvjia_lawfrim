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

    private Integer id;//id

    private String name;

//    private Integer sysUserId;

    private Integer state;

    private String account;//账户

    private String password;//密码

    private Integer roleId;//角色id

    private String roleName;//角色名称


}