package com.autumn.mybatis.plugins;

import com.autumn.mybatis.mapper.EntityMapper;
import com.autumn.mybatis.mapper.MapperInfo;
import com.autumn.mybatis.mapper.MapperUtils;
import com.autumn.mybatis.metadata.EntityColumnOrder;
import com.autumn.mybatis.metadata.EntityRelation;
import com.autumn.mybatis.metadata.EntityRelationMode;
import com.autumn.mybatis.metadata.EntityTable;
import com.autumn.mybatis.wrapper.QueryWrapper;
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
import java.util.Collection;
import java.util.List;

/**
 * 内置查询关联插件
 *
 * @Description TODO
 * @Author 老码农
 * @Date 2019-08-28 7:02
 */
@Intercepts({
        @Signature(type = Executor.class, method = "query", args = {MappedStatement.class, Object.class,
                RowBounds.class, ResultHandler.class, CacheKey.class, BoundSql.class}),
        @Signature(type = Executor.class, method = "query", args = {MappedStatement.class, Object.class,
                RowBounds.class, ResultHandler.class})})
class QueryRelationInterceptor extends AbstractEntityMapperExecutorInterceptor {

    public QueryRelationInterceptor() {

    }

    @Override
    protected boolean isInterceptorMethod(MapperInfo mapperInfo, String mapperMethodName) {
        return mapperMethodName.equals(EntityMapper.GET_AND_LOAD)
                || mapperMethodName.equals(EntityMapper.GET_BY_LOCK_AND_LOAD)
                || mapperMethodName.equals(EntityMapper.SELECT_FOR_FIRST_AND_LOAD)
                || mapperMethodName.equals(EntityMapper.LOAD);
    }

    @SuppressWarnings({"unchecked", "rawtypes"})
    @Override
    protected Object intercept(Invocation invocation, Executor executor, MapperInfo mapperInfo) throws Throwable {
        if (mapperInfo.getMethod().getName().equals(EntityMapper.GET_AND_LOAD)
                || mapperInfo.getMethod().getName().equals(EntityMapper.GET_BY_LOCK_AND_LOAD)
                || mapperInfo.getMethod().getName().equals(EntityMapper.SELECT_FOR_FIRST_AND_LOAD)) {
            List list = (List) invocation.proceed();
            if (mapperInfo.getEntityTable().getRelations().size() > 0 && list.size() > 0) {
                Object entity = list.get(0);
                if (entity != null) {
                    this.load(mapperInfo, entity);
                }
            }
            return list;
        } else if (mapperInfo.getMethod().getName().equals(EntityMapper.LOAD)) {
            Object entity = invocation.getArgs()[1];
            List list = new ArrayList();
            if (entity != null) {
                if (mapperInfo.getEntityTable().getRelations().size() > 0) {
                    this.load(mapperInfo, entity);
                }
                list.add(entity);
            }
            return list;
        }
        return invocation.proceed();
    }

    /**
     * 加载
     *
     * @param mapperInfo
     * @param entity
     */
    @SuppressWarnings({"unchecked", "rawtypes"})
    private void load(MapperInfo mapperInfo, Object entity) {
        EntityTable table = mapperInfo.getEntityTable();
        Object key = table.getKeyColumns().get(0).getValue(entity);
        if (key != null) {
            for (EntityRelation relation : table.getRelations()) {
                EntityTable relationTabe = relation.getRelationTabe();
                QueryWrapper wrapper = new QueryWrapper(relation.getRelationEntityType());
                wrapper.where().eq(relation.getRelationProperty().getName(), key);
                if (relation.getOrderColumns().size() > 0) {
                    for (EntityColumnOrder orderColumn : relation.getOrderColumns()) {
                        wrapper.order(orderColumn.getColumn().getPropertyName(), orderColumn.getDirection());
                    }
                } else {
                    wrapper.orderBy(relationTabe.getKeyColumns().get(0).getPropertyName());
                }
                EntityMapper relationMapper = MapperUtils.resolveEntityMapper(relation.getRelationEntityType());
                if (relation.getRelationMode().equals(EntityRelationMode.ONE_TO_MANY)) {
                    Collection result = relation.createOneToManyCollection();
                    List items = relationMapper.selectForList(wrapper);
                    for (Object item : items) {
                        if (relationTabe.getRelations().size() > 0) {
                            relationMapper.load(item);
                        }
                        result.add(item);
                    }
                    relation.getParentProperty().setValue(entity, result);
                } else {
                    Object result = relationMapper.selectForFirst(wrapper);
                    if (result != null && relationTabe.getRelations().size() > 0) {
                        relationMapper.load(result);
                    }
                    relation.getParentProperty().setValue(entity, result);
                }
            }
        }
    }
}
