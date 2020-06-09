package com.autumn.mybatis.annotation;

import com.autumn.mybatis.provider.AbstractProvider;
import com.autumn.mybatis.provider.DbProvider;
import com.autumn.mybatis.provider.ProviderDriveType;
import org.apache.ibatis.plugin.Interceptor;

import java.lang.annotation.Documented;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * AutumnMybatis 多数据源
 *
 * @author 老码农
 * <p>
 * 2017-11-30 10:41:00
 */
@Documented
@Retention(RetentionPolicy.RUNTIME)
@Target({})
public @interface AutumnMybatisMultipleDataSource {

    /**
     * 名称(对应数据源名称或标识)
     *
     * @return
     */
    String name();

    /**
     * 是否为默认，即当一个接口多个实现时，是否为默认注入
     *
     * @return
     */
    boolean primary() default false;

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
     * @return 2017-12-08 16:52:17
     */
    Class<? extends DbProvider> providerType() default AbstractProvider.class;

    /**
     * 插件类型集
     *
     * @return 2017-12-08 16:53:39
     */
    Class<? extends Interceptor>[] pluginTypes() default {};
}
