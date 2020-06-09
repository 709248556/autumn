package com.autumn.evaluator.nodes;

import com.autumn.evaluator.Enums;
import com.autumn.evaluator.ParseContext;
import com.autumn.evaluator.Token;
import com.autumn.evaluator.Variant;

import java.io.Serializable;

/**
 * 空值节点
 */
public final class NullNode extends AbstractTreeNode implements Serializable {

    /**
	 * 序列号
	 */
	private static final long serialVersionUID = -2450544310899299091L;

	/**
     * 默认值
     */
    private static final Variant DEFAULT_VALUE = new Variant();

    /**
     * 实例化 NilNode 类新实例
     */
    public NullNode() {
        super();
    }

    /**
     * 实例化 NilNode 类新实例
     *
     * @param token 标记
     */
    public NullNode(Token token) {
        super(token);
    }

    /**
     * 获取值
     */
    public Variant getValue() {
        return NullNode.DEFAULT_VALUE;
    }

    /**
     * 获取首个标记
     */
    @Override
    public Token getFirstToken() {
        return this.getToken();
    }

    /**
     * 获取最后标记
     */
    @Override
    public Token getLastToken() {
        return this.getToken();
    }

    /**
     * 获取节点类型
     */
    @Override
    public Enums.NodeType getType() {
        return Enums.NodeType.NULL;
    }

    /**
     * @return
     */
    @Override
    public AbstractTreeNode dupNode() {
        if (this.getIsNil()) {
            return new NullNode();
        }
        return new NullNode(this.getToken());
    }

    /**
     * 节点表达式
     *
     * @return
     */
    @Override
    public String toString() {
        return "NULL";
    }

    /**
     * @param parseContext
     * @return
     */
    @Override
    public Variant parse(ParseContext parseContext) {
        return this.getValue();
    }

}