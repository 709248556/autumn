package com.autumn.util.reflect;

import java.util.List;

/**
 * 
 * @author 老码农
 *
 *         2017-10-31 17:18:21
 */
public abstract class AbstractReflectInfo<T1, T2, T3> {

	public AbstractReflectInfo() {
		List<Class<?>> items = ReflectUtils.getGenericActualArgumentsType(this.getClass(), AbstractReflectInfo.class);
		for (int i = 0; i < items.size(); i++) {
			System.out.println(i + " -> " + items.get(i));
		}
	}

}
