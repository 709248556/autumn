package com.autumn.runtime.task;

import com.autumn.util.NamingThreadFactory;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

/**
 * 任务帮助
 * <p>
 * </p>
 *
 * @description TODO
 * @author: 老码农
 * @create: 2020-04-02 00:45
 **/
class TaskUtils {

    private static final ExecutorService LINKED_BLOCKING_QUEUE_EXECUTOR_SERVICE;

    static {
        int cores = Runtime.getRuntime().availableProcessors();
        //指字了无界队列,空闲0秒后回收，若超过LinkedBlockingQueue的最大元素则拒绝并出错。
        LINKED_BLOCKING_QUEUE_EXECUTOR_SERVICE = new ThreadPoolExecutor(cores * 2, cores * 8,
                0L, TimeUnit.MILLISECONDS,
                new LinkedBlockingQueue<>(),
                new NamingThreadFactory("stoneMaterialTask"),
                new ThreadPoolExecutor.CallerRunsPolicy());
    }

    /**
     * 获取先进先除的任务服务
     *
     * @return
     */
    public static ExecutorService getLinkedBlockingQueueService() {
        return TaskUtils.LINKED_BLOCKING_QUEUE_EXECUTOR_SERVICE;
    }

    /**
     * 关闭
     */
    public static void shutdown() {
        if (!TaskUtils.LINKED_BLOCKING_QUEUE_EXECUTOR_SERVICE.isShutdown()) {
            TaskUtils.LINKED_BLOCKING_QUEUE_EXECUTOR_SERVICE.shutdownNow();
        }
    }

}
