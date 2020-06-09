package com.autumn.util;

import java.io.UnsupportedEncodingException;

import org.junit.Test;

import com.autumn.util.security.HashUtils;

/**
 * 
 * @author 老码农
 *
 * 2017-12-14 10:09:13
 */
public class HashUtilsTest {

	/**
	 * 
	* 
	* 2017-12-14 10:17:54
	 * @throws UnsupportedEncodingException 
	 */
	@Test
	public void md5Test() throws UnsupportedEncodingException {
		String value = HashUtils.md5("abc");
		System.out.println(value);
	}
	
}
