package com.autumn.mq.event;

/**
 * 事件数据配置信息
 * 
 * @author 老码农
 *         <p>
 *         Description
 *         </p>
 * @date 2018-01-25 21:41:47
 */
public interface AutumnMqEventDataConfigureInfo {

	/**
	 * 获取队列名称
	 * 
	 * @return
	 *
	 */
	String getName();

	/**
	 * 获取路油Id
	 * <p>
	 * {@link AutumnMqEventTypeEnum.TOPIC} 根据生产者与消费者进行相应的配置
	 * </p>
	 * 
	 * @return
	 *
	 */
	String getRoutingKey();

	/**
	 * 获取交换器后缀 {@link AutumnMqEventTypeEnum.TOPIC} 根据生产者与消费者进行相应的配置
	 * 
	 * @return
	 *
	 */
	String getExchangeSuffix();

	/**
	 * 获取事件类型
	 * 
	 * @return
	 *
	 */
	AutumnMqEventTypeEnum getEventType();

	/**
	 * 获取事件数据类型
	 * 
	 * @return
	 */
	Class<? extends AutumnMqEventData> getEventDataType();
}
