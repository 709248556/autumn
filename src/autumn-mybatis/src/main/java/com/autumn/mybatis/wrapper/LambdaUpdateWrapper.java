package com.autumn.mybatis.wrapper;

import com.autumn.mybatis.wrapper.commands.UpdateWrapperCommand;
import com.autumn.mybatis.wrapper.expressions.ColumnExpression;
import com.autumn.util.function.FunctionOneResult;

/**
 * 更新 Lambda 包装
 *
 * @Description TODO
 * @Author 老码农
 * @Date 2019-06-09 1:31
 */
public class LambdaUpdateWrapper<TSourceWrapper, TEntity>
        extends AbstractUpdateWrapper<LambdaUpdateWrapper<TSourceWrapper, TEntity>, FunctionOneResult<TEntity, ?>>
        implements OfWrapper<TSourceWrapper> {

    private final Class<TEntity> entityClass;
    private final TSourceWrapper sourceWrapper;

    /**
     * 获取实体类型
     *
     * @return
     */
    public Class<TEntity> getEntityClass() {
        return this.entityClass;
    }

    /**
     * 实例化
     *
     * @param sourceWrapper  来源
     * @param entityClass    实体类型
     * @param wrapperCommand 包装命令
     */
    LambdaUpdateWrapper(TSourceWrapper sourceWrapper, Class<TEntity> entityClass, UpdateWrapperCommand wrapperCommand) {
        super(entityClass, wrapperCommand);
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
