package com.autumn.mq.rabbit;

import java.util.UUID;

import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.Exchange;
import org.springframework.amqp.core.ExchangeTypes;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.core.MessageProperties;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.rabbit.core.RabbitAdmin;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.rabbit.support.CorrelationData;

import com.autumn.mq.AutumnMqQueue;
import com.autumn.util.StringUtils;

/**
 * Rabbit Queue 定义
 * 
 * @author 老码农
 *         <p>
 *         Description
 *         </p>
 * @date 2017-12-10 16:58:32
 */
public abstract class AbstractRabbitAutumnQueue implements AutumnMqQueue {

	private final AbstractRabbitQueueDeclareFactory factory;
	private final Queue queue;
	private RabbitTemplate rabbitTemplate;

	private boolean isInitialize = false;

	/**
	 * 
	 * @param factory
	 */
	public AbstractRabbitAutumnQueue(AbstractRabbitQueueDeclareFactory factory) {
		this.factory = factory;
		if (StringUtils.isNullOrBlank((factory.getName()))) {
			this.queue = null;
		} else {
			this.queue = new Queue(factory.getName(), factory.isDurable(), factory.isExclusive(),
					factory.isAutoDelete());
		}
	}

	@Override
	public String getName() {
		return factory.getName();
	}

	@Override
	public boolean isDurable() {
		return factory.isDurable();
	}

	@Override
	public boolean isExclusive() {
		return factory.isExclusive();
	}

	@Override
	public boolean isAutoDelete() {
		return factory.isAutoDelete();
	}

	@Override
	public String getExchangeName() {
		return factory.getExchangeName();
	}

	/**
	 * 获取队列
	 * 
	 * @return
	 */
	public Queue getQueue() {
		return queue;
	}

	/**
	 * 获取交换器
	 * 
	 * @return
	 */
	public abstract Exchange getExchange();

	@Override
	public final String getExchangeType() {
		return this.getExchange().getType();
	}

	@Override
	public final boolean isTopicType() {
		return ExchangeTypes.TOPIC.equalsIgnoreCase(this.getExchangeType());
	}

	@Override
	public final boolean isDirectType() {
		return ExchangeTypes.DIRECT.equalsIgnoreCase(this.getExchangeType());
	}

	@Override
	public final boolean isFanoutType() {
		return ExchangeTypes.FANOUT.equalsIgnoreCase(this.getExchangeType());
	}

	@Override
	public final boolean isHeadersType() {
		return ExchangeTypes.HEADERS.equalsIgnoreCase(this.getExchangeType());
	}

	@Override
	public final boolean isSystemType() {
		return ExchangeTypes.SYSTEM.equalsIgnoreCase(this.getExchangeType());
	}

	/**
	 * 获取绑定
	 * 
	 * @return
	 */
	public abstract Binding getBinding();

	/**
	 * 获取工厂
	 * 
	 * @return
	 */
	public AbstractRabbitQueueDeclareFactory getFactory() {
		return factory;
	}

	@Override
	public final void send(Object object) {
		this.send(object, (String) null);
	}

	/**
	 * 读取重复发送次数
	 * 
	 * @param properties
	 *            属性
	 * @return
	 */
	@Override
	public long getOrInitializeRepeatSendCount(MessageProperties properties) {
		if (properties == null) {
			return 0L;
		}
		Object value = properties.getHeaders().get(AutumnMqQueue.X_REPEAT_SEND_COUNT);
		if (value == null || !(value instanceof Long)) {
			long count = 0L;
			properties.setHeader(AutumnMqQueue.X_REPEAT_SEND_COUNT, count);
			return count;
		} else {
			return (Long) value;
		}
	}

	/**
	 * 设置重复发送次数
	 * 
	 * @param properties
	 *            属性
	 */
	@Override
	public void setRepeatSendCount(MessageProperties properties, long count) {
		if (properties != null) {
			properties.setHeader(AutumnMqQueue.X_REPEAT_SEND_COUNT, count);
		}
	}

	@Override
	public final void send(Object object, String msaagegId) {
		MessageProperties properties = new MessageProperties();
		properties.setMessageId(msaagegId);
		this.send(object, properties);
	}

	/**
	 * 发送
	 * 
	 * @param properties
	 *            属性
	 * @param object
	 *            对象
	 */
	@Override
	public void send(Object object, MessageProperties properties) {
		this.send(null, object, properties);
	}

	@Override
	public void send(String routingKey, Object object, MessageProperties properties) {
		if (properties == null) {
			properties = new MessageProperties();
		}
		if (StringUtils.isNullOrBlank(properties.getMessageId())) {
			properties.setMessageId(UUID.randomUUID().toString().replace("-", ""));
		}
		this.getOrInitializeRepeatSendCount(properties);
		properties.setExpiration(null);
		CorrelationData idData = new CorrelationData(properties.getMessageId());
		RabbitTemplate template = getRabbitTemplate();
		this.initialize();
		Message msg = template.getMessageConverter().toMessage(object, properties);
		if (this.isTopicType()) {
			if (routingKey == null) {
				routingKey = this.getName();
			}
			if (!StringUtils.isNullOrBlank(routingKey)) {
				template.send(this.getExchangeName(), routingKey, msg, idData);
			}
		} else if (this.isFanoutType()) {
			template.send(this.getExchangeName(), "", msg, idData);
		} else {
			if (!StringUtils.isNullOrBlank(this.getName())) {
				template.send("", this.getName(), msg, idData);
			}
		}
	}

	/**
	 * 获取模板
	 * 
	 * @return
	 */
	public final RabbitTemplate getRabbitTemplate() {
		return this.rabbitTemplate;
	}

	/**
	 * 设置模板
	 * 
	 * @param rabbitTemplate
	 *            模板
	 */
	public void setRabbitTemplate(RabbitTemplate rabbitTemplate) {
		this.rabbitTemplate = rabbitTemplate;
	}

	@Override
	public final void initialize() {
		if (this.isInitialize) {
			return;
		}
		synchronized (this) {
			if (this.isInitialize) {
				return;
			}
			RabbitAdmin rabbitAdmin = new RabbitAdmin(this.getRabbitTemplate().getConnectionFactory());
			initialize(rabbitAdmin);
			this.isInitialize = true;
		}
	}

	/**
	 * 初始化
	 * 
	 *
	 */
	protected void initialize(RabbitAdmin rabbitAdmin) {
		if (this.getExchange() != null) {
			rabbitAdmin.declareExchange(this.getExchange());
		}
		if (this.getQueue() != null) {
			rabbitAdmin.declareQueue(this.getQueue());
		}
		if (this.getBinding() != null) {
			rabbitAdmin.declareBinding(this.getBinding());
		}
	}
}
