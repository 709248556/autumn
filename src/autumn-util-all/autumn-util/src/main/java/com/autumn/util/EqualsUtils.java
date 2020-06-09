package com.autumn.util;

import java.util.HashMap;
import java.util.Map;

/**
 * 比较帮助
 * 
 * @author 老码农
 *
 *         2017-09-30 10:04:30
 */
public class EqualsUtils {

	private static final Map<Class<?>, Class<?>> PRIMITIVE_TYPE_MAP = new HashMap<>();

	static {
		PRIMITIVE_TYPE_MAP.put(Byte.TYPE, Byte.class);
		PRIMITIVE_TYPE_MAP.put(Short.TYPE, Short.class);
		PRIMITIVE_TYPE_MAP.put(Character.TYPE, Character.class);
		PRIMITIVE_TYPE_MAP.put(Integer.TYPE, Integer.class);
		PRIMITIVE_TYPE_MAP.put(Long.TYPE, Long.class);
		PRIMITIVE_TYPE_MAP.put(Float.TYPE, Float.class);
		PRIMITIVE_TYPE_MAP.put(Double.TYPE, Double.class);
		PRIMITIVE_TYPE_MAP.put(Boolean.TYPE, Boolean.class);
		PRIMITIVE_TYPE_MAP.put(Void.TYPE, Void.class);
	}

	/**
	 * 比较两个对象是否相等
	 * 
	 * @param left
	 *            左对象
	 * @param rigth
	 *            右对象
	 * @return
	 * @author 老码农 2017-09-30 10:05:13
	 */
	public static boolean equals(Object left, Object rigth) {
		if (left == null && rigth == null) {
			return true;
		}
		if (left != null && rigth != null) {
			return left.equals(rigth);
		}
		return false;
	}
	
	/**
	 * 数组值比较
	 * 
	 * @param leftArray
	 *            左数组
	 * @param rigthArray
	 *            右数组
	 * @return
	 * @author 老码农 2017-10-10 10:27:50
	 */
	public static <T> boolean arrayValueEquals(T[] leftArray, T[] rigthArray) {
		if (leftArray == null && rigthArray == null) {
			return true;
		}
		if (leftArray != null && rigthArray != null) {
			if (leftArray.length != rigthArray.length) {
				return false;
			}
			for (int i = 0; i < leftArray.length; i++) {
				if (!EqualsUtils.equals(leftArray[i], rigthArray[i])) {
					return false;
				}
			}
			return true;
		} else {
			if (leftArray == null && rigthArray.length == 0) {
				return true;
			}
			return rigthArray == null && leftArray.length == 0;
		}
	}

	/**
	 * 比较原始类型,即类类型与原始类型是否相等
	 * 
	 * @param leftClass
	 *            左类型
	 * @param rightClass
	 *            右类型
	 * @return
	 */
	public static boolean equalsPrimitiveClass(Class<?> leftClass, Class<?> rightClass) {
		if (leftClass.isPrimitive()) {
			return rightClass.equals(PRIMITIVE_TYPE_MAP.get(leftClass));
		}
		//右边的总是包装类,这里不会执行
		if (rightClass.isPrimitive()) {
			return leftClass.equals(PRIMITIVE_TYPE_MAP.get(rightClass));
		}
		return leftClass.equals(rightClass);
	}

	/**
	 * 比较两个对象是否相等
	 *
	 * @param leftObject
	 * @param rigthObject
	 * @return
	 */
	public static boolean equalsObject(Object leftObject, Object rigthObject) {
		if (leftObject == null && rigthObject == null) {
			return true;
		}
		if (leftObject != null && rigthObject != null) {
			return leftObject.equals(rigthObject);
		}
		return false;
	}
	
}
