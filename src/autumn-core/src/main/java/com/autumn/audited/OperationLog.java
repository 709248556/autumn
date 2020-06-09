package com.autumn.audited;

/**
 * 操作审计消息
 * <p>
 * 实现该接口可生成操作日志,与 {@link com.autumn.audited.annotation.LogMessage } 注解等同，但此接口优先
 * </p>
 *
 * @Description TODO
 * @Author 老码农
 * @Date 2019-07-27 18:04
 */
public interface OperationLog {

    /**
     * 日志消息
     *
     * @return
     */
    String logMessage();
}
