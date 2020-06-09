package com.autumn.redis.ops;

import com.autumn.redis.AutumnRedisTemplate;
import org.springframework.dao.DataAccessException;
import org.springframework.data.redis.connection.RedisConnection;
import org.springframework.data.redis.core.RedisCallback;
import org.springframework.data.redis.core.TimeoutUtils;

import java.util.*;
import java.util.concurrent.TimeUnit;

/**
 * 二进制
 * <p>
 * </p>
 *
 * @description TODO
 * @author: 老码农
 * @create: 2020-04-29 15:04
 **/
public class ByteArrayOperations extends AbstractOperations {

    /**
     * 实例化
     *
     * @param redisTemplate
     */
    public ByteArrayOperations(AutumnRedisTemplate template) {
        super(template);
    }


    public void set(byte[] key, byte[] value) {
        this.getTemplate().execute(action -> {
            action.set(key, value);
            return value;
        }, true);
    }

    public void set(byte[] key, byte[] value, long timeout, TimeUnit unit) {
        this.getTemplate().execute(new RedisCallback<byte[]>() {
            @Override
            public byte[] doInRedis(RedisConnection connection) throws DataAccessException {
                potentiallyUsePsetEx(connection);
                return value;
            }

            private void potentiallyUsePsetEx(RedisConnection connection) {
                if (!TimeUnit.MILLISECONDS.equals(unit) || !failsafeInvokePsetEx(connection)) {
                    connection.setEx(key, TimeoutUtils.toSeconds(timeout, unit), value);
                }
            }

            private boolean failsafeInvokePsetEx(RedisConnection connection) {
                boolean failed = false;
                try {
                    connection.pSetEx(key, timeout, value);
                } catch (UnsupportedOperationException e) {
                    failed = true;
                }
                return !failed;
            }
        }, true);
    }

    public Boolean setIfAbsent(byte[] key, byte[] value) {
        return this.getTemplate().execute(connection -> connection.setNX(key, value), true);
    }

    public void multiSet(Map<? extends byte[], ? extends byte[]> map) {
        Map<byte[], byte[]> rawKeys = new LinkedHashMap<>(map.size());
        for (Map.Entry<? extends byte[], ? extends byte[]> entry : map.entrySet()) {
            rawKeys.put(entry.getKey(), entry.getValue());
        }
        this.getTemplate().execute(connection -> {
            connection.mSet(rawKeys);
            return null;
        }, true);
    }

    public Boolean multiSetIfAbsent(Map<? extends byte[], ? extends byte[]> map) {
        if (map.isEmpty()) {
            return true;
        }
        Map<byte[], byte[]> rawKeys = new LinkedHashMap<>(map.size());
        for (Map.Entry<? extends byte[], ? extends byte[]> entry : map.entrySet()) {
            rawKeys.put(entry.getKey(), entry.getValue());
        }
        return this.getTemplate().execute(connection -> connection.mSetNX(rawKeys), true);
    }

    public byte[] get(byte[] key) {
        if (key == null) {
            return null;
        }
        return this.getTemplate().execute(connection -> connection.get(key), true);
    }

    public byte[] getAndSet(byte[] key, byte[] value) {
        return this.getTemplate().execute(connection -> connection.getSet(key, value), true);
    }

    public List<byte[]> multiGet(Collection<byte[]> keys) {
        if (keys.isEmpty()) {
            return Collections.emptyList();
        }
        byte[][] rawKeys = new byte[keys.size()][];
        int counter = 0;
        for (byte[] hashKey : keys) {
            rawKeys[counter++] = hashKey;
        }
        return this.getTemplate().execute(connection -> connection.mGet(rawKeys), true);
    }

    public Long increment(byte[] key, long delta) {
        return this.getTemplate().execute(connection -> connection.incrBy(key, delta), true);
    }

    public Double increment(byte[] key, double delta) {
        return this.getTemplate().execute(connection -> connection.incrBy(key, delta), true);
    }

    public Integer append(byte[] key, byte[] value) {
        return this.getTemplate().execute(connection -> {
            Long result = connection.append(key, value);
            return (result != null) ? result.intValue() : null;
        }, true);
    }

    public String get(byte[] key, long start, long end) {
        byte[] rawReturn = this.getTemplate().execute(connection -> connection.getRange(key, start, end), true);
        return this.getTemplate().getStringSerializer().deserialize(rawReturn);
    }

    public void set(byte[] key, byte[] value, long offset) {
        this.getTemplate().execute(connection -> {
            connection.setRange(key, value, offset);
            return null;
        }, true);
    }

    public Long size(byte[] key) {
        return this.getTemplate().execute(connection -> connection.strLen(key), true);
    }

    public Boolean setBit(byte[] key, long offset, boolean value) {
        return this.getTemplate().execute(connection -> connection.setBit(key, offset, value), true);
    }

    public Boolean getBit(byte[] key, long offset) {
        return this.getTemplate().execute(connection -> connection.getBit(key, offset), true);
    }
}
