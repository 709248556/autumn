package com.autumn.util;

import java.util.Collection;
import java.util.LinkedHashMap;
import java.util.Map;

/**
 * 水平对齐方式
 */
public enum HorizontalAlignment implements ValuedEnum<Integer> {

    /**
     * 常規
     */
    GENERAL(1, "常規"),

    /**
     * 左对齐
     */
    LEFT(2, "左对齐"),

    /**
     * 居中对齐
     */
    CENTER(3, "居中对齐"),

    /**
     * 右对齐
     */
    RIGHT(4, "右对齐");

    private final int value;
    private final String name;

    private HorizontalAlignment(int value, String name) {
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

    private static final Map<Integer, HorizontalAlignment> VALUE_MAP = new LinkedHashMap<>(5);

    static {
        for (HorizontalAlignment type : HorizontalAlignment.values()) {
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
        HorizontalAlignment item = VALUE_MAP.get(value);
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
    public static HorizontalAlignment getItem(Integer value) {
        return VALUE_MAP.get(value);
    }

    /**
     * 项目集合
     *
     * @return
     */
    public static Collection<HorizontalAlignment> items() {
        return VALUE_MAP.values();
    }
}
