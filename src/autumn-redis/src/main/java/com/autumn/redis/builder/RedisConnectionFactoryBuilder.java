package com.autumn.redis.builder;

import org.springframework.boot.autoconfigure.data.redis.RedisProperties;
import org.springframework.data.redis.connection.*;
import org.springframework.util.Assert;
import org.springframework.util.StringUtils;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.List;

/**
 * Redis 连接工厂生成器抽象
 * <p>
 * </p>
 *
 * @description TODO
 * @author: 老码农
 * @create: 2020-04-29 12:23
 **/
abstract class RedisConnectionFactoryBuilder {

    private final RedisProperties properties;

    /**
     * 实例化
     *
     * @param properties
     */
    public RedisConnectionFactoryBuilder(RedisProperties properties) {
        RedisProperties.Jedis jedis = properties.getJedis();
        if (jedis.getPool() == null) {
            jedis.setPool(this.createDefaultPool());
        }
        RedisProperties.Lettuce lettuce = properties.getLettuce();
        if (lettuce.getPool() == null) {
            lettuce.setPool(this.createDefaultPool());
        }
        this.properties = properties;
    }

    /**
     * 创建默认连接池
     *
     * @return
     */
    protected RedisProperties.Pool createDefaultPool() {
        return new RedisProperties.Pool();
    }

    /**
     * 获取属性
     *
     * @return
     */
    public RedisProperties getProperties() {
        return this.properties;
    }

    /**
     * 创建连接工厂
     *
     * @return
     */
    public abstract RedisConnectionFactory createRedisConnectionFactory();

    /**
     * 单机配置模式
     *
     * @return
     */
    protected final RedisStandaloneConfiguration getStandaloneConfig() {
        RedisStandaloneConfiguration config = new RedisStandaloneConfiguration();
        if (com.autumn.util.StringUtils.isNotNullOrBlank(this.properties.getUrl())) {
            ConnectionInfo connectionInfo = parseUrl(this.properties.getUrl());
            config.setHostName(connectionInfo.getHostName());
            config.setPort(connectionInfo.getPort());
            if (com.autumn.util.StringUtils.isNotNullOrBlank(connectionInfo.getPassword())) {
                config.setPassword(RedisPassword.of(connectionInfo.getPassword()));
            }
        } else {
            config.setHostName(this.properties.getHost());
            config.setPort(this.properties.getPort());
            if (com.autumn.util.StringUtils.isNotNullOrBlank(this.properties.getPassword())) {
                config.setPassword(RedisPassword.of(this.properties.getPassword()));
            }
        }
        config.setDatabase(this.properties.getDatabase());
        return config;
    }

    /**
     * 获取哨兵模式配置
     *
     * @return
     */
    protected final RedisSentinelConfiguration getSentinelConfig() {
        RedisProperties.Sentinel sentinelProperties = this.properties.getSentinel();
        if (sentinelProperties == null) {
            return null;
        }
        if (com.autumn.util.StringUtils.isNullOrBlank(sentinelProperties.getMaster())) {
            return null;
        }
        RedisSentinelConfiguration config = new RedisSentinelConfiguration();
        config.master(sentinelProperties.getMaster());
        config.setSentinels(createSentinels(sentinelProperties));
        if (com.autumn.util.StringUtils.isNotNullOrBlank(this.properties.getPassword())) {
            config.setPassword(RedisPassword.of(this.properties.getPassword()));
        }
        config.setDatabase(this.properties.getDatabase());
        return config;
    }

    /**
     * 获取集群模式配置
     *
     * @return
     */
    protected final RedisClusterConfiguration getClusterConfiguration() {
        RedisProperties.Cluster clusterProperties = this.properties.getCluster();
        if (clusterProperties == null) {
            return null;
        }
        if (clusterProperties.getNodes() == null || clusterProperties.getNodes().size() == 0) {
            return null;
        }
        RedisClusterConfiguration config = new RedisClusterConfiguration(
                clusterProperties.getNodes());
        if (clusterProperties.getMaxRedirects() != null) {
            config.setMaxRedirects(clusterProperties.getMaxRedirects());
        }
        if (com.autumn.util.StringUtils.isNotNullOrBlank(this.properties.getPassword())) {
            config.setPassword(RedisPassword.of(this.properties.getPassword()));
        }
        return config;
    }

    private List<RedisNode> createSentinels(RedisProperties.Sentinel sentinel) {
        List<RedisNode> nodes = new ArrayList<>();
        for (String node : sentinel.getNodes()) {
            try {
                String[] parts = StringUtils.split(node, ":");
                Assert.state(parts.length == 2, "Must be defined as 'host:port'");
                nodes.add(new RedisNode(parts[0], Integer.valueOf(parts[1])));
            } catch (RuntimeException ex) {
                throw new IllegalStateException(
                        "Invalid redis sentinel " + "property '" + node + "'", ex);
            }
        }
        return nodes;
    }

    protected ConnectionInfo parseUrl(String url) {
        try {
            URI uri = new URI(url);
            boolean useSsl = (url.startsWith("rediss://"));
            String password = null;
            if (uri.getUserInfo() != null) {
                password = uri.getUserInfo();
                int index = password.indexOf(':');
                if (index >= 0) {
                    password = password.substring(index + 1);
                }
            }
            return new ConnectionInfo(uri, useSsl, password);
        } catch (URISyntaxException ex) {
            throw new IllegalArgumentException("Malformed url '" + url + "'", ex);
        }
    }

    protected static class ConnectionInfo {

        private final URI uri;

        private final boolean useSsl;

        private final String password;

        public ConnectionInfo(URI uri, boolean useSsl, String password) {
            this.uri = uri;
            this.useSsl = useSsl;
            this.password = password;
        }

        public boolean isUseSsl() {
            return this.useSsl;
        }

        public String getHostName() {
            return this.uri.getHost();
        }

        public int getPort() {
            return this.uri.getPort();
        }

        public String getPassword() {
            return this.password;
        }

    }

}
