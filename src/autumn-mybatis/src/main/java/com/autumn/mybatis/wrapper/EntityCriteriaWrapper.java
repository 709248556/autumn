package com.autumn.mybatis.wrapper;

import com.autumn.mybatis.executes.MapperExecute;
import com.autumn.mybatis.executes.impl.MapperExecuteImpl;
import com.autumn.mybatis.wrapper.expressions.ColumnExpression;
import com.autumn.exception.ExceptionUtils;

/**
 * 实体条件包装器
 *
 * @author ycg
 * @param <TEntity> 实体类型
 */
public class EntityCriteriaWrapper<TEntity> extends CriteriaWrapper
        implements EntityLambdaWrapper<MapperExecute<TEntity>, LambdaCriteriaWrapper<EntityCriteriaWrapper<TEntity>, TEntity>> {

    private final Class<TEntity> entityClass;

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
     * @param entityClass 实体类型
     */
    public EntityCriteriaWrapper(Class<TEntity> entityClass) {
        super(ExceptionUtils.checkNotNull(entityClass, "entityClass"));
        this.entityClass = entityClass;
    }

    @Override
    protected ColumnExpression createColumnExpression(String name) {
        return this.column(name);
    }

    private MapperExecute<TEntity> mapperExecute = null;

    @Override
    public MapperExecute<TEntity> execute() {
        if (mapperExecute == null) {
            mapperExecute = new MapperExecuteImpl<>(this, this.getEntityClass());
        }
        return this.mapperExecute;
    }

    private LambdaCriteriaWrapper<EntityCriteriaWrapper<TEntity>, TEntity> lambdaWrapper = null;

    @Override
    public LambdaCriteriaWrapper<EntityCriteriaWrapper<TEntity>, TEntity> lambda() {
        if (lambdaWrapper == null) {
            this.lambdaWrapper = new LambdaCriteriaWrapper<>(this, this.entityClass, this.getWrapperCommand());
        }
        return this.lambdaWrapper;
    }

}
