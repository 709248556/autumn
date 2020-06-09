package com.autumn.evaluator.nodes;

import com.autumn.evaluator.*;
import com.autumn.evaluator.exception.VariableNotExistException;
import com.autumn.exception.ArgumentNullException;

import java.io.Serializable;
import java.util.ArrayList;

/**
 * 数组节点
 */
public final class ArrayNode extends AbstractTreeNode implements Serializable {
    /**
     * 序列号
     */
    private static final long serialVersionUID = -2271471346658290390L;

    /**
     * 实例化 ArrayNode 类新实例
     *
     * @param start 开始
     * @param end   结束
     */
    public ArrayNode(ArrayPosition start, ArrayPosition end) {
        super();
        this.setStartPosition(start);
        this.setEndPosition(end);
    }

    /**
     * 实例化 ArrayNode 类新实例
     *
     * @param token 标记
     * @param start 开始
     * @param end   结束
     */
    public ArrayNode(Token token, ArrayPosition start, ArrayPosition end) {
        super(token);
        this.setStartPosition(start);
        this.setEndPosition(end);
    }

    /**
     * 获取节点类型
     */
    @Override
    public Enums.NodeType getType() {
        return Enums.NodeType.ARRAY;
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
     * 获取开始位置
     */
    private ArrayPosition startPosition;

    public ArrayPosition getStartPosition() {
        return startPosition;
    }

    private void setStartPosition(ArrayPosition value) {
        startPosition = value;
    }

    /**
     * 获取结束位置
     */
    private ArrayPosition endPosition;

    public ArrayPosition getEndPosition() {
        return endPosition;
    }

    private void setEndPosition(ArrayPosition value) {
        endPosition = value;
    }

    /**
     * @return
     */
    @Override
    public AbstractTreeNode dupNode() {
        ArrayNode node;
        if (this.getIsNil()) {
            node = new ArrayNode(this.getStartPosition(), this.getEndPosition());
        } else {
            node = new ArrayNode(this.getToken(), this.getStartPosition(), this.getEndPosition());
        }
        return node;
    }

    /**
     * @param parseContext
     * @return
     */
    @Override
    public Variant parse(ParseContext parseContext) {
        Utils.checkNotNull("parseContext", parseContext);
        Context context = parseContext.getContext();
        if (context == null) {
            throw new ArgumentNullException("Context");
        }
        ArrayList<Variant> colValues = new ArrayList<>();
        for (int c = this.getStartPosition().getColNumber(); c <= this.getEndPosition().getColNumber(); c++) {
            ArrayList<Variant> rowValues = new ArrayList<>();
            String colName = ArrayPosition.getArrayColName(c);
            for (int r = this.getStartPosition().getRowNumber(); r <= this.getEndPosition().getRowNumber(); r++) {
                String variable = colName + (new Integer(r)).toString();
                Variant value = context.getVariable(variable);
                if (value != null) {
                    rowValues.add(value);
                } else {
                    throw this.variableNotExistException(parseContext, variable);
                }
            }
            colValues.add(new Variant(colName, rowValues.toArray(new Variant[]{})));
        }
        return new Variant(colValues.toArray(new Variant[0]));
    }

    /**
     * 输出节点
     *
     * @return
     */
    @Override
    public String toString() {
        return String.format("%s:%s", this.getStartPosition().toString(), this.getEndPosition().toString());
    }

    private VariableNotExistException variableNotExistException(ParseContext parseContext, String variable) {
        if (this.getIsNil()) {
            return new VariableNotExistException(-1, this.toString().length(), variable,
                    String.format("无法解析的表达式，变量 %s 不存在。", variable));
        } else {
            return new VariableNotExistException(this.getToken().getStartIndex(), this.getToken().getLength(), variable,
                    String.format("无法解析的表达式，变量 %s 不存在。", variable));
        }
    }
}