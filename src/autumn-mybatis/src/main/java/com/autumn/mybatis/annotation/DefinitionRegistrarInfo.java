package com.autumn.mybatis.annotation;

import com.autumn.mybatis.factory.DataSourceFactory;
import com.autumn.mybatis.factory.DynamicDataSourceRouting;
import com.autumn.mybatis.provider.DbProvider;
import com.autumn.mybatis.provider.ProviderDriveType;
import org.apache.ibatis.plugin.Interceptor;
import org.springframework.core.io.Resource;

/**
 * 配置注册信息
 *
 * @author 老码农
 * <p>
 * Description
 * </p>
 * @date 2017-12-03 17:09:33
 */
class DefinitionRegistrarInfo extends RegistrarDefaultMybatisInfo {

    private static final long serialVersionUID = -2408554698639980818L;

    private String name;
    private String dataSourceBeanName;
    private String dataSourceFactoryBeanName;
    private String transactionManagerBeanName;
    private String sqlSessionFactoryBeanName;
    private String sqlSessionTemplateBeanName;
    private String providerBeanName;
    private String mapperRegisterBeanName;
    private ProviderDriveType driveType;
    private Class<? extends DbProvider> providerType;
    private Resource[] mapperLocations;
    private Resource configLocation;
    private Class<? extends DataSourceFactory> dataSourceFactoryType;
    private Class<? extends DynamicDataSourceRouting> dataSourceRoutingType;

    private boolean primary;
    private Class<? extends Interceptor>[] pluginTypes;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDataSourceBeanName() {
        return dataSourceBeanName;
    }

    public void setDataSourceBeanName(String dataSourceBeanName) {
        this.dataSourceBeanName = dataSourceBeanName;
    }

    public String getTransactionManagerBeanName() {
        return transactionManagerBeanName;
    }

    public void setTransactionManagerBeanName(String transactionManagerBeanName) {
        this.transactionManagerBeanName = transactionManagerBeanName;
    }

    public String getSqlSessionFactoryBeanName() {
        return sqlSessionFactoryBeanName;
    }

    public void setSqlSessionFactoryBeanName(String sqlSessionFactoryBeanName) {
        this.sqlSessionFactoryBeanName = sqlSessionFactoryBeanName;
    }

    public String getSqlSessionTemplateBeanName() {
        return sqlSessionTemplateBeanName;
    }

    public void setSqlSessionTemplateBeanName(String sqlSessionTemplateBeanName) {
        this.sqlSessionTemplateBeanName = sqlSessionTemplateBeanName;
    }

    public String getProviderBeanName() {
        return providerBeanName;
    }

    public void setProviderBeanName(String providerBeanName) {
        this.providerBeanName = providerBeanName;
    }

    public String getMapperRegisterBeanName() {
        return mapperRegisterBeanName;
    }

    public void setMapperRegisterBeanName(String mapperRegisterBeanName) {
        this.mapperRegisterBeanName = mapperRegisterBeanName;
    }

    /**
     * 获取驱动类型
     *
     * @return
     */
    public ProviderDriveType getDriveType() {
        return driveType;
    }

    /**
     * 设置驱动类型
     *
     * @param driveType 驱动类型
     */
    public void setDriveType(ProviderDriveType driveType) {
        this.driveType = driveType;
    }

    /**
     * 获取提供者类型
     *
     * @return 2017-12-08 16:55:42
     */
    public Class<? extends DbProvider> getProviderType() {
        return providerType;
    }

    /**
     * 设置提供者类型
     *
     * @param providerType 驱动类型 2017-12-08 16:55:51
     */
    public void setProviderType(Class<? extends DbProvider> providerType) {
        this.providerType = providerType;
    }

    public boolean isPrimary() {
        return primary;
    }

    public void setPrimary(boolean primary) {
        this.primary = primary;
    }

    /**
     * 获取插件类型集
     *
     * @return 2017-12-08 18:25:28
     */
    public Class<? extends Interceptor>[] getPluginTypes() {
        return pluginTypes;
    }

    /**
     * 设置插件类型集
     *
     * @param pluginTypes 插件类型集 2017-12-08 18:25:49
     */
    public void setPluginTypes(Class<? extends Interceptor>[] pluginTypes) {
        this.pluginTypes = pluginTypes;
    }

    public Resource[] getMapperLocations() {
        return mapperLocations;
    }

    public void setMapperLocations(Resource[] mapperLocations) {
        this.mapperLocations = mapperLocations;
    }

    public Resource getConfigLocation() {
        return configLocation;
    }

    public void setConfigLocation(Resource configLocation) {
        this.configLocation = configLocation;
    }

    public String getDataSourceFactoryBeanName() {
        return dataSourceFactoryBeanName;
    }

    public void setDataSourceFactoryBeanName(String dataSourceFactoryBeanName) {
        this.dataSourceFactoryBeanName = dataSourceFactoryBeanName;
    }

    public Class<? extends DataSourceFactory> getDataSourceFactoryType() {
        return dataSourceFactoryType;
    }

    public void setDataSourceFactoryType(Class<? extends DataSourceFactory> dataSourceFactoryType) {
        this.dataSourceFactoryType = dataSourceFactoryType;
    }

    public Class<? extends DynamicDataSourceRouting> getDataSourceRoutingType() {
        return dataSourceRoutingType;
    }

    public void setDataSourceRoutingType(Class<? extends DynamicDataSourceRouting> dataSourceRoutingType) {
        this.dataSourceRoutingType = dataSourceRoutingType;
    }
}
