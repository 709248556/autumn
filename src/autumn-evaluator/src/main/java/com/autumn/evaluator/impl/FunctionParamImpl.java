package com.autumn.evaluator.impl;

//
// * 表示 call 委托（用户自定义函数）的一个参数 通过 this[]属于获取(VB则为item方法)，索引从1开始，
//

import com.autumn.evaluator.*;
import com.autumn.evaluator.exception.FunctionParamException;
import com.autumn.evaluator.nodes.AbstractTreeNode;
import com.autumn.util.StringUtils;

import java.io.Serializable;

/**
 * 函数参数
 */
public class FunctionParamImpl implements FunctionParam, Serializable {

    /**
     * 序列号
     */
    private static final long serialVersionUID = 5475332525619464250L;

    private static final String DEFAULT_PARAM_NAME = "defaultName";

    /**
     * 实例化 FunParam 类新实例
     *
     * @param order        顺序
     * @param name         参数名称
     * @param caption      参数说明
     * @param defaultValue 默认值
     * @param paramType    参数类型
     */
    public FunctionParamImpl(int order, String name, String caption, Variant defaultValue, int paramType) {
        if (StringUtils.isNullOrBlank(name)) {
            this.name = DEFAULT_PARAM_NAME;
        } else {
            this.name = name;
        }
        this.order = order;
        this.caption = caption;
        this.defaultValue = defaultValue;
        this.paramType = paramType;
    }

    /**
     * 实例化 FunParam 类新实例
     *
     * @param order        顺序
     * @param name         参数名称
     * @param caption      参数说明
     * @param defaultValue 默认值
     * @param paramType    参数类型
     * @param parseContext 解析上下文
     * @param node         节点
     */
    public FunctionParamImpl(int order, String name, String caption, Variant defaultValue, int paramType, ParseContext parseContext, AbstractTreeNode node) {
        this(order, name, caption, defaultValue, paramType);
        this.parseContext = parseContext;
        this.node = node;
    }


    private ParseContext parseContext = null;
    private AbstractTreeNode node = null;

    /**
     * 创建调用参数
     *
     * @param parseContext 解析上下文
     * @param node         节点
     * @return
     */
    public FunctionParamImpl createCallParam(ParseContext parseContext,
                                             AbstractTreeNode node) {
        FunctionParamImpl functionParam = new FunctionParamImpl(this.getOrder(), this.getName(), this.getCaption(), this.getDefaultValue(), this.getParamType());
        functionParam.parseContext = parseContext;
        functionParam.node = node;
        return functionParam;
    }

    /**
     * 获取参数顺序
     */
    private final int order;


    @Override
    public final int getOrder() {
        return order;
    }


    /**
     * 参数名称
     */
    private final String name;

    @Override
    public final String getName() {
        return name;
    }

    private final String caption;

    @Override
    public final String getCaption() {
        return this.caption;
    }

    /**
     * 参数类型
     */
    private final int paramType;

    @Override
    public final int getParamType() {
        return this.paramType;
    }

    /**
     * 参数值默认值
     */
    private final Variant defaultValue;

    @Override
    public final Variant getDefaultValue() {
        return defaultValue;
    }

    @Override
    public final Variant getValue() {
        if (this.node != null) {
            Variant value = node.parse(this.parseContext);
            if (value.isNull()) {
                return this.getDefaultValue();
            }
            if (this.getParamType() != VariantType.NULL && value.isNull()) {
                String message = String.format("第 %s 参数 [%s] 不能为空值。", (new Integer(this.getOrder())).toString(), this.getName());
                throw new FunctionParamException(this.getParamContext().getFunction().getName(), this.getName(), message);
            } else {
                if (this.getParamType() != VariantType.NULL && !((this.getParamType() & value.getVariantType()) == value.getVariantType())) {
                    String message = String.format("第 %s 个参数 [%s]  的类型必须是 [%s] 。", (new Integer(this.getOrder())).toString(), this.getName(), this.getParamType());
                    throw new FunctionParamException(this.getParamContext().getFunction().getName(), this.getName(), message);
                }
            }
            return value;
        } else {
            return this.getDefaultValue();
        }
    }

    /**
     * 获取父级参数集合
     */
    private FunctionParamContextImpl paramContext;


    @Override
    public final FunctionParamContext getParamContext() {
        return this.paramContext;
    }

    /**
     * 设置函数
     *
     * @param paramContext 参数上下文
     */
    public final void setParamContext(FunctionParamContextImpl paramContext) {
        this.paramContext = paramContext;
    }
}