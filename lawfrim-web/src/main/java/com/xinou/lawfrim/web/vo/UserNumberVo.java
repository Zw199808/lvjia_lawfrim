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
 * @since 2020-10-23
 */
@Data
@SuperBuilder
@NoArgsConstructor
public class UserNumberVo implements Serializable {

    @ApiModelProperty("客户数量")
    private Integer customNumber;

    @ApiModelProperty("律师数量")
    private Integer lawyerNumber;


}