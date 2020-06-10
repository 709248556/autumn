package com.autumn.mybatis.mapper;

import com.autumn.mybatis.wrapper.LockModeEnum;
import com.autumn.mybatis.wrapper.QueryWrapper;
import com.autumn.mybatis.wrapper.UpdateWrapper;
import com.autumn.mybatis.wrapper.Wrapper;
import com.autumn.util.function.FunctionTwoAction;
import org.apache.ibatis.annotations.*;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

/**
 * 实体映射
 *
 * @author 老码农
 * <p>
 * 2017-10-11 17:45:28
 */
public interface EntityMapper<TEntity> extends Mapper {

    /**
     * 包装参数名称
     */
    public final static String ARG_NAME_WRAPPER = "wrapper";

    /**
     * 转换类型参数名称
     */
    public final static String ARG_NAME_CONVERT_CLASS = "convertClass";

    /**
     * 转换类型处理参数名称
     */
    public final static String ARG_NAME_CONVERT_ACTION = "convertAction";

    /**
     * 完整包装参数名称
     */
    public final static String ARG_NAME_WRAPPER_FULL = ARG_NAME_WRAPPER + ".section";

    /**
     * 锁模式参数名称
     */
    public final static String ARG_NAME_LOCK_MODE = "lockMode";

    /**
     * 键参数名称
     */
    public final static String ARG_NAME_ID = "id";

    /**
     * 列表参数名称
     */
    public final static String ARG_NAME_LIST = "list";

    /*
     * 下面常量的名称必须与自身函数名称相同，并且与EntityMapperProvider对应的函数名称也必须相同，不能使用重载函数
     */

    /**
     * 插入
     */
    public final static String INSERT = "insert";

    /**
     * 插入子级
     */
    public final static String INSERT_CHILDREN = "insertChildren";

    /**
     * 批量插入
     */
    public final static String INSERT_BY_LIST = "insertByList";

    /**
     * 更新
     */
    public final static String UPDATE = "update";

    /**
     * 更新并重置子级(先删除，再添加)
     */
    public final static String UPDATE_AND_RESET_CHILDREN = "updateAndResetChildren";

    /**
     * 非 null 更新
     */
    public final static String UPDATE_BY_NOT_NULL = "updateByNotNull";

    /**
     * 指定条件更新
     */
    public final static String UPDATE_BY_WHERE = "updateByWhere";

    /**
     * 基于主键删除
     */
    public final static String DELETE_BY_ID = "deleteById";

    /**
     * 基于主键仅删除子级,不删除当前表的记录
     */
    public final static String DELETE_CHILDREN_BY_ID = "deleteChildrenById";

    /**
     * 基于条件删除
     */
    public final static String DELETE_BY_WHERE = "deleteByWhere";

    /**
     * 删除所有
     */
    public final static String DELETE_BY_ALL = "deleteByAll";

    /**
     * 快速清除
     */
    public final static String TRUNCATE = "truncate";

    /**
     * 基于主键查询
     */
    public final static String GET = "get";

    /**
     * 基于主键查询并加载关联
     */
    public final static String GET_AND_LOAD = "getAndLoad";

    /**
     * 基于主键加锁查询
     */
    public final static String GET_BY_LOCK = "getByLock";

    /**
     * 基于主键加锁查询并加载关联
     */
    public final static String GET_BY_LOCK_AND_LOAD = "getByLockAndLoad";

    /**
     * 加载子级
     */
    public final static String LOAD = "load";

    /**
     * 查符合条件的首条件
     */
    public final static String SELECT_FOR_FIRST = "selectForFirst";

    /**
     * 查符合条件的首条件并加载关联
     */
    public final static String SELECT_FOR_FIRST_AND_LOAD = "selectForFirstAndLoad";

    /**
     * 查符合条件的首条件
     */
    public final static String SELECT_FOR_MAP_FIRST = "selectForMapFirst";

    /**
     * 查询Map列表
     */
    public final static String SELECT_FOR_MAP_LIST = "selectForMapList";

    /**
     * 分页查询Map
     */
    public final static String SELECT_FOR_MAP_PAGE = "selectForMapPage";

    /**
     * 查询所有
     */
    public final static String SELECT_FOR_ALL = "selectForAll";

    /**
     * 查询得到list集合
     */
    public final static String SELECT_FOR_LIST = "selectForList";

    /**
     * 分页查询
     */
    public final static String SELECT_FOR_PAGE = "selectForPage";

    /**
     * 分页查询并转换
     */
    public final static String SELECT_FOR_PAGE_CONVERT = "selectForPageConvert";

    /**
     * 分页查询并转换和处理
     */
    public final static String SELECT_FOR_PAGE_CONVERT_ACTION = "selectForPageConvertAction";

    /**
     * 查询所有记录数
     */
    public final static String COUNT = "count";

    /**
     * 根据条件查询记录数
     */
    public final static String COUNT_BY_WHERE = "countByWhere";

    /**
     * 查询唯一结果
     */
    public final static String UNIQUE_RESULT = "uniqueResult";

    /**
     * 插入(如果配置自增主键，则加载自增id,存在关联子级，则会插入关联子级)
     *
     * @param entity 实体
     * @return 返回影响行数
     */
    @InsertProvider(type = EntityMapperProvider.class, method = INSERT)
    int insert(TEntity entity);

    /**
     * 仅插入子级,当前对象不会插入
     *
     * @param entity 实体
     * @return 返回总计插入的记录数
     */
    @InsertProvider(type = EntityMapperProvider.class, method = INSERT_CHILDREN)
    int insertChildren(TEntity entity);

    /**
     * 批量插入(如果数据库主键id为自增，不会返回自增id值，插入数据条数由调用者控制，也不支持一对多或一对多的关联对象插入)
     *
     * @param entitys
     * @return
     */
    @InsertProvider(type = EntityMapperProvider.class, method = INSERT_BY_LIST)
    int insertByList(@Param(ARG_NAME_LIST) List<TEntity> entitys);

    /**
     * 截断表数据(快速清除,不支持事务，清除任何数据，并如初始表的自动化id)
     *
     * @return
     */
    @DeleteProvider(type = EntityMapperProvider.class, method = TRUNCATE)
    void truncate();

    /**
     * 删除所有数据
     *
     * @return
     */
    @DeleteProvider(type = EntityMapperProvider.class, method = DELETE_BY_ALL)
    int deleteByAll();

    /**
     * 基于主键删除，仅删除当前对象，不会删除子级。
     *
     * @param id 主键
     * @return 返回影响行数
     */
    @DeleteProvider(type = EntityMapperProvider.class, method = DELETE_BY_ID)
    int deleteById(Serializable id);

    /**
     * 基于主键仅删除子级,不会删除当前表的记录
     *
     * @param id 主键
     * @return 返回删除所有子级对象的记录数
     */
    @DeleteProvider(type = EntityMapperProvider.class, method = DELETE_CHILDREN_BY_ID)
    int deleteChildrenById(Serializable id);

    /**
     * 基于条件删除
     *
     * @param wrapper 包装器
     * @return 返回影响行数
     */
    @DeleteProvider(type = EntityMapperProvider.class, method = DELETE_BY_WHERE)
    int deleteByWhere(@Param(ARG_NAME_WRAPPER) Wrapper wrapper);

    /**
     * 基于对象进行更新，包括null值，不会更新关联子级
     *
     * @param entity 实体
     * @return 返回影响行数
     */
    @UpdateProvider(type = EntityMapperProvider.class, method = UPDATE)
    int update(TEntity entity);

    /**
     * 基于对象进行更新，包括null值。并重置子级(先删除子级，再添加子级)
     *
     * @param entity 实体
     * @return 返回影响行数
     */
    @UpdateProvider(type = EntityMapperProvider.class, method = UPDATE_AND_RESET_CHILDREN)
    int updateAndResetChildren(TEntity entity);

    /**
     * 基于对象进行更新，跳过 null 值，不会更新关联子级
     *
     * @param entity 实体
     * @return 返回影响行数
     */
    @UpdateProvider(type = EntityMapperProvider.class, method = UPDATE_BY_NOT_NULL)
    int updateByNotNull(TEntity entity);

    /**
     * 根据条件更新
     *
     * @param wrapper 包装
     * @return
     */
    @UpdateProvider(type = EntityMapperProvider.class, method = UPDATE_BY_WHERE)
    int updateByWhere(@Param(ARG_NAME_WRAPPER) UpdateWrapper wrapper);

    /**
     * 基于主键查询
     *
     * @param id 主键值
     * @return 返回 查询的实体
     */
    @SelectProvider(type = EntityMapperProvider.class, method = GET)
    TEntity get(Serializable id);

    /**
     * 基于主键查询并加载关联
     *
     * @param keyValue 主键值
     * @return 返回 查询的实体
     */
    @SelectProvider(type = EntityMapperProvider.class, method = GET_AND_LOAD)
    TEntity getAndLoad(Serializable keyValue);

    /**
     * 基于主键查询并加锁
     *
     * @param id       主键
     * @param lockMode 锁模式
     * @return 返回 查询的实体
     */
    @SelectProvider(type = EntityMapperProvider.class, method = GET_BY_LOCK)
    TEntity getByLock(@Param(ARG_NAME_ID) Serializable id, @Param(ARG_NAME_LOCK_MODE) LockModeEnum lockMode);

    /**
     * 基于主键查询并加锁和加载关联
     *
     * @param id       主键
     * @param lockMode 锁模式
     * @return 返回 查询的实体
     */
    @SelectProvider(type = EntityMapperProvider.class, method = GET_BY_LOCK_AND_LOAD)
    TEntity getByLockAndLoad(@Param(ARG_NAME_ID) Serializable id, @Param(ARG_NAME_LOCK_MODE) LockModeEnum lockMode);

    /**
     * 加载关联实体
     *
     * @param entity 实体
     * @return 返回已加载的实体
     */
    @SelectProvider(type = EntityMapperProvider.class, method = LOAD)
    TEntity load(TEntity entity);

    /**
     * 查询实体首条记录
     *
     * @param wrapper 包装器
     * @return
     */
    @SelectProvider(type = EntityMapperProvider.class, method = SELECT_FOR_FIRST)
    TEntity selectForFirst(@Param(ARG_NAME_WRAPPER) QueryWrapper wrapper);

    /**
     * 查询实体首条记录并加载关联
     *
     * @param wrapper 包装器
     * @return
     */
    @SelectProvider(type = EntityMapperProvider.class, method = SELECT_FOR_FIRST_AND_LOAD)
    TEntity selectForFirstAndLoad(@Param(ARG_NAME_WRAPPER) QueryWrapper wrapper);

    /**
     * 查询list集合
     *
     * @param wrapper 包装器
     * @return
     */
    //@Author: yan
    //type 指明了EntityMapperProvider类
    //method 指明了调用EntityMapperProvider类中的selectForList方法
    @SelectProvider(type = EntityMapperProvider.class, method = SELECT_FOR_LIST)
    List<TEntity> selectForList(@Param(ARG_NAME_WRAPPER) QueryWrapper wrapper);

    /**
     * 查询所有
     *
     * @return 返回
     */
    @SelectProvider(type = EntityMapperProvider.class, method = SELECT_FOR_ALL)
    List<TEntity> selectForAll();

    /**
     * 分页查询
     *
     * @param wrapper 包装器
     * @return
     */
    @SelectProvider(type = EntityMapperProvider.class, method = SELECT_FOR_PAGE)
    PageResult<TEntity> selectForPage(@Param(ARG_NAME_WRAPPER) QueryWrapper wrapper);

    /**
     * 分页查询并转换
     *
     * @param wrapper      包装器
     * @param convertClass 转换类型
     * @return
     */
    @SelectProvider(type = EntityMapperProvider.class, method = SELECT_FOR_PAGE_CONVERT)
    <TConvert> PageResult<TConvert> selectForPageConvert(@Param(ARG_NAME_WRAPPER) QueryWrapper wrapper,
                                                         @Param(ARG_NAME_CONVERT_CLASS) Class<TConvert> convertClass);

    /**
     * 分页查询并转换
     *
     * @param wrapper       包装器
     * @param convertClass  转换类型
     * @param convertAction 转换处理
     * @return
     */
    @SelectProvider(type = EntityMapperProvider.class, method = SELECT_FOR_PAGE_CONVERT_ACTION)
    <TConvert> PageResult<TConvert> selectForPageConvertAction(@Param(ARG_NAME_WRAPPER) QueryWrapper wrapper,
                                                               @Param(ARG_NAME_CONVERT_CLASS) Class<TConvert> convertClass,
                                                               @Param(ARG_NAME_CONVERT_ACTION) FunctionTwoAction<TEntity, TConvert> convertAction);

    /**
     * 查符合条件的首条Map
     *
     * @param wrapper 包装器
     * @return
     */
    @SelectProvider(type = EntityMapperProvider.class, method = SELECT_FOR_MAP_FIRST)
    Map<String, Object> selectForMapFirst(@Param(ARG_NAME_WRAPPER) QueryWrapper wrapper);

    /**
     * 查符合条件的首条Map List
     *
     * @param wrapper 包装器
     * @return
     */
    @SelectProvider(type = EntityMapperProvider.class, method = SELECT_FOR_MAP_LIST)
    List<Map<String, Object>> selectForMapList(@Param(ARG_NAME_WRAPPER) QueryWrapper wrapper);

    /**
     * 分页查询Map
     *
     * @param wrapper 包装器
     * @return
     */
    @SelectProvider(type = EntityMapperProvider.class, method = SELECT_FOR_MAP_PAGE)
    PageResult<Map<String, Object>> selectForMapPage(@Param(ARG_NAME_WRAPPER) QueryWrapper wrapper);

    /**
     * 查询所有记录数
     *
     * @return 返回
     */
    @SelectProvider(type = EntityMapperProvider.class, method = COUNT)
    int count();

    /**
     * 根据条件查询记录数
     *
     * @param wrapper 包装器
     * @return
     */
    @SelectProvider(type = EntityMapperProvider.class, method = COUNT_BY_WHERE)
    int countByWhere(@Param(ARG_NAME_WRAPPER) Wrapper wrapper);

    /**
     * 唯一结果(只有一个字段值)
     *
     * @param wrapper 包装器
     * @return
     */
    @SelectProvider(type = EntityMapperProvider.class, method = UNIQUE_RESULT)
    Object uniqueResult(@Param(ARG_NAME_WRAPPER) QueryWrapper wrapper);

}
