package com.autumn.evaluator;

public class DateTimeFunctionTest extends AppTest {

    public DateTimeFunctionTest() {
        super("日期与时间测试");
    }

    /**
     * 返回一个日期时间在另外一个日期时间的基础添加指定的年\月\日\时\分\秒
     */
    public void testDateAddYMDHMS() {
        String expression = "DateAddYMDHMS(#2018-03-19 12:12:12#, 0, 1, 0, 0, 0, 0)";
        test(expression);
    }

    /**
     * 返回一个日期在另外一个日期的基础添加指定的年\月\日
     */
    public void testDateAddYMD() {
        String expression = "DateAddYMD(#2018-03-19#, 0, 1, 0)";
        test(expression);
    }

    /**
     * 返回一个日期中的月低最后一天
     */
    public void testMonthLastDay() {
        String expression = "MonthLastDay(#2018-03-19#)";
        test(expression);
    }

    /**
     * 返回一个日期中的年份
     */
    public void testYear() {
        String expression = "Year(#2018-03-19#)";
        test(expression);
    }

    /**
     * 返回一个日期中的星期
     */
    public void testWeekDay() {
        String expression = "WeekDay(#2018-03-19#, 1)";
        test(expression);
    }

    /**
     * 返回一当前机器中的日期
     */
    public void testToDay() {
        String expression = "ToDay()";
        test(expression);
    }

    /**
     * 日期转换
     */
    public void testDateConvert() {
        String expression = "DateConvert(\"2018-03-19\")";
        test(expression);
    }

    /**
     * 返回一个日期时间中的秒钟
     */
    public void testSecond() {
        String expression = "Second(#2018-03-19 12:12:12#)";
        test(expression);
    }

    /**
     * 返回当前机器中的日期与时间
     */
    public void testNow() {
        String expression = "Now()";
        test(expression);
    }

    /**
     * 返回一个日期中的月份
     */
    public void testMonth() {
        String expression = "Month(#2018-03-19 12:12:12#)";
        test(expression);
    }

    /**
     * 返回一个日期时间中的分钟数
     */
    public void testMinute() {
        String expression = "Minute(#2018-03-19 12:12:12#)";
        test(expression);
    }

    /**
     * 返回一个日期时间中的小时数
     */
    public void testHour() {
        String expression = "Minute(#2018-03-19 12:12:12#)";
        test(expression);
    }

    /**
     * 返回两个日期相差的天数
     */
    public void testDateDiff() {
        String expression = "DateDiff(#2018-03-19 12:12:12#, #2018-03-23 12:12:12#)";
        test(expression);
    }

    /**
     * 返回一个日期中的天数
     */
    public void testDay() {
        String expression = "Day(#2018-03-19 12:12:12#)";
        test(expression);
    }

    /**
     * 日期时间戳的毫秒数
     */
    public void testTimestamp() {
        String expression = "timestamp(#2018-03-19 12:12:12#)";
        test(expression);
    }

    /**
     * 返回指定的日期
     */
    public void testDate() {
        String expression = "Date(2018, 1, 12)";
        test(expression);
    }

    /**
     * 根据参数类型返回一个日期格式
     */
    public void testDateFormat() {
        String expression = "DateFormat(#2018-03-19 12:12:12#, 1)";
        test(expression);
    }
}
