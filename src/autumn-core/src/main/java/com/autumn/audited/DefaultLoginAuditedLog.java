package com.autumn.audited;

import com.autumn.util.StringUtils;

/**
 * TODO
 *
 * @Description TODO
 * @Author 老码农
 * @Date 2019-07-27 19:02
 */
public class DefaultLoginAuditedLog extends AbstractAuditedLog implements LoginAuditedLog {

    /**
     * @param clientInfoProvider
     * @param auditedLogStorage
     */
    public DefaultLoginAuditedLog(ClientInfoProvider clientInfoProvider, AuditedLogStorage auditedLogStorage) {
        super(clientInfoProvider, auditedLogStorage);
    }

    @Override
    public void addLog(LoginAuditedLogMessage message) {
        if (message != null) {
            if (message.isSuccess() && StringUtils.isNullOrBlank(message.getStatusMessage())) {
                message.setStatusMessage("成功");
            }
            this.setLogClientMessage(message);
            try {
                this.auditedLogStorage.saveLoginLog(message);
            } catch (Exception err) {
                logger.error("保存登录日志的出错:" + err.getMessage(), err);
            }
        } else {
            logger.error("添加登录日志的对象为 null。");
        }
    }

    @Override
    public void addSuccessLogByAccount(Long userId, String userName, String userAccount) {
        LoginAuditedLogMessage message = new LoginAuditedLogMessage();
        message.setUserId(userId);
        message.setUserName(userName);
        message.setUserAccount(userAccount);
        message.setSuccess(true);
        message.setStatusMessage("成功");
        this.addLog(message);
    }

    @Override
    public void addFailLogByAccount(String userAccount, String statusMessage) {
        LoginAuditedLogMessage message = new LoginAuditedLogMessage();
        message.setUserAccount(userAccount);
        message.setSuccess(false);
        message.setStatusMessage(statusMessage);
        this.addLog(message);
    }

    @Override
    public void addSuccessLogByProvider(Long userId, String userName, String provider, String providerKey) {
        LoginAuditedLogMessage message = new LoginAuditedLogMessage();
        message.setUserId(userId);
        message.setUserName(userName);
        message.setProvider(provider);
        message.setProviderKey(providerKey);
        message.setSuccess(true);
        message.setStatusMessage("成功");
        this.addLog(message);
    }

    @Override
    public void addFailLogByProvider(String provider, String providerKey, String statusMessage) {
        LoginAuditedLogMessage message = new LoginAuditedLogMessage();
        message.setProvider(provider);
        message.setProviderKey(providerKey);
        message.setSuccess(false);
        message.setStatusMessage(statusMessage);
        this.addLog(message);
    }
}
