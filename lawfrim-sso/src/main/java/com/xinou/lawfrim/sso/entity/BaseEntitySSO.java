package com.xinou.lawfrim.sso.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.Serializable;

/**
 * @author: XGLLHZ
 * @date: 2020/5/28 上午10:19
 * @description:
 */
@Data
@EqualsAndHashCode(callSuper = false)
public class BaseEntitySSO implements Serializable {

    @TableField(exist = false)
    private Integer page = 1;

    @TableField(exist = false)
    private Integer pageSize = 10;

    @TableField(exist = false)
    private String selectValue = "";

    @TableField(exist = false)
    private String searchValue = "";

}
