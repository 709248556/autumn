package com.autumn.mq.rabbit;

import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.core.ChannelAwareMessageListener;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.rabbit.listener.SimpleMessageListenerContainer;

import com.autumn.mq.AutumnMqChannel;
import com.autumn.mq.AutumnMqChannelMessageListener;
import com.autumn.mq.AutumnMqDelayQueue;
import com.autumn.mq.AutumnMqQueue;
import com.autumn.exception.ExceptionUtils;
import com.autumn.util.StringUtils;
import com.rabbitmq.client.Channel;

/**
 * 队列定义抽象工厂
 * 
 * @author 老码农
 *         <p>
 *         Description
 *         </p>
 * @date 2017-12-10 17:34:01
 */
public abstract class AbstractRabbitQueueDeclareFactory {

	public final static String DELAY = "delay.";

	private final String name;
	private final boolean durable;
	private final boolean exclusive;
	private final boolean autoDelete;
	private final String exchangeName;
	private final String delayQueueName;
	private final String delayQueueExchangeName;

	/**
	 * 实例化
	 * 
	 * @param name
	 *            名称
	 * @param exchangeName
	 *            交换器名称
	 * @param delayQueueName
	 *            延迟队列名称
	 * @param delayQueueExchangeName
	 *            延迟队列交换器名称
	 * @param durable
	 *            是否持久化
	 * @param exclusive
	 *            是否排它性，即只有本实例能访问
	 * @param autoDelete
	 *            是否自动删除
	 */
	protected AbstractRabbitQueueDeclareFactory(String name, String exchangeName, String delayQueueName,
			String delayQueueExchangeName, boolean durable, boolean exclusive, boolean autoDelete) {
		this.name = name;
		this.exchangeName = ExceptionUtils.checkNotNullOrBlank(exchangeName, "exchangeName");
		this.delayQueueName = delayQueueName;
		this.delayQueueExchangeName = delayQueueExchangeName;
		this.durable = durable;
		this.exclusive = exclusive;
		this.autoDelete = autoDelete;
	}

	/**
	 * 实例化
	 * 
	 * @param name
	 *            名称队列名称
	 * @param exchangeType
	 *            交换器类型(与 exchangeSuffix 共同组成名称)
	 * @param exchangeSuffix
	 *            交换器后缀(与 exchangeType 共同组成名称)
	 * @param durable
	 *            是否持久化
	 * @param exclusive
	 *            是否排它性，即只有本实例能访问
	 * @param autoDelete
	 *            是否自动删除
	 */
	public AbstractRabbitQueueDeclareFactory(String name, String exchangeType, String exchangeSuffix, boolean durable,
			boolean exclusive, boolean autoDelete) {
		this(name, exchangeType + "." + exchangeSuffix,
				(StringUtils.isNullOrBlank(exchangeSuffix) ? DELAY + name : DELAY + exchangeSuffix),
				exchangeType + "." + DELAY + exchangeSuffix, durable, exclusive, autoDelete);
	}

	/**
	 * 获取名称
	 * 
	 * @return
	 */
	public String getName() {
		return name;
	}

	/**
	 * 是否持久化
	 * 
	 * @return
	 */
	public boolean isDurable() {
		return durable;
	}

	/**
	 * 是否排它性，即只有本实例能访问
	 * 
	 * @return
	 */
	public boolean isExclusive() {
		return exclusive;
	}

	/**
	 * 是否自动删除
	 * 
	 * @return
	 */
	public boolean isAutoDelete() {
		return autoDelete;
	}

	/**
	 * 获取交换器
	 * 
	 * @return
	 */
	public String getExchangeName() {
		return exchangeName;
	}

	/**
	 * 获取延迟队列名称
	 * 
	 * @return
	 */
	public String getDelayQueueName() {
		return delayQueueName;
	}

	/**
	 * 获取延迟队列交换器
	 * 
	 * @return
	 */
	public String getDelayQueueExchangeName() {
		return delayQueueExchangeName;
	}

	/**
	 * 声明
	 * 
	 * @param rabbitTemplate
	 *            模板
	 * @return
	 */
	public abstract AutumnMqQueue declare(RabbitTemplate rabbitTemplate);

	/**
	 * 支持延迟声明
	 * 
	 * @param rabbitTemplate
	 *            模板
	 * @return
	 */
	public abstract AutumnMqDelayQueue delayDeclare(RabbitTemplate rabbitTemplate);

	/**
	 * 创建消息监听
	 * 
	 * @param connectionFactory
	 *            连接工厂
	 * @param qutumnQueue
	 *            队列
	 * @param messageListener
	 *            消息监听
	 * @return
	 */
	public <TQueue extends AutumnMqQueue> SimpleMessageListenerContainer createMessageListener(
			ConnectionFactory connectionFactory, TQueue queue, AutumnMqChannelMessageListener<TQueue> messageListener) {
		SimpleMessageListenerContainer container = new SimpleMessageListenerContainer(connectionFactory);
		container.setQueueNames(this.getName());
		container.setAcknowledgeMode(messageListener.getAcknowledgeMode());
		container.setExposeListenerChannel(true);
		container.setMaxConcurrentConsumers(messageListener.getConcurrentConsumers());
		container.setConcurrentConsumers(messageListener.getConcurrentConsumers());
		container.setMessageListener(new ChannelAwareMessageListener() {
			@Override
			public void onMessage(Message message, Channel channel) throws Exception {
				AutumnMqChannel<TQueue> autumnChannel = new AutumnRabbitChannel<TQueue>(queue, message, channel);
				messageListener.onMessage(message, autumnChannel);
			}
		});
		return container;
	}
}
