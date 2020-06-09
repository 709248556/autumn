package com.autumn.mybatis.annotation;

import com.autumn.exception.ExceptionUtils;
import com.autumn.mybatis.factory.DefaultSinglePropertiesDataSourceFactory;
import com.autumn.mybatis.mapper.AutumnMybatisClassPathMapperScanner;
import com.autumn.mybatis.provider.DbProvider;
import com.autumn.mybatis.provider.ProviderDriveType;
import com.autumn.spring.boot.bean.BeanRegisterManager;
import com.autumn.spring.boot.bind.RelaxedPropertyResolver;
import com.autumn.util.StringUtils;
import com.autumn.util.tuple.TupleTwo;
import org.springframework.beans.factory.support.BeanDefinitionRegistry;
import org.springframework.core.type.AnnotationMetadata;

import java.util.Map;

/**
 * Mapper 单数据源扫描注册
 *
 * @author 老码农
 * <p>
 * 2017-11-30 11:01:52
 */
class AutumnMybatisSingleDataSourceRegistrar extends AbstractAutumnMybatisRegistrar {

    @Override
    public void registerBeanDefinitions(AnnotationMetadata importingClassMetadata, BeanDefinitionRegistry registry) {
        DefinitionRegistrarInfo regInfo = DefinitionRegistrarUtils.singleDataSourceMapperScannerRegistrarInfos(this.getEnvironment(), importingClassMetadata);
        regInfo.setScanInfos(this.getScanInfos(importingClassMetadata));

        if (regInfo.getDriveType().equals(ProviderDriveType.AUTO)) {
            if (!regInfo.getDataSourceFactoryType().equals(DefaultSinglePropertiesDataSourceFactory.class)) {
                throw ExceptionUtils.throwConfigureException("无法自动适配驱动类型[" + ProviderDriveType.class.getName() + "]。");
            }
            Map<String, Object> propertiesMap = new RelaxedPropertyResolver(this.getEnvironment(),
                    DefaultSinglePropertiesDataSourceFactory.DEFAULT_PROPERTIES_PREFIX)
                    .getSubProperties(".");
            Object url = propertiesMap.get("url");
            if (url == null || StringUtils.isNullOrBlank(url.toString().trim())) {
                throw ExceptionUtils.throwConfigureException("数据源未配置 url 属性");
            }
            ProviderDriveType type = ProviderDriveType.getDriveTypeByUrl(url.toString());
            if (type.equals(ProviderDriveType.CUSTOM)) {
                throw ExceptionUtils.throwConfigureException("连接窜[" + url + "]无法自动识别驱动类型。");
            }
            regInfo.setDriveType(type);
        }

        AutumnMybatisClassPathMapperScanner scanner = new AutumnMybatisClassPathMapperScanner(registry);
        if (this.getResourceLoader() != null) {
            scanner.setResourceLoader(this.getResourceLoader());
        }

        BeanRegisterManager regManager = new BeanRegisterManager(this.getEnvironment(),
                this.getResourceLoader(), registry);

        TupleTwo<String, Class<? extends DbProvider>> provider = DefinitionRegistrarUtils.registerDbProvider(regManager, regInfo);
        DefinitionRegistrarUtils.registerSingleDataSource(regManager, regInfo, provider);
        scanner.setMapperRegisterBeanName(regInfo.getMapperRegisterBeanName());
        scanner.setSqlSessionFactoryBeanName(regInfo.getSqlSessionFactoryBeanName());
        //使用工厂，两者都使用会报警
        // scanner.setSqlSessionTemplateBeanName(regInfo.getSqlSessionTemplateBeanName());
        scanner.registerFilters();
        scanner.doScan(regInfo.getMapperInterfacePackages());
    }


}
