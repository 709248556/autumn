package com.autumn.util.convert;

import java.math.BigDecimal;

import org.junit.Test;

import com.autumn.util.TestEnum;
import com.autumn.util.convert.ByteConvert;
import com.autumn.util.convert.ByteTypeConvert;

/**
 * 
 * @author 老码农
 *
 * 2017-12-06 12:32:04
 */
public class ByteTest {
	@Test
	public void byteConvertTest() {
		ByteConvert con = new ByteConvert();
		Byte value;
//		value = (Byte) con.convert("1507");//超过范围报错
//		System.out.println(value);
		value = (Byte) con.convert("15");
		System.out.println(value);
		value = (Byte) con.convert("1.35");
		System.out.println(value);
//		value = (Byte) con.convert(12356);//超过范围报错
//		System.out.println(value);
		value = (Byte) con.convert(null);
		System.out.println(value);
//		value = (Byte) con.convert(new BigDecimal("128"));
//		System.out.println(value);
		value = (Byte) con.convert(25.55);
		System.out.println(value);
		value = (Byte)con.convert(TestEnum.password);
		System.out.println(value);	
	}
	@Test
	public void byteTypeConvertTest() {
		ByteTypeConvert con = new ByteTypeConvert();
		byte value;
//		value = (byte) con.convert("1507515024679");
//		System.out.println(value);
		value = (byte) con.convert("15");
		System.out.println(value);
		value = (Byte) con.convert("1.35");
		System.out.println(value);
//		value = (byte) con.convert(12356);
//		System.out.println(value);
		value = (byte) con.convert(null);
		System.out.println(value);
		value = (byte) con.convert(new BigDecimal("124"));
		System.out.println(value);
		value = (byte) con.convert(25.55);
		System.out.println(value);
		value = (byte)con.convert(TestEnum.password);
		System.out.println(value);	
	}
}
