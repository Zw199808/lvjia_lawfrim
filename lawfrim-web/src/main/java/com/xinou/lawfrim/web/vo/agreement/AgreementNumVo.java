package com.xinou.lawfrim.web.vo.agreement;

import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.annotations.ApiModelProperty;
import io.swagger.models.auth.In;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;
import org.springframework.beans.factory.annotation.Autowired;

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
public class AgreementNumVo implements Serializable {

    @ApiModelProperty("月份")
    @JsonFormat(shape = JsonFormat.Shape.STRING,pattern="yyyy-MM",timezone="GMT+8")
    private Timestamp groupYear;

    @ApiModelProperty("每月总数")
    private Integer totalNumber;

    @ApiModelProperty("每月新增")
    private Integer increaseNumber;

    @ApiModelProperty("每月解决")
    private Integer solveNumber;
}