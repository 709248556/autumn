package com.autumn.audited;

import com.autumn.timing.Clock;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

/**
 * 操
 *
 * @Description TODO
 * @Author 老码农
 * @Date 2019-07-27 19:02
 */
public abstract class AbstractAuditedLog {

    /**
     * 日志
     */
    protected final Log logger = LogFactory.getLog(this.getClass());

    /**
     * 客户端提供程序
     */
    protected final ClientInfoProvider clientInfoProvider;

    /**
     * 审计日志存储
     */
    protected final AuditedLogStorage auditedLogStorage;

    /**
     *
     * @param clientInfoProvider
     * @param auditedLogStorage
     */
    public AbstractAuditedLog(ClientInfoProvider clientInfoProvider, AuditedLogStorage auditedLogStorage) {
        this.clientInfoProvider = clientInfoProvider;
        this.auditedLogStorage = auditedLogStorage;
    }

    /**
     * 设置客户端消息
     *
     * @param message 消息
     */
    protected void setLogClientMessage(AbstractAuditedMessage message) {
        ClientBrowserInfo browserInfo = clientInfoProvider.getBrowserInfo();
        message.setGmtCreate(Clock.now());
        message.setClientIpAddress(clientInfoProvider.getClientIpAddress());
        message.setClientName(clientInfoProvider.getClientPlatformName());
        message.setClientVersion(clientInfoProvider.getClientPlatformVersion());
        message.setBrowserName(browserInfo.getBrowserName());
        message.setBrowserInfo(browserInfo.getBrowserInfo());
        message.setBrowserPlatform(browserInfo.getBrowserPlatform());
    }
}
