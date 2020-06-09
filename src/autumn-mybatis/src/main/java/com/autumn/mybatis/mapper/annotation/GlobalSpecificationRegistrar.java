package com.autumn.mybatis.mapper.annotation;

import com.autumn.exception.ExceptionUtils;
import com.autumn.mybatis.mapper.SpecificationDefinition;
import com.autumn.mybatis.metadata.EntityTable;
import com.autumn.spring.boot.bean.AbstractImportBeanRegistrar;
import com.autumn.util.TypeUtils;
import org.springframework.beans.factory.support.BeanDefinitionRegistry;
import org.springframework.core.annotation.AnnotationAttributes;
import org.springframework.core.type.AnnotationMetadata;

/**
 * 全局的规范类型注册
 *
 * @Description TODO
 * @Author 老码农
 * @Date 2019-08-25 16:46
 */
class GlobalSpecificationRegistrar extends AbstractImportBeanRegistrar {

    @Override
    public void registerBeanDefinitions(AnnotationMetadata importingClassMetadata, BeanDefinitionRegistry registry) {
        AnnotationAttributes annoAttrs = AnnotationAttributes.fromMap(importingClassMetadata.getAnnotationAttributes(GlobalSpecification.class.getName()));
        Class<? extends SpecificationDefinition> specificationType = annoAttrs.getClass("specificationType");
        if (specificationType == null) {
            ExceptionUtils.throwSystemException("全局规范配置类型 SpecificationDefinition 为 null。");
        }
        if (specificationType.isInterface()) {
            ExceptionUtils.throwSystemException("全局规范配置类型 " + specificationType.getName() + " 不能是接口类型,必须是实现类型。");
        }
        if (TypeUtils.isAbstract(specificationType)) {
            ExceptionUtils.throwSystemException("全局规范配置类型 " + specificationType.getName() + " 不能是抽象类型,必须是实现类型。");
        }
        EntityTable.setGlobalSpecificationType(specificationType);
    }
}
