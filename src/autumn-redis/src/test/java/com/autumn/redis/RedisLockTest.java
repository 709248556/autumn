package com.autumn.redis;

import org.junit.Test;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.function.Consumer;

/**
 * 锁测试
 * <p>
 * </p>
 *
 * @description TODO
 * @author: 老码农
 * @create: 2020-04-30 15:55
 **/
public class RedisLockTest extends AbstractRedisTest {

    private int count = 0;

    private void exec(String name, Consumer<Set<Integer>> consumer) throws InterruptedException {
        List<Thread> threads = new ArrayList<>(50);
        Set<Integer> keys = new HashSet<>(2500);
        count = 0;
        long start = System.currentTimeMillis();
        for (int i = 1; i <= 50; i++) {
            Thread thread = new Thread(() -> {
                for (int j = 1; j <= 50; j++) {
                    consumer.accept(keys);
                }
            });
            threads.add(thread);
        }
        for (Thread thread : threads) {
            thread.start();
        }
        for (Thread thread : threads) {
            thread.join();
        }
        long diff = System.currentTimeMillis() - start;
        System.out.println("------------------------");
        System.out.println("最后结果:" + count);
        System.out.println("元素数量:" + keys.size());
        System.out.println(name + "-> 用时(ms):" + diff);
    }

    private void execNon(String name, Consumer<Set<Integer>> consumer) throws InterruptedException {
        count = 0;
        Set<Integer> keys = new HashSet<>(2500);
        long start = System.currentTimeMillis();
        for (int i = 1; i <= 50; i++) {
            for (int j = 1; j <= 50; j++) {
                consumer.accept(keys);
            }
        }
        long diff = System.currentTimeMillis() - start;
        System.out.println("------------------------");
        System.out.println("最后结果:" + count);
        System.out.println("元素数量:" + keys.size());
        System.out.println(name + "-> 用时(ms):" + diff);
    }

    @Test
    public void test1() throws InterruptedException {
        exec("无锁", keys -> {
            count++;
            if (!keys.add(count)) {
                System.out.println(count);
            }
        });
    }

    @Test
    public void test2() throws InterruptedException {
        exec("同步锁", keys -> {
            synchronized (this) {
                count++;
                if (!keys.add(count)) {
                    System.out.println(count);
                }
            }
        });
    }

    @Test
    public void lockTest() {
        String lockKey = "lock-1";
        template.delete(lockKey);
        template.opsForLock().lock(lockKey);
        template.opsForLock().unLock(lockKey);
        long start = System.currentTimeMillis();
        for (int i = 1; i <= 2500; i++) {
            template.opsForLock().lock(lockKey);
            template.opsForLock().unLock(lockKey);
        }
        long diff = System.currentTimeMillis() - start;
        System.out.println("------------------------");
        System.out.println("用时(ms):" + diff);
    }

    @Test
    public void test3() throws InterruptedException {
        final String key = "test-3";
        template.delete(key);
        exec("Autumn Redis 锁(多线程)", keys -> {
            try {
                template.opsForLock().lock(key);
                count++;
                if (!keys.add(count)) {
                    System.out.println(count);
                }
            } finally {
                boolean result = template.opsForLock().unLock(key);
                if (!result) {
                    System.out.println("解锁失败");
                }
            }
        });
    }

    @Test
    public void test4() throws InterruptedException {
        final String key = "test-4";
        template.delete(key);
        execNon("Autumn Redis 锁(单线程)", keys -> {
            try {
                template.opsForLock().lock(key);
                count++;
                if (!keys.add(count)) {
                    System.out.println(count);
                }
            } finally {
                boolean result = template.opsForLock().unLock(key);
                if (!result) {
                    System.out.println("解锁失败");
                }
            }
        });
    }
}
