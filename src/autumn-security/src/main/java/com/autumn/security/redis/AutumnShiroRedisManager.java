package com.autumn.security.redis;

import com.autumn.util.tuple.TupleTwo;
import org.crazycake.shiro.IRedisManager;

/**
 * <p>
 * </p>
 *
 * @description TODO
 * @author: 老码农
 * @create: 2020-04-07 23:28
 **/
public interface AutumnShiroRedisManager extends IRedisManager {

    /**
     * set value
     *
     * @param key   key
     * @param value value
     * @return value
     */
    byte[] set(byte[] key, byte[] value);

    /**
     * 递增
     *
     * @param key
     * @return
     */
    long incr(String key);

    /**
     * 递增
     *
     * @param key
     * @param delta 步长
     * @return
     */
    long incrBy(String key, final long delta);

    /**
     * 递减
     *
     * @param key
     * @return
     */
    long decr(String key);

    /**
     * 递减
     *
     * @param key
     * @param delta 步长
     * @return
     */
    long decrBy(String key, long delta);


    /**
     * 条件递减
     *
     * @param key
     * @param delta    步长
     * @param minValue
     * @return
     */
    TupleTwo<Boolean, Long> decrByIf(String key, final long delta, final long minValue);

    /**
     * 匹配删除
     *
     * @param pattern
     * @return
     */
    long deleteByMatches(String pattern);
}
