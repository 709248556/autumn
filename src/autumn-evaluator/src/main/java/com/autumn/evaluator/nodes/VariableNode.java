package com.autumn.evaluator.nodes;

import com.autumn.evaluator.*;
import com.autumn.evaluator.exception.VariableNotExistException;

import java.io.Serializable;

/**
 * 变量节点
 */
public final class VariableNode extends AbstractTreeNode implements Serializable {

    /**
     * 序列号
     */
    private static final long serialVersionUID = -2435743056301589256L;

    /**
     * 实例化 VariableNode 类新实例
     *
     * @param token 标记
     */
    public VariableNode(Token token) {
        super(token);
        this.setName(token.getText());
    }

    /**
     * 实例化 VariableNode 类新实例
     *
     * @param name 值
     */
    public VariableNode(String name) {
        super();
        this.setName(name);
    }

    /**
     * 获取变量名称
     */
    private String name;

    public String getName() {
        return name;
    }

    private void setName(String value) {
        name = value;
    }

    /**
     * 获取节点类型
     */
    @Override
    public Enums.NodeType getType() {
        return Enums.NodeType.VARIABLE;
    }

    /**
     * 克隆
     *
     * @return
     */
    @Override
    public AbstractTreeNode dupNode() {
        if (this.getIsNil()) {
            return new VariableNode(this.getName());
        }
        return new VariableNode(this.getToken());
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
        return this.getName();
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
     * 解析
     *
     * @param parseContext 上下文
     * @return
     */
    @Override
    public Variant parse(ParseContext parseContext) {
        Utils.checkNotNull("parseContext", parseContext);
        Variable systemVariable = Variable.getVariable(this.getName());
        if (systemVariable != null) {
            return systemVariable.getValue();
        }
        Context context = parseContext.getContext();
        Variant value = context.getVariable(this.getName());
        if (value != null) {
            return value;
        } else {
            if (this.getIsNil()) {
                throw new VariableNotExistException(-1, this.getName().length(), this.getName(),
                        String.format("无法解析的表达式，变量 %s 不存在。", this.getName()));
            } else {
                throw new VariableNotExistException(this.getToken().getStartIndex(), this.getToken().getLength(),
                        this.getName(), String.format("无法解析的表达式，变量 %s 不存在。", this.getName()));
            }
        }
    }
}