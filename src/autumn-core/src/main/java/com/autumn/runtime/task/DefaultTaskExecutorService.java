package com.autumn.runtime.task;

import java.util.concurrent.ExecutorService;

/**
 * 任务执行服务实现
 * <p>
 * </p>
 *
 * @description TODO
 * @author: 老码农
 * @create: 2020-04-02 00:47
 **/
public class DefaultTaskExecutorService implements TaskExecutorService {

    @Override
    public ExecutorService getLinkedBlockingQueueService() {
        return TaskUtils.getLinkedBlockingQueueService();
    }
}
