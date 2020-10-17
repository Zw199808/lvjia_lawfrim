package com.xinou.lawfrim.web.vo;

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
public class CustomVo implements Serializable {

    @ApiModelProperty("主键")
    private Integer id;

    @ApiModelProperty("姓名")
    private String name;

    @ApiModelProperty("账户")
    private String account;

    @ApiModelProperty("密码")
    private String password;

    @ApiModelProperty("注册时间(字符串)")
    private String createTime;

    @ApiModelProperty("上传合同数")
    private Integer agreeNum;
}