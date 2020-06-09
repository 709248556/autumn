package com.autumn.mybatis.wrapper.expressions;

import java.util.List;

import com.autumn.mybatis.wrapper.clauses.AbstractCriteriaClause;

/**
 * 组表达
 * 
 * @author 老码农
 *
 *         2017-10-26 15:00:43
 */
public class CriteriaGroupExpression extends AbstractCriteriaExpression {

	private final AbstractCriteriaExpression content;

	CriteriaGroupExpression(AbstractCriteriaExpression content) {
		this.content = content;
	}

	@Override
	public List<AbstractCriteriaClause> createSections() {
		List<AbstractCriteriaClause> items = content.createSections();
		if (items != null && items.size() > 0) {
			AbstractCriteriaClause section = items.get(0);
			section.addLeftBrackets();
			section = items.get(items.size() - 1);
			section.addRigthBrackets();
		}
		return items;
	}

}
