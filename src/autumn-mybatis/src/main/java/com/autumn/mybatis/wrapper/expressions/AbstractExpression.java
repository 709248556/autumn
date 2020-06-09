package com.autumn.mybatis.wrapper.expressions;

import java.util.List;

import com.autumn.mybatis.metadata.EntityTable;
import com.autumn.mybatis.wrapper.commands.CriteriaBean;
import com.autumn.mybatis.wrapper.CriteriaBeanParser;
import com.autumn.mybatis.wrapper.CriteriaOperatorEnum;
import com.autumn.mybatis.wrapper.LogicalOperatorEnum;

/**
 * 表达式抽象
 *
 * @author 老码农
 *         <p>
 *         2017-10-26 14:56:54
 */
public abstract class AbstractExpression {

	/**
	 * 解析 Bean 生成条件表达式
	 *
	 * @param entityTable
	 *            表
	 * @param criteriaBeans
	 *            条件 Bean
	 * @return
	 */
	public static <E extends CriteriaBean> AbstractCriteriaExpression parserBean(EntityTable entityTable,
			List<E> criteriaBeans) {
		CriteriaBeanParser<E> beanParser = new CriteriaBeanParser<>(entityTable, criteriaBeans);
		return beanParser.parser();
	}

	/**
	 * 逻辑
	 *
	 * @param operator
	 * @param left
	 * @param rigth
	 * @return
	 */
	public static LogicalCriteriaExpression logical(LogicalOperatorEnum operator, AbstractCriteriaExpression left,
			AbstractCriteriaExpression rigth) {
		return new LogicalCriteriaExpression(operator, left, rigth);
	}

	/**
	 * 连接
	 *
	 * @param left
	 * @param rigth
	 * @return
	 */
	public static LogicalCriteriaExpression and(AbstractCriteriaExpression left, AbstractCriteriaExpression rigth) {
		return logical(LogicalOperatorEnum.AND, left, rigth);
	}

	/**
	 * 连接
	 *
	 * @param left
	 * @param rigth
	 * @return
	 */
	public static LogicalCriteriaExpression or(AbstractCriteriaExpression left, AbstractCriteriaExpression rigth) {
		return logical(LogicalOperatorEnum.OR, left, rigth);
	}

	/**
	 * 属性
	 *
	 * @param table
	 * @param propertyName
	 * @return
	 */
	public static PropertyExpression property(EntityTable table, String propertyName) {
		return new PropertyExpression(table, propertyName);
	}

	/**
	 * 列
	 *
	 * @param columnName
	 *            名称
	 * @return
	 */
	public static ColumnExpression column(String columnName) {
		return new ColumnExpression(columnName);
	}

	/**
	 * 常量
	 *
	 * @param value
	 *            名称
	 * @return
	 */
	public static ConstantExpression constant(Object value) {
		return new ConstantExpression(value);
	}

	/**
	 * between
	 *
	 * @param value
	 *            第一个值
	 * @param secondValue
	 *            第二个值
	 * @return
	 */
	public static BetweenExpression between(Object value, Object secondValue) {
		return new BetweenExpression(value, secondValue);
	}

	/**
	 * 二元
	 *
	 * @param left
	 *            左
	 * @param rigth
	 *            右
	 * @return
	 */
	public static BinaryCriteriaExpression binary(CriteriaOperatorEnum operator, AbstractOperatorExpression left,
			AbstractOperatorExpression rigth) {
		return new BinaryCriteriaExpression(operator, left, rigth);
	}

	/**
	 * 空值表达式
	 *
	 * @param columnExpression
	 *            列表达式
	 * @return
	 */
	public static BinaryCriteriaExpression isNull(ColumnExpression columnExpression) {
		return binary(CriteriaOperatorEnum.IS_NULL, columnExpression, none());
	}

	/**
	 * 非空值表达式
	 *
	 * @param columnExpression
	 *            列表达式
	 * @return
	 */
	public static BinaryCriteriaExpression isNotNull(ColumnExpression columnExpression) {
		return binary(CriteriaOperatorEnum.IS_NOT_NULL, columnExpression, none());
	}

	/**
	 * 空表达式
	 * 
	 * @return
	 */
	public static NoneExpression none() {
		return new NoneExpression();
	}

	/**
	 * 等于
	 *
	 * @param left
	 * @param rigth
	 * @return
	 */
	public static BinaryCriteriaExpression eq(AbstractOperatorExpression left, AbstractOperatorExpression rigth) {
		return binary(CriteriaOperatorEnum.EQ, left, rigth);
	}

	/**
	 * 不等于
	 *
	 * @param left
	 * @param rigth
	 * @return
	 */
	public static BinaryCriteriaExpression notEq(AbstractOperatorExpression left, AbstractOperatorExpression rigth) {
		return binary(CriteriaOperatorEnum.NOT_EQ, left, rigth);
	}

	/**
	 * 一元表达式
	 * 
	 * @param criteriaOperator 条件运算符
	 * @param value 值
	 * @return
	 */
	public static UnaryCriteriaExpression unary(CriteriaOperatorEnum criteriaOperator, Object value) {
		return new UnaryCriteriaExpression(criteriaOperator, value);
	}

	/**
	 * 组表达式
	 *
	 * @param content
	 *            内空
	 * @return
	 */
	public static CriteriaGroupExpression group(AbstractCriteriaExpression content) {
		return new CriteriaGroupExpression(content);
	}
}
