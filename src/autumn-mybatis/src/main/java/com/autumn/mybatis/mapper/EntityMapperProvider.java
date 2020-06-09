package com.autumn.mybatis.mapper;

import com.autumn.mybatis.metadata.EntityColumn;
import com.autumn.mybatis.metadata.EntityTable;
import com.autumn.mybatis.provider.InsertSelectKey;
import org.apache.ibatis.executor.keygen.KeyGenerator;
import org.apache.ibatis.executor.keygen.NoKeyGenerator;
import org.apache.ibatis.executor.keygen.SelectKeyGenerator;
import org.apache.ibatis.mapping.*;
import org.apache.ibatis.reflection.MetaObject;
import org.apache.ibatis.reflection.SystemMetaObject;
import org.apache.ibatis.scripting.defaults.RawSqlSource;
import org.apache.ibatis.session.Configuration;

import javax.persistence.GenerationType;
import java.util.ArrayList;
import java.util.List;

/**
 * 实体驱动提供者
 *
 * @author 老码农
 * <p>
 * 2017-10-11 17:49:40
 */
public class EntityMapperProvider extends MapperProvider {

    private static final String METAOBJECT_PROPERTY_KEY_GENERATOR = "keyGenerator";
    private static final String METAOBJECT_PROPERTY_KEY_PROPERTIES = "keyProperties";
    private static final String METAOBJECT_PROPERTY_KEY_COLUMNS = "keyColumns";

    /**
     * @param mapperClass
     * @param rapperRegister
     */
    public EntityMapperProvider(Class<?> mapperClass, MapperRegister rapperRegister) {
        super(mapperClass, rapperRegister);
    }

    /**
     * @param ms
     * @param column
     */
    private void setInsertSelectKey(MappedStatement ms, EntityColumn column) {
        String keyId = ms.getId() + SelectKeyGenerator.SELECT_KEY_SUFFIX;
        if (ms.getConfiguration().hasKeyGenerator(keyId)) {
            return;
        }
        Class<?> entityClass = column.getTable().getEntityClass();
        Configuration configuration = ms.getConfiguration();
        InsertSelectKey insertSelectKey = this.getDbProvider().getInsertBuilder().getIdentitySelectKey(column);
        KeyGenerator keyGenerator;
        SqlSource sqlSource = new RawSqlSource(configuration, insertSelectKey.getSelectKeySql(), entityClass);
        MappedStatement.Builder statementBuilder = new MappedStatement.Builder(configuration, keyId, sqlSource,
                SqlCommandType.SELECT);
        statementBuilder.resource(ms.getResource());
        statementBuilder.fetchSize(null);
        statementBuilder.statementType(StatementType.STATEMENT);
        statementBuilder.keyGenerator(new NoKeyGenerator());
        statementBuilder.keyProperty(column.getPropertyName());
        statementBuilder.keyColumn(null);
        statementBuilder.databaseId(null);
        statementBuilder.lang(configuration.getDefaultScriptingLanguageInstance());
        statementBuilder.resultOrdered(false);
        statementBuilder.resultSets(null);
        statementBuilder.timeout(configuration.getDefaultStatementTimeout());

        List<ParameterMapping> parameterMappings = new ArrayList<>();
        ParameterMap.Builder inlineParameterMapBuilder = new ParameterMap.Builder(configuration,
                statementBuilder.id() + "-Inline", entityClass, parameterMappings);
        statementBuilder.parameterMap(inlineParameterMapBuilder.build());

        List<ResultMap> resultMaps = new ArrayList<>();
        ResultMap.Builder inlineResultMapBuilder = new ResultMap.Builder(configuration,
                statementBuilder.id() + "-Inline", column.getJavaType(), new ArrayList<>(), null);
        resultMaps.add(inlineResultMapBuilder.build());
        statementBuilder.resultMaps(resultMaps);
        statementBuilder.resultSetType(null);
        statementBuilder.flushCacheRequired(false);
        statementBuilder.useCache(false);
        statementBuilder.cache(null);
        MappedStatement statement = statementBuilder.build();
        configuration.addMappedStatement(statement);
        MappedStatement keyStatement = configuration.getMappedStatement(keyId, false);
        keyGenerator = new SelectKeyGenerator(keyStatement, insertSelectKey.isExecuteBefore());
        configuration.addKeyGenerator(keyId, keyGenerator);
        MetaObject msObject = SystemMetaObject.forObject(ms);
        msObject.setValue(METAOBJECT_PROPERTY_KEY_GENERATOR, keyGenerator);
        msObject.setValue(METAOBJECT_PROPERTY_KEY_PROPERTIES, column.getTable().getKeyPropertieNames());
        msObject.setValue(METAOBJECT_PROPERTY_KEY_COLUMNS, column.getTable().createKeyColumnNames());
    }

    /**
     * 插入
     *
     * @param ms
     * @return
     */
    public String insert(MappedStatement ms) {
        EntityTable table = this.getEntityTable(this.getEntityClass(ms));
        for (EntityColumn col : table.getColumns()) {
            if (col.isPrimaryKey() && col.getGenerationType().equals(GenerationType.IDENTITY)) {
                setInsertSelectKey(ms, col);
            }
        }
        return this.getDbProvider().getInsertBuilder().getInsertCommand(table);
    }

    /**
     * 插入子级
     *
     * @param ms
     * @return
     */
    public String insertChildren(MappedStatement ms) {
        EntityTable table = this.getEntityTable(this.getEntityClass(ms));
        setPageMapType(ms, table, "MapperInsertChildrenMap");
        return "select null";
    }

    /**
     * 批量插入
     *
     * @param
     * @return
     */
    public String insertByList(MappedStatement ms) {
        EntityTable table = this.getEntityTable(this.getEntityClass(ms));
        return this.getDbProvider().getInsertBuilder().getInsertByListCommand(table, EntityMapper.ARG_NAME_LIST);
    }

    /**
     * 快速清除表数据
     *
     * @param ms
     * @return
     */
    public String truncate(MappedStatement ms) {
        EntityTable table = this.getEntityTable(this.getEntityClass(ms));
        return this.getDbProvider().getDeleteBuilder().getTruncateCommand(table);
    }

    /**
     * 删除所有数据
     *
     * @param ms
     * @return
     */
    public String deleteByAll(MappedStatement ms) {
        EntityTable table = this.getEntityTable(this.getEntityClass(ms));
        return this.getDbProvider().getDeleteBuilder().getDeleteByAllCommand(table);
    }

    /**
     * 基于主键进行删除
     *
     * @param ms
     * @return
     */
    public String deleteById(MappedStatement ms) {
        EntityTable table = this.getEntityTable(this.getEntityClass(ms));
        return this.getDbProvider().getDeleteBuilder().getDeleteByIdCommand(table);
    }

    /**
     * 仅删除子级,不会删除当前表的记录
     *
     * @param ms
     * @return
     */
    public String deleteChildrenById(MappedStatement ms) {
        EntityTable table = this.getEntityTable(this.getEntityClass(ms));
        setPageMapType(ms, table, "MapperDeleteChildrenById");
        return "select null";
    }

    /**
     * 基于条件进行删除
     *
     * @param ms
     * @return
     */
    public String deleteByWhere(MappedStatement ms) {
        EntityTable table = this.getEntityTable(this.getEntityClass(ms));
        return this.getDbProvider().getDeleteBuilder().getDeleteByWhereCommand(table, EntityMapper.ARG_NAME_WRAPPER_FULL);
    }

    /**
     * 基于主键进行更新,全部更新
     *
     * @param ms
     * @return
     */
    public String update(MappedStatement ms) {
        EntityTable table = this.getEntityTable(this.getEntityClass(ms));
        return this.getDbProvider().getUpdateBuilder().getUpdateCommand(table, false);
    }

    /**
     * 基于对象进行更新，包括null值。并重置子级(先删除子级，再添加子级)
     *
     * @param ms
     * @return
     */
    public String updateAndResetChildren(MappedStatement ms) {
        EntityTable table = this.getEntityTable(this.getEntityClass(ms));
        return this.getDbProvider().getUpdateBuilder().getUpdateCommand(table, false);
    }

    /**
     * 基于主键进行更新,跳过null值
     *
     * @param ms
     * @return
     */
    public String updateByNotNull(MappedStatement ms) {
        EntityTable table = this.getEntityTable(this.getEntityClass(ms));
        return this.getDbProvider().getUpdateBuilder().getUpdateCommand(table, true);
    }

    /**
     * 修改指定字段
     *
     * @param ms
     * @return
     */
    public String updateByWhere(MappedStatement ms) {
        EntityTable table = this.getEntityTable(this.getEntityClass(ms));
        return this.getDbProvider().getUpdateBuilder().getUpdateByWhereCommand(table, EntityMapper.ARG_NAME_WRAPPER_FULL);
    }

    /**
     * 基于主键查询
     *
     * @param ms
     * @return
     */
    public String get(MappedStatement ms) {
        EntityTable table = this.getEntityTable(this.getEntityClass(ms));
        setResultType(ms, table, "MapperGetMap");
        return this.getDbProvider().getQueryBuilder().getSelectByIdCommand(table);
    }

    /**
     * 基于主键查询并加载关联
     *
     * @param ms
     * @return
     */
    public String getAndLoad(MappedStatement ms) {
        EntityTable table = this.getEntityTable(this.getEntityClass(ms));
        setResultType(ms, table, "MapperGetAndLoadMap");
        return this.getDbProvider().getQueryBuilder().getSelectByIdCommand(table);
    }

    /**
     * 基于主键查询
     *
     * @param ms
     * @return
     */
    public String getByLock(MappedStatement ms) {
        EntityTable table = this.getEntityTable(this.getEntityClass(ms));
        setResultType(ms, table, "MapperGetByLockMap");
        return this.getDbProvider().getQueryBuilder().getSelectByIdCommand(table, EntityMapper.ARG_NAME_ID, EntityMapper.ARG_NAME_LOCK_MODE);
    }

    /**
     * 基于主键查询并加载关联
     *
     * @param ms
     * @return
     */
    public String getByLockAndLoad(MappedStatement ms) {
        EntityTable table = this.getEntityTable(this.getEntityClass(ms));
        setResultType(ms, table, "MapperGetLoadByLockMap");
        return this.getDbProvider().getQueryBuilder().getSelectByIdCommand(table, EntityMapper.ARG_NAME_ID, EntityMapper.ARG_NAME_LOCK_MODE);
    }

    /**
     * 加载关联实体
     *
     * @param ms
     * @return
     */
    public String load(MappedStatement ms) {
        EntityTable table = this.getEntityTable(this.getEntityClass(ms));
        setPageMapType(ms, table, "MapperLoadMap");
        return "select null";
    }

    /**
     * 查询实体首条记录
     *
     * @param ms
     * @return
     */
    public String selectForFirst(MappedStatement ms) {
        EntityTable table = this.getEntityTable(this.getEntityClass(ms));
        setResultType(ms, table, "MapperSelectByFirstMap");
        return this.getDbProvider().getQueryBuilder().getSelectForFirstCommand(table,
                EntityMapper.ARG_NAME_WRAPPER_FULL);
    }

    /**
     * 查询实体首条记录和加载关联
     *
     * @param ms
     * @return
     */
    public String selectForFirstAndLoad(MappedStatement ms) {
        EntityTable table = this.getEntityTable(this.getEntityClass(ms));
        setResultType(ms, table, "MapperSelectLoadByFirstMap");
        return this.getDbProvider().getQueryBuilder().getSelectForFirstCommand(table, EntityMapper.ARG_NAME_WRAPPER_FULL);
    }

    /**
     * 查询得到list
     *
     * @param ms
     * @return
     */
    public String selectForList(MappedStatement ms) {
        EntityTable table = this.getEntityTable(this.getEntityClass(ms));
        setResultType(ms, table, "MapperSelectByListMap");
        return this.getDbProvider().getQueryBuilder().getSelectForListCommand(table, EntityMapper.ARG_NAME_WRAPPER_FULL);
    }

    /**
     * 全部查询
     *
     * @param ms
     * @return
     */
    public String selectForAll(MappedStatement ms) {
        EntityTable table = this.getEntityTable(this.getEntityClass(ms));
        setResultType(ms, table, "MapperSelectForAllMap");
        return this.getDbProvider().getQueryBuilder().getSelectForAllCommand(table);
    }

    /**
     * 分页查询
     *
     * @param ms
     * @return
     */
    public String selectForPage(MappedStatement ms) {
        EntityTable table = this.getEntityTable(this.getEntityClass(ms));
        setPageMapType(ms, table, "MapperSelectForPageMap");
        return "select null";
    }

    /**
     * 分页查询并转换
     *
     * @param ms
     * @return
     */
    public String selectForPageConvert(MappedStatement ms) {
        EntityTable table = this.getEntityTable(this.getEntityClass(ms));
        setPageMapType(ms, table, "MapperSelectForPageConvertMap");
        return "select null";
    }

    /**
     * 分页查询并转换和处理
     *
     * @param ms
     * @return
     */
    public String selectForPageConvertAction(MappedStatement ms) {
        EntityTable table = this.getEntityTable(this.getEntityClass(ms));
        setPageMapType(ms, table, "MapperSelectForPageConvertActionMap");
        return "select null";
    }

    /**
     * Map First 首条记录
     *
     * @param ms
     * @return
     */
    public String selectForMapFirst(MappedStatement ms) {
        EntityTable table = this.getEntityTable(this.getEntityClass(ms));
        return this.getDbProvider().getQueryBuilder().getSelectForMapFirstCommand(table, EntityMapper.ARG_NAME_WRAPPER_FULL);
    }

    /**
     * Map 列表查询结果
     *
     * @param ms
     * @return
     */
    public String selectForMapList(MappedStatement ms) {
        EntityTable table = this.getEntityTable(this.getEntityClass(ms));
        return this.getDbProvider().getQueryBuilder().getSelectForMapListCommand(table, EntityMapper.ARG_NAME_WRAPPER_FULL);
    }

    /**
     * Map 分页查询结果
     *
     * @param ms
     * @return
     */
    public String selectForMapPage(MappedStatement ms) {
        EntityTable table = this.getEntityTable(this.getEntityClass(ms));
        setPageMapType(ms, table, "MapperSelectForMapPageMap");
        return "select null";
    }

    /**
     * 全部查询记录数
     *
     * @param ms
     * @return
     */
    public String count(MappedStatement ms) {
        EntityTable table = this.getEntityTable(this.getEntityClass(ms));
        return this.getDbProvider().getQueryBuilder().getCountCommand(table);
    }

    /**
     * 根据条件查询记录数
     *
     * @param ms
     * @return
     */
    public String countByWhere(MappedStatement ms) {
        EntityTable table = this.getEntityTable(this.getEntityClass(ms));
        return this.getDbProvider().getQueryBuilder().getCountByWhereCommand(table, EntityMapper.ARG_NAME_WRAPPER_FULL);
    }

    /**
     * 唯一结果(只有一个字段值)
     *
     * @param ms
     * @return
     */
    public String uniqueResult(MappedStatement ms) {
        EntityTable table = this.getEntityTable(this.getEntityClass(ms));
        return this.getDbProvider().getQueryBuilder().getSelectForMapFirstCommand(table, EntityMapper.ARG_NAME_WRAPPER_FULL);
    }

}
