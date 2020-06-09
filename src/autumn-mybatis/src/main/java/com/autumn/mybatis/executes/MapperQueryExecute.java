package com.autumn.mybatis.executes;

import com.autumn.mybatis.mapper.PageResult;
import com.autumn.util.function.FunctionTwoAction;

import java.util.List;
import java.util.Map;

/**
 * 查询执行器
 *
 * @param <TEntity> 实体类型
 * @author 老码农 2019-06-11 21:42:41
 */
public interface MapperQueryExecute<TEntity> extends MapperExecute<TEntity> {

    /**
     * 查询首条记录
     *
     * @return
     */
    TEntity selectForFirst();

    /**
     * 查询首条记录并加载关联
     *
     * @return
     */
    TEntity selectForFirstAndLoad();

    /**
     * 加载关联
     *
     * @param entity 实体
     * @return
     */
    TEntity load(TEntity entity);

    /**
     * 查询列表
     *
     * @return 返回 List
     */
    List<TEntity> selectForList();

    /**
     * 分页查询
     *
     * @return 返回 PageResult
     */
    PageResult<TEntity> selectForPage();

    /**
     * 分页查询并转换
     *
     * @param convertClass 转换类型
     * @return
     */
    <TConvert> PageResult<TConvert> selectForPage(Class<TConvert> convertClass);

    /**
     * 分页查询并转换
     *
     * @param convertClass  转换类型
     * @param convertAction 转换处理
     * @return
     */
    <TConvert> PageResult<TConvert> selectForPage(Class<TConvert> convertClass,
                                                  FunctionTwoAction<TEntity, TConvert> convertAction);

    /**
     * 查询首条记录的Map
     *
     * @return
     */
    Map<String, Object> selectForMapFirst();

    /**
     * 查询列表 Map
     *
     * @return
     */
    List<Map<String, Object>> selectForMapList();

    /**
     * Map 分页查询
     *
     * @return 返回 PageResult
     */
    PageResult<Map<String, Object>> selectForMapPage();

    /**
     * 唯一结果(只有一个字段值)
     *
     * @return 只有一个字段值
     */
    Object uniqueResult();

    /**
     * 唯一结果(只有一个字段值)
     *
     * @param resultClass 结果Class类型
     * @param <TResult>   结果类型
     * @return 只有一个字段值
     */
    <TResult> TResult uniqueResult(Class<TResult> resultClass);
}
