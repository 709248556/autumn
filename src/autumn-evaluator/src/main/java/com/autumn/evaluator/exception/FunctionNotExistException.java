package com.autumn.evaluator.exception;

import java.io.Serializable;

/**
 * 由于函数不存在而产生的异常
 */
public class FunctionNotExistException extends FunctionException implements Serializable {

    /**
	 * 序列号
	 */
	private static final long serialVersionUID = 6945863379886975363L;

	/**
     * 实例化 FunctionNotExistException 类新实例
     *
     * @param startIndex   开始位置
     * @param length       长度
     * @param functionName 函数名称
     * @param message      消息名称
     */
    public FunctionNotExistException(int startIndex, int length, String functionName, String message) {
        super(startIndex, length, functionName, message);
    }
}