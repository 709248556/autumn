package com.autumn.mybatis.annotation;

import com.autumn.exception.ExceptionUtils;
import com.autumn.mybatis.factory.AbstractDynamicPropertiesDataSourceFactory;
import com.autumn.mybatis.factory.DefaultMultiplePropertiesDataSourceFactory;
import com.autumn.mybatis.mapper.AutumnMybatisClassPathMapperScanner;
import com.autumn.mybatis.provider.DbProvider;
import com.autumn.mybatis.provider.ProviderDriveType;
import com.autumn.spring.boot.bean.BeanRegisterManager;
import com.autumn.spring.boot.bind.RelaxedPropertyResolver;
import com.autumn.util.StringUtils;
import com.autumn.util.tuple.TupleTwo;
import org.springframework.beans.factory.support.BeanDefinitionRegistry;
import org.springframework.core.type.AnnotationMetadata;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * 扫描多数据源注册
 *
 * @author 老码农
 * <p>
 * 2017-11-30 12:22:50
 */
class AutumnMybatisMultipleDataSourceRegistrar extends AbstractAutumnMybatisRegistrar {

    /**
     * 加载扫描
     *
     * @param regInfos
     * @param scanInfos
     */
    private void loadScan(List<DefinitionRegistrarInfo> regInfos, List<AutumnMybatisScanInfo> scanInfos) {
        if (scanInfos.size() > 0) {
            for (DefinitionRegistrarInfo regInfo : regInfos) {
                if (regInfo.getScanInfos() == null) {
                    regInfo.setScanInfos(new ArrayList<>());
                }
                if (!StringUtils.isNullOrBlank(regInfo.getName())) {
                    for (AutumnMybatisScanInfo scanInfo : scanInfos) {
                        if (!StringUtils.isNullOrBlank(scanInfo.getName())
                                && regInfo.getName().equalsIgnoreCase(scanInfo.getName())) {
                            regInfo.getScanInfos().add(scanInfo);
                        }
                    }
                }
            }
        }
    }

    @Override
    public void registerBeanDefinitions(AnnotationMetadata importingClassMetadata, BeanDefinitionRegistry registry) {

        List<DefinitionRegistrarInfo> regInfos = DefinitionRegistrarUtils.multipleDataSourceMapperScannerRegistrarInfos(this.getEnvironment(), importingClassMetadata);
        List<AutumnMybatisScanInfo> scanInfos = this.getScanInfos(importingClassMetadata);
        this.loadScan(regInfos, scanInfos);

        BeanRegisterManager regManager = new BeanRegisterManager(this.getEnvironment(),
                this.getResourceLoader(), registry);

        Map<String, Map<String, Object>> propertiesMap = null;

        List<MultipleDefinitionRegistrarInfo> multipleInfos = new ArrayList<>();
        for (DefinitionRegistrarInfo regInfo : regInfos) {
            if (regInfo.getDriveType().equals(ProviderDriveType.AUTO)) {
                if (!regInfo.getDataSourceFactoryType().equals(DefaultMultiplePropertiesDataSourceFactory.class)) {
                    throw ExceptionUtils.throwConfigureException("无法自动适配驱动类型[" + ProviderDriveType.class.getName() + "]。");
                }
                if (propertiesMap == null) {
                    propertiesMap = AbstractDynamicPropertiesDataSourceFactory.toPropertiesMapConvert(new RelaxedPropertyResolver(this.getEnvironment(),
                            DefaultMultiplePropertiesDataSourceFactory.DEFAULT_PROPERTIES_PREFIX)
                            .getSubProperties("."));
                }
                regInfo.setDriveType(this.findDriveTyppe(propertiesMap, regInfo.getName()));
            }
            TupleTwo<String, Class<? extends DbProvider>> tupleTwo = DefinitionRegistrarUtils
                    .registerDbProvider(regManager, regInfo);
            multipleInfos.add(new MultipleDefinitionRegistrarInfo(regInfo, tupleTwo));
        }
        DefinitionRegistrarUtils.registerMultipleDataSource(regManager, multipleInfos);
        for (DefinitionRegistrarInfo regInfo : regInfos) {
            AutumnMybatisClassPathMapperScanner scanner = new AutumnMybatisClassPathMapperScanner(registry);
            if (this.getResourceLoader() != null) {
                scanner.setResourceLoader(this.getResourceLoader());
            }
            scanner.setMapperRegisterBeanName(regInfo.getMapperRegisterBeanName());
            scanner.setSqlSessionFactoryBeanName(regInfo.getSqlSessionFactoryBeanName());
            //使用工厂，两者都使用会报警
            //scanner.setSqlSessionTemplateBeanName(regInfo.getSqlSessionTemplateBeanName());
            scanner.registerFilters();
            scanner.doScan(regInfo.getMapperInterfacePackages());
        }
    }

    private ProviderDriveType findDriveTyppe(Map<String, Map<String, Object>> propertiesMap, String name) {
        Map<String, Object> result = propertiesMap.get(name);
        if (result == null) {
            throw ExceptionUtils.throwConfigureException("无法自动识别名称为[" + name + "]数据源驱动类型。");
        }
        Object url = propertiesMap.get("url");
        if (url == null || StringUtils.isNullOrBlank(url.toString().trim())) {
            throw ExceptionUtils.throwConfigureException("数据源名称[" + name + "]未配置 url 属性");
        }
        ProviderDriveType type = ProviderDriveType.getDriveTypeByUrl(url.toString());
        if (type.equals(ProviderDriveType.CUSTOM)) {
            throw ExceptionUtils.throwConfigureException("数据源名称[" + name + "]连接窜[" + url + "]无法自动识别驱动类型。");
        }
        return type;
    }


}
