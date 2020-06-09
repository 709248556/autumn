package com.autumn.security.session.impl;

import com.autumn.runtime.session.AutumnUser;
import com.autumn.runtime.task.TaskExecutorService;
import com.autumn.security.session.AutumnSessionListener;
import com.autumn.security.session.SessionListenerProxy;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.shiro.session.Session;
import org.apache.shiro.session.SessionListener;

import java.util.Collections;
import java.util.LinkedHashSet;
import java.util.Set;
import java.util.function.Consumer;

/**
 * 会话监听代理实现
 * <p>
 * </p>
 *
 * @description TODO
 * @author: 老码农
 * @create: 2020-04-01 20:15
 **/
public class SessionListenerProxyImpl implements SessionListenerProxy {

    private Set<SessionListener> listeners = Collections.synchronizedSet(new LinkedHashSet<>(16));

    private final TaskExecutorService taskExecutorService;

    /**
     * 实例化
     *
     * @param taskExecutorService 任务服务
     */
    public SessionListenerProxyImpl(TaskExecutorService taskExecutorService) {
        this.taskExecutorService = taskExecutorService;
    }

    private void callListeners(Consumer<SessionListener> consumer, String name) {
        this.taskExecutorService.getLinkedBlockingQueueService().submit(() -> {
            this.listeners.stream().parallel().forEach(listener -> {
                try {
                    consumer.accept(listener);
                } catch (Exception err) {
                    Log log = LogFactory.getLog(listener.getClass());
                    log.error("调用会话监听方法[" + listener.getClass() + "]的 [" + name + "]出错:" + err.getMessage(), err);
                }
            });
        });
    }

    private void callAutumnListeners(Consumer<AutumnSessionListener> consumer, String name) {
        this.taskExecutorService.getLinkedBlockingQueueService().submit(() -> {
            this.listeners.stream().parallel().forEach(listener -> {
                try {
                    if (listener instanceof AutumnSessionListener) {
                        consumer.accept((AutumnSessionListener) listener);
                    }
                } catch (Exception err) {
                    Log log = LogFactory.getLog(listener.getClass());
                    log.error("调用会话监听方法[" + listener.getClass() + "]的 [" + name + "]出错:" + err.getMessage(), err);
                }
            });
        });
    }

    @Override
    public void onStart(Session session) {
        this.callListeners(c -> c.onStart(session), "onStart");
    }

    @Override
    public void onStop(Session session) {
        this.callListeners(c -> c.onStop(session), "onStop");
    }

    @Override
    public void onExpiration(Session session) {
        this.callListeners(c -> c.onExpiration(session), "onExpiration");
    }

    @Override
    public void onStart(Session session, AutumnUser user) {
        this.callAutumnListeners(c -> c.onStart(session, user), "onStart");
    }

    @Override
    public void onStop(Session session, AutumnUser user) {
        this.callAutumnListeners(c -> c.onStop(session, user), "onStop");
    }

    @Override
    public void onExpiration(Session session, AutumnUser user) {
        this.callAutumnListeners(c -> c.onExpiration(session, user), "onExpiration");
    }

    @Override
    public void registerListener(SessionListener listener) {
        listeners.add(listener);
    }

}
