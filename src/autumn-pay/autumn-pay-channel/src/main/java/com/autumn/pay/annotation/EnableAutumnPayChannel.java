package com.autumn.pay.annotation;

import com.autumn.pay.configure.PayChannelAutoConfiguration;
import org.springframework.context.annotation.Import;

import java.lang.annotation.*;

/**
 * 启用交易通道
 *
 * @Description TODO
 * @Author 老码农
 * @Date 2019-09-16 1:04
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.TYPE)
@Documented
@Import({PayChannelAutoConfiguration.class})
public @interface EnableAutumnPayChannel {
}
