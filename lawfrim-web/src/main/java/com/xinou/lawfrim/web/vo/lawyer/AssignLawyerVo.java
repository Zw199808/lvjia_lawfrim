package com.xinou.lawfrim.web.vo.lawyer;

import com.xinou.lawfrim.web.vo.lawyer.LawyerChangeVo;
import io.swagger.annotations.ApiModelProperty;
import io.swagger.models.auth.In;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

import java.io.Serializable;
import java.util.List;

/**
 * <p>
 * 
 * </p>
 *
 * @author Wangxin
 * @since 2020-10-21
 */
@Data
@SuperBuilder
@NoArgsConstructor
public class AssignLawyerVo implements Serializable {

    @ApiModelProperty("律师id")
    private Integer lawyerId;

    @ApiModelProperty("律师名")
    private String lawyerName;

    @ApiModelProperty("是否顾问 1是")
    private Integer isAdviser;

    @ApiModelProperty("综合评分")
    private double score;

    @ApiModelProperty("未处理、未完成数")
    private Integer notAuditNum;

    @ApiModelProperty("已完成数")
    private Integer auditNum;


}