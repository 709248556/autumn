package com.autumn.evaluator.exception;

//
// * 解析或运算时，由于运算符引起的异常
// * 类型不匹配（如字符窜乘法引起异常）、不符合运算规则的运算符或运算不支持运算符
//

import java.io.Serializable;

/**
 * 运算符解析异常
 */
public class ParserDelimiterException extends ParserException implements Serializable {

    /**
	 * 序列号
	 */
	private static final long serialVersionUID = -8291299965606289384L;

	/**
     * 实例化 ParserVariableException 类新实例
     *
     * @param startIndex 开始位置
     * @param length     长度
     * @param delimiter  运算符
     * @param message    消息名称
     */
    public ParserDelimiterException(int startIndex, int length, String delimiter, String message) {
        this(startIndex, length, delimiter, message, null);
    }

    /**
     * 实例化 ParserVariableException 类新实例
     *
     * @param startIndex     开始位置
     * @param length         长度
     * @param delimiter      运算符
     * @param message        消息名称
     * @param innerException 引起的异常
     */
    public ParserDelimiterException(int startIndex, int length, String delimiter, String message, RuntimeException innerException) {
        super(startIndex, length, message, innerException);
        this.setDelimiter(delimiter);
    }

    /**
     * 获取运算符
     */
    private String delimiter;

    public final String getDelimiter() {
        return delimiter;
    }

    private void setDelimiter(String value) {
        delimiter = value;
    }

}