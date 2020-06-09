package com.autumn.mybatis.wrapper;

import com.autumn.mybatis.executes.MapperQueryExecute;
import com.autumn.mybatis.executes.impl.MapperQueryExecuteImpl;
import com.autumn.mybatis.mapper.EntityMapper;
import com.autumn.exception.ExceptionUtils;

/**
 * 实体查询包装
 *
 * @author 老码农 2018-12-07 17:24:43
 */
public class EntityQueryWrapper<TEntity> extends QueryWrapper
        implements EntityLambdaWrapper<MapperQueryExecute<TEntity>, LambdaQueryWrapper<EntityQueryWrapper<TEntity>, TEntity>> {

    private final Class<TEntity> entityClass;

    /**
     * 实例化 EntityQuery 类新实例
     *
     * @param entityClass 实体类型
     */
    public EntityQueryWrapper(Class<TEntity> entityClass) {
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



    /**
     * 查询记录数
     *
     * @param mapper 仓储
     * @return
     */
    public int countByWhere(EntityMapper<TEntity> mapper) {
        ExceptionUtils.checkNotNull(mapper, "mapper");
        return mapper.countByWhere(this);
    }

    /**
     * 是否存在记录
     *
     * @param mapper 仓储
     * @return
     */
    public boolean exist(EntityMapper<TEntity> mapper) {
        return this.countByWhere(mapper) > 0;
    }

    private MapperQueryExecute<TEntity> mapperExecute = null;

    @Override
    public MapperQueryExecute<TEntity> execute() {
        if (mapperExecute == null) {
            mapperExecute = new MapperQueryExecuteImpl<>(this, this.getEntityClass());
        }
        return this.mapperExecute;
    }

    private LambdaQueryWrapper<EntityQueryWrapper<TEntity>, TEntity> lambdaWrapper = null;

    @Override
    public LambdaQueryWrapper<EntityQueryWrapper<TEntity>, TEntity> lambda() {
        if (lambdaWrapper == null) {
            this.lambdaWrapper = new LambdaQueryWrapper<>(this, this.entityClass, this.getWrapperCommand());
        }
        return this.lambdaWrapper;
    }
}
