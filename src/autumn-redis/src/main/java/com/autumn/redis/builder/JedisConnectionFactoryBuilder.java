package com.autumn.redis.builder;

import org.springframework.boot.autoconfigure.data.redis.RedisProperties;
import org.springframework.data.redis.connection.RedisClusterConfiguration;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.connection.RedisSentinelConfiguration;
import org.springframework.data.redis.connection.jedis.JedisClientConfiguration;
import org.springframework.data.redis.connection.jedis.JedisConnectionFactory;
import org.springframework.util.StringUtils;
import redis.clients.jedis.JedisPoolConfig;

import java.time.Duration;

/**
 * 基于 Jedis 的 Redis 连接工厂生成器抽象
 * <p>
 * </p>
 *
 * @description TODO
 * @author: 老码农
 * @create: 2020-04-29 17:55
 **/
public class JedisConnectionFactoryBuilder extends RedisConnectionFactoryBuilder {

    /**
     * 实例化
     *
     * @param properties
     */
    public JedisConnectionFactoryBuilder(RedisProperties properties) {
        super(properties);
    }

    @Override
    public RedisConnectionFactory createRedisConnectionFactory() {
        JedisClientConfiguration clientConfiguration = getJedisClientConfiguration();
        RedisSentinelConfiguration sentinelConfig = this.getSentinelConfig();
        if (sentinelConfig != null) {
            return new JedisConnectionFactory(sentinelConfig, clientConfiguration);
        }
        RedisClusterConfiguration clusterConfig = this.getClusterConfiguration();
        if (clusterConfig != null) {
            return new JedisConnectionFactory(clusterConfig, clientConfiguration);
        }
        return new JedisConnectionFactory(getStandaloneConfig(), clientConfiguration);
    }

    protected JedisClientConfiguration getJedisClientConfiguration() {
        JedisClientConfiguration.JedisClientConfigurationBuilder builder = applyProperties(
                JedisClientConfiguration.builder());
        RedisProperties.Jedis jedis = this.getProperties().getJedis();
        if (jedis != null) {
            RedisProperties.Pool pool = jedis.getPool();
            if (pool != null) {
                applyPooling(pool, builder);
            }
        }
        if (StringUtils.hasText(this.getProperties().getUrl())) {
            customizeConfigurationFromUrl(builder);
        }
        return builder.build();
    }

    protected JedisClientConfiguration.JedisClientConfigurationBuilder applyProperties(
            JedisClientConfiguration.JedisClientConfigurationBuilder builder) {
        if (this.getProperties().isSsl()) {
            builder.useSsl();
        }
        if (this.getProperties().getTimeout() != null) {
            Duration timeout = this.getProperties().getTimeout();
            builder.readTimeout(timeout).connectTimeout(timeout);
        }
        return builder;
    }

    protected JedisPoolConfig jedisPoolConfig(RedisProperties.Pool pool) {
        JedisPoolConfig config = new JedisPoolConfig();
        config.setMaxTotal(pool.getMaxActive());
        config.setMaxIdle(pool.getMaxIdle());
        config.setMinIdle(pool.getMinIdle());
        if (pool.getMaxWait() != null) {
            config.setMaxWaitMillis(pool.getMaxWait().toMillis());
        }
        return config;
    }

    private void applyPooling(RedisProperties.Pool pool,
                              JedisClientConfiguration.JedisClientConfigurationBuilder builder) {
        builder.usePooling().poolConfig(jedisPoolConfig(pool));
    }

    private void customizeConfigurationFromUrl(
            JedisClientConfiguration.JedisClientConfigurationBuilder builder) {
        ConnectionInfo connectionInfo = parseUrl(this.getProperties().getUrl());
        if (connectionInfo.isUseSsl()) {
            builder.useSsl();
        }
    }


}
