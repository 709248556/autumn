package com.autumn.util;

import org.junit.Test;

/**
 * 
 * @author 老码农
 *
 *         2017-09-30 09:20:01
 */
public class TimeTest {

	@Test
	public void test1() {
		Time time = Time.MIN_VALUE;
		System.out.println(time);
		System.out.println(time.getTotalMilliseconds());
		time = Time.MAX_VALUE;
		System.out.println(time);
		System.out.println(time.getTotalMilliseconds());
	}

	@Test
	public void test2() {
		Time time = new Time();
		System.out.println(time);
		System.out.println(time.getTotalMilliseconds());
		time = new Time(12, 14, 23, 58);
		System.out.println(time);
		System.out.println(time.getTotalMilliseconds());
	}
	
	@Test
	public void test3() {
		Time time = new Time(25641);
		System.out.println(time);
		System.out.println(time.getTotalMilliseconds());
		time = new Time(856456);
		System.out.println(time);
		System.out.println(time.getTotalMilliseconds());
	}

}
