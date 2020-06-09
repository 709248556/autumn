package com.autumn.util.tuple;

import java.io.Serializable;

/**
 * 单个
 * 
 * @author 老码农
 *
 *         2017-09-30 13:45:30
 */
public class Tuple<T1> implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -4340953436576826848L;
	private final T1 item1;

	/**
	 * 实例化 Unit 类
	 * 
	 * @param item1
	 *            项目1
	 */
	public Tuple(T1 item1) {
		this.item1 = item1;
	}

	/**
	 * 获取项目1
	 * 
	 * @return
	 * @author 老码农 2017-09-30 13:47:16
	 */
	public T1 getItem1() {
		return item1;
	}

}
