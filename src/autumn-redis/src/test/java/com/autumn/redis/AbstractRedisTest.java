package com.autumn.redis;

import com.autumn.redis.builder.JedisConnectionFactoryBuilder;
import org.springframework.boot.autoconfigure.data.redis.RedisProperties;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.connection.jedis.JedisConnectionFactory;

/**
 * ???????
 * <p>
 * </p>
 *
 * @description TODO
 * @author: ?????
 * @create: 2020-04-30 15:56
 **/
public abstract class AbstractRedisTest {

    protected final AutumnRedisTemplate template;

    public AbstractRedisTest() {
        this.template = this.createRedisTemplate();
    }

    /**
     * 创建属性
     *
     * @return
     */
    protected RedisProperties createProperties() {
        RedisProperties properties = new RedisProperties();
        properties.setHost("127.0.0.1");
        properties.getJedis().setPool(new RedisProperties.Pool());
        properties.getJedis().getPool().setMaxActive(50);
        properties.getJedis().getPool().setMaxIdle(20);
        return properties;
    }

    /**
     * 创建连接工厂
     *
     * @return
     */
    protected RedisConnectionFactory createRedisConnectionFactory() {
        JedisConnectionFactoryBuilder builder = new JedisConnectionFactoryBuilder(this.createProperties());
        JedisConnectionFactory factory = (JedisConnectionFactory) builder.createRedisConnectionFactory();
        factory.afterPropertiesSet();
        return factory;
    }

    /**
     * 创建 RedisTemplate
     *
     * @return
     */
    protected AutumnRedisTemplate createRedisTemplate() {
        AutumnRedisTemplate template = new AutumnRedisTemplate(this.createRedisConnectionFactory());
        template.afterPropertiesSet();
        return template;
    }

}
