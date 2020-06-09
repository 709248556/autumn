package com.autumn.mybatis.mapper;

import com.autumn.exception.ExceptionUtils;
import com.autumn.mybatis.mapper.annotation.MapperProviderViewSelect;
import com.autumn.mybatis.mapper.annotation.MapperViewSelect;
import com.autumn.mybatis.metadata.EntityTable;
import com.autumn.mybatis.provider.DbProvider;
import com.autumn.util.StringUtils;

import java.lang.reflect.Method;

/**
 * Mapper 信息
 *
 * @author 老码农
 * <p>
 * 2017-12-08 14:29:13
 */
public class MapperInfo {

    private final String msId;
    private final Method method;
    private final Class<?> mapperClass;
    private final Class<?> entityClass;
    private final EntityTable entityTable;
    private final DbProvider dbProvider;

    /**
     * @param dbProvider  dbProvider 提供者
     * @param msId        myBatis 对象 id
     * @param method      方法
     * @param mapperClass
     * @param entityClass
     */
    public MapperInfo(DbProvider dbProvider, String msId, Method method, Class<?> mapperClass, Class<?> entityClass) {
        this.dbProvider = dbProvider;
        this.msId = msId;
        this.method = method;
        this.mapperClass = mapperClass;
        this.entityClass = entityClass;
        if (entityClass != null) {
            this.entityTable = EntityTable.getTable(entityClass);
            if (this.entityTable.isView()
                    && StringUtils.isNullOrBlank(this.entityTable.getViewQueryStatement())) {
                this.entityTable.setViewQueryStatement(getTableViewSql(dbProvider, this.entityTable, mapperClass));
            }
        } else {
            this.entityTable = null;
        }
    }

    private static String getTableViewSql(DbProvider dbProvider, EntityTable entityTable, Class<?> mapperClass) {
        MapperViewSelect mapperViewSelect = mapperClass.getAnnotation(MapperViewSelect.class);
        if (mapperViewSelect == null) {
            String errMsg = "类型[" + entityTable.getEntityClass().getName() + "]为视图类型，" +
                    "但在 Mapper [" + mapperClass.getName() + "]未配置 [" + MapperViewSelect.class.getName() + "] 注解，无法获取查询代码。";
            throw ExceptionUtils.throwConfigureException(errMsg);
        }
        if (StringUtils.isNullOrBlank(mapperViewSelect.value())) {
            String errMsg = "类型[" + entityTable.getEntityClass().getName() + "]为视图类型，" +
                    "但在 Mapper [" + mapperClass.getName() + "] [" + MapperViewSelect.class.getName() + "] 注解默认的 value 为空白值。";
            throw ExceptionUtils.throwConfigureException(errMsg);
        }
        if (mapperViewSelect.providerViewSelect() != null
                && mapperViewSelect.providerViewSelect().length > 0) {
            for (MapperProviderViewSelect viewSelect
                    : mapperViewSelect.providerViewSelect()) {
                if (viewSelect.driveType().equals(dbProvider.getDriveType())) {
                    if (StringUtils.isNotNullOrBlank(viewSelect.value())) {
                        return viewSelect.value();
                    }
                }
            }
        }
        return mapperViewSelect.value();
    }

    /**
     * 获取 msId
     *
     * @return 2017-12-08 14:31:38
     */
    public String getMsId() {
        return this.msId;
    }

    /**
     * 获取方法
     *
     * @return 2017-12-08 14:48:45
     */
    public Method getMethod() {
        return this.method;
    }

    /**
     * 获取 Mapper 类名
     *
     * @return 2017-12-08 14:32:02
     */
    public Class<?> getMapperClass() {
        return this.mapperClass;
    }

    /**
     * 获取实体类型
     *
     * @return 如果来源于 {@link EntityMapper} 时，则 not null，否则 null。
     */
    public Class<?> getEntityClass() {
        return this.entityClass;
    }

    /**
     * 获取表
     *
     * @return {@link #getEntityClass()} 非 null 时返回，否则返回 null
     */
    public EntityTable getEntityTable() {
        return this.entityTable;
    }

    /**
     * 获取提供者
     *
     * @return
     */
    public DbProvider getDbProvider() {
        return this.dbProvider;
    }
}
