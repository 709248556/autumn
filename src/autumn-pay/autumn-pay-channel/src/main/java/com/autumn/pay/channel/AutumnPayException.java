package com.autumn.pay.channel;

import com.autumn.exception.AutumnException;

/**
 * 支付异常
 *
 * @Description TODO
 * @Author 老码农
 * @Date 2019-07-12 14:03
 */
public class AutumnPayException extends AutumnException {

    private static final long serialVersionUID = -7109394021805365883L;

    /**
     * 无构造实例化
     */
    public AutumnPayException() {
        super();
    }

    /**
     * 实例化
     *
     * @param message 消息
     */
    public AutumnPayException(String message) {
        super(message);
    }

    /**
     * 实例化
     *
     * @param message 消息
     * @param cause   源异常
     */
    public AutumnPayException(String message, Throwable cause) {
        super(message, cause);
    }

    /**
     * 实例化
     *
     * @param cause 源异常
     */
    public AutumnPayException(Throwable cause) {
        super(cause);
    }

    /**
     * 实例化
     *
     * @param message            消息
     * @param cause              源异常
     * @param enableSuppression
     * @param writableStackTrace 写入跟踪
     */
    protected AutumnPayException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
