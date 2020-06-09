package com.autumn.mybatis.mapper.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * 索引
 * <p>
 * 在属性上使用
 * </P>
 * 
 * @author 老码农
 *         <p>
 *         Description
 *         </p>
 * @date 2018-01-20 23:36:08
 */
@Target({ ElementType.FIELD, ElementType.METHOD })
@Retention(RetentionPolicy.RUNTIME)
public @interface Index {

	/**
	 * 索引名称
	 * 
	 * @return
	 */
	String name() default "";

	/**
	 * 是否是唯一
	 * 
	 * @return
	 */
	boolean unique() default false;
}
