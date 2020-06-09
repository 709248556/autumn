package com.autumn.evaluator.impl;

//
// * 表示 FunctionParam 集合，应用于 call 委托（用户自定义函数），根据表达式函数参数解析传入，以逗号","分隔
//

import com.autumn.evaluator.Function;
import com.autumn.evaluator.FunctionManager;
import com.autumn.evaluator.FunctionParam;
import com.autumn.evaluator.FunctionParamContext;
import com.autumn.util.StringUtils;

import java.io.Serializable;
import java.util.Collection;
import java.util.HashMap;

/**
 * 函数参数上下文
 */
public class FunctionParamContextImpl implements FunctionParamContext, Serializable {

    /**
     * 序列号
     */
    private static final long serialVersionUID = -5022048948799674082L;

    private HashMap<Integer, FunctionParam> PARAM_INTEGER_MAP = new HashMap<>();
    private HashMap<String, FunctionParam> PARAM_NAME_MAP = new HashMap<>();

    /**
     * 实例化 FunParams 类新实例
     *
     * @param funName
     */
    public FunctionParamContextImpl(String funName) {
        this.function = FunctionManager.getFunction(funName);
    }

    /**
     * 实例化 FunParams 类新实例
     *
     * @param fun 函数
     */
    public FunctionParamContextImpl(Function fun) {
        this.function = fun;
    }

    /**
     * 函数
     */
    private final Function function;

    @Override
    public final Function getFunction() {
        return function;
    }


    /**
     * 添加参数
     *
     * @param param 参数
     */
    public final void add(FunctionParamImpl param) {
        param.setParamContext(this);
        PARAM_INTEGER_MAP.put(param.getOrder(), param);
        if (param.getName() != null) {
            PARAM_NAME_MAP.put(param.getName().toUpperCase(), param);
        }
    }

    @Override
    public final int getParamSize() {
        return this.PARAM_INTEGER_MAP.size();
    }

    /**
     * 获取参数
     *
     * @param order 顺序
     * @return
     */
    @Override
    public final FunctionParam getParam(int order) {
        return this.PARAM_INTEGER_MAP.get(order);
    }

    /**
     * 获取参数
     *
     * @param name 名称
     * @return
     */
    @Override
    public final FunctionParam getParam(String name) {
        if (StringUtils.isNullOrBlank(name)) {
            return null;
        }
        return PARAM_NAME_MAP.get(name.toUpperCase());
    }

    /**
     * 循环访问 FunParam 枚举数
     *
     * @return
     */
    @Override
    public final Collection<FunctionParam> getParams() {
        return this.PARAM_INTEGER_MAP.values();
    }
}