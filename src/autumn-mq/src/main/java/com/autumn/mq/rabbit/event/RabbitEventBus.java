package com.autumn.mq.rabbit.event;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.amqp.core.MessageProperties;

import com.autumn.mq.AutumnMqDelayQueue;
import com.autumn.mq.event.AutumnMqEventData;
import com.autumn.mq.event.AutumnMqEventHandlerBeanFactory;
import com.autumn.mq.event.AutumnMqEventBus;
import com.autumn.exception.ExceptionUtils;
import com.autumn.util.TimeSpan;

/**
 * 
 * @author 老码农
 *
 *         2017-12-16 18:16:03
 */
public class RabbitEventBus implements AutumnMqEventBus {

	private final static Log logger = LogFactory.getLog(RabbitEventBus.class);

	private AutumnMqEventHandlerBeanFactory factory;

	public RabbitEventBus(AutumnMqEventHandlerBeanFactory factory) {
		this.factory = factory;
	}

	/**
	 * 获取事件处理工厂
	 * 
	 * @return
	 *
	 */
	@Override
	public AutumnMqEventHandlerBeanFactory getFactory() {
		return factory;
	}

	private AutumnMqDelayQueue getQueue(AutumnMqEventData eventData) {
		ExceptionUtils.checkNotNull(eventData, "eventData");
		return this.factory.getDelayQueue(eventData.getClass());
	}

	@Override
	public void send(AutumnMqEventData eventData) {
		AutumnMqDelayQueue queue = getQueue(eventData);
		queue.send(eventData);
		if (logger.isDebugEnabled()) {
			logger.debug("触发事件：" + queue.getName());
		}
	}	

	@Override
	public void send(AutumnMqEventData eventData, String msaagegId) {
		AutumnMqDelayQueue queue = getQueue(eventData);
		queue.send(eventData, msaagegId);
		if (logger.isDebugEnabled()) {
			logger.debug("触发事件：" + queue.getName());
		}
	}

	@Override
	public void send(AutumnMqEventData eventData, MessageProperties properties) {
		AutumnMqDelayQueue queue = getQueue(eventData);
		queue.send(eventData, properties);
		if (logger.isDebugEnabled()) {
			logger.debug("触发事件：" + queue.getName());
		}
	}

	@Override
	public void send(String routingKey, AutumnMqEventData eventData, MessageProperties properties) {
		AutumnMqDelayQueue queue = getQueue(eventData);
		queue.send(routingKey, eventData, properties);
		if (logger.isDebugEnabled()) {
			logger.debug("触发事件：" + queue.getName());
		}
	}

	@Override
	public void send(AutumnMqEventData eventData, Long delayMilliseconds) {
		AutumnMqDelayQueue queue = getQueue(eventData);
		queue.send(eventData, delayMilliseconds);
		if (logger.isDebugEnabled()) {
			logger.debug("触发事件：" + queue.getName() + " 延迟:" + delayMilliseconds + " 毫秒");
		}

	}

	@Override
	public void send(AutumnMqEventData eventData, TimeSpan delayTimeSpan) {
		AutumnMqDelayQueue queue = getQueue(eventData);
		queue.send(eventData, delayTimeSpan);
		if (logger.isDebugEnabled()) {
			if (delayTimeSpan != null) {
				logger.debug("触发事件：" + queue.getName() + " 延迟:" + delayTimeSpan.getTotalMilliseconds() + " 毫秒");
			} else {
				logger.debug("触发事件：" + queue.getName());
			}
		}
	}

	@Override
	public void send(AutumnMqEventData eventData, String msaagegId, Long delayMilliseconds) {
		AutumnMqDelayQueue queue = getQueue(eventData);
		queue.send(eventData, msaagegId, delayMilliseconds);
		if (logger.isDebugEnabled()) {
			logger.debug("触发事件：" + queue.getName() + " 延迟:" + delayMilliseconds + " 毫秒");
		}
	}

	@Override
	public void send(AutumnMqEventData eventData, String msaagegId, TimeSpan delayTimeSpan) {
		AutumnMqDelayQueue queue = getQueue(eventData);
		queue.send(eventData, msaagegId, delayTimeSpan);
		if (logger.isDebugEnabled()) {
			if (delayTimeSpan != null) {
				logger.debug("触发事件：" + queue.getName() + " 延迟:" + delayTimeSpan.getTotalMilliseconds() + " 毫秒");
			} else {
				logger.debug("触发事件：" + queue.getName());
			}
		}
	}

	@Override
	public void send(AutumnMqEventData eventData, MessageProperties properties, Long delayMilliseconds) {
		AutumnMqDelayQueue queue = getQueue(eventData);
		queue.send(eventData, properties, delayMilliseconds);
		if (logger.isDebugEnabled()) {
			logger.debug("触发事件：" + queue.getName() + " 延迟:" + delayMilliseconds + " 毫秒");
		}
	}

	@Override
	public void send(AutumnMqEventData eventData, MessageProperties properties, TimeSpan delayTimeSpan) {
		AutumnMqDelayQueue queue = getQueue(eventData);
		queue.send(eventData, properties, delayTimeSpan);
		if (logger.isDebugEnabled()) {
			if (delayTimeSpan != null) {
				logger.debug("触发事件：" + queue.getName() + " 延迟:" + delayTimeSpan.getTotalMilliseconds() + " 毫秒");
			} else {
				logger.debug("触发事件：" + queue.getName());
			}
		}
	}

	@Override
	public void send(String routingKey, AutumnMqEventData eventData, MessageProperties properties, Long delayMilliseconds) {
		AutumnMqDelayQueue queue = getQueue(eventData);
		queue.send(routingKey, eventData, properties, delayMilliseconds);
		if (logger.isDebugEnabled()) {
			logger.debug("触发事件：" + queue.getName() + " 延迟:" + delayMilliseconds + " 毫秒");
		}
	}

}
