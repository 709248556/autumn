package com.autumn.mq.rabbit.annotation;

import java.lang.annotation.Documented;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * AutumnRabbit 连接
 * 
 * @author 老码农
 *
 *         2018-01-11 18:05:52
 */
@Documented
@Retention(RetentionPolicy.RUNTIME)
@Target({})
public @interface AutumnRabbitConnection {

	/**
	 * 连接工厂后缀
	 */
	public final static String CONNECTION_FACTORY_BEAN_SUFFIX = "RabbitConnectionFactory";

	/**
	 * 模板后缀
	 */
	public final static String RABBIT_TEMPLATE_BEAN_SUFFIX = "RabbitTemplate";

	/**
	 * 消息模板后缀
	 */
	public final static String RABBIT_MESSAGING_TEMPLATE_BEAN_SUFFIX = "RabbitMessagingTemplate";

	/**
	 * Admin 后缀
	 */
	public final static String RABBIT_ADMIN_BEAN_SUFFIX = "RabbitAdmin";

	/**
	 * 事件处理Bean工厂后缀
	 */
	public final static String EVENT_HANDLER_BEAN_FACTORY_BEAN_SUFFIX = "EventHandlerBeanFactory";

	/**
	 * Mq事件总线后缀
	 */
	public final static String MQ_EVENT_BUS_BEAN_SUFFIX = "MqEventBus";

	/**
	 * 默认 Bean 前缀
	 */
	public final static String DEFAULT_BEAN_PREFIX = "autumn";

	/**
	 * 默认 rabbitmq 属性前缀
	 */
	public final static String DEFAULT_RABBITMQ_PROPERTIES_PREFIX = "spring.rabbitmq";

	/**
	 * Bean 名称前缀
	 * 
	 * @return
	 *
	 */
	String beanPrefix() default DEFAULT_BEAN_PREFIX;

	/**
	 * 属性配置前缀
	 * 
	 * @return 未配置默认 spring.rabbitmq
	 *
	 */
	String propertiesPrefix() default DEFAULT_RABBITMQ_PROPERTIES_PREFIX;

	/**
	 * 事件处理器包路径集合(具有事件生产者与消费者时使用，仅用生产者时可不配置)
	 * 
	 * @return
	 *
	 */
	String[] eventHandlerPackages();

	/**
	 * 事件数据包路径集合(仅有事件生产者时使用)
	 * 
	 * @return
	 */
	String[] eventDataPackages();

	/**
	 * 是否默认
	 * 
	 * @return
	 *
	 */
	boolean primary() default true;
}
