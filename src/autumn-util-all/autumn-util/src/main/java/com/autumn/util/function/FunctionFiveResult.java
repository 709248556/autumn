package com.autumn.util.function;

import java.io.Serializable;

/**
 * 具有5个参数和具有返回值函数
 * 
 * @author 老码农
 *
 *         2017-12-05 16:38:06
 */
@FunctionalInterface
public interface FunctionFiveResult<T1, T2, T3, T4, T5, TResult> extends Serializable {

	/**
	 * 应用
	 * 
	 * @param t1
	 *            参数1
	 * @param t2
	 *            参数2
	 * @param t3
	 *            参数3
	 * @param t4
	 *            参数4
	 * @param t5
	 *            参数5
	 * @return 2017-12-05 16:36:27
	 */
	TResult apply(T1 t1, T2 t2, T3 t3, T4 t4, T5 t5);
}