package com.autumn.security.token;

import org.apache.shiro.authc.UsernamePasswordToken;

/**
 * 用户名称或密码
 *
 * @author 老码农 2018-12-06 11:21:30
 */
public class UserNamePasswordAuthenticationToken extends UsernamePasswordToken implements AutumnAuthenticationToken {

    /**
     *
     */
    private static final long serialVersionUID = 2913321780177558038L;

    /**
     *
     */
    public UserNamePasswordAuthenticationToken() {
        super();
    }

    /**
     * @param username
     * @param password
     */
    public UserNamePasswordAuthenticationToken(final String username, final char[] password) {
        super(username, password);
    }

    /**
     * @param username
     * @param password
     * @param rememberMe
     */
    public UserNamePasswordAuthenticationToken(final String username, final String password, final boolean rememberMe) {
        super(username, password != null ? password.toCharArray() : null, rememberMe, null);
    }

    /**
     * 实例化
     *
     * @param username
     * @param password
     * @param rememberMe
     * @param host
     */
    public UserNamePasswordAuthenticationToken(final String username, final String password,
                                               final boolean rememberMe, final String host) {
        super(username, password != null ? password.toCharArray() : null, rememberMe, host);
    }

    private CredentialsDeviceInfo credentialsDeviceInfo;

    @Override
    public CredentialsDeviceInfo getCredentialsDeviceInfo() {
        return this.credentialsDeviceInfo;
    }

    @Override
    public void setCredentialsDeviceInfo(CredentialsDeviceInfo credentialsDeviceInfo) {
        this.credentialsDeviceInfo = credentialsDeviceInfo;
    }

    @Override
    public ToeknAuditedLog createAuditedLog() {
        ToeknAuditedLog log = new ToeknAuditedLog();
        log.setUserAccount(this.getUsername());
        log.setProvider("");
        log.setProviderKey("");
        log.setFailStatusMessage("用户名或密码不正确。");
        return log;
    }
}
