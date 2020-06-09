package com.autumn.util.convert;

import java.math.BigDecimal;

import org.junit.Test;

import com.autumn.util.convert.BooleanConvert;
import com.autumn.util.convert.BooleanTypeConvert;

/**
 * 
 * @author 老码农
 *
 * 2017-12-06 12:31:58
 */
public class BooleanTest {
	@Test
	public void byteConvertTest() {
		System.out.println(Number.class.isAssignableFrom(Boolean.class));
		BooleanConvert con = new BooleanConvert();
		Boolean value;
		value = (Boolean) con.convert("15");
		System.out.println(value);
		value = (Boolean) con.convert("1");
		System.out.println(value);		
		value = (Boolean) con.convert("1.35");
		System.out.println(value);
		value = (Boolean) con.convert(12356);
		System.out.println(value);
		value = (Boolean) con.convert(null);
		System.out.println(value);
		value = (Boolean) con.convert(12356L);
		System.out.println(value);
		value = (Boolean) con.convert(new BigDecimal("12356"));
		System.out.println(value);
//		value = (Boolean) con.convert(new Date());
//		System.out.println(value);
		value = (Boolean) con.convert(25.55);
		System.out.println(value);
	}
	@Test
	public void byteTypeConvertTest() {
		BooleanTypeConvert con = new BooleanTypeConvert();
		boolean value;
		value = (boolean) con.convert("15");
		System.out.println(value);
		value = (boolean) con.convert("1");
		System.out.println(value);
		value = (boolean) con.convert("null");
		System.out.println(value);
		value = (boolean) con.convert("1.35");
		System.out.println(value);
		value = (boolean) con.convert(12356);
		System.out.println(value);
		value = (boolean) con.convert(null);
		System.out.println(value);
		value = (boolean) con.convert(12356L);
		System.out.println(value);
		value = (boolean) con.convert(new BigDecimal("12356"));
		System.out.println(value);
//		value = (byte) con.convert(new Date());
//		System.out.println(value);
		value = (boolean) con.convert(25.55);
		System.out.println(value);
	}
}
