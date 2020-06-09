package com.autumn.security.redis.impl;

import com.autumn.redis.AutumnRedisTemplate;
import com.autumn.security.redis.AutumnShiroRedisManager;
import com.autumn.util.tuple.TupleTwo;

import java.util.Set;
import java.util.concurrent.TimeUnit;

/**
 * <p>
 * </p>
 *
 * @description TODO
 * @author: 老码农
 * @create: 2020-04-07 23:30
 **/
public class AutumnShiroRedisManagerImpl implements AutumnShiroRedisManager {

    private final AutumnRedisTemplate redisTemplate;

    /**
     * 实例化
     *
     * @param redisTemplate 模板
     */
    public AutumnShiroRedisManagerImpl(AutumnRedisTemplate redisTemplate) {
        this.redisTemplate = redisTemplate;
    }

    /**
     * 字符序列化
     *
     * @param value
     * @return
     */
    private byte[] StringSerialize(String value) {
        return this.redisTemplate.getStringSerializer().serialize(value);
    }

    /**
     * 字符反序列化
     *
     * @param value
     * @return
     */
    private String StringDeserialize(byte[] bytes) {
        return this.redisTemplate.getStringSerializer().deserialize(bytes);
    }

    @Override
    public byte[] set(byte[] key, byte[] value) {
        this.redisTemplate.opsForByteArray().set(key, value);
        return value;
    }

    @Override
    public long incr(String key) {
        return this.redisTemplate.opsForCustomValue().incr(key);
    }

    @Override
    public long incrBy(String key, long delta) {
        return this.redisTemplate.opsForCustomValue().incrBy(key, delta);
    }

    @Override
    public long decr(String key) {
        return this.redisTemplate.opsForCustomValue().decr(key);
    }

    @Override
    public long decrBy(String key, long delta) {
        return this.redisTemplate.opsForCustomValue().decrBy(key, delta);
    }

    @Override
    public TupleTwo<Boolean, Long> decrByIf(String key, long delta, long minValue) {
        return this.redisTemplate.opsForCustomValue().decrByIf(key, delta, minValue);
    }

    @Override
    public long deleteByMatches(String pattern) {
        return this.redisTemplate.opsForCustomValue().deleteByMatches(pattern);
    }

    @Override
    public byte[] get(byte[] key) {
        return this.redisTemplate.opsForByteArray().get(key);
    }

    @Override
    public byte[] set(byte[] key, byte[] value, int expire) {
        this.redisTemplate.opsForByteArray().set(key, value, expire, TimeUnit.SECONDS);
        return value;
    }

    @Override
    public void del(byte[] key) {
        this.redisTemplate.execute(connection -> connection.del(key), true);
    }

    @Override
    public Long dbSize(byte[] pattern) {
        long dbSize = 0L;
        Set<byte[]> keys = this.keys(pattern);
        if (keys != null) {
            dbSize = keys.size();
        }
        return dbSize;
    }

    @Override
    public Set<byte[]> keys(byte[] pattern) {
        return this.redisTemplate.execute(connection -> connection.keys(pattern),
                true);
    }
}
