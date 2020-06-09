package com.autumn.security.token;

import org.apache.shiro.authc.AuthenticationToken;

/**
 * 授权身份票据
 *
 * @author 老码农 2018-12-06 11:22:21
 */
public interface AutumnAuthenticationToken extends AuthenticationToken {

    /**
     * 获取认证设备信息
     *
     * @return
     */
    CredentialsDeviceInfo getCredentialsDeviceInfo();

    /**
     * 设置设备信息
     *
     * @param credentialsDeviceInfo 认证设备信息
     */
    void setCredentialsDeviceInfo(CredentialsDeviceInfo credentialsDeviceInfo);

    /**
     * 创建审计日志
     *
     * @return
     */
    ToeknAuditedLog createAuditedLog();
}
