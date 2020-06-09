package com.autumn.redis.ops;

import com.autumn.redis.AutumnRedisTemplate;
import org.springframework.data.redis.serializer.RedisSerializer;

/**
 * 操作抽象
 *
 * @author 老码农
 * <p>
 * Description
 * </p>
 * @date 2017-12-13 06:08:50
 */
abstract class AbstractOperations {


    private final AutumnRedisTemplate template;

    /**
     * 实例化
     *
     * @param template
     */
    public AbstractOperations(AutumnRedisTemplate template) {
        this.template = template;
    }

    /**
     * 获取模板
     *
     * @return
     */
    public final AutumnRedisTemplate getTemplate() {
        return this.template;
    }

    /**
     * 序列化键
     *
     * @param key 键
     * @return
     */
    public byte[] serializeKey(String key) {
        RedisSerializer<String> keySerializer = (RedisSerializer<String>) this.template.getKeySerializer();
        return keySerializer.serialize(key);
    }

    /**
     * 反序列化键
     *
     * @param rawKey 二进制键
     * @return
     */
    public String deserializeKey(byte[] rawKey) {
        if (rawKey == null || rawKey.length == 0) {
            return null;
        }
        RedisSerializer<String> keySerializer = (RedisSerializer<String>) this.template.getKeySerializer();
        return keySerializer.deserialize(rawKey);
    }

    /**
     * 序列化值
     *
     * @param value 值
     * @return
     */
    public byte[] serializeValue(Object value) {
        RedisSerializer<Object> valueSerializer = (RedisSerializer<Object>) this.template.getValueSerializer();
        return valueSerializer.serialize(value);
    }

    /**
     * 反序列化值
     *
     * @param rawValue 二进制值
     * @return
     */
    public Object deserializeValue(byte[] rawValue) {
        if (rawValue == null || rawValue.length == 0) {
            return null;
        }
        RedisSerializer<Object> valueSerializer = (RedisSerializer<Object>) this.template.getValueSerializer();
        return valueSerializer.deserialize(rawValue);
    }
}