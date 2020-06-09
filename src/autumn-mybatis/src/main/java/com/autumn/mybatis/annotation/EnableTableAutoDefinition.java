package com.autumn.mybatis.annotation;

import com.autumn.mybatis.configure.TableAutoDefinitionConfiguration;
import org.springframework.context.annotation.Import;

import java.lang.annotation.*;

/**
 * 启用表自动定义
 * <p>
 * 建议在开发阶段启用，在生产环境禁用
 * </p>
 *
 * @description TODO
 * @author: 老码农
 * @create: 2020-04-05 14:23
 **/
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.TYPE)
@Documented
@Import({TableAutoDefinitionConfiguration.class})
public @interface EnableTableAutoDefinition {

}
