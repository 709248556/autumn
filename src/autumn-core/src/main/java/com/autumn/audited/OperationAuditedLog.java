package com.autumn.audited;

import com.autumn.application.ApplicationModule;

/**
 * 操作审计
 *
 * @Description TODO
 * @Author 老码农
 * @Date 2019-07-27 18:05
 */
public interface OperationAuditedLog {

    /**
     * 添加日志
     *
     * @param message 消息
     */
    void addLog(OperationAuditedLogMessage message);

    /**
     * 添加日志
     *
     * @param moduleName    模块名称
     * @param operationName 操作名称
     * @param logDetails    日志详情
     */
    void addLog(String moduleName, String operationName, Object logDetails);

    /**
     * 添加日志
     *
     * @param module        模块
     * @param operationName 操作名称
     * @param logDetails    日志详情
     */
    void addLog(ApplicationModule module, String operationName, Object logDetails);

    /**
     * 获取日志详情
     *
     * @param obj 对象
     * @return
     */
    String getLogDetails(Object obj);

}
