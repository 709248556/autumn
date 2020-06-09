package com.autumn.audited;

import lombok.ToString;

/**
 * 操作审计消息
 *
 * @Description TODO
 * @Author 老码农
 * @Date 2019-07-27 18:13
 */
@ToString(callSuper = true)
public class OperationAuditedLogMessage extends AbstractAuditedMessage {

    private static final long serialVersionUID = 1246420190759582465L;

    /**
     * 模块名称
     */
    private String moduleName;

    /**
     * 操作名称
     */
    private String operationName;

    /**
     * 日志详情
     */
    private String logDetails;

    public String getModuleName() {
        return moduleName;
    }

    public void setModuleName(String moduleName) {
        this.moduleName = moduleName;
    }

    public String getOperationName() {
        return operationName;
    }

    public void setOperationName(String operationName) {
        this.operationName = operationName;
    }

    public String getLogDetails() {
        return logDetails;
    }

    public void setLogDetails(String logDetails) {
        this.logDetails = logDetails;
    }
}
