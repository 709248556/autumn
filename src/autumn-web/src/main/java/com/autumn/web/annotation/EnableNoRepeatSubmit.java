package com.autumn.web.annotation;

import com.autumn.redis.annotation.EnableAutumnRedis;
import com.autumn.web.configure.AutumnWebAutoConfiguration;
import com.autumn.web.configure.NoRepeatSubmitConfiguration;
import org.springframework.context.annotation.Import;

import java.lang.annotation.*;

/**
 * 启用防止重复提交
 * <p>
 * </p>
 *
 * @description: TODO
 * @author: 老码农
 * @create: 2019-11-14 20:53
 **/
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.TYPE)
@EnableAutumnRedis
@Documented
@Import({ NoRepeatSubmitConfiguration.class, AutumnWebAutoConfiguration.class })
public @interface EnableNoRepeatSubmit {

}
