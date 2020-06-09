package com.autumn.mybatis.wrapper.expressions;

import java.util.ArrayList;
import java.util.List;

import com.autumn.mybatis.wrapper.CriteriaOperatorEnum;
import com.autumn.mybatis.wrapper.LogicalOperatorEnum;
import com.autumn.mybatis.wrapper.clauses.AbstractCriteriaClause;
import com.autumn.mybatis.wrapper.clauses.BetweenCriteriaClause;
import com.autumn.mybatis.wrapper.clauses.LikeCriteriaClause;
import com.autumn.mybatis.wrapper.clauses.NoneCriteriaClause;
import com.autumn.mybatis.wrapper.clauses.SimpleCriteriaClause;
import com.autumn.exception.ExceptionUtils;

/**
 * 二元表达式
 * 
 * @author 老码农
 *
 *         2017-10-26 15:05:51
 */
public class BinaryCriteriaExpression extends AbstractCriteriaExpression {

	private final CriteriaOperatorEnum operater;
	private final ColumnExpression column;
	private final AbstractOperatorExpression rigth;

	BinaryCriteriaExpression(CriteriaOperatorEnum operater, AbstractOperatorExpression left,
			AbstractOperatorExpression rigth) {
		this.operater = operater;
		if (left instanceof ConstantExpression && rigth instanceof ConstantExpression) {
			ExceptionUtils.throwNotSupportException(
					"不支持" + left.getClass().getName() + " 与 " + rigth.getClass().getName() + " 进行二元运算。");
		}
		if (left instanceof ColumnExpression) {
			this.column = (ColumnExpression) left;
			this.rigth = rigth;
		} else {
			this.column = (ColumnExpression) rigth;
			this.rigth = left;
		}
	}

	private AbstractCriteriaClause createSection() {
		if (operater.equals(CriteriaOperatorEnum.LIKE) || operater.equals(CriteriaOperatorEnum.LEFT_LIKE)
				|| operater.equals(CriteriaOperatorEnum.RIGHT_LIKE)) {
			if (!(rigth instanceof ConstantExpression)) {
				ExceptionUtils.throwNotSupportException(
						"不支持" + column.getClass().getName() + " 与 " + rigth.getClass().getName() + " 进行二元运算。");
			}
			ConstantExpression constant = (ConstantExpression) this.rigth;
			return new LikeCriteriaClause(LogicalOperatorEnum.AND, this.column.getColumnName(), operater,
					constant.getValue());
		}
		if (operater.equals(CriteriaOperatorEnum.BETWEEN)) {
			if (!(rigth instanceof BetweenExpression)) {
				ExceptionUtils.throwNotSupportException(
						"不支持" + column.getClass().getName() + " 与 " + rigth.getClass().getName() + " 进行二元运算。");
			}
			BetweenExpression constant = (BetweenExpression) this.rigth;
			return new BetweenCriteriaClause(LogicalOperatorEnum.AND, this.column.getColumnName(), operater,
					constant.getValue(), constant.getSecondValue());
		}
		if (rigth instanceof NoneExpression) {
			return new NoneCriteriaClause(LogicalOperatorEnum.AND, this.column.getColumnName(), operater);
		}
		if (rigth instanceof ConstantExpression) {
			ConstantExpression constant = (ConstantExpression) this.rigth;
			return new SimpleCriteriaClause(LogicalOperatorEnum.AND, this.column.getColumnName(), operater,
					constant.getValue());
		}
		ColumnExpression col = (ColumnExpression) this.rigth;
		return new SimpleCriteriaClause(LogicalOperatorEnum.AND, this.column.getColumnName(), operater,
				col.getColumnName());
	}

	@Override
	public List<AbstractCriteriaClause> createSections() {
		List<AbstractCriteriaClause> items = new ArrayList<>();
		items.add(createSection());
		return items;
	}

}
