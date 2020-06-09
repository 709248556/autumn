package com.autumn.application.dto.input;

import com.autumn.annotation.FriendlyProperty;
import com.autumn.mybatis.wrapper.commands.CriteriaBean;
import lombok.ToString;

import java.io.Serializable;

/**
 * 条件项目
 *
 * @author 老码农 2018-08-14 13:20:24
 */
@ToString(callSuper = true)
public class QueryCriteriaItem implements CriteriaBean, Serializable {

    /**
     *
     */
    private static final long serialVersionUID = 3853658637364438678L;

    @FriendlyProperty(value = "逻辑表达式(and、or、not)")
    private String logic;
    @FriendlyProperty(value = "表达式或字段名")
    private String expression;
    @FriendlyProperty(value = "运算符(=、>、>=、<、<=、!=、Like等)")
    private String op;
    @FriendlyProperty(value = "值")
    private Object value;
    @FriendlyProperty(value = "第二个值")
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

    /**
     * 获取值
     *
     * @return
     */
    @Override
    public Object getValue() {
        return value;
    }

    /**
     * 设置值
     *
     * @param value 值
     */
    @Override
    public void setValue(Object value) {
        this.value = value;
    }

    /**
     * 获取记录值
     *
     * @return
     */
    @Override
    public Object getSecondValue() {
        return secondValue;
    }

    /**
     * 设置记录值
     *
     * @param secondValue 记录值
     */
    @Override
    public void setSecondValue(Object secondValue) {
        this.secondValue = secondValue;
    }
}
