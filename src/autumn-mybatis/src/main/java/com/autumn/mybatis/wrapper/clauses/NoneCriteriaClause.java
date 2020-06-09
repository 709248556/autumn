package com.autumn.mybatis.wrapper.clauses;

import com.autumn.mybatis.wrapper.CriteriaOperatorEnum;
import com.autumn.mybatis.wrapper.LogicalOperatorEnum;

/**
 * 
 * @author 老码农
 * 2019-06-04 21:47:58
 */
public class NoneCriteriaClause extends AbstractCriteriaClause {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * 
	 * @param logical
	 * @param expression
	 * @param operator
	 */
	public NoneCriteriaClause(LogicalOperatorEnum logical, String expression, CriteriaOperatorEnum operator) {
		super(logical, expression, operator);		
	}

	@Override
	public String builderExpression(String paramName) {
		return String.format("%s %s", this.getExpression(), this.getOperator().getOperator());
	}

}
