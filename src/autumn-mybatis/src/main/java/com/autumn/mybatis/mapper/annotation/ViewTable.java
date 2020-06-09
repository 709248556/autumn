package com.autumn.mybatis.mapper.annotation;

import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import static java.lang.annotation.ElementType.TYPE;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

/**
 * 视图表
 * <p>
 * 此类表只能进行查询操作
 * </p>
 * 
 * @author 老码农
 *         <p>
 *         Description
 *         </p>
 * @date 2018-01-20 23:20:58
 */
@Target(TYPE)
@Retention(RUNTIME)
public @interface ViewTable {
	
}
