package com.autumn.util.tuple;

/**
 * 五个项目
 * 
 * @author 老码农
 *
 *         2017-09-30 13:55:52
 */
public class TupleFive<T1, T2, T3, T4, T5> extends TupleFour<T1, T2, T3, T4> {

	private final T5 item5;

	/**
	 * 
	 */
	private static final long serialVersionUID = -2413503833222832933L;

	/**
	 * 
	 * @param item1
	 * @param item2
	 * @param item3
	 * @param item4
	 * @param item5
	 */
	public TupleFive(T1 item1, T2 item2, T3 item3, T4 item4, T5 item5) {
		super(item1, item2, item3, item4);
		this.item5 = item5;
	}

	/**
	 * 获取项目5
	 * 
	 * @return
	 * @author 老码农 2017-09-30 14:00:55
	 */
	public T5 getItem5() {
		return item5;
	}
}
