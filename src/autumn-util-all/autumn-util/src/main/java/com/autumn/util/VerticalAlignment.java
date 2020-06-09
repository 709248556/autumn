package com.autumn.util;

import java.util.Collection;
import java.util.LinkedHashMap;
import java.util.Map;

/**
 * 垂直对齐方式
 */
public enum VerticalAlignment implements ValuedEnum<Integer> {

    /**
     * 顶部对齐
     */
    TOP(1, "顶部对齐"),

    /**
     * 居中对齐
     */
    CENTER(2, "居中对齐"),

    /**
     * 底部
     */
    BOTTOM(3, "底部对齐");

    private final int value;
    private final String name;

    private VerticalAlignment(int value, String name) {
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

    private static final Map<Integer, VerticalAlignment> VALUE_MAP = new LinkedHashMap<>(5);

    static {
        for (VerticalAlignment type : VerticalAlignment.values()) {
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
        VerticalAlignment item = VALUE_MAP.get(value);
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
    public static VerticalAlignment getItem(Integer value) {
        return VALUE_MAP.get(value);
    }

    /**
     * 项目集合
     *
     * @return
     */
    public static Collection<VerticalAlignment> items() {
        return VALUE_MAP.values();
    }
}
