package com.xinou.lawfrim.web.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import java.util.Date;
import com.xinou.lawfrim.common.util.BaseEntity;
import com.baomidou.mybatisplus.annotation.TableField;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

/**
 * <p>
 * 
 * </p>
 *
 * @author Wangxin
 * @since 2020-10-16
 */
@Data
@EqualsAndHashCode(callSuper = true)
@Accessors(chain = true)
@TableName("bus_agreement")
@ApiModel(value="BusAgreement对象", description="")
public class BusAgreement extends BaseEntity {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "合同名称")
    @TableField("name")
    private String name;

    @ApiModelProperty(value = "客户id")
    @TableField("custom_id")
    private Integer customId;

    @ApiModelProperty(value = "合同状态 0:- 1:发布 2:初审 3:终审 4:审核完成")
    @TableField("state")
    private Integer state;

    @ApiModelProperty(value = "截止日期")
    @TableField("end_time")
    private Date endTime;

    @ApiModelProperty(value = "需审核方0：-1：甲方2：乙方")
    @TableField("audit")
    private Integer audit;

    @ApiModelProperty(value = "备注")
    @TableField("remark")
    private String remark;


}
