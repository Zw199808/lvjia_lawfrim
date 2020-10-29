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
public class ExcelLawyerVo implements Serializable {

    @ApiModelProperty("序号")
    private Integer index;

    @ApiModelProperty("姓名")
    private String name;

    @ApiModelProperty("账户")
    private String account;

    @ApiModelProperty("角色名称")
    private String roleName;

    @ApiModelProperty("注册时间(字符串)")
    private Timestamp createTime;

    @ApiModelProperty("合同数")
    private Integer agreeNum;
}