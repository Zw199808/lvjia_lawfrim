package com.xinou.lawfrim.web.vo.lawyer;

import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

import java.io.Serializable;
import java.sql.Timestamp;

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

    @ApiModelProperty("姓名")
    private String name;

//    private Integer sysUserId;

    @ApiModelProperty("律师在线状态0:- 1:在线 2:不在线")
    private Integer state;

    @ApiModelProperty("账户")
    private String account;

    @ApiModelProperty("密码")
    private String password;

    @ApiModelProperty("角色id")
    private Integer roleId;

    @ApiModelProperty("角色名称")
    private String roleName;

    @ApiModelProperty("注册时间(字符串)")
    private Timestamp createTime;

    @JsonFormat(shape = JsonFormat.Shape.STRING,pattern="yyyy-MM-dd HH:mm:ss",timezone="GMT+8")
    @ApiModelProperty("注册时间(时间戳)")
    private Timestamp gmtCreate;

    @ApiModelProperty("合同数")
    private Integer agreeNum;
}