package com.autumn.util.tuple;

/**
 * 六个项目
 * 
 * @author 老码农
 *
 *         2017-09-30 14:01:36
 */
public class TupleSix<T1, T2, T3, T4, T5, T6> extends TupleFive<T1, T2, T3, T4, T5> {

	/**
	 * 
	 */
	private static final long serialVersionUID = 4006029604988147164L;
	private final T6 item6;

	/**
	 * 
	 * @param item1
	 * @param item2
	 * @param item3
	 * @param item4
	 * @param item5
	 * @param item6
	 */
	public TupleSix(T1 item1, T2 item2, T3 item3, T4 item4, T5 item5, T6 item6) {
		super(item1, item2, item3, item4, item5);
		this.item6 = item6;
	}

	/**
	 * 获取项目6
	 * 
	 * @return
	 * @author 老码农 2017-09-30 14:02:55
	 */
	public T6 getItem6() {
		return item6;
	}

}
