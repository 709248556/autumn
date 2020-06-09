package com.autumn.util;

import java.util.Collection;
import java.util.LinkedHashMap;
import java.util.Map;

/**
 * 数据类型枚举
 * <p>
 * </p>
 *
 * @description: TODO
 * @author: 老码农
 * @create: 2019-11-21 18:17
 **/
public enum DataTypeEnum implements ValuedEnum<Integer> {

    /**
     * 文本
     */
    STRING(1, "文本"),

    /**
     * 整数
     */
    INTEGER(2, "整数"),

    /**
     * 小数
     */
    DECIMAL(3, "小数"),

    /**
     * 浮点数
     */
    FLOAT(4, "浮点数"),

    /**
     * 布尔
     */
    BOOLEAN(5, "布尔(是/否)"),

    /**
     * 日期
     */
    DATE(6, "日期"),

    /**
     * 日期时间
     */
    DATETIME(7, "日期时间"),

    /**
     * 时间
     */
    TIME(8, "时间"),

    /**
     * 枚举
     */
    ENUM(9, "枚举");

    private final int value;
    private final String name;

    private DataTypeEnum(int value, String name) {
        this.value = value;
        this.name = name;
    }

    @Override
    public Integer value() {
        return this.value;
    }

    /**
     * 获取名称
     *
     * @return
     */
    public String getName() {
        return this.name;
    }

    @Override
    public String toString() {
        return this.getName() + "[" + this.value() + "]";
    }

    private static final Map<Integer, DataTypeEnum> VALUE_MAP = new LinkedHashMap<>(10);

    static {
        for (DataTypeEnum type : DataTypeEnum.values()) {
            VALUE_MAP.put(type.value(), type);
        }
    }

    /**
     * 是否存在
     *
     * @param value 值
     * @return
     */
    public static boolean exist(Integer value) {
        if (value == null) {
            return false;
        }
        return VALUE_MAP.containsKey(value);
    }

    /**
     * 获取名称
     *
     * @param value 值
     * @return
     */
    public static String getName(Integer value) {
        DataTypeEnum item = VALUE_MAP.get(value);
        if (item == null) {
            return "";
        }
        return item.getName();
    }

    /**
     * 获取项目
     *
     * @param value 值
     * @return
     */
    public static DataTypeEnum getItem(Integer value) {
        return VALUE_MAP.get(value);
    }

    /**
     * 项目集合
     *
     * @return
     */
    public static Collection<DataTypeEnum> items() {
        return VALUE_MAP.values();
    }

}
