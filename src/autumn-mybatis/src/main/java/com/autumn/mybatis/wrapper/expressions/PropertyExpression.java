package com.autumn.mybatis.wrapper.expressions;

import com.autumn.mybatis.metadata.EntityColumn;
import com.autumn.mybatis.metadata.EntityTable;
import com.autumn.exception.ExceptionUtils;

/**
 * 属性表达式
 * 
 * @author 老码农
 *
 *         2017-10-26 15:18:19
 */
public class PropertyExpression extends ColumnExpression {

	private final EntityColumn column;

	/**
	 * 实例化
	 */
	PropertyExpression(EntityTable table, String propertyName) {
		this.column = table.getPropertyMap().get(propertyName);
		if (this.column == null) {
			ExceptionUtils.throwNotSupportException("属性 " + propertyName + " 不存在或不支持");
		}
		this.setColumnName(this.column.getColumnName());
	}

	/**
	 * 获取列
	 * 
	 * @return
	 */
	public EntityColumn getColumn() {
		return column;
	}

}
