package com.xinou.lawfrim.web.dto;

import com.baomidou.mybatisplus.annotation.TableField;
import io.swagger.annotations.ApiModelProperty;
import io.swagger.models.auth.In;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;
import org.springframework.web.multipart.MultipartFile;

import java.io.Serializable;
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

    @ApiModelProperty(value = "id")
    private Integer id;

    @ApiModelProperty(value = "需审核方0：-1：甲方2：乙方")
    private Integer audit;

    @ApiModelProperty(value = "截止日期")
    private Date endTime;

    @ApiModelProperty(value = "备注")
    private String remark;

    @ApiModelProperty(value = "文件名")
    private String name;

    @ApiModelProperty(value = "客户id")
    private Integer customId;

    @ApiModelProperty(value = "合同状态0:- 1:发布 2:初审 3:终审 4:审核完成")
    private Integer state;

    @ApiModelProperty(value = "合同状态0:- 1:发布 2:初审 3:终审 4:审核完成")
    private List<Integer> stateList;
}
