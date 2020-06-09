package com.autumn.security.crypto;

import com.autumn.exception.ExceptionUtils;
import com.autumn.spring.boot.properties.AutumnAuthProperties;
import com.autumn.spring.boot.properties.AutumnSecurityProperties;
import com.autumn.util.StringUtils;
import com.autumn.util.security.RsaUtils;

import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.security.InvalidKeyException;
import java.security.PrivateKey;
import java.security.spec.InvalidKeySpecException;

/**
 * 编码抽象
 * <p>
 * </p>
 *
 * @description TODO
 * @author: 老码农
 * @create: 2020-04-23 21:05
 **/
public abstract class AbstractCryptoEncode {

    private Charset charset = StandardCharsets.UTF_8;
    private final AutumnAuthProperties authProperties;
    private final AutumnSecurityProperties securityProperties;

    /**
     * @param authProperties
     * @param securityProperties
     */
    public AbstractCryptoEncode(AutumnAuthProperties authProperties,
                                AutumnSecurityProperties securityProperties) {
        this.authProperties = authProperties;
        this.securityProperties = securityProperties;
    }

    /**
     * 获取编辑
     *
     * @return
     */
    public Charset getCharset() {
        return charset;
    }

    /**
     * 设置编码
     *
     * @param charset 编码
     */
    public void setCharset(Charset charset) {
        this.charset = charset;
    }

    /**
     * 获取授权属性
     *
     * @return
     */
    public AutumnAuthProperties getAuthProperties() {
        return this.authProperties;
    }

    /**
     * 获取安全属性
     *
     * @return
     */
    public AutumnSecurityProperties getSecurityProperties() {
        return this.securityProperties;
    }

    /**
     * 是否配置安全
     *
     * @return
     */
    protected boolean isConfigSecurity() {
        return StringUtils.isNotNullOrBlank(this.getRsaPrivateKey());
    }

    /**
     * 获取 Rsa 私钥
     *
     * @return
     */
    protected String getRsaPrivateKey() {
        if (this.getSecurityProperties().getRsa() == null
                || !this.getSecurityProperties().getRsa().isEnable()) {
            return "";
        }
        return this.getSecurityProperties().getRsa().getPrivateKey();
    }

    /**
     * Rsa 解密
     *
     * @param base64Content base64编码内容
     * @return
     */
    protected String rsaDecrypt(String base64Content) {
        try {
            PrivateKey privateKey = RsaUtils.getPrivateKey(this.getRsaPrivateKey());
            return RsaUtils.decryptFormString(base64Content, privateKey, this.getCharset());
        } catch (InvalidKeySpecException | InvalidKeyException e) {
            throw ExceptionUtils.throwSystemException("系统出错，无法获取 RSA 提供程序。");
        }
    }
}
