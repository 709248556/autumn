package com.autumn.spring.boot.properties;

/**
 * 属性常量
 * 
 * @author 老码农
 *
 *         2018-02-01 11:59:26
 */
public class PropertiesConstant {

	/**
	 * rabbit mq 后缀
	 */
	private final static String RABBIT_MQ_SUFFIX = ".rabbitmq";

	/**
	 * redis 后缀
	 */
	private final static String REDIS_SUFFIX = ".redis";

	/**
	 * Message Bean 属性前缀
	 */
	public final static String AUTUMN_MESSAGE_BENA_PREFIX = "autumnMessage";

	/**
	 * Message 属性前缀
	 */
	public final static String AUTUMN_MESSAGE_PROPERTIES_PREFIX = "autumn.message";

	/**
	 * Message Rabbit 属性前缀(短信\邮件\日志\登录\注册\注销\配置等相应的通知)
	 */
	public final static String AUTUMN_MESSAGE_RABBIT_MQ_PROPERTIES_PREFIX = AUTUMN_MESSAGE_PROPERTIES_PREFIX
			+ RABBIT_MQ_SUFFIX;

	/**
	 * Cloud Bean 属性前缀
	 */
	public final static String AUTUMN_CLOUD_BENA_PREFIX = "autumnCloud";

	/**
	 * Cloud 属性前缀
	 */
	public final static String AUTUMN_CLOUD_PROPERTIES_PREFIX = "autumn.cloud";

	/**
	 * Cloud Rabbit 属性前缀(基于分布式(云)的Rabbit mq)
	 */
	public final static String AUTUMN_CLOUD_RABBIT_MQ_PROPERTIES_PREFIX = AUTUMN_CLOUD_PROPERTIES_PREFIX
			+ RABBIT_MQ_SUFFIX;

	/**
	 * Cloud redis 属性前缀(基于分布式(云)的redis)
	 */
	public final static String AUTUMN_CLOUD_REDIS_PROPERTIES_PREFIX = "spring.redis";
}
