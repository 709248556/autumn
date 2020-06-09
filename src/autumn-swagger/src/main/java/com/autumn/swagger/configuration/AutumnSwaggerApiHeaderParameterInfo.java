package com.autumn.swagger.configuration;

import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;

/**
 * Api 头参数信息
 * 
 * @author 老码农 2018-12-27 11:05:44
 */
@Setter
@Getter
public class AutumnSwaggerApiHeaderParameterInfo implements Serializable {
	private static final long serialVersionUID = 8587729529013059448L;

	/**
	 * 参数名称
	 */
	private String name;

	/**
	 * 数据类型
	 */
	private String dataType;

	/**
	 * 说明
	 */
	private String description;

	/**
	 * 是否必须
	 */
	private boolean required;
}
