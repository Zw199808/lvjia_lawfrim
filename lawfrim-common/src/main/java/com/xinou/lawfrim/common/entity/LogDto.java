package com.xinou.lawfrim.common.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

import java.sql.Timestamp;
import java.util.List;

/**
 * @author xiao_XX
 * @date 2020/09/29.
 * Description:
 */
@Data
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = false)
public class LogDto {
    /**
     * 操作模块
     **/
    private String module;
    /**
     * 操作方法
     **/
    private String methods;
    /**
     * 操作时间
     **/
    private Timestamp operationTimeStr;
    /**
     * 操作描述
     **/
    private String description;
    /**
     * 报错信息文件名
     */
    private String exceptionMessage;

    private Integer pageNumber;

    private Integer pageSize;

    private Integer type;

    /**
     * 日志搜索开始时间
     **/
    private String startTimeStr;
    /**
     * 日志搜索结束时间
     **/
    private String endTimeStr;
    /**
     * 姓名搜索
     */
    private String realName;
    /**
     * 类型
     **/
    private List<Integer> typeList;
}
