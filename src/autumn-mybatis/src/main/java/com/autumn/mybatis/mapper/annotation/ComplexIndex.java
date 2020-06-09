package com.autumn.mybatis.mapper.annotation;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * 复合索引
 * <p>
 * 
 * @ComplexIndexs 的子级
 *                </P>
 * 
 * @author 老码农
 *         <p>
 *         Description
 *         </p>
 * @date 2018-01-20 23:42:08
 */
@Target({})
@Retention(RetentionPolicy.RUNTIME)
public @interface ComplexIndex {

	/**
	 * 属性集合
	 * 
	 * @return
	 */
	String[] propertys();

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
