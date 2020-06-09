package com.autumn.mybatis.wrapper.clauses;

import com.autumn.mybatis.wrapper.CriteriaOperatorEnum;
import com.autumn.mybatis.wrapper.LogicalOperatorEnum;

/**
 * between 条件
 * @author 老码农
 *
 * 2017-12-06 13:23:59
 */
public class BetweenCriteriaClause extends AbstractCriteriaClause {
    /**
	 * 
	 */
	private static final long serialVersionUID = 7417402581264083332L;

	/**
     *
     * @param logical
     * @param expression
     * @param operator
     * @param value
     * @param secondValue
     */
    public BetweenCriteriaClause(LogicalOperatorEnum logical, String expression, CriteriaOperatorEnum operator, Object value ,Object secondValue) {
        super(logical, expression, operator);
        this.setValue(value);
        this.setSecondValue(secondValue);
    }

    @Override
    public String builderExpression(String paramName) {
        return null;
    }
}
