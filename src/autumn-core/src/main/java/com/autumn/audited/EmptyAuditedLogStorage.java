package com.autumn.audited;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

/**
 * 空审计日志实现
 *
 * @Description TODO
 * @Author 老码农
 * @Date 2019-07-27 19:27
 */
public class EmptyAuditedLogStorage implements AuditedLogStorage {

    /**
     * 日志
     */
    protected static final Log logger = LogFactory.getLog(EmptyAuditedLogStorage.class);

    @Override
    public void saveLoginLog(LoginAuditedLogMessage message) {
        logger.info("登录-》" + message.toString());
    }

    @Override
    public void saveOperationLog(OperationAuditedLogMessage message) {
        logger.info("操作-》" + message.toString());
    }
}
