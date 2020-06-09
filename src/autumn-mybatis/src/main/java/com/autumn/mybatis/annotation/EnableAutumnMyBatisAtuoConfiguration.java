package com.autumn.mybatis.annotation;

import com.autumn.mybatis.configure.AutumntMyBatisAutoConfiguration;
import org.springframework.context.annotation.Import;

import java.lang.annotation.*;

/**
 * 启用 AutumnMyBatis 自动配置
 *
 * @Description TODO
 * @Author 老码农
 * @Date 2019-07-24 20:23
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.TYPE)
@Documented
@Import({AutumntMyBatisAutoConfiguration.class})
public @interface EnableAutumnMyBatisAtuoConfiguration {

}
