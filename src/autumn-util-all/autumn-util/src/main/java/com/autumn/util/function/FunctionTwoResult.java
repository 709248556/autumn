package com.autumn.util.function;

import java.io.Serializable;

/**
 * 具有2个参数和具有返回值函数
 * 
 * @author 老码农
 *
 *         2017-12-05 16:35:47
 */
@FunctionalInterface
public interface FunctionTwoResult<T1, T2, TResult> extends Serializable {

	/**
	 * 应用
	 * 
	 * @param t1
	 *            参数1
	 * @param t2
	 *            参数2
	 * @return 2017-12-05 16:36:27
	 */
	TResult apply(T1 t1, T2 t2);
}
