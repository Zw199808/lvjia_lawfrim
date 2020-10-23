package com.xinou.lawfrim.web.vo.agreement;

import io.swagger.annotations.ApiModelProperty;
import io.swagger.models.auth.In;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;
import org.springframework.beans.factory.annotation.Autowired;

import java.io.Serializable;

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
    private String groupYear;

    @ApiModelProperty("每月总数")
    private Integer totalNumber;

    @ApiModelProperty("每月新增")
    private Integer increaseNumber;

    @ApiModelProperty("每月解决")
    private Integer solveNumber;
}