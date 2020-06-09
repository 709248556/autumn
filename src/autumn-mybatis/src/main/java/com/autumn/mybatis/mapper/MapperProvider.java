package com.autumn.mybatis.mapper;

import com.autumn.mybatis.metadata.EntityColumn;
import com.autumn.mybatis.metadata.EntityTable;
import com.autumn.mybatis.provider.DbProvider;
import org.apache.ibatis.cache.Cache;
import org.apache.ibatis.mapping.*;
import org.apache.ibatis.reflection.MetaObject;
import org.apache.ibatis.reflection.SystemMetaObject;
import org.apache.ibatis.scripting.xmltags.DynamicSqlSource;
import org.apache.ibatis.scripting.xmltags.SqlNode;
import org.apache.ibatis.scripting.xmltags.XMLLanguageDriver;
import org.apache.ibatis.session.Configuration;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Mapper 提供者
 *
 * @author 老码农
 * <p>
 * 2017-10-17 17:49:24
 */
public class MapperProvider {
    static final Pattern DELIMITER = Pattern.compile("^[`\\[\"]?(.*?)[`\\]\"]?$");
    private static final XMLLanguageDriver LANGUAGE_DRIVER = new XMLLanguageDriver();
    protected final MapperCache mapperCache;
    private final Map<EntityTable, ResultMap> entityTableResultMap = new ConcurrentHashMap<>();
    private final Class<?> parentMapperClass;

    /**
     * 获取映射类型
     *
     * @return
     */
    public Class<?> getParentMapperClass() {
        return this.parentMapperClass;
    }

    private final MapperRegister rapperRegister;

    /**
     * @param parentMapperClass
     * @param rapperRegister
     */
    public MapperProvider(Class<?> parentMapperClass, MapperRegister rapperRegister) {
        this.parentMapperClass = parentMapperClass;
        this.rapperRegister = rapperRegister;
        this.mapperCache = new MapperCache(parentMapperClass);
    }

    /**
     * 注册方法
     *
     * @param name
     * @param method
     */
    void registerMethod(String name, Method method) {
        this.mapperCache.registerMethod(name, method);
    }

    /**
     * 生成当前实体的resultMap对象
     *
     * @param table         表
     * @param configuration 配置
     * @param mapName       map名称
     * @return
     */
    public ResultMap getResultMap(EntityTable table, Configuration configuration, String mapName) {
        List<ResultMapping> resultMappings = new ArrayList<ResultMapping>();
        for (EntityColumn entityColumn : table.getColumns()) {
            String column = entityColumn.getColumnName();
            Matcher matcher = DELIMITER.matcher(column);
            if (matcher.find()) {
                column = matcher.group(1);
            }
            ResultMapping.Builder builder = new ResultMapping.Builder(configuration, entityColumn.getPropertyName(),
                    column, entityColumn.getJavaType());
            builder.jdbcType(entityColumn.getJdbcType());
            try {
                builder.typeHandler(entityColumn.newTypeHandlerInstance());
            } catch (Exception e) {
                throw new MapperException(e.getMessage(), e);
            }
            List<ResultFlag> flags = new ArrayList<ResultFlag>();
            if (entityColumn.isPrimaryKey()) {
                flags.add(ResultFlag.ID);
            }
            builder.flags(flags);
            resultMappings.add(builder.build());
        }
        String id = table.getEntityClass().getName() + "." + mapName;
        ResultMap.Builder builder = new ResultMap.Builder(configuration, id, table.getEntityClass(), resultMappings,
                true);
        ResultMap resultMap = builder.build();
        entityTableResultMap.put(table, resultMap);
        return resultMap;
    }

    /**
     * 获取 MapperInfo 信息
     *
     * @param msId msId
     * @return 2017-12-08 14:54:04
     */
    public MapperInfo getMapperInfo(String msId) {
        Class<?> msPapperClass = MapperUtils.getMapperClass(msId);
        if (msPapperClass != null && this.parentMapperClass.isAssignableFrom(msPapperClass)
                && !msPapperClass.equals(this.parentMapperClass)) {
            String methodName = MapperUtils.getMethodName(msId);
            Method method = this.mapperCache.getMethodByName(methodName);
            if (method != null) {
                Class<?> entityClass = this.mapperCache.getEntityClass(msId);
                if (entityClass != null) {
                    return new MapperInfo(this.getDbProvider(), msId, method, msPapperClass, entityClass);
                }
                if (EntityMapper.class.isAssignableFrom(msPapperClass)) {
                    throw new MapperException("无法获取 Mapper 泛型的实体类型:" + msPapperClass.getName());
                }
                return new MapperInfo(this.getDbProvider(), msId, method, msPapperClass, null);
            }
            return null;
        }
        return null;
    }

    /**
     * 重新设置SqlSource
     *
     * @param ms
     * @throws java.lang.reflect.InvocationTargetException
     * @throws IllegalAccessException
     */
    public void setSqlSource(MappedStatement ms) throws Exception {
        Class<?> type = MapperUtils.getMapperClass(ms.getId());
        if (type.equals(this.parentMapperClass)) {
            throw new MapperException("不能直接使用 " + this.parentMapperClass.toString() + " 必须是其子类或者子接口。");
        }
        Method method = this.mapperCache.getMethod(ms);
        try {
            if (method.getReturnType() == Void.TYPE) {
                method.invoke(this, ms);
            } else if (SqlNode.class.isAssignableFrom(method.getReturnType())) {
                SqlNode sqlNode = (SqlNode) method.invoke(this, ms);
                DynamicSqlSource dynamicSqlSource = new DynamicSqlSource(ms.getConfiguration(), sqlNode);
                setSqlSource(ms, dynamicSqlSource);
            } else if (String.class.equals(method.getReturnType())) {
                String xmlSql = (String) method.invoke(this, ms);
                SqlSource sqlSource = createSqlSource(ms, xmlSql);
                setSqlSource(ms, sqlSource);
            } else {
                throw new MapperException("自定义Mapper方法返回类型错误,可选的返回类型为void,SqlNode,String三种!");
            }
            checkCache(ms);
        } catch (IllegalAccessException e) {
            throw new MapperException(e);
        } catch (InvocationTargetException e) {
            throw new MapperException(e.getTargetException() != null ? e.getTargetException() : e);
        }
    }

    /**
     * 通过xmlSql创建sqlSource
     *
     * @param ms
     * @param xmlSql
     * @return
     */
    public SqlSource createSqlSource(MappedStatement ms, String xmlSql) {
        return LANGUAGE_DRIVER.createSqlSource(ms.getConfiguration(), "<script>\n\t" + xmlSql + "</script>", null);
    }

    /**
     * 检查是否配置过缓存
     *
     * @param ms
     * @throws Exception
     */
    private void checkCache(MappedStatement ms) throws Exception {
        if (ms.getCache() == null) {
            String nameSpace = ms.getId().substring(0, ms.getId().lastIndexOf("."));
            Cache cache;
            try {
                // 不存在的时候会抛出异常
                cache = ms.getConfiguration().getCache(nameSpace);
            } catch (IllegalArgumentException e) {
                return;
            }
            if (cache != null) {
                MetaObject metaObject = SystemMetaObject.forObject(ms);
                metaObject.setValue("cache", cache);
            }
        }
    }

    /**
     * 重新设置SqlSource，同时判断如果是Jdbc3KeyGenerator，就设置为MultipleJdbc3KeyGenerator
     *
     * @param ms
     * @param sqlSource
     */
    protected void setSqlSource(MappedStatement ms, SqlSource sqlSource) {
        MetaObject msObject = SystemMetaObject.forObject(ms);
        msObject.setValue("sqlSource", sqlSource);
        // 如果是Jdbc3KeyGenerator，就设置为MultipleJdbc3KeyGenerator
        /*
         * KeyGenerator keyGenerator = ms.getKeyGenerator(); if (keyGenerator instanceof
         * Jdbc3KeyGenerator) { msObject.setValue("keyGenerator", new
         * MultipleJdbc3KeyGenerator()); }
         */
    }

    /**
     * 设置返回类型
     *
     * @param ms
     * @param entityTable 表
     * @param mapName     map名称
     */
    protected void setResultType(MappedStatement ms, EntityTable entityTable, String mapName) {
        List<ResultMap> resultMaps = new ArrayList<ResultMap>();
        resultMaps.add(getResultMap(entityTable, ms.getConfiguration(), mapName));
        MetaObject metaObject = SystemMetaObject.forObject(ms);
        metaObject.setValue("resultMaps", Collections.unmodifiableList(resultMaps));
    }

    /**
     * 设置 Page Map 类型
     *
     * @param ms
     * @param entityTable 表
     * @param mapName     map名称
     */
    protected void setPageMapType(MappedStatement ms, EntityTable entityTable, String mapName) {
        List<ResultMap> resultMaps = new ArrayList<ResultMap>();
        String id = entityTable.getEntityClass().getName() + "." + mapName;
        ResultMap.Builder builder = new ResultMap.Builder(ms.getConfiguration(), id, PageResult.class,
                new ArrayList<>(), false);
        resultMaps.add(builder.build());
        MetaObject metaObject = SystemMetaObject.forObject(ms);
        metaObject.setValue("resultMaps", Collections.unmodifiableList(resultMaps));
    }

    /**
     * 获取实体类型
     *
     * @param ms
     * @return
     */
    public Class<?> getEntityClass(MappedStatement ms) {
        return this.mapperCache.getEntityClass(ms);
    }

    /**
     * 获取实体表
     *
     * @param entityClass 实体类型
     * @return
     */
    public EntityTable getEntityTable(Class<?> entityClass) {
        return EntityTable.getTable(entityClass);
    }

    /**
     * 获取注册
     *
     * @return
     */
    public final MapperRegister getRapperRegister() {
        return rapperRegister;
    }

    /**
     * 获取驱动提供者
     *
     * @return
     */
    public final DbProvider getDbProvider() {
        return getRapperRegister().getDbProvider();
    }

}
