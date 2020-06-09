package com.autumn.mybatis.wrapper;

import com.autumn.mybatis.wrapper.commands.QueryWrapperCommand;
import com.autumn.mybatis.wrapper.expressions.ColumnExpression;
import com.autumn.util.function.FunctionOneResult;

/**
 * 查询 Lambda 包装器
 *
 * @Description TODO
 * @Author 老码农
 * @Date 2019-06-09 1:08
 */
public class LambdaQueryWrapper<TSourceWrapper, TEntity>
        extends AbstractQueryWrapper<LambdaQueryWrapper<TSourceWrapper, TEntity>, FunctionOneResult<TEntity, ?>>
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
    LambdaQueryWrapper(TSourceWrapper sourceWrapper, Class<TEntity> entityClass, QueryWrapperCommand wrapperCommand) {
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
