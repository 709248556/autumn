package com.autumn.evaluator.exception;

import java.io.Serializable;

/**
 * 函数异常
 */
public class FunctionException extends ParserException implements Serializable {

    /**
	 * 序列号
	 */
	private static final long serialVersionUID = 3813546071542072383L;

	/**
     * 实例化 FunctionException 类新实例
     *
     * @param startIndex   开始位置
     * @param length       长度
     * @param functionName 函数名称
     * @param message      消息名称
     */
    public FunctionException(int startIndex, int length, String functionName, String message) {
        this(startIndex, length, functionName, message, null);
    }

    /**
     * 实例化 FunctionException 类新实例
     *
     * @param startIndex     开始位置
     * @param length         长度
     * @param functionName   函数名称
     * @param message        消息名称
     * @param innerException 引起该异常的异常
     */
    public FunctionException(int startIndex, int length, String functionName, String message, RuntimeException innerException) {
        super(startIndex, length, message, innerException);
        this.setFunctionName(functionName);
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

}