package com.autumn.mybatis.wrapper;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

/**
 * 逻辑运算符
 *
 * @author 老码农
 * <p>
 * 2017-10-26 11:07:19
 */
public enum LogicalOperatorEnum implements Serializable {

    /**
     * And 表达式
     */
    AND("AND", true),
    /**
     * Or 表达式
     */
    OR("OR", true),
    /**
     * NOT 表达式
     */
    NOT("NOT", true);

    private final String operator;
    private final boolean isSimple;

    /**
     * @param operator
     * @param isSimple
     */
    private LogicalOperatorEnum(String operator, boolean isSimple) {
        this.operator = operator;
        this.isSimple = isSimple;
    }

    /**
     * 获取运算符
     *
     * @return
     */
    public String getOperator() {
        return operator;
    }

    /**
     * 获取是否为简单表达式
     *
     * @return
     */
    public boolean isSimple() {
        return isSimple;
    }

    /**
     * 获取operator 地图
     *
     * @return
     */
    public static Map<String, LogicalOperatorEnum> getMapByOperator() {
        Map<String, LogicalOperatorEnum> map = new HashMap<>();
        for (LogicalOperatorEnum co : LogicalOperatorEnum.values()) {
            map.put(co.getOperator(), co);
        }
        return map;
    }
}
