package com.autumn.zero.authorization.application.services.impl;

import com.autumn.audited.AuditedLogStorage;
import com.autumn.audited.LoginAuditedLogMessage;
import com.autumn.audited.OperationAuditedLogMessage;
import com.autumn.util.AutoMapUtils;
import com.autumn.zero.authorization.entities.common.log.UserLoginLog;
import com.autumn.zero.authorization.entities.common.log.UserOperationLog;
import com.autumn.zero.authorization.repositories.common.log.UserLoginLogRepository;
import com.autumn.zero.authorization.repositories.common.log.UserOperationLogRepository;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * 审计日志存储
 *
 * @Description TODO
 * @Author 老码农
 * @Date 2019-07-28 2:34
 */
public class AuditedLogStorageImpl implements AuditedLogStorage {

    @Autowired
    private UserLoginLogRepository userLoginLogRepository;

    @Autowired
    private UserOperationLogRepository userOperationLogRepository;

    public AuditedLogStorageImpl() {

    }

    @Override
    public void saveLoginLog(LoginAuditedLogMessage message) {
        UserLoginLog log = AutoMapUtils.map(message, UserLoginLog.class);
        log.forNullToDefault();
        userLoginLogRepository.insert(log);
    }

    @Override
    public void saveOperationLog(OperationAuditedLogMessage message) {
        UserOperationLog log = AutoMapUtils.map(message, UserOperationLog.class);
        log.forNullToDefault();
        userOperationLogRepository.insert(log);
    }
}
