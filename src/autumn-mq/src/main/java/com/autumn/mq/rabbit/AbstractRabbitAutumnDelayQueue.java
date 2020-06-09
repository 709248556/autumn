package com.autumn.mq.rabbit;

import java.util.UUID;

import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.Exchange;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.core.MessageProperties;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.rabbit.core.RabbitAdmin;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.rabbit.support.CorrelationData;

import com.autumn.mq.AutumnMqDelayQueue;
import com.autumn.util.StringUtils;
import com.autumn.util.TimeSpan;

/**
 * 具有延迟 Rabbit Queue 定义
 * 
 * @author 老码农
 *         <p>
 *         Description
 *         </p>
 * @date 2017-12-10 16:58:13
 */
public abstract class AbstractRabbitAutumnDelayQueue extends AbstractRabbitAutumnQueue implements AutumnMqDelayQueue {

	/**
	 * 
	 * @param factory
	 */
	public AbstractRabbitAutumnDelayQueue(AbstractRabbitQueueDeclareFactory factory) {
		super(factory);
	}

	@Override
	public String getDelayQueueName() {
		return this.getFactory().getDelayQueueName();
	}

	@Override
	public String getDelayQueueExchangeName() {
		return this.getFactory().getDelayQueueExchangeName();
	}

	/**
	 * 获取延迟队列
	 * 
	 * @return
	 */
	public abstract Queue getDelayQueue();

	/**
	 * 获取延迟队列交机器
	 * 
	 * @return
	 */
	public abstract Exchange getDelayExchange();

	/**
	 * 获取延迟绑定
	 * 
	 * @return
	 */
	public abstract Binding getDelayBinding();

	/**
	 * 发送
	 * 
	 * @param object
	 *            消息对象
	 * @param delayMilliseconds
	 *            延迟毫秒数
	 */
	@Override
	public final void send(Object object, Long delayMilliseconds) {
		this.send(object, (String) null, delayMilliseconds);
	}

	/**
	 * 发送
	 * 
	 * @param object
	 *            消息对象
	 * @param delayTimeSpan
	 *            延迟时间间隔
	 */
	@Override
	public final void send(Object object, TimeSpan delayTimeSpan) {
		this.send(object, (String) null, delayTimeSpan);
	}

	@Override
	public final void send(Object object, String msaagegId, Long delayMilliseconds) {
		MessageProperties properties = new MessageProperties();
		properties.setMessageId(msaagegId);
		this.send(object, properties, delayMilliseconds);
	}

	@Override
	public final void send(Object object, String msaagegId, TimeSpan delayTimeSpan) {
		if (delayTimeSpan != null) {
			this.send(object, msaagegId, delayTimeSpan.getTotalMilliseconds());
		} else {
			this.send(object, msaagegId, 0L);
		}
	}

	@Override
	public final void send(Object object, MessageProperties properties, TimeSpan delayTimeSpan) {
		if (delayTimeSpan != null) {
			this.send(object, properties, delayTimeSpan.getTotalMilliseconds());
		} else {
			this.send(object, properties, 0L);
		}
	}

	@Override
	public void send(Object object, MessageProperties properties, Long delayMilliseconds) {
		this.send(null, object, properties, delayMilliseconds);
	}

	@Override
	public void send(String routingKey, Object object, MessageProperties properties, Long delayMilliseconds) {
		if (this.isFanoutType()) {
			this.send(routingKey, object, properties);
			return;
		}
		if (delayMilliseconds != null && delayMilliseconds.longValue() > 0L) {
			if (properties == null) {
				properties = new MessageProperties();
			}
			if (StringUtils.isNullOrBlank(properties.getMessageId())) {
				properties.setMessageId(UUID.randomUUID().toString());
			}
			properties.setExpiration(delayMilliseconds.toString());
			this.getOrInitializeRepeatSendCount(properties);
			CorrelationData idData = new CorrelationData(properties.getMessageId());
			RabbitTemplate template = this.getRabbitTemplate();
			this.initialize();
			Message msg = template.getMessageConverter().toMessage(object, properties);
			if (this.isTopicType()) {
				if (routingKey == null) {
					routingKey = this.getName();
				}
				template.send(this.getDelayQueueExchangeName(), routingKey, msg, idData);
			} else {
				template.send(this.getDelayQueueExchangeName(), this.getName(), msg, idData);
			}
		} else {
			this.send(routingKey, object, properties);
		}
	}

	@Override
	protected void initialize(RabbitAdmin rabbitAdmin) {
		super.initialize(rabbitAdmin);
		if (this.getDelayExchange() != null) {
			rabbitAdmin.declareExchange(this.getDelayExchange());
		}
		if (this.getDelayQueue() != null) {
			rabbitAdmin.declareQueue(this.getDelayQueue());
		}
		if (this.getDelayBinding() != null) {
			rabbitAdmin.declareBinding(this.getDelayBinding());
		}
	}
}
