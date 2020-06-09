package com.autumn.mybatis.annotation;

import com.autumn.mybatis.factory.DataSourceFactory;
import com.autumn.mybatis.factory.DefaultSinglePropertiesDataSourceFactory;
import com.autumn.mybatis.provider.AbstractProvider;
import com.autumn.mybatis.provider.DbProvider;
import com.autumn.mybatis.provider.ProviderDriveType;
import org.apache.ibatis.plugin.Interceptor;
import org.springframework.context.annotation.Import;

import java.lang.annotation.*;

/**
 * 启用 Autumn Mybatis
 *
 * @author 老码农
 * <p>
 * 2017-11-28 12:59:14
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.TYPE)
@Documented
@Import({AutumnMybatisSingleDataSourceRegistrar.class})
public @interface EnableAutumnMybatis {

    /**
     * MyBatis mapper Interface 的包集合
     *
     * @return
     */
    String[] value();

    /**
     * 实体(POJO)类型(与数据映射的实体)包路径
     *
     * @return
     */
    String[] typeAliasesPackages();

    /**
     * MyBatis mapper 自定义的 xml 配置位置
     *
     * @return
     */
    String[] mapperLocations() default {};

    /**
     * MyBatis configLocation 配置文件位置
     *
     * @return
     * @author 老码农 2017-12-04 16:19:09
     */
    String configLocation() default "";

    /**
     * 提供者类型(AUTO 表示根据连接窜自动查找)
     *
     * @return
     */
    ProviderDriveType driveType() default ProviderDriveType.AUTO;

    /**
     * 提供者的类型（AbstractProvider 表示根据 driveType 自动确定)
     *
     * @return
     */
    Class<? extends DbProvider> providerType() default AbstractProvider.class;

    /**
     * 插件类型集
     *
     * @return 2017-12-08 16:53:39
     */
    Class<? extends Interceptor>[] pluginTypes() default {};

    /**
     * 数据源工厂类型
     *
     * @return 无构造的 Bean 类型
     */
    Class<? extends DataSourceFactory> dataSourceFactoryType() default DefaultSinglePropertiesDataSourceFactory.class;

    /**
     * 数据源工厂 Bean 名称，如果指定了 Bean 名称则会自动获绑定指定的 Bean
     *
     * @return
     */
    String dataSourceFactoryBeanName() default "";

}
