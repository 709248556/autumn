package com.autumn.cloud.configure;

import com.autumn.cloud.lock.DistributedLock;
import com.autumn.cloud.lock.impl.DistributedLockImpl;
import org.redisson.api.RedissonClient;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.autoconfigure.data.redis.RedisProperties;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * 锁 Bean 定义
 *
 * @author 老码农
 * <p>
 * 2017-11-27 18:05:40
 */
@Configuration
@EnableConfigurationProperties(RedisProperties.class)
public class AutumnLockConfiguration {

    /**
     * 客户端
     *
     * @return
     */
    @Bean
    @ConditionalOnMissingBean(RedissonClient.class)
    public RedissonClient redissonClient(RedisProperties properties) {
        RedissonClientBuilder builder = new RedissonClientBuilder(properties);
        return builder.createRedissonClient();
    }

    /**
     * 分布式锁
     *
     * @param redissonClient 客户端
     * @return
     */
    @Bean
    @ConditionalOnMissingBean(DistributedLock.class)
    public DistributedLock distributedLocker(RedissonClient redissonClient) {
        return new DistributedLockImpl(redissonClient);
    }
}
