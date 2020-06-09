package com.autumn.util;

import com.autumn.util.automap.model.UserInfo;
import com.autumn.validation.ValidationUtils;
import org.junit.Test;

/**
 * TODO
 *
 * @Description TODO
 * @Author 老码农
 * @Date 2019-08-15 5:36
 */
public class ValidationTest {

    @Test
    public void test1() {
        UserInfo userInfo = new UserInfo();
        ValidationUtils.validation(userInfo);
    }
}
