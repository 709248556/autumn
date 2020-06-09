package com.autumn.mybatis.metadata;

import com.autumn.annotation.FriendlyProperty;
import com.autumn.mybatis.mapper.MapperException;
import com.autumn.mybatis.mapper.PropertyMapperUtils;
import com.autumn.mybatis.mapper.SpecificationDefinition;
import com.autumn.mybatis.mapper.annotation.ColumnOrder;
import com.autumn.mybatis.provider.DbProvider;
import com.autumn.util.StringUtils;
import com.autumn.util.TypeUtils;
import com.autumn.util.reflect.BeanProperty;
import com.autumn.util.reflect.ReflectUtils;
import com.autumn.util.tuple.TupleTwo;
import com.esotericsoftware.reflectasm.ConstructorAccess;
import org.apache.ibatis.type.JdbcType;
import org.apache.ibatis.type.TypeHandler;

import javax.persistence.Column;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import java.io.Serializable;

/**
 * 实体列
 *
 * @author 老码农
 * <p>
 * 2017-09-27 17:17:46
 */
public final class EntityColumn implements Comparable<EntityColumn>, Serializable {

    /**
     *
     */
    private static final long serialVersionUID = 5192599894162062927L;
    private final EntityTable table;
    private final BeanProperty property;
    private final String propertyName;
    private final String columnName;
    private final String friendlyName;
    private final Class<?> javaType;
    private final JdbcType jdbcType;
    private final Class<? extends TypeHandler<?>> typeHandlerClass;
    private final boolean primaryKey;
    private final boolean insertable;
    private final boolean updatable;
    private final boolean nullable;
    private final int columnOrder;
    private final int length;
    private final int precision;
    private final String sequenceName;
    private final int scale;
    private final String orderBy;
    private final GenerationType generationType;
    private final ConstructorAccess<? extends TypeHandler<?>> typeHandlerConstructorAccess;
    private final String columnHolder;

    /**
     * 实例化 EntityColumn 类
     *
     * @param table                   表
     * @param property                属性
     * @param specificationDefinition 规范
     */
    EntityColumn(EntityTable table, BeanProperty property, SpecificationDefinition specificationDefinition)
            throws MapperException {
        if (!TypeUtils.isBaseOrBinaryType(property.getType())) {
            throw new MapperException("不支持 " + property.getType().getName() + " 类型映射。");
        }
        if (specificationDefinition == null) {
            specificationDefinition = SpecificationDefinition.DEFAULT_DEFINITION;
        }
        this.table = table;
        this.property = property;
        this.javaType = property.getType();
        this.propertyName = this.property.getName();
        Column column = PropertyMapperUtils.getOrDefaultColumn(property, specificationDefinition);
        this.columnName = !StringUtils.isNullOrBlank(column.name()) ? column.name()
                : PropertyMapperUtils.getOrDefaultColumnName(property, specificationDefinition);
        FriendlyProperty friendlyProperty = property.getAnnotation(FriendlyProperty.class);
        if (friendlyProperty != null) {
            this.friendlyName = friendlyProperty.value();
        } else {
            this.friendlyName = "";
        }
        this.insertable = column.insertable();
        this.updatable = column.updatable();
        this.length = this.javaType.equals(String.class) ? column.length() : 0;
        this.precision = column.precision();
        this.scale = column.scale();
        this.primaryKey = property.isAnnotation(Id.class);
        ColumnOrder annColumnOrder = property.getAnnotation(ColumnOrder.class);
        if (annColumnOrder != null) {
            this.columnOrder = annColumnOrder.value();
        } else {
            this.columnOrder = 0;
        }
        if (this.primaryKey) {
            this.sequenceName = TypeUtils.isIntegerType(this.javaType) ? PropertyMapperUtils.getColumnSequenceName(property) : "";
            this.generationType = PropertyMapperUtils.getKeyColumnGenerationType(table.getEntityClass(), property);
            if (this.generationType.equals(GenerationType.SEQUENCE) && StringUtils.isNullOrBlank(this.sequenceName)) {
                throw new MapperException("主键生成方式为序列(SEQUENCE)时 sequenceName 的值不能为空或空白值。");
            }
            this.nullable = false;
        } else {
            this.generationType = GenerationType.AUTO;
            this.sequenceName = "";
            this.nullable = column.nullable();
        }
        TupleTwo<JdbcType, Class<? extends TypeHandler<?>>> typeMap = PropertyMapperUtils.getPropertyTypeMap(property);
        this.jdbcType = typeMap.getItem1();
        this.typeHandlerClass = typeMap.getItem2();
        this.typeHandlerConstructorAccess = ReflectUtils.getConstructorAccess(this.typeHandlerClass);
        this.orderBy = PropertyMapperUtils.getColumnOrderBy(property);
        this.columnHolder = getColumnHolder(false, null);
    }

    /**
     * 获取属性
     *
     * @return
     * @author 老码农 2017-09-29 10:05:50
     */
    public BeanProperty getProperty() {
        return this.property;
    }

    /**
     * 获取对应的表
     *
     * @return
     * @author 老码农 2017-09-28 09:50:36
     */
    public EntityTable getTable() {
        return this.table;
    }

    /**
     * 获取Java类型
     *
     * @return
     * @author 老码农 2017-09-28 09:50:47
     */
    public Class<?> getJavaType() {
        return javaType;
    }

    /**
     * 获取Jdbc类型
     *
     * @return
     * @author 老码农 2017-09-28 09:51:12
     */
    public JdbcType getJdbcType() {
        return jdbcType;
    }

    /**
     * 获取类型处理器类
     *
     * @return
     */
    public Class<? extends TypeHandler<?>> getTypeHandlerClass() {
        return typeHandlerClass;
    }

    /**
     * 创建类型处理器实例
     *
     * @return
     */
    public TypeHandler<?> newTypeHandlerInstance() {
        return this.typeHandlerConstructorAccess.newInstance();
    }

    /**
     * 获取是否是主键
     *
     * @return
     * @author 老码农 2017-09-28 09:51:22
     */
    public boolean isPrimaryKey() {
        return this.primaryKey;
    }

    /**
     * 获取是否插入
     *
     * @return
     * @author 老码农 2017-09-28 09:51:32
     */
    public boolean isInsertable() {
        return this.insertable;
    }

    /**
     * 获取是否更新
     *
     * @return
     * @author 老码农 2017-09-28 09:51:46
     */
    public boolean isUpdatable() {
        return updatable;
    }

    /**
     * 获取允许为空
     *
     * @return
     * @author 老码农 2017-09-28 09:51:54
     */
    public boolean isNullable() {
        return nullable;
    }

    /**
     * 获取长度
     *
     * @return
     * @author 老码农 2017-09-28 09:52:43
     */
    public int getLength() {
        return length;
    }

    /**
     * 获取精度
     *
     * @return
     * @author 老码农 2017-09-28 09:52:51
     */
    public int getPrecision() {
        return precision;
    }

    /**
     * 获取小数位数
     *
     * @return
     * @author 老码农 2017-09-28 09:54:28
     */
    public int getScale() {
        return scale;
    }

    /**
     * 获取属性名称
     *
     * @return
     * @author 老码农 2017-09-27 18:18:28
     */
    public String getPropertyName() {
        return propertyName;
    }

    /**
     * 获取列名称
     *
     * @return
     * @author 老码农 2017-09-27 18:18:35
     */
    public String getColumnName() {
        return columnName;
    }

    /**
     * 获取友好名称
     *
     * @return
     */
    public String getFriendlyName() {
        return this.friendlyName;
    }

    /**
     * 获取排序方式
     *
     * @return
     */
    public String getOrderBy() {
        return orderBy;
    }

    /**
     * 获取列顺序
     *
     * @return
     */
    public final int getColumnOrder() {
        return this.columnOrder;
    }

    /**
     * 获取生成方式(包含非主键)
     *
     * @return
     */
    public GenerationType getGenerationType() {
        return generationType;
    }

    /**
     * 是否为序列分配主键
     *
     * @return
     */
    public boolean isSequenceAssignKey() {
        return this.isPrimaryKey() && GenerationType.SEQUENCE.equals(this.getGenerationType());
    }

    /**
     * 是否为表分配主键
     *
     * @return
     */
    public boolean isTableAssignKey() {
        return this.isPrimaryKey() && GenerationType.TABLE.equals(this.getGenerationType());
    }

    /**
     * 是否为自增分配主键
     *
     * @return
     */
    public boolean isIdentityAssignKey() {
        return this.isPrimaryKey() && GenerationType.IDENTITY.equals(this.getGenerationType());
    }

    /**
     * 是否为自动分配主键(程序控制)
     *
     * @return
     */
    public boolean isAutoAssignKey() {
        return this.isPrimaryKey() && GenerationType.AUTO.equals(this.getGenerationType());
    }

    /**
     * 获取序列名称
     *
     * @return
     */
    public String getSequenceName() {
        return sequenceName;
    }

    /**
     * 获取值
     *
     * @param instance 实例
     * @return
     * @author 老码农 2017-09-29 10:00:35
     */
    public Object getValue(Object instance) {
        return property.getValue(instance);
    }

    /**
     * 设置值
     *
     * @param instance 实例
     * @param value    值
     * @author 老码农 2017-09-29 10:01:46
     */
    public void setValue(Object instance, Object value) {
        property.setValue(instance, TypeUtils.toObjectConvert(this.getJavaType(), value));
    }

    /**
     * 获取列的处理器(逗号分隔）
     *
     * @param isContainEntityClass 包括实体类
     * @param prefixes             前缀
     * @return 返回格式如:#{entityName.age+prefixes,jdbcType=NUMERIC,typeHandler=MyTypeHandler},
     */
    public String getColumnHolderWithComma(boolean isContainEntityClass, String prefixes) {
        return getColumnHolder(isContainEntityClass, prefixes, ",");
    }

    /**
     * 获取列的处理器(默认)
     *
     * @return 返回格式如:#{entityName.age+prefixes,jdbcType=NUMERIC,typeHandler=MyTypeHandler},
     */
    public String getColumnHolder() {
        return this.columnHolder;
    }

    /**
     * 获取列的处理器(无分隔）
     *
     * @param isContainEntityClass 包括实体类
     * @param prefixes             前缀
     * @return 返回格式如:#{entityName.age+prefixes,jdbcType=NUMERIC,typeHandler=MyTypeHandler},
     */
    public String getColumnHolder(boolean isContainEntityClass, String prefixes) {
        return getColumnHolder(isContainEntityClass, prefixes, null);
    }

    /**
     * 获取列的处理器
     *
     * @param isContainEntityClass 包括实体类
     * @param prefixes             前缀
     * @param separator            分隔符
     * @return 返回格式如:#{entityName.age+prefixes,jdbcType=NUMERIC,typeHandler=MyTypeHandler}+separator
     */
    public String getColumnHolder(boolean isContainEntityClass, String prefixes, String separator) {
        StringBuilder sb = new StringBuilder("#{");
        if (isContainEntityClass) {
            sb.append(this.getTable().getEntityClass().getName());
            sb.append(".");
        }
        if (!StringUtils.isNullOrBlank(prefixes)) {
            sb.append(prefixes);
        }
        sb.append(this.getPropertyName());

        sb.append(",jdbcType=");
        sb.append(this.jdbcType.toString());
        sb.append(",typeHandler=");
        sb.append(this.typeHandlerClass.getCanonicalName());

        sb.append("}");
        if (!StringUtils.isNullOrBlank(separator)) {
            sb.append(separator);
        }
        return sb.toString();
    }

    /**
     * 获取列操作符处理器
     *
     * @param operator 操作符 =、>=、<=、<>等
     * @return
     */
    public String getColumnOperatorHolder(String operator, DbProvider dbProvider) {
        String colName;
        if (dbProvider != null) {
            colName = dbProvider.getSafeTableName(this.getColumnName());
        } else {
            colName = this.getColumnName();
        }
        return String.format("%s %s %s", colName, operator, this.getColumnHolder());
    }

    /**
     * 获取列等于处理器
     *
     * @return
     */
    public String getColumnEqualsHolder(DbProvider dbProvider) {
        return getColumnOperatorHolder("=", dbProvider);
    }

    /**
     * 获取列不等于处理器
     *
     * @return
     */
    public String getColumnNotEqualsHolder(DbProvider dbProvider) {
        return getColumnOperatorHolder("<>", dbProvider);
    }

    /**
     * 获取列大于处理器
     *
     * @return
     */
    public String getColumnGreaterThanHolder(DbProvider dbProvider) {
        return getColumnOperatorHolder(">", dbProvider);
    }

    /**
     * 获取列大于处理器
     *
     * @return
     */
    public String getColumnGreaterThanOrEqualsHolder(DbProvider dbProvider) {
        return getColumnOperatorHolder(">=", dbProvider);
    }

    /**
     * 获取列小于处理器
     *
     * @return
     */
    public String getColumnLessThanHolder(DbProvider dbProvider) {
        return getColumnOperatorHolder("<", dbProvider);
    }

    /**
     * 获取列小于或等于处理器
     *
     * @return
     */
    public String getColumnLessThanOrEqualsHolder(DbProvider dbProvider) {
        return getColumnOperatorHolder("<=", dbProvider);
    }

    @Override
    public String toString() {
        if (this.getPropertyName().equalsIgnoreCase(this.getColumnName())) {
            return String.format("%s(%s -> %s)", this.getPropertyName(), this.getJavaType().getName(),
                    this.getJdbcType());
        }
        return String.format("%s[%s](%s -> %s)", this.getPropertyName(), this.getColumnName(),
                this.getJavaType().getName(), this.getJdbcType());
    }

    @Override
    public int compareTo(EntityColumn o) {
        return Integer.compare(this.getColumnOrder(), o.getColumnOrder());
    }

}
