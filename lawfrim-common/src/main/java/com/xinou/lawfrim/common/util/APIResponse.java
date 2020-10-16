package com.xinou.lawfrim.common.util;

import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.Serializable;
import java.util.Collections;
import java.util.Map;

/**
 * @author: XGLLHZ
 * @date: 2020/5/7 下午9:49
 * @description:
 */
@Data
@EqualsAndHashCode(callSuper = false)
public class APIResponse<T> implements Serializable {

    private String reCode;   //返回状态码

    private String reMsg;   //返回消息体

    private T body;   //返回数据体

    public APIResponse() {
        this.reCode = Config.RE_SUCCESS_CODE;
        this.reMsg = Config.RE_SUCCESS_MSG;
        body = (T) Collections.EMPTY_MAP;
    }

    public APIResponse(Map<String, Object> data) {
        this.reCode = Config.RE_SUCCESS_CODE;
        this.reMsg = Config.RE_SUCCESS_MSG;
        body = (T) data;
    }

    public APIResponse(String errorCode, String errorMsg) {
        this.reCode = errorCode;
        this.reMsg = errorMsg;
        body = (T) Collections.EMPTY_MAP;
    }

}
