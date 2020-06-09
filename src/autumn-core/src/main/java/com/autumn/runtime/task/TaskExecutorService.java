package com.autumn.runtime.task;

import java.util.concurrent.ExecutorService;

/**
 * 任务执行服务
 * <p>
 * </p>
 *
 * @description TODO
 * @author: 老码农
 * @create: 2020-04-02 00:46
 **/
public interface TaskExecutorService {

    /**
     * 获取先进先除的任务服务
     *
     * @return
     */
    ExecutorService getLinkedBlockingQueueService();
}
