package com.autumn.mybatis.executes;

/**
 * 更新执行器
 *
 * @param <TEntity> 实体类型
 * @author 老码农 2019-06-11 22:15:29
 */
public interface MapperUpdateExecute<TEntity> extends MapperExecute<TEntity> {

    /**
     * 更新
     *
     * @return
     */
    int update();
}
