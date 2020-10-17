package com.xinou.lawfrim.web.dto;

import lombok.Data;

import java.io.Serializable;

/**
 * 分页对象
 * @author lizhongyuan
 */
@Data
public class PageDto implements Serializable {

	/** 页码 */
	private Integer pageNumber= 1;
	/** 每页结果数 */
	private Integer pageSize= 10;
}
