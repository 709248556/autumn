package com.autumn.domain.repositories.plugins;

import com.autumn.domain.entities.Entity;
import com.autumn.domain.entities.auditing.AuditingTransient;
import com.autumn.domain.entities.auditing.SoftDelete;
import com.autumn.exception.ExceptionUtils;
import com.autumn.mybatis.mapper.EntityMapper;
import com.autumn.mybatis.mapper.MapperInfo;
import com.autumn.mybatis.mapper.MapperUtils;
import com.autumn.mybatis.wrapper.*;
import org.apache.ibatis.cache.CacheKey;
import org.apache.ibatis.executor.Executor;
import org.apache.ibatis.mapping.BoundSql;
import org.apache.ibatis.mapping.MappedStatement;
import org.apache.ibatis.plugin.Intercepts;
import org.apache.ibatis.plugin.Invocation;
import org.apache.ibatis.plugin.Signature;
import org.apache.ibatis.session.ResultHandler;
import org.apache.ibatis.session.RowBounds;

import java.io.Serializable;
import java.util.*;

/**
 * 查询审计拦截
 *
 * @Description TODO
 * @Author 老码农
 * @Date 2019-08-27 15:24
 */
@Intercepts({
        @Signature(type = Executor.class, method = "query", args = {MappedStatement.class, Object.class,
                RowBounds.class, ResultHandler.class, CacheKey.class, BoundSql.class}),
        @Signature(type = Executor.class, method = "query", args = {MappedStatement.class, Object.class,
                RowBounds.class, ResultHandler.class})})
public class SelectAuditingInterceptor extends AbstractAuditingInterceptor {
    /**
     * 包装器查询状态
     */
    public static final int WRAPPER_STATUS_SELECT_AUDITING = -58978547;

    private static final Set<String> QUERY_METHOD_SET = new HashSet<>();

    static {
        QUERY_METHOD_SET.add(EntityMapper.GET);
        QUERY_METHOD_SET.add(EntityMapper.GET_AND_LOAD);
        QUERY_METHOD_SET.add(EntityMapper.GET_BY_LOCK);
        QUERY_METHOD_SET.add(EntityMapper.GET_BY_LOCK_AND_LOAD);
        QUERY_METHOD_SET.add(EntityMapper.SELECT_FOR_FIRST);
        QUERY_METHOD_SET.add(EntityMapper.SELECT_FOR_FIRST_AND_LOAD);
        QUERY_METHOD_SET.add(EntityMapper.SELECT_FOR_LIST);
        QUERY_METHOD_SET.add(EntityMapper.SELECT_FOR_ALL);
        QUERY_METHOD_SET.add(EntityMapper.SELECT_FOR_MAP_FIRST);
        QUERY_METHOD_SET.add(EntityMapper.SELECT_FOR_MAP_LIST);
        QUERY_METHOD_SET.add(EntityMapper.COUNT);
        QUERY_METHOD_SET.add(EntityMapper.COUNT_BY_WHERE);
        QUERY_METHOD_SET.add(EntityMapper.UNIQUE_RESULT);
    }

    @Override
    protected boolean isInterceptorAuditingMethod(MapperInfo mapperInfo, String mapperMethodName) {
        return SoftDelete.class.isAssignableFrom(mapperInfo.getEntityClass())
                && QUERY_METHOD_SET.contains(mapperMethodName);
    }

    @SuppressWarnings({"unchecked", "rawtypes"})
    @Override
    protected Object intercept(Invocation invocation, Executor executor, MapperInfo mapperInfo) throws Throwable {
        switch (mapperInfo.getMethod().getName()) {
            case EntityMapper.GET:
            case EntityMapper.GET_AND_LOAD: {
                Serializable keyValue = (Serializable) invocation.getArgs()[1];
                return this.queryListById(keyValue, null, mapperInfo,
                        mapperInfo.getMethod().getName().equals(EntityMapper.GET_AND_LOAD));
            }
            case EntityMapper.GET_BY_LOCK:
            case EntityMapper.GET_BY_LOCK_AND_LOAD: {
                Map<String, Object> argMap = (Map<String, Object>) invocation.getArgs()[1];
                Serializable keyValue = (Serializable) argMap.get(EntityMapper.ARG_NAME_ID);
                LockModeEnum mode = (LockModeEnum) argMap.get(EntityMapper.ARG_NAME_LOCK_MODE);
                return this.queryListById(keyValue, mode,
                        mapperInfo, mapperInfo.getMethod().getName().equals(EntityMapper.GET_BY_LOCK_AND_LOAD));
            }
            case EntityMapper.SELECT_FOR_ALL: {
                QueryWrapper wrapper = new QueryWrapper(mapperInfo.getEntityClass());
                wrapper.where().eq(SoftDelete.FIELD_IS_DELETE, false);
                wrapper.orderByDefault();
                if (wrapper.getOrderColumns() == 0) {
                    wrapper.orderBy(Entity.FIELD_ID);
                }
                wrapper.setWrapperStatus(WRAPPER_STATUS_SELECT_AUDITING);
                EntityMapper entityMapper = MapperUtils.resolveEntityMapper(mapperInfo.getEntityClass());
                return entityMapper.selectForList(wrapper);
            }
            case EntityMapper.COUNT: {
                CriteriaWrapper wrapper = new CriteriaWrapper(mapperInfo.getEntityClass());
                wrapper.where().eq(SoftDelete.FIELD_IS_DELETE, false);
                wrapper.setWrapperStatus(WRAPPER_STATUS_SELECT_AUDITING);
                EntityMapper entityMapper = MapperUtils.resolveEntityMapper(mapperInfo.getEntityClass());
                List<Integer> result = new ArrayList<>();
                result.add(entityMapper.countByWhere(wrapper));
                return result;
            }
            case EntityMapper.SELECT_FOR_FIRST:
            case EntityMapper.SELECT_FOR_FIRST_AND_LOAD:
            case EntityMapper.SELECT_FOR_LIST:
            case EntityMapper.SELECT_FOR_MAP_FIRST:
            case EntityMapper.SELECT_FOR_MAP_LIST:
            case EntityMapper.UNIQUE_RESULT: {
                Map<String, Object> argMap = (Map<String, Object>) invocation.getArgs()[1];
                QueryWrapper wrapper = (QueryWrapper) argMap.get(EntityMapper.ARG_NAME_WRAPPER);
                //如果是查询审计来源
                if (wrapper.getWrapperStatus() != null && wrapper.getWrapperStatus().equals(WRAPPER_STATUS_SELECT_AUDITING)) {
                    return invocation.proceed();
                }
                wrapper.where().eq(SoftDelete.FIELD_IS_DELETE, false);
                break;
            }
            case EntityMapper.COUNT_BY_WHERE: {
                Map<String, Object> argMap = (Map<String, Object>) invocation.getArgs()[1];
                Wrapper wrapper = (Wrapper) argMap.get(EntityMapper.ARG_NAME_WRAPPER);
                if (wrapper instanceof AbstractWrapper) {
                    AbstractWrapper baseWrapper = (AbstractWrapper) wrapper;
                    //如果是查询审计来源
                    if (baseWrapper.getWrapperStatus() != null && baseWrapper.getWrapperStatus().equals(WRAPPER_STATUS_SELECT_AUDITING)) {
                        return invocation.proceed();
                    }
                    baseWrapper.where().eq(SoftDelete.FIELD_IS_DELETE, false);
                } else {
                    if (wrapper == null) {
                        throw new NullPointerException("wrapper is null");
                    }
                    ExceptionUtils.throwNotSupportException("不支持类型为[" + wrapper.getClass().getName() + "]的包装器。");
                }
                break;
            }
            default: {
                break;
            }
        }
        return invocation.proceed();
    }

    /**
     * 查询首条
     *
     * @param keyValue   主键
     * @param mode       锁
     * @param mapperInfo 映射信息
     * @param isLoad     是否加载
     * @return
     */
    @SuppressWarnings({"unchecked"})
    private List queryListById(Serializable keyValue, LockModeEnum mode, MapperInfo mapperInfo, boolean isLoad) {
        QueryWrapper wrapper = new QueryWrapper(mapperInfo.getEntityClass());
        wrapper.where()
                .eq(Entity.FIELD_ID, keyValue)
                .eq(SoftDelete.FIELD_IS_DELETE, false)
                .of()
                .lock(mode)
                .orderBy(Entity.FIELD_ID);
        wrapper.setWrapperStatus(WRAPPER_STATUS_SELECT_AUDITING);
        EntityMapper entityMapper = MapperUtils.resolveEntityMapper(mapperInfo.getEntityClass());
        //已转
        List items = entityMapper.selectForList(wrapper);
        if (isLoad && items.size() > 0) {
            entityMapper.load(items.get(0));
        }
        return items;
    }

}
