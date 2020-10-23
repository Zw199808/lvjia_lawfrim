package com.xinou.lawfrim.web.vo.agreementAudit;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

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
public class ScoreVo implements Serializable {

    @ApiModelProperty("平均分")
    private double averageScore;

    @ApiModelProperty("最高分")
    private double maxScore;

    @ApiModelProperty("最低分")
    private double minScore;

}