package com.xinou.lawfrim.sso.entity;


import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.Serializable;

/**
 * @author: XGLLHZ
 * @date: 2020/5/28 上午10:57
 * @description:
 */
@Data
@EqualsAndHashCode(callSuper = false)
public class AdminEntity extends BaseEntitySSO implements Serializable {

    private String account;

    private String pwd;

    private String ip;

    private Integer type;

    private String icNo;

}
