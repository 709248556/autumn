package com.autumn.cloud.lock.impl;

import com.autumn.cloud.lock.DistributedLock;
import org.redisson.api.RLock;
import org.redisson.api.RedissonClient;

import java.util.concurrent.TimeUnit;
import java.util.function.Consumer;
import java.util.function.Supplier;

/**
 * 分布式锁实现
 *
 * @author 老码农
 * <p>
 * 2017-11-23 12:08:56
 */
public class DistributedLockImpl extends AbstractResourceLock implements DistributedLock {

    private final RedissonClient redissonClient;

    public DistributedLockImpl(RedissonClient redissonClient) {
        this.redissonClient = redissonClient;
    }

    @Override
    public void lock(String resourceName) {
        RLock lock = redissonClient.getLock(resourceName);
        lock.lock();
    }

    @Override
    public void lock(String resourceName, int timeout) throws Exception {
        RLock lock = redissonClient.getLock(resourceName);
        boolean seccess = lock.tryLock(timeout, TimeUnit.MILLISECONDS);
        if (!seccess) {
            throw throwUnableLockTimeoutException(resourceName, timeout);
        }
    }

    @Override
    public void lock(String resourceName, Consumer<String> lockUnitOfWorker) {
        RLock lock = redissonClient.getLock(resourceName);
        try {
            lock.lock();
            if (lockUnitOfWorker != null) {
                lockUnitOfWorker.accept(resourceName);
            }
        } finally {
            lock.unlock();
        }
    }

    @Override
    public void lock(String resourceName, Consumer<String> lockUnitOfWorker, int timeout) throws Exception {
        RLock lock = redissonClient.getLock(resourceName);
        boolean seccess = lock.tryLock(timeout, TimeUnit.MILLISECONDS);
        if (seccess) {
            try {
                if (lockUnitOfWorker != null) {
                    lockUnitOfWorker.accept(resourceName);
                }
            } finally {
                lock.unlock();
            }
        } else {
            throw throwUnableLockTimeoutException(resourceName, timeout);
        }
    }

    @Override
    public <TResult> TResult lock(String resourceName, Supplier<TResult> lockUnitOfWorker) {
        RLock lock = redissonClient.getLock(resourceName);
        try {
            lock.lock();
            if (lockUnitOfWorker != null) {
                return lockUnitOfWorker.get();
            }
            return null;
        } finally {
            lock.unlock();
        }
    }

    @Override
    public <TResult> TResult lock(String resourceName, Supplier<TResult> lockUnitOfWorker, int timeout)
            throws Exception {
        RLock lock = redissonClient.getLock(resourceName);
        boolean seccess = lock.tryLock(timeout, TimeUnit.MILLISECONDS);
        if (seccess) {
            try {
                if (lockUnitOfWorker != null) {
                    return lockUnitOfWorker.get();
                }
                return null;
            } finally {
                lock.unlock();
            }
        } else {
            throw throwUnableLockTimeoutException(resourceName, timeout);
        }
    }

    @Override
    public void unlock(String resourceName) {
        RLock lock = redissonClient.getLock(resourceName);
        lock.unlock();
    }
}
