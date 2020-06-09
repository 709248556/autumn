package com.autumn.mybatis.configure;

import com.autumn.mybatis.annotation.EnableAutumnPerformance;
import com.autumn.mybatis.plugins.PerformanceInterceptor;
import com.autumn.spring.boot.bean.AbstractImportBeanRegistrar;
import com.autumn.spring.boot.bean.BeanRegisterManager;
import org.springframework.beans.factory.support.BeanDefinitionRegistry;
import org.springframework.beans.factory.support.GenericBeanDefinition;
import org.springframework.core.annotation.AnnotationAttributes;
import org.springframework.core.type.AnnotationMetadata;

import java.util.LinkedHashMap;
import java.util.Map;

/**
 * 性能监视配置
 * <p>
 * </p>
 *
 * @description TODO
 * @author: 老码农
 * @create: 2020-04-07 01:23
 **/

public class AutumnPerformanceBeanRegistrar extends AbstractImportBeanRegistrar {


    @Override
    public void registerBeanDefinitions(AnnotationMetadata importingClassMetadata,
                                        BeanDefinitionRegistry registry) {
        AnnotationAttributes annoAttrs = AnnotationAttributes
                .fromMap(importingClassMetadata.getAnnotationAttributes(EnableAutumnPerformance.class.getName()));
        Long maxExecuteTime = annoAttrs.getNumber("maxExecuteTime");
        boolean writeInLog = annoAttrs.getBoolean("writeInLog");
        boolean consolePrint = annoAttrs.getBoolean("consolePrint");
        BeanRegisterManager regManager = new BeanRegisterManager(this.getEnvironment(), this.getResourceLoader(), registry);
        String handlerBeanName = "autumnPerformanceInterceptor";
        if (!registry.containsBeanDefinition(handlerBeanName)) {
            Map<String, Object> propertyValues = new LinkedHashMap<>(3);
            propertyValues.put("maxExecuteTime", maxExecuteTime);
            propertyValues.put("writeInLog", writeInLog);
            propertyValues.put("consolePrint", consolePrint);
            GenericBeanDefinition definition = regManager.createBeanDefinition(PerformanceInterceptor.class,
                    propertyValues,
                    null,
                    null);
            regManager.registerBean(handlerBeanName, definition);
        }
    }
}
