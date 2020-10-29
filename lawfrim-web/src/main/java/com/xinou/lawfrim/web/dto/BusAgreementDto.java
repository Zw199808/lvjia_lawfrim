package com.xinou.lawfrim.web.dto;

import com.baomidou.mybatisplus.annotation.TableField;
import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.annotations.ApiModelProperty;
import io.swagger.models.auth.In;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;
import org.springframework.web.multipart.MultipartFile;

import java.io.Serializable;
import java.sql.Timestamp;
import java.util.Date;
import java.util.List;

/**
 * <p>
 * 
 * </p>
 *
 * @author Wangxin
 * @since 2020-10-20
 */
@Data
@ToString
@NoArgsConstructor
public class BusAgreementDto extends PageDto implements Serializable {

    @ApiModelProperty(value = "合同id")
    private Integer id;

    @ApiModelProperty(value = "需审核方0：-1：甲方2：乙方")
    private Integer audit;

    @ApiModelProperty(value = "律师Id")
    private Integer lawyerId;

    @ApiModelProperty(value = "截止日期")
    @JsonFormat(shape = JsonFormat.Shape.STRING,pattern="yyyy-MM-dd HH:mm:ss",timezone="GMT+8")
    private Timestamp endTime;

    @ApiModelProperty(value = "备注")
    private String remark;

    @ApiModelProperty(value = "文件名")
    private String name;

    @ApiModelProperty(value = "客户id")
    private Integer customId;

    @ApiModelProperty(value = "adminId")
    private Integer adminId;

    @ApiModelProperty(value = "合同状态0:- 1:发布 2:初审 3:终审 4:审核完成 5:转移中")
    private Integer state;

    @ApiModelProperty(value = "合同状态0:- 1:发布 2:初审 3:终审 4:审核完成")
    private List<Integer> stateList;

    @ApiModelProperty(value = "领取合同时间")
    private String gmtTime;

    @ApiModelProperty(value = "领取合同时间2")
    private String gmtTime2;

    @ApiModelProperty(value = "是否今日标签 1:不按时间查询")
    private Integer tag;
}
