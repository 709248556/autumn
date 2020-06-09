package com.autumn.evaluator.exception;

import java.io.Serializable;

/**
 * 解析异常
 */
public class ParserException extends RuntimeException implements Serializable {

    /**
	 * 序列号
	 */
	private static final long serialVersionUID = 8868820566111389929L;

	/**
     * 实例化 ParserException 类新实例
     *
     * @param startIndex 开始位置
     * @param length     长度
     * @param message    消息
     */
    public ParserException(int startIndex, int length, String message) {
        this(startIndex, length, message, null);
    }

    /**
     * 实例化 ParserException 类新实例
     *
     * @param startIndex     开始位置
     * @param length         长度
     * @param message        消息
     * @param innerException 引起的异常
     */
    public ParserException(int startIndex, int length, String message, RuntimeException innerException) {
        super(message, innerException);
        this.setStartIndex(startIndex);
        this.setLength(length);
    }

    /**
     * 获取开始位置
     */
    private int startIndex;

    public final int getStartIndex() {
        return startIndex;
    }

    private void setStartIndex(int value) {
        startIndex = value;
    }

    /**
     * 获取长度
     */
    private int length;

    public final int getLength() {
        return length;
    }

    private void setLength(int value) {
        length = value;
    }

}