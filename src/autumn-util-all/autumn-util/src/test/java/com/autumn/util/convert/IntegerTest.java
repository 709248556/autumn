package com.autumn.util.convert;

import java.math.BigDecimal;

import org.junit.Test;

import com.autumn.util.TestEnum;
import com.autumn.util.convert.IntegerConvert;
import com.autumn.util.convert.IntegerTypeConvert;

/**
 * 
 * @author 老码农
 *
 * 2017-12-06 12:32:28
 */
public class IntegerTest {
	
	@Test
	public void intConvertTest() {
//		System.out.println(System.currentTimeMillis());
		IntegerConvert con = new IntegerConvert();
		Integer value;
		//超过范围报错
//		value = (Integer) con.convert("1507515024679");
//		System.out.println(value);
		value = (Integer) con.convert("12.35");
		System.out.println(value);
		value = (Integer) con.convert(12356);
		System.out.println(value);
		value = (Integer) con.convert(null);
		System.out.println(value);
		//超过范围报错
//		value = (Integer) con.convert(1507515024679l);
		//超过范围报错
		value = (Integer) con.convert(15075L);
		System.out.println(value);
		//超过范围报错
//		value = (Integer) con.convert(new BigDecimal("1507515024679"));
		value = (Integer) con.convert(new BigDecimal("15075151.34"));
		System.out.println(value);
//		value = (Integer) con.convert(new Date());
//		System.out.println(value);
		value = (Integer) con.convert(25.55);
		System.out.println(value);
		value = (Integer)con.convert(TestEnum.password);
		System.out.println(value);	
	}

	@Test
	public void intTypeConvertTest() {
		IntegerTypeConvert con = new IntegerTypeConvert();
		int value;
//		value = (Integer) con.convert("1507515024679");
//		System.out.println(value);
		value = (int) con.convert("12356");
		System.out.println(value);
		value = (int) con.convert("12.35");
		System.out.println(value);
		value = (int) con.convert(12356);
		System.out.println(value);
		value = (int) con.convert(null);
		System.out.println(value);
		value = (int) con.convert(12356L);
		System.out.println(value);
		value = (int) con.convert(new BigDecimal("12356"));
		System.out.println(value);
		value = (int) con.convert(25.55);
		System.out.println(value);
		value = (int)con.convert(TestEnum.password);
		System.out.println(value);	
	}
}
