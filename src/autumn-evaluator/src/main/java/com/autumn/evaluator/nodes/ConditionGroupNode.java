package com.autumn.evaluator.nodes;

import com.autumn.evaluator.*;

import java.io.Serializable;

/**
 * 括号节点
 */
public final class ConditionGroupNode extends AbstractTreeNode implements Serializable {

    /**
	 * 序列号
	 */
	private static final long serialVersionUID = 2973879913868328360L;

	/**
     * 实例化 ParenthesisNode 类新实例
     *
     * @param content 内容
     */
    public ConditionGroupNode(AbstractTreeNode content) {
        super();
        content.parent = this;
        this.setContent(content);
    }

    /**
     * 实例化 ParenthesisNode 类新实例
     *
     * @param token   标记
     * @param content 内容
     */
    public ConditionGroupNode(Token token, AbstractTreeNode content) {
        super(token);
        content.parent = this;
        this.setContent(content);
    }

    /**
     * 获取节点类型
     */
    @Override
    public Enums.NodeType getType() {
        return Enums.NodeType.CONDITION_GROUP;
    }

    /**
     * 内容节点
     */
    private AbstractTreeNode content;

    public AbstractTreeNode getContent() {
        return content;
    }

    private void setContent(AbstractTreeNode value) {
        content = value;
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
        return this.getContent().getLastToken();
    }

    /**
     * @return
     */
    @Override
    public AbstractTreeNode dupNode() {
        if (this.getIsNil()) {
            return new ConditionGroupNode(this.getContent().dupNode());
        }
        return new ConditionGroupNode(this.getToken(), this.getContent().dupNode());
    }

    /**
     * 节点表达式
     *
     * @return
     */
    @Override
    public String toString() {
        return String.format("(%s)", this.getContent().toString());
    }

    /**
     * @param parseContext
     * @return
     */
    @Override
    public Variant parse(ParseContext parseContext) {
        Utils.checkNotNull("parseContext", parseContext);
        return this.getContent().parse(parseContext);
    }


}