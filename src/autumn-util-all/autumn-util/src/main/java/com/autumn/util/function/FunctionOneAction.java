package com.autumn.util.function;

import java.io.Serializable;

/**
 * 具有1个参数无返回值
 * 
 * @author 老码农
 *
 *         2017-12-05 16:30:25
 */
@FunctionalInterface
public interface FunctionOneAction<T> extends Serializable {

	/**
	 * 应用
	 * 
	 * @param t
	 *            参数
	 * @return 2017-12-05 16:31:16
	 */
	void apply(T t);

}
