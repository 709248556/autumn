package com.autumn.util.tuple;

/**
 * 9个项目
 * 
 * @author 老码农
 *
 *         2017-09-30 14:09:35
 */
public class TupleNine<T1, T2, T3, T4, T5, T6, T7, T8, T9> extends TupleEight<T1, T2, T3, T4, T5, T6, T7, T8> {

	/**
	 * 
	 */
	private static final long serialVersionUID = 6493456668666507932L;
	private final T9 item9;

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
	 * @param item9
	 */
	public TupleNine(T1 item1, T2 item2, T3 item3, T4 item4, T5 item5, T6 item6, T7 item7, T8 item8, T9 item9) {
		super(item1, item2, item3, item4, item5, item6, item7, item8);
		this.item9 = item9;
	}

	/**
	 * 获取项目9
	 * 
	 * @return
	 * @author 老码农 2017-09-30 14:11:12
	 */
	public T9 getItem9() {
		return item9;
	}

}
