package com.autumn.security.token;

import org.apache.shiro.authc.AuthenticationToken;

/**
 * 授权抽象
 *
 * @author 老码农 2018-12-06 11:29:52
 */
public abstract class AbstractAutumnAuthenticationToken implements AuthenticationToken, AutumnAuthenticationToken {

    /**
     *
     */
    private static final long serialVersionUID = -3854223437906552191L;

    /**
     *
     */
    public AbstractAutumnAuthenticationToken() {

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
}
