package com.autumn.mybatis.factory;

/**
 * 默认使用多属性(autumn.datasource.multiple)数据源工厂
 *
 * @author 老码农
 * <p>
 * Description
 * </p>
 * @date 2017-12-30 17:34:22
 */
public class DefaultMultiplePropertiesDataSourceFactory extends AbstractDynamicPropertiesDataSourceFactory {

    /**
     * 默认属性前缀
     */
    public final static String DEFAULT_PROPERTIES_PREFIX = "autumn.datasource.multiple";

    /**
     *
     */
    public DefaultMultiplePropertiesDataSourceFactory() {
        this(DEFAULT_PROPERTIES_PREFIX);
    }

    /**
     * @param propertiesPrefix 属性前缀
     */
    protected DefaultMultiplePropertiesDataSourceFactory(String propertiesPrefix) {
        super(propertiesPrefix);
    }
}
