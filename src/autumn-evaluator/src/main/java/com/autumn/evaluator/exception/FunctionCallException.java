package com.autumn.evaluator.exception;

import java.io.Serializable;

/**
 * 函数调用异常
 */
public class FunctionCallException extends FunctionException implements Serializable {

    /**
	 * 序列号
	 */
	private static final long serialVersionUID = 1257070821348246928L;

	/**
     * 实例化 FunctionCallException 类新实例
     *
     * @param startIndex     开始位置
     * @param length         长度
     * @param functionName   函数名称
     * @param message        消息名称
     * @param innerException 引起该异常的异常
     */
    public FunctionCallException(int startIndex, int length, String functionName, String message, RuntimeException innerException) {
        super(startIndex, length, functionName, message, innerException);
    }

}