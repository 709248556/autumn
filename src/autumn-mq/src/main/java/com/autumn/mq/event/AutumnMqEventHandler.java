package com.autumn.mq.event;

import org.apache.commons.logging.Log;

/**
 * 事件处理程序
 * <p>
 * 参数名称 TEventData 不能修改
 * </p>
 * 
 * @author 老码农
 *
 *         2017-12-16 14:38:02
 */
public interface AutumnMqEventHandler<TEventData extends AutumnMqEventData> {

	/**
	 * 
	 */
	public final static long DEFAUL_AUTO_REPEAT_SEND_INTERVAL = 1000 * 5L;

	/**
	 * 是否自动确认
	 * 
	 * @return
	 *
	 */
	default boolean autoAck() {
		return true;
	};

	/**
	 * 错误是否自动重发
	 * 
	 * @return
	 *
	 */
	boolean errorAutoRepeatSend();

	/**
	 * 获取错误重新发送的时间间隔(毫秒，默认5秒)
	 * 
	 * @return
	 *
	 */
	default Long getAutoRepeatSendInterval() {
		return DEFAUL_AUTO_REPEAT_SEND_INTERVAL;
	};

	/**
	 * 获取日志
	 * 
	 * @return
	 */
	Log getLogger();

	/**
	 * 处理
	 * 
	 * @param message
	 *            消息
	 *
	 */
	void onHandler(AutumnMqEventMessage<TEventData> message);
}
