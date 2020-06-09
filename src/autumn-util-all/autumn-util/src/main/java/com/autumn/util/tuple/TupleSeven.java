package com.autumn.util.tuple;

/**
 * 七个项目
 * 
 * @author 老码农
 *
 *         2017-09-30 14:03:39
 */
public class TupleSeven<T1, T2, T3, T4, T5, T6, T7> extends TupleSix<T1, T2, T3, T4, T5, T6> {

	/**
	 * 
	 */
	private static final long serialVersionUID = -1070660891255705503L;
	private final T7 item7;

	/**
	 * 
	 * @param item1
	 * @param item2
	 * @param item3
	 * @param item4
	 * @param item5
	 * @param item6
	 * @param item7
	 */
	public TupleSeven(T1 item1, T2 item2, T3 item3, T4 item4, T5 item5, T6 item6, T7 item7) {
		super(item1, item2, item3, item4, item5, item6);
		this.item7 = item7;
	}

	/**
	 * 获取项目7
	 * 
	 * @return
	 * @author 老码农 2017-09-30 14:04:35
	 */
	public T7 getItem7() {
		return item7;
	}
}
