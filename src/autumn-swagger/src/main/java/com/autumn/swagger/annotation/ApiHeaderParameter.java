package com.autumn.swagger.annotation;

import java.lang.annotation.Documented;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * 
 * Api 头请求信息
 * 
 * @author 老码农 2018-12-27 11:09:50
 */
@Retention(RetentionPolicy.RUNTIME)
@Target({})
@Documented
public @interface ApiHeaderParameter {
	
	/**
	 * 字段 name
	 */
	public static final String FIELD_NAME = "name";

	/**
	 * 字段 dataType
	 */
	public static final String FIELD_DATA_TYPE = "dataType";

	/**
	 * 字段 description
	 */
	public static final String FIELD_DESCRIPTION = "description";

	/**
	 * 字段 required
	 */
	public static final String FIELD_REQUIRED = "required";

	/**
	 * 参数名称
	 * 
	 * @return
	 *
	 */
	String name();

	/**
	 * 类型
	 * 
	 * @return
	 *
	 */
	String dataType() default "string";

	/**
	 * 说明
	 * 
	 * @return
	 *
	 */
	String description() default "";

	/**
	 * 是否必须
	 * 
	 * @return
	 *
	 */
	boolean required() default false;
}
