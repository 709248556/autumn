package com.autumn.evaluator.visit;


import com.autumn.evaluator.ParseContext;
import com.autumn.evaluator.nodes.AbstractTreeNode;

/**
 * 访问表达式抽象
 */
public interface VisitExpression {
	
    /**
     * 访问
     *
     * @param node    节点
     * @param context 上下文
     */
    void visit(AbstractTreeNode node, ParseContext context);
}