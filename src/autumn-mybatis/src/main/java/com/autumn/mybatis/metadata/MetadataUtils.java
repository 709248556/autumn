package com.autumn.mybatis.metadata;

import com.autumn.exception.ExceptionUtils;
import com.autumn.mybatis.mapper.MapperException;
import com.autumn.mybatis.mapper.SpecificationDefinition;
import com.autumn.mybatis.mapper.annotation.Index;
import com.autumn.mybatis.mapper.annotation.*;
import com.autumn.mybatis.mapper.impl.FirstUpperCaseSpecificationDefinitionImpl;
import com.autumn.mybatis.mapper.impl.OriginalSpecificationDefinitionImpl;
import com.autumn.mybatis.mapper.impl.StandardSpecificationDefinitionImpl;
import com.autumn.mybatis.wrapper.OrderDirectionEnum;
import com.autumn.util.StringUtils;
import com.autumn.util.TypeUtils;
import com.autumn.util.reflect.BeanProperty;
import com.autumn.util.reflect.ReflectUtils;
import com.autumn.util.tuple.TupleTwo;
import org.apache.commons.collections.map.CaseInsensitiveMap;

import javax.persistence.*;
import java.lang.reflect.Type;
import java.util.*;
import java.util.function.Supplier;

/**
 * 元工具
 *
 * @Description TODO
 * @Author 老码农
 * @Date 2019-08-29 15:25
 */
abstract class MetadataUtils {

    /**
     * 获取表规范
     *
     * @param entityClass
     * @return
     * @author 老码农 2017-12-04 11:51:30
     */
    public static SpecificationDefinition getTableSpecification(Class<?> entityClass) {
        TableSpecification specification = entityClass.getAnnotation(TableSpecification.class);
        if (specification == null || specification.specificationType() == null) {
            return EntityTable.getGlobalSpecification();
        }
        return getOrNewSpecificationInstance(specification.specificationType());
    }

    /**
     * 获取创建规范实例
     *
     * @param specificationTyp
     * @return
     */
    public static SpecificationDefinition getOrNewSpecificationInstance(Class<? extends SpecificationDefinition> specificationTyp) {
        if (specificationTyp == null || specificationTyp.equals(StandardSpecificationDefinitionImpl.class)) {
            return SpecificationDefinition.DEFAULT_DEFINITION;
        }
        if (specificationTyp.equals(OriginalSpecificationDefinitionImpl.class)) {
            return SpecificationDefinition.ORIGINAL_DEFINITION;
        }
        if (specificationTyp.equals(FirstUpperCaseSpecificationDefinitionImpl.class)) {
            return SpecificationDefinition.FIRST_UPPERCASE_DEFINITION;
        }
        try {
            return specificationTyp.newInstance();
        } catch (Exception e) {
            throw ExceptionUtils.throwConfigureException("无法实例化类型：" + specificationTyp);
        }
    }

    /**
     * 获取表信息
     *
     * @param entityClass
     * @return
     */
    public static TableInfo getTableInfo(Class<?> entityClass, SpecificationDefinition specificationDefinition) {
        TableInfo tableInfo = new TableInfo();
        Table table = entityClass.getAnnotation(Table.class);
        if (table != null) {
            if (StringUtils.isNullOrBlank(table.name())) {
                tableInfo.setName(specificationDefinition.defaultTableName(entityClass.getSimpleName()));
            } else {
                tableInfo.setName(table.name());
            }
            tableInfo.setSchema(!StringUtils.isNullOrBlank(table.schema()) ? table.schema() : "");
        } else {
            tableInfo.setName(specificationDefinition.defaultTableName(entityClass.getSimpleName()));
            tableInfo.setSchema("");
        }
        if (StringUtils.isNullOrBlank(tableInfo.getSchema())) {
            tableInfo.setFullName(tableInfo.getName());
        } else {
            tableInfo.setFullName(String.format("%s.%s", tableInfo.getSchema(), tableInfo.getName()));
        }
        ViewTable viewTable = entityClass.getAnnotation(ViewTable.class);
        if (viewTable != null) {
            tableInfo.setView(true);
        } else {
            tableInfo.setView(false);
        }
        TableEngine tableEngine = entityClass.getAnnotation(TableEngine.class);
        if (tableEngine != null) {
            tableInfo.setEngineName(tableEngine.value());
        } else {
            tableInfo.setEngineName("");
        }
        return tableInfo;
    }

    /**
     * 创建列集合
     *
     * @param entityClass 实体类型
     * @return
     */
    public static List<EntityColumn> createColumns(EntityTable table, Class<?> entityClass,
                                                   SpecificationDefinition specificationDefinition) {
        Collection<BeanProperty> propertys = ReflectUtils.getBeanPropertyMap(entityClass).values();
        List<EntityColumn> cols = new ArrayList<>(propertys.size());
        int count = 0;
        for (BeanProperty property : propertys) {
            if (TypeUtils.isBaseOrBinaryType(property.getType()) && !property.isAnnotation(Transient.class)) {
                EntityColumn column = new EntityColumn(table, property, specificationDefinition);
                if (column.isIdentityAssignKey() || column.isSequenceAssignKey() || column.isTableAssignKey()) {
                    count++;
                    if (count > 1) {
                        throw new MapperException(entityClass.getName() + " 的主键生成方式为非程序控制时，最多只支持一个。");
                    }
                }
                cols.add(column);
            }
        }
        Collections.sort(cols);
        return cols;
    }

    /**
     * 创建主键列
     *
     * @param cols
     * @return
     */
    public static List<EntityColumn> createKeyColumns(List<EntityColumn> cols) {
        List<EntityColumn> keyCols = new ArrayList<>(cols.size());
        for (EntityColumn col : cols) {
            if (col.isPrimaryKey()) {
                keyCols.add(col);
            }
        }
        return keyCols;
    }

    /**
     * 创建属性Map
     *
     * @param cols
     * @return
     */
    public static Map<String, EntityColumn> createPropertyMap(List<EntityColumn> cols) {
        Map<String, EntityColumn> map = new LinkedHashMap<>(cols.size());
        for (EntityColumn col : cols) {
            map.put(col.getPropertyName(), col);
        }
        return map;
    }

    /**
     * 创建列Map
     *
     * @param cols
     * @return
     */
    @SuppressWarnings("unchecked")
    public static Map<String, EntityColumn> createColumnMap(List<EntityColumn> cols) {
        Map<String, EntityColumn> map = new CaseInsensitiveMap(cols.size());
        for (EntityColumn col : cols) {
            map.put(col.getColumnName(), col);
        }
        return map;
    }

    /**
     * 创建排序列
     *
     * @param orderByValueSupplier
     * @param errorName
     * @param propertyMap
     * @return
     */
    public static TupleTwo<List<EntityColumnOrder>, String> createOrderColumns(Supplier<String> orderByValueSupplier,
                                                                               String errorName, Map<String, EntityColumn> propertyMap) {
        List<EntityColumnOrder> columns = new ArrayList<>(propertyMap.size());
        StringBuilder orderStr = new StringBuilder();
        String value = null;
        if (orderByValueSupplier != null) {
            value = orderByValueSupplier.get();
        }
        if (!StringUtils.isNullOrBlank(value)) {
            String[] items = value.split(",");
            for (String item : items) {
                if (!StringUtils.isNullOrBlank(item)) {
                    String[] orders = item.trim().split("\\s+");
                    EntityColumn col = propertyMap.get(orders[0]);
                    if (col == null) {
                        throw new MapperException(errorName + " 的排序属性 " + item + " 不存在!。");
                    }
                    OrderDirectionEnum direction = OrderDirectionEnum.ASC;
                    if (orders.length > 1) {
                        String order = orders[1];
                        if (OrderDirectionEnum.ASC.getValue().equalsIgnoreCase(order)) {
                            direction = OrderDirectionEnum.ASC;
                        } else if (OrderDirectionEnum.DESC.getValue().equalsIgnoreCase(order)) {
                            direction = OrderDirectionEnum.DESC;
                        } else {
                            throw new MapperException(errorName + " 的排序属性 " + item + " 的排序方式只能支持 Asc 或 Desc。");
                        }
                    }
                    EntityColumnOrder orderCol = new EntityColumnOrder(col, direction);
                    columns.add(orderCol);
                    if (orderStr.length() > 0) {
                        orderStr.append(",");
                    }
                    orderStr.append(orderCol.toString());
                }
            }
        }
        return new TupleTwo<>(columns, orderStr.toString());
    }

    private static void checkRelationProperty(Class<?> parentEntityClass, BeanProperty property, JoinColumn joinColumn) {
        if (!property.canReadWrite()) {
            throw new MapperException(parentEntityClass.getName()
                    + " 属性[" + property.getName() + "]绑定关系时，必须是可读写。");
        }
        if (StringUtils.isNullOrBlank(joinColumn.name())) {
            throw new MapperException(parentEntityClass.getName()
                    + " 属性[" + property.getName() + "]注解 JoinColumn 的 name 为空白值,无法建立关系。");
        }
        if (property.isArray()) {
            throw new MapperException(parentEntityClass.getName()
                    + " 属性[" + property.getName() + "]无法关联数组类型。");

        } else if (property.isMapType()) {
            throw new MapperException(parentEntityClass.getName()
                    + " 属性[" + property.getName() + "]无法关联 Map 类型。");
        } else {
            if (!property.isCollectionType() && property.isInterface()) {
                throw new MapperException(parentEntityClass.getName()
                        + " 属性[" + property.getName() + "]为一对一时，实体类型[" + property.getType().getName() + "]不能为接口类型。");
            }
        }
    }

    private static Class<?> relationClass(Class<?> parentEntityClass, BeanProperty property) {
        if (property.isCollectionType()) {
            Type type = TypeUtils.getCollectionItemType(property.getReadMethod().getGenericReturnType());
            if (!(type instanceof Class)) {
                throw new MapperException(parentEntityClass.getName()
                        + " 属性[" + property.getName() + "]关联的一对多时，集合参数必须是实体类型。");
            }
            return (Class<?>) type;
        } else {
            Type type = property.getReadMethod().getGenericReturnType();
            if (!(type instanceof Class)) {
                throw new MapperException(parentEntityClass.getName()
                        + " 属性[" + property.getName() + "]关联的一对一时，类型实体类型。");
            }
            return (Class<?>) type;
        }
    }

    /**
     * 创建关联集合
     *
     * @param parentTable
     * @param parentEntityClass
     * @return
     */
    public static List<EntityRelation> createRelations(EntityTable parentTable, Class<?> parentEntityClass) {
        Collection<BeanProperty> propertys = ReflectUtils.getBeanPropertyMap(parentEntityClass).values();
        List<EntityRelation> relations = new ArrayList<>(propertys.size());
        for (BeanProperty property : propertys) {
            if (!TypeUtils.isBaseOrBinaryType(property.getType()) && !property.isAnnotation(Transient.class)) {
                JoinColumn joinColumn = property.getAnnotation(JoinColumn.class);
                if (joinColumn != null) {
                    if (parentTable.getKeyColumns().size() != 1) {
                        throw new MapperException(parentEntityClass.getName()
                                + " 属性[" + property.getName() + "]关联的配置出错,父级对象不是唯一主键。");
                    }
                    checkRelationProperty(parentEntityClass, property, joinColumn);
                    EntityRelationMode mode;
                    if (property.isCollectionType()) {
                        mode = EntityRelationMode.ONE_TO_MANY;
                        OneToOne oneToOne = property.getAnnotation(OneToOne.class);
                        if (oneToOne != null) {
                            throw new MapperException(parentEntityClass.getName()
                                    + " 属性[" + property.getName() + "]关联的配置出错,集合类型只能配置一对多，不能配置一对一(OneToOne)。");
                        }
                        if (joinColumn.unique()) {
                            throw new MapperException(parentEntityClass.getName()
                                    + " 属性[" + property.getName() + "]关联的配置出错,集合类型只能配置一对多，不能配置为唯一(unique = true)。");
                        }
                    } else {
                        mode = EntityRelationMode.ONE_TO_ONE;
                        OneToMany oneToMany = property.getAnnotation(OneToMany.class);
                        if (oneToMany != null) {
                            throw new MapperException(parentEntityClass.getName()
                                    + " 属性[" + property.getName() + "]关联的配置出错,对象类型只能配置一对一，不能配置一对多(OneToMany)。");
                        }
                    }
                    ManyToMany manyToMany = property.getAnnotation(ManyToMany.class);
                    if (manyToMany != null) {
                        throw new MapperException(parentEntityClass.getName()
                                + " 属性[" + property.getName() + "]关联的配置出错,不支持多对多(ManyToMany)，只能通过逻辑配置。");
                    }
                    Class<?> relationType = relationClass(parentEntityClass, property);
                    if (relationType.equals(parentEntityClass)) {
                        throw new MapperException(parentEntityClass.getName()
                                + " 属性[" + property.getName() + "]关联的对象不能引用自身类型。");
                    }
                    if (TypeUtils.isBaseOrBinaryType(relationType)) {
                        throw new MapperException(parentEntityClass.getName()
                                + " 属性[" + property.getName() + "]关联的对象不能为基本类型[" + relationType.getName() + "]");
                    }
                    if (relationType.isInterface()) {
                        throw new MapperException(parentEntityClass.getName()
                                + " 属性[" + property.getName() + "]关联的对象不能为接口类型[" + relationType.getName() + "]");
                    }
                    if (relationType.isArray()) {
                        throw new MapperException(parentEntityClass.getName()
                                + " 属性[" + property.getName() + "]关联的对象不能为数组类型[" + relationType.getName() + "]");
                    }
                    if (Collection.class.isAssignableFrom(relationType)) {
                        throw new MapperException(parentEntityClass.getName()
                                + " 属性[" + property.getName() + "]关联的对象不能为集合类型[" + relationType.getName() + "]");
                    }
                    Map<String, BeanProperty> propertyMap = ReflectUtils.getBeanPropertyMap(relationType);
                    BeanProperty relationProperty = propertyMap.get(joinColumn.name());
                    if (relationProperty == null) {
                        throw new MapperException(parentEntityClass.getName()
                                + " 属性[" + property.getName() + "]关联对应的类型["
                                + relationType.getName() + "]不存在属性[" + joinColumn.name() + "]。");
                    }
                    if (!relationProperty.getType().equals(parentTable.getKeyColumns().get(0).getProperty().getType())) {
                        throw new MapperException(parentEntityClass.getName()
                                + " 属性[" + property.getName() + "]关联对应的类型["
                                + relationType.getName() + "]的属性[" + joinColumn.name() + "]类型与父级的主键类型不相同。");
                    }
                    EntityTable relationTable = EntityTable.getTable(relationType);
                    if (relationTable.getKeyColumns().size() != 1) {
                        //用于默认排序，级联删除等，否则会造成问题
                        throw new MapperException(parentEntityClass.getName()
                                + " 属性[" + property.getName() + "]关联对应的类型["
                                + relationType.getName() + "]，必须存在唯一主键。");
                    }
                    stackOverCheck(property, parentTable, relationTable);
                    EntityRelation relation = new EntityRelation(parentTable, property, mode, relationProperty, relationType, relationTable, joinColumn);
                    relations.add(relation);
                }
            }
        }
        return relations;
    }

    /**
     * 溢出检查
     *
     * @param property
     * @param rootTable
     * @param relationTable
     */
    private static void stackOverCheck(BeanProperty property, EntityTable rootTable, EntityTable relationTable) {
        for (EntityRelation relation : relationTable.getRelations()) {
            if (relation.getRelationTabe().getEntityClass().equals(rootTable.getEntityClass())) {
                throw new MapperException(rootTable.getEntityClass().getName()
                        + " 属性[" + property.getName() + "]关联出现循环引用。");
            }
            stackOverCheck(property, rootTable, relation.getRelationTabe());
        }
    }

    /**
     * 创建索引
     *
     * @param table 表
     * @return
     */
    public static List<EntityIndex> createEntityIndexs(EntityTable table) {
        List<EntityIndex> items = new ArrayList<>();
        List<EntityColumn> columns;
        for (EntityColumn column : table.getColumns()) {
            com.autumn.mybatis.mapper.annotation.Index index = column.getProperty().getAnnotation(Index.class);
            if (index != null) {
                columns = new ArrayList<>();
                columns.add(column);
                items.add(new EntityIndex(table, index.name(), index.unique(), columns));
            }
        }
        ComplexIndexs cIndexs = table.getEntityClass().getAnnotation(ComplexIndexs.class);
        if (cIndexs != null) {
            for (ComplexIndex index : cIndexs.indexs()) {
                if (index != null) {
                    columns = getIndexColumns(table, index);
                    items.add(new EntityIndex(table, index.name(), index.unique(), columns));
                }
            }
        }
        return items;
    }

    private static List<EntityColumn> getIndexColumns(EntityTable table, ComplexIndex index) {
        if (index.propertys() == null || index.propertys().length == 0) {
            ExceptionUtils.throwConfigureException("实体对象 " + table.getEntityClass().getName() + " 的复合索引至少需要一个属性以上。");
        }
        List<EntityColumn> columns = new ArrayList<>();
        for (String property : index.propertys()) {
            if (StringUtils.isNullOrBlank(property)) {
                ExceptionUtils.throwConfigureException(
                        "实体对象 " + table.getEntityClass().getName() + " 的复合索引包含无效的属性，属性名为 null 或空白值。");
            }
            EntityColumn column = table.getPropertyMap().get(property);
            if (column == null) {
                ExceptionUtils.throwConfigureException(
                        "实体对象 " + table.getEntityClass().getName() + " 的复合索引属性 " + property + " 不存在。");
            }
            columns.add(column);
        }
        return columns;
    }

    /**
     * 表信息
     */
    public static class TableInfo {
        private String name;
        private String schema;
        private String fullName;
        private boolean view;
        private String viewQueryStatement;
        private String engineName;

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getSchema() {
            return schema;
        }

        public void setSchema(String schema) {
            this.schema = schema;
        }

        public String getFullName() {
            return fullName;
        }

        public void setFullName(String fullName) {
            this.fullName = fullName;
        }

        public boolean isView() {
            return view;
        }

        public void setView(boolean view) {
            this.view = view;
        }

        public String getEngineName() {
            return engineName;
        }

        public void setEngineName(String engineName) {
            this.engineName = engineName;
        }
    }
}
