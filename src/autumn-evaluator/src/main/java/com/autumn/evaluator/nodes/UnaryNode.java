package com.autumn.evaluator.nodes;

import com.autumn.evaluator.*;
import com.autumn.exception.NotSupportException;

import java.io.Serializable;

/**
 * 一元运算符
 */
public final class UnaryNode extends AbstractTreeNode implements Serializable {

    /**
     * 序列号
     */
    private static final long serialVersionUID = 8953589326863204030L;

    /**
     * 实例化 UnaryNode 类新实例
     *
     * @param nodeType 节点类型
     * @param operand  操作数
     */
    public UnaryNode(Enums.NodeType nodeType, AbstractTreeNode operand) {
        super();
        operand.parent = this;
        this.nodeType = nodeType;
        this.setOperand(operand);
    }

    /**
     * 实例化 UnaryNode 类新实例
     *
     * @param token    标记
     * @param nodeType 节点类型
     * @param operand  操作数
     */
    public UnaryNode(Token token, Enums.NodeType nodeType, AbstractTreeNode operand) {
        super(token);
        operand.parent = this;
        this.nodeType = nodeType;
        this.setOperand(operand);
    }

    /**
     * 克隆
     *
     * @return
     */
    @Override
    public AbstractTreeNode dupNode() {
        if (this.getIsNil()) {
            return new UnaryNode(this.getType(), this.getOperand().dupNode());
        }
        return new UnaryNode(this.getToken(), this.getType(), this.getOperand().dupNode());
    }

    private Enums.NodeType nodeType;

    /**
     * 获取节点类型
     */
    @Override
    public Enums.NodeType getType() {
        return this.nodeType;
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
        return this.getOperand().getLastToken();
    }

    /**
     * 获取操作数
     */
    private AbstractTreeNode operand;

    public AbstractTreeNode getOperand() {
        return operand;
    }

    private void setOperand(AbstractTreeNode value) {
        operand = value;
    }

    /**
     * 获取运算符
     *
     * @return
     */
    public String delimiter() {
        switch (this.getType()) {
            case NEGATE:
                return "-";
            case UNARY_PLUS:
                return "+";
            case NOT:
                return "!";
            case PERCENT:
                return "%";
            default:
                throw new NotSupportException(String.format("一元运算不支持运算符 %s 。", this.getType()));
        }
    }

    /**
     * 获取是否支持的节点类型
     *
     * @param nodeType 节点类型
     * @return
     */
    public static boolean isSupportedNodeType(Enums.NodeType nodeType) {
        switch (nodeType) {
            case NEGATE:
            case UNARY_PLUS:
            case NOT:
            case PERCENT:
                return true;
            default:
                return false;
        }
    }

    /**
     * 节点表达式
     * <p>
     * 语言类型
     *
     * @return
     */
    @Override
    public String toString() {
        if (this.getType() == Enums.NodeType.PERCENT) {
            return String.format("%s%s", this.getOperand().toString(), this.delimiter());
        }
        return String.format("%s%s", this.delimiter(), this.getOperand().toString());
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
        Variant value = this.getOperand().parse(parseContext);
        switch (this.getType()) {
            case NOT:
                if (!value.isBoolean()) {
                    throw this.parserDelimiterException(this.toString(), "逻辑 非 运算类型必须为 VariantType.BOOLEAN");
                }
                return new Variant(!((boolean) value.getValue()));
            case PERCENT:
                return Variant.divide(value, new Variant(100));
            case UNARY_PLUS:
                if (!value.isNumber()) {
                    throw this.parserDelimiterException(this.toString(), "非数字不支持单目运算。");
                }
                return value;
            case NEGATE:
                if (!value.isNumber()) {
                    throw this.parserDelimiterException(this.toString(), "非数字不支持单目运算。");
                }
                return Variant.multiply(value, new Variant(-1));
            default:
                throw this.parserDelimiterException(this.toString(),
                        String.format("无法解析的表达式，不支持的运算符 \"%s\" 。", this.toString()));
        }
    }

}