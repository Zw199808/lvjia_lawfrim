package com.xinou.lawfrim.web.base;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableLogic;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.xinou.lawfrim.web.group.DeleteGroup;
import com.xinou.lawfrim.web.group.InfoGroup;
import com.xinou.lawfrim.web.group.UpdateGroup;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;
import lombok.experimental.SuperBuilder;

import javax.validation.constraints.Min;
import java.sql.Timestamp;

/**
 * Created by xiao_XX on date 2020/06/09.
 * Description:
 */
@Data
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
@Accessors(chain = true)
@JsonInclude(JsonInclude.Include.NON_NULL)
public class BaseWebEntity {
    @ApiModelProperty(value = "主键id")
    @Min(
            message = "ID必须大于0",
            value = 1,
            groups = {DeleteGroup.class, UpdateGroup.class, InfoGroup.class}
    )
    @TableId(type = IdType.AUTO)
    private Long id;

    @TableLogic
    @JsonIgnore
    @ApiModelProperty(hidden = true)
    private Integer isDelete;

    @ApiModelProperty(hidden = true)
    private Timestamp gmtCreate;

    @ApiModelProperty(hidden = true)
    @JsonFormat(timezone = "GMT+8", shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss")
    private Timestamp gmtModified;

    /**
     * 当前页
     */
    @Builder.Default
    @TableField(exist = false)
    @ApiModelProperty(value = "当前页", example = "1")
    private Long pageNumber = 1L;

    /**
     * 页面大小
     */
    @Builder.Default
    @TableField(exist = false)
    @ApiModelProperty(value = "页面大小", example = "10")
    private Long pageSize = 10L;
}
