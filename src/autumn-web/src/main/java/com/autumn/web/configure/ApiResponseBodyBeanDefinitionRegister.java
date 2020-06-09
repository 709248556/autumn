package com.autumn.web.configure;

import com.autumn.exception.ExceptionUtils;
import com.autumn.spring.boot.bean.AbstractImportBeanRegistrar;
import com.autumn.spring.boot.bean.BeanRegisterManager;
import com.autumn.spring.boot.bean.BeanScanUtils;
import com.autumn.util.StringUtils;
import com.autumn.util.TypeUtils;
import com.autumn.web.annotation.ApiResponseBodyScan;
import com.autumn.web.annotation.EnableAutumnApiResponseBody;
import com.autumn.web.handlers.ApiRequestMappingInfoHandler;
import com.autumn.web.handlers.ApiResponseBodyHandler;
import com.autumn.web.handlers.DefaultErrorInfoResultHandler;
import com.autumn.web.handlers.ErrorInfoResultHandler;
import com.autumn.web.handlers.impl.ApiRequestMappingInfoHandlerImpl;
import org.springframework.beans.factory.config.RuntimeBeanReference;
import org.springframework.beans.factory.support.BeanDefinitionRegistry;
import org.springframework.beans.factory.support.GenericBeanDefinition;
import org.springframework.core.annotation.AnnotationAttributes;
import org.springframework.core.type.AnnotationMetadata;

import java.util.LinkedHashSet;
import java.util.Set;

/**
 * Api 响应体扫描配置
 *
 * @author 老码农
 * <p>
 * 2018-01-15 09:17:57
 */
public class ApiResponseBodyBeanDefinitionRegister extends AbstractImportBeanRegistrar {

    private Set<String> createPackages(AnnotationMetadata importingClassMetadata) {
        Set<ApiResponseBodyScan> scans = BeanScanUtils.findStartupAnnotations(importingClassMetadata, ApiResponseBodyScan.class);
        Set<String> paks = new LinkedHashSet<>(scans.size());
        for (ApiResponseBodyScan scan : scans) {
            if (scan.value() != null) {
                for (String s : scan.value()) {
                    if (StringUtils.isNotNullOrBlank(s)) {
                        paks.add(s);
                    }
                }
            }
        }
        return paks;
    }

    @Override
    public void registerBeanDefinitions(AnnotationMetadata importingClassMetadata, BeanDefinitionRegistry registry) {
        AnnotationAttributes annoAttrs = AnnotationAttributes
                .fromMap(importingClassMetadata.getAnnotationAttributes(EnableAutumnApiResponseBody.class.getName()));
        Class<? extends ErrorInfoResultHandler> errorInfoResultHandlerClass = annoAttrs.getClass(EnableAutumnApiResponseBody.ERROR_INFO_RESULT_HANDLER_CLASS_ATTRIBUTE_NAME);
        if (errorInfoResultHandlerClass == null) {
            errorInfoResultHandlerClass = DefaultErrorInfoResultHandler.class;
        } else {
            if (TypeUtils.isAbstract(errorInfoResultHandlerClass)) {
                ExceptionUtils.throwConfigureException("类型 " + errorInfoResultHandlerClass + " 不能是抽象或接口类。");
            }
        }
        Set<String> paks = createPackages(importingClassMetadata);
        String[] arr = annoAttrs.getStringArray(EnableAutumnApiResponseBody.API_CONTROLLER_PACKAGES_ATTRIBUTE_NAME);
        if (arr != null && arr.length > 0) {
            for (String s : arr) {
                if (StringUtils.isNotNullOrBlank(s)) {
                    paks.add(s);
                }
            }
        }

        Object status = annoAttrs.get(EnableAutumnApiResponseBody.ERROR_RESPONSE_STATUS_ATTRIBUTE_NAME);
        BeanRegisterManager regManager = new BeanRegisterManager(this.getEnvironment(),
                this.getResourceLoader(), registry);
        GenericBeanDefinition definition;
        String errorHandlerBeanName = regManager.generateBeanName(errorInfoResultHandlerClass);
        if (!registry.containsBeanDefinition(errorHandlerBeanName)) {
            definition = regManager.createBeanDefinition(errorInfoResultHandlerClass, null, null, null);
            regManager.registerBean(errorHandlerBeanName, definition);
        }
        String apiRequestBeanName = regManager.generateBeanName(ApiRequestMappingInfoHandler.class);
        if (!registry.containsBeanDefinition(apiRequestBeanName)) {
            definition = regManager.createBeanDefinition(ApiRequestMappingInfoHandlerImpl.class, null, null, null);
            definition.getConstructorArgumentValues().addGenericArgumentValue(paks.toArray(new String[0]));
            regManager.registerBean(apiRequestBeanName, definition);
        }
        String apiResponseBeanName = regManager.generateBeanName(ApiResponseBodyHandler.class);
        if (!registry.containsBeanDefinition(apiResponseBeanName)) {
            definition = regManager.createBeanDefinition(ApiResponseBodyHandler.class, null, null, null);
            definition.getConstructorArgumentValues().addGenericArgumentValue(new RuntimeBeanReference(errorHandlerBeanName));
            definition.getConstructorArgumentValues().addGenericArgumentValue(new RuntimeBeanReference(apiRequestBeanName));
            definition.getConstructorArgumentValues().addGenericArgumentValue(status);
            regManager.registerBean(apiResponseBeanName, definition);
        }
    }
}
