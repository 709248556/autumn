package com.autumn.mybatis.wrapper;

import com.autumn.mybatis.metadata.EntityTable;
import com.autumn.mybatis.wrapper.expressions.AbstractCriteriaExpression;
import com.autumn.mybatis.wrapper.expressions.ColumnExpression;
import com.autumn.util.function.FunctionTwoAction;

import java.util.function.Function;

/**
 * 公共条件
 *
 * @param <TSourceWrapper> 源Wrapper类型
 * @param <TNameFunc>      名称或函数
 */
public class CommonCriteria<TSourceWrapper, TNameFunc> extends AbstractCriteria<CommonCriteria<TSourceWrapper, TNameFunc>, TNameFunc>
        implements OfWrapper<TSourceWrapper> {

    private final TSourceWrapper sourceWrapper;
    private final FunctionTwoAction<LogicalOperatorEnum, AbstractCriteriaExpression> criteriaAction;

    /**
     * 实例化 公共条件
     *
     * @param sourceWrapper            源Wrapper
     * @param entityTable
     * @param columnExpressionFunction
     * @param criteriaAction
     */
    CommonCriteria(TSourceWrapper sourceWrapper, EntityTable entityTable,
                   Function<TNameFunc, ColumnExpression> columnExpressionFunction,
                   FunctionTwoAction<LogicalOperatorEnum, AbstractCriteriaExpression> criteriaAction) {
        super(entityTable, columnExpressionFunction);
        this.sourceWrapper = sourceWrapper;
        this.criteriaAction = criteriaAction;
    }

    @Override
    public CommonCriteria<TSourceWrapper, TNameFunc> criteria(LogicalOperatorEnum logicalOperater, AbstractCriteriaExpression expression) {
        criteriaAction.apply(logicalOperater, expression);
        return this.returnThis();
    }

    @Override
    public TSourceWrapper of() {
        return this.sourceWrapper;
    }

}
