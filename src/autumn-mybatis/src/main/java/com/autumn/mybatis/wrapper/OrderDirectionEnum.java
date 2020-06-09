package com.autumn.mybatis.wrapper;

import java.io.Serializable;

/**
 * 排序方式
 * 
 * @author 老码农
 *
 *         2017-10-29 12:21:13
 */
public enum OrderDirectionEnum implements Serializable {

	/**
	 * 升序
	 */
	ASC("ASC"),
	/**
	 * 降序
	 */
	DESC("DESC");

	private final String value;

	/**
	 * 
	 * @param value
	 *            值
	 */
	private OrderDirectionEnum(String value) {
		this.value = value;
	}

	/**
	 * 获取值
	 * 
	 * @return
	 */
	public String getValue() {
		return value;
	}

	@Override
	public String toString() {
		return getValue();
	}

}
