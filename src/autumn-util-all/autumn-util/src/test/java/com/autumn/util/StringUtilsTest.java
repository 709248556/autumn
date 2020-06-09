package com.autumn.util;

import java.math.BigDecimal;

import org.junit.Assert;
import org.junit.Test;

/**
 * 字符工具测试
 * 
 * @author 老码农
 *
 *         2017-09-28 12:03:27
 */
public class StringUtilsTest {

	@Test
	public void configurePropertieNameTest() {

		String value = StringUtils.configurePropertieName("driver-class-name");
		System.out.println(value);
		org.junit.Assert.assertEquals(value, "driverClassName");

		value = StringUtils.configurePropertieName("Driver-class-name");
		System.out.println(value);
		org.junit.Assert.assertEquals(value, "driverClassName");

		value = StringUtils.configurePropertieName("type");
		System.out.println(value);
		org.junit.Assert.assertEquals(value, "type");

		value = StringUtils.configurePropertieName("Type");
		System.out.println(value);
		org.junit.Assert.assertEquals(value, "type");
	}

	@Test
	public void removeALLWhitespaceTest() {
		String value = StringUtils.removeALLWhitespace("a b c");
		System.out.println(value);
		value = StringUtils.removeALLWhitespace("a\r\n b c");
		System.out.println(value);
		value = StringUtils.removeALLWhitespace("a b	c");
		System.out.println(value);
	}

	@Test
	public void columnStandardCapitalizeTest() {

		String value = StringUtils.dbStandardCapitalize("userName");
		System.out.println(value);
		org.junit.Assert.assertEquals(value, "user_name");

		value = StringUtils.dbStandardCapitalize("UserName");
		System.out.println(value);
		org.junit.Assert.assertEquals(value, "user_name");

		value = StringUtils.dbStandardCapitalize("isDelete");
		System.out.println(value);
		org.junit.Assert.assertEquals(value, "is_delete");

		value = StringUtils.dbStandardCapitalize("ABC");
		System.out.println(value);
		org.junit.Assert.assertEquals(value, "a_b_c");
	}

	@Test
	public void columnStandardCapitalizeTest1() {

		String value = StringUtils.dbStandardCapitalize("DynamicMonthInterestRate");
		System.out.println(value);

	}

	@Test
	public void padLeftTest() {

		String value = StringUtils.padLeft("A", 5, '0');

		System.out.println(value);

	}

	@Test
	public void padRightTest() {

		String value = StringUtils.padRight("A", 5, '0');

		System.out.println(value);

	}

	@Test
	public void encodeBase64Test() {
		String source = "guinong";

		System.out.println("source:" + source);

		String value = StringUtils.encodeBase64Utf8String(source);

		System.out.println("Base64 encode:" + value);

		System.out.println(value);

		value = StringUtils.decodeBase64Utf8String(value);

		System.out.println("Base64 decode:" + value);
	}

	@Test
	public void encodeBase64Test2() {
		String source = "Hello World";

		System.out.println("source:" + source);
		String value = StringUtils.encodeBase64Utf8String(source);

		System.out.println("Base64 encode:" + value);
		byte[] f = StringUtils.decodeBase64Utf8(value);

		String ab = StringUtils.getStringUtf8(f);
		System.out.println(ab);

		ab = StringUtils.encodeBase64Utf8String(StringUtils.getStringUtf8(f));
		System.out.println(ab);

		value = StringUtils.decodeBase64Utf8String(value);
		System.out.println("Base64 decode:" + value);
	}

	@Test
	public void rmbTest() {
		BigDecimal number = new BigDecimal("8624267000534135.10");
		String value = BigDecimalUtils.toRmb(number);
		Assert.assertEquals(value, "捌仟陆佰贰拾肆兆贰仟陆佰柒拾亿零伍拾叁万肆仟壹佰叁拾伍元壹角零分");
		System.out.println(value);

		number = new BigDecimal("98000000000000.30");
		value = BigDecimalUtils.toRmb(number);
		Assert.assertEquals(value, "玖拾捌兆元零叁角零分");
		System.out.println(value);

		number = new BigDecimal("0.30");
		value = BigDecimalUtils.toRmb(number);
		Assert.assertEquals(value, "叁角零分");
		System.out.println(value);

		number = new BigDecimal("1.00");
		value = BigDecimalUtils.toRmb(number);
		Assert.assertEquals(value, "壹元整");
		System.out.println(value);

		number = new BigDecimal("15868.00");
		value = BigDecimalUtils.toRmb(number);
		Assert.assertEquals(value, "壹万伍仟捌佰陆拾捌元整");
		System.out.println(value);

		number = new BigDecimal("100001.10");
		value = BigDecimalUtils.toRmb(number);
		Assert.assertEquals(value, "壹拾万零壹元壹角零分");
		System.out.println(value);

		number = new BigDecimal("100001.01");
		value = BigDecimalUtils.toRmb(number);
		// Assert.assertEquals(value, "壹拾万零壹元壹角零分");
		System.out.println(value);

		double money = 2020004.015;

		number = new BigDecimal(money);
		value = BigDecimalUtils.toRmb(number);
		// Assert.assertEquals(value, "壹拾万零壹元壹角零分");
		System.out.println(value);
	}

	@Test
	public void removeTest() {
		String value = "///aaaa/b/c";
		System.out.println(StringUtils.removeStart(value, '/'));
		value="sss/s//////";
		System.out.println(StringUtils.removeEnd(value, '/'));
	}

}
