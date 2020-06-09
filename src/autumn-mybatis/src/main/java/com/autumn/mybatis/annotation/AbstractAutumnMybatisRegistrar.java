package com.autumn.mybatis.annotation;

import com.autumn.spring.boot.bean.AbstractImportBeanRegistrar;
import com.autumn.spring.boot.bean.BeanScanUtils;
import org.springframework.core.type.AnnotationMetadata;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

/**
 * 扫描抽象
 *
 * @Description TODO
 * @Author 老码农
 * @Date 2019-08-25 2:59
 */
abstract class AbstractAutumnMybatisRegistrar extends AbstractImportBeanRegistrar {

    /**
     * 获取扫描信息
     *
     * @param annotationMetadata 注解元
     * @return
     */
    protected List<AutumnMybatisScanInfo> getScanInfos(AnnotationMetadata annotationMetadata) {
        Set<AutumnMybatisScan> scans = BeanScanUtils.findStartupAnnotations(annotationMetadata, AutumnMybatisScan.class);
        List<AutumnMybatisScanInfo> items = new ArrayList<>(scans.size());
        for (AutumnMybatisScan scan : scans) {
            AutumnMybatisScanInfo info = new AutumnMybatisScanInfo();
            info.setName(scan.name());
            info.setTypeAliasesPackages(scan.typeAliasesPackages());
            info.setMapperInterfacePackages(scan.value());
            items.add(info);
        }
        return items;
    }

}
