package com.autumn.evaluator.nodes;

import com.autumn.evaluator.*;
import com.autumn.exception.NotSupportException;

import java.io.Serializable;

/**
 * 二元运算符
 */
public final class BinaryNode extends AbstractTreeNode implements Serializable {
    /**
     * 序列号
     */
    private static final long serialVersionUID = 691101425069402205L;

    /**
     * 实例化 BinaryClause 类新实例
     *
     * @param nodeType 节点类型
     * @param left     左节点
     * @param right    右节点
     */
    public BinaryNode(Enums.NodeType nodeType, AbstractTreeNode left, AbstractTreeNode right) {
        super();
        left.parent = this;
        right.parent = this;
        this.setLeft(left);
        this.setRight(right);
        this.nodeType = nodeType;
    }

    /**
     * 实例化 BinaryClause 类新实例
     *
     * @param token    标记
     * @param nodeType 节点类型
     * @param left     左节点
     * @param right    右节点
     */
    public BinaryNode(Token token, Enums.NodeType nodeType, AbstractTreeNode left, AbstractTreeNode right) {
        super(token);
        left.parent = this;
        right.parent = this;
        this.setLeft(left);
        this.setRight(right);
        this.nodeType = nodeType;
    }

    /**
     * 克隆
     *
     * @return
     */
    @Override
    public AbstractTreeNode dupNode() {
        if (this.getIsNil()) {
            return new BinaryNode(this.getType(), this.getLeft().dupNode(), this.getRight().dupNode());
        }
        return new BinaryNode(this.getToken(), this.getType(), this.getLeft().dupNode(), this.getRight().dupNode());
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
        return this.getLeft().getFirstToken();
    }

    /**
     * 获取最后标记
     */
    @Override
    public Token getLastToken() {
        return this.getRight().getLastToken();
    }

    /**
     * 获取左节点
     */
    private AbstractTreeNode left;

    public AbstractTreeNode getLeft() {
        return left;
    }

    private void setLeft(AbstractTreeNode value) {
        left = value;
    }

    /**
     * 获取右节点
     */
    private AbstractTreeNode right;

    public AbstractTreeNode getRight() {
        return right;
    }

    private void setRight(AbstractTreeNode value) {
        right = value;
    }

    /**
     * 节点表达式
     *
     * @return
     */
    @Override
    public String toString() {
        return String.format("%s %s %s", this.getLeft().toString(), this.delimiter(), this.getRight().toString());
    }

    /**
     * 获取运算符
     *
     * @return
     */
    public String delimiter() {
        switch (this.getType()) {
            case CONCAT:
            case ADD:
                return "+";
            case AND:
                return "&&";
            case DIVIDE:
                return "/";
            case EQUAL:
                return "==";
            case GREATER_THAN:
                return ">";
            case GREATER_THAN_OR_EQUAL:
                return ">=";
            case LESS_THAN:
                return "<";
            case LESS_THAN_OR_EQUAL:
                return "<=";
            case MODULO:
                return "%";
            case MULTIPLY:
                return "*";
            case NOT_EQUAL:
                return "!=";
            case OR:
                return "||";
            case POWER:
                return "^";
            case SUBTRACT:
                return "-";
            default:
                throw new NotSupportException(String.format("二元运算不支持运算符 %s 。", this.getType()));
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
            case ADD:
            case AND:
            case CONCAT:
            case DIVIDE:
            case EQUAL:
            case GREATER_THAN:
            case GREATER_THAN_OR_EQUAL:
            case LESS_THAN:
            case LESS_THAN_OR_EQUAL:
            case MODULO:
            case MULTIPLY:
            case NOT_EQUAL:
            case OR:
            case POWER:
            case SUBTRACT:
                return true;
            default:
                return false;
        }
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
        Variant left, right;
        if (this.getType() == Enums.NodeType.AND || this.getType() == Enums.NodeType.OR) {
            // 采用短路,减少可能不须要产生解析
            left = this.getLeft().parse(parseContext);
            if (this.getType() == Enums.NodeType.AND) {
                if (!left.isBoolean()) {
                    throw this.parserDelimiterException(this.toString(), "逻辑 与 运算类型必须为 VariantType.BOOLEAN");
                }
                if ((boolean) left.getValue()) {
                    right = this.getRight().parse(parseContext).clone();
                    if (!right.isBoolean()) {
                        throw this.parserDelimiterException(this.toString(), "逻辑 与 运算类型必须为 VariantType.BOOLEAN");
                    }
                    return right;
                } else {
                    return new Variant(false);
                }
            } else {
                if (!left.isBoolean()) {
                    throw this.parserDelimiterException(this.toString(), "逻辑 或 运算类型必须为 VariantType.BOOLEAN");
                }
                if ((boolean) left.getValue()) {
                    return new Variant(true);
                } else {
                    right = this.getRight().parse(parseContext).clone();
                    if (!right.isBoolean()) {
                        throw this.parserDelimiterException(this.toString(), "逻辑 或 运算类型必须为 VariantType.BOOLEAN");
                    }
                    return right;
                }
            }
        } else {
            left = this.getLeft().parse(parseContext);
            right = this.getRight().parse(parseContext);
            switch (this.getType()) {
                case EQUAL:
                    return new Variant(left.compareTo(right) == 0);
                case NOT_EQUAL:
                    return new Variant(left.compareTo(right) != 0);
                case LESS_THAN:
                    return new Variant(left.compareTo(right) < 0);
                case LESS_THAN_OR_EQUAL:
                    return new Variant(left.compareTo(right) <= 0);
                case GREATER_THAN:
                    return new Variant(left.compareTo(right) > 0);
                case GREATER_THAN_OR_EQUAL:
                    return new Variant(left.compareTo(right) >= 0);
                case ADD:
                    return Variant.add(left, right);
                case CONCAT:
                    return Variant.join(left, right);
                case SUBTRACT:
                    return Variant.subtract(left, right);
                case MULTIPLY:
                    return Variant.multiply(left, right);
                case DIVIDE:
                    return Variant.divide(left, right);
                case MODULO:
                    return Variant.modulo(left, right);
                case POWER:
                    return Variant.power(left, right);
                default:
                    throw this.parserDelimiterException(this.toString(), String.format("无法解析的表达式，不支持的运算符 \"%s\" 。", this.toString()));
            }
        }
    }


}