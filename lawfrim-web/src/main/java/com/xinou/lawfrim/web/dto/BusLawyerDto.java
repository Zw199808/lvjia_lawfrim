package com.xinou.lawfrim.web.dto;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import com.xinou.lawfrim.common.util.BaseEntity;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
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
public class BusLawyerDto extends BaseEntity implements Serializable {


    private String name;//姓名

    private Integer sysUserId;//sys表userId

    private Integer state;//律师在线状态 0:- 1:在线 2:不在线

    private String account;//账户

    private String password;//密码

    private Integer roleId;//角色
}
