package com.autumn.redis.configurre;

import com.autumn.redis.AutumnRedisTemplate;
import com.autumn.redis.builder.JedisConnectionFactoryBuilder;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.autoconfigure.data.redis.RedisProperties;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.StringRedisTemplate;

/**
 * 启用 AutumnRedis 配置
 *
 * @author 老码农
 * <p>
 * 2018-01-14 15:09:05
 */
@Configuration
@EnableConfigurationProperties({RedisProperties.class})
public class AutumnRedisConfiguration {

    public final static String REDIS_TEMPLATE_BEAN_NAME = "redisTemplate";

    /**
     * 连接工厂
     *
     * @param redisProperties 属性
     * @return
     */
    @Bean
    @Primary
    public RedisConnectionFactory redisConnectionFactory(RedisProperties redisProperties) {
        JedisConnectionFactoryBuilder builder = new JedisConnectionFactoryBuilder(redisProperties);
        return builder.createRedisConnectionFactory();
    }

    /**
     * @param redisConnectionFactory
     * @return
     */
    @Bean(REDIS_TEMPLATE_BEAN_NAME)
    @Primary
    @ConditionalOnMissingBean(name = REDIS_TEMPLATE_BEAN_NAME)
    public RedisTemplate<String, Object> redisTemplate(RedisConnectionFactory redisConnectionFactory) {
        //Jackson2JsonRedisSerializer JdkSerializationRedisSerializer RedisAutoConfiguration
        RedisTemplate<String, Object> redisTemplate = new RedisTemplate<>();
        redisTemplate.setConnectionFactory(redisConnectionFactory);
        redisTemplate.setKeySerializer(AutumnRedisTemplate.STRING_SERIALIZER);
        redisTemplate.setValueSerializer(AutumnRedisTemplate.VALUE_SERIALIZER);
        redisTemplate.setHashKeySerializer(AutumnRedisTemplate.STRING_SERIALIZER);
        redisTemplate.setHashValueSerializer(AutumnRedisTemplate.VALUE_SERIALIZER);
        return redisTemplate;
    }

    /**
     * @param redisConnectionFactory
     * @return
     */
    @Bean
    @Primary
    @ConditionalOnMissingBean(StringRedisTemplate.class)
    public StringRedisTemplate stringRedisTemplate(RedisConnectionFactory redisConnectionFactory) {
        StringRedisTemplate template = new StringRedisTemplate();
        template.setConnectionFactory(redisConnectionFactory);
        return template;
    }

    /**
     * @param redisConnectionFactory
     * @return
     */
    @Bean
    @Primary
    @ConditionalOnMissingBean(AutumnRedisTemplate.class)
    public AutumnRedisTemplate autumnRedisTemplate(RedisConnectionFactory redisConnectionFactory) {
        AutumnRedisTemplate template = new AutumnRedisTemplate();
        template.setConnectionFactory(redisConnectionFactory);
        return template;
    }
}
