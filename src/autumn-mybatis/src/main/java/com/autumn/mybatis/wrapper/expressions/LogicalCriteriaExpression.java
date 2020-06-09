package com.autumn.mybatis.wrapper.expressions;

import java.util.ArrayList;
import java.util.List;

import com.autumn.mybatis.wrapper.LogicalOperatorEnum;
import com.autumn.mybatis.wrapper.clauses.AbstractCriteriaClause;

/**
 * 逻辑表达式
 * @author 老码农
 *
 *         2017-10-26 17:45:00
 */
public class LogicalCriteriaExpression extends AbstractCriteriaExpression {

	private final LogicalOperatorEnum operater;
	private final AbstractCriteriaExpression left;
	private final AbstractCriteriaExpression rigth;

	LogicalCriteriaExpression(LogicalOperatorEnum operater, AbstractCriteriaExpression left, AbstractCriteriaExpression rigth) {
		this.operater = operater;
		this.left = left;
		this.rigth = rigth;
	}

	@Override
	public List<AbstractCriteriaClause> createSections() {
		List<AbstractCriteriaClause> leftSections = left.createSections();
		List<AbstractCriteriaClause> rigthSections = rigth.createSections();
		List<AbstractCriteriaClause> items = new ArrayList<>();
		if (leftSections != null && leftSections.size() > 0) {
			setBrackets(leftSections);
			items.addAll(leftSections);
		}
		if (rigthSections != null && rigthSections.size() > 0) {
			setBrackets(rigthSections);
			AbstractCriteriaClause session = rigthSections.get(0);
			session.setLogical(operater);
			items.addAll(rigthSections);
		}
		return items;
	}

	/**
	 * 设置括号
	 * @param sections
	 */
	private void setBrackets(List<AbstractCriteriaClause> sections) {
		if (sections != null && sections.size() > 0) {
			AbstractCriteriaClause session = sections.get(0);
			session.addLeftBrackets();
			session = sections.get(sections.size() - 1);
			session.addRigthBrackets();
		}
	}

}
