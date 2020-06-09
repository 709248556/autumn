package com.autumn.mybatis.executes.impl;

import com.autumn.mybatis.executes.MapperExecute;
import com.autumn.mybatis.mapper.EntityMapper;
import com.autumn.mybatis.mapper.MapperUtils;
import com.autumn.mybatis.wrapper.Wrapper;

/**
 * 执行器抽象
 *
 * @param <TEntity> 实体类型
 * @author 老码农 2019-06-11 18:48:24
 */
public class MapperExecuteImpl<TWrapper extends Wrapper, TEntity> implements MapperExecute<TEntity> {

    protected final Class<TEntity> entityClass;
    protected final TWrapper wrapper;

    /**
     * @param wrapper
     * @param entityClass
     */
    public MapperExecuteImpl(TWrapper wrapper, Class<TEntity> entityClass) {
        this.entityClass = entityClass;
        this.wrapper = wrapper;
    }

    /**
     * 获取 实体 Mapper
     *
     * @return
     */
    protected final EntityMapper<TEntity> getEntityMapper() {
        return MapperUtils.resolveEntityMapper(this.entityClass);
    }

    @Override
    public int delete() {
        return this.getEntityMapper().deleteByWhere(this.wrapper);
    }

    @Override
    public int count() {
        return this.getEntityMapper().countByWhere(this.wrapper);
    }

    @Override
    public boolean exist() {
        return this.count() > 0;
    }

}
