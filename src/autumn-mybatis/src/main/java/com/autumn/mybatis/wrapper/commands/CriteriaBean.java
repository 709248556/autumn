package com.autumn.mybatis.wrapper.commands;


/**
 * 条件Bean
 * 
 * @author 老码农
 *
 *         2017-10-29 15:43:38
 */
public interface CriteriaBean  {

	/**
	 * 获取逻辑
	 * 
	 * @return
	 */
	String getLogic();

	/**
	 * 设置逻辑
	 * 
	 * @param logic
	 *            And|Or|Not
	 */
	void setLogic(String logic);

	/**
	 * 获取表达式
	 * 
	 * @return
	 */
	String getExpression();

	/**
	 * 设置表达式
	 * 
	 * @param expression
	 *            属性名或列名
	 */
	void setExpression(String expression);

	/**
	 * 获取运算符
	 * 
	 * @return
	 */
	String getOp();

	/**
	 * 设置运算符
	 * 
	 * @param op
	 */
	void setOp(String op);

	/**
	 * 获取条件值
	 * 
	 * @return
	 */
	Object getValue();

	/**
	 * 设置条件值
	 * 
	 * @param value
	 */
	void setValue(Object value);

	/**
	 * 获取第二个值
	 * 
	 * @return
	 */
	Object getSecondValue();

	/**
	 * 设置第二个值
	 * 
	 * @param secondValue
	 *            值
	 */
	void setSecondValue(Object secondValue);
}
