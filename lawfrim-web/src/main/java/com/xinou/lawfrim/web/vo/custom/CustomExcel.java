package com.xinou.lawfrim.web.vo.custom;

import com.alibaba.excel.annotation.ExcelProperty;
import com.alibaba.excel.annotation.write.style.ColumnWidth;
import com.alibaba.excel.annotation.write.style.HeadStyle;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * @author WangXin
 * @date 2020/10/26
 * Description:
 */
@Data
@HeadStyle(fillBackgroundColor = 13)
public class CustomExcel {

    /**
     * 序号
     */
    @ExcelProperty(value = "序号", index = 0)
    @ColumnWidth(10)
    private Integer index;
    /**
     * 姓名
     */
    @ExcelProperty(value = "姓名", index = 1)
    @ColumnWidth(15)
    private String name;
    /**
     * 账户
     */
    @ExcelProperty(value = "账户", index = 2)
    @ColumnWidth(15)
    private String account;
    /**
     * 角色
     */
    @ExcelProperty(value = "注册时间", index = 3)
    @ColumnWidth(25)
    private String createTime;

    /**
     * 合同数
     */
    @ExcelProperty(value = "上传合同数", index = 4)
    @ColumnWidth(15)
    private Integer agreeNum;


}
