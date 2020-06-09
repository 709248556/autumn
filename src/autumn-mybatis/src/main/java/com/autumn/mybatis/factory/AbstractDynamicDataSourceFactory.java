package com.autumn.mybatis.factory;

import com.autumn.exception.ExceptionUtils;
import com.autumn.mybatis.provider.DbProvider;
import com.autumn.mybatis.provider.ProviderDriveType;
import com.autumn.util.StringUtils;
import com.autumn.util.TypeUtils;
import com.autumn.util.reflect.ReflectUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import javax.sql.DataSource;
import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;

/**
 * 动态属性数据源抽象
 *
 * @author 老码农
 * <p>
 * Description
 * </p>
 * @date 2017-12-30 10:35:52
 */
public abstract class AbstractDynamicDataSourceFactory implements DataSourceFactory {

    /**
     * 日志
     */
    protected final Log logger = LogFactory.getLog(this.getClass());

    /**
     * 默认连接池
     * {@link com.alibaba.druid.pool.DruidDataSource}
     */
    public static final String DATA_SOURCE_TYPE_DEFAULT = "com.alibaba.druid.pool.DruidDataSource";

    /**
     * 创建数据源
     *
     * @param dataSourcePropertiesMap 数据源属性Map
     * @param provider                驱动提供者
     * @return
     */
    @SuppressWarnings("unchecked")
    protected DataSource createDataSource(Map<String, Object> dataSourcePropertiesMap, DbProvider provider) {
        Map<String, Object> map = new HashMap<>(dataSourcePropertiesMap.size());
        for (Map.Entry<String, Object> entry : dataSourcePropertiesMap.entrySet()) {
            if (entry.getKey() != null) {
                map.put(StringUtils.configurePropertieName(entry.getKey()), entry.getValue());
            }
        }
        //池转换
        Object pool = map.get("pool");
        if (pool instanceof Map) {
            Map poolMap = (Map) pool;
            for (Object key : poolMap.keySet()) {
                if (key != null) {
                    Object value = poolMap.get(key);
                    if (value != null) {
                        map.put(StringUtils.configurePropertieName(key.toString()), value);
                    }
                }
            }
        }
        Object url = map.get("url");
        if (url == null || StringUtils.isNullOrBlank(url.toString().trim())) {
            throw ExceptionUtils.throwConfigureException("数据源未配置  url 属性");
        }
        ProviderDriveType driveType = ProviderDriveType.getDriveTypeByUrl(url.toString());
        Object type = map.get("type");
        if (type == null || StringUtils.isNullOrBlank(type.toString().trim())) {
            type = DATA_SOURCE_TYPE_DEFAULT;
        }
        String driverClass = this.getDriverClass(map, driveType);
        try {
            Class<? extends DataSource> dataSourceType = (Class<? extends DataSource>) Class.forName((String) type);
            DataSource ds = dataSourceType.newInstance();
            map.remove("type");
            map.put("driverClassName", driverClass);
            Map<String, Method> methodMap = ReflectUtils.findPropertiesSetMethodMap(dataSourceType);
            for (Map.Entry<String, Object> entry : map.entrySet()) {
                Method setMethod = methodMap.get(entry.getKey());
                if (entry.getValue() != null && setMethod != null) {
                    String value = entry.getValue().toString().trim();
                    if (!StringUtils.isNullOrBlank(value)) {
                        setMethod.invoke(ds, TypeUtils.toConvert(setMethod.getParameterTypes()[0], value));
                    }
                }
            }
            return ds;
        } catch (Exception e) {
            throw ExceptionUtils.throwConfigureException(e.getMessage(), e);
        }
    }

    /**
     * 查找提供者
     *
     * @param map
     * @param driveType
     * @return
     */
    protected String getDriverClass(Map<String, Object> map, ProviderDriveType driveType) {
        Object driverClass = map.get("driverClassName");
        if (!this.existDriverValue(driverClass)) {
            driverClass = map.get("driver");
        }
        if (!this.existDriverValue(driverClass)) {
            driverClass = map.get("driverClass");
        }
        if (!this.existDriverValue(driverClass)) {
            if (driveType.equals(ProviderDriveType.CUSTOM)) {
                throw ExceptionUtils.throwConfigureException("DbProvider 为 CUSTOM 时必须配置连接的 driverClassName 的驱动提供者。");
            }
            if (StringUtils.isNullOrBlank(driveType.getDriveClassName())) {
                throw ExceptionUtils.throwConfigureException("DbProvider 为 " + driveType.toString() + " 时,无默认 driverClassName 的值，必须配置连接的 driverClassName 的驱动提供者。");
            }
            driverClass = driveType.getDriveClassName();
        }
        String driverType = driverClass.toString().trim();
        if (!TypeUtils.isExistClassName(driverType)) {
            throw ExceptionUtils.throwConfigureException("未搜索到 " + driverType + " 的驱动提供者。");
        }
        return driverType;
    }

    private boolean existDriverValue(Object driverClass) {
        return driverClass != null && StringUtils.isNotNullOrBlank(driverClass.toString());
    }
}
