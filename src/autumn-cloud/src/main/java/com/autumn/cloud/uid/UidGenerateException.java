package com.autumn.cloud.uid;

import com.autumn.exception.ApplicationException;

/**
 * Uid 生成异常
 *
 * @Description TODO
 * @Author 老码农
 * @Date 2019-08-25 18:05
 */
public class UidGenerateException extends ApplicationException {
    private static final long serialVersionUID = 4977249888349481767L;

    /**
     * 无构造实例化
     */
    public UidGenerateException() {
        super();
    }

    /**
     * 实例化
     *
     * @param message 消息
     */
    public UidGenerateException(String message) {
        super(message);
    }

    /**
     * 实例化
     *
     * @param message 消息
     * @param cause   源异常
     */
    public UidGenerateException(String message, Throwable cause) {
        super(message, cause);
    }

    /**
     * 实例化
     *
     * @param cause 源异常
     */
    public UidGenerateException(Throwable cause) {
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
    protected UidGenerateException(String message, Throwable cause, boolean enableSuppression,
                                   boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }

}
