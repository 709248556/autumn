package com.autumn.mybatis.metadata;

import com.autumn.exception.ExceptionUtils;
import com.autumn.mybatis.mapper.SpecificationDefinition;
import com.autumn.mybatis.mapper.annotation.TableOrderBy;
import com.autumn.util.reflect.ReflectUtils;
import com.autumn.util.tuple.TupleTwo;
import com.esotericsoftware.reflectasm.ConstructorAccess;

import javax.persistence.GenerationType;
import java.io.Serializable;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * 实体表
 *
 * @author 老码农
 * <p>
 * 2017-09-27 17:18:48
 */
public final class EntityTable implements Serializable {

    /**
     *
     */
    private static final long serialVersionUID = 2597268218663658578L;

    /**
     * 默认实体数量,实例可用 500 * 0.75
     */
    public static final int DEFAULT_ENTITY_NUMBER = 500;

    private static final Map<Class<?>, EntityTable> ENTITY_TABLE_MAP = new ConcurrentHashMap<>(DEFAULT_ENTITY_NUMBER);
    private static SpecificationDefinition globalSpecification = SpecificationDefinition.DEFAULT_DEFINITION;

    /**
     * 获取表
     *
     * @param entityClass 实体类型
     * @return
     * @author 老码农 2017-09-27 17:23:47
     */
    public static EntityTable getTable(Class<?> entityClass) {
        return ENTITY_TABLE_MAP.computeIfAbsent(entityClass, EntityTable::new);
    }

    /**
     * 获取表集合
     *
     * @return
     */
    public static Collection<EntityTable> getTables() {
        return ENTITY_TABLE_MAP.values();
    }

    /**
     * 获取全局规范
     *
     * @return
     */
    public static SpecificationDefinition getGlobalSpecification() {
        return EntityTable.globalSpecification;
    }

    /**
     * 设置全局规范
     *
     * @param globalSpecification 全局规范
     */
    public static void setGlobalSpecification(SpecificationDefinition globalSpecification) {
        EntityTable.globalSpecification = ExceptionUtils.checkNotNull(globalSpecification, "globalSpecification");
    }

    /**
     * 设置全局规范类型
     *
     * @param specificationTyp 规范类型
     */
    public static void setGlobalSpecificationType(Class<? extends SpecificationDefinition> specificationTyp) {
        setGlobalSpecification(MetadataUtils.getOrNewSpecificationInstance(specificationTyp));
    }

    private final Class<?> entityClass;
    private final String name;
    private final String schema;
    private final List<EntityColumn> columns;
    private final List<EntityRelation> relations;
    private final Map<String, EntityColumn> propertyMap;
    private final Map<String, EntityColumn> columnMap;
    private final List<EntityColumnOrder> orderColumns;
    private final String orderByClause;
    private final String fullName;
    private final ConstructorAccess<?> constructorAccess;
    private final List<EntityColumn> keyColumns;
    private final List<EntityIndex> indexs;
    private final String engineName;
    private final boolean view;
    private String viewQueryStatement;
    private final boolean tableAssignKey;
    private final boolean identityAssignKey;
    private final boolean sequenceAssignKey;
    private final boolean autoAssignKey;

    /**
     * 实例化 EntityTable 类新实例
     *
     * @param entityClass 实体类
     */
    private EntityTable(Class<?> entityClass) {
        this.entityClass = ExceptionUtils.checkNotNull(entityClass, "entityClass");
        SpecificationDefinition specificationDefinition = MetadataUtils.getTableSpecification(entityClass);
        MetadataUtils.TableInfo tableInfo = MetadataUtils.getTableInfo(entityClass, specificationDefinition);
        //ForeignKey 工作;
        this.name = tableInfo.getName();
        this.schema = tableInfo.getSchema();
        this.fullName = tableInfo.getFullName();
        this.view = tableInfo.isView();
        this.viewQueryStatement = "";
        this.engineName = tableInfo.getEngineName();
        this.columns = Collections.unmodifiableList(MetadataUtils.createColumns(this, entityClass, specificationDefinition));
        this.keyColumns = Collections.unmodifiableList(MetadataUtils.createKeyColumns(columns));
        this.tableAssignKey = this.keyColumns.size() == 1 && this.keyColumns.get(0).getGenerationType().equals(GenerationType.TABLE);
        this.identityAssignKey = this.keyColumns.size() == 1 && this.keyColumns.get(0).getGenerationType().equals(GenerationType.IDENTITY);
        this.sequenceAssignKey = this.keyColumns.size() == 1 && this.keyColumns.get(0).getGenerationType().equals(GenerationType.SEQUENCE);
        this.autoAssignKey = this.keyColumns.size() > 0 && !(this.tableAssignKey || this.identityAssignKey || this.sequenceAssignKey);

        // 若非需要主键会造成部份视图不何用
        // if (this.keyColumns.size() == 0) {
        // throw new MapperException(entityClass.getName() + " 至少需要配置一个主键。");
        // }
        this.propertyMap = Collections.unmodifiableMap(MetadataUtils.createPropertyMap(this.columns));
        this.columnMap = Collections.unmodifiableMap(MetadataUtils.createColumnMap(this.columns));
        TupleTwo<List<EntityColumnOrder>, String> orderTuple = createOrderColumns(entityClass, this.propertyMap);
        this.orderColumns = Collections.unmodifiableList(orderTuple.getItem1());
        this.orderByClause = orderTuple.getItem2();
        this.constructorAccess = ReflectUtils.getConstructorAccess(this.entityClass);
        this.indexs = Collections.unmodifiableList(MetadataUtils.createEntityIndexs(this));
        this.relations = Collections.unmodifiableList(MetadataUtils.createRelations(this, entityClass));
    }

    /**
     * @param entityClass
     * @param propertyMap
     * @return
     */
    private TupleTwo<List<EntityColumnOrder>, String> createOrderColumns(Class<?> entityClass,
                                                                         Map<String, EntityColumn> propertyMap) {
        return MetadataUtils.createOrderColumns(() -> {
            TableOrderBy tableOrderBy = entityClass.getAnnotation(TableOrderBy.class);
            if (tableOrderBy != null) {
                return tableOrderBy.value();
            }
            return null;
        }, entityClass.getCanonicalName(), propertyMap);
    }

    /**
     * 创建实体新实例
     *
     * @return
     */
    public Object newEntityInstance() {
        return this.constructorAccess.newInstance();
    }

    /**
     * 获取实体类型
     *
     * @return
     * @author 老码农 2017-09-27 17:20:15
     */
    public final Class<?> getEntityClass() {
        return this.entityClass;
    }

    /**
     * 获取列集合
     *
     * @return
     * @author 老码农 2017-09-27 17:50:28
     */
    public final List<EntityColumn> getColumns() {
        return this.columns;
    }

    /**
     * 获取关联集合
     *
     * @return
     */
    public final List<EntityRelation> getRelations() {
        return this.relations;
    }

    /**
     * 获取属性 Map
     *
     * @return
     * @author 老码农 2017-09-27 17:52:44
     */
    public final Map<String, EntityColumn> getPropertyMap() {
        return this.propertyMap;
    }

    /**
     * 获取列的 Map(不区分大小写)
     *
     * @return
     * @author 老码农 2017-09-27 17:52:51
     */
    public final Map<String, EntityColumn> getColumnMap() {
        return this.columnMap;
    }

    /**
     * 获取主键列集合
     *
     * @return
     */
    public final List<EntityColumn> getKeyColumns() {
        return this.keyColumns;
    }

    /**
     * 获取索引集合
     *
     * @return
     */
    public final List<EntityIndex> getIndexs() {
        return this.indexs;
    }

    /**
     * 获取表名称
     *
     * @return
     * @author 老码农 2017-09-27 17:58:48
     */
    public final String getName() {
        return this.name;
    }

    /**
     * 获取架构
     *
     * @return
     * @author 老码农 2017-09-27 17:58:54
     */
    public final String getSchema() {
        return this.schema;
    }

    /**
     * 是否是视图
     *
     * @return
     */
    public final boolean isView() {
        return this.view;
    }

    /**
     * 获取视图查询命令
     *
     * @return
     */
    public String getViewQueryStatement() {
        return this.viewQueryStatement;
    }

    /**
     * 设置视图查询命令
     *
     * @param viewQueryStatement 视图查询命令
     */
    public void setViewQueryStatement(String viewQueryStatement) {
        this.viewQueryStatement = viewQueryStatement;
    }

    /**
     * 获取完整名称
     *
     * @return
     */
    public String getFullName() {
        return this.fullName;
    }

    /**
     * 获取引擎名称
     *
     * @return
     */
    public String getEngineName() {
        return this.engineName;
    }

    @Override
    public String toString() {
        return this.getFullName();
    }

    /**
     * 获取排序列
     *
     * @return
     */
    public List<EntityColumnOrder> getOrderColumns() {
        return orderColumns;
    }

    /**
     * 获取排序
     *
     * @return
     */
    public String getOrderByClause() {
        return orderByClause;
    }

    /**
     * 获取主键属性名称集
     *
     * @return
     */
    public String[] getKeyPropertieNames() {
        List<EntityColumn> keyCols = this.getKeyColumns();
        String[] keys = new String[keyCols.size()];
        for (int i = 0; i < keyCols.size(); i++) {
            keys[i] = keyCols.get(i).getPropertyName();
        }
        return keys;
    }

    /**
     * 是否为自动分配主键
     *
     * @return
     */
    public final boolean isIdentityAssignKey() {
        return this.identityAssignKey;
    }

    /**
     * 是否为表分配主键
     *
     * @return
     */
    public final boolean isTableAssignKey() {
        return this.tableAssignKey;
    }

    /**
     * 是否为序列分配主键
     *
     * @return
     */
    public final boolean isSequenceAssignKey() {
        return this.sequenceAssignKey;
    }

    /**
     * 是否为自动分配主键
     *
     * @return
     */
    public final boolean isAutoAssignKey() {
        return this.autoAssignKey;
    }

    /**
     * 创建主键列名称集
     *
     * @return
     */
    public String[] createKeyColumnNames() {
        List<EntityColumn> keyCols = this.getKeyColumns();
        String[] keys = new String[keyCols.size()];
        for (int i = 0; i < keyCols.size(); i++) {
            keys[i] = keyCols.get(i).getColumnName();
        }
        return keys;
    }

    @Override
    public int hashCode() {
        return this.getEntityClass().hashCode();
    }

    @Override
    public boolean equals(Object obj) {
        if (obj instanceof EntityTable) {
            EntityTable table = (EntityTable) obj;
            return this.getEntityClass().equals(table.getEntityClass());
        }
        return false;
    }
}
