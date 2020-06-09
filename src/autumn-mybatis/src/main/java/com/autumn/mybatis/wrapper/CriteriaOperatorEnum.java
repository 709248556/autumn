package com.autumn.mybatis.wrapper;

import java.util.HashMap;
import java.util.Map;

/**
 * 条件运算符
 *
 * @author 老码农
 * <p>
 * 2017-10-26 10:53:45
 */
public enum CriteriaOperatorEnum {

    /**
     * 等于
     */
    EQ(CriteriaOperatorConstant.EQ, true, true, "EQ", "EQUAL", "=="),

    /**
     * 不等于
     */
    NOT_EQ(CriteriaOperatorConstant.NOT_EQ, true, true, "NOT_EQ", "NOTEQ", "NOTEQUAL", "!="),

    /**
     * 大于
     */
    GT(CriteriaOperatorConstant.GT, true, true, "GT", "GREATERTHAN", "GREATER_THAN"),

    /**
     * 大于等于
     */
    GE(CriteriaOperatorConstant.GE, true, true, "GE", "GREATERTHANOREQUAL", "GREATER_THAN_OR_EQUAL"),

    /**
     * 小于
     */
    LT(CriteriaOperatorConstant.LT, true, true, "LT", "LESSTHAN", "LESS_THAN"),

    /**
     * 小于等于
     */
    LE(CriteriaOperatorConstant.LE, true, true, "LE", "LESSTHANOREQUAL", "LESS_THAN_OR_EQUAL"),

    /**
     * is null 表达式
     */
    IS_NULL(CriteriaOperatorConstant.IS_NULL, true, false, "ISNULL", "IS NULL"),

    /**
     * is not null 表达式
     */
    IS_NOT_NULL(CriteriaOperatorConstant.IS_NOT_NULL, true, false, "ISNOTNULL", "IS NOT NULL"),

    /**
     * IN 表达式
     */
    IN(CriteriaOperatorConstant.IN, false, true),

    /**
     * not in 表达式
     */
    NOT_IN(CriteriaOperatorConstant.NOT_IN, false, true, "NOTIN", "NOT IN"),

    /**
     * IN 的sql表达式
     */
    IN_SQL(CriteriaOperatorConstant.IN_SQL, false, true),

    /**
     * not in 的sql表达式
     */
    NOT_IN_SQL(CriteriaOperatorConstant.NOT_IN_SQL, false, true),

    /**
     * EXISTS 表达式
     */
    EXISTS(CriteriaOperatorConstant.EXISTS, false, true),

    /**
     * not EXISTS 表达式
     */
    NOT_EXISTS(CriteriaOperatorConstant.NOT_EXISTS, false, true, "NOTEXISTS", "NOT EXISTS"),

    /**
     * like '%value%'
     */
    LIKE(CriteriaOperatorConstant.LIKE, true, false, "CONTAINS", "WITH"),

    /**
     * 左like 'value%'
     */
    LEFT_LIKE(CriteriaOperatorConstant.LEFT_LIKE, true, false, "STARTSWITH", "STARTS_WITH", "LEFTLIKE", "LLIKE", "LEFTWITH", "LEFT_WITH"),

    /**
     * 右like '%value'
     */
    RIGHT_LIKE(CriteriaOperatorConstant.RIGHT_LIKE, true, false, "ENDSWITH", "ENDS_WITH", "RIGHTLIKE", "RLIKE", "RIGHTWITH", "RIGHT_WITH"),

    /**
     * between (1 and 2)
     */
    BETWEEN(CriteriaOperatorConstant.BETWEEN, true, false, "B");

    private final String operator;
    private final boolean simple;
    private final boolean supportClient;
    private final String[] operators;

    /**
     * @param operator
     * @param supportClient
     * @param simple
     * @param operators
     */
    private CriteriaOperatorEnum(String operator, boolean supportClient, boolean simple, String... operators) {
        this.operator = operator;
        this.simple = simple;
        this.supportClient = supportClient;
        String[] ops;
        if (operators != null) {
            ops = new String[operators.length + 1];
        } else {
            ops = new String[1];
        }
        ops[0] = operator;
        if (operators != null) {
            System.arraycopy(operators, 0, ops, 1, operators.length);
        }
        this.operators = ops;
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
     * 获取操作符集合
     *
     * @return
     */
    public String[] getOperators() {
        return this.operators;
    }

    /**
     * 是否支持客户端
     *
     * @return
     */
    public boolean isSupportClient() {
        return this.supportClient;
    }

    /**
     * 获取是否为简单表达式
     *
     * @return
     */
    public boolean isSimple() {
        return this.simple;
    }

    /**
     * 运算符转换
     *
     * @param operator
     * @return
     */

    public static CriteriaOperatorEnum operator(String operator) {
        for (CriteriaOperatorEnum co : CriteriaOperatorEnum.values()) {
            if (co.getOperator().equalsIgnoreCase(operator)) {
                return co;
            }
        }
        return null;
    }

    /**
     * 获取operator 地图
     *
     * @return
     */
    public static Map<String, CriteriaOperatorEnum> getMapByOperator() {
        Map<String, CriteriaOperatorEnum> map = new HashMap<>();
        for (CriteriaOperatorEnum co : CriteriaOperatorEnum.values()) {
            for (String op : co.getOperators()) {
                map.put(op, co);
            }
        }
        return map;
    }

}
