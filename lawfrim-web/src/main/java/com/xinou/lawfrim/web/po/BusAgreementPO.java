package com.xinou.lawfrim.web.po;

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
@ApiModel(value="BusAgreementPO", description="")
public class BusAgreementPO extends BaseEntity {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "合同名称")
    private String name;

    @ApiModelProperty(value = "客户id")
    private Integer customId;

    @ApiModelProperty(value = "合同状态 0:- 1:发布 2:初审 3:终审 4:审核完成")
    private Integer state;


    @ApiModelProperty(value = "需审核方0：-1：甲方2：乙方")
    private Integer audit;

    @ApiModelProperty(value = "备注")
    private String remark;

}
