package com.xinou.lawfrim.web.dto;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.io.Serializable;

/**
 * <p>
 * 
 * </p>
 *
 * @author Wangxin
 * @since 2020-10-24
 */
@Data
@ToString
@NoArgsConstructor
public class SortRuleDto extends PageDto implements Serializable {

    @ApiModelProperty(value = "未处理（未完成）0- 1:按其排序")
    private Integer notAuditNum;

    @ApiModelProperty(value = "已处理0- 1:按其排序")
    private Integer auditNum;

    @ApiModelProperty(value = "是否顾问 1：按其排序")
    private Integer isAdviser;

    @ApiModelProperty(value = "综合评分 1:按其排序")
    private Integer score;

}
