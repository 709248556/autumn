package com.autumn.domain.values;

import com.alibaba.fastjson.annotation.JSONField;

import java.io.Serializable;
import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

/**
 * 常量项目值
 * <p>
 * 提供常量项目，支持相关常量枚举
 * </p>
 *
 * @param <TValue> 值类型
 * @author 老码农 2018-11-24 22:10:48
 */
public class ConstantItemValue<TValue> implements Serializable, Comparable<ConstantItemValue<TValue>> {

    /**
     *
     */
    private static final long serialVersionUID = 2728020982643647381L;

    @JSONField(ordinal = 1)
    private final TValue value;
    @JSONField(ordinal = 2)
    private final String name;
    /**
     * 顺序
     */
    @JSONField(ordinal = 3)
    private int order = 0;

    @JSONField(ordinal = 4)
    private final String explain;

    /**
     * 实例化 ConstantItemValue 类新实例
     *
     * @param value   值
     * @param name    名称
     * @param explain 说明
     */
    public ConstantItemValue(TValue value, String name, String explain) {
        this(value, name, explain, 0);
    }

    /**
     * 实例化 ConstantItemValue 类新实例
     *
     * @param value   值
     * @param name    名称
     * @param explain 说明
     * @param order   顺序
     */
    public ConstantItemValue(TValue value, String name, String explain, int order) {
        this.value = value;
        this.name = name;
        this.explain = explain;
        this.order = order;
    }

    /**
     * 获取值
     *
     * @return
     */
    public TValue getValue() {
        return value;
    }

    /**
     * 获取名称
     *
     * @return
     */
    public String getName() {
        return name;
    }

    /**
     * 获取顺序
     *
     * @return
     */
    public int getOrder() {
        return order;
    }

    /**
     * 设置顺序
     *
     * @param order
     */
    public void setOrder(int order) {
        this.order = order;
    }

    /**
     * 获取说明
     *
     * @return
     */
    public String getExplain() {
        return explain;
    }

    @Override
    public int hashCode() {
        int valueHashCode = 1;
        int nameHashCode = 1;
        if (this.getValue() != null) {
            valueHashCode = this.getValue().hashCode();
        }
        if (this.getName() != null) {
            nameHashCode = this.getName().hashCode();
        }
        return valueHashCode ^ nameHashCode;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == null) {
            return false;
        }
        if (!obj.getClass().equals(this.getClass())) {
            return false;
        }
        @SuppressWarnings("unchecked")
        ConstantItemValue<TValue> civ = (ConstantItemValue<TValue>) obj;
        boolean eqValue = this.objectEquals(this.getValue(), civ.getValue());
        if (eqValue) {
            return this.objectEquals(this.getName(), civ.getName());
        }
        return false;
    }

    /**
     * 对象比较
     *
     * @param left  左值
     * @param right 右值
     * @return
     */
    private boolean objectEquals(Object left, Object right) {
        if (left == null && right == null) {
            return true;
        }
        if (left != null && right != null) {
            return left.equals(right);
        }
        return false;
    }

    /**
     * 排序
     *
     * @param items 项目集合
     * @return
     */
    public static <D extends ConstantItemValue<T>, T> List<D> sorted(Collection<D> items) {
        return items.stream().sorted(ConstantItemValue::compareTo).collect(Collectors.toList());
    }

    @Override
    public String toString() {
        return String.format("[%s]%s %s", this.getValue(), this.getName(), this.getExplain());
    }

    @Override
    public int compareTo(ConstantItemValue<TValue> o) {
        return Integer.compare(this.getOrder(), o.getOrder());
    }

    /**
     * 获取所有静态字段
     *
     * @param constantClass 常量类型
     * @param fieldType     字段类型
     * @return
     */
    public static List<Field> findStaticFields(Class<?> constantClass, Class<?> fieldType) {
        Field[] fields = constantClass.getDeclaredFields();
        List<Field> fieldList = new ArrayList<>(fields.length);
        for (Field field : fields) {
            int modifiers = field.getModifiers();
            if (field.getType().equals(fieldType)
                    && Modifier.isStatic(modifiers)
                    && Modifier.isPublic(modifiers)
                    && Modifier.isFinal(modifiers)) {
                field.setAccessible(true);
                fieldList.add(field);
            }
        }
        return fieldList;
    }


}
