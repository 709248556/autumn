package com.autumn.mybatis.metadata;

import com.autumn.exception.SystemException;
import com.autumn.util.TypeUtils;
import com.autumn.util.reflect.BeanProperty;
import com.autumn.util.tuple.TupleTwo;

import javax.persistence.JoinColumn;
import javax.persistence.OrderBy;
import java.io.Serializable;
import java.util.Collection;
import java.util.List;

/**
 * 实体关联
 *
 * @Description TODO
 * @Author 老码农
 * @Date 2019-08-28 2:16
 */
public final class EntityRelation implements Serializable {
    private static final long serialVersionUID = 4611524896522145975L;

    private final EntityTable parentTable;
    private final BeanProperty parentProperty;
    private final EntityRelationMode relationMode;
    private final BeanProperty relationProperty;
    private final Class<?> relationEntityType;
    private final EntityTable relationTabe;
    private final List<EntityColumnOrder> orderColumns;
    private final String orderByClause;
    private final boolean unique;
    private final boolean nullable;
    private final boolean insertable;
    private final boolean updatable;

    /**
     * 实例代
     *
     * @param parentTable        父级表
     * @param parentProperty     父级属性
     * @param relationMode       关联模式
     * @param relationProperty   关联属性
     * @param relationEntityType 关联实体类型
     * @param relationTabe       关联表
     * @param joinColumn         关联列
     */
    EntityRelation(EntityTable parentTable,
                   BeanProperty parentProperty,
                   EntityRelationMode relationMode,
                   BeanProperty relationProperty,
                   Class<?> relationEntityType,
                   EntityTable relationTabe,
                   JoinColumn joinColumn) {
        this.parentTable = parentTable;
        this.parentProperty = parentProperty;
        this.relationMode = relationMode;
        this.relationProperty = relationProperty;
        this.relationEntityType = relationEntityType;
        this.relationTabe = relationTabe;
        TupleTwo<List<EntityColumnOrder>, String> orderTuple = MetadataUtils.createOrderColumns(() -> {
                    OrderBy orderBy = parentProperty.getAnnotation(OrderBy.class);
                    if (orderBy != null) {
                        return orderBy.value();
                    }
                    return null;
                }, "类型[" + parentTable.getEntityClass().getName() + " 的 关联属性[" + parentProperty.getName() + "]"
                , relationTabe.getPropertyMap());
        this.orderColumns = orderTuple.getItem1();
        this.orderByClause = orderTuple.getItem2();
        this.unique = joinColumn.unique();
        this.nullable = joinColumn.nullable();
        this.insertable = joinColumn.insertable();
        this.updatable = joinColumn.updatable();
    }


    /**
     * 获取父级表
     *
     * @return
     */
    public EntityTable getParentTable() {
        return this.parentTable;
    }

    /**
     * 获取父级属性
     *
     * @return
     */
    public BeanProperty getParentProperty() {
        return this.parentProperty;
    }

    /**
     * 获取关联模式
     *
     * @return
     */
    public EntityRelationMode getRelationMode() {
        return this.relationMode;
    }

    /**
     * 获取关联属性
     *
     * @return
     */
    public BeanProperty getRelationProperty() {
        return this.relationProperty;
    }

    /**
     * 获取关联类型
     *
     * @return
     */
    public Class<?> getRelationEntityType() {
        return this.relationEntityType;
    }

    /**
     * 获取关联表
     *
     * @return
     */
    public EntityTable getRelationTabe() {
        return this.relationTabe;
    }

    /**
     * 获取排序列集合
     *
     * @return
     */
    public List<EntityColumnOrder> getOrderColumns() {
        return this.orderColumns;
    }

    /**
     * 获取排序表达式
     *
     * @return
     */
    public String getOrderByClause() {
        return this.orderByClause;
    }

    /**
     * 创建
     *
     * @return
     */
    public Collection createOneToManyCollection() {
        if (this.getRelationMode().equals(EntityRelationMode.ONE_TO_MANY)) {
            return TypeUtils.createCollection(this.getParentProperty().getReadMethod().getGenericReturnType());
        }
        throw new SystemException("非一对多关系，不能创建集合对象。");
    }

    /**
     * 是否唯一
     *
     * @return
     */
    public boolean isUnique() {
        return this.unique;
    }

    /**
     * 是否允许为空
     *
     * @return
     */
    public boolean isNullable() {
        return this.nullable;
    }

    /**
     * 是否插入
     *
     * @return
     */
    public boolean isInsertable() {
        return this.insertable;
    }

    /**
     * 是否更新与删除
     *
     * @return
     */
    public boolean isUpdatable() {
        return this.updatable;
    }

}
