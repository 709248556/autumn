package com.autumn.mq.event;

/**
 * 事件处理配置信息
 * 
 * @author 老码农
 *         <p>
 *         Description
 *         </p>
 * @date 2018-01-25 21:45:42
 */
public interface AutumnMqEventHandlerConfigureInfo extends AutumnMqEventDataConfigureInfo {

	/**
	 * 获取每次并发处理的消费数量
	 * 
	 * @return
	 */
	int getConcurrentConsumers();

	/**
	 * 获取处理器类型
	 * 
	 * @return
	 */
	Class<? extends AutumnMqEventHandler<?>> getEventHandlerType();
}
