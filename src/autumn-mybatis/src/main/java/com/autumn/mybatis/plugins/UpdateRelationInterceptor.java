package com.autumn.mybatis.plugins;

import com.autumn.exception.SystemException;
import com.autumn.mybatis.mapper.EntityMapper;
import com.autumn.mybatis.mapper.MapperInfo;
import com.autumn.mybatis.mapper.MapperUtils;
import com.autumn.mybatis.metadata.*;
import com.autumn.mybatis.wrapper.QueryWrapper;
import org.apache.ibatis.executor.Executor;
import org.apache.ibatis.mapping.MappedStatement;
import org.apache.ibatis.plugin.Intercepts;
import org.apache.ibatis.plugin.Invocation;
import org.apache.ibatis.plugin.Signature;

import java.util.Collection;
import java.util.List;
import java.util.Map;

/**
 * 同步关联拦截(插入、更新、删除)
 *
 * @Description TODO
 * @Author 老码农
 * @Date 2019-08-28 18:12
 */
@Intercepts({@Signature(type = Executor.class, method = "update", args = {MappedStatement.class, Object.class})})
class UpdateRelationInterceptor extends AbstractEntityMapperExecutorInterceptor {

    public UpdateRelationInterceptor() {
        
    }

    @Override
    protected boolean isInterceptorMethod(MapperInfo mapperInfo, String mapperMethodName) {
        return mapperMethodName.equals(EntityMapper.INSERT)
                || mapperMethodName.equals(EntityMapper.INSERT_CHILDREN)
                || mapperMethodName.equals(EntityMapper.DELETE_CHILDREN_BY_ID)
                || mapperMethodName.equals(EntityMapper.UPDATE_AND_RESET_CHILDREN);
    }

    @Override
    protected Object intercept(Invocation invocation, Executor executor, MapperInfo mapperInfo) throws Throwable {
        if (mapperInfo.getMethod().getName().equals(EntityMapper.INSERT_CHILDREN)) {
            Object entity = invocation.getArgs()[1];
            if (mapperInfo.getEntityTable().getRelations().size() > 0) {
                return this.addChildren(entity, mapperInfo);
            }
            return 0;
        } else if (mapperInfo.getMethod().getName().equals(EntityMapper.DELETE_CHILDREN_BY_ID)) {
            Object keyValue = invocation.getArgs()[1];
            if (mapperInfo.getEntityTable().getRelations().size() > 0) {
                return this.deleteChildren(keyValue, mapperInfo.getEntityTable());
            }
            return 0;
        } else {
            if (mapperInfo.getEntityTable().getRelations().size() > 0) {
                int result = (int) invocation.proceed();
                if (mapperInfo.getMethod().getName().equals(EntityMapper.INSERT)) {
                    Object entity = invocation.getArgs()[1];
                    result += addChildren(entity, mapperInfo);
                }
                if (mapperInfo.getMethod().getName().equals(EntityMapper.UPDATE_AND_RESET_CHILDREN)) {
                    Object entity = invocation.getArgs()[1];
                    if (entity != null) {
                        result += this.deleteChildren(mapperInfo.getEntityTable().getKeyColumns().get(0).getValue(entity),
                                mapperInfo.getEntityTable());
                        result += addChildren(entity, mapperInfo);
                    }
                }
                return result;
            } else {
                return invocation.proceed();
            }
        }
    }

    /**
     * 添加子级
     *
     * @param entity     实体
     * @param mapperInfo 映射
     */
    @SuppressWarnings({"unchecked", "rawtypes"})
    private int addChildren(Object entity, MapperInfo mapperInfo) {
        if (entity == null) {
            return 0;
        }
        int count = 0;
        EntityTable table = mapperInfo.getEntityTable();
        Object key = table.getKeyColumns().get(0).getValue(entity);
        if (key == null) {
            throw new SystemException(entity + "主键值为null,无法添加关联子级。");
        }
        for (EntityRelation relation : table.getRelations()) {
            //不插入
            if (!relation.isInsertable()) {
                continue;
            }
            EntityTable relationTabe = relation.getRelationTabe();
            Object parentValue = relation.getParentProperty().getValue(entity);
            if (parentValue == null) {
                continue;
            }
            if (relation.getRelationMode().equals(EntityRelationMode.ONE_TO_MANY)) {
                Collection list = (Collection) parentValue;
                if (list.size() > 0) {
                    EntityMapper entityMapper = MapperUtils.resolveEntityMapper(relation.getRelationEntityType());
                    for (Object relationEntity : list) {
                        if (relationEntity == null) {
                            throw new SystemException(entity + " 的一对多属性[" + relation.getParentProperty().getName() + "]存在null的元素。");
                        }
                        relation.getRelationProperty().setValue(relationEntity, key);
                        count += entityMapper.insert(relationEntity);
                    }
                }
            } else {
                EntityMapper entityMapper = MapperUtils.resolveEntityMapper(relation.getRelationEntityType());
                relation.getRelationProperty().setValue(parentValue, key);
                count += entityMapper.insert(parentValue);
            }
        }
        return count;
    }

    /**
     * 删除子级
     *
     * @param key   主键
     * @param table 表
     */
    @SuppressWarnings({"unchecked", "rawtypes"})
    private int deleteChildren(Object key, EntityTable table) {
        if (key == null) {
            return 0;
        }
        int count = 0;
        for (EntityRelation relation : table.getRelations()) {
            //不更新与删除
            if (!relation.isUpdatable()) {
                continue;
            }
            EntityTable relationTabe = relation.getRelationTabe();
            EntityColumn relationKeyColumn = relationTabe.getKeyColumns().get(0);
            QueryWrapper wrapper = new QueryWrapper(relation.getRelationEntityType());
            wrapper.where().eq(relation.getRelationProperty().getName(), key)
                    .of()
                    .select().column(relationKeyColumn.getPropertyName());
            if (relation.getOrderColumns().size() > 0) {
                for (EntityColumnOrder orderColumn : relation.getOrderColumns()) {
                    wrapper.order(orderColumn.getColumn().getPropertyName(), orderColumn.getDirection());
                }
            } else {
                wrapper.orderBy(relationKeyColumn.getPropertyName());
            }
            EntityMapper relationMapper = MapperUtils.resolveEntityMapper(relation.getRelationEntityType());
            //存在子级，递归先删除末级
            if (relationTabe.getRelations().size() > 0) {
                List<Map<String, Object>> mapList = relationMapper.selectForMapList(wrapper);
                for (Map<String, Object> map : mapList) {
                    Object relationKey = map.get(relationKeyColumn.getPropertyName());
                    count += deleteChildren(relationKey, relationTabe);
                }
            }
            count += relationMapper.deleteByWhere(wrapper);
        }
        return count;
    }
}
