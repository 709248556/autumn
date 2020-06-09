package com.autumn.evaluator;


import com.autumn.evaluator.nodes.AbstractTreeNode;
import com.autumn.util.DoubleLinkedList;

/**
 * 文法解析
 */
public interface LexerParse {

    /**
     * 编译
     *
     * @return 返回 Node
     */
    AbstractTreeNode compiled();

    /**
     * 读取标记集合
     *
     * @return 返回标记集合
     */
    DoubleLinkedList<Token> readTokens();
}