package com.autumn.mq;

import org.springframework.amqp.core.MessageProperties;

/**
 * 队列声明
 * 
 * @author 老码农
 *         <p>
 *         Description
 *         </p>
 * @date 2017-12-10 15:06:16
 */
public interface AutumnMqQueue {

	/**
	 * 重复发送次数
	 */
	public final static String X_REPEAT_SEND_COUNT = "x-repeat-send-count";

	/**
	 * 获取名称
	 * 
	 * @return
	 */
	String getName();

	/**
	 * 获取交换器名称
	 * 
	 * @return
	 */
	String getExchangeName();

	/**
	 * 获取交换器类型
	 * 
	 * @return
	 */
	String getExchangeType();

	/**
	 * 是否是 Topic 类型
	 * 
	 * @return
	 *
	 */
	boolean isTopicType();

	/**
	 * 是否是 Direct 类型
	 * 
	 * @return
	 *
	 */
	boolean isDirectType();

	/**
	 * 是否是 fanout 类型
	 * 
	 * @return
	 *
	 */
	boolean isFanoutType();

	/**
	 * 是否是 headers 类型
	 * 
	 * @return
	 *
	 */
	boolean isHeadersType();

	/**
	 * 是否是 system 类型
	 * 
	 * @return
	 *
	 */
	boolean isSystemType();

	/**
	 * 是否持久化
	 * 
	 * @return
	 */
	boolean isDurable();

	/**
	 * 是否排它性，即只有本实例能访问
	 * 
	 * @return
	 */
	boolean isExclusive();

	/**
	 * 是否自动删除
	 * 
	 * @return
	 */
	boolean isAutoDelete();

	/**
	 * 初始化
	 */
	void initialize();

	/**
	 * 发送
	 * 
	 * @param object
	 *            对象
	 */
	void send(Object object);

	/**
	 * 发送
	 * 
	 * @param object
	 *            发送的对象
	 * @param msaagegId
	 *            消息Id
	 */
	void send(Object object, String msaagegId);

	/**
	 * 发送
	 * 
	 * @param object
	 *            发送的对象
	 * @param properties
	 *            消息属性
	 */
	void send(Object object, MessageProperties properties);

	/**
	 * 发送
	 * 
	 * @param routingKey
	 *            路油id
	 * @param object
	 *            发送的对象
	 * @param properties
	 *            消息属性
	 */
	void send(String routingKey, Object object, MessageProperties properties);

	/**
	 * 读取或初始化重复发送次数
	 * 
	 * @param properties
	 *            属性
	 * @return
	 */
	long getOrInitializeRepeatSendCount(MessageProperties properties);

	/**
	 * 设置重复发送次数
	 * 
	 * @param properties
	 *            属性
	 * @param count
	 *            属性次数
	 */
	void setRepeatSendCount(MessageProperties properties, long count);

}
