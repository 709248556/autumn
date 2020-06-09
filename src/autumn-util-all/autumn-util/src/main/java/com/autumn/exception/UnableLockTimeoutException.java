package com.autumn.exception;


/**
 * 由于无法获取锁超时而产生的异常
 *
 * @author 老码农
 * <p>
 * 2017-11-23 11:31:17
 */
public class UnableLockTimeoutException extends ApplicationException {

    /**
     *
     */
    private static final long serialVersionUID = -592128077745037615L;

    /**
     * 由于无法获取分布式锁而产生的异常
     */
    public static final int UNABLE_TO_AQUIRE_LOCK_ERRORCODE = ApplicationErrorCode.APPLICATION_ERRORCODE + 5000;

    /**
     * 无构造实例化
     */
    public UnableLockTimeoutException() {
        super();
        this.setCode(UNABLE_TO_AQUIRE_LOCK_ERRORCODE);
    }

    /**
     * 实例化
     *
     * @param message 消息
     */
    public UnableLockTimeoutException(String message) {
        super(message);
        this.setCode(UNABLE_TO_AQUIRE_LOCK_ERRORCODE);
    }

    /**
     * 实例化
     *
     * @param message 消息
     * @param cause   源异常
     */
    public UnableLockTimeoutException(String message, Throwable cause) {
        super(message, cause);
        this.setCode(UNABLE_TO_AQUIRE_LOCK_ERRORCODE);
    }

    /**
     * 实例化
     *
     * @param cause 源异常
     */
    public UnableLockTimeoutException(Throwable cause) {
        super(cause);
        this.setCode(UNABLE_TO_AQUIRE_LOCK_ERRORCODE);
    }

    /**
     * 实例化
     *
     * @param message            消息
     * @param cause              源异常
     * @param enableSuppression
     * @param writableStackTrace 写入跟踪
     */
    protected UnableLockTimeoutException(String message, Throwable cause, boolean enableSuppression,
                                         boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
        this.setCode(UNABLE_TO_AQUIRE_LOCK_ERRORCODE);
    }

}
