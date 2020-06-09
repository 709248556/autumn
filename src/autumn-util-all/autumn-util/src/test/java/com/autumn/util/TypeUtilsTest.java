package com.autumn.util;

import org.junit.Assert;
import org.junit.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Set;

/**
 * 
 * @author 老码农
 *
 *         2017-10-10 13:52:10
 */
public class TypeUtilsTest {

	public enum Abc {
		/**
		 * 
		 */
		A,
		/**
		 * 
		 */
		B,
		/**
		 * 
		 */
		C
	}
	
	/**
	 * Logo AUTUMN
	 */
	public static String LOGO =
                    "        ◆           ◆                ◆   ◆◆◆◆◆◆◆◆◆◆◆   ◆                ◆   ◆◆                      ◆◆  ◆                  ◆\n" +
                    "       ◆ ◆         ◆                ◆             ◆             ◆                ◆   ◆  ◆                  ◆  ◆  ◆  ◆              ◆\n" +
                    "      ◆   ◆        ◆                ◆             ◆             ◆                ◆   ◆    ◆              ◆    ◆  ◆     ◆           ◆\n" +
                    "     ◆     ◆       ◆                ◆             ◆             ◆                ◆   ◆      ◆          ◆      ◆  ◆        ◆        ◆\n" +
                    "    ◆◆◆◆◆◆     ◆                ◆             ◆             ◆                ◆   ◆       ◆        ◆       ◆  ◆           ◆     ◆\n" +
                    "   ◆          ◆    ◆                ◆             ◆             ◆                ◆   ◆         ◆    ◆         ◆  ◆             ◆   ◆\n" +
                    "  ◆            ◆   ◆                ◆             ◆             ◆                ◆   ◆          ◆  ◆          ◆  ◆               ◆ ◆\n" +
                    " ◆              ◆   ◆◆◆◆◆◆◆◆◆              ◆              ◆◆◆◆◆◆◆◆◆    ◆            ◆            ◆  ◆                  ◆\n";     
           
	@Test
	public void LogoPrint() {	
		System.out.println(LOGO);	
	}

	@Test
	public void rawValueTest1() {
		short value = Short.MAX_VALUE;
		byte[] a = ByteUtils.getRawValue(value);
		short result = ByteUtils.getShortValue(a);
		System.out.println(result);
		Assert.assertEquals(value, result);
	}

	@Test
	public void rawValueTest3() {
		int value = Integer.MAX_VALUE;
		byte[] a = ByteUtils.getRawValue(value);
		int result = ByteUtils.getIntegerValue(a);
		System.out.println(result);
		Assert.assertEquals(value, result);
	}

	@Test
	public void rawValueTest4() {
		long value = 589587445586578L;
		byte[] a = ByteUtils.getRawValue(value);
		long result = ByteUtils.getLongValue(a);
		System.out.println(result);
		Assert.assertEquals(value, result);
	}

	@Test
	public void rawValueTest5() {
		float value = 568566.0f;
		byte[] a = ByteUtils.getRawValue(value);
		float result = ByteUtils.getFloatValue(a);
		System.out.println(result);
		boolean b = value - result == 0f;
		Assert.assertTrue(b);
	}
	
	@Test
	public void rawValueTest6() {
		double value = 56855866.00;
		byte[] a = ByteUtils.getRawValue(value);
		double result = ByteUtils.getDoubleValue(a);
		System.out.println(result);
		boolean b = value - result == 0f;
		Assert.assertTrue(b);
	}

	@Test
	public void test1() {
		System.out.println("TypeUtilsTest:" + TypeUtilsTest.class.getName());
		Object value;
		value = TypeUtils.toObjectConvert(Abc.class, "a");
		System.out.println(value);
		Abc[] ac = Abc.values();
		System.out.println(Arrays.toString(ac));
		// Abc.values()

		// Abc.valueOf(enumType, name)

	}

	/**
	 * 获取接口
	 * 
	 * 2017-12-06 12:34:57
	 */
	@Test
	public void testGetInterfaces() {
		Set<Class<?>> interfaces = TypeUtils.getInterfaces(ArrayList.class);
		System.out.println(interfaces.size());
	}

}
