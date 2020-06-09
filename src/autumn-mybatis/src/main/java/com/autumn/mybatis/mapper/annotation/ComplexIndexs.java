package com.autumn.mybatis.mapper.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * 复合索引
 * <p>
 * 在表上使用
 * </P>
 * 
 * @author 老码农
 *         <p>
 *         Description
 *         </p>
 * @date 2018-01-21 00:06:40
 */
@Target({ ElementType.TYPE })
@Retention(RetentionPolicy.RUNTIME)
public @interface ComplexIndexs {

	/**
	 * 索引集合
	 * 
	 * @return
	 */
	ComplexIndex[] indexs();
}
