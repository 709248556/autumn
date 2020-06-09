package com.autumn.domain.values;

import lombok.ToString;

import java.io.Serializable;

/**
 * 值类型抽象
 * 
 * @author 老码农
 *         <p>
 *         值类型抽象
 *         </p>
 * @date 2017-11-09 23:10:26
 */
@ToString(callSuper = true)
public abstract class AbstractValue  implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 43993153509957650L;

	@Override
	public int hashCode() {
		// TODO 未完成
		return super.hashCode();
	}

	@Override
	public boolean equals(Object obj) {
		if (obj == null) {
			return false;
		}
		// TODO 未完成
		return super.equals(obj);
	}
}
