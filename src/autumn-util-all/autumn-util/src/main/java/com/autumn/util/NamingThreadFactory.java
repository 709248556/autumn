package com.autumn.util;

import org.apache.commons.lang.ClassUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ThreadFactory;
import java.util.concurrent.atomic.AtomicLong;
import java.lang.Thread.UncaughtExceptionHandler;

/**
 * 命名的线程工厂
 *
 * @Description TODO
 * @Author 老码农
 * @Date 2019-08-25 18:47
 */
public class NamingThreadFactory implements ThreadFactory {

    /**
     * 日志
     */
    private static final Log LOGGER = LogFactory.getLog(NamingThreadFactory.class);

    /**
     * 线程名称
     */
    private String name;
    /**
     * Is daemon thread
     */
    private boolean daemon;
    /**
     * UncaughtExceptionHandler
     */
    private UncaughtExceptionHandler uncaughtExceptionHandler;
    /**
     * Sequences for multi thread name prefix
     */
    private final ConcurrentHashMap<String, AtomicLong> sequences;

    /**
     * Constructors
     */
    public NamingThreadFactory() {
        this(null, false, null);
    }

    public NamingThreadFactory(String name) {
        this(name, false, null);
    }

    public NamingThreadFactory(String name, boolean daemon) {
        this(name, daemon, null);
    }

    public NamingThreadFactory(String name, boolean daemon, UncaughtExceptionHandler handler) {
        this.name = name;
        this.daemon = daemon;
        this.uncaughtExceptionHandler = handler;
        this.sequences = new ConcurrentHashMap<>();
    }

    @Override
    public Thread newThread(Runnable r) {
        Thread thread = new Thread(r);
        thread.setDaemon(this.daemon);

        // If there is no specified name for thread, it will auto detect using the invoker classname instead.
        // Notice that auto detect may cause some performance overhead
        String prefix = this.name;
        if (StringUtils.isBlank(prefix)) {
            prefix = getInvoker(2);
        }
        thread.setName(prefix + "-" + getSequence(prefix));

        // no specified uncaughtExceptionHandler, just do logging.
        if (this.uncaughtExceptionHandler != null) {
            thread.setUncaughtExceptionHandler(this.uncaughtExceptionHandler);
        } else {
            thread.setUncaughtExceptionHandler((t, e) -> LOGGER.error("unhandled exception in thread: " + t.getId() + ":" + t.getName(), e));
        }

        return thread;
    }

    /**
     * Get the method invoker's class name
     *
     * @param depth
     * @return
     */
    private String getInvoker(int depth) {
        Exception e = new Exception();
        StackTraceElement[] stes = e.getStackTrace();
        if (stes.length > depth) {
            return ClassUtils.getShortClassName(stes[depth].getClassName());
        }
        return getClass().getSimpleName();
    }

    /**
     * Get sequence for different naming prefix
     *
     * @param invoker
     * @return
     */
    private long getSequence(String invoker) {
        AtomicLong r = this.sequences.get(invoker);
        if (r == null) {
            r = new AtomicLong(0);
            AtomicLong previous = this.sequences.putIfAbsent(invoker, r);
            if (previous != null) {
                r = previous;
            }
        }
        return r.incrementAndGet();
    }

    /**
     * 获取线程名称
     */
    public String getName() {
        return name;
    }

    /**
     * 设置线程名称
     *
     * @param name 线程名称
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * 是否守护进程
     *
     * @return
     */
    public boolean isDaemon() {
        return daemon;
    }

    /**
     * 设置守护进程
     *
     * @param daemon
     */
    public void setDaemon(boolean daemon) {
        this.daemon = daemon;
    }

    /**
     * 获取 未捕获异常处理器
     *
     * @return
     */
    public UncaughtExceptionHandler getUncaughtExceptionHandler() {
        return uncaughtExceptionHandler;
    }

    /**
     * 设置 未捕获异常处理器
     *
     * @param handler 处理器
     */
    public void setUncaughtExceptionHandler(UncaughtExceptionHandler handler) {
        this.uncaughtExceptionHandler = handler;
    }
}
