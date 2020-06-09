package com.autumn.mybatis.wrapper;

import com.autumn.mybatis.executes.MapperUpdateExecute;
import com.autumn.mybatis.executes.impl.MapperUpdateExecuteImpl;
import com.autumn.exception.ExceptionUtils;

/**
 * 实体指定更新
 *
 * @author ycg
 * @param <TEntity> 实体
 */
public class EntityUpdateWrapper<TEntity> extends UpdateWrapper
        implements EntityLambdaWrapper<MapperUpdateExecute<TEntity>, LambdaUpdateWrapper<EntityUpdateWrapper<TEntity>, TEntity>> {

    private final Class<TEntity> entityClass;

    /**
     * 实例化 EntityQuery 类新实例
     *
     * @param entityClass 实体类型
     */
    public EntityUpdateWrapper(Class<TEntity> entityClass) {
        super(ExceptionUtils.checkNotNull(entityClass, "entityClass"));
        this.entityClass = entityClass;
    }

    /**
     * 获取实体类型
     *
     * @return
     */
    public final Class<TEntity> getEntityClass() {
        return entityClass;
    }

    private MapperUpdateExecute<TEntity> mapperExecute = null;

    @Override
    public MapperUpdateExecute<TEntity> execute() {
        if (mapperExecute == null) {
            mapperExecute = new MapperUpdateExecuteImpl<>(this, this.getEntityClass());
        }
        return this.mapperExecute;
    }

    private LambdaUpdateWrapper<EntityUpdateWrapper<TEntity>, TEntity> lambdaWrapper = null;

    @Override
    public LambdaUpdateWrapper<EntityUpdateWrapper<TEntity>, TEntity> lambda() {
        if (lambdaWrapper == null) {
            this.lambdaWrapper = new LambdaUpdateWrapper<>(this, this.entityClass, this.getWrapperCommand());
        }
        return this.lambdaWrapper;
    }

}
