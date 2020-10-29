package com.xinou.lawfrim.web.vo.agreement;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.xinou.lawfrim.web.vo.lawyer.LawyerChangeVo;
import io.swagger.annotations.ApiModelProperty;
import io.swagger.models.auth.In;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

import java.io.Serializable;
import java.sql.Timestamp;
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
public class AgreementInfoVo implements Serializable {

    @ApiModelProperty("合同id")
    private Integer agreeId;

    @ApiModelProperty("合同名称")
    private String agreeName;

    @ApiModelProperty("上传人姓名")
    private String customName;

    @ApiModelProperty("合同类型0：- 1：买卖合同 2：供水电气热力合同 3：赠与合同 4：借款合同 5：租赁合同 6：股权合同 7：融资租赁合同 8：承揽合同 9：建设工程合同 10：物流运输合同 11：技术合同 12：保管合同 13：仓储合同 14：委托合同 15：行纪合同 16：居间合同 17：混合法律关系合同 18：其他合同")
    private Integer agreeType;

    @ApiModelProperty("需审核方0：- 1：甲方 2：乙方")
    private Integer agreeAudit;

    @ApiModelProperty("上传日期")
    private Timestamp applyTime;

    @ApiModelProperty("截止日期")
    private Timestamp endTime;

    @ApiModelProperty("合同状态")
    private Integer state;

    @ApiModelProperty("初审律师")
    private String firstLawyerName;

    @ApiModelProperty("审批表Id")
    private Integer agreementAuditId;

    @ApiModelProperty("初审接受时间")
    private Timestamp gmtCreate;

    @ApiModelProperty("初审回复时间/复审接受时间")
    private Timestamp firstAuditTime;

    @ApiModelProperty("复审律师")
    private String endLawyerName;

    @ApiModelProperty("复审回复时间")
    private Timestamp gmtModified;

//    @ApiModelProperty("转移律师")
//    private String changeLawyerName;
//
//    @ApiModelProperty("转移时间")
//    private String changeTime;

    @ApiModelProperty("转移律师")
    private List<LawyerChangeVo> lawyerChangeVoList;

    @ApiModelProperty("分数")
    private String score;
}