package com.autumn.mybatis.mapper.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * 表引擎,对于部份数据库可指定如 Mysql 可指定 MyISAM
 * 
 * <pre>
 * 生成脚本时有效
 * </pre>
 * 
 * @author 老码农
 *
 *         2018-04-11 16:37:41
 */
@Target({ ElementType.TYPE })
@Retention(RetentionPolicy.RUNTIME)
public @interface TableEngine {

	/**
	 * <pre>
	 * MySql 的 InnoDB\MyISAM\MRG_MYISAM\MEMORY 等
	 * </pre>
	 * 
	 * @return
	 *
	 */
	String value() default "";
}
