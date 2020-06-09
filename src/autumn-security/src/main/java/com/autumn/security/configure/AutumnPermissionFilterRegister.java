package com.autumn.security.configure;

import com.autumn.security.filter.AutumnPermissionFilter;
import com.autumn.security.filter.DefaultAutumnPermissionFilter;
import org.springframework.beans.factory.support.BeanDefinitionRegistry;
import org.springframework.beans.factory.support.GenericBeanDefinition;
import org.springframework.core.annotation.AnnotationAttributes;
import org.springframework.core.type.AnnotationMetadata;

import com.autumn.security.annotation.EnableAutumnSecurity;
import com.autumn.spring.boot.bean.AbstractImportBeanRegistrar;
import com.autumn.spring.boot.bean.BeanRegisterManager;
import com.autumn.exception.ExceptionUtils;
import com.autumn.util.TypeUtils;

/**
 * 权限过滤注册
 * 
 * @author 老码农 2018-12-12 12:23:59
 */
public class AutumnPermissionFilterRegister extends AbstractImportBeanRegistrar {

	@Override
	public void registerBeanDefinitions(AnnotationMetadata importingClassMetadata, BeanDefinitionRegistry registry) {
		AnnotationAttributes annoAttrs = AnnotationAttributes
				.fromMap(importingClassMetadata.getAnnotationAttributes(EnableAutumnSecurity.class.getName()));

		Class<? extends AutumnPermissionFilter> permissionFilterClass = annoAttrs
				.getClass(EnableAutumnSecurity.PERMISSION_FILTER_TYPE_ATTRIBUTE_NAME);

		if (permissionFilterClass == null) {
			permissionFilterClass = DefaultAutumnPermissionFilter.class;
		} else {
			if (TypeUtils.isAbstract(permissionFilterClass)) {
				ExceptionUtils.throwConfigureException("类型 " + permissionFilterClass + " 不能是抽象或接口类。");
			}
		}
		BeanRegisterManager regManager = new BeanRegisterManager(this.getEnvironment(),
				this.getResourceLoader(), registry);
		String handlerBeanName = "autumnPermissionFilter";
		if (!registry.containsBeanDefinition(handlerBeanName)) {
			GenericBeanDefinition definition = regManager.createBeanDefinition(permissionFilterClass, null, null, null);
			regManager.registerBean(handlerBeanName, definition);
		}
	}

}
