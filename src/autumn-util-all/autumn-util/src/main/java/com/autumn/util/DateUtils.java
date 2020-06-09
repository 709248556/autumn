package com.autumn.util;

import com.autumn.exception.ExceptionUtils;
import com.autumn.exception.InvalidCastException;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.*;
import java.time.chrono.IsoChronology;
import java.util.Calendar;
import java.util.Date;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Map.Entry;

/**
 * 日期帮助
 *
 * @author 老码农
 * <p>
 * 2017-09-30 15:33:57
 */
public class DateUtils {

    private final static Map<String, String> DATE_REG_PATTERN = new LinkedHashMap<>(50);

    /**
     *
     */
    public static final String FORMAT_DATE = "yyyy-MM-dd";

    /**
     *
     */
    public static final String FORMAT_DATE_TIME = "yyyy-MM-dd HH:mm:ss";

    /**
     *
     */
    public static final String FORMAT_DATE_TIME_MS = "yyyy-MM-dd HH:mm:ss.SSS";

    /**
     *
     */
    public static final String FORMAT_DATE_HH_MM = "yyyy-MM-dd HH:mm";

    /**
     *
     */
    public static final String FORMAT_DATE_CHINESE = "yyyy年MM月dd日";

    /**
     *
     */
    public static final String FORMAT_DATE_TIME_CHINESE = "yyyy年MM月dd日 HH时mm分ss秒";

    /**
     *
     */
    public static final String FORMAT_DATE_HH_MM_CHINESE = "yyyy年MM月dd日 HH时mm分";

    /**
     * 日期格式化(yyyy-MM-dd)
     *
     * @return
     */
    public static SimpleDateFormat createFormatDate() {
        return new SimpleDateFormat(FORMAT_DATE);
    }

    /**
     * 日期时间格式化(yyyy-MM-dd HH:mm:ss)
     *
     * @return
     */
    public static SimpleDateFormat createFormatDateTime() {
        return new SimpleDateFormat(FORMAT_DATE_TIME);
    }

    /**
     * 日期时间毫秒格式化(yyyy-MM-dd HH:mm:ss.SSS)
     *
     * @return
     */
    public static SimpleDateFormat createFormatDateTimeMillisecond() {
        return new SimpleDateFormat(FORMAT_DATE_TIME_MS);
    }

    /**
     * 时间格式化HH:mm:ss)
     *
     * @return
     */
    public static SimpleDateFormat createFormatTime() {
        return new SimpleDateFormat("HH:mm:ss");
    }

    static {
        DATE_REG_PATTERN.put("[\\d]{4}-[\\d]{1,2}-[\\d]{1,2} [\\d]{1,2}:[\\d]{1,2}:[\\d]{1,2}", FORMAT_DATE_TIME);
        DATE_REG_PATTERN.put("[\\d]{4}-[\\d]{1,2}-[\\d]{1,2} [\\d]{1,2}:[\\d]{1,2}:[\\d]{1,2}.[\\d]{1,3}", FORMAT_DATE_TIME + ".SSS");

        // DATE_REG_PATTERN.put("[\\d]{4}-[\\d]{1,2}-[\\d]{1,2}T[\\d]{1,2}:[\\d]{1,2}:[\\d]{1,2}", FORMAT_DATE_TIME);

        DATE_REG_PATTERN.put("[\\d]{4}/[\\d]{1,2}/[\\d]{1,2} [\\d]{1,2}:[\\d]{1,2}:[\\d]{1,2}", "yyyy/MM/dd HH:mm:ss");
        DATE_REG_PATTERN.put("[\\d]{4}/[\\d]{1,2}/[\\d]{1,2} [\\d]{1,2}:[\\d]{1,2}:[\\d]{1,2}.[\\d]{1,3}", "yyyy/MM/dd HH:mm:ss.SSS");
        DATE_REG_PATTERN.put("[\\d]{4}-[\\d]{1,2}-[\\d]{1,2}", FORMAT_DATE);
        DATE_REG_PATTERN.put("[\\d]{4}/[\\d]{1,2}/[\\d]{1,2}", "yyyy/MM/dd");

        DATE_REG_PATTERN.put("[\\d]{4}-[\\d]{1,2}-[\\d]{1,2} [\\d]{1,2}:[\\d]{1,2}", FORMAT_DATE_HH_MM);
        DATE_REG_PATTERN.put("[\\d]{4}/[\\d]{1,2}/[\\d]{1,2} [\\d]{1,2}:[\\d]{1,2}", "yyyy/MM/dd HH:mm");

        DATE_REG_PATTERN.put("[\\d]{4}年[\\d]{1,2}月[\\d]{1,2}日", FORMAT_DATE_CHINESE);
        DATE_REG_PATTERN.put("[\\d]{4}年[\\d]{1,2}月[\\d]{1,2}日 [\\d]{1,2}时[\\d]{1,2}分[\\d]{1,2}秒", FORMAT_DATE_TIME_CHINESE);
        DATE_REG_PATTERN.put("[\\d]{4}年[\\d]{1,2}月[\\d]{1,2}日 [\\d]{1,2}时[\\d]{1,2}分[\\d]{1,2}秒[\\d]{1,3}毫秒", FORMAT_DATE_TIME_CHINESE + "SSS毫秒");
        DATE_REG_PATTERN.put("[\\d]{4}年[\\d]{1,2}月[\\d]{1,2}日 [\\d]{1,2}时[\\d]{1,2}分", FORMAT_DATE_HH_MM_CHINESE);
        DATE_REG_PATTERN.put("[\\d]{4}年[\\d]{1,2}月[\\d]{1,2}日 [\\d]{1,2}:[\\d]{1,2}:[\\d]{1,2}", "yyyy年MM月dd日 HH:mm:ss");
        DATE_REG_PATTERN.put("[\\d]{4}年[\\d]{1,2}月[\\d]{1,2}日 [\\d]{1,2}:[\\d]{1,2}:[\\d]{1,2}.[\\d]{1,3}", "yyyy年MM月dd日 HH:mm:ss.SSS");
        DATE_REG_PATTERN.put("[\\d]{4}年[\\d]{1,2}月[\\d]{1,2}日 [\\d]{1,2}:[\\d]{1,2}", "yyyy年MM月dd日 HH:mm");
    }


    private final static String ISO_DATE_CONTAINS = "T";

    private static InvalidCastException createInvalidCastException(String dateString, Class<?> type, Exception err) {
        return new InvalidCastException("无法将字符窜 " + dateString + " 转换为 " + type.getName() + " 类型。", err);
    }

    /**
     * 是否为闰年
     *
     * @param year 年
     * @return
     */
    public static boolean isLeapYear(int year) {
        return IsoChronology.INSTANCE.isLeapYear(year);
    }

    /**
     * 日期解析
     *
     * @param dateString 日期字符窜
     * @return
     */
    public static Date parseDate(String dateString) {
        if (StringUtils.isNullOrBlank(dateString)) {
            return null;
        }
        dateString = dateString.trim();
        for (Entry<String, String> entry : DATE_REG_PATTERN.entrySet()) {
            if (dateString.matches(entry.getKey())) {
                try {
                    return new SimpleDateFormat(entry.getValue()).parse(dateString);
                } catch (Exception e) {
                    throw createInvalidCastException(dateString, Date.class, e);
                }
            }
        }
        if (dateString.contains(ISO_DATE_CONTAINS)) {
            return parseIsoDate(dateString);
        }
        throw createInvalidCastException(dateString, Date.class, null);
    }


    private final static int DATE_SECTION_LENGTH = 2;

    /**
     * Iso日期解析
     *
     * @param iosDateString 日期格式
     * @return
     */
    public static Date parseIsoDate(String iosDateString) {
        if (iosDateString == null) {
            return null;
        }
        String[] a = iosDateString.split("T");
        String errorInfo = "无法将字符窜 " + iosDateString + " 转换为 IosDate 类型。";
        if (a.length != DATE_SECTION_LENGTH) {
            throw ExceptionUtils.throwInvalidCastException(errorInfo);
        }
        String[] b = a[1].split("\\+");
        if (b.length > DATE_SECTION_LENGTH) {
            throw ExceptionUtils.throwInvalidCastException(errorInfo);
        }
        String[] c = b[0].split("[.]");
        if (c.length > DATE_SECTION_LENGTH) {
            throw ExceptionUtils.throwInvalidCastException(errorInfo);
        }
        Date date;
        try {
            if (c.length == 2) {
                date = createFormatDateTimeMillisecond().parse(a[0] + " " + c[0] + "." + c[1]);
            } else {
                date = createFormatDateTime().parse(a[0] + " " + c[0]);
            }
        } catch (ParseException e) {
            throw createInvalidCastException(iosDateString, Date.class, e);
        }
        return date;
    }

    /**
     * 本地日期时间解析
     *
     * @param dateString 日期字符窜
     * @return
     */
    public static LocalDateTime parseLocalDateTime(String dateString) {
        Date date = parseDate(dateString);
        if (date == null) {
            return null;
        }
        return LocalDateTime.ofInstant(date.toInstant(), ZoneId.systemDefault());
    }

    /**
     * 转换为 LocalDateTime
     *
     * @param date 日期
     * @return
     */
    public static LocalDateTime toLocalDateTime(Date date) {
        return LocalDateTime.ofInstant(date.toInstant(), ZoneId.systemDefault());
    }

    /**
     * 转换为 LocalDateTime
     *
     * @param localDate 日期
     * @return
     */
    public static LocalDateTime toLocalDateTime(LocalDate localDate) {
        Instant instant = localDate.atStartOfDay().atZone(ZoneId.systemDefault()).toInstant();
        return LocalDateTime.ofInstant(instant, ZoneId.systemDefault());
    }

    /**
     * 转换为 LocalDate
     *
     * @param date 日期
     * @return
     */
    public static LocalDate toLocalDate(Date date) {
        return toLocalDateTime(date).toLocalDate();
    }

    /**
     * 转换为 LocalTime
     *
     * @param date 日期
     * @return
     */
    public static LocalTime toLocalTime(Date date) {
        return toLocalDateTime(date).toLocalTime();
    }

    /**
     * 由 LocalDateTime 转换为 Date
     *
     * @param localDateTime
     * @return
     */
    public static Date from(LocalDateTime localDateTime) {
        ZoneId zone = ZoneId.systemDefault();
        return Date.from(localDateTime.atZone(zone).toInstant());
    }

    /**
     * 由 LocalDate 转换为 Date
     *
     * @param localDate
     * @return
     */
    public static Date from(LocalDate localDate) {
        ZoneId zone = ZoneId.systemDefault();
        Instant instant = localDate.atStartOfDay().atZone(zone).toInstant();
        return Date.from(instant);
    }

    /**
     * 由 LocalDate 与 localTime 转换为 Date
     *
     * @param localDate
     * @param localTime
     * @return
     */
    public static Date from(LocalDate localDate, LocalTime localTime) {
        ZoneId zone = ZoneId.systemDefault();
        LocalDateTime localDateTime = LocalDateTime.of(localDate, localTime);
        return Date.from(localDateTime.atZone(zone).toInstant());
    }

    /**
     * 在指定的日期上添加时间间隔
     *
     * @param date     日期
     * @param timeSpan 时间间隔
     * @return
     * @author 老码农 2017-09-30 15:44:54
     */
    public static Date add(Date date, TimeSpan timeSpan) {
        return new Date(date.getTime() + timeSpan.getTotalMilliseconds());
    }

    /**
     * 在指定的日期上添加年份
     *
     * @param date  日期
     * @param years 年数
     * @return
     * @author 老码农 2017-09-30 15:44:54
     */
    public static Date addYear(Date date, int years) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        calendar.add(Calendar.YEAR, years);
        return calendar.getTime();
    }

    /**
     * 在指定的日期上添加月份
     *
     * @param date
     * @param months
     * @return
     * @author 老码农 2017-09-30 15:46:45
     */
    public static Date addMonth(Date date, int months) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        calendar.add(Calendar.MONTH, months);
        return calendar.getTime();
    }

    /**
     * 在指定的日期上添加天数
     *
     * @param date 日期
     * @param days 天数
     * @return
     * @author 老码农 2017-09-30 15:47:44
     */
    public static Date addDay(Date date, int days) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        calendar.add(Calendar.DATE, days);
        return calendar.getTime();
    }

    /**
     * 在指定的日期上添加小时数
     *
     * @param date  日期
     * @param hours 小时数
     * @return
     * @author 老码农 2017-09-30 15:49:21
     */
    public static Date addHour(Date date, int hours) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        calendar.add(Calendar.HOUR_OF_DAY, hours);
        return calendar.getTime();
    }

    /**
     * 在指定的日期上添加分钟数
     *
     * @param date    日期
     * @param minutes 分钟
     * @return
     * @author 老码农 2017-09-30 15:50:15
     */
    public static Date addMinute(Date date, int minutes) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        calendar.add(Calendar.MINUTE, minutes);
        return calendar.getTime();
    }

    /**
     * 在指定的日期上添加秒数
     *
     * @param date    日期
     * @param seconds 秒数
     * @return
     * @author 老码农 2017-09-30 15:51:21
     */
    public static Date addSecond(Date date, int seconds) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        calendar.add(Calendar.SECOND, seconds);
        return calendar.getTime();
    }

    /**
     * 在指定的日期上添加毫秒数
     *
     * @param date        日期
     * @param millisecond 毫秒数
     * @return
     * @author 老码农 2017-09-30 15:51:21
     */
    public static Date addMillisecond(Date date, int millisecond) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        calendar.add(Calendar.MILLISECOND, millisecond);
        return calendar.getTime();
    }

    /**
     * 获取年份
     *
     * @param value 值
     * @return
     * @author 老码农 2017-09-30 16:10:19
     */
    public static int getYear(Date value) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(value);
        return calendar.get(Calendar.YEAR);
    }

    /**
     * 获取月份(从1开始)
     *
     * @param value 值
     * @return
     * @author 老码农 2017-09-30 16:10:19
     */
    public static int getMonth(Date value) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(value);
        return calendar.get(Calendar.MONTH) + 1;
    }

    /**
     * 获取日
     *
     * @param value 值
     * @return
     * @author 老码农 2017-09-30 16:10:19
     */
    public static int getDay(Date value) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(value);
        return calendar.get(Calendar.DAY_OF_MONTH);
    }

    /**
     * 获取日期部份(去掉小时、分钟、秒钟、毫秒)
     *
     * @param value 值
     * @return
     * @author 老码农 2017-09-30 16:06:06
     */
    public static Date getDate(Date value) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(value);
        Time tiem = getTime(calendar);
        return new Date(calendar.getTimeInMillis() - tiem.getTotalMilliseconds());
    }

    /**
     * 获取时间部份
     *
     * @param calendar
     * @return
     * @author 老码农 2017-09-30 17:37:05
     */
    public static Time getTime(Calendar calendar) {
        return new Time(calendar.get(Calendar.HOUR_OF_DAY), calendar.get(Calendar.MINUTE),
                calendar.get(Calendar.SECOND), calendar.get(Calendar.MILLISECOND));
    }

    /**
     * 获取时间部份
     *
     * @param value 值
     * @return
     * @author 老码农 2017-09-30 16:16:30
     */
    public static Time getTime(Date value) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(value);
        return getTime(calendar);
    }

    /**
     * 两个日期相减
     *
     * @param left  左
     * @param rigth 右
     * @return
     * @author 老码农 2017-10-09 18:40:14
     */
    public static TimeSpan subtract(Date left, Date rigth) {
        return new TimeSpan(left.getTime() - rigth.getTime());
    }

    /**
     * 时间格式化（YYYY-MM-dd HH:mm:ss） 如果date参数为空则默认当前系统时间
     *
     * @param date
     * @return
     */
    public static String dateFormat(Date date) {
        return dateFormat(date, createFormatDateTime());
    }

    /**
     * 时间格式化 如果date参数为空则默认当前系统时间
     *
     * @param date         日期
     * @param formatString 格式字符窜
     * @return
     */
    public static String dateFormat(Date date, String formatString) {
        return dateFormat(date, new SimpleDateFormat(formatString));
    }

    /**
     * 时间格式化 如果date参数为空则默认当前系统时间
     *
     * @param date       日期
     * @param dateFormat 格式
     * @return
     */
    public static String dateFormat(Date date, SimpleDateFormat dateFormat) {
        if (date == null) {
            date = new Date();
        }
        synchronized (dateFormat) {
            return dateFormat.format(date);
        }
    }

    /**
     * 获取当前系统时间
     *
     * @return
     */
    public static Date currentSystemTime() {
        return new Date();
    }

    /**
     * 通过时间秒毫秒数判断两个时间的间隔
     *
     * @param date
     * @param secondDate
     * @return
     */
    public static int differentDaysByMillisecond(Date date, Date secondDate) {
        return (int) ((secondDate.getTime() - date.getTime()) / (1000 * 3600 * 24));
    }

    /**
     * 获取每个月的最大天数
     *
     * @param calendar 日历
     * @return
     */
    public static int getMonthMaxDays(Calendar calendar) {
        return calendar.getActualMaximum(Calendar.DAY_OF_MONTH);
    }

    /**
     * 获取每个月的最大天数
     *
     * @param date 日期
     * @return
     */
    public static int getMonthMaxDays(Date date) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        return getMonthMaxDays(calendar);
    }

    /**
     * 获取每个月的最大天数
     *
     * @param year  年度
     * @param month 月度
     * @return
     */
    public static int getMonthMaxDays(int year, int month) {
        switch (month) {
            case 2:
                return isLeapYear(year) ? 29 : 28;
            case 4:
            case 6:
            case 9:
            case 11:
                return 30;
            default:
                return 31;
        }
    }

    /**
     * 计算两个月相关
     *
     * @param beginDate 开始日期
     * @param endDate   结束日期
     * @return
     */
    public static int getMonthDiff(Date beginDate, Date endDate) {
        return getMonthDiff(beginDate, endDate, true);
    }

    /**
     * 计算两个月相关
     *
     * @param beginDate 开始日期
     * @param endDate   结束日期
     * @param isCalcDay 是否计算到天
     * @return
     */
    public static int getMonthDiff(Date beginDate, Date endDate, boolean isCalcDay) {
        Calendar c1 = Calendar.getInstance();
        Calendar c2 = Calendar.getInstance();
        c1.setTime(beginDate);
        c2.setTime(endDate);
        int year1 = c1.get(Calendar.YEAR);
        int year2 = c2.get(Calendar.YEAR);
        int month1 = c1.get(Calendar.MONTH);
        int month2 = c2.get(Calendar.MONTH);
        int months = (year2 - year1) * 12;
        months += month2 - month1;
        if (isCalcDay) {
            int day1 = c1.get(Calendar.DAY_OF_MONTH);
            int day2 = c2.get(Calendar.DAY_OF_MONTH);
            // 是否跨日
            if (day2 < day1) {
                int maxDay1 = getMonthMaxDays(c1);
                int maxDay2 = getMonthMaxDays(c2);
                // 如果都不是月底的话
                if (!(day1 == maxDay1 && day2 == maxDay2)) {
                    months--;
                }
            }
        }
        return months;
    }

    /**
     * 获取指定日期的开始时间
     *
     * @param date 本地时间
     * @return 返回指定日期的 00:00:00.0000000
     */
    public static LocalDateTime getDayBeginLocalDateTime(Date date) {
        return getDayBeginLocalDateTime(toLocalDateTime(date));
    }

    /**
     * 获取指定日期的开始时间
     *
     * @param localDateTime 本地时间
     * @return 返回指定日期的 00:00:00.0000000
     */
    public static LocalDateTime getDayBeginLocalDateTime(LocalDateTime localDateTime) {
        return LocalDateTime.of(localDateTime.getYear(), localDateTime.getMonth(), localDateTime.getDayOfMonth(),
                0, 0, 0, 0);
    }

    /**
     * 获取指定日期的结束时间
     *
     * @param date 本地时间
     * @return 返回指定日期的 23:59:59.999999999
     */
    public static LocalDateTime getDayEndLocalDateTime(Date date) {
        return getDayEndLocalDateTime(toLocalDateTime(date));
    }

    /**
     * 获取指定日期的结束时间
     *
     * @param localDateTime 本地时间
     * @return 返回指定日期的 23:59:59.999999999
     */
    public static LocalDateTime getDayEndLocalDateTime(LocalDateTime localDateTime) {
        return LocalDateTime.of(localDateTime.getYear(), localDateTime.getMonth(), localDateTime.getDayOfMonth(),
                23, 59, 59, 999999999);
    }

    /**
     * 获取指定日期的开始时间
     *
     * @param date 本地时间
     * @return 返回指定日期的 00:00:00.0000000
     */
    public static Date getDayBeginDateTime(Date date) {
        return from(getDayBeginLocalDateTime(date));
    }

    /**
     * 获取指定日期的开始时间
     *
     * @param date 本地时间
     * @return 返回指定日期的 23:59:59.999999999
     */
    public static Date getDayEndDateTime(Date date) {
        return from(getDayEndLocalDateTime(date));
    }

    /**
     * 获取指定日期的月初开始时间
     *
     * @param date 本地时间
     * @return 返回月初的 00:00:00.0000000
     */
    public static LocalDateTime getMonthBeginLocalDateTime(Date date) {
        return getMonthBeginLocalDateTime(toLocalDateTime(date));
    }

    /**
     * 获取指定日期的月初开始时间
     *
     * @param localDateTime 本地时间
     * @return 返回月初的 00:00:00.0000000
     */
    public static LocalDateTime getMonthBeginLocalDateTime(LocalDateTime localDateTime) {
        return LocalDateTime.of(localDateTime.getYear(), localDateTime.getMonth(), 1,
                0, 0, 0, 0);
    }

    /**
     * 获取指定日期的月底结束时间
     *
     * @param date 本地时间
     * @return 返回月底的 23:59:59.999999999
     */
    public static LocalDateTime getMonthEndLocalDateTime(Date date) {
        return getMonthEndLocalDateTime(toLocalDateTime(date));
    }

    /**
     * 获取指定日期的月底结束时间
     *
     * @param localDateTime 本地时间
     * @return 返回月底的 23:59:59.999999999
     */
    public static LocalDateTime getMonthEndLocalDateTime(LocalDateTime localDateTime) {
        return LocalDateTime.of(localDateTime.getYear(), localDateTime.getMonth(),
                getMonthMaxDays(localDateTime.getYear(), localDateTime.getMonth().getValue()),
                23, 59, 59, 999999999);
    }

    /**
     * 获取指定日期的月初开始时间
     *
     * @param date 本地时间
     * @return 返回月初的 00:00:00.0000000
     */
    public static Date getMonthBeginDateTime(Date date) {
        return from(getMonthBeginLocalDateTime(date));
    }

    /**
     * 获取指定日期的月初开始时间
     *
     * @param year  年
     * @param month 月
     * @return 返回月初的 00:00:00.0000000
     */
    public static Date getMonthBeginDateTime(int year, int month) {
        return from(LocalDateTime.of(year, month, 1, 0, 0));
    }

    /**
     * 获取指定日期的月底结束时间
     *
     * @param date 本地时间
     * @return 返回月底的 23:59:59.999999999
     */
    public static Date getMonthEndDateTime(Date date) {
        return from(getMonthEndLocalDateTime(date));
    }

    /**
     * 获取指定日期的月底结束时间
     *
     * @param year  年
     * @param month 月
     * @return 返回月底的 23:59:59.999999999
     */
    public static Date getMonthEndDateTime(int year, int month) {
        return from(LocalDateTime.of(year, month,
                getMonthMaxDays(year, month),
                23, 59, 59, 999999999));
    }

    /**
     * 获取指定日期的月初开始日期
     *
     * @param date 本地时间
     * @return 返回月初的日期
     */
    public static LocalDate getMonthBeginLocalDate(Date date) {
        return getMonthBeginLocalDate(toLocalDate(date));
    }

    /**
     * 获取指定日期的月初开始时间
     *
     * @param localDate 本地日期
     * @return 返回月初的日期
     */
    public static LocalDate getMonthBeginLocalDate(LocalDate localDate) {
        return LocalDate.of(localDate.getYear(), localDate.getMonth(), 1);
    }

    /**
     * 获取指定日期的月底结束日期
     *
     * @param date 本地时间
     * @return 返回月底的日期
     */
    public static LocalDate getMonthEndLocalDate(Date date) {
        return getMonthEndLocalDate(toLocalDate(date));
    }

    /**
     * 获取指定日期的月底结束结束
     *
     * @param localDate 本地日期
     * @return 返回月底的日期
     */
    public static LocalDate getMonthEndLocalDate(LocalDate localDate) {
        return LocalDate.of(localDate.getYear(), localDate.getMonth(),
                getMonthMaxDays(localDate.getYear(), localDate.getMonth().getValue()));
    }

    /**
     * 获取指定日期的月初开始日期
     *
     * @param date 本地时间
     * @return 返回月初的日期
     */
    public static Date getMonthBeginDate(Date date) {
        return from(getMonthBeginLocalDate(date));
    }

    /**
     * 获取指定日期的月初开始日期
     *
     * @param year  年
     * @param month 月
     * @return 返回月初的日期
     */
    public static Date getMonthBeginDate(int year, int month) {
        return from(LocalDate.of(year, month, 1));
    }

    /**
     * 获取指定日期的月底结束日期
     *
     * @param date 本地时间
     * @return 返回月底的日期
     */
    public static Date getMonthEndDate(Date date) {
        return from(getMonthEndLocalDate(date));
    }

    /**
     * 获取指定日期的月底结束日期
     *
     * @param year  年
     * @param month 月
     * @return 返回月底的日期
     */
    public static Date getMonthEndDate(int year, int month) {
        return from(LocalDate.of(year, month, getMonthMaxDays(year, month)));
    }

    /**
     * 获取星期值
     *
     * @param localDateTime 日期时间
     * @return 1=星期一...7=星期天
     */
    public static int getWeekValue(LocalDateTime localDateTime) {
        return localDateTime.getDayOfWeek().getValue();
    }

    /**
     * 获取星期值
     *
     * @param localDate 日期
     * @return 1=星期一...7=星期天
     */
    public static int getWeekValue(LocalDate localDate) {
        return localDate.getDayOfWeek().getValue();
    }

    /**
     * 获取星期值
     *
     * @param date 日期
     * @return 1=星期一...7=星期天
     */
    public static int getWeekValue(Date date) {
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        int value = cal.get(Calendar.DAY_OF_WEEK) - 1;
        return value == 0 ? 7 : value;
    }

    /**
     * 获取指定日期周的开始时间
     *
     * @param localDateTime 日期时间
     * @return 返回星期一的日期
     */
    public static LocalDateTime getWeekBeginLocalDateTime(LocalDateTime localDateTime) {
        int dayofweek = localDateTime.getDayOfWeek().getValue() + 1;
        if (dayofweek == 1) {
            dayofweek += 7;
        }
        LocalDateTime time = localDateTime.plusDays(2 - dayofweek);
        return getDayBeginLocalDateTime(time);
    }

    /**
     * 获取指定日期周的结束时间
     *
     * @param localDateTime 日期时间
     * @return 返回星期天的日期
     */
    public static LocalDateTime getWeekEndLocalDateTime(LocalDateTime localDateTime) {
        LocalDateTime time = getWeekBeginLocalDateTime(localDateTime).plusDays(6);
        return getDayEndLocalDateTime(time);
    }

    /**
     * 获取指定日期周的开始日期
     *
     * @param localDate 日期时间
     * @return 返回星期一的日期
     */
    public static LocalDate getWeekBeginLocalDate(LocalDate localDate) {
        int dayofweek = localDate.getDayOfWeek().getValue() + 1;
        if (dayofweek == 1) {
            dayofweek += 7;
        }
        return localDate.plusDays(2 - dayofweek);
    }

    /**
     * 获取指定日期周的结束日期
     *
     * @param localDate 日期
     * @return 返回星期天的日期
     */
    public static LocalDate getWeekEndLocalDate(LocalDate localDate) {
        return getWeekBeginLocalDate(localDate).plusDays(6);
    }

    /**
     * 获取指定日期周的开始时间
     *
     * @param date 日期
     * @return 返回星期一的日期
     */
    public static Date getWeekBeginDateTime(Date date) {
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        int dayofweek = cal.get(Calendar.DAY_OF_WEEK);
        if (dayofweek == 1) {
            dayofweek += 7;
        }
        cal.add(Calendar.DATE, 2 - dayofweek);
        return getDayBeginDateTime(cal.getTime());
    }

    /**
     * 获取指定日期周的结束时间
     *
     * @return 返回星期天的日期
     */
    public static Date getWeekEndDateTime(Date date) {
        Calendar cal = Calendar.getInstance();
        cal.setTime(getWeekBeginDateTime(date));
        cal.add(Calendar.DAY_OF_WEEK, 6);
        return getDayEndDateTime(cal.getTime());
    }


}
