package com.autumn.mybatis.factory;

import com.autumn.mybatis.provider.DbProvider;
import com.autumn.spring.boot.bind.RelaxedPropertyResolver;
import com.autumn.exception.ExceptionUtils;
import com.autumn.util.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;

import javax.sql.DataSource;
import java.util.Map;

/**
 * 动态属性数据源工厂实现
 *
 * @author 老码农
 * <p>
 * Description
 * </p>
 * @date 2017-12-30 00:17:34
 */
public abstract class AbstractDynamicPropertiesDataSourceFactory extends AbstractDynamicDataSourceFactory {

    @Autowired
    private Environment env;

    private final String propertiesPrefix;
    private Map<String, Map<String, Object>> propertiesMap;

    public AbstractDynamicPropertiesDataSourceFactory(String propertiesPrefix) {
        this.propertiesPrefix = ExceptionUtils.checkNotNullOrBlank(propertiesPrefix, "propertiesPrefix");
        this.propertiesMap = null;
    }

    /**
     * Map 属性转换
     *
     * @param mapProperties 原属性
     * @return 将带有-符合去掉，并返回新的 Map
     */
    public static Map<String, Map<String, Object>> toPropertiesMapConvert(Map<String, Object> mapProperties) {
        Map<String, Map<String, Object>> result = CollectionUtils.newHashMap();
        for (Map.Entry<String, Object> entry : mapProperties.entrySet()) {
            Object value = entry.getValue();
            String key = entry.getKey();
            String[] keys = key.split("\\.");
            if (keys.length > 1) {
                key = keys[0];
                Map<String, Object> itemMap = result.computeIfAbsent(key, k -> CollectionUtils.newHashMap());
                StringBuilder itemKey = new StringBuilder();
                for (int i = 1; i < keys.length; i++) {
                    if (i > 1) {
                        itemKey.append(".");
                    }
                    itemKey.append(keys[i]);
                }
                itemMap.put(itemKey.toString(), value);
            }
        }
        return result;
    }

    /**
     * 获取属性
     *
     * @return
     */
    private Map<String, Map<String, Object>> getPropertiesMap() {
        if (this.propertiesMap != null) {
            return this.propertiesMap;
        }
        synchronized (this) {
            if (this.propertiesMap != null) {
                return this.propertiesMap;
            }
            this.propertiesMap = toPropertiesMapConvert(
                    new RelaxedPropertyResolver(this.env, this.propertiesPrefix).getSubProperties("."));
            return this.propertiesMap;
        }
    }

    @Override
    public DataSource getDataSource(Object routingKey, DbProvider provider) {
        Map<String, Map<String, Object>> map = getPropertiesMap();
        Map<String, Object> result = map.get(routingKey);
        if (result == null) {
            return null;
        }
        return createDataSource(result, provider);
    }

}
