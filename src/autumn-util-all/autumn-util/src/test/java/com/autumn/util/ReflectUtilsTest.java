package com.autumn.util;

import java.util.List;

import org.junit.Test;

import com.autumn.util.data.User2;
import com.autumn.util.data.UserInfo;
import com.autumn.util.reflect.BeanProperty;
import com.autumn.util.reflect.ReflectUtils;

/**
 * 
 * @author 老码农
 *
 * 2017-12-06 12:31:52
 */
public class ReflectUtilsTest {

	@Test
	public void test1() {
		List<BeanProperty> ps = ReflectUtils.findBeanPropertys(UserInfo.class);

		System.out.println(ps.size());

	}

	@Test
	public void test2() {
		List<BeanProperty> ps = ReflectUtils.findBeanPropertys(User2.class);
		User2 u = new User2();
		ps.get(1).setValue(u, 520);

		System.out.println(ps.size());
		System.out.println(u.getId());

	}

}
