package com.autumn.spring.boot.properties;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;

import java.io.Serializable;

/**
 * 安全属性
 * <p>
 * </p>
 *
 * @description TODO
 * @author: 老码农
 * @create: 2020-04-23 16:45
 **/
@ConfigurationProperties(prefix = AutumnSecurityProperties.PREFIX)
@Getter
@Setter
public class AutumnSecurityProperties extends AbstractAutumnProperties {

    private static final long serialVersionUID = 5329875137511228181L;

    /**
     * 属性前缀
     */
    public final static String PREFIX = "autumn.security";

    /**
     * Rsa 配置
     */
    private AutumnSecurityRsaProperties rsa = new AutumnSecurityRsaProperties();

    /**
     * Rsa安全属性
     */
    @Getter
    @Setter
    public class AutumnSecurityRsaProperties implements Serializable {

        private static final long serialVersionUID = -1459970526806023591L;

        /**
         * 是否启用
         */
        private boolean enable = true;

        /**
         * 公钥(PKCS8 base64格式)
         */
        private String publicKey;

        /**
         * 私钥(PKCS8 base64格式)
         */
        private String privateKey;

    }

}
