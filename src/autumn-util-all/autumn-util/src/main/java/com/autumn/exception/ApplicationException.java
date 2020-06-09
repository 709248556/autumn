package com.autumn.exception;

/**
 * 应用产生的异常
 *
 * @author 老码农
 * <p>
 * 2017-10-09 09:44:28
 */
public class ApplicationException extends AutumnException {

    /**
     *
     */
    private static final long serialVersionUID = -1219860138283113181L;

    /**
     * 无构造实例化
     */
    public ApplicationException() {
        super();
        this.setCode(ApplicationErrorCode.APPLICATION_ERRORCODE);
    }

    /**
     * 实例化
     *
     * @param message 消息
     */
    public ApplicationException(String message) {
        super(message);
        this.setCode(ApplicationErrorCode.APPLICATION_ERRORCODE);
    }

    /**
     * 实例化
     *
     * @param message 消息
     * @param cause   源异常
     */
    public ApplicationException(String message, Throwable cause) {
        super(message, cause);
        this.setCode(ApplicationErrorCode.APPLICATION_ERRORCODE);
    }

    /**
     * 实例化
     *
     * @param cause 源异常
     */
    public ApplicationException(Throwable cause) {
        super(cause);
        this.setCode(ApplicationErrorCode.APPLICATION_ERRORCODE);
    }

    /**
     * 实例化
     *
     * @param message            消息
     * @param cause              源异常
     * @param enableSuppression
     * @param writableStackTrace 写入跟踪
     */
    protected ApplicationException(String message, Throwable cause, boolean enableSuppression,
                                   boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
        this.setCode(ApplicationErrorCode.APPLICATION_ERRORCODE);
    }

    @Override
    public final ErrorLevel getLevel() {
        return ErrorLevel.APPLICATION;
    }

    @Override
    public final void setLevel(ErrorLevel level) {
    }

}
