package com.autumn.mybatis.wrapper.expressions;

/**
 * 列表达式
 * 
 * @author 老码农
 *
 *         2017-10-26 16:07:14
 */
public class ColumnExpression extends AbstractOperatorExpression {

	private String columnName;

	/**
	 * 
	 * @param columnName
	 *            列名称
	 */
	ColumnExpression(String columnName) {
		this.columnName = columnName;
	}

	/**
	 * 
	 */
	ColumnExpression() {

	}

	/**
	 * 获取列名称
	 * 
	 * @return
	 */
	public final String getColumnName() {
		return columnName;
	}

	/**
	 * 
	 * @param columnName
	 */
	protected void setColumnName(String columnName) {
		this.columnName = columnName;
	}
}
