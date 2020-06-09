package com.autumn.evaluator;

/**
 * 变量类型
 */
public class VariantType {

	/**
	 * 整数类型
	 */
	public static final int INTEGER = 1;

	/**
	 * 金额类型
	 */
	public static final int DECIMAL = 2;

	/**
	 * 浮点类型
	 */
	public static final int DOUBLE = 4;

	/**
	 * 布尔类型
	 */
	public static final int BOOLEAN = 8;

	/**
	 * 字符类型
	 */
	public static final int STRING = 16;

	/**
	 * 日期时间类型
	 */
	public static final int DATETIME = 32;

	/**
	 * UUID类型
	 */
	public static final int UUID = 64;

	/**
	 * 数组类型
	 */
	public static final int ARRAY = 128;

	/**
	 * null 类型
	 */
	public static final int NULL = 255;

	/**
	 * 数字类型
	 */
	public static final int NUMBER = INTEGER | DECIMAL | DOUBLE;

	private static boolean isAllowValue(int value, int allowValue) {
		return ((value & allowValue) == value);
	}

	public static boolean isAllowInteger(int value) {
		return isAllowValue(value, VariantType.INTEGER);
	}

	public static boolean isAllowNumber(int value) {
		return isAllowValue(value, VariantType.NUMBER);
	}
}
