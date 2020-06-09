package com.autumn.mybatis.factory;

import com.autumn.exception.ExceptionUtils;
import com.autumn.mybatis.provider.DbProvider;
import com.autumn.spring.boot.bind.RelaxedPropertyResolver;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;

import javax.sql.DataSource;
import java.util.Map;

/**
 * 默认单数据源属性数据源工厂
 *
 * @author 老码农
 * <p>
 * Description
 * </p>
 * @date 2017-12-30 11:40:19
 */
public class DefaultSinglePropertiesDataSourceFactory extends AbstractDynamicDataSourceFactory {

    /**
     * 默认属性前缀
     */
    public final static String DEFAULT_PROPERTIES_PREFIX = "spring.datasource";

    private DataSource dataSource = null;

    @Autowired
    private Environment env;

    private final String propertiesPrefix;

    /**
     *
     */
    public DefaultSinglePropertiesDataSourceFactory() {
        this(DEFAULT_PROPERTIES_PREFIX);
    }

    /**
     * @param propertiesPrefix 属性前缀
     */
    protected DefaultSinglePropertiesDataSourceFactory(String propertiesPrefix) {
        this.propertiesPrefix = ExceptionUtils.checkNotNullOrBlank(propertiesPrefix, "propertiesPrefix");
    }

    @Override
    public DataSource getDataSource(Object routingKey, DbProvider provider) {
        if (this.dataSource != null) {
            return this.dataSource;
        }
        synchronized (this) {
            if (this.dataSource != null) {
                return this.dataSource;
            }
            Map<String, Object> propertiesMap = new RelaxedPropertyResolver(this.env, this.propertiesPrefix).getSubProperties(".");
            this.dataSource = this.createDataSource(propertiesMap, provider);
            return this.dataSource;
        }
    }
}
