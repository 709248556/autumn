package com.autumn.evaluator;

//
// * 标记结构和解析支持语法类型，对于CS则支持 &&、||、==、!= 而VB表达式则支持 and、or、=、<>运算符
//

import java.io.Serializable;

/**
 * 解析标记
 */
public class Token implements Serializable {

    /**
	 * 序列号
	 */
	private static final long serialVersionUID = -6442576494408841105L;

	/**
     * 实例化 Token 类新实例
     *
     * @param text       名称
     * @param value      值
     * @param type       类型
     * @param startIndex 开始位置
     * @param length     长度
     */
    public Token(String text, Object value, Enums.TokenType type, int startIndex, int length) {
        this.setText(text);
        this.setType(type);
        this.setStartIndex(startIndex);
        this.setLength(length);
        this.setConst((type == Enums.TokenType.INTEGER || type == Enums.TokenType.DECIMAL || type == Enums.TokenType.DOUBLE || type == Enums.TokenType.STRING || type == Enums.TokenType.DATETIME || type == Enums.TokenType.UUID || type == Enums.TokenType.STATIC_ARRAY));
        if (value == null) {
            this.setValue(text);
        } else {
            this.setValue(value);
        }
    }

    /**
     * 获取文本
     */
    private String text;

    public final String getText() {
        return text;
    }

    private void setText(String value) {
        text = value;
    }

    /**
     * 获取值
     */
    private Object value;

    public final Object getValue() {
        return value;
    }

    private void setValue(Object value) {
        this.value = value;
    }

    /**
     * 获取是否属于常量
     */
    private boolean isConst;

    public boolean isConst() {
        return isConst;
    }

    public void setConst(boolean aConst) {
        isConst = aConst;
    }

    /**
     * 获取类型
     */
    private Enums.TokenType type;

    public final Enums.TokenType getType() {
        return type;
    }

    private void setType(Enums.TokenType value) {
        type = value;
    }

    /**
     * 开始位置
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

    /**
     * 输出解析名称
     *
     * @return
     */
    @Override
    public String toString() {
        return this.getText();
    }
}