package com.autumn.mybatis.wrapper.expressions;


/**
 * 常量表达式
 *
 * @author 老码农
 * <p>
 * 2017-10-26 15:20:42
 */
public class ConstantExpression extends AbstractOperatorExpression {
    private final Object value;

    /**
     * @param value
     */
    ConstantExpression(Object value) {
        //参数转换
        this.value = value;
    }

    /**
     * @return
     */
    public Object getValue() {
        return value;
    }

}
