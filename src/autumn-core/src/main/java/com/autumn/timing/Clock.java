package com.autumn.timing;

import com.autumn.exception.ExceptionUtils;

import java.time.LocalDateTime;
import java.util.Date;

/**
 * 时钟
 *
 * @Description TODO
 * @Author 老码农
 * @Date 2019-08-06 17:38
 */
public abstract class Clock {

    private static ClockProvider clockProvider;

    static {
        clockProvider = new LocalClockProvider();
    }

    /**
     * 获取时钟提供程序
     *
     * @return
     */
    public static ClockProvider getClockProvider() {
        return Clock.clockProvider;
    }

    /**
     * 设置时钟提供程序
     *
     * @param clockProvider
     */
    public static void setClockProvider(ClockProvider clockProvider) {
        Clock.clockProvider = ExceptionUtils.checkNotNull(clockProvider, "clockProvider");
    }

    /**
     * 当前时间
     *
     * @return
     */
    public static Date now() {
        return getClockProvider().now();
    }

    /**
     * 本地当前时间
     *
     * @return
     */
    public static LocalDateTime ldtNow() {
        return getClockProvider().ldtNow();
    }

    /**
     * gmt当前时间
     *
     * @return
     */
    public static Date gmtNow() {
        return getClockProvider().gmtNow();
    }

    /**
     * 当前时间毫秒(从1970-1-1开始)
     *
     * @return
     */
    public static Long currentTimeMillis() {
        return getClockProvider().currentTimeMillis();
    }

    /**
     * gmt当前时间毫秒(从1970-1-1开始)
     *
     * @return
     */
    public static Long gmtCurrentTimeMillis() {
        return getClockProvider().gmtCurrentTimeMillis();
    }
}
