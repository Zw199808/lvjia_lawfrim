package com.xinou.lawfrim.web.vo.lawyer;

import com.alibaba.excel.annotation.ExcelProperty;
import com.alibaba.excel.annotation.write.style.ColumnWidth;
import com.alibaba.excel.annotation.write.style.HeadStyle;
import lombok.Data;

/**
 * @author WangXin
 * @date 2020/10/26
 * Description:
 */
@Data
@HeadStyle(fillBackgroundColor = 13)
public class LawyerAgreementExcel {

    /**
     * 合同名
     */
    @ExcelProperty(value = {"历史完成合同","序号"}, index = 0)
    @ColumnWidth(10)
    private Integer index;
    /**
     * 合同名
     */
    @ExcelProperty(value = {"历史完成合同","合同名"}, index = 1)
    @ColumnWidth(25)
    private String agreeName;
    /**
     * 客户
     */
    @ExcelProperty(value = {"历史完成合同","客户"}, index = 2)
    @ColumnWidth(15)
    private String customName;
    /**
     * 初审
     */
    @ExcelProperty(value = {"历史完成合同","初审"}, index = 3)
    @ColumnWidth(15)
    private String firstLawyerName;
    /**
     * 复审
     */
    @ExcelProperty(value = {"历史完成合同","复审"}, index = 4)
    @ColumnWidth(15)
    private String endLawyerName;
    /**
     * 要的时间
     */
    @ExcelProperty(value = {"历史完成合同","要的时间"}, index = 5)
    @ColumnWidth(15)
    private String endTime;
    /**
     * 完成情况:初审
     */
    @ExcelProperty(value = {"历史完成合同","完成情况","初审"}, index = 6)
    @ColumnWidth(25)
    private String first;
    /**
     * 完成情况：终审
     */
    @ExcelProperty(value = {"历史完成合同","完成情况","终审"}, index = 7)
    @ColumnWidth(25)
    private String second;

    /**
     * 备注
     */
    @ExcelProperty(value = {"历史完成合同","备注"}, index = 8)
    @ColumnWidth(15)
    private String remark;
}
