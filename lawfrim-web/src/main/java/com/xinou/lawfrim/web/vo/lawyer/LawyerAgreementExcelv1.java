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
public class LawyerAgreementExcelv1 {

    /**
     * 合同名
     */
    @ExcelProperty(value = {"今日发布合同审查统计","序号"}, index = 0)
    @ColumnWidth(10)
    private Integer index;
    /**
     * 合同名
     */
    @ExcelProperty(value = {"今日发布合同审查统计","合同名"}, index = 1)
    @ColumnWidth(25)
    private String agreeName;
    /**
     * 客户
     */
    @ExcelProperty(value = {"今日发布合同审查统计","客户"}, index = 2)
    @ColumnWidth(15)
    private String customName;
    /**
     * 初审
     */
    @ExcelProperty(value = {"今日发布合同审查统计","初审"}, index = 3)
    @ColumnWidth(15)
    private String firstLawyerName;
    /**
     * 复审
     */
    @ExcelProperty(value = {"今日发布合同审查统计","复审"}, index = 4)
    @ColumnWidth(15)
    private String endLawyerName;
    /**
     * 要的日期
     */
    @ExcelProperty(value = {"今日发布合同审查统计","要的日期"}, index = 5)
    @ColumnWidth(15)
    private String endTime;


    /**
     * 要的时间
     */
    @ExcelProperty(value = {"今日发布合同审查统计","要的时间"}, index = 6)
    @ColumnWidth(15)
    private String endTime2;
    /**
     * 完成情况:初审
     */
    @ExcelProperty(value = {"今日发布合同审查统计","完成情况","初审"}, index = 7)
    @ColumnWidth(25)
    private String first;
    /**
     * 完成情况：终审
     */
    @ExcelProperty(value = {"今日发布合同审查统计","完成情况","终审"}, index = 8)
    @ColumnWidth(25)
    private String second;

    /**
     * 备注
     */
    @ExcelProperty(value = {"今日发布合同审查统计","备注"}, index = 9)
    @ColumnWidth(15)
    private String remark;

    public LawyerAgreementExcelv1(){}

    public LawyerAgreementExcelv1(LawyerAgreementExcelv2 lawyerAgreementExcel) {
        this.index = lawyerAgreementExcel.getIndex();
        this.agreeName = lawyerAgreementExcel.getAgreeName();
        this.customName = lawyerAgreementExcel.getCustomName();
        this.firstLawyerName = lawyerAgreementExcel.getFirstLawyerName();
        this.endLawyerName = lawyerAgreementExcel.getEndLawyerName();
        this.endTime = lawyerAgreementExcel.getEndTime();
        this.endTime2 = lawyerAgreementExcel.getEndTime2();
        this.first = lawyerAgreementExcel.getFirst();
        this.second = lawyerAgreementExcel.getSecond();
        this.remark = lawyerAgreementExcel.getRemark();
    }
}
