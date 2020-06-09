package com.autumn.redis.builder;

import io.lettuce.core.resource.ClientResources;
import org.apache.commons.pool2.impl.GenericObjectPoolConfig;
import org.springframework.boot.autoconfigure.data.redis.RedisProperties;
import org.springframework.data.redis.connection.RedisClusterConfiguration;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.connection.RedisSentinelConfiguration;
import org.springframework.data.redis.connection.lettuce.LettuceClientConfiguration;
import org.springframework.data.redis.connection.lettuce.LettuceConnectionFactory;
import org.springframework.data.redis.connection.lettuce.LettucePoolingClientConfiguration;
import org.springframework.util.StringUtils;

/**
 * 基于 Lettuce 的 Redis 连接工厂生成器抽象
 * <p>
 * </p>
 *
 * @description TODO
 * @author: 老码农
 * @create: 2020-04-29 18:01
 **/
public class LettuceConnectionConfigurationBuilder extends RedisConnectionFactoryBuilder {

    private final ClientResources clientResources;

    /**
     * 实例化
     *
     * @param properties
     */
    public LettuceConnectionConfigurationBuilder(RedisProperties properties) {
        this(properties, null);
    }

    /**
     * 实例化
     *
     * @param properties
     * @param clientResources
     */
    public LettuceConnectionConfigurationBuilder(RedisProperties properties, ClientResources clientResources) {
        super(properties);
        this.clientResources = clientResources;
    }

    @Override
    public RedisConnectionFactory createRedisConnectionFactory() {
        LettuceClientConfiguration clientConfig = getLettuceClientConfiguration(
                this.clientResources, this.getProperties().getLettuce().getPool());
        return createLettuceConnectionFactory(clientConfig);
    }


    private LettuceConnectionFactory createLettuceConnectionFactory(
            LettuceClientConfiguration clientConfiguration) {
        RedisSentinelConfiguration sentinelConfig = this.getSentinelConfig();
        if (sentinelConfig != null) {
            return new LettuceConnectionFactory(getSentinelConfig(), clientConfiguration);
        }
        RedisClusterConfiguration clusterConfig = this.getClusterConfiguration();
        if (clusterConfig != null) {
            return new LettuceConnectionFactory(getClusterConfiguration(), clientConfiguration);
        }
        return new LettuceConnectionFactory(getStandaloneConfig(), clientConfiguration);
    }

    private LettuceClientConfiguration getLettuceClientConfiguration(
            ClientResources clientResources, RedisProperties.Pool pool) {
        LettuceClientConfiguration.LettuceClientConfigurationBuilder builder = createBuilder(pool);
        applyProperties(builder);
        if (StringUtils.hasText(this.getProperties().getUrl())) {
            customizeConfigurationFromUrl(builder);
        }
        builder.clientResources(clientResources);
        return builder.build();
    }

    private LettuceClientConfiguration.LettuceClientConfigurationBuilder applyProperties(
            LettuceClientConfiguration.LettuceClientConfigurationBuilder builder) {
        if (this.getProperties().isSsl()) {
            builder.useSsl();
        }
        if (this.getProperties().getTimeout() != null) {
            builder.commandTimeout(this.getProperties().getTimeout());
        }
        if (this.getProperties().getLettuce() != null) {
            RedisProperties.Lettuce lettuce = this.getProperties().getLettuce();
            if (lettuce.getShutdownTimeout() != null
                    && !lettuce.getShutdownTimeout().isZero()) {
                builder.shutdownTimeout(this.getProperties().getLettuce().getShutdownTimeout());
            }
        }
        return builder;
    }

    private void customizeConfigurationFromUrl(
            LettuceClientConfiguration.LettuceClientConfigurationBuilder builder) {
        ConnectionInfo connectionInfo = parseUrl(this.getProperties().getUrl());
        if (connectionInfo.isUseSsl()) {
            builder.useSsl();
        }
    }

    private LettuceClientConfiguration.LettuceClientConfigurationBuilder createBuilder(RedisProperties.Pool pool) {
        if (pool == null) {
            return LettuceClientConfiguration.builder();
        }
        return new PoolBuilderFactory().createBuilder(pool);
    }

    /**
     * Inner class to allow optional commons-pool2 dependency.
     */
    private static class PoolBuilderFactory {

        public LettuceClientConfiguration.LettuceClientConfigurationBuilder createBuilder(RedisProperties.Pool properties) {
            return LettucePoolingClientConfiguration.builder()
                    .poolConfig(getPoolConfig(properties));
        }

        private GenericObjectPoolConfig getPoolConfig(RedisProperties.Pool properties) {
            GenericObjectPoolConfig config = new GenericObjectPoolConfig();
            config.setMaxTotal(properties.getMaxActive());
            config.setMaxIdle(properties.getMaxIdle());
            config.setMinIdle(properties.getMinIdle());
            if (properties.getMaxWait() != null) {
                config.setMaxWaitMillis(properties.getMaxWait().toMillis());
            }
            return config;
        }

    }
}
