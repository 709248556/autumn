package com.autumn.security.constants;

import com.autumn.domain.values.ConstantField;
import com.autumn.domain.values.IntegerConstantItemValue;

import java.io.Serializable;
import java.util.Collection;
import java.util.Map;

/**
 * 用户状态常量
 *
 * @author 老码农 2018-11-24 22:02:10
 */
public final class UserStatusConstants implements Serializable {

    /**
     *
     */
    private static final long serialVersionUID = -6196790225106421047L;

    /**
     * 正常
     */
    @ConstantField(name = "正常", explain = "正常状态")
    public static final int NORMAL = 1;

    /**
     * 锁定
     */
    @ConstantField(name = "锁定", explain = "锁定状态，不可登录")
    public static final int LOCKING = 2;

    /**
     * 过期
     */
    @ConstantField(name = "过期", explain = "过期状态，不可登录")
    public static final int EXPIRED = 3;

    /**
     * 未激活
     */
    @ConstantField(name = "未激活", explain = "未激活状态，需激活")
    public static final int NOT_ACTIVATE = 4;

    private static final Map<Integer, IntegerConstantItemValue> NAME_MAP;

    static {
        NAME_MAP = IntegerConstantItemValue.generateMap(UserStatusConstants.class);
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
     * 获取项目
     *
     * @param value 值
     * @return
     */
    public static IntegerConstantItemValue getItem(Integer value) {
        return NAME_MAP.get(value);
    }

    /**
     * 项目集合
     *
     * @return
     */
    public static Collection<IntegerConstantItemValue> items() {
        return NAME_MAP.values();
    }
}
