package com.autumn.util;

import org.junit.Test;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Calendar;
import java.util.Date;

/**
 * @author 老码农
 * <p>
 * 2017-09-30 16:06:45
 */
public class DateUtilsTest {

    @Test
    public void test1() {
        Date date = DateUtils.addDay(new Date(), 1);
        System.out.println(date);
        System.out.println(DateUtils.getYear(date));
        System.out.println(DateUtils.getMonth(date));
        System.out.println(DateUtils.getDay(date));

        System.out.println(DateUtils.getTime(date));

    }

    @Test
    public void test2() {
        Date date = DateUtils.add(new Date(), new TimeSpan(4, 1, 4, 5, 9));
        System.out.println(date);
        System.out.println(DateUtils.getYear(date));
        System.out.println(DateUtils.getMonth(date));
        System.out.println(DateUtils.getDay(date));

        System.out.println(DateUtils.getTime(date));

    }

    @Test
    public void test3() {
        Date date = DateUtils.getDate(new Date());
        System.out.println(date);
        System.out.println(DateUtils.getYear(date));
        System.out.println(DateUtils.getMonth(date));
        System.out.println(DateUtils.getDay(date));

        System.out.println(DateUtils.getTime(date));

    }

    @Test
    public void test4() {
        String str = "2017-12-21";
        Date date = DateUtils.parseDate(str);
        System.out.println(date);
        System.out.println(DateUtils.parseLocalDateTime(str));
    }

    @Test
    public void test5() {
        Calendar c1 = Calendar.getInstance();
        Date date = DateUtils.parseDate("2017-2-28T17:28:11");
        c1.setTime(date);
        int year = c1.get(Calendar.YEAR);
        int month = c1.get(Calendar.MONTH);
        int day = c1.get(Calendar.DAY_OF_MONTH);
        System.out.println("year:" + year + " month:" + month + " day:" + day);
        System.out.println(DateUtils.parseLocalDateTime("2017-2-28T17:28:11"));
    }

    @Test
    public void test6() {
        Date beginDate = DateUtils.parseDate("2017/7/21 17:28:11");
        Date endDate = DateUtils.parseDate("2018/7/21 17:28:11");
        System.out.println(DateUtils.getMonthDiff(beginDate, endDate));
    }

    @Test
    public void test7() {
        for (int year = 2012; year <= 2018; year++) {
            for (int month = 1; month <= 12; month++) {
                String monthString = String.format("%s-%s月的总天数", year, month);
                String dateString = String.format("%s-%s-%s", year, month, 1);
                Date date = DateUtils.parseDate(dateString);

                System.out.println(monthString + " :" + DateUtils.getMonthMaxDays(date));
                System.out.println(monthString + " ym:" + DateUtils.getMonthMaxDays(year, month));
            }
        }
    }

    @Test
    public void test8() {
        LocalDateTime localDateTime = LocalDateTime.now();
        System.out.println(DateUtils.getWeekBeginLocalDateTime(localDateTime));
        System.out.println(DateUtils.getWeekEndLocalDateTime(localDateTime));
        System.out.println(DateUtils.getWeekValue(localDateTime));

		LocalDate localDate = LocalDate.now();
		System.out.println(DateUtils.getWeekBeginLocalDate(localDate));
		System.out.println(DateUtils.getWeekEndLocalDate(localDate));
        System.out.println(DateUtils.getWeekValue(localDate));

		Date date = new Date();
		System.out.println(DateUtils.dateFormat(DateUtils.getWeekBeginDateTime(date)));
		System.out.println(DateUtils.dateFormat(DateUtils.getWeekEndDateTime(date)));
        System.out.println(DateUtils.getWeekValue(date));
    }

    @Test
    public void test9() {
        LocalDateTime localDateTime = LocalDateTime.now();
        LocalDate localDate = LocalDate.now();
        Date date = new Date();

        System.out.println(DateUtils.getDayBeginLocalDateTime(date));
        System.out.println(DateUtils.getDayBeginLocalDateTime(localDateTime));

        System.out.println(DateUtils.getDayEndLocalDateTime(date));
        System.out.println(DateUtils.getDayEndLocalDateTime(localDateTime));

        System.out.println(DateUtils.dateFormat(DateUtils.getDayBeginDateTime(date)));
        System.out.println(DateUtils.dateFormat(DateUtils.getDayEndDateTime(date)));
    }

    @Test
    public void test10() {
        LocalDateTime localDateTime = LocalDateTime.now();
        LocalDate localDate = LocalDate.now();
        Date date = new Date();

        System.out.println(DateUtils.getMonthBeginLocalDateTime(date));
        System.out.println(DateUtils.getMonthBeginLocalDateTime(localDateTime));

        System.out.println(DateUtils.getMonthEndLocalDateTime(date));
        System.out.println(DateUtils.getMonthEndLocalDateTime(localDateTime));

        System.out.println(DateUtils.dateFormat(DateUtils.getMonthBeginDateTime(date)));
        System.out.println(DateUtils.dateFormat(DateUtils.getMonthEndDateTime(date)));
    }

    @Test
    public void test11() {
        LocalDateTime localDateTime = LocalDateTime.now();
        LocalDate localDate = LocalDate.now();
        Date date = new Date();

        System.out.println(DateUtils.getMonthBeginLocalDate(date));
        System.out.println(DateUtils.getMonthBeginLocalDate(localDate));

        System.out.println(DateUtils.getMonthEndLocalDate(date));
        System.out.println(DateUtils.getMonthEndLocalDate(localDate));

        System.out.println(DateUtils.dateFormat(DateUtils.getMonthBeginDate(date)));
        System.out.println(DateUtils.dateFormat(DateUtils.getMonthEndDate(date)));
    }



}
