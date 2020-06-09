package com.autumn.evaluator.nodes;

import com.autumn.evaluator.*;

import java.io.Serializable;

/**
 * 条件运算符
 */
public final class ConditionalNode extends AbstractTreeNode implements Serializable {

    /**
     * 序列号
     */
    private static final long serialVersionUID = -6055725804727852524L;

    /**
     * 实例化 ConditionalNode 类新实例
     *
     * @param test    逻辑表达式
     * @param ifTrue  true节点
     * @param ifFalse false节点
     */
    public ConditionalNode(AbstractTreeNode test, AbstractTreeNode ifTrue, AbstractTreeNode ifFalse) {
        this(null, test, ifTrue, ifFalse);
    }

    /**
     * 实例化 ConditionalNode 类新实例
     *
     * @param token   标记
     * @param test    逻辑表达式
     * @param ifTrue  true节点
     * @param ifFalse false节点
     */
    public ConditionalNode(Token token, AbstractTreeNode test, AbstractTreeNode ifTrue, AbstractTreeNode ifFalse) {
        super(token);
        test.parent = this;
        ifTrue.parent = this;
        ifFalse.parent = this;
        this.setTest(test);
        this.setIfTrue(ifTrue);
        this.setIfFalse(ifFalse);
    }

    /**
     * 获取节点类型
     */
    @Override
    public Enums.NodeType getType() {
        return Enums.NodeType.CONDITIONAL;
    }

    /**
     * 克隆
     *
     * @return
     */
    @Override
    public AbstractTreeNode dupNode() {
        if (this.getIsNil()) {
            return new ConditionalNode(this.getTest().dupNode(), this.getIfTrue().dupNode(), this.getIfFalse().dupNode());
        }
        return new ConditionalNode(this.getToken(), this.getTest().dupNode(), this.getIfTrue().dupNode(), this.getIfFalse().dupNode());
    }

    /**
     * 测式条件
     */
    private AbstractTreeNode test;

    public AbstractTreeNode getTest() {
        return test;
    }

    private void setTest(AbstractTreeNode value) {
        test = value;
    }

    /**
     * 获取条件为 true 时的节点
     */
    private AbstractTreeNode ifTrue;

    public AbstractTreeNode getIfTrue() {
        return ifTrue;
    }

    private void setIfTrue(AbstractTreeNode value) {
        ifTrue = value;
    }

    /**
     * 获取条件为 false 时的节点
     */
    private AbstractTreeNode ifFalse;

    public AbstractTreeNode getIfFalse() {
        return ifFalse;
    }

    private void setIfFalse(AbstractTreeNode value) {
        ifFalse = value;
    }

    /**
     * 获取首个标记
     */
    @Override
    public Token getFirstToken() {
        return this.getTest().getFirstToken();
    }

    /**
     * 获取最后标记
     */
    @Override
    public Token getLastToken() {
        return this.getIfFalse().getLastToken();
    }

    /**
     * 节点表达式
     *
     * @return
     */
    @Override
    public String toString() {
        return String.format("%s ? %s : %s", this.getTest().toString(), this.getIfTrue().toString(), this.getIfFalse().toString());
    }

    /**
     * 解析
     *
     * @param parseContext 上下文
     * @return
     */
    @Override
    public Variant parse(ParseContext parseContext) {
        Utils.checkNotNull("parseContext", parseContext);
        Variant logical = this.getTest().parse(parseContext);
        if (!logical.isBoolean()) {
            throw this.parserException();
        }
        if ((boolean) logical.getValue()) {
            return this.getIfTrue().parse(parseContext);
        } else {
            return this.getIfFalse().parse(parseContext);
        }
    }


}