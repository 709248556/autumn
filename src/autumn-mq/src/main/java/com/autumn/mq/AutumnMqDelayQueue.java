package com.autumn.mq;

import org.springframework.amqp.core.MessageProperties;

import com.autumn.util.TimeSpan;

/**
 * 支持延迟发送的列队定义
 * 
 * @author 老码农
 *         <p>
 *         Description
 *         </p>
 * @date 2017-12-10 16:40:55
 */
public interface AutumnMqDelayQueue extends AutumnMqQueue {

	/**
	 * 获取延迟队列名称
	 * 
	 * @return
	 */
	String getDelayQueueName();

	/**
	 * 获取延迟队列交换器名称
	 * 
	 * @return
	 */
	String getDelayQueueExchangeName();

	/**
	 * 发送
	 * 
	 * @param object
	 *            消息对象
	 * @param delayMilliseconds
	 *            延迟毫秒数
	 */
	void send(Object object, Long delayMilliseconds);

	/**
	 * 发送
	 * 
	 * @param object
	 *            消息对象
	 * @param delayTimeSpan
	 *            延迟时间间隔
	 */
	void send(Object object, TimeSpan delayTimeSpan);

	/**
	 * 发送
	 * 
	 * @param object
	 *            消息对象
	 * @param msaagegId
	 *            消息Id
	 * @param delayMilliseconds
	 *            延迟毫秒数
	 */
	void send(Object object, String msaagegId, Long delayMilliseconds);

	/**
	 * 发送
	 * 
	 * @param object
	 *            消息对象
	 * @param msaagegId
	 *            消息Id
	 * @param delayTimeSpan
	 *            延迟时间间隔
	 */
	void send(Object object, String msaagegId, TimeSpan delayTimeSpan);

	/**
	 * 发送
	 * 
	 * @param object
	 *            消息对象
	 * @param properties
	 *            属性
	 * @param delayTimeSpan
	 *            延迟时间间隔
	 */
	void send(Object object, MessageProperties properties, TimeSpan delayTimeSpan);

	/**
	 * 发送
	 * 
	 * @param object
	 *            消息对象
	 * @param properties
	 *            属性
	 * @param delayMilliseconds
	 *            延迟毫秒数
	 */
	void send(Object object, MessageProperties properties, Long delayMilliseconds);

	/**
	 * 发送
	 * 
	 * @param routingKey
	 *            路油Key
	 * @param object
	 *            消息对象
	 * @param properties
	 *            属性
	 * @param delayMilliseconds
	 *            延迟毫秒数
	 */
	void send(String routingKey, Object object, MessageProperties properties, Long delayMilliseconds);
}
