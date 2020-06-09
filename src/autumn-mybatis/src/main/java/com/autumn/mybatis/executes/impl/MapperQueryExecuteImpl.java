package com.autumn.mybatis.executes.impl;

import com.autumn.mybatis.executes.MapperQueryExecute;
import com.autumn.mybatis.mapper.PageResult;
import com.autumn.mybatis.wrapper.EntityQueryWrapper;
import com.autumn.exception.ExceptionUtils;
import com.autumn.util.TypeUtils;
import com.autumn.util.function.FunctionTwoAction;

import java.util.List;
import java.util.Map;

/**
 * 查询执行器
 *
 * @param <TEntity> 实体类型
 * @author 老码农 2019-06-11 19:03:04
 */
public class MapperQueryExecuteImpl<TEntity> extends MapperExecuteImpl<EntityQueryWrapper<TEntity>, TEntity>
        implements MapperQueryExecute<TEntity> {

    /**
     * @param wrapper
     * @param entityClass
     */
    public MapperQueryExecuteImpl(EntityQueryWrapper<TEntity> wrapper, Class<TEntity> entityClass) {
        super(wrapper, entityClass);
    }

    @Override
    public TEntity selectForFirst() {
        return this.getEntityMapper().selectForFirst(this.wrapper);
    }

    @Override
    public TEntity selectForFirstAndLoad() {
        return this.getEntityMapper().selectForFirstAndLoad(this.wrapper);
    }

    @Override
    public TEntity load(TEntity entity) {
        return this.getEntityMapper().load(entity);
    }

    @Override
    public List<TEntity> selectForList() {
        return this.getEntityMapper().selectForList(this.wrapper);
    }

    @Override
    public PageResult<TEntity> selectForPage() {
        return this.getEntityMapper().selectForPage(this.wrapper);
    }

    @Override
    public <TConvert> PageResult<TConvert> selectForPage(Class<TConvert> convertClass) {
        return this.getEntityMapper().selectForPageConvert(this.wrapper, convertClass);
    }

    @Override
    public <TConvert> PageResult<TConvert> selectForPage(Class<TConvert> convertClass,
                                                         FunctionTwoAction<TEntity, TConvert> convertAction) {
        return this.getEntityMapper().selectForPageConvertAction(this.wrapper, convertClass, convertAction);
    }

    @Override
    public Map<String, Object> selectForMapFirst() {
        return this.getEntityMapper().selectForMapFirst(this.wrapper);
    }

    @Override
    public List<Map<String, Object>> selectForMapList() {
        return this.getEntityMapper().selectForMapList(this.wrapper);
    }

    @Override
    public PageResult<Map<String, Object>> selectForMapPage() {
        return this.getEntityMapper().selectForMapPage(this.wrapper);
    }

    @Override
    public Object uniqueResult() {
        return this.getEntityMapper().uniqueResult(this.wrapper);
    }

    @Override
    public <TResult> TResult uniqueResult(Class<TResult> resultClass) {
        ExceptionUtils.checkNotNull(resultClass, "resultClass");
        Object value = this.uniqueResult();
        if (value == null) {
            return null;
        }
        return TypeUtils.toConvert(resultClass, value);
    }

}
