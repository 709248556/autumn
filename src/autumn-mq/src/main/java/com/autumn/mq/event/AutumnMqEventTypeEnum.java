package com.autumn.mq.event;

/**
 * 事件类型
 * 
 * @author 老码农
 *
 *         2017-12-16 14:32:04
 */
public enum AutumnMqEventTypeEnum {

	/**
	 * 精确匹配
	 */
	DIRECT,
	/**
	 * 主题模式
	 */
	TOPIC,
	/**
	 * 广播模式
	 */
	BROADCAST
}
