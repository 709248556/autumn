package com.autumn.mybatis.annotation;

import com.autumn.mybatis.mapper.AutumnMybatisClassPathMapperScanner;
import com.autumn.mybatis.provider.DbProvider;
import com.autumn.spring.boot.bean.BeanRegisterManager;
import com.autumn.util.tuple.TupleTwo;
import org.springframework.beans.factory.support.BeanDefinitionRegistry;
import org.springframework.core.type.AnnotationMetadata;

/**
 * 动态数据源注册
 *
 * @author 老码农
 * <p>
 * Description
 * </p>
 * @date 2017-12-29 20:53:35
 */
class AutumnMybatisDynamicDataSourceRegistrar extends AbstractAutumnMybatisRegistrar {

    /**
     *
     */
    @Override
    public void registerBeanDefinitions(AnnotationMetadata importingClassMetadata, BeanDefinitionRegistry registry) {

        DefinitionRegistrarInfo regInfo = DefinitionRegistrarUtils.dynamicDataSourceMapperScannerRegistrarInfos(this.getEnvironment(), importingClassMetadata);
        regInfo.setScanInfos(this.getScanInfos(importingClassMetadata));

        AutumnMybatisClassPathMapperScanner scanner = new AutumnMybatisClassPathMapperScanner(registry);
        if (this.getResourceLoader() != null) {
            scanner.setResourceLoader(this.getResourceLoader());
        }

        BeanRegisterManager regManager = new BeanRegisterManager(this.getEnvironment(),
                this.getResourceLoader(), registry);

        TupleTwo<String, Class<? extends DbProvider>> provider = DefinitionRegistrarUtils
                .registerDbProvider(regManager, regInfo);

        DefinitionRegistrarUtils.registerDynamicDataSource(regManager, regInfo, provider);
        scanner.setMapperRegisterBeanName(regInfo.getMapperRegisterBeanName());
        scanner.setSqlSessionFactoryBeanName(regInfo.getSqlSessionFactoryBeanName());
        //使用工厂，两者都使用会报警
        //scanner.setSqlSessionTemplateBeanName(regInfo.getSqlSessionTemplateBeanName());
        scanner.registerFilters();
        scanner.doScan(regInfo.getMapperInterfacePackages());

    }

}
