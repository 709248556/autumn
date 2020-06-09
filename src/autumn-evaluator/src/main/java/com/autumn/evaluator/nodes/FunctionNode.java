package com.autumn.evaluator.nodes;

import com.autumn.evaluator.*;
import com.autumn.evaluator.exception.FunctionCallException;
import com.autumn.evaluator.exception.FunctionException;
import com.autumn.evaluator.exception.FunctionNotExistException;
import com.autumn.evaluator.impl.FunctionParamContextImpl;
import com.autumn.evaluator.impl.FunctionParamImpl;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * 函数
 */
public final class FunctionNode extends AbstractTreeNode implements Serializable {

    /**
     * 序列号
     */
    private static final long serialVersionUID = -4690221043889702990L;

    /**
     * 实例化 FunctionNode 类新实例
     *
     * @param name       名称
     * @param collection 集合
     */
    FunctionNode(String name, AbstractTreeNode[] collection) {
        super();
        this.setName(name);
        this.setArguments(this.readArguments(collection));
    }

    /**
     * 实例化 FunctionNode 类新实例
     *
     * @param token      标记
     * @param collection 集合
     */
    FunctionNode(Token token, AbstractTreeNode[] collection) {
        super(token);
        this.setName(token.getText().toUpperCase());
        this.setArguments(this.readArguments(collection));
    }

    /**
     * 获取函数名称
     */
    private String name;

    public String getName() {
        return name;
    }

    private void setName(String value) {
        name = value;
    }

    private List<AbstractTreeNode> readArguments(AbstractTreeNode[] collection) {
        ArrayList<AbstractTreeNode> items = new ArrayList<>();
        for (AbstractTreeNode item : collection) {
            item.parent = this;
            items.add(item);
        }
        return items;
    }

    /**
     * 获取参数集合
     */
    private List<AbstractTreeNode> arguments;

    public List<AbstractTreeNode> getArguments() {
        return arguments;
    }

    private void setArguments(List<AbstractTreeNode> value) {
        arguments = value;
    }

    /**
     * 获取节点类型
     */
    @Override
    public Enums.NodeType getType() {
        return Enums.NodeType.CALL;
    }

    /**
     * 克隆
     *
     * @return
     */
    @Override
    public AbstractTreeNode dupNode() {
        FunctionNode fun;
        if (this.getIsNil()) {
            fun = new FunctionNode(this.getName(), this.getArguments().toArray(new AbstractTreeNode[0]));
        } else {
            fun = new FunctionNode(this.getToken(), this.getArguments().toArray(new AbstractTreeNode[0]));
        }
        return fun;
    }

    /**
     * 获取首个标记
     */
    @Override
    public Token getFirstToken() {
        return this.getToken();
    }

    private Token lastToken = null;
    private boolean readLastToken = false;

    /**
     * 获取最后标记
     */
    @Override
    public Token getLastToken() {
        if (!readLastToken) {
            if (this.getArguments().size() > 0) {
                this.lastToken = this.getArguments().get(getArguments().size() - 1).getLastToken();
            } else {
                this.lastToken = this.getToken();
            }
            readLastToken = true;
        }
        return this.lastToken;
    }

    /**
     * @param msg
     * @return
     */
    private FunctionException createFunctionException(String msg) {
        if (this.getIsNil()) {
            return new FunctionException(-1, 0, this.getName(), msg);
        } else {
            return new FunctionException(this.getToken().getStartIndex(), this.getToken().getLength(), this.getName(), msg);
        }
    }

    /**
     * 节点表达式
     *
     * @return
     */
    @Override
    public String toString() {
        StringBuilder str = new StringBuilder();
        str.append(this.getName());
        str.append("(");
        for (int i = 0; i < this.getArguments().size(); i++) {
            if (i > 0) {
                str.append(",");
            }
            AbstractTreeNode value = this.getArguments().get(i);
            if (!(value instanceof NullNode)) {
                str.append(value.toString());
            }
        }
        str.append(")");
        return str.toString();
    }

    /**
     * @param parseContext
     * @return
     */
    @Override
    public Variant parse(ParseContext parseContext) {
        Utils.checkNotNull("parseContext", parseContext);
        Function fun = FunctionManager.getFunction(this.getName());
        if (fun != null) {
            FunctionParamContextImpl funParamContext = new FunctionParamContextImpl(this.getName());
            for (int i = 0; i < this.getArguments().size(); i++) {
                int order = i + 1;
                FunctionParamImpl functionParam = (FunctionParamImpl) fun.getFunctionParamContext().getParam(order);
                AbstractTreeNode node = this.getArguments().get(i);
                if (functionParam != null) {
                    funParamContext.add(functionParam.createCallParam(parseContext, node));
                } else {
                    //动态参数
                    functionParam = new FunctionParamImpl(order, "", "", Variant.DEFAULT, fun.getDynamicParamType(), parseContext, node);
                    funParamContext.add(functionParam);
                }
            }
            // 无参数函数验证
            if (!fun.isDynamicParam() && fun.getMinParamCount() <= 0 && fun.getFunctionParamContext().getParamSize() == 0
                    && funParamContext.getParamSize() > 0) {
                throw this.createFunctionException(String.format("参数过多,最多只能输入 %s 个参数,实际传入 %s 个参数。",
                        fun.getFunctionParamContext().getParamSize(), funParamContext.getParamSize()));
            }
            // 低于最小参数
            if (fun.getMinParamCount() > 0 && funParamContext.getParamSize() < fun.getMinParamCount()) {
                throw this.createFunctionException(
                        String.format("缺少参数,至少需要输入 %s 个参数,实际传入 %s 个参数。", fun.getMinParamCount(), funParamContext.getParamSize()));
            }
            if (fun.getFunctionParamContext().getParamSize() > 0) {
                // 非动态参数时，传入大于设置参数，则抛参数过多
                if (!fun.isDynamicParam() && funParamContext.getParamSize() > fun.getFunctionParamContext().getParamSize()) {
                    throw this.createFunctionException(String.format("参数过多,最多只能输入 %s 个参数,实际传入 %s 个参数。", fun.getFunctionParamContext().getParamSize(), funParamContext.getParamSize()));
                }
                if (fun.getFunctionParamContext().getParamSize() > funParamContext.getParamSize()) // 添加默认参数
                {
                    for (FunctionParam param : fun.getFunctionParamContext().getParams()) {
                        if (!param.getDefaultValue().isNull()) {
                            FunctionParam p1 = funParamContext.getParam(param.getOrder());
                            if (p1 == null) {
                                funParamContext.add(new FunctionParamImpl(param.getOrder(), param.getName(), param.getCaption(),
                                        param.getDefaultValue().clone(), param.getDefaultValue().getVariantType()));
                            }
                        }
                    }
                }
            }
            try {
                return fun.getCallable().call(this.getName(), funParamContext, parseContext.getContext());
            } catch (RuntimeException err) {
                if (this.getIsNil()) {
                    throw new FunctionCallException(-1, this.getName().length(), this.getName(), err.getMessage(), err);
                } else {
                    throw new FunctionCallException(this.getToken().getStartIndex(), this.getToken().getLength(),
                            this.getName(), err.getMessage(), err);
                }
            }
        } else {
            if (this.getIsNil()) {
                throw new FunctionNotExistException(-1, this.getName().length(), this.getName(),
                        String.format("函数 %s 未注册。", this.getName()));
            } else {
                throw new FunctionNotExistException(this.getToken().getStartIndex(), this.getToken().getLength(),
                        this.getName(), String.format("函数 %s 未注册。", this.getName()));
            }
        }
    }

}