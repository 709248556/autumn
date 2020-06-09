package com.autumn.evaluator;

import com.autumn.evaluator.nodes.AbstractTreeNode;

import java.util.List;

/**
 * 解析会话
 *
 * @Description TODO
 * @Author 老码农
 * @Date 2019-08-17 4:32
 */
public interface EvaluatorSession {

    /**
     * 解析
     *
     * @param expression 表达式
     * @param context    上下文
     * @return
     */
    Variant parse(String expression, Context context);

    /**
     * 编译
     *
     * @param expression 表达式
     * @return
     */
    AbstractTreeNode compiled(String expression);

    /**
     * 读取标记
     *
     * @param expression 表达式
     * @return
     */
    List<Token> readTokens(String expression);
}
