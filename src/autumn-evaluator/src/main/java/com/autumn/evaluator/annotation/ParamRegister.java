package com.autumn.evaluator.annotation;

import java.lang.annotation.Repeatable;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import static java.lang.annotation.ElementType.*;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

/**
 * 参数注册
 * 
 * @author 老码农
 *
 *         2018-04-25 12:24:33
 */
@Target({ TYPE })
@Retention(RUNTIME)
@Repeatable(ParamRegisters.class)
public @interface ParamRegister {

	/**
	 * 参数顺序(从1开始)
	 * 
	 * @return
	 *
	 */
	int order();

	/**
	 * 参数名称
	 * 
	 * @return
	 *
	 */
	String name();

	/**
	 * 说明
	 * 
	 * @return
	 *
	 */
	String caption();

	/**
	 * 参数类型
	 * 
	 * {@link com.autumn.evaluator.VariantType}
	 * 
	 * @return
	 *
	 */
	int paramType();

	/**
	 * 默认值
	 * 
	 * @return
	 *
	 */
	String defaultValue() default "";
}
