package com.autumn.security.session.impl;

import org.apache.shiro.session.Session;

/**
 * 内存会话
 * <p>
 * </p>
 *
 * @description TODO
 * @author: 老码农
 * @create: 2020-04-29 11:31
 **/
public class AutumnSessionInMemory {

    private final Session session;
    private final long createTime;
    private final long memoryTimeout;

    public AutumnSessionInMemory(Session session, long memoryTimeout) {
        this.session = session;
        this.memoryTimeout = memoryTimeout;
        this.createTime = System.currentTimeMillis();
    }

    public Session getSession() {
        return session;
    }

    public long getCreateTime() {
        return createTime;
    }

    public long getMemoryTimeout() {
        return memoryTimeout;
    }

    /**
     * 是否过期
     *
     * @return
     */
    public boolean isExpire() {
        return System.currentTimeMillis() - this.createTime > this.getMemoryTimeout();
    }

}
