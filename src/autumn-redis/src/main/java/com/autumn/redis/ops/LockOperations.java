package com.autumn.redis.ops;

import com.autumn.exception.UnableLockTimeoutException;
import com.autumn.redis.AutumnRedisTemplate;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.data.redis.connection.ReturnType;

import java.util.UUID;

/**
 * 锁操作
 * <p>
 * </p>
 *
 * @description TODO
 * @author: 老码农
 * @create: 2020-04-29 22:18
 **/
public class LockOperations extends AbstractOperations {

    /**
     * 日志
     */
    private static final Log LOG = LogFactory.getLog(LockOperations.class);

    /**
     * 锁脚本
     */
    public final static byte[] SCRIPT_LOCK_SCRIPT;

    /**
     * 解锁脚本
     */
    public final static byte[] SCRIPT_UN_LOCK_SCRIPT;

    /**
     * 锁前缀
     */
    private final String LOCK_PREFIX = "autumn:lock:";

    static {
        String script = "if redis.call('get', KEYS[1]) == ARGV[1] then " +
                "return redis.call('del', KEYS[1]); " +
                "else " +
                "return 0 " +
                "end;";
        SCRIPT_UN_LOCK_SCRIPT = AutumnRedisTemplate.STRING_SERIALIZER.serialize(script);
        script = "if redis.call('get', KEYS[1]) == ARGV[1] then " +
                "return 1; " +
                "else " +
                "local v = redis.call('setnx', KEYS[1], ARGV[1]); " +
                "if v == 1 and tonumber(ARGV[2]) > 0 then " +
                "redis.call('pexpire', KEYS[1], tonumber(ARGV[2])); " +
                "end; " +
                "return v; " +
                "end;";
        SCRIPT_LOCK_SCRIPT = AutumnRedisTemplate.STRING_SERIALIZER.serialize(script);
    }

    protected final String lockId;

    /**
     * 实例化
     *
     * @param template
     */
    public LockOperations(AutumnRedisTemplate template) {
        super(template);
        //确保不同进程的id不一样。
        this.lockId = UUID.randomUUID().toString().replace("-", "");
    }

    /**
     * 获取当前线程锁id
     *
     * @return
     */
    protected String getCurrentThreadLockId() {
        return this.lockId + "_" + Thread.currentThread().getId();
    }

    /**
     * 即时获取锁
     *
     * @param key     键
     * @param timeout 锁过期时间(毫秒)
     * @return 获取成功则返回 true,否则 false
     */
    public boolean tryAcquireLock(String key, long timeout) {
        final byte[] rawKey = this.serializeKey(LOCK_PREFIX + key);
        final byte[] rawValue = this.serializeKey(this.getCurrentThreadLockId());
        final byte[] rawTimeout = this.serializeKey(Long.toString(timeout));
        return this.getTemplate().execute(connection -> {
            Long result = connection.eval(SCRIPT_LOCK_SCRIPT, ReturnType.INTEGER,
                    1, rawKey, rawValue, rawTimeout);
            return result == 1;
        }, true);
    }

    /**
     * 无限等待获取锁
     *
     * @param key 键
     * @return
     */
    public boolean tryLock(String key) {
        return this.tryLock(key, -1L);
    }

    /**
     * 获取锁
     *
     * @param key     键
     * @param timeout 超时(毫秒)(小于或等于0表示无限)
     * @return
     */
    public boolean tryLock(String key, long timeout) {
        long start = System.currentTimeMillis();
        long diff = 0L;
        while (!this.tryAcquireLock(key, timeout)) {
            if (timeout > 0L) {
                diff += (System.currentTimeMillis() - start);
                if (diff >= timeout) {
                    return false;
                }
            }
        }
        return true;
    }

    /**
     * 加锁(无限等待)
     *
     * @param key 键
     */
    public boolean lock(String key) {
        return this.lock(key, -1L);
    }

    /**
     * 加锁
     *
     * @param key     键
     * @param timeout 超时(毫秒)(小于或等于0表示无限)
     */
    public boolean lock(String key, long timeout) {
        if (!this.tryLock(key, timeout)) {
            throw new UnableLockTimeoutException(String.format("锁 %s 超过 %s 毫秒无法获取而超时 。", key, timeout));
        }
        return true;
    }

    /**
     * 尝试解锁
     *
     * @param key 键
     * @return 解锁成功则返回 true，反之为 false
     */
    public boolean tryUnLock(String key) {
        final byte[] rawKey = this.serializeKey(LOCK_PREFIX + key);
        final byte[] rawValue = this.serializeKey(this.getCurrentThreadLockId());
        return this.getTemplate().execute(connection -> {
            Long result = connection.eval(SCRIPT_UN_LOCK_SCRIPT, ReturnType.INTEGER, 1, rawKey, rawValue);
            return result != 0L;
        }, true);
    }

    /**
     * 解锁(出错时，无限等待)
     *
     * @param key 键
     * @return
     */
    public boolean unLock(String key) {
        return this.unLock(key, -1L);
    }

    /**
     * 解锁
     *
     * @param key     键
     * @param timeout 发生异常时，指定的超时时间内重试(小于或等于0表示无限)
     * @return 解锁成功则返回 true，反之为 false
     */
    public boolean unLock(String key, long timeout) {
        if (timeout <= 0L) {
            return this.tryUnLock(key);
        }
        long start = System.currentTimeMillis();
        long diff = 0L;
        while (true) {
            try {
                return this.tryUnLock(key);
            } catch (Exception err) {
                LOG.error("解锁出错:" + err.getMessage());
            }
            diff += (System.currentTimeMillis() - start);
            if (diff >= timeout) {
                throw new UnableLockTimeoutException(String.format("锁 %s 超过 %s 毫秒无法解锁而超时 。", key, timeout));
            }
        }
    }
}
