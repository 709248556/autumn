package com.autumn.util.convert;

import java.math.BigDecimal;
import java.util.Date;

import com.autumn.util.DateUtils;
import org.junit.Test;

import com.autumn.util.TestEnum;
import com.autumn.util.convert.DoubleConvert;
import com.autumn.util.convert.DoubleTypeConvert;

/**
 * 
 * @author 老码农
 *
 * 2017-12-06 12:32:16
 */
public class DoubleTest {
	@Test
	public void doubleConvertTest() {
		
//		System.out.println(System.currentTimeMillis());
		DoubleConvert con = new DoubleConvert();

		Double value = (Double) con.convert("12356");
		System.out.println(value);
		
		value = (Double)con.convert(DateUtils.parseDate("2012-12-09"));
		System.out.println(value);
		value = (Double) con.convert("1994-02-17 12:55:19");
		System.out.println(value);
		value = (Double) con.convert("1999年02月17日");
		System.out.println(value);
		value = (Double) con.convert("1999年02月17日 23时54分16秒");
		System.out.println(value);
		value = (Double) con.convert("2015/02/17");
		System.out.println(value);
		value = (Double) con.convert("1999/02/17 23:54:16");
		System.out.println(value);
		value = (Double) con.convert("12.35");
		System.out.println(value);
		value = (Double) con.convert(12356);
		System.out.println(value);
		value = (Double) con.convert(null);
		System.out.println(value);
		value = (Double) con.convert(12356L);
		System.out.println(value);
		value = (Double) con.convert(new BigDecimal("12356"));
		System.out.println(value);
		value = (Double) con.convert(new Date());
		System.out.println(value);
		value = (Double) con.convert(25.55);
		System.out.println(value);
		value = (Double)con.convert(TestEnum.password);
		System.out.println(value);	
	}

	@Test
	public void doubleTypeConvertTest() {
		DoubleTypeConvert con = new DoubleTypeConvert();
		double value;

		value = (double)con.convert("2012-12-09");
		System.out.println(value);
		value = (double) con.convert("1994-02-17 12:55:19");
		value = (double) con.convert("12356");
		System.out.println(value);
		value = (double) con.convert("1999年02月17日");
		System.out.println(value);
		value = (double) con.convert("1999年02月17日 23时54分16秒");
		System.out.println(value);
		value = (double) con.convert("2015/02/17");
		System.out.println(value);
		value = (double) con.convert("1999/02/17 23:54:16");
		System.out.println(value);
		value = (double) con.convert("12.35");
		System.out.println(value);
		value = (double) con.convert(12356);
		System.out.println(value);
		value = (double) con.convert(null);
		System.out.println(value);
		value = (double) con.convert(12356);
		System.out.println(value);
		value = (double) con.convert(new BigDecimal("12356"));
		System.out.println(value);
		value = (double) con.convert(new Date());
		System.out.println(value);
		value = (double) con.convert(25.55);
		System.out.println(value);
		value = (double)con.convert(TestEnum.password);
		System.out.println(value);	
	}
}
