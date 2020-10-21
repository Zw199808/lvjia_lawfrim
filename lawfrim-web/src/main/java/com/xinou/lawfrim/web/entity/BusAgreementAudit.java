package com.xinou.lawfrim.web.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.xinou.lawfrim.common.util.BaseEntity;
import com.baomidou.mybatisplus.annotation.TableField;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import java.sql.Timestamp;

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
@TableName("bus_agreement_audit")
@ApiModel(value="BusAgreementAudit对象", description="")
public class BusAgreementAudit extends BaseEntity {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "律师id")
    @TableField("lawyer_id")
    private Integer lawyerId;

    @ApiModelProperty(value = "合同id")
    @TableField("agreement_id")
    private Integer agreementId;

    @ApiModelProperty(value = "初审合同")
    @TableField("first_agreement_name")
    private String firstAgreementName;

    @ApiModelProperty(value = "终审合同")
    @TableField("second_agreement_name")
    private String secondAgreementName;

    @ApiModelProperty(value = "评分")
    @TableField("score")
    private Double score;

    @ApiModelProperty(value = "合同类型 0：- 1：买卖合同 2：供水电气热力合同 3：赠与合同 4：借款合同 5：租赁合同 6：股权合同 7：融资租赁合同 8：承揽合同 9：建设工程合同 10：物流运输合同 11：技术合同 12：保管合同 13：仓储合同 14：委托合同 15：行纪合同 16：居间合同 17：混合法律关系合同 18：其他合同")
    @TableField("type")
    private Integer type;

    @ApiModelProperty("处理中合同数")
    @TableField(exist = false)
    private Integer notAuditAgreement;

    @ApiModelProperty("已处理合同数")
    @TableField(exist = false)
    private Integer auditAgreement;

    @ApiModelProperty("合同总数")
    @TableField(exist = false)
    private Integer agreeNum;

    @ApiModelProperty("复审律师id")
    @TableField(exist = false)
    private Integer endLawyerId;

    @ApiModelProperty("初审回复时间")
    @JsonFormat(shape = JsonFormat.Shape.STRING,pattern="yyyy-MM-dd HH:mm:ss",timezone="GMT+8")
    private Timestamp firstAuditTime;

}
