package com.autumn.timing;

import java.time.LocalDateTime;
import java.util.Date;

/**
 * 时钟提供程序
 *
 * @Description TODO
 * @Author 老码农
 * @Date 2019-08-06 17:37
 */
public interface ClockProvider {

    /**
     * 当前时间
     *
     * @return
     */
    Date now();

    /**
     * 当前时间毫秒(从1970-1-1开始)
     *
     * @return
     */
    Long currentTimeMillis();

    /**
     * 本地当前时间
     * @return
     */
    LocalDateTime ldtNow();

    /**
     * 获取gmt当前时间
     *
     * @return
     */
    Date gmtNow();

    /**
     * gmt当前时间毫秒(从1970-1-1开始)
     *
     * @return
     */
    Long gmtCurrentTimeMillis();
}
