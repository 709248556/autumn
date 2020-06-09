package com.autumn.mq;

import org.springframework.amqp.core.AcknowledgeMode;
import org.springframework.amqp.core.Message;

/**
 * 通道消息监听
 * 
 * @author 老码农
 *         <p>
 *         Description
 *         </p>
 * @date 2017-12-11 03:19:32
 */
@FunctionalInterface
public interface AutumnMqChannelMessageListener<TQueue extends AutumnMqQueue> {

	/**
	 * 处理消息
	 * 
	 * @param message
	 *            消息
	 * @param channel
	 *            通道
	 * @throws Exception
	 *             异常
	 */
	void onMessage(Message message, AutumnMqChannel<TQueue> channel) throws Exception;

	/**
	 * 获取回复模式
	 * 
	 * @return 默认 MANUAL
	 */
	default AcknowledgeMode getAcknowledgeMode() {
		return AcknowledgeMode.MANUAL;
	}

	/**
	 * 每次并发处理的消费数量,
	 * 
	 * @return 返回，默认为 1
	 */
	default int getConcurrentConsumers() {
		return 1;
	}
}
