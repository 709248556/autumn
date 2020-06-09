package com.autumn.security.crypto;

import com.autumn.exception.ExceptionUtils;
import com.autumn.runtime.session.AutumnUser;
import com.autumn.runtime.session.AutumnUserDeviceInfo;
import com.autumn.runtime.session.DefaultAutumnUserDeviceInfo;
import com.autumn.security.token.CredentialsDeviceInfo;
import com.autumn.spring.boot.properties.AutumnAuthProperties;
import com.autumn.spring.boot.properties.AutumnSecurityProperties;
import com.autumn.util.AutoMapUtils;
import com.autumn.util.Base64Utils;
import com.autumn.util.StringUtils;

import java.util.Locale;
import java.util.UUID;

/**
 * 默认设备驱动提供
 * <p>
 * </p>
 *
 * @description TODO
 * @author: 老码农
 * @create: 2020-04-20 00:55 AutumnAuthProperties authProperties
 **/
public class DefaultAutumnDeviceEncode extends AbstractCryptoEncode implements AutumnDeviceEncode {

    /**
     * @param authProperties
     * @param securityProperties
     */
    public DefaultAutumnDeviceEncode(AutumnAuthProperties authProperties, AutumnSecurityProperties securityProperties) {
        super(authProperties, securityProperties);
    }

    @Override
    public boolean matches(AutumnUser user, CredentialsDeviceInfo rawCredentialsDeviceInfo) {
        if (user.getDeviceInfo() == null || rawCredentialsDeviceInfo == null
                || StringUtils.isNullOrBlank(user.getDeviceInfo().getDeviceIdentification())
                || StringUtils.isNullOrBlank(rawCredentialsDeviceInfo.getDeviceIdentification())) {
            return false;
        }
        String deviceId = this.onEncodeDeviceId(rawCredentialsDeviceInfo);
        return user.getDeviceInfo().getDeviceIdentification().equals(deviceId);
    }


    @Override
    public AutumnUserDeviceInfo createStorage(AutumnUser user, CredentialsDeviceInfo rawCredentialsDeviceInfo) {
        DefaultAutumnUserDeviceInfo userDeviceInfo = AutoMapUtils.map(rawCredentialsDeviceInfo, DefaultAutumnUserDeviceInfo.class);
        String token = (UUID.randomUUID().toString() + "." + UUID.randomUUID().toString()).replace("-", "").toUpperCase(Locale.ENGLISH);
        userDeviceInfo.setDeviceToken(token);
        userDeviceInfo.setExpires(this.getAuthProperties().getDeviceExpire());
        //根据配置确定是否解密设备id
        userDeviceInfo.setDeviceIdentification(this.onEncodeDeviceId(rawCredentialsDeviceInfo));
        return userDeviceInfo;
    }

    /**
     * 编码设备id
     *
     * @param user        用户
     * @param rawPassword 原始密码(外部输入)
     * @return
     */
    protected String onEncodeDeviceId(CredentialsDeviceInfo rawCredentialsDeviceInfo) {
        if (rawCredentialsDeviceInfo == null || StringUtils.isNullOrBlank(rawCredentialsDeviceInfo.getDeviceIdentification())) {
            return "";
        }
        return this.decryptDeviceId(rawCredentialsDeviceInfo.getDeviceIdentification().trim());
    }

    /**
     * 解密 原始设备id
     * <p>
     * 处理客户端输设备id的解密处理
     * </p>
     *
     * @param rawPassword
     * @return
     */
    protected String decryptDeviceId(String deviceIdentification) {
        if (!this.isTransferEncryptDeviceId()) {
            return deviceIdentification;
        }
        if (!Base64Utils.isBase64(deviceIdentification)) {
            throw ExceptionUtils.throwValidationException("DeviceId 必须是加密后的 Base64 字符串。");
        }
        try {
            return this.rsaDecrypt(deviceIdentification.trim());
        } catch (Exception Err) {
            throw ExceptionUtils.throwValidationException("DeviceId 必须是加密后的 Base64 字符串。");
        }
    }

    @Override
    public boolean isTransferEncryptDeviceId() {
        AutumnAuthProperties.TransferEncrypt encrypt = this.getAuthProperties().getTransferEncrypt();
        if (encrypt != null && encrypt.isDeviceId() && this.isConfigSecurity()) {
            return true;
        }
        return false;
    }

}
