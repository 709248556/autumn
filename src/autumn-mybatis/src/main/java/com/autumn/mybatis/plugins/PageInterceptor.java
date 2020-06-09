package com.autumn.mybatis.plugins;

import com.autumn.exception.ExceptionUtils;
import com.autumn.mybatis.mapper.*;
import com.autumn.mybatis.wrapper.QueryWrapper;
import com.autumn.util.AutoMapUtils;
import com.autumn.util.function.FunctionTwoAction;
import org.apache.ibatis.cache.CacheKey;
import org.apache.ibatis.executor.Executor;
import org.apache.ibatis.mapping.BoundSql;
import org.apache.ibatis.mapping.MappedStatement;
import org.apache.ibatis.plugin.Intercepts;
import org.apache.ibatis.plugin.Invocation;
import org.apache.ibatis.plugin.Signature;
import org.apache.ibatis.session.ResultHandler;
import org.apache.ibatis.session.RowBounds;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * 内置分页拦截
 * <p>
 * 不需要注入配置，会自动注入
 * </p>
 *
 * @author 老码农 2019-06-10 02:06:53
 */
@Intercepts({
        @Signature(type = Executor.class, method = "query", args = {MappedStatement.class, Object.class,
                RowBounds.class, ResultHandler.class, CacheKey.class, BoundSql.class}),
        @Signature(type = Executor.class, method = "query", args = {MappedStatement.class, Object.class,
                RowBounds.class, ResultHandler.class})})
class PageInterceptor extends AbstractEntityMapperExecutorInterceptor {

    public PageInterceptor() {

    }

    @Override
    protected boolean isInterceptorMethod(MapperInfo mapperInfo, String mapperMethodName) {
        return mapperMethodName.equals(EntityMapper.SELECT_FOR_PAGE)
                || mapperMethodName.equals(EntityMapper.SELECT_FOR_PAGE_CONVERT)
                || mapperMethodName.equals(EntityMapper.SELECT_FOR_PAGE_CONVERT_ACTION)
                || mapperMethodName.equals(EntityMapper.SELECT_FOR_MAP_PAGE);
    }

    @SuppressWarnings({"unchecked", "rawtypes"})
    @Override
    protected Object intercept(Invocation invocation, Executor executor, MapperInfo mapperInfo) throws Throwable {
        Map<String, Object> argMap = (Map<String, Object>) invocation.getArgs()[1];
        QueryWrapper wrapper = (QueryWrapper) argMap.get(EntityMapper.ARG_NAME_WRAPPER);
        if (!wrapper.isPage()) {
            wrapper.page(1, QueryWrapper.DEFAULT_PAGE_SIZE);
        }
        EntityMapper entityMapper = MapperUtils.resolveEntityMapper(mapperInfo.getEntityClass());
        int count = entityMapper.countByWhere(wrapper);
        if (mapperInfo.getMethod().getName().equals(EntityMapper.SELECT_FOR_MAP_PAGE)) {
            return this.createByMapPage(wrapper, entityMapper, count);
        }
        if (mapperInfo.getMethod().getName().equals(EntityMapper.SELECT_FOR_PAGE)) {
            return this.createByPage(wrapper, entityMapper, count, null, null);
        }
        Class<?> convertClass = (Class<?>) argMap.get(EntityMapper.ARG_NAME_CONVERT_CLASS);
        if (convertClass == null) {
            throw ExceptionUtils.throwValidationException(
                    "convertClass 为 null，无法调用分页方法[ " + mapperInfo.getMethod().getName() + "]。");
        }
        if (mapperInfo.getMethod().getName().equals(EntityMapper.SELECT_FOR_PAGE_CONVERT)) {
            return this.createByPage(wrapper, entityMapper, count, convertClass, null);
        }
        FunctionTwoAction<Object, Object> convertAction =
                (FunctionTwoAction<Object, Object>) argMap.get(EntityMapper.ARG_NAME_CONVERT_ACTION);
        return this.createByPage(wrapper, entityMapper, count, convertClass, convertAction);
    }

    @SuppressWarnings({"rawtypes", "unchecked"})
    private List<PageResult> createByPage(QueryWrapper wrapper, EntityMapper entityMapper, int count,
                                          Class<?> convertClass, FunctionTwoAction<Object, Object> convertAction)
            throws InstantiationException, IllegalAccessException {
        DefaultPageResult<?> result = new DefaultPageResult<>(wrapper.getCurrentPage(), wrapper.getLimit(), count);
        if (count > 0) {
            if (result.getCurrentPage() != wrapper.getCurrentPage()) {
                wrapper.page(result.getCurrentPage(), result.getPageSize());
            }
            List items = entityMapper.selectForList(wrapper);
            if (convertClass != null) {
                if (convertAction != null) {
                    List targetItems = items.getClass().newInstance();
                    for (Object source : items) {
                        Object target = AutoMapUtils.map(source, convertClass);
                        convertAction.apply(source, target);
                        targetItems.add(target);
                    }
                    items = targetItems;
                } else {
                    items = AutoMapUtils.mapForList(items, convertClass);
                }
            }
            result.setItems(items);
        }
        List<PageResult> results = new ArrayList<>(1);
        results.add(result);
        return results;
    }

    @SuppressWarnings({"rawtypes", "unchecked"})
    private List<PageResult<Map<String, Object>>> createByMapPage(QueryWrapper wrapper, EntityMapper entityMapper, int count) {
        DefaultPageResult<Map<String, Object>> result = new DefaultPageResult<>(wrapper.getCurrentPage(), wrapper.getLimit(), count);
        if (count > 0) {
            if (result.getCurrentPage() != wrapper.getCurrentPage()) {
                wrapper.page(result.getCurrentPage(), result.getPageSize());
            }
            List<Map<String, Object>> items = entityMapper.selectForMapList(wrapper);
            result.setItems(items);
        }
        List<PageResult<Map<String, Object>>> results = new ArrayList<>(1);
        results.add(result);
        return results;
    }


}
