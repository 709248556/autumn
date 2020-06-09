package com.autumn.security.token;

import com.autumn.exception.ExceptionUtils;

/**
 * 设备登录
 * <p>
 * </p>
 *
 * @description TODO
 * @author: 老码农
 * @create: 2020-04-19 23:54
 **/
public class DeviceAuthenticationToken extends AbstractAutumnAuthenticationToken {

    private static final long serialVersionUID = 6690875615809862131L;
    private final String token;

    /**
     * @param token
     * @param deviceIdentification
     */
    public DeviceAuthenticationToken(String token, String deviceIdentification) {
        this.token = ExceptionUtils.checkNotNullOrBlank(token, "token");
        DefaultCredentialsDeviceInfo deviceInfo = new DefaultCredentialsDeviceInfo();
        deviceInfo.setDeviceIdentification(ExceptionUtils.checkNotNullOrBlank(deviceIdentification, "deviceIdentification"));
        this.setCredentialsDeviceInfo(deviceInfo);
    }

    /**
     * 获取票据
     *
     * @return
     */
    public String getToken() {
        return this.token;
    }

    @Override
    public Object getPrincipal() {
        return this.getCredentialsDeviceInfo();
    }

    @Override
    public Object getCredentials() {
        return this.getToken();
    }

    @Override
    public ToeknAuditedLog createAuditedLog() {
        ToeknAuditedLog log = new ToeknAuditedLog();
        log.setUserAccount("");
        log.setProvider(this.getCredentialsDeviceInfo().getDeviceType() + " " + this.getCredentialsDeviceInfo().getPlatformName());
        log.setProviderKey(this.getToken());
        log.setFailStatusMessage("票据(Token)与设备标识不匹配。");
        return log;
    }
}
