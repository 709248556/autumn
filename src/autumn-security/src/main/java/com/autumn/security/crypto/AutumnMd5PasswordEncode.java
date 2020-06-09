package com.autumn.security.crypto;

import com.autumn.exception.ExceptionUtils;
import com.autumn.runtime.session.AutumnUser;
import com.autumn.spring.boot.properties.AutumnAuthProperties;
import com.autumn.spring.boot.properties.AutumnSecurityProperties;
import com.autumn.util.Base64Utils;
import com.autumn.util.StringUtils;
import com.autumn.util.security.HashUtils;

/**
 * 采用Md5密码驱动
 *
 * @author 老码农 2018-04-14 18:03:25
 */
public class AutumnMd5PasswordEncode extends AbstractCryptoEncode implements AutumnPasswordEncode {

    /**
     * @param authProperties
     * @param securityProperties
     */
    public AutumnMd5PasswordEncode(AutumnAuthProperties authProperties,
                                   AutumnSecurityProperties securityProperties) {
        super(authProperties, securityProperties);
    }

    /**
     * 编码
     *
     * @param user        用户
     * @param rawPassword 原始密码(外部输入)
     * @return
     */
    protected String onEncode(AutumnUser user, String rawPassword) {
        String source = String.format("%s_%s_%s", user.getId(), rawPassword, StringUtils.reverseOrder(rawPassword));
        return HashUtils.md5(source, this.getCharset());
    }

    @Override
    public boolean isTransferEncryptUserPassword() {
        AutumnAuthProperties.TransferEncrypt encrypt = this.getAuthProperties().getTransferEncrypt();
        if (encrypt != null && encrypt.isUserPassword() && this.isConfigSecurity()) {
            return true;
        }
        return false;
    }

    /**
     * 解密 原始密码
     * <p>
     * 处理客户端输入密码的解密处理
     * </p>
     *
     * @param rawPassword
     * @return
     */
    protected String decryptRawPassword(String rawPassword) {
        if (!this.isTransferEncryptUserPassword()) {
            return rawPassword;
        }
        if (!Base64Utils.isBase64(rawPassword)) {
            throw ExceptionUtils.throwValidationException("密码必须是加密后的 Base64 字符串。");
        }
        try {
            return this.rsaDecrypt(rawPassword.trim());
        } catch (Exception Err) {
            throw ExceptionUtils.throwValidationException("密码必须是加密后的 Base64 字符串。");
        }
    }

    @Override
    public String encode(AutumnUser user, String rawPassword) {
        return this.onEncode(user, this.decryptRawPassword(rawPassword));
    }

    @Override
    public boolean matches(AutumnUser user, String rawPassword) {
        return this.onEncode(user, this.decryptRawPassword(rawPassword)).equals(user.getPassword());
    }

}
