package com.autumn.logistics.annotation;

import com.autumn.logistics.configure.LogisticsChannelAutoConfiguration;
import org.springframework.context.annotation.Import;

import java.lang.annotation.*;

/**
 * 启用物流通道
 * <p>
 * </p>
 *
 * @description TODO
 * @author: 老码农
 * @create: 2020-03-02 00:44
 **/
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.TYPE)
@Documented
@Import({LogisticsChannelAutoConfiguration.class})
public @interface EnableAutumnLogisticsChannel {

}
