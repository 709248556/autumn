package com.autumn.word;

/**
 * 解析产生的异常
 */
@SuppressWarnings("serial")
public class WordEvaluateException extends RuntimeException {
    /**
     * Word 解析产生的异常
     *
     * @param message 消息
     */
    public WordEvaluateException(String message) {
        super(message);
    }
}