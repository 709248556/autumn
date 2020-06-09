package com.autumn.util.reflect;

import java.lang.reflect.Field;
import java.util.Map;

import org.junit.Test;

import com.autumn.util.data.TestUserInfo2;

/**
 * 
 * @author 老码农
 *
 *         2017-11-15 17:47:43
 */
public class TestUserInfoTest {

	@Test
	public void test1() {

		Map<String, BeanProperty> bp = ReflectUtils.getBeanPropertyMap(TestUserInfo2.class);

		//TestUserInfo2 u = new TestUserInfo2();
		
		Field[] fi= TestUserInfo2.class.getSuperclass().getDeclaredFields();

		System.out.println(fi.length);

		System.out.println(bp.size());
	}

}
