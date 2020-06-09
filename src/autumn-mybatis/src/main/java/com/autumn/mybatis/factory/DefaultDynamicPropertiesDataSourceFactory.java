package com.autumn.mybatis.factory;

/**
 * 默认使用动态属性(autumn.datasource.dynamic)数据源工厂
 *
 * @author 老码农
 * <p>
 * Description
 * </p>
 * @date 2017-12-30 00:37:37
 */
public class DefaultDynamicPropertiesDataSourceFactory extends AbstractDynamicPropertiesDataSourceFactory {

    /**
     * 默认属性前缀
     */
    public final static String DEFAULT_PROPERTIES_PREFIX = "autumn.datasource.dynamic";

    /**
     *
     */
    public DefaultDynamicPropertiesDataSourceFactory() {
        this(DEFAULT_PROPERTIES_PREFIX);
    }

    /**
     * @param propertiesPrefix 属性前缀
     */
    protected DefaultDynamicPropertiesDataSourceFactory(String propertiesPrefix) {
        super(propertiesPrefix);
    }

}
