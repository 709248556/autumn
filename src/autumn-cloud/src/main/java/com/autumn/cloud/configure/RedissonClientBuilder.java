package com.autumn.cloud.configure;

import com.autumn.util.StringUtils;
import org.redisson.Redisson;
import org.redisson.api.RedissonClient;
import org.redisson.config.*;
import org.springframework.boot.autoconfigure.data.redis.RedisProperties;

import java.util.ArrayList;
import java.util.List;

/**
 * 配置生成器
 * <p>
 * </p>
 *
 * @description TODO
 * @author: 老码农
 * @create: 2020-04-30 05:45
 **/
public class RedissonClientBuilder {

    private static final String REDIS_ADDRESS_PREFIX = "redis://";
    private static final int MS_TIMEOUT = 1000;

    private final RedisProperties properties;

    /**
     * 实例化
     *
     * @param properties
     */
    public RedissonClientBuilder(RedisProperties properties) {
        this.properties = properties;
    }

    /**
     * 创建客户端
     *
     * @return
     */
    public RedissonClient createRedissonClient() {
        Config config = this.createConfig();
        return Redisson.create(config);
    }

    /**
     * 创建配置
     *
     * @return
     */
    public Config createConfig() {
        Config config = new Config();
        if (this.isCluster()) {
            this.builderClusterServersConfig(config);
        } else if (this.isSentinel()) {
            this.builderSentinelServersConfig(config);
        } else {
            this.builderSingleServerConfig(config);
        }
        return config;
    }

    /**
     * 是否为集群
     *
     * @return
     */
    private boolean isCluster() {
        RedisProperties.Cluster clusterProperties = this.properties.getCluster();
        if (clusterProperties == null) {
            return false;
        }
        if (clusterProperties.getNodes() == null || clusterProperties.getNodes().size() == 0) {
            return false;
        }
        return true;
    }

    /**
     * 是否为哨兵模式
     *
     * @return
     */
    private boolean isSentinel() {
        RedisProperties.Sentinel sentinelProperties = this.properties.getSentinel();
        if (sentinelProperties == null) {
            return false;
        }
        if (com.autumn.util.StringUtils.isNullOrBlank(sentinelProperties.getMaster())) {
            return false;
        }
        return true;
    }

    /**
     * 生成集群配置
     *
     * @param config
     * @return
     */
    private ClusterServersConfig builderClusterServersConfig(Config config) {
        ClusterServersConfig clusterConfig = config.useClusterServers()
                .addNodeAddress(getRedisAddress(properties.getCluster().getNodes()));
        this.setMasterSlave(clusterConfig);
        return clusterConfig;
    }

    /**
     * 生成哨兵模式配置
     *
     * @param config
     * @return
     */
    private SentinelServersConfig builderSentinelServersConfig(Config config) {
        SentinelServersConfig sentinelConfig = config.useSentinelServers()
                .addSentinelAddress(getRedisAddress(properties.getSentinel().getNodes()))
                .setMasterName(properties.getSentinel().getMaster()).setDatabase(properties.getDatabase());
        this.setMasterSlave(sentinelConfig);
        return sentinelConfig;
    }

    /**
     * 设置主从
     *
     * @param baseConfig 基配配置
     */
    private void setMasterSlave(BaseMasterSlaveServersConfig baseConfig) {
        if (properties.getTimeout() != null) {
            int timeout = (int) properties.getTimeout().getSeconds() * MS_TIMEOUT;
            baseConfig.setTimeout(timeout);
        }
        if (properties.getJedis() != null && properties.getJedis().getPool() != null) {
            RedisProperties.Pool pool = properties.getJedis().getPool();
            baseConfig.setMasterConnectionPoolSize(pool.getMaxActive())
                    .setSlaveConnectionPoolSize(pool.getMaxActive())
                    .setMasterConnectionMinimumIdleSize(pool.getMinIdle())
                    .setSlaveConnectionMinimumIdleSize(pool.getMinIdle());

        }
        if (!StringUtils.isNullOrBlank(properties.getPassword())) {
            baseConfig.setPassword(properties.getPassword());
        }
    }

    /**
     * 生成单机模式
     *
     * @param config 配置
     * @return
     */
    private SingleServerConfig builderSingleServerConfig(Config config) {
        SingleServerConfig singleConfig = config.useSingleServer()
                .setAddress(REDIS_ADDRESS_PREFIX + properties.getHost() + ":" + properties.getPort()).setDatabase(properties.getDatabase());
        if (properties.getTimeout() != null) {
            int timeout = (int) properties.getTimeout().getSeconds() * MS_TIMEOUT;
            singleConfig.setTimeout(timeout);
        }
        if (properties.getJedis() != null && properties.getJedis().getPool() != null) {
            RedisProperties.Pool pool = properties.getJedis().getPool();
            singleConfig.setConnectionPoolSize(pool.getMaxActive())
                    .setConnectionMinimumIdleSize(pool.getMinIdle());
        }
        if (!StringUtils.isNullOrBlank(properties.getPassword())) {
            singleConfig.setPassword(properties.getPassword());
        }
        return singleConfig;
    }

    /**
     * @param nodes
     * @return
     */
    private String[] getRedisAddress(List<String> nodes) {
        List<String> items = new ArrayList<>(nodes.size());
        for (String node : nodes) {
            if (!StringUtils.isNullOrBlank(node)) {
                items.add(REDIS_ADDRESS_PREFIX + node.trim());
            }
        }
        return items.toArray(new String[0]);
    }

}
