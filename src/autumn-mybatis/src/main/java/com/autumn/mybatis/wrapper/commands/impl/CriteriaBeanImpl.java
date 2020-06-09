package com.autumn.mybatis.wrapper.commands.impl;

import java.io.Serializable;

import com.autumn.mybatis.wrapper.commands.CriteriaBean;

/**
 * 条件Bean实现
 *
 * @author 老码农
 *         <p>
 *         2017-10-29 15:39:15
 */
public class CriteriaBeanImpl implements CriteriaBean, Serializable {

    /**
     *
     */
    private static final long serialVersionUID = 468639070099242890L;
    private String logic;
    private String expression;
    private String op;
    private Object value;
    private Object secondValue;

    /**
     * 获取逻辑
     *
     * @return
     */
    @Override
    public String getLogic() {
        return logic;
    }

    /**
     * 设置逻辑
     *
     * @param logic And|Or|Not
     */
    @Override
    public void setLogic(String logic) {
        this.logic = logic;
    }

    /**
     * 获取表达式
     *
     * @return
     */
    @Override
    public String getExpression() {
        return expression;
    }

    /**
     * 设置表达式
     *
     * @param expression 属性名或列名
     */
    @Override
    public void setExpression(String expression) {
        this.expression = expression;
    }

    /**
     * 获取运算符
     *
     * @return
     */
    @Override
    public String getOp() {
        return op;
    }

    /**
     * 设置运算符
     *
     * @param op
     */
    @Override
    public void setOp(String op) {
        this.op = op;
    }

    @Override
    public Object getValue() {
        return value;
    }

    @Override
    public void setValue(Object value) {
        this.value = value;
    }

    @Override
    public Object getSecondValue() {
        return secondValue;
    }

    @Override
    public void setSecondValue(Object secondValue) {
        this.secondValue = secondValue;
    }
}
