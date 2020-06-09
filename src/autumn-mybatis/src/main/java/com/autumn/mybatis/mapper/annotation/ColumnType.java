package com.autumn.mybatis.mapper.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import org.apache.ibatis.type.JdbcType;
import org.apache.ibatis.type.TypeHandler;
import org.apache.ibatis.type.UnknownTypeHandler;

/**
 * 列类型
 * 
 * @author 老码农
 *
 *         2017-10-11 10:47:18
 */
@Target({ ElementType.FIELD, ElementType.METHOD })
@Retention(RetentionPolicy.RUNTIME)
public @interface ColumnType {

	/**
	 * 对应的Jdbc类型
	 * 
	 * @return
	 */
	JdbcType jdbcType() default JdbcType.UNDEFINED;

	/**
	 * 对应的处理类型
	 * 
	 * @return
	 */
	Class<? extends TypeHandler<?>> typeHandlerClass() default UnknownTypeHandler.class;
}
