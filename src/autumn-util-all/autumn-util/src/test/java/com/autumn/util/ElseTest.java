package com.autumn.util;

import org.junit.Test;

public class ElseTest {

	private long calcIntegral(int currentPosition, long min, long max) {
		if (currentPosition < 0) {
			currentPosition = 1;
		} else {
			if (currentPosition > 100) {
				currentPosition = 100;
			}
		}
		if (currentPosition == 0) {
			return min;
		} else if (currentPosition == 100) {
			return max;
		} else {
			long diff = max - min;
			if (diff <= 0) {
				return min;
			}
			double section = (double) diff / (double) 100;
			Double value = section * currentPosition;
			long result = value.longValue();
			if (result > max) {
				return max;
			} else if (result < min) {
				return min;
			}
			return result;
		}
	}

	@Test
	public void test1() {
		System.out.println("-------------------------");

		long max = 80L;
		long min = 20L;

		for (int i = 0; i <= 100; i++) {
			if (max - min <= 100) {
				long value = min + i;
				if (value > max) {
					value = max;
				}
				System.out.println(i + "=" + value);
			} else {
				System.out.println(i + "=" + calcIntegral(i, min, max));
			}
		}
	}

	@Test
	public void test2() {
		System.out.println("-------------------------");
		long max = 12L;
		long min = 2L;
		for (int i = 0; i <= 100; i++) {
			System.out.println(i + "=" + calcIntegral(i, min, max));
		}
	}

}
