package com.autumn.util;

import java.util.Date;

/**用户字段枚举
 * @author JuWa ▪ Zhang
 * @date 2017年9月15日
 */
public enum TestEnum implements ColumnEnum {

	/**
	 * 
	 */
	sex(Boolean.class),
	/**
	 * 
	 */
	type(String.class),
	/**
	 * 
	 */
	password(String.class),
	/**
	 * 
	 */
	address(String.class),
	/**
	 * 
	 */
	level(String.class),
	/**
	 * 
	 */
	education(String.class),
	/**
	 * 
	 */
	userGroupId(Integer.class),
	/**
	 * 
	 */
	age(Integer.class),
	/**
	 * 
	 */
	isDelete(Boolean.class),
	/**
	 * 
	 */
	registerTime(Date.class),
	/**
	 * 
	 */
	birthday(Integer.class);

	private TestEnum() {
		
	}
	private Class<?> columnType;
	private TestEnum(Class<?> clazz) {
		this.columnType = clazz;
	}
	
	@Override
	public Class<?> getColumnType() {
		return columnType;
	}
	
}
