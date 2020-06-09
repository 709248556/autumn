package com.autumn.mybatis.wrapper;

import com.autumn.exception.ExceptionUtils;
import com.autumn.mybatis.metadata.EntityTable;
import com.autumn.mybatis.wrapper.expressions.AbstractExpression;
import com.autumn.mybatis.wrapper.expressions.ColumnExpression;
import com.autumn.mybatis.wrapper.expressions.ConstantExpression;
import com.autumn.mybatis.wrapper.expressions.PropertyExpression;
import com.autumn.util.LambdaUtils;
import com.autumn.util.TypeUtils;

import java.io.Serializable;
import java.lang.invoke.SerializedLambda;

/**
 * 解析抽象
 *
 * @author 老码农
 * <p>
 * 2017-10-29 16:45:49
 */
abstract class AbstractParser {

    private final EntityTable entityTable;

    /**
     * 无表实例化
     */
    public AbstractParser() {
        this.entityTable = null;
    }

    /**
     * 转入 entityClass 实例化
     *
     * @param entityClass 实体类型
     */
    public AbstractParser(Class<?> entityClass) {
        if (entityClass == null) {
            this.entityTable = null;
        } else {
            this.entityTable = EntityTable.getTable(entityClass);
        }
    }

    /**
     * 转入 EntityTable 实例化
     *
     * @param entityTable 实体表
     */
    public AbstractParser(EntityTable entityTable) {
        this.entityTable = entityTable;
    }

    /**
     * 获取表
     *
     * @return
     */
    public final EntityTable getEntityTable() {
        return this.entityTable;
    }

    /**
     * 是否是实体表
     *
     * @return
     */
    public final boolean isEntity() {
        return this.getEntityTable() != null;
    }

    /**
     * 列表达式
     *
     * @param name 属性或列名称
     * @return
     */
    protected final ColumnExpression column(String name) {
        ExceptionUtils.checkNotNullOrBlank(name, "name");
        if (this.getEntityTable() != null) {
            return AbstractExpression.property(this.getEntityTable(), name);
        } else {
            return AbstractExpression.column(name);
        }
    }

    /**
     * 列表达式
     *
     * @param function lambda 函数
     * @return
     */
    protected final <TEntity> ColumnExpression columnByLambda(Serializable function) {
        ExceptionUtils.checkNotNull(function, "function");
        SerializedLambda lambda = LambdaUtils.resolveCacheLambda(function);
        String name = LambdaUtils.getReadFiledName(lambda);
        return this.column(name);
    }

    /**
     * 常量表达式
     *
     * @param column 列
     * @param value  值
     * @return
     */
    protected final ConstantExpression constant(ColumnExpression column, Object value) {
        return AbstractExpression.constant(constantValue(column, value));
    }

    /**
     * 常量值
     *
     * @param column 列
     * @param value  值
     * @return
     */
    protected final Object constantValue(ColumnExpression column, Object value) {
        if (value == null) {
            return null;
        }
        if (column instanceof PropertyExpression) {
            PropertyExpression property = (PropertyExpression) column;
            return TypeUtils.toObjectConvert(property.getColumn().getJavaType(), value);
        }
        return value;
    }
}
