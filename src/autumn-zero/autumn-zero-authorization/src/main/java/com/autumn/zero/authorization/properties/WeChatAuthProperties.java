package com.autumn.zero.authorization.properties;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;

import java.io.Serializable;

/**
 * 微信授权信息
 *
 * @Description TODO
 * @Author 老码农
 * @Date 2019-07-26 16:46
 */
@Getter
@Setter
@ConfigurationProperties(prefix = WeChatAuthProperties.PREFIX)
public class WeChatAuthProperties implements Serializable {
    private static final long serialVersionUID = 5178362695598331069L;

    /**
     * 属性前缀
     */
    public final static String PREFIX = "autumn.auth.wechat";

    /**
     * 应用id
     */
    private String appId;

    /**
     * 应用密文
     */
    private String appSecret;
}
