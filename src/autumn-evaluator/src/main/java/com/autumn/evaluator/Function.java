package com.autumn.evaluator;

import com.autumn.evaluator.annotation.FunctionRegister;
import com.autumn.evaluator.annotation.ParamRegister;
import com.autumn.evaluator.impl.FunctionParamImpl;
import com.autumn.evaluator.impl.FunctionParamContextImpl;
import com.autumn.exception.ArgumentNullException;
import com.autumn.exception.ExceptionUtils;
import com.autumn.util.StringUtils;

import java.io.Serializable;

//
// * 表示一个函数,区分类别和说明以及调用格式，对于友好交互提供使用帮助
// * call 表示该函数的回调委托
//

/**
 * 函数
 */
public class Function implements Serializable {

    /**
     * 序列号
     */
    private static final long serialVersionUID = 5588703960148212135L;
    /**
     * 获取函数名称
     */
    private final String name;

    public final String getName() {
        return name;
    }


    private final String category;

    /**
     * 获取函数类别
     */
    public final String getCategory() {
        return this.category;
    }

    private final String caption;

    /**
     * 获取函数说明
     */
    public final String getCaption() {
        return this.caption;
    }

    private String format = "";

    /**
     * 设置格式
     *
     * @param format
     */
    public final void setFormat(String format) {
        this.format = format;
    }

    /**
     * 获取格式
     */
    public final String getFormat() {
        return format;
    }

    /**
     * 获取回调函数
     */
    private final Callable callable;

    /**
     * 获取回调
     *
     * @return
     */
    public final Callable getCallable() {
        return callable;
    }

    /**
     * 获取函数参数集合
     */
    private final FunctionParamContextImpl functionParamContext;

    /**
     * 获取函数参数上下文
     *
     * @return
     */
    public final FunctionParamContextImpl getFunctionParamContext() {
        return this.functionParamContext;
    }

    /**
     * 获取是否是动态参数集合(即参数数量不限制，如SUM)
     */
    private boolean dynamicParam;

    /**
     * 是否是动态参数
     *
     * @return
     */
    public final boolean isDynamicParam() {
        return this.dynamicParam;
    }

    private final int dynamicParamType;

    /**
     * 获取动态参数类型
     */
    public final int getDynamicParamType() {
        return this.dynamicParamType;
    }

    private final int minParamCount;

    /**
     * 获取参数数量或最小参数数量
     */
    public final int getMinParamCount() {
        return minParamCount;
    }

    /**
     * 函数类型
     */
    private final Class<?> callableClass;

    /**
     * 获取函数类型
     *
     * @return
     */
    public final Class<?> getCallableClass() {
        return this.callableClass;
    }

    /**
     * 实例化 Function 类新实例
     *
     * @param callable 回调
     * @throws ArgumentNullException/
     */
    public Function(Callable callable) {
        this.callable = ExceptionUtils.checkNotNull(callable, "callable");
        this.callableClass = callable.getClass();
        FunctionRegister funRegister = this.callableClass.getAnnotation(FunctionRegister.class);
        if (funRegister == null) {
            throw ExceptionUtils.throwSystemException(this.callableClass.getName() + " 未配置 FunctionRegister 注解。");
        }
        this.name = funRegister.name();
        this.category = funRegister.category();
        this.caption = funRegister.caption();
        this.dynamicParam = funRegister.isDynamicParam();
        this.minParamCount = funRegister.minParamCount();
        this.dynamicParamType = funRegister.dynamicParamType();
        this.functionParamContext = new FunctionParamContextImpl(this);
        this.loadRegisterParams();
    }

    /**
     * @param callable
     * @param name
     * @param category
     * @param caption
     * @param isDynamicParam
     * @param dynamicParamType
     * @param minParamCount
     */
    public Function(Callable callable, String name, String category, String caption, boolean isDynamicParam, int dynamicParamType, int minParamCount) {
        this.callable = ExceptionUtils.checkNotNull(callable, "callable");
        this.callableClass = callable.getClass();
        this.name = ExceptionUtils.checkNotNullOrBlank(name, "name");
        this.category = category;
        this.caption = caption;
        this.dynamicParam = isDynamicParam;
        this.minParamCount = minParamCount;
        this.dynamicParamType = dynamicParamType;
        this.functionParamContext = new FunctionParamContextImpl(this);
        this.loadRegisterParams();
    }

    /**
     * 加载注册参数
     */
    private void loadRegisterParams() {
        ParamRegister[] params = this.callableClass.getAnnotationsByType(ParamRegister.class);
        if (params != null) {
            StringBuilder format = new StringBuilder();
            format.append(this.getName() + "(");
            if (params != null && params.length > 0) {
                for (ParamRegister par : params) {
                    if (par.order() <= 0) {
                        ExceptionUtils.throwSystemException("函数[" + this.getName() + "]参数顺序不能小于1");
                    }
                    this.getFunctionParamContext().add(new FunctionParamImpl(par.order(), par.name(), par.caption(),
                            new Variant(par.defaultValue()), par.paramType()));
                    if (par.defaultValue() != null) {
                        format.append(String.format("[%s],", par.name()));
                    } else {
                        format.append(String.format("%s,", par.name()));
                    }
                }
            }
            if (this.isDynamicParam()) {
                if (this.getName().equals(("Switch").toUpperCase())) {
                    format.append("Case1,Break1,Case2,Break2,...,Default");
                } else {
                    String key;
                    if (this.getDynamicParamType() == VariantType.STRING) {
                        key = "Text";
                    } else if (this.getDynamicParamType() == VariantType.BOOLEAN) {
                        key = "Logic";
                    } else if (this.getDynamicParamType() == VariantType.DATETIME) {
                        key = "Date";
                    } else if (this.getDynamicParamType() == VariantType.UUID) {
                        key = "UUID";
                    } else if (this.getDynamicParamType() == VariantType.ARRAY) {
                        key = "ARRAY";
                    } else if ((VariantType.INTEGER & VariantType.DECIMAL & VariantType.DOUBLE) == this.getDynamicParamType()) {
                        key = "NUMBER";
                    } else {
                        key = "Value";
                    }
                    for (int i = 1; i <= 3; i++) {
                        format.append(key + (new Integer(i)).toString() + ",");
                    }
                    format.append("...");
                }
            } else {
                if (params != null && params.length > 0) {
                    format.deleteCharAt(format.length() - 1);
                }
            }
            format.append(")");
            this.format = format.toString();
        }
    }


    @Override
    public String toString() {
        String str = this.getFormat();
        if (StringUtils.isNullOrBlank(str)) {
            return this.getName();
        }
        return str;
    }

}