package com.autumn.mybatis.wrapper;

import com.autumn.mybatis.metadata.EntityTable;
import com.autumn.mybatis.wrapper.expressions.AbstractCriteriaExpression;
import com.autumn.mybatis.wrapper.expressions.AbstractExpression;
import com.autumn.mybatis.wrapper.expressions.ColumnExpression;
import com.autumn.exception.ExceptionUtils;

import java.util.function.Function;

/**
 * 条件组
 *
 * @param <TChildren> 子级类型
 * @param <TNameFunc> 名称函数类型
 * @author 老码农
 * 2019-06-11 10:19:18
 */
public class CriteriaGroup<TChildren extends AbstractCriteria<TChildren, TNameFunc>, TNameFunc> extends AbstractCriteria<CriteriaGroup<TChildren, TNameFunc>, TNameFunc> {

    private AbstractCriteriaExpression expression;

    /**
     * 实例化
     *
     * @param entityTable              实体表
     * @param columnExpressionFunction 列表达式
     */
    CriteriaGroup(EntityTable entityTable,
                  Function<TNameFunc, ColumnExpression> columnExpressionFunction) {
        super(entityTable, columnExpressionFunction);
        this.expression = null;
    }

    @Override
    public CriteriaGroup<TChildren, TNameFunc> criteria(LogicalOperatorEnum logicalOperater, AbstractCriteriaExpression expression) {
        ExceptionUtils.checkNotNull(logicalOperater, "logicalOperater");
        ExceptionUtils.checkNotNull(expression, "expression");
        if (this.expression == null) {
            this.expression = expression;
        } else {
            this.expression = AbstractExpression.logical(logicalOperater, this.expression, expression);
        }
        return this.returnThis();
    }

    /**
     * 添加条件
     *
     * @return
     */
    TChildren addCriteria(TChildren children, LogicalOperatorEnum operater) {
        if (this.expression != null) {
            children.criteria(operater, this.expression);
        }
        this.expression = null;
        return children;
    }

}
