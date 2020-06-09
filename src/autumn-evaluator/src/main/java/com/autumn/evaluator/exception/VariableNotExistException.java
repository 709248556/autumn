package com.autumn.evaluator.exception;

//
// * 变量不存在而引起的异常，由于变量支持动态生成,所以异常生成解析树时不会发生，而是运行时才会引发；
//

import java.io.Serializable;

/**
 * 解析变量不存在引起的异常
 */
public class VariableNotExistException extends ParserException implements Serializable {

    /**
	 * 序列号
	 */
	private static final long serialVersionUID = 21831423333126818L;

	/**
     * 实例化 ParserVariableException 类新实例
     *
     * @param startIndex   开始位置
     * @param length       长度
     * @param variableName 变量名称
     * @param message      消息名称
     */
    public VariableNotExistException(int startIndex, int length, String variableName, String message) {
        super(startIndex, length, message);
        this.setVariableName(variableName);
    }

    /**
     * 获取变量名
     */
    private String variableName;

    public final String getVariableName() {
        return variableName;
    }

    private void setVariableName(String value) {
        variableName = value;
    }

}