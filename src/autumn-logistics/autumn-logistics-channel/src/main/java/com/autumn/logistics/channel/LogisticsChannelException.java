package com.autumn.logistics.channel;

import com.autumn.exception.AutumnException;

/**
 * 物流通道异常
 * <p>
 * </p>
 *
 * @description TODO
 * @author: 老码农
 * @create: 2020-03-02 11:27
 **/
public class LogisticsChannelException extends AutumnException {
    private static final long serialVersionUID = 6079262258822214301L;

    /**
     * 实例化
     */
    public LogisticsChannelException() {
        super("物流通道异常");
    }

    /**
     * 实例化
     *
     * @param message 消息
     */
    public LogisticsChannelException(String message) {
        super(message);
    }

    /**
     * 实例化
     *
     * @param message 消息
     * @param cause   源异常
     */
    public LogisticsChannelException(String message, Throwable cause) {
        super(message, cause);
    }
}
