package com.xinou.lawfrim.common.util;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.xinou.lawfrim.common.util.Config;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.experimental.Accessors;

import java.io.Serializable;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 返回类
 *
 * @author lizhongyuan
 */
@Data
@ApiModel
@Accessors(chain = true)
@JsonInclude(JsonInclude.Include.NON_NULL)
public class APIResponse<T> implements Serializable {

    @ApiModelProperty(notes = "状态码", example = "200")
    private String reCode;

    @ApiModelProperty(notes = "状态信息", example = "成功")
    private String reMsg;

    @ApiModelProperty(notes = "返回数据")
    private Map<String, Object> body = new HashMap<>();

    @ApiModelProperty(notes = "token")
    private String token = "";

    @ApiModelProperty(notes = "返回实体类示例, 仅用于参考, 实际返回值中没有此字段")
    private T example;

    public APIResponse(String errorCode, String errorMsg) {
        this.reCode = errorCode;
        this.reMsg = errorMsg;
    }

    public APIResponse() {
        this.reCode = Config.RE_SUCCESS_CODE;
        this.reMsg = Config.RE_SUCCESS_MSG;
    }

    public APIResponse(Long id) {
        this.reCode = Config.RE_SUCCESS_CODE;
        this.reMsg = Config.RE_SUCCESS_MSG;
        body.put("id", id);
    }

    public APIResponse(Map data) {
        this.reCode = Config.RE_SUCCESS_CODE;
        this.reMsg = Config.RE_SUCCESS_MSG;
        body.putAll(data);
    }

    public APIResponse(T data) {
        this.reCode = Config.RE_SUCCESS_CODE;
        this.reMsg = Config.RE_SUCCESS_MSG;
        body.put("dataInfo", data);
    }

    public APIResponse(Collection<T> data) {
        this.reCode = Config.RE_SUCCESS_CODE;
        this.reMsg = Config.RE_SUCCESS_MSG;
        body.put("total", data.size());
        body.put("dataList", data);
    }

    public APIResponse(Collection<T> data, Long total) {
        this.reCode = Config.RE_SUCCESS_CODE;
        this.reMsg = Config.RE_SUCCESS_MSG;
        body.put("total", total);
        body.put("dataList", data);
    }

    public APIResponse(Collection<T> data, Integer total) {
        this.reCode = Config.RE_SUCCESS_CODE;
        this.reMsg = Config.RE_SUCCESS_MSG;
        body.put("total", total);
        body.put("dataList", data);
    }

    public APIResponse<T> put(String key, Object value) {
        body.put(key, value);
        return this;
    }

    public <V> V get(String key) {
        return (V) body.get(key);
    }

    @JsonIgnore
    public List<T> getDataList() {
        return (List<T>) body.get("dataList");
    }

    @JsonIgnore
    public T getDataInfo() {
        return (T) body.get("dataInfo");
    }
}
