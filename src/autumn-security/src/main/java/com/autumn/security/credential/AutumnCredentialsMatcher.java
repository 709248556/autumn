package com.autumn.security.credential;

import com.autumn.audited.LoginAuditedLog;
import com.autumn.audited.LoginAuditedLogMessage;
import com.autumn.exception.ExceptionUtils;
import com.autumn.runtime.session.AutumnUser;
import com.autumn.security.crypto.AutumnDeviceEncode;
import com.autumn.security.crypto.AutumnPasswordEncode;
import com.autumn.security.session.AutumnWebSessionManager;
import com.autumn.security.token.AutumnAuthenticationToken;
import com.autumn.security.token.ToeknAuditedLog;
import com.autumn.spring.boot.properties.AutumnAuthProperties;
import com.autumn.util.WebUtils;
import org.apache.shiro.authc.AuthenticationInfo;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authc.credential.CredentialsMatcher;
import org.apache.shiro.subject.Subject;
import org.apache.shiro.util.ThreadContext;
import org.springframework.beans.factory.annotation.Autowired;

import javax.servlet.http.HttpServletRequest;

/**
 * 认证匹配器
 *
 * @author 老码农 2018-12-06 14:52:59
 */
public class AutumnCredentialsMatcher implements CredentialsMatcher {

    @Autowired
    protected LoginAuditedLog loginAuditedLog;

    @Autowired
    protected AutumnDeviceEncode deviceEncode;

    @Autowired
    protected AutumnPasswordEncode passwordEncode;

    private final AutumnUserCredentialsService credentialsService;

    /**
     * @param credentialsService
     */
    public AutumnCredentialsMatcher(AutumnUserCredentialsService credentialsService) {
        this.credentialsService = credentialsService;
    }

    @Override
    public boolean doCredentialsMatch(AuthenticationToken token, AuthenticationInfo info) {
        AutumnAuthenticationToken autumnToken;
        if (token instanceof AutumnAuthenticationToken) {
            autumnToken = (AutumnAuthenticationToken) token;
        } else {
            throw ExceptionUtils.throwNotSupportException("不支持的认证类型[" + token.getClass().getName() + "]");
        }
        AutumnUser user = (AutumnUser) info.getPrincipals().getPrimaryPrincipal();
        boolean isMatch = this.credentialsService.matches(user, autumnToken);
        if (isMatch) {
            Subject subject = ThreadContext.getSubject();
            if (subject != null) {
                subject.logout();
            }
            if (user.getDeviceInfo() != null) {
                HttpServletRequest request = WebUtils.getCurrentRequest();
                if (request != null) {
                    request.setAttribute(AutumnWebSessionManager.TOKEN_SESSION_ID, user.getDeviceInfo().getDeviceToken());
                }
            }
        }
        this.addCredentiaLog(user, autumnToken, isMatch);
        return isMatch;
    }

    /**
     * 获取认证服务
     *
     * @return
     */
    public AutumnUserCredentialsService getCredentialsService() {
        return this.credentialsService;
    }

    /**
     * 传输加密
     *
     * @return
     */
    public AutumnAuthProperties.TransferEncrypt transferEncrypt() {
        AutumnAuthProperties.TransferEncrypt encrypt = new AutumnAuthProperties.TransferEncrypt();
        encrypt.setUserPassword(passwordEncode.isTransferEncryptUserPassword());
        encrypt.setDeviceId(deviceEncode.isTransferEncryptDeviceId());
        return encrypt;
    }

    /**
     * 添加认证日志
     *
     * @param user
     * @param token
     * @param isMatch
     */
    private void addCredentiaLog(AutumnUser user, AutumnAuthenticationToken token, boolean isMatch) {
        ToeknAuditedLog log = token.createAuditedLog();
        String account = log.getUserAccount() != null ? log.getUserAccount() : "";
        String provider = log.getProvider() != null ? log.getProvider() : "";
        String providerKey = log.getProviderKey() != null ? log.getProviderKey() : "";
        LoginAuditedLogMessage msg = new LoginAuditedLogMessage();
        msg.setUserId(user.getId());
        msg.setUserName(user.getUserName());
        msg.setUserAccount(account);
        msg.setProvider(provider);
        msg.setProviderKey(providerKey);
        msg.setSuccess(isMatch);
        if (isMatch) {
            msg.setStatusMessage("成功");
        } else {
            msg.setStatusMessage(log.getFailStatusMessage() != null ? log.getFailStatusMessage() : "认证失败");
        }
        this.loginAuditedLog.addLog(msg);
    }
}
