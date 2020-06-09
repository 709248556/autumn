package com.autumn.audited;

/**
 * 审计日志存储
 *
 * @Description TODO
 * @Author 老码农
 * @Date 2019-07-27 19:20
 */
public interface AuditedLogStorage {

    /**
     * 保存登录日志
     *
     * @param message 消息
     */
    void saveLoginLog(LoginAuditedLogMessage message);

    /**
     * 保存操作日志
     *
     * @param message 消息
     */
    void saveOperationLog(OperationAuditedLogMessage message);

}
