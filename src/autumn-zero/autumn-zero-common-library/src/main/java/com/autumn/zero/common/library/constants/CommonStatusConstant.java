package com.autumn.zero.common.library.constants;

import com.autumn.domain.values.ConstantField;
import com.autumn.domain.values.IntegerConstantItemValue;

import java.util.Collection;
import java.util.Map;

/**
 * 公共状态常量
 *
 * @Description TODO
 * @Author 老码农
 * @Date 2019-07-25 18:22
 */
public class CommonStatusConstant {

    /**
     * 启用
     */
    @ConstantField(name = "启用")
    public static final int STATUS_ENABLE = 1;

    /**
     * 禁用
     */
    @ConstantField(name = "禁用")
    public static final int STATUS_STOP = 2;

    /**
     *
     */
    public static final String API_MODEL_PROPERTY = "状态（1=启用，2=禁用)";

    private static final Map<Integer, IntegerConstantItemValue> NAME_MAP;
    static {
        NAME_MAP = IntegerConstantItemValue.generateMap(CommonStatusConstant.class);
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
        return NAME_MAP.containsKey(value);
    }

    /**
     * 获取名称
     *
     * @param value 值
     * @return
     */
    public static String getName(Integer value) {
        IntegerConstantItemValue item = NAME_MAP.get(value);
        if (item == null) {
            return "";
        }
        return item.getName();
    }

    /**
     * 项目集合
     *
     * @return
     */
    public static Collection<IntegerConstantItemValue> items() {
        return IntegerConstantItemValue.sorted(NAME_MAP.values());
    }
}
