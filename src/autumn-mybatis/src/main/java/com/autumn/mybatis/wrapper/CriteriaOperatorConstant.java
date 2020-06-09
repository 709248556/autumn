package com.autumn.mybatis.wrapper;

/**
 * 条件操作符常量
 *
 * @Description TODO
 * @Author 老码农
 * @Date 2019-08-30 5:55
 */
public class CriteriaOperatorConstant {

    /**
     * 等于
     */
    public static final String EQ = "=";

    /**
     * 不等于
     */
    public static final String NOT_EQ = "<>";

    /**
     * 大于
     */
    public static final String GT = ">";

    /**
     * 大于等于
     */
    public static final String GE = ">=";

    /**
     * 小于
     */
    public static final String LT = "<";

    /**
     * 小于等于
     */
    public static final String LE = "<=";

    /**
     * is null 表达式
     */
    public static final String IS_NULL = "IS_NULL";

    /**
     * is not null 表达式
     */
    public static final String IS_NOT_NULL = "IS_NOT_NULL";

    /**
     * IN 表达式
     */
    public static final String IN = "IN";

    /**
     * not in 表达式
     */
    public static final String NOT_IN = "NOT_IN";

    /**
     * IN 的sql表达式
     */
    public static final String IN_SQL = "IN_SQL";

    /**
     * not in 的sql表达式
     */
    public static final String NOT_IN_SQL = "NOT_IN_SQL";

    /**
     * EXISTS 表达式
     */
    public static final String EXISTS = "EXISTS";

    /**
     * not EXISTS 表达式
     */
    public static final String NOT_EXISTS = "NOT_EXISTS";

    /**
     * like '%value%'
     */
    public static final String LIKE = "LIKE";

    /**
     * 左like 'value%'
     */
    public static final String LEFT_LIKE = "LEFT_LIKE";

    /**
     * 右like '%value'
     */
    public static final String RIGHT_LIKE = "RIGHT_LIKE";

    /**
     * between (1 and 2)
     */
    public static final String BETWEEN = "BETWEEN";

}
