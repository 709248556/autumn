package com.autumn.mybatis.wrapper;

import com.autumn.mybatis.wrapper.commands.WrapperCommand;
import com.autumn.mybatis.wrapper.expressions.ColumnExpression;
import com.autumn.exception.ExceptionUtils;
import com.autumn.util.function.FunctionOneResult;

/**
 * Lambda 条件表达式
 *
 * @param <TSourceWrapper> 子级表达式
 * @param <TEntity>        实体类型
 */
public class LambdaCriteriaWrapper<TSourceWrapper, TEntity>
        extends AbstractWrapper<LambdaCriteriaWrapper<TSourceWrapper, TEntity>, FunctionOneResult<TEntity, ?>>
        implements OfWrapper<TSourceWrapper> {


    private final Class<TEntity> entityClass;
    private final TSourceWrapper sourceWrapper;

    /**
     * 获取实体类型
     *
     * @return
     */
    public Class<TEntity> getEntityClass() {
        return entityClass;
    }

    /**
     * 实例化 SimpleLambdaWrapper 新实例
     *
     * @param sourceWrapper  来源
     * @param entityClass    实体类型
     * @param wrapperCommand 包装器命令
     */
    LambdaCriteriaWrapper(TSourceWrapper sourceWrapper, Class<TEntity> entityClass, WrapperCommand wrapperCommand) {
        super(ExceptionUtils.checkNotNull(entityClass, "entityClass"), wrapperCommand);
        this.sourceWrapper = sourceWrapper;
        this.entityClass = entityClass;
    }

    @Override
    protected ColumnExpression createColumnExpression(FunctionOneResult<TEntity, ?> entityWrapperFunction) {
        return this.columnByLambda(entityWrapperFunction);
    }

    @Override
    public TSourceWrapper of() {
        return this.sourceWrapper;
    }
}
