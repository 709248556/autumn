package com.autumn.util.convert;

import java.math.BigDecimal;
import java.util.Date;

import org.junit.Test;

import com.autumn.util.TestEnum;
import com.autumn.util.convert.FloatConvert;
import com.autumn.util.convert.FloatTypeConvert;

/**
 * 
 * @author 老码农
 *
 * 2017-12-06 12:32:22
 */
public class FloatTest {

	@Test
	public void floatTest() {
		
//		System.out.println(System.currentTimeMillis());
		FloatConvert con = new FloatConvert();
		Float value = (Float) con.convert("12356");
		System.out.println(value);
		
		value = (Float)con.convert("2012-12-09");
		System.out.println(value);
		value = (Float) con.convert("1994-02-17 12:55:19");
		System.out.println(value);
		value = (Float) con.convert("1999年02月17日");
		System.out.println(value);
		value = (Float) con.convert("1999年02月17日 23时54分16秒");
		System.out.println(value);
		value = (Float) con.convert("2015/02/17");
		System.out.println(value);
		value = (Float) con.convert("1999/02/17 23:54:16");
		System.out.println(value);
		value = (Float) con.convert("12.35");
		System.out.println(value);
		value = (Float) con.convert(12356);
		System.out.println(value);
		value = (Float) con.convert(null);
		System.out.println(value);
		value = (Float) con.convert(12356L);
		System.out.println(value);
		value = (Float) con.convert(new BigDecimal("12356"));
		System.out.println(value);
		value = (Float) con.convert(new Date());
		System.out.println(value);
		value = (Float) con.convert(25.55);
		System.out.println(value);
		value = (Float)con.convert(TestEnum.password);
		System.out.println(value);	
	}

	@Test
	public void floatTypeConvertTest() {
		FloatTypeConvert con = new FloatTypeConvert();
		float value;

		value = (float)con.convert("2012-12-09");
		System.out.println(value);
		value = (float) con.convert("1994-02-17 12:55:19");
		System.out.println(value);
		value = (float) con.convert("1999年02月17日");
		System.out.println(value);
		value = (float) con.convert("1999年02月17日 23时54分16秒");
		System.out.println(value);
		value = (float) con.convert("2015/02/17");
		System.out.println(value);
		value = (float) con.convert("1999/02/17 23:54:16");
		System.out.println(value);
		value = (float) con.convert("12356");
		System.out.println(value);
		value = (float) con.convert("12.35");
		System.out.println(value);
		value = (float) con.convert(12356);
		System.out.println(value);
		value = (float) con.convert(null);
		System.out.println(value);
		value = (float) con.convert(12334L);
		System.out.println(value);
		value = (float) con.convert(new BigDecimal("12356"));
		System.out.println(value);
		value = (float) con.convert(new Date());
		System.out.println(value);
		value = (float) con.convert(25.55);
		System.out.println(value);
		value = (float)con.convert(TestEnum.password);
		System.out.println(value);	
	}

}
