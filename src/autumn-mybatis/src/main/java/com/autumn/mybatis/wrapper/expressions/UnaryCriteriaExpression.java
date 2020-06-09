package com.autumn.mybatis.wrapper.expressions;

import java.util.ArrayList;
import java.util.List;

import com.autumn.mybatis.wrapper.CriteriaOperatorEnum;
import com.autumn.mybatis.wrapper.LogicalOperatorEnum;
import com.autumn.mybatis.wrapper.clauses.AbstractCriteriaClause;
import com.autumn.mybatis.wrapper.clauses.SimpleCriteriaClause;

/**
 * 一元运算符
 * 
 * @author 老码农 2019-06-04 22:54:56
 */
public class UnaryCriteriaExpression extends AbstractCriteriaExpression {

	private final CriteriaOperatorEnum operater;
	private final Object value;

	/**
	 * 
	 * @param operater
	 * @param value
	 */
	UnaryCriteriaExpression(CriteriaOperatorEnum operater, Object value) {
		this.operater = operater;
		this.value = value;
	}

	@Override
	public List<AbstractCriteriaClause> createSections() {
		List<AbstractCriteriaClause> items = new ArrayList<>();
		items.add(new SimpleCriteriaClause(LogicalOperatorEnum.AND, this.value.toString(), this.operater, null));
		return items;
	}

}
