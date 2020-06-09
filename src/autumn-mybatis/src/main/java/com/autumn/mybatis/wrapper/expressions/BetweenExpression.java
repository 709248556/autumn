package com.autumn.mybatis.wrapper.expressions;

/**
 * 介于表达式
 * 
 * @author 老码农
 *
 *         2017-10-26 18:22:34
 */
public class BetweenExpression extends ConstantExpression {

	private final Object secondValue;

	/**
	 *
	 * @param value
	 * 		第一个值
	 * @param secondValue
	 * 		第二个值
	 */
	BetweenExpression(Object value, Object secondValue) {
		super(value);
		//参数转换
		this.secondValue =secondValue;
	}

	/**
	 * 获取 to 值
	 * 
	 * @return
	 */
	public Object getSecondValue() {
		return secondValue;
	}

}
