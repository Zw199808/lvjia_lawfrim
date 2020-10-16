package com.xinou.lawfrim.web.entity;

import com.baomidou.mybatisplus.annotation.TableName;
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
 * @author lizhongyuan
 * @since 2020-10-16
 */
@Data
@EqualsAndHashCode(callSuper = true)
@Accessors(chain = true)
@TableName("bus_change_record")
@ApiModel(value="BusChangeRecord对象", description="")
public class BusChangeRecord extends BaseEntity {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "律师id")
    @TableField("lawyer_id")
    private Integer lawyerId;

    @ApiModelProperty(value = "审批表id")
    @TableField("agreement_audit_id")
    private Integer agreementAuditId;

    @ApiModelProperty(value = "领取方式
0:-
1:领取
2:转移
3:分配")
    @TableField("type")
    private Integer type;

    @ApiModelProperty(value = "分配律师id
/原律师id")
    @TableField("old_or_assign_lawyer_id")
    private Integer oldOrAssignLawyerId;

    @ApiModelProperty(value = "转移状态
0：-
1：未接收
2：已接收")
    @TableField("state")
    private Integer state;


}
