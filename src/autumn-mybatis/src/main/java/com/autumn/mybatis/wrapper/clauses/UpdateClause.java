package com.autumn.mybatis.wrapper.clauses;

/**
 * 更新条款
 * 
 * @author 老码农
 *
 *         2017-10-30 18:33:43
 */
public class UpdateClause {
	private final String columnName;
	private final Object value;

	/**
	 * 实例化
	 * 
	 * @param columnName
	 *            列名称
	 * @param value
	 *            值
	 */
	public UpdateClause(String columnName, Object value) {
		this.columnName = columnName;
		this.value = value;
	}

	/**
	 * 获取列名称
	 * 
	 * @return
	 */
	public String getColumnName() {
		return columnName;
	}

	/**
	 * 获取值
	 * 
	 * @return
	 */
	public Object getValue() {
		return value;
	}
}
