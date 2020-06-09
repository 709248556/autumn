package com.autumn.spring.boot.properties;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 * 授权
 *
 * @author 老码农
 * <p>
 * Description
 * </p>
 * @date 2017-11-04 02:25:49
 */
@ConfigurationProperties(prefix = AutumnAuthProperties.PREFIX)
@Getter
@Setter
public class AutumnAuthProperties extends AbstractAutumnProperties {

    /**
     *
     */
    private static final long serialVersionUID = 808855799819234501L;

    /**
     * 属性前缀
     */
    public final static String PREFIX = "autumn.auth";

    /**
     * 默认 Token 开头
     */
    public static final String DEFAULT_TOKEN_HEAD = "Basic";

    /**
     * 默认 缓存键前缀
     */
    public static final String DEFAULT_CACHE_KEY_PREFIX = "autumn_auth_cache:";

    /**
     * 默认 会话键前缀
     */
    public static final String DEFAULT_SESSION_KEY_PREFIX = "autumn_session:";

    /**
     * 内存缓存提供程序
     */
    public static final String CACHE_PROVIDER_MEMORY = "memory";

    /**
     * Redis缓存提供程序
     */
    public static final String REDIS_PROVIDER_MEMORY = "redis";

    /**
     * 基于 token 的密钥
     */
    private String tokenSecret;

    /**
     * 会话过期(秒)
     */
    private int expire = 3600;

    /**
     * 设备过期(秒)
     * <p>
     * 小于或等于0，表示永远不过期
     * </p>
     */
    private int deviceExpire = -1;

    /**
     * Token 开头
     */
    private String tokenHead = DEFAULT_TOKEN_HEAD;
    /**
     * 缓存键前缀
     */
    private String cacheKeyPrefix = DEFAULT_CACHE_KEY_PREFIX;
    /**
     * 会话键前缀
     */
    private String sessionKeyPrefix = DEFAULT_SESSION_KEY_PREFIX;
    /**
     * 缓存提供程序
     */
    private String cacheProvider = CACHE_PROVIDER_MEMORY;
    /**
     * 授权会话 redis 配置(如果驱动为 redis 时)
     */
    private AuthRedisProperties redis;

    /**
     * 传输加密
     */
    private TransferEncrypt transferEncrypt = new TransferEncrypt();

    /**
     * 是否记住我
     */
    private boolean isRememberMe;

    /**
     * 启用图形码证码
     */
    private boolean enableImageCaptcha = true;

    /**
     * 图形验证码过期时间
     */
    private int imageCaptchaExpire = 1800;

    /**
     * 图形验证码登录错误次数
     */
    private int imageCaptchaLoginErrorCount = 3;

    /**
     * 实例化
     */
    public AutumnAuthProperties() {
        this.setRedis(new AuthRedisProperties());
    }

    /**
     * 是否是 Redis 提供缓存
     *
     * @return
     */
    public boolean isRedisCacheProvider() {
        return REDIS_PROVIDER_MEMORY.equals(this.getCacheProvider());
    }

    /**
     * 授权 Auth Redis 属性
     *
     * @author 老码农 2018-12-06 12:39:28
     */
    public static class AuthRedisProperties extends AbstractRedisProperties {

        /**
         *
         */
        private static final long serialVersionUID = 6113162939282188516L;
    }

    /**
     * 传输加密
     */
    @Getter
    @Setter
    public static class TransferEncrypt {

        /**
         * 用户密码
         * <p>
         * 对用户密码进行加密转输
         * </p>
         */
        private boolean userPassword = false;

        /**
         * 设备id加密
         */
        private boolean deviceId = false;

    }


}
