package com.autumn.util.convert;

import java.math.BigDecimal;

import org.junit.Test;

import com.autumn.util.TestEnum;
import com.autumn.util.convert.ShortConvert;
import com.autumn.util.convert.ShortTypeConvert;

/**
 * 
 * @author 老码农
 *
 * 2017-12-06 12:32:39
 */
public class ShortTest {
	@Test
	public void shortConvertTest() {
//		Integer.parseInt("1.2");   NumberFormatException
//		Integer.valueOf("1.2");    NumberFormatException
		ShortConvert con = new ShortConvert();
		Short value;
//		value = (Short) con.convert("32768");//超过范围报错
//		System.out.println(value);
		value = (Short) con.convert("3777.555");
		System.out.println(value);
		value = (Short) con.convert(12356);
		System.out.println(value);
		value = (Short) con.convert(null);
		System.out.println(value);
		value = (Short) con.convert(12356L);
		System.out.println(value);
		value = (Short) con.convert(new BigDecimal("12334"));
		System.out.println(value);
//		value = (Short) con.convert(new Date());
//		System.out.println(value);
		value = (Short) con.convert(25.55);
		System.out.println(value);
		value = (Short)con.convert(TestEnum.password);
		System.out.println(value);	
	}
	@Test
	public void shortTypeTypeConvertTest() {
		ShortTypeConvert con = new ShortTypeConvert();
		short value;
//		value = (short) con.convert("1507515024679");//超过范围报错
//		System.out.println(value);
		value = (short) con.convert("15");
		System.out.println(value);
		value = (short) con.convert(12356);
		System.out.println(value);
		value = (short) con.convert(null);
		System.out.println(value);
		value = (short) con.convert(12356L);
		System.out.println(value);
		value = (short) con.convert(new BigDecimal("1243.5"));
		System.out.println(value);
//		value = (short) con.convert(new Date());
//		System.out.println(value);
		value = (short) con.convert(25.55);
		System.out.println(value);
		value = (short)con.convert(TestEnum.password);
		System.out.println(value);	
	}
}
