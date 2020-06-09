package com.autumn.evaluator.exception;

//
// * 函数参数异常，根据特性 FunctionRegisterAttribute和ParamRegisterAttribute 的配置，未符合条件则引起异常
// * 参数过多、缺少、数据类型不匹配等
//

import java.io.Serializable;

/**
 * 函数参数异常
 */
public class FunctionParamException extends RuntimeException implements Serializable {

    /**
	 * 序列号
	 */
	private static final long serialVersionUID = -1963982025484257329L;

	/**
     * 获取参数名称
     */
    private String paramName;

    public final String getParamName() {
        return paramName;
    }

    private void setParamName(String value) {
        paramName = value;
    }

    /**
     * 获取函数名称
     */
    private String functionName;

    public final String getFunctionName() {
        return functionName;
    }

    private void setFunctionName(String value) {
        functionName = value;
    }

    /**
     * 实例化 FunctionParamException 类新实例
     *
     * @param functionName 函数名称
     * @param paramName    参数名称
     * @param message      消息
     */
    public FunctionParamException(String functionName, String paramName, String message) {
        super(message);
        this.setFunctionName(functionName);
        this.setParamName(paramName);
    }
}