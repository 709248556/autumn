package com.autumn.redis;

import com.autumn.redis.ops.AutumnValueOperations;
import com.autumn.redis.ops.ByteArrayOperations;
import com.autumn.redis.ops.LockOperations;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.RedisSerializer;
import org.springframework.data.redis.serializer.StringRedisSerializer;
import org.springframework.lang.Nullable;

import java.nio.charset.StandardCharsets;

/**
 * Redis 模板
 *
 * @author 老码农
 * <p>
 * 2017-12-12 21:13:19
 */
public class AutumnRedisTemplate extends RedisTemplate<String, Object> {

    /**
     *
     */
    public final static RedisSerializer<String> STRING_SERIALIZER = new StringRedisSerializer(StandardCharsets.UTF_8);

    /**
     *
     */
    public final static RedisSerializer<Object> VALUE_SERIALIZER = new ObjectRedisConverter();

    @Nullable
    private AutumnValueOperations customValueOps;

    @Nullable
    private ByteArrayOperations byteArrayOps;

    @Nullable
    private LockOperations lockOps;

    /**
     *
     */
    public AutumnRedisTemplate() {
        super();
        setKeySerializer(STRING_SERIALIZER);
        setValueSerializer(VALUE_SERIALIZER);
        setHashKeySerializer(STRING_SERIALIZER);
        setHashValueSerializer(VALUE_SERIALIZER);
    }

    /**
     * @param connectionFactory
     */
    public AutumnRedisTemplate(RedisConnectionFactory connectionFactory) {
        this();
        this.setConnectionFactory(connectionFactory);
    }

    /**
     * 获取自定义值
     *
     * @return
     */
    public AutumnValueOperations opsForCustomValue() {
        if (this.customValueOps == null) {
            this.customValueOps = new AutumnValueOperations(this);
        }
        return this.customValueOps;
    }

    /**
     * 获取数组
     *
     * @return
     */
    public ByteArrayOperations opsForByteArray() {
        if (this.byteArrayOps == null) {
            this.byteArrayOps = new ByteArrayOperations(this);
        }
        return this.byteArrayOps;
    }

    /**
     * 获取锁
     *
     * @return
     */
    public LockOperations opsForLock() {
        if (this.lockOps == null) {
            //必须加锁，确保生成的uuid是唯一的
            synchronized (this) {
                if (this.lockOps == null) {
                    this.lockOps = new LockOperations(this);
                }
            }
        }
        return this.lockOps;
    }
}
