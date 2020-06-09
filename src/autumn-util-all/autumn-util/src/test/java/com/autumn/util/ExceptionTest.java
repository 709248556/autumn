package com.autumn.util;

import com.autumn.exception.ErrorLevel;
import com.autumn.exception.ExceptionUtils;
import org.junit.Test;

/**
 * 异常测试
 * 
 * @author 老码农
 *
 *         2017-10-09 17:48:11
 */
public class ExceptionTest {

	@Test
	public void throwAutumnExceptionTest() {
		ExceptionUtils.throwAutumnException("中国%s", 20, ErrorLevel.APPLICATION);
	}

}
