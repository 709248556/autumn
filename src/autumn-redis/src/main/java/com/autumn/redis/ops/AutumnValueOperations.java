package com.autumn.redis.ops;

import com.autumn.redis.AutumnRedisTemplate;
import com.autumn.util.function.FunctionOneResult;
import com.autumn.util.tuple.TupleTwo;
import org.springframework.dao.DataAccessException;
import org.springframework.data.redis.connection.RedisConnection;
import org.springframework.data.redis.connection.ReturnType;
import org.springframework.data.redis.core.RedisCallback;
import org.springframework.data.redis.core.TimeoutUtils;

import java.util.List;
import java.util.concurrent.TimeUnit;

/**
 * 值操作
 *
 * @param <T>
 * @author 老码农
 * <p>
 * Description
 * </p>
 * @date 2017-12-13 01:13:17
 */
public class AutumnValueOperations extends AbstractOperations {

    /**
     * 原子递减到最小值脚本
     */
    public final static byte[] SCRIPT_DECRBY_MIN_VALUE_BYTE;
    /**
     * 读取后删除的脚本
     */
    public final static byte[] SCRIPT_READ_DELETE_BYTE;
    /**
     * 通配符匹配删除
     */
    public final static byte[] SCRIPT_MATCHES_DELETE_BYTE;

    static {
        String script = "local v = redis.call('get',KEYS[1]); if type(v) == 'string' and v > ARGV[1] then local r = redis.call('decrby',KEYS[1],ARGV[2]);return {1,r} else return {0,v} end;";
        SCRIPT_DECRBY_MIN_VALUE_BYTE = AutumnRedisTemplate.STRING_SERIALIZER.serialize(script);
        script = "local v = redis.call('get',KEYS[1]); redis.call('del', KEYS[1]);return v";
        SCRIPT_READ_DELETE_BYTE = AutumnRedisTemplate.STRING_SERIALIZER.serialize(script);
        script = "local keys = redis.call('keys', ARGV[1]) for i=1,#keys,5000 do redis.call('del', unpack(keys, i, math.min(i+4999, #keys))) end return #keys";
        SCRIPT_MATCHES_DELETE_BYTE = AutumnRedisTemplate.STRING_SERIALIZER.serialize(script);
    }

    /**
     * @param template
     */
    public AutumnValueOperations(AutumnRedisTemplate template) {
        super(template);
    }

    /**
     * 设置
     *
     * @param key   键值
     * @param value
     * @return
     */
    public <TValue> void set(String key, TValue value) {
        final byte[] rawKey = this.serializeKey(key);
        final byte[] rawValue = this.serializeValue(value);
        this.getTemplate().execute(connection -> {
            connection.set(rawKey, rawValue);
            return value;
        }, true);
    }

    /**
     * 设置
     *
     * @param key     键
     * @param value   值
     * @param timeout 超时时间
     * @param unit    时间单位
     */
    public <TValue> void set(String key, TValue value, long timeout, TimeUnit unit) {
        final byte[] rawKey = this.serializeKey(key);
        final byte[] rawValue = this.serializeValue(value);
        this.getTemplate().execute(new RedisCallback<TValue>() {
            @Override
            public TValue doInRedis(RedisConnection connection) throws DataAccessException {
                potentiallyUsePsetEx(connection);
                return value;
            }

            private void potentiallyUsePsetEx(RedisConnection connection) {
                if (!TimeUnit.MILLISECONDS.equals(unit) || !failsafeInvokePsetEx(connection)) {
                    connection.setEx(rawKey, TimeoutUtils.toSeconds(timeout, unit), rawValue);
                }
            }

            private boolean failsafeInvokePsetEx(RedisConnection connection) {
                boolean failed = false;
                try {
                    connection.pSetEx(rawKey, timeout, rawValue);
                } catch (UnsupportedOperationException e) {
                    // in case the connection does not support pSetEx return
                    // false to allow fallback to other operation.
                    failed = true;
                }
                return !failed;
            }

        }, true);
    }

    /**
     * 获取值
     *
     * @param key
     * @param <TValue>
     * @return
     */
    public <TValue> TValue get(String key) {
        return this.get(key, (o) -> {
            if (o == null) {
                return null;
            }
            return (TValue) o;
        });
    }

    /**
     * 获取
     *
     * @param key  键
     * @param func 函数转换
     * @return
     */
    public <TValue> TValue get(String key, FunctionOneResult<Object, TValue> func) {
        final byte[] rawKey = this.serializeKey(key);
        return this.getTemplate().execute(connection -> {
            Object value = this.deserializeValue(connection.get(rawKey));
            return func.apply(value);
        }, true);
    }

    /**
     * @param key
     * @param <TValue>
     * @return
     */
    public <TValue> TValue getAndDelete(String key) {
        return this.getAndDelete(key, (o) -> {
            if (o == null) {
                return null;
            }
            return (TValue) o;
        });
    }

    /**
     * 获取后删除
     *
     * @param key  键
     * @param func 函数转换
     * @return
     */
    public <TValue> TValue getAndDelete(String key, FunctionOneResult<Object, TValue> func) {
        final byte[] rawKey = this.serializeKey(key);
        return this.getTemplate().execute(connection -> {
            byte[] result = connection.eval(SCRIPT_READ_DELETE_BYTE, ReturnType.VALUE, 1, rawKey);
            Object value = this.serializeValue(result);
            return func.apply(value);
        }, true);
    }

    /**
     * 递增(++)
     *
     * @param key 键
     * @return
     */
    public Long incr(String key) {
        final byte[] rawKey = this.serializeKey(key);
        return this.getTemplate().execute(connection -> connection.incr(rawKey), true);
    }

    /**
     * 递增(+=value)
     *
     * @param key 键
     * @return
     */
    public Long incrBy(String key, long delta) {
        final byte[] rawKey = this.serializeKey(key);
        return this.getTemplate().execute(connection -> connection.incrBy(rawKey, delta), true);
    }

    /**
     * 递减(--)
     *
     * @param key 键
     * @return
     */
    public Long decr(String key) {
        final byte[] rawKey = this.serializeKey(key);
        return this.getTemplate().execute(connection -> connection.decr(rawKey), true);
    }

    /**
     * 递减(-=value)
     *
     * @param key 键
     * @return
     */
    public Long decrBy(String key, long delta) {
        final byte[] rawKey = this.serializeKey(key);
        return this.getTemplate().execute(connection -> connection.incrBy(rawKey, delta), true);
    }

    private final static int DECR_BY_IF_DATA_SIZE = 2;

    /**
     * 条件递减
     *
     * @param key      键
     * @param delta    步长
     * @param minValue 最小值
     * @return
     */
    public TupleTwo<Boolean, Long> decrByIf(String key, long delta, long minValue) {
        final byte[] rawKey = this.serializeKey(key);
        final byte[] rawdelta = this.serializeKey(Long.toString(delta));
        final byte[] rawMinValue = this.serializeKey(Long.toString(minValue));
        return this.getTemplate().execute(connection -> {
            List<Object> resultBytes = connection.eval(SCRIPT_DECRBY_MIN_VALUE_BYTE, ReturnType.MULTI, 1, rawKey,
                    rawMinValue, rawdelta);
            if (resultBytes != null && resultBytes.size() == DECR_BY_IF_DATA_SIZE) {
                boolean isSuccess = (long) resultBytes.get(0) > 0L;
                Object result = resultBytes.get(1);
                if (result == null) {
                    return new TupleTwo<>(isSuccess, 0L);
                }
                if (result instanceof Long) {
                    return new TupleTwo<>(isSuccess, (Long) result);
                }
                String str = this.deserializeKey((byte[]) result);
                if (str == null) {
                    return new TupleTwo<>(isSuccess, 0L);
                }
                return new TupleTwo<>(isSuccess, Long.parseLong(str));
            }
            return new TupleTwo<>(false, 0L);
        }, true);
    }

    /**
     * 删除匹配
     *
     * @param keyExpression 键表达式
     *                      <p>
     *                      a* 表示a开头的键
     *                      </p>
     *                      <p>
     *                      *a 表示对a结束的键
     *                      </p>
     * @return 返回成功删除的键数量
     */
    public long deleteByMatches(String keyExpression) {
        final byte[] rawKey = this.serializeKey(keyExpression);
        return this.getTemplate().execute(connection -> {
            Long value = connection.eval(SCRIPT_MATCHES_DELETE_BYTE, ReturnType.INTEGER, 0, rawKey);
            return value;
        }, true);
    }
}