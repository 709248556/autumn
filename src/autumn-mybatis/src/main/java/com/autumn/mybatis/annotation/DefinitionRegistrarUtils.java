package com.autumn.mybatis.annotation;

import com.autumn.exception.ExceptionUtils;
import com.autumn.mybatis.BeanConstant;
import com.autumn.mybatis.factory.DynamicNameRoutingDataSource;
import com.autumn.mybatis.factory.DynamicSingleRoutingDataSource;
import com.autumn.mybatis.mapper.MapperRegister;
import com.autumn.mybatis.plugins.AbstractInterceptor;
import com.autumn.mybatis.provider.DbProvider;
import com.autumn.mybatis.provider.ProviderDriveType;
import com.autumn.mybatis.provider.ProviderUtils;
import com.autumn.mybatis.session.MybatisSqlSessionFactoryBean;
import com.autumn.spring.boot.bean.BeanRegisterManager;
import com.autumn.spring.boot.bind.RelaxedPropertyResolver;
import com.autumn.util.PackageUtils;
import com.autumn.util.TypeUtils;
import com.autumn.util.tuple.TupleTwo;
import org.apache.ibatis.plugin.Interceptor;
import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.beans.factory.config.RuntimeBeanReference;
import org.springframework.beans.factory.support.GenericBeanDefinition;
import org.springframework.beans.factory.support.ManagedArray;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.core.annotation.AnnotationAttributes;
import org.springframework.core.env.Environment;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;
import org.springframework.core.io.support.ResourcePatternResolver;
import org.springframework.core.type.AnnotationMetadata;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.util.StringUtils;

import java.io.IOException;
import java.util.*;

/**
 * Mapper 扫描注册帮助
 *
 * @author 老码农
 * <p>
 * Description
 * </p>
 * @date 2017-12-03 12:27:55
 */
class DefinitionRegistrarUtils {

    private static final String CLASSPATH_REPLACE = "classpath:";
    private static final String MYBATIS_PAHT_DEFAULT = "mybatis";
    private final static String ATTRIBUTE_DATA_SOURCE_ROUTING_TYPE = "dataSourceRoutingType";
    private final static String ATTRIBUTE_DATA_SOURCE_FACTORY_TYPE = "dataSourceFactoryType";
    private final static String ATTRIBUTE_DATA_SOURCE_FACTORY_BEAN_NAME = "dataSourceFactoryBeanName";

    /**
     * 注册单数据源
     *
     * @param definitionRegistrar 注册定义
     * @param info                信息
     * @param provider            驱动
     */
    public static void registerSingleDataSource(BeanRegisterManager definitionRegistrar,
                                                DefinitionRegistrarInfo info,
                                                TupleTwo<String, Class<? extends DbProvider>> provider) {
        info.setPrimary(true);
        RuntimeBeanReference ref;
        if (!com.autumn.util.StringUtils.isNullOrBlank(info.getDataSourceFactoryBeanName())) {
            // 数据源工厂
            ref = new RuntimeBeanReference(info.getDataSourceFactoryBeanName());
        } else {
            if (info.getDataSourceFactoryType() == null) {
                throw ExceptionUtils.throwConfigureException("未配置 dataSourceFactoryType 属性。");
            }
            if (TypeUtils.isAbstract(info.getDataSourceFactoryType())) {
                throw ExceptionUtils.throwConfigureException(" dataSourceFactoryType 属性不能为抽象类型。");
            }
            String beanName = BeanConstant.getDataSourceFactoryBeanName(info.getName());
            // 注册数据源工厂
            GenericBeanDefinition dataSourceFactoryBean = definitionRegistrar
                    .createBeanDefinition(info.getDataSourceFactoryType(), null, null, null);
            dataSourceFactoryBean.setPrimary(info.isPrimary());
            if (!definitionRegistrar.registerBean(beanName, dataSourceFactoryBean)) {
                throw ExceptionUtils.throwConfigureException("已存在 dataSourceFactory Bean :" + beanName);
            }
            ref = new RuntimeBeanReference(beanName);
        }
        // 注册数据源
        GenericBeanDefinition dataSourceBean = definitionRegistrar
                .createBeanDefinition(DynamicSingleRoutingDataSource.class, null, null, "close");
        dataSourceBean.setPrimary(info.isPrimary());
        //数据源工厂
        dataSourceBean.getConstructorArgumentValues().addGenericArgumentValue(ref);
        //提供者
        dataSourceBean.getConstructorArgumentValues().addGenericArgumentValue(new RuntimeBeanReference(provider.getItem1()));
        //实体包集
        dataSourceBean.getConstructorArgumentValues().addGenericArgumentValue(info.getTypeAliasesPackages());
        //映射包集
        dataSourceBean.getConstructorArgumentValues().addGenericArgumentValue(info.getMapperInterfacePackages());
        if (!definitionRegistrar.registerBean(info.getDataSourceBeanName(), dataSourceBean)) {
            throw ExceptionUtils.throwConfigureException("已存在 DataSource Bean :" + info.getDataSourceBeanName());
        }
        registerSession(definitionRegistrar, info);
        definitionRegistrar.getLogger().info("Register Autumn MyBatis Single DataSource");
    }

    /**
     * 注册动态数据源
     *
     * @param definitionRegistrar 注册定义
     * @param info                信息
     * @param provider            驱动类型
     */
    public static void registerDynamicDataSource(BeanRegisterManager definitionRegistrar,
                                                 DefinitionRegistrarInfo info,
                                                 TupleTwo<String, Class<? extends DbProvider>> provider) {
        RuntimeBeanReference ref;
        if (!com.autumn.util.StringUtils.isNullOrBlank(info.getDataSourceFactoryBeanName())) {
            // 数据源工厂
            ref = new RuntimeBeanReference(info.getDataSourceFactoryBeanName());
        } else {
            if (info.getDataSourceFactoryType() == null) {
                throw ExceptionUtils.throwConfigureException("未配置 dataSourceFactoryType 属性。");
            }
            if (TypeUtils.isAbstract(info.getDataSourceFactoryType())) {
                throw ExceptionUtils.throwConfigureException(" dataSourceFactoryType 属性不能为抽象类型。");
            }
            String beanName = BeanConstant.getDataSourceFactoryBeanName(info.getName());
            // 注册数据源工厂
            GenericBeanDefinition dataSourceFactoryBean = definitionRegistrar
                    .createBeanDefinition(info.getDataSourceFactoryType(), null, null, null);
            dataSourceFactoryBean.setPrimary(info.isPrimary());
            if (!definitionRegistrar.registerBean(beanName, dataSourceFactoryBean)) {
                throw ExceptionUtils.throwConfigureException("已存在 dataSourceFactory Bean :" + beanName);
            }
            ref = new RuntimeBeanReference(beanName);
        }
        if (info.getDataSourceRoutingType() == null) {
            throw ExceptionUtils.throwConfigureException("未配置 " + ATTRIBUTE_DATA_SOURCE_ROUTING_TYPE + " 属性。");
        }
        if (TypeUtils.isAbstract(info.getDataSourceRoutingType())) {
            throw ExceptionUtils.throwConfigureException(ATTRIBUTE_DATA_SOURCE_ROUTING_TYPE + " 属性不能为抽象类型。");
        }
        // 注册数据源
        GenericBeanDefinition dataSourceBean = definitionRegistrar.createBeanDefinition(info.getDataSourceRoutingType(),
                null, null, "close");
        dataSourceBean.setPrimary(info.isPrimary());
        //数据源工厂
        dataSourceBean.getConstructorArgumentValues().addGenericArgumentValue(ref);

        dataSourceBean.getConstructorArgumentValues().addGenericArgumentValue(new RuntimeBeanReference(provider.getItem1()));
        dataSourceBean.getConstructorArgumentValues().addGenericArgumentValue(info.getTypeAliasesPackages());
        dataSourceBean.getConstructorArgumentValues().addGenericArgumentValue(info.getMapperInterfacePackages());

        if (!definitionRegistrar.registerBean(info.getDataSourceBeanName(), dataSourceBean)) {
            throw ExceptionUtils.throwConfigureException("已存在 DataSource Bean :" + info.getDataSourceBeanName());
        }
        registerSession(definitionRegistrar, info);
        definitionRegistrar.getLogger().info("Register Autumn MyBatis Dynamic DataSource");
    }

    /**
     * 注册多数据源
     *
     * @param definitionRegistrar 注册定义
     * @param infos               信息集合
     */
    public static void registerMultipleDataSource(BeanRegisterManager definitionRegistrar,
                                                  List<MultipleDefinitionRegistrarInfo> infos) {
        if (infos.size() == 0) {
            return;
        }
        DefinitionRegistrarInfo mainInfo = infos.get(0).getInfo();
        RuntimeBeanReference ref;
        if (!com.autumn.util.StringUtils.isNullOrBlank(mainInfo.getDataSourceFactoryBeanName())) {
            // 数据源工厂
            ref = new RuntimeBeanReference(mainInfo.getDataSourceFactoryBeanName());
        } else {
            if (mainInfo.getDataSourceFactoryType() == null) {
                throw ExceptionUtils.throwConfigureException("未配置 dataSourceFactoryType 属性。");
            }
            if (TypeUtils.isAbstract(mainInfo.getDataSourceFactoryType())) {
                throw ExceptionUtils.throwConfigureException(" dataSourceFactoryType 属性不能为抽象类型。");
            }
            String beanName = BeanConstant.getDataSourceFactoryBeanName("multiple");
            // 注册数据源工厂
            GenericBeanDefinition dataSourceFactoryBean = definitionRegistrar
                    .createBeanDefinition(mainInfo.getDataSourceFactoryType(), null, null, null);
            dataSourceFactoryBean.setPrimary(true);
            if (!definitionRegistrar.registerBean(beanName, dataSourceFactoryBean)) {
                throw ExceptionUtils.throwConfigureException("已存在 dataSourceFactory Bean :" + beanName);
            }
            ref = new RuntimeBeanReference(beanName);
        }
        for (MultipleDefinitionRegistrarInfo info : infos) {
            // 注册数据源
            GenericBeanDefinition dataSourceBean = definitionRegistrar
                    .createBeanDefinition(DynamicNameRoutingDataSource.class, null, null, "close");
            dataSourceBean.setPrimary(info.getInfo().isPrimary());
            //数据源工厂
            dataSourceBean.getConstructorArgumentValues().addGenericArgumentValue(ref);
            //提供者
            dataSourceBean.getConstructorArgumentValues().addGenericArgumentValue(new RuntimeBeanReference(info.getProviderInfo().getItem1()));
            //实体包集
            dataSourceBean.getConstructorArgumentValues().addGenericArgumentValue(info.getInfo().getTypeAliasesPackages());
            //映射包集
            dataSourceBean.getConstructorArgumentValues().addGenericArgumentValue(info.getInfo().getMapperInterfacePackages());
            //路油名称
            dataSourceBean.getConstructorArgumentValues().addGenericArgumentValue(info.getInfo().getName());
            if (!definitionRegistrar.registerBean(info.getInfo().getDataSourceBeanName(), dataSourceBean)) {
                throw ExceptionUtils
                        .throwConfigureException("已存在 DataSource Bean :" + info.getInfo().getDataSourceBeanName());
            }
            registerSession(definitionRegistrar, info.getInfo());
        }
        definitionRegistrar.getLogger().info("Register Autumn MyBatis Multiple DataSource");
    }

    private static Set<Class<? extends Interceptor>> getPluginTypeList(Class<? extends Interceptor>[] pluginTypes) {
        Set<Class<? extends Interceptor>> items = new LinkedHashSet<>(AbstractInterceptor.getBuiltInPlugins());
        if (pluginTypes != null) {
            for (Class<? extends Interceptor> pluginType : pluginTypes) {
                if (pluginType != null) {
                    items.add(pluginType);
                }
            }
        }
        return items;
    }

    /**
     * 注册会话
     *
     * @param definitionRegistrar
     * @param info
     */
    private static void registerSession(BeanRegisterManager definitionRegistrar, DefinitionRegistrarInfo info) {
        RuntimeBeanReference ref;
        // 注册事务
        ref = new RuntimeBeanReference(info.getDataSourceBeanName());
        GenericBeanDefinition transactionManagerBeanDef = definitionRegistrar
                .createBeanDefinition(DataSourceTransactionManager.class, null, null, null);
        transactionManagerBeanDef.setPrimary(info.isPrimary());
        transactionManagerBeanDef.getConstructorArgumentValues().addGenericArgumentValue(ref);
        definitionRegistrar.registerBean(info.getTransactionManagerBeanName(), transactionManagerBeanDef);

        // 注册会话工厂
        ref = new RuntimeBeanReference(info.getDataSourceBeanName());
        GenericBeanDefinition sqlSessionFactoryBeanDef = definitionRegistrar
                .createBeanDefinition(MybatisSqlSessionFactoryBean.class, null, null, null);
        sqlSessionFactoryBeanDef.setPrimary(info.isPrimary());
        sqlSessionFactoryBeanDef.getPropertyValues().add("dataSource", ref);
        String[] typeAliasesPackages = info.getTypeAliasesPackages();
        if (typeAliasesPackages != null && typeAliasesPackages.length > 0) {
            String typeAliasesPackage = String.join(";", typeAliasesPackages);

            sqlSessionFactoryBeanDef.getPropertyValues().add("typeAliasesPackage", typeAliasesPackage);
        }
        if (info.getMapperLocations() != null) {
            sqlSessionFactoryBeanDef.getPropertyValues().add("mapperLocations", info.getMapperLocations());
        }
        if (info.getConfigLocation() != null) {
            sqlSessionFactoryBeanDef.getPropertyValues().add("configLocation", info.getConfigLocation());
        }
        Set<Class<? extends Interceptor>> pluginTypes = getPluginTypeList(info.getPluginTypes());
        ManagedArray pluginTypeRefs = new ManagedArray(Interceptor.class.getName(), pluginTypes.size());
        for (Class<? extends Interceptor> pluginType : pluginTypes) {
            GenericBeanDefinition pluginTypeBeanDef = definitionRegistrar.createBeanDefinition(pluginType, null,
                    null, null);
            pluginTypeBeanDef.setPrimary(info.isPrimary());
            String pluginBeanName;
            if (com.autumn.util.StringUtils.isNullOrBlank(info.getName())) {
                pluginBeanName = definitionRegistrar.generateBeanName(pluginType);
            } else {
                pluginBeanName = info.getName() + com.autumn.util.StringUtils
                        .upperCaseCapitalize(definitionRegistrar.generateBeanName(pluginType));
            }
            if (definitionRegistrar.registerBean(pluginBeanName, pluginTypeBeanDef)) {
                pluginTypeRefs.add(new RuntimeBeanReference(pluginBeanName));
            } else {
                throw ExceptionUtils.throwConfigureException("已存在 " + pluginType.getName() + " Bean :" + pluginBeanName);
            }
        }
        sqlSessionFactoryBeanDef.getPropertyValues().add("plugins", pluginTypeRefs);
        definitionRegistrar.registerBean(info.getSqlSessionFactoryBeanName(), sqlSessionFactoryBeanDef);

        if (info.isPrimary()) {
            // 注册会话模板,引模板可用于全局
            GenericBeanDefinition sqlSessionTemplateBeanDef = definitionRegistrar
                    .createBeanDefinition(SqlSessionTemplate.class, null, null, null);
            sqlSessionTemplateBeanDef.setPrimary(info.isPrimary());
            ref = new RuntimeBeanReference(info.getSqlSessionFactoryBeanName());
            sqlSessionTemplateBeanDef.getConstructorArgumentValues().addGenericArgumentValue(ref);
            definitionRegistrar.registerBean(info.getSqlSessionTemplateBeanName(), sqlSessionTemplateBeanDef);
        }
    }

    /**
     * 注册Db驱动提供者
     *
     * @param definitionRegistrar        定义注册
     * @param mapperScannerRegistrarInfo
     */
    public static TupleTwo<String, Class<? extends DbProvider>> registerDbProvider(
            BeanRegisterManager definitionRegistrar, DefinitionRegistrarInfo mapperScannerRegistrarInfo) {
        Class<? extends DbProvider> providerType = ProviderUtils.getConfigDbProviderClass(
                mapperScannerRegistrarInfo.getDriveType(), mapperScannerRegistrarInfo.getProviderType());

        String providerBeanName = mapperScannerRegistrarInfo.getProviderBeanName();
        String mapperRegisterBeanName = mapperScannerRegistrarInfo.getMapperRegisterBeanName();

        if (com.autumn.util.StringUtils.isNullOrBlank(providerBeanName)) {
            providerBeanName = definitionRegistrar.generateBeanName(providerType);
        }
        if (com.autumn.util.StringUtils.isNullOrBlank(mapperRegisterBeanName)) {
            mapperRegisterBeanName = definitionRegistrar.generateBeanName(MapperRegister.class);
        }

        GenericBeanDefinition providerBean = definitionRegistrar.createBeanDefinition(providerType, null, null, null);
        providerBean.setPrimary(mapperScannerRegistrarInfo.isPrimary());

        if (!definitionRegistrar.registerBean(providerBeanName, providerBean)) {
            throw ExceptionUtils.throwConfigureException("已存在 DbProvider Bean :" + providerBeanName);
        }

        // definitionRegistrar.logger.info("注册数据提供者 : " + providerType.getName() + "(" +
        // providerBeanName + ")");

        GenericBeanDefinition mapperRegisterBean = definitionRegistrar.createBeanDefinition(MapperRegister.class, null,
                null, null);
        mapperRegisterBean.setPrimary(mapperScannerRegistrarInfo.isPrimary());
        mapperRegisterBean.getConstructorArgumentValues().addGenericArgumentValue(new RuntimeBeanReference(providerBeanName));

        if (!definitionRegistrar.registerBean(mapperRegisterBeanName, mapperRegisterBean)) {
            throw ExceptionUtils.throwConfigureException("已存在 MapperRegister Bean :" + mapperRegisterBeanName);
        }
        return new TupleTwo<>(providerBeanName, providerType);
    }


    /**
     * 获取多数据源
     *
     * @param environment
     * @param importingClassMetadata
     * @return
     */
    public static List<DefinitionRegistrarInfo> multipleDataSourceMapperScannerRegistrarInfos(Environment environment,
                                                                                              AnnotationMetadata importingClassMetadata) {
        int primaryCount = 0;
        AnnotationAttributes annoMultipleAttrs = AnnotationAttributes.fromMap(
                importingClassMetadata.getAnnotationAttributes(EnableAutumnMybatisMultipleDataSource.class.getName()));
        List<DefinitionRegistrarInfo> infos = new ArrayList<>();
        if (annoMultipleAttrs != null) {
            AnnotationAttributes[] annotationClass = annoMultipleAttrs.getAnnotationArray("value");
            if (annotationClass != null) {
                for (AnnotationAttributes annoAttrs : annotationClass) {
                    String name = annoAttrs.getString("name");
                    if (com.autumn.util.StringUtils.isNullOrBlank(name)) {
                        ExceptionUtils.throwConfigureException("多数据源配置属性 name 不能为空。");
                    }
                    DefinitionRegistrarInfo info = new DefinitionRegistrarInfo();
                    info.setName(name);
                    info.setPrimary(annoAttrs.getBoolean("primary"));
                    info.setDataSourceFactoryType(annoMultipleAttrs.getClass(ATTRIBUTE_DATA_SOURCE_FACTORY_TYPE));
                    info.setDataSourceFactoryBeanName(
                            annoMultipleAttrs.getString(ATTRIBUTE_DATA_SOURCE_FACTORY_BEAN_NAME));
                    setMapperScannerRegistrarInfo(environment, annoAttrs, info);
                    if (info.isPrimary()) {
                        primaryCount++;
                    }
                    if (primaryCount > 1) {
                        ExceptionUtils.throwConfigureException("多数据源配置 primary = true 只允许存在一项。");
                    }
                    infos.add(info);
                }
            }
        }
        return infos;
    }

    /**
     * 获取单数据源
     *
     * @param importingClassMetadata 导入类元
     * @return
     */
    public static DefinitionRegistrarInfo singleDataSourceMapperScannerRegistrarInfos(Environment environment,
                                                                                      AnnotationMetadata importingClassMetadata) {
        AnnotationAttributes annoAttrs = AnnotationAttributes
                .fromMap(importingClassMetadata.getAnnotationAttributes(EnableAutumnMybatis.class.getName()));
        DefinitionRegistrarInfo info = new DefinitionRegistrarInfo();
        if (annoAttrs == null) {
            return info;
        }
        info.setName(null);
        info.setPrimary(true);
        info.setDataSourceFactoryType(annoAttrs.getClass(ATTRIBUTE_DATA_SOURCE_FACTORY_TYPE));
        info.setDataSourceFactoryBeanName(annoAttrs.getString(ATTRIBUTE_DATA_SOURCE_FACTORY_BEAN_NAME));
        setMapperScannerRegistrarInfo(environment, annoAttrs, info);
        return info;
    }

    /**
     * 获取动态数据源
     *
     * @param importingClassMetadata 导入类元
     * @return
     */
    public static DefinitionRegistrarInfo dynamicDataSourceMapperScannerRegistrarInfos(Environment environment,
                                                                                       AnnotationMetadata importingClassMetadata) {
        AnnotationAttributes annoAttrs = AnnotationAttributes.fromMap(
                importingClassMetadata.getAnnotationAttributes(EnableAutumnMybatisDynamicDataSource.class.getName()));
        DefinitionRegistrarInfo info = new DefinitionRegistrarInfo();
        if (annoAttrs == null) {
            return info;
        }
        String name = annoAttrs.getString("name");
        if (com.autumn.util.StringUtils.isNullOrBlank(name)) {
            name = BeanConstant.Dynamic.DEFAULT_DYNAMIC_NAME_PREFIX;
        }
        info.setPrimary(annoAttrs.getBoolean("primary"));
        info.setName(name);
        info.setDataSourceFactoryType(annoAttrs.getClass(ATTRIBUTE_DATA_SOURCE_FACTORY_TYPE));
        info.setDataSourceFactoryBeanName(annoAttrs.getString(ATTRIBUTE_DATA_SOURCE_FACTORY_BEAN_NAME));
        info.setDataSourceRoutingType(annoAttrs.getClass(ATTRIBUTE_DATA_SOURCE_ROUTING_TYPE));
        setMapperScannerRegistrarInfo(environment, annoAttrs, info);
        return info;
    }

    /**
     * @param environment
     * @param annoAttrs
     * @param info
     */
    private static <E extends DefinitionRegistrarInfo> void setMapperScannerRegistrarInfo(Environment environment,
                                                                                          AnnotationAttributes annoAttrs,
                                                                                          E info) {
        info.setTypeAliasesPackages(PackageUtils.toPackages(annoAttrs.getStringArray("typeAliasesPackages")));
        info.setMapperInterfacePackages(PackageUtils.toPackages(annoAttrs.getStringArray("value")));
        ProviderDriveType driveType = (ProviderDriveType) annoAttrs.get("driveType");
        info.setDriveType(driveType);
        info.setProviderType(annoAttrs.getClass("providerType"));
        info.setPluginTypes((Class<? extends Interceptor>[]) annoAttrs.getClassArray("pluginTypes"));

        String configLocation = annoAttrs.getString("configLocation");
        Map<String, Object> rpr = new RelaxedPropertyResolver(environment, MYBATIS_PAHT_DEFAULT).getSubProperties(".");
        if (rpr != null && com.autumn.util.StringUtils.isNullOrBlank(configLocation) && environment != null) {
            Object resObject = rpr.get("configLocation");
            if (resObject != null) {
                configLocation = resObject.toString().replace(CLASSPATH_REPLACE, "");
            }
        }
        info.setConfigLocation(getConfigLocation(configLocation));
        String[] mapperLocations = annoAttrs.getStringArray("mapperLocations");
        if (mapperLocations == null || mapperLocations.length == 0) {
            Object resObject = rpr.get("mapperLocations");
            if (resObject != null) {
                mapperLocations = StringUtils.tokenizeToStringArray(
                        resObject.toString().replace(CLASSPATH_REPLACE, "")
                                .replace(ResourcePatternResolver.CLASSPATH_ALL_URL_PREFIX, ""),
                        ConfigurableApplicationContext.CONFIG_LOCATION_DELIMITERS);
                for (int i = 0; i < mapperLocations.length; i++) {
                    mapperLocations[i] = ResourcePatternResolver.CLASSPATH_ALL_URL_PREFIX + mapperLocations[i];
                }
            }
        }
        info.setMapperLocations(getMapperLocations(mapperLocations));
        info.setDataSourceBeanName(BeanConstant.getDataSourceBeanName(info.getName()));
        info.setTransactionManagerBeanName(BeanConstant.getTransactionManagerBeanName(info.getName()));
        info.setSqlSessionFactoryBeanName(BeanConstant.getSqlSessionFactoryBeanName(info.getName()));
        info.setSqlSessionTemplateBeanName(BeanConstant.getSqlSessionTemplateBeanName(info.getName()));
        info.setProviderBeanName(BeanConstant.getDbProviderBeanName(info.getName()));
        info.setMapperRegisterBeanName(BeanConstant.getMapperRegisterBeanName(info.getName()));
    }

    private static Resource getConfigLocation(String configLocation) {
        if (com.autumn.util.StringUtils.isNullOrBlank(configLocation)) {
            return null;
        }
        return new ClassPathResource(configLocation);
    }

    private static Resource[] getMapperLocations(String[] stringArray) {
        if (stringArray == null || stringArray.length == 0) {
            return null;
        }
        PathMatchingResourcePatternResolver resolver = new PathMatchingResourcePatternResolver();
        List<Resource> resources = new ArrayList<>();
        for (String str : stringArray) {
            if (!com.autumn.util.StringUtils.isNullOrBlank(str)) {
                try {
                    resources.addAll(Arrays.asList(resolver.getResources(str)));
                } catch (IOException e) {
                    throw ExceptionUtils.throwConfigureException(e.getMessage(), e);
                }
            }
        }
        Resource[] arr = new Resource[resources.size()];
        return resources.toArray(arr);
    }


}
