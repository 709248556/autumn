package com.autumn.util;

import org.junit.Test;

/**
 * 
 * @author 老码农
 *
 * 2017-09-30 14:26:10
 */
public class TimeSpanTest {

	@Test
	public void test1() {
		TimeSpan time = TimeSpan.MIN_VALUE;
		System.out.println(time);
		System.out.println(time.getTotalMilliseconds());
		time = TimeSpan.MAX_VALUE;
		System.out.println(time);
		System.out.println(time.getTotalMilliseconds());
		System.out.println("days:" + time.getTotalDays());
		System.out.println("hours:" + time.getTotalHours());
		System.out.println("minutes:" + time.getTotalMinutes());
		System.out.println("seconds:" + time.getTotalSeconds());
	}
	
}
