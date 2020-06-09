package com.autumn.mq.configurre;

import com.autumn.mq.rabbit.annotation.AutumnRabbitConnection;
import com.autumn.mq.rabbit.annotation.EnableAutumnRabbit;
import com.autumn.mq.rabbit.register.AutumnRabbitBeanRegisterManager;
import com.autumn.mq.rabbit.register.AutumnRabbitConnectionInfo;
import com.autumn.spring.boot.bean.AbstractImportBeanRegistrar;
import com.autumn.util.StringUtils;
import org.springframework.beans.factory.support.BeanDefinitionRegistry;
import org.springframework.core.annotation.AnnotationAttributes;
import org.springframework.core.type.AnnotationMetadata;

import java.util.Map;

/**
 * 扫描
 * 
 * @author 老码农
 *
 *         2017-12-16 17:29:36
 */
public class AutumnRabbitBeanRegistrar extends AbstractImportBeanRegistrar {

	/**
	 * 注册 Bean 定义
	 */
	@Override
	public void registerBeanDefinitions(AnnotationMetadata importingClassMetadata, BeanDefinitionRegistry registry) {
		AnnotationAttributes annoAttrs = AnnotationAttributes
				.fromMap(importingClassMetadata.getAnnotationAttributes(EnableAutumnRabbit.class.getName()));
		AnnotationAttributes[] annotationClass = annoAttrs.getAnnotationArray("value");
		int count = 0;

		AutumnRabbitBeanRegisterManager regManager = new AutumnRabbitBeanRegisterManager(
				this.getEnvironment(), this.getResourceLoader(), registry);

		for (AnnotationAttributes annotationAttributes : annotationClass) {
			AutumnRabbitConnectionInfo info = new AutumnRabbitConnectionInfo();
			info.setBeanPrefix(annotationAttributes.getString("beanPrefix"));
			if (StringUtils.isNullOrBlank(info.getBeanPrefix())) {
				info.setBeanPrefix(annotationAttributes.getString("autumn"));
			}
			info.setPropertiesPrefix(annotationAttributes.getString("propertiesPrefix"));
			if (StringUtils.isNullOrBlank(info.getPropertiesPrefix())) {
				info.setPropertiesPrefix(
						annotationAttributes.getString(AutumnRabbitConnection.DEFAULT_RABBITMQ_PROPERTIES_PREFIX));
			}
			info.setEventHandlerPackages(annotationAttributes.getStringArray("eventHandlerPackages"));
			info.setEventDataPackages(annotationAttributes.getStringArray("eventDataPackages"));
			boolean isPrimary = annotationAttributes.getBoolean("primary");
			if (isPrimary) {
				if (count > 0) {
					isPrimary = false;
				}
				count++;
			}
			info.setPrimary(isPrimary);
			Map<String, Object> map = regManager.getRegisterManager()
					.getEnvironmentPropertiesMap(info.getPropertiesPrefix());
			info.setProperties(map);
			regManager.register(info);
		}
	}
}
