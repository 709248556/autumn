package com.autumn.mq.event;

import org.springframework.amqp.core.MessageProperties;

import com.autumn.util.TimeSpan;

/**
 * 事件总线
 * 
 * @author 老码农
 *
 *         2017-12-16 18:13:23
 */
public interface AutumnMqEventBus {

	/**
	 * 获取工厂
	 * 
	 * @return
	 *
	 */
	AutumnMqEventHandlerBeanFactory getFactory();

	/**
	 * 发送
	 * 
	 * @param eventData
	 *            对象
	 */
	void send(AutumnMqEventData eventData);

	/**
	 * 发送
	 * 
	 * @param eventData
	 *            发送的对象
	 * @param msaagegId
	 *            消息Id
	 */
	void send(AutumnMqEventData eventData, String msaagegId);

	/**
	 * 发送
	 * 
	 * @param eventData
	 *            发送的对象
	 * @param properties
	 *            消息属性
	 */
	void send(AutumnMqEventData eventData, MessageProperties properties);

	/**
	 * 发送
	 * 
	 * @param routingKey
	 *            路油key
	 * @param eventData
	 *            发送的对象
	 * @param properties
	 *            消息属性
	 */
	void send(String routingKey, AutumnMqEventData eventData, MessageProperties properties);

	/**
	 * 发送
	 * 
	 * @param eventData
	 *            事件数据
	 * @param delayMilliseconds
	 *            延迟毫秒数
	 */
	void send(AutumnMqEventData eventData, Long delayMilliseconds);

	/**
	 * 发送
	 * 
	 * @param eventData
	 *            事件数据
	 * @param delayTimeSpan
	 *            延迟时间间隔
	 */
	void send(AutumnMqEventData eventData, TimeSpan delayTimeSpan);

	/**
	 * 发送
	 * 
	 * @param eventData
	 *            事件数据
	 * @param msaagegId
	 *            消息Id
	 * @param delayMilliseconds
	 *            延迟毫秒数
	 */
	void send(AutumnMqEventData eventData, String msaagegId, Long delayMilliseconds);

	/**
	 * 发送
	 * 
	 * @param eventData
	 *            事件数据
	 * @param msaagegId
	 *            消息Id
	 * @param delayTimeSpan
	 *            延迟时间间隔
	 */
	void send(AutumnMqEventData eventData, String msaagegId, TimeSpan delayTimeSpan);

	/**
	 * 发送
	 * 
	 * @param eventData
	 *            事件数据
	 * @param properties
	 *            属性
	 * @param delayTimeSpan
	 *            延迟时间间隔
	 */
	void send(AutumnMqEventData eventData, MessageProperties properties, TimeSpan delayTimeSpan);

	/**
	 * 发送
	 * 
	 * @param eventData
	 *            事件数据
	 * @param properties
	 *            属性
	 * @param delayMilliseconds
	 *            延迟毫秒数
	 */
	void send(AutumnMqEventData eventData, MessageProperties properties, Long delayMilliseconds);

	/**
	 * 发送
	 * 
	 * @param routingKey
	 *            路油key
	 * @param eventData
	 *            事件数据
	 * @param properties
	 *            属性
	 * @param delayMilliseconds
	 *            延迟毫秒数
	 */
	void send(String routingKey, AutumnMqEventData eventData, MessageProperties properties, Long delayMilliseconds);

}
