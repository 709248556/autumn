package com.autumn.mybatis.mapper.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * 列顺序
 * 
 * @author 老码农
 *
 *         2018-04-10 13:43:28
 */
@Target({ ElementType.FIELD, ElementType.METHOD })
@Retention(RetentionPolicy.RUNTIME)
public @interface ColumnOrder {

	/**
	 * 值
	 * 
	 * @return
	 */
	int value() default 0;
}
