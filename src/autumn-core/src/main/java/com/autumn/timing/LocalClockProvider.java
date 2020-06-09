package com.autumn.timing;

import java.time.LocalDateTime;
import java.util.Date;

/**
 * 本地时钟
 *
 * @Description TODO
 * @Author 老码农
 * @Date 2019-08-06 17:40
 */
public class LocalClockProvider implements ClockProvider {

    @Override
    public Date now() {
        return new Date();
    }

    @Override
    public Long currentTimeMillis() {
        return System.currentTimeMillis();
    }

    @Override
    public LocalDateTime ldtNow() {
        return LocalDateTime.now();
    }

    @Override
    public Date gmtNow() {
        return this.now();
    }

    @Override
    public Long gmtCurrentTimeMillis() {
        return this.currentTimeMillis();
    }
}
