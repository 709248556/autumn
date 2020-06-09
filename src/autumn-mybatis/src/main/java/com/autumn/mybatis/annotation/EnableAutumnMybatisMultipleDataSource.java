package com.autumn.mybatis.annotation;

import com.autumn.mybatis.factory.DataSourceFactory;
import com.autumn.mybatis.factory.DefaultMultiplePropertiesDataSourceFactory;
import org.springframework.context.annotation.Import;

import java.lang.annotation.*;

/**
 * 启用 AutumnMybatis 多数据源
 *
 * @author 老码农
 * <p>
 * 2017-11-30 12:21:40
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.TYPE)
@Documented
@Import({AutumnMybatisMultipleDataSourceRegistrar.class})
public @interface EnableAutumnMybatisMultipleDataSource {

    /**
     * 数据源配置
     *
     * @return
     */
    AutumnMybatisMultipleDataSource[] value();

    /**
     * 数据源工厂类型
     *
     * @return 无构造的 Bean 类型
     */
    Class<? extends DataSourceFactory> dataSourceFactoryType() default DefaultMultiplePropertiesDataSourceFactory.class;

    /**
     * 数据源工厂 Bean 名称，如果指定了 Bean 名称则会自动获绑定指定的 Bean
     *
     * @return
     */
    String dataSourceFactoryBeanName() default "";

}
