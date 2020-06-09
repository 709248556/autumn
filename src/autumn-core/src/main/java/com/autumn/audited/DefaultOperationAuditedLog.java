package com.autumn.audited;

import com.autumn.application.ApplicationModule;
import com.autumn.runtime.session.AutumnSession;
import com.autumn.util.StringUtils;

/**
 * 默认操作审计操作
 *
 * @Description TODO
 * @Author 老码农
 * @Date 2019-07-27 18:22
 */
public class DefaultOperationAuditedLog extends AbstractAuditedLog implements OperationAuditedLog {


    private final AutumnSession session;

    /**
     * @param session
     * @param clientInfoProvider
     * @param auditedLogStorage
     */
    public DefaultOperationAuditedLog(AutumnSession session, ClientInfoProvider clientInfoProvider, AuditedLogStorage auditedLogStorage) {
        super(clientInfoProvider, auditedLogStorage);
        this.session = session;
    }

    @Override
    public void addLog(OperationAuditedLogMessage message) {
        if (message != null) {
            message.setUserId(session.getUserId());
            message.setUserName(session.getUserName());
            this.setLogClientMessage(message);
            try {
                this.auditedLogStorage.saveOperationLog(message);
            } catch (Exception err) {
                logger.error("保存操作日志的出错:" + err.getMessage(), err);
            }
        } else {
            logger.error("添加操作日志的对象为 null。");
        }
    }

    @Override
    public void addLog(String moduleName, String operationName, Object logDetails) {
        String msg = this.getLogDetails(logDetails);
        if (StringUtils.isNotNullOrBlank(msg)) {
            OperationAuditedLogMessage message = new OperationAuditedLogMessage();
            message.setModuleName(moduleName);
            message.setOperationName(operationName);
            message.setLogDetails(msg);
            this.addLog(message);
        }
    }

    @Override
    public void addLog(ApplicationModule module, String operationName, Object logDetails) {
        this.addLog(module.getModuleName(), operationName, logDetails);
    }

    @Override
    public String getLogDetails(Object obj) {
        return AuditedUtils.getOperationLogDetails(obj);
    }
}
