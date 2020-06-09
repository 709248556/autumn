package com.autumn.util.convert;

import java.math.BigDecimal;

import org.junit.Test;

import com.autumn.util.TestEnum;
import com.autumn.util.convert.CharacterConvert;
import com.autumn.util.convert.CharacterTypeConvert;

/**
 * 
 * @author 老码农
 *
 * 2017-12-06 12:32:09
 */
public class CharacterTest {
	@Test
	public void charConvertTest() {
		CharacterConvert con = new CharacterConvert();
		Character value;
		//超过范围报错
//		value = (Character) con.convert("243523");
//		System.out.println(value);
		//0-31/127为控制字符，
		value = (Character) con.convert("33");
		System.out.println(value);
		value = (Character) con.convert("99.23");
		System.out.println(value);
		value = (Character) con.convert(1236);
		System.out.println(value);
		value = (Character) con.convert(null);
		System.out.println(value);
		value = (Character) con.convert(12356L);
		System.out.println(value);
		value = (Character) con.convert(new BigDecimal("22345"));
		System.out.println(value);
		value = (Character) con.convert(69.55);
		System.out.println(value);
		value = (Character)con.convert(TestEnum.password);
		System.out.println(value);	
	}
	@Test
	public void cahrTypeConvertTest() {
		System.out.println('\u0000');
		CharacterTypeConvert con = new CharacterTypeConvert();
		char value;
//		value = (char) con.convert("1507515024679");
//		System.out.println(value);
		value = (char) con.convert("37");
		System.out.println(value);
		value = (Character) con.convert("49.23");
		System.out.println(value);
		value = (char) con.convert(1156);
		System.out.println(value);
		value = (char) con.convert(null);
		System.out.println(value);
		value = (char) con.convert(12356L);
		System.out.println(value);
		value = (char) con.convert(new BigDecimal("10356"));
		System.out.println(value);
//		value = (char) con.convert(new Date());
//		System.out.println(value);
		value = (char) con.convert(125.55);
		System.out.println(value);
		value = (char)con.convert(TestEnum.password);
		System.out.println(value);	
		
	}
}
