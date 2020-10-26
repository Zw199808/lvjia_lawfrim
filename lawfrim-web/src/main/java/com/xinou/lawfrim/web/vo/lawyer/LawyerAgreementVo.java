package com.xinou.lawfrim.web.vo.lawyer;

import com.alibaba.excel.annotation.ExcelProperty;
import com.alibaba.excel.annotation.write.style.ColumnWidth;
import io.swagger.annotations.ApiModelProperty;
import io.swagger.models.auth.In;
import lombok.Data;

/**
 * @author WangXin
 * @date 2020/10/26
 * Description:
 */
@Data
public class LawyerAgreementVo {

    @ApiModelProperty("序号")
    private Integer index;

    @ApiModelProperty("合同名称")
    private String agreeName;

    @ApiModelProperty("上传人姓名")
    private String customName;

    @ApiModelProperty("初审律师姓名")
    private String firstLawyerName;

    @ApiModelProperty("复审律师姓名")
    private String endLawyerName;

    @ApiModelProperty("截止日期")
    private String endTime;

    @ApiModelProperty("合同状态")
    private String first;

    @ApiModelProperty("合同状态")
    private String second;

    @ApiModelProperty("备注")
    private String remark;

}
