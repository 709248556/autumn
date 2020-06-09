package com.autumn.mybatis.wrapper.clauses;

import com.autumn.mybatis.wrapper.CriteriaOperatorEnum;
import com.autumn.mybatis.wrapper.LogicalOperatorEnum;
import com.autumn.exception.ExceptionUtils;

/**
 * 简单的条件表达式
 * 
 * @author 老码农
 *
 *         2017-10-26 11:04:54
 */
public class SimpleCriteriaClause extends AbstractCriteriaClause {

	/**
	 * 
	 */
	private static final long serialVersionUID = 5640272924606191438L;

	/**
	 * 简单表达式
	 * 
	 * @param logical
	 * @param expression
	 * @param operator
	 * @param value
	 */
	public SimpleCriteriaClause(LogicalOperatorEnum logical, String expression, CriteriaOperatorEnum operator,
			Object value) {
		super(logical, expression, operator);
		if (!logical.isSimple()) {
			ExceptionUtils.throwNotSupportException("表达式不支持 " + logical.getOperator() + " 运算符");
		}
		if (!operator.isSimple()) {
			ExceptionUtils.throwNotSupportException("表达式不支持 " + operator.getOperator() + " 运算符");
		}
		this.setValue(value);
	}

	@Override
	public String builderExpression(String paramName) {
		return String.format("%s %s #{%s.value}", this.getExpression(), this.getOperator().getOperator(), paramName);
	}

}
