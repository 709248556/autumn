package com.autumn.mq.rabbit.register;

import com.autumn.mq.FastJsonMessageConverter;
import com.autumn.mq.rabbit.event.RabbitEventBus;
import com.autumn.mq.rabbit.event.RabbitEventHandlerBeanFactory;
import com.autumn.spring.boot.bean.BeanRegisterManager;
import com.autumn.util.CollectionUtils;
import org.springframework.amqp.rabbit.connection.CachingConnectionFactory;
import org.springframework.amqp.rabbit.core.RabbitAdmin;
import org.springframework.amqp.rabbit.core.RabbitMessagingTemplate;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.MutablePropertyValues;
import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.beans.factory.config.RuntimeBeanReference;
import org.springframework.beans.factory.support.BeanDefinitionRegistry;
import org.springframework.beans.factory.support.GenericBeanDefinition;
import org.springframework.boot.autoconfigure.amqp.RabbitProperties.Template;
import org.springframework.core.env.Environment;
import org.springframework.core.io.ResourceLoader;
import org.springframework.messaging.converter.MappingJackson2MessageConverter;

import java.util.Map;

/**
 * Rabbit Bean 定义注册管理
 * 
 * @author 老码农
 *
 *         2018-02-01 09:40:28
 */
public class AutumnRabbitBeanRegisterManager {

	private final BeanRegisterManager registerManager;

	/**
	 * 
	* @return
	*
	 */
	public BeanRegisterManager getRegisterManager() {
		return registerManager;
	}

	/**
	 * 
	 * @param environment
	 * @param resourceLoader
	 * @param registry
	 */
	public AutumnRabbitBeanRegisterManager(Environment environment, ResourceLoader resourceLoader,
										   BeanDefinitionRegistry registry) {
		super();
		this.registerManager = new BeanRegisterManager(environment, resourceLoader, registry);
	}

	/**
	 * 注册
	 * 
	 * @param info
	 *            配置信息
	 */
	public void register(AutumnRabbitConnectionInfo info) {
		registerMessageConverter(info);
		registerMappingMessageConverter(info);
		registerConnectionFactory(info);
		registerRabbitTemplate(info);
		registerRabbitMessagingTemplate(info);
		registerRabbitAdmin(info);
		registerEventHandlerBeanFactory(info);
		registerMqEventBus(info);
	}

	/**
	 * 注册消息转换
	 * 
	 * @param info
	 */
	public void registerMessageConverter(AutumnRabbitConnectionInfo info) {
		String beanName = info.getMessageConverterBeanName();
		if (this.registerManager.getRegistry().containsBeanDefinition(beanName)) {
			return;
		}
		GenericBeanDefinition definition = this.registerManager.createBeanDefinition(FastJsonMessageConverter.class,
				null, null, null);
		definition.setPrimary(true);
		this.registerManager.registerBean(beanName, definition);
	}

	/**
	 * 注册 Mapping 消息转换
	 * 
	 * @param info
	 */
	public void registerMappingMessageConverter(AutumnRabbitConnectionInfo info) {
		String beanName = info.getMappingMessageConverterBeanName();
		if (this.registerManager.getRegistry().containsBeanDefinition(beanName)) {
			return;
		}
		GenericBeanDefinition definition = this.registerManager
				.createBeanDefinition(MappingJackson2MessageConverter.class, null, null, null);
		definition.setPrimary(true);
		this.registerManager.registerBean(beanName, definition);
	}

	/**
	 * 注册连接工厂
	 * 
	 * @param info
	 */
	public void registerConnectionFactory(AutumnRabbitConnectionInfo info) {
		String beanName = info.getConnectionFactoryBeanName();
		if (this.registerManager.getRegistry().containsBeanDefinition(beanName)) {
			return;
		}
		Map<String, Object> propertyValues = CollectionUtils.newHashMap();
		propertyValues.put("host", info.getRabbitProperties().getHost());
		propertyValues.put("port", info.getRabbitProperties().getPort());
		propertyValues.put("addresses", info.getRabbitProperties().determineAddresses());
		propertyValues.put("username", info.getRabbitProperties().getUsername());
		propertyValues.put("password", info.getRabbitProperties().getPassword());
		propertyValues.put("virtualHost", info.getRabbitProperties().getVirtualHost());
		propertyValues.put("publisherConfirms", info.getRabbitProperties().isPublisherConfirms());
		propertyValues.put("publisherReturns", info.getRabbitProperties().isPublisherReturns());
		if (info.getRabbitProperties().getConnectionTimeout() != null) {
			propertyValues.put("connectionTimeout", info.getRabbitProperties().getConnectionTimeout());
		}
		GenericBeanDefinition definition = this.registerManager.createBeanDefinition(CachingConnectionFactory.class,
				propertyValues, null, null);
		definition.setPrimary(info.isPrimary());
		this.registerManager.registerBean(beanName, definition);
	}

	/**
	 * 注册 RabbitTemplate
	 * 
	 * @param info
	 */
	public void registerRabbitTemplate(AutumnRabbitConnectionInfo info) {
		String beanName = info.getRabbitTemplateBeanName();
		if (this.registerManager.getRegistry().containsBeanDefinition(beanName)) {
			return;
		}
		Map<String, Object> propertyValues = CollectionUtils.newHashMap();
		Template template = info.getRabbitProperties().getTemplate();
		if (template != null) {
			if (template.getReceiveTimeout() != null) {
				propertyValues.put("receiveTimeout", template.getReceiveTimeout());
			}
			if (template.getReplyTimeout() != null) {
				propertyValues.put("replyTimeout", template.getReplyTimeout());
			}
			if (template.getMandatory() != null) {
				propertyValues.put("mandatory", template.getMandatory());
			}
		}
		GenericBeanDefinition definition = this.registerManager.createBeanDefinition(RabbitTemplate.class,
				propertyValues, null, null);
		definition.getConstructorArgumentValues()
				.addGenericArgumentValue(new RuntimeBeanReference(info.getConnectionFactoryBeanName()));
		MutablePropertyValues mv = definition.getPropertyValues();
		mv.add("messageConverter", new RuntimeBeanReference(info.getMessageConverterBeanName()));
		definition.setPrimary(info.isPrimary());
		definition.setScope(ConfigurableBeanFactory.SCOPE_PROTOTYPE);
		this.registerManager.registerBean(beanName, definition);
	}

	/**
	 * 注册 RabbitMessagingTemplate
	 * 
	 * @param info
	 */
	public void registerRabbitMessagingTemplate(AutumnRabbitConnectionInfo info) {
		String beanName = info.getRabbitMessagingTemplateBeanName();
		if (this.registerManager.getRegistry().containsBeanDefinition(beanName)) {
			return;
		}
		GenericBeanDefinition definition = this.registerManager.createBeanDefinition(RabbitMessagingTemplate.class,
				null, null, null);
		definition.setPrimary(info.isPrimary());
		MutablePropertyValues mv = definition.getPropertyValues();
		mv.add("rabbitTemplate", new RuntimeBeanReference(info.getRabbitTemplateBeanName()));
		mv.add("messageConverter", new RuntimeBeanReference(info.getMappingMessageConverterBeanName()));
		this.registerManager.registerBean(beanName, definition);
	}

	/**
	 * 注册 RabbitAdmin
	 * 
	 * @param info
	 */
	public void registerRabbitAdmin(AutumnRabbitConnectionInfo info) {
		String beanName = info.getRabbitAdminBeanName();
		if (this.registerManager.getRegistry().containsBeanDefinition(beanName)) {
			return;
		}
		GenericBeanDefinition definition = this.registerManager.createBeanDefinition(RabbitAdmin.class, null, null,
				null);
		definition.setPrimary(info.isPrimary());
		definition.getConstructorArgumentValues()
				.addGenericArgumentValue(new RuntimeBeanReference(info.getConnectionFactoryBeanName()));
		this.registerManager.registerBean(beanName, definition);
	}

	/**
	 * 注册事件处理Bean工厂
	 * 
	 * @param info
	 */
	public void registerEventHandlerBeanFactory(AutumnRabbitConnectionInfo info) {
		String beanName = info.getEventHandlerBeanFactoryBeanName();
		if (this.registerManager.getRegistry().containsBeanDefinition(beanName)) {
			return;
		}
		
		Map<String, Object> propertyValues = CollectionUtils.newHashMap();
		propertyValues.put("eventHandlerPackages", info.getEventHandlerPackages());
		propertyValues.put("eventDataPackages", info.getEventDataPackages());
		propertyValues.put("eventHandlerInfos", info.getEventHandlerInfos());
		propertyValues.put("eventDataInfos", info.getEventDataInfos());

		GenericBeanDefinition definition = this.registerManager
				.createBeanDefinition(RabbitEventHandlerBeanFactory.class, propertyValues, "initialize", "close");
		definition.setPrimary(info.isPrimary());
		definition.getConstructorArgumentValues()
				.addGenericArgumentValue(new RuntimeBeanReference(info.getConnectionFactoryBeanName()));
		definition.getConstructorArgumentValues()
				.addGenericArgumentValue(new RuntimeBeanReference(info.getRabbitTemplateBeanName()));
		this.registerManager.registerBean(beanName, definition);		
		
	}

	/**
	 * 注册 Mq 事件总线
	 * 
	 * @param info
	 */
	public void registerMqEventBus(AutumnRabbitConnectionInfo info) {
		String beanName = info.getMqEventBusBeanName();
		if (this.registerManager.getRegistry().containsBeanDefinition(beanName)) {
			return;
		}
		GenericBeanDefinition definition = this.registerManager.createBeanDefinition(RabbitEventBus.class, null, null,
				null);
		definition.setPrimary(info.isPrimary());
		definition.getConstructorArgumentValues()
				.addGenericArgumentValue(new RuntimeBeanReference(info.getEventHandlerBeanFactoryBeanName()));
		this.registerManager.registerBean(beanName, definition);
	}
}
