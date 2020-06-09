package com.autumn.mq.rabbit.register;

import java.time.Duration;
import java.util.Collection;
import java.util.Map;

import org.springframework.boot.autoconfigure.amqp.RabbitProperties;
import org.springframework.boot.autoconfigure.amqp.RabbitProperties.Ssl;

import com.autumn.mq.event.AutumnMqEventDataConfigureInfo;
import com.autumn.mq.event.AutumnMqEventHandlerConfigureInfo;
import com.autumn.mq.rabbit.annotation.AutumnRabbitConnection;
import com.autumn.util.StringUtils;

/**
 * Rabbit 配置信息
 * 
 * @author 老码农
 *
 *         2018-01-11 18:35:09
 */
public class AutumnRabbitConnectionInfo {
	private String beanPrefix;
	private String propertiesPrefix;
	private String[] eventHandlerPackages;
	private String[] eventDataPackages;
	private Collection<AutumnMqEventHandlerConfigureInfo> eventHandlerInfos;
	private Collection<AutumnMqEventDataConfigureInfo> eventDataInfos;
	private Map<String, Object> properties;
	private RabbitProperties rabbitProperties;
	private boolean primary;

	public boolean isPrimary() {
		return primary;
	}

	public void setPrimary(boolean primary) {
		this.primary = primary;
	}

	public AutumnRabbitConnectionInfo() {
		this.rabbitProperties = new RabbitProperties();
	}

	public String getBeanPrefix() {
		return beanPrefix;
	}

	public void setBeanPrefix(String beanPrefix) {
		this.beanPrefix = beanPrefix;
	}

	public String getPropertiesPrefix() {
		return propertiesPrefix;
	}

	public void setPropertiesPrefix(String propertiesPrefix) {
		this.propertiesPrefix = propertiesPrefix;
	}

	public String[] getEventHandlerPackages() {
		return eventHandlerPackages;
	}

	public void setEventHandlerPackages(String[] eventHandlerPackages) {
		this.eventHandlerPackages = eventHandlerPackages;
	}

	public String[] getEventDataPackages() {
		return eventDataPackages;
	}

	public void setEventDataPackages(String[] eventDataPackages) {
		this.eventDataPackages = eventDataPackages;
	}

	public Collection<AutumnMqEventHandlerConfigureInfo> getEventHandlerInfos() {
		return eventHandlerInfos;
	}

	public void setEventHandlerInfos(Collection<AutumnMqEventHandlerConfigureInfo> eventHandlerInfos) {
		this.eventHandlerInfos = eventHandlerInfos;
	}

	public Collection<AutumnMqEventDataConfigureInfo> getEventDataInfos() {
		return eventDataInfos;
	}

	public void setEventDataInfos(Collection<AutumnMqEventDataConfigureInfo> eventDataInfos) {
		this.eventDataInfos = eventDataInfos;
	}

	public void setProperties(Map<String, Object> properties) {
		this.properties = properties;
		if (this.properties != null) {
			this.rabbitProperties = new RabbitProperties();
			Object value = properties.get("addresses");
			if (value != null) {
				this.rabbitProperties.setAddresses(value.toString());
			}
			value = properties.get("host");
			if (value != null) {
				this.rabbitProperties.setHost(value.toString());
			}
			value = properties.get("port");
			if (value != null) {
				this.rabbitProperties.setPort(Integer.valueOf(value.toString().trim()));
			}
			value = properties.get("virtualHost");
			if (value != null) {
				this.rabbitProperties.setVirtualHost(value.toString());
			}
			value = properties.get("username");
			if (value != null) {
				this.rabbitProperties.setUsername(value.toString());
			}
			value = properties.get("password");
			if (value != null) {
				this.rabbitProperties.setPassword(value.toString());
			}
			value = properties.get("publisherConfirms");
			if (value != null) {
				this.rabbitProperties.setPublisherConfirms("true".equalsIgnoreCase(value.toString()));
			}
			value = properties.get("publisherReturns");
			if (value != null) {
				this.rabbitProperties.setPublisherReturns("true".equalsIgnoreCase(value.toString()));
			}
			value = properties.get("connectionTimeout");
			if (value != null) {
				this.rabbitProperties.setConnectionTimeout(Duration.ofSeconds(Long.valueOf(value.toString().trim())));
			}
			Object sslMapObject = properties.get("ssl");
			if (sslMapObject != null && sslMapObject instanceof Map) {
				@SuppressWarnings({ "unchecked" })
				Map<String, Object> sslMap = (Map<String, Object>) sslMapObject;
				this.setSslMap(this.rabbitProperties.getSsl(), sslMap);
			}
		}
	}

	private void setSslMap(Ssl ssl, Map<String, Object> sslMap) {
		Object value;
		value = sslMap.get("enabled");
		if (value != null) {
			ssl.setEnabled("true".equalsIgnoreCase(value.toString()));
		}
		value = sslMap.get("keyStore");
		if (value != null) {
			ssl.setKeyStore(value.toString());
		}
		value = sslMap.get("keyStoreType");
		if (value != null) {
			ssl.setKeyStoreType(value.toString());
		}
		value = sslMap.get("keyStorePassword");
		if (value != null) {
			ssl.setKeyStorePassword(value.toString());
		}
		value = sslMap.get("trustStore");
		if (value != null) {
			ssl.setTrustStore(value.toString());
		}
		value = sslMap.get("trustStoreType");
		if (value != null) {
			ssl.setTrustStoreType(value.toString());
		}
		value = sslMap.get("trustStorePassword");
		if (value != null) {
			ssl.setTrustStorePassword(value.toString());
		}
		value = sslMap.get("algorithm");
		if (value != null) {
			ssl.setAlgorithm(value.toString());
		}
		value = sslMap.get("validateServerCertificate");
		if (value != null) {
			ssl.setValidateServerCertificate("true".equalsIgnoreCase(value.toString()));
		}
		value = sslMap.get("verifyHostname");
		if (value != null) {
			ssl.setVerifyHostname("true".equalsIgnoreCase(value.toString()));
		}
	}

	public RabbitProperties getRabbitProperties() {
		return this.rabbitProperties;
	}

	private String getBeanName(String suffix) {
		if (StringUtils.isNullOrBlank(getBeanPrefix())) {
			return StringUtils.lowerCaseCapitalize(suffix);
		}
		return getBeanPrefix() + suffix;
	}

	/**
	 * 获取连接工厂BeanName
	 * 
	 * @return
	 *
	 */
	public String getConnectionFactoryBeanName() {
		return getBeanName(AutumnRabbitConnection.CONNECTION_FACTORY_BEAN_SUFFIX);
	}

	/**
	 * 获取连接工厂BeanName
	 * 
	 * @return
	 *
	 */
	public String getRabbitTemplateBeanName() {
		return getBeanName(AutumnRabbitConnection.RABBIT_TEMPLATE_BEAN_SUFFIX);
	}

	/**
	 * 获取 RabbitMessagingTemplate BeanName
	 * 
	 * @return
	 *
	 */
	public String getRabbitMessagingTemplateBeanName() {
		return getBeanName(AutumnRabbitConnection.RABBIT_MESSAGING_TEMPLATE_BEAN_SUFFIX);
	}

	/**
	 * 获取 RabbitAdmin BeanName
	 * 
	 * @return
	 *
	 */
	public String getRabbitAdminBeanName() {
		return getBeanName(AutumnRabbitConnection.RABBIT_ADMIN_BEAN_SUFFIX);
	}

	/**
	 * 获取 EventHandlerBeanFactory BeanName
	 * 
	 * @return
	 *
	 */
	public String getEventHandlerBeanFactoryBeanName() {
		return getBeanName(AutumnRabbitConnection.EVENT_HANDLER_BEAN_FACTORY_BEAN_SUFFIX);
	}

	/**
	 * 获取 MqEventBus BeanName
	 * 
	 * @return
	 *
	 */
	public String getMqEventBusBeanName() {
		return getBeanName(AutumnRabbitConnection.MQ_EVENT_BUS_BEAN_SUFFIX);
	}

	/**
	 * 获取消息转换
	 * 
	 * @return
	 *
	 */
	public String getMessageConverterBeanName() {
		return "rabbitMessageConverter";
	}

	/**
	 * 获取Mapping消息转换
	 * 
	 * @return
	 *
	 */
	public String getMappingMessageConverterBeanName() {
		return "rabbitMappingMessageConverter";
	}
}
