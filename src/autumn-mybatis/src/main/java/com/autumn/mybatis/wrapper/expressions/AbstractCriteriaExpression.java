package com.autumn.mybatis.wrapper.expressions;

import java.util.List;

import com.autumn.mybatis.wrapper.clauses.AbstractCriteriaClause;

/**
 * 条件表达式
 * 
 * @author 老码农
 *
 *         2017-10-26 14:59:59
 */
public abstract class AbstractCriteriaExpression extends AbstractExpression {

	/**
	 * 创建段
	 * 
	 * @return
	 */
	public abstract List<AbstractCriteriaClause> createSections();
	
}
