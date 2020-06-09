package com.autumn.mybatis.annotation;

import com.autumn.mybatis.BeanConstant;
import com.autumn.mybatis.factory.DataSourceFactory;
import com.autumn.mybatis.factory.DynamicDataSourceRouting;
import com.autumn.mybatis.provider.AbstractProvider;
import com.autumn.mybatis.provider.DbProvider;
import com.autumn.mybatis.provider.ProviderDriveType;
import org.apache.ibatis.plugin.Interceptor;
import org.springframework.context.annotation.Import;

import java.lang.annotation.*;

/**
 * 启动 AutumnMybatis 动态数据源
 * <p>
 * 指数据源来源不明确，需要自动拼接或动态生成。
 * </p>
 *
 * @author 老码农
 * <p>
 * 2017-12-29 15:52:54
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.TYPE)
@Documented
@Import({AutumnMybatisDynamicDataSourceRegistrar.class})
public @interface EnableAutumnMybatisDynamicDataSource {

    /**
     * MyBatis mapper Interface 的包集合
     *
     * @return
     */
    String[] value();

    /**
     * 是否为默认(非默认时，在事务需要明确事务Bean)
     *
     * @return
     */
    boolean primary() default true;

    /**
     * 实体(POJO)类型(与数据映射的实体)包路径
     *
     * @return
     */
    String[] typeAliasesPackages();

    /**
     * 名称
     *
     * @return
     */
    String name() default BeanConstant.Dynamic.DEFAULT_DYNAMIC_NAME_PREFIX;

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
     * 提供者类型
     *
     * @return
     */
    ProviderDriveType driveType();

    /**
     * 提供者的类型
     * <p>
     * 如果配置为 AbstractProvider 则自动根据 driveType 识别，而配置为实现类则会根据配置类型
     * </p>
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

    /**
     * 数据源路由类型
     *
     * @return 4个构参数的 Bean 类型，继承 {@link com.autumn.mybatis.factory.AbstractDynamicRoutingKeyDataSource}
     */
    Class<? extends DynamicDataSourceRouting> dataSourceRoutingType();

    /**
     * 数据源工厂类型
     *
     * @return 无构造的 Bean 类型
     */
    Class<? extends DataSourceFactory> dataSourceFactoryType();

    /**
     * 数据源工厂 Bean 名称，如果指定了 Bean 名称则会自动获绑定指定的 Bean
     *
     * @return
     */
    String dataSourceFactoryBeanName() default "";

}
