package com.autumn.util.convert;

import java.math.BigDecimal;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

import org.junit.Test;

import com.autumn.util.TestEnum;
import com.autumn.util.convert.LongConvert;
import com.autumn.util.convert.LongTypeConvert;

/**
 * 
 * @author 老码农
 *
 * 2017-12-06 12:32:33
 */
public class LongTest {

	@Test
	public void longConvertTest() throws ParseException {
		
		LongConvert con = new LongConvert();
		String dt="Thu May 28 18:23:17 CST 2015";
		SimpleDateFormat sdf1= new SimpleDateFormat("EEE MMM dd HH:mm:ss z yyyy", Locale.ENGLISH);
		SimpleDateFormat sdf2= new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

		System.out.println(sdf2.format(sdf1.parse(dt)));  
		Long value = (Long) con.convert("10.10");
		System.out.println(value);
		value = (Long)con.convert("2012-12-09");
		System.out.println(value);
		value = (Long) con.convert("1994-02-17 12:55:19");
		System.out.println(value);
		value = (Long) con.convert("1999年02月17日");
		System.out.println(value);
		value = (Long) con.convert("1999年02月17日 23时54分16秒");
		System.out.println(value);
		value = (Long) con.convert("2015/02/17");
		System.out.println(value);
		value = (Long) con.convert("1999/02/17 23:54:16");
		System.out.println(value);
		value = (Long) con.convert("12.35");
		System.out.println(value);
		value = (Long) con.convert(12356);
		System.out.println(value);
		value = (Long) con.convert(null);
		System.out.println(value);
		value = (Long) con.convert(12336L);
		System.out.println(value);
		value = (Long) con.convert(new BigDecimal("12356"));
		System.out.println(value);
		value = (Long) con.convert(new Date());
		System.out.println(value);
		value = (Long) con.convert(25.55);
		System.out.println(value);
		value = (Long)con.convert(TestEnum.password);
		System.out.println(value);	
//		
//		System.out.println(DataConvertUtils.targetConvert(Long.class, "123.23"));	
//		System.out.println(DataConvertUtils.targetConvert(Long.class, null));	
//		System.out.println(DataConvertUtils.targetConvert(Long.TYPE, null));	
	}

	@Test
	public void longTypeConvertTest() {
		LongTypeConvert con = new LongTypeConvert();
		long value;

//		value = (long)con.convert("2012-12-09");
//		System.out.println(value);
//		value = (long) con.convert("1994-02-17 12:55:19");
//		System.out.println(value);
//		value = (long) con.convert("1999年02月17日");
//		System.out.println(value);
//		value = (long) con.convert("1999年02月17日 23时54分16秒");
//		System.out.println(value);
//		value = (long) con.convert("2015/02/17");
//		System.out.println(value);
//		value = (long) con.convert("1999/02/17 23:54:16");
//		System.out.println(value);
//		value = (long) con.convert("12356");
//		System.out.println(value);
//		value = (Long) con.convert("12.35");
//		System.out.println(value);
		value = (long) con.convert(12356);
		System.out.println(value);
		value = (Long) con.convert(null);
		System.out.println(value);
		value = (long) con.convert(12356L);
		System.out.println(value);
		value = (long) con.convert(new BigDecimal("12356"));
		System.out.println(value);
		value = (long) con.convert(new Date());
		System.out.println(value);
		value = (long) con.convert(25.55);
		System.out.println(value);
		value = (long)con.convert(TestEnum.password);
		System.out.println(value);	
	}

}
