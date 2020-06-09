package com.autumn.util.date;

import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.Date;

/**
 * 日期解析
 *
 * @Description TODO
 * @Author 老码农
 * @Date 2019-10-07 14:49
 */
public interface DateParse {

    /**
     * 获取正则表过式
     *
     * @return
     */
    String getRegexPattern();

    /**
     * 获取匹配表过式
     *
     * @return
     */
    String getPattern();

    /**
     * 是否匹配
     *
     * @param date 日期值
     * @return
     */
    boolean isMatches(String date);

    /**
     * 解析日期
     *
     * @param date 日期值
     * @return
     */
    Date parseDate(String date);

    /**
     * 解析本地日期时间
     *
     * @param date 日期值
     * @return
     */
    LocalDateTime parseLocalDateTime(String date);

    /**
     * 解析本地日期
     *
     * @param date 日期值
     * @return
     */
    LocalDateTime parseLocalDate(String date);

    /**
     * 解析本地时间
     *
     * @param time 时间值
     * @return
     */
    LocalTime parseLocalTime(String time);

}
