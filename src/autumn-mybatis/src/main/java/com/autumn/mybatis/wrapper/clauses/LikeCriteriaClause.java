package com.autumn.mybatis.wrapper.clauses;

import com.autumn.mybatis.wrapper.CriteriaOperatorEnum;
import com.autumn.mybatis.wrapper.LogicalOperatorEnum;

/**
 * 
 * @author 老码农
 *
 *         2017-10-26 11:30:14
 */
public class LikeCriteriaClause extends AbstractCriteriaClause {

	/**
	 * 
	 */
	private static final long serialVersionUID = 8824515702014164232L;
	
	/**
	 * 
	 * @param logical
	 * @param expression
	 * @param operator
	 * @param value
	 */
	public LikeCriteriaClause(LogicalOperatorEnum logical, String expression, CriteriaOperatorEnum operator, Object value) {
		super(logical, expression, operator);
		this.setValue(value);
	}

	@Override
	public String builderExpression(String paramName) {
		StringBuilder builer = new StringBuilder();		
		builer.append(this.getExpression());	
		builer.append(" ");
		builer.append(this.getOperator().getOperator());
		builer.append(" CONCAT(");
		if (this.getOperator().equals(CriteriaOperatorEnum.LIKE)) {
			builer.append("'%',#{");
			builer.append(paramName);
			builer.append(".value},'%')");
		} else if (this.getOperator().equals(CriteriaOperatorEnum.LEFT_LIKE)) {
			builer.append("#{");
			builer.append(paramName);
			builer.append(".value},'%')");
		} else {
			builer.append("'%',#{");
			builer.append(paramName);
			builer.append(".value})");
		}
		return builer.toString();
	}

}
