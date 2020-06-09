package com.autumn.evaluator.nodes;

import com.autumn.evaluator.Enums;
import com.autumn.evaluator.ParseContext;
import com.autumn.evaluator.Token;
import com.autumn.evaluator.Variant;

import java.io.Serializable;

/**
 * 常量节点
 */
public final class ConstantNode extends AbstractTreeNode implements Serializable {

    /**
	 * 序列号
	 */
	private static final long serialVersionUID = 400954709327567364L;

	/**
     * 实例化 ConstantNode 类新实例
     *
     * @param token 标记
     */
    public ConstantNode(Token token) {
        super(token);
        this.setValue((Variant) token.getValue());
    }

    /**
     * 实例化 ConstantNode 类新实例
     *
     * @param value 值
     */
    public ConstantNode(Variant value) {
        super();
        this.setValue(value);
    }

    /**
     * 克隆
     *
     * @return
     */
    @Override
    public AbstractTreeNode dupNode() {
        if (this.getIsNil()) {
            return new ConstantNode(this.getValue().clone());
        }
        return new ConstantNode(this.getToken());
    }

    /**
     * 获取节点类型
     */
    @Override
    public Enums.NodeType getType() {
        return Enums.NodeType.CONSTANT;
    }

    /**
     * 获取值
     */
    private Variant value;

    public Variant getValue() {
        return value;
    }

    private void setValue(Variant value) {
        this.value = value;
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
     * 节点表达式
     *
     * @return
     */
    @Override
    public String toString() {
        if (this.getValue().isString() || this.getValue().isUUID()) {
            return String.format("%s%s%s", "\"", this.getValue().toString(), "\"");
        }
        if (this.getValue().isNull()) {
            return "NULL";
        }
        if (this.getValue().isDateTime()) {
            return String.format("%s[%s]", "\"", this.getValue().toString());
        }
        if (this.getValue().isArray()) {
            StringBuilder sb = new StringBuilder();
            sb.append("#");
            Variant[] nodes = (Variant[]) this.getValue().getValue();
            for (int i = 0; i < nodes.length; i++) {
                if (i > 0) {
                    sb.append(",");
                }
                Variant subNode = nodes[i];
                if (subNode.isArray()) {
                    Variant[] subNodes = (Variant[]) subNode.getValue();
                    if (subNodes.length == 1) {
                        subNode = subNodes[0];
                    }
                }
                if (subNode.isString()) {
                    sb.append(String.format("%s%s", "\"", subNode.toString()));
                } else if (subNode.isDateTime()) {
                    sb.append(String.format("%s[%s]", "\"", subNode.toString()));
                } else if (subNode.isNull()) {
                    sb.append("NULL");
                } else {
                    sb.append(subNode.toString());
                }
            }
            sb.append("#");
            return sb.toString();
        }
        return this.getValue().toString();
    }

    /**
     * 解析
     *
     * @param parseContext 上下文
     * @return
     */
    @Override
    public Variant parse(ParseContext parseContext) {
        return this.getValue();
    }


}