package com.autumn.mybatis.mapper;

import org.apache.ibatis.type.JdbcType;
import org.apache.ibatis.type.TypeHandler;

/**
 * 
 * @author 老码农
 *
 *         2017-10-30 12:37:15
 */
public class QueryResultMapping {
	private String property;
	private String column;
	private Class<?> javaType;
	private JdbcType jdbcType;
	private TypeHandler<?> typeHandler;

	/**
	 * 获取属性
	 * 
	 * @return
	 */
	public String getProperty() {
		return property;
	}

	/**
	 * 设置属性
	 * 
	 * @param property
	 */
	public void setProperty(String property) {
		this.property = property;
	}

	/**
	 * 获取列
	 * 
	 * @return
	 */
	public String getColumn() {
		return column;
	}

	/**
	 * 设置列
	 * 
	 * @param column
	 */
	public void setColumn(String column) {
		this.column = column;
	}

	/**
	 * 获取 Java 类型
	 * 
	 * @return
	 */
	public Class<?> getJavaType() {
		return javaType;
	}

	/**
	 * 设置 Java 类型
	 * 
	 * @param javaType
	 */
	public void setJavaType(Class<?> javaType) {
		this.javaType = javaType;
	}

	/**
	 * 获取 Jdbc 类型
	 * 
	 * @return
	 */
	public JdbcType getJdbcType() {
		return jdbcType;
	}

	/**
	 * 设置 Jdbc 类型
	 * 
	 * @param jdbcType
	 */
	public void setJdbcType(JdbcType jdbcType) {
		this.jdbcType = jdbcType;
	}

	/**
	 * 获取类型处理器
	 * 
	 * @return
	 */
	public TypeHandler<?> getTypeHandler() {
		return typeHandler;
	}

	/**
	 * 设置类型处理器
	 * 
	 * @param typeHandler
	 */
	public void setTypeHandler(TypeHandler<?> typeHandler) {
		this.typeHandler = typeHandler;
	}
}
