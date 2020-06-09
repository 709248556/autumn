package com.autumn.util;

import java.nio.charset.StandardCharsets;

import org.junit.Test;

/**
 * 
 * @author 老码农 2018-08-20 15:21:43
 */
public class Base64Test {

	@Test
	public void test1() {
		String str = "中华人民共和国";

		String result = Base64Utils.encodeToString(str, StandardCharsets.UTF_8);

		System.out.println(result);
		
		
		
		byte[] value = Base64Utils.decodeFromString(result, StandardCharsets.UTF_8);

		String v2 = new String(value, StandardCharsets.UTF_8);

		System.out.println(v2);
		
		byte[] v5 = Base64Utils.encode(str.getBytes(StandardCharsets.UTF_8));
		System.out.println(new String(v5,StandardCharsets.UTF_8));
		
		
	}
}
