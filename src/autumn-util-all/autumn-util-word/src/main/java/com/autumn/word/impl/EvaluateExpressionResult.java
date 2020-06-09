package com.autumn.word.impl;

/**
 * 解析表达式结果
 *
 * @Description TODO
 * @Author 老码农
 * @Date 2019-07-03 12:21
 */
public class EvaluateExpressionResult {

    private final String evlExpression;
    private final String beginRunText;
    private final String endRunText;

    /**
     * @param evlExpression
     * @param beginRunText
     * @param endRunText
     */
    public EvaluateExpressionResult(String evlExpression, String beginRunText, String endRunText) {
        this.evlExpression = evlExpression;
        this.beginRunText = beginRunText;
        this.endRunText = endRunText;
    }


    /**
     * 获取解析表达式
     *
     * @return
     */
    public final String getEvlExpression() {
        return evlExpression;
    }

    /**
     * 获取开始规则的文本
     *
     * @return
     */
    public final String getBeginRunText() {
        return beginRunText;
    }

    /**
     * 获取结束规则的文本
     *
     * @return
     */
    public final String getEndRunText() {
        return endRunText;
    }
}
