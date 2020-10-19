package com.xinou.lawfrim.web.base;

import lombok.Builder;
import lombok.Data;
import lombok.experimental.Tolerate;

import java.io.Serializable;

/**
 * @author lizhongyuan
 */
@Data
@Builder
public class JwtModel implements Serializable {

    /**
     * 用户id
     */
    private Long userId;

    /**
     * 用户账号
     */
    private String account;

    /**
     * 用户名
     */
    private String userName;

    /**
     * 1 sys 2 web
     */
    private Integer type;


    @Tolerate
    public JwtModel() {}
}
