package com.autumn.util.tuple;

/**
 * 八个项目
 * 
 * @author 老码农
 *
 *         2017-09-30 14:05:30
 */
public class TupleEight<T1, T2, T3, T4, T5, T6, T7, T8> extends TupleSeven<T1, T2, T3, T4, T5, T6, T7> {

	/**
	 * 
	 */
	private static final long serialVersionUID = -7366913341636392773L;
	private final T8 item8;

	/**
	 * 
	 * @param item1
	 * @param item2
	 * @param item3
	 * @param item4
	 * @param item5
	 * @param item6
	 * @param item7
	 * @param item8
	 */
	public TupleEight(T1 item1, T2 item2, T3 item3, T4 item4, T5 item5, T6 item6, T7 item7, T8 item8) {
		super(item1, item2, item3, item4, item5, item6, item7);
		this.item8 = item8;
	}

	/**
	 * 获取项目8
	 * 
	 * @return
	 * @author 老码农 2017-09-30 14:08:59
	 */
	public T8 getItem8() {
		return item8;
	}

}
