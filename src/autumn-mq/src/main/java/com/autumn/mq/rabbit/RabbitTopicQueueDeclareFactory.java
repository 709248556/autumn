package com.autumn.mq.rabbit;

import java.util.HashMap;
import java.util.Map;

import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.Exchange;
import org.springframework.amqp.core.ExchangeTypes;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.core.TopicExchange;
import org.springframework.amqp.rabbit.core.RabbitTemplate;

import com.autumn.mq.AutumnMqDelayQueue;
import com.autumn.mq.AutumnMqQueue;
import com.autumn.exception.ExceptionUtils;
import com.autumn.util.StringUtils;

/**
 * 基于 Topic 队列声明工厂
 * 
 * @author 老码农
 *         <p>
 *         Description
 *         </p>
 * @date 2017-12-10 18:13:04
 */
public class RabbitTopicQueueDeclareFactory extends AbstractRabbitQueueDeclareFactory {

	private final String routingKey;

	/**
	 * 实例化
	 * 
	 * @param name
	 *            名称
	 * @param exchangeSuffix
	 *            交换器后缀
	 * @param routingKey
	 *            路油
	 */
	public RabbitTopicQueueDeclareFactory(String name, String exchangeSuffix, String routingKey) {
		this(name, exchangeSuffix, routingKey, true);
	}

	/**
	 * 实例化
	 * 
	 * @param name
	 *            名称
	 * @param exchangeSuffix
	 *            交换器后缀
	 * @param routingKey
	 *            路油id
	 * @param durable
	 *            持久
	 */
	public RabbitTopicQueueDeclareFactory(String name, String exchangeSuffix, String routingKey, boolean durable) {
		this(name, exchangeSuffix, routingKey, durable, false, false);
	}

	/**
	 * 实例化
	 * 
	 * @param name
	 *            名称
	 * @param exchangeSuffix
	 *            变换器后缀
	 * @param routingKey
	 *            路油id
	 * @param durable
	 *            持久
	 * @param exclusive
	 *            排它
	 * @param autoDelete
	 *            自动删除
	 */
	public RabbitTopicQueueDeclareFactory(String name, String exchangeSuffix, String routingKey, boolean durable,
			boolean exclusive, boolean autoDelete) {
		super(name, ExchangeTypes.TOPIC, StringUtils.isNullOrBlank(exchangeSuffix) ? name : exchangeSuffix, durable,
				exclusive, autoDelete);
		if (StringUtils.isNullOrBlank(routingKey)) {
			this.routingKey = name;
		} else {
			this.routingKey = routingKey;
		}
	}

	@Override
	public AutumnMqQueue declare(RabbitTemplate rabbitTemplate) {
		RabbitAutumnQueue raabitDeclare = new RabbitAutumnQueue(this);
		raabitDeclare.setRabbitTemplate(rabbitTemplate);
		return raabitDeclare;
	}

	@Override
	public AutumnMqDelayQueue delayDeclare(RabbitTemplate rabbitTemplate) {
		RabbitAutumnDelayQueue raabitDeclare = new RabbitAutumnDelayQueue(this);
		raabitDeclare.setRabbitTemplate(rabbitTemplate);
		return raabitDeclare;
	}

	/**
	 * 获取路油Id
	 * 
	 * @return
	 *
	 */
	public String getRoutingKey() {
		return routingKey;
	}

	/**
	 * Rabbit Queue direct 定义
	 * 
	 * @author 老码农
	 *         <p>
	 *         Description
	 *         </p>
	 * @date 2017-12-10 15:58:59
	 */
	private static class RabbitAutumnQueue extends AbstractRabbitAutumnQueue implements AutumnMqQueue {
		private final TopicExchange exchange;
		private final Binding binding;

		/**
		 * 
		 * @param factory
		 */
		public RabbitAutumnQueue(RabbitTopicQueueDeclareFactory factory) {
			super(factory);
			ExceptionUtils.checkNotNullOrBlank(factory.getExchangeName(), "exchange");
			this.exchange = new TopicExchange(factory.getExchangeName(), factory.isDurable(), factory.isAutoDelete());
			if (this.getQueue() != null) {
				this.binding = BindingBuilder.bind(this.getQueue()).to(this.exchange).with(factory.getRoutingKey());
			} else {
				this.binding = BindingBuilder.bind(this.exchange).to(this.exchange).with(factory.getRoutingKey());
			}
		}

		@Override
		public Exchange getExchange() {
			return this.exchange;
		}

		@Override
		public Binding getBinding() {
			return binding;
		}
	}

	/**
	 * Rabbit Queue direct 定义
	 * 
	 * @author 老码农
	 *         <p>
	 *         Description
	 *         </p>
	 * @date 2017-12-10 15:58:59
	 */
	private static class RabbitAutumnDelayQueue extends AbstractRabbitAutumnDelayQueue {
		private final Queue delayQueue;
		private final TopicExchange exchange;
		private final TopicExchange delayExchange;
		private final Binding binding;
		private final Binding delayBinding;

		/**
		 * 
		 * @param factory
		 */
		public RabbitAutumnDelayQueue(RabbitTopicQueueDeclareFactory factory) {
			super(factory);
			ExceptionUtils.checkNotNullOrBlank(factory.getExchangeName(), "exchange");		
			ExceptionUtils.checkNotNullOrBlank(factory.getDelayQueueExchangeName(), "delayQueueExchange");
			this.exchange = new TopicExchange(factory.getExchangeName(), factory.isDurable(), factory.isAutoDelete());
			if (this.getQueue() != null) {
				this.binding = BindingBuilder.bind(this.getQueue()).to(this.exchange).with(factory.getRoutingKey());
			} else {
				this.binding = BindingBuilder.bind(this.exchange).to(this.exchange).with(factory.getRoutingKey());
			}
			this.delayExchange = new TopicExchange(factory.getDelayQueueExchangeName(), factory.isDurable(),
					factory.isAutoDelete());
			
			if (!StringUtils.isNullOrBlank(factory.getDelayQueueName())) {
				Map<String, Object> arguments = new HashMap<>();
				arguments.put("x-dead-letter-exchange", factory.getExchangeName());
				arguments.put("x-dead-letter-routing-key", factory.getRoutingKey());
				this.delayQueue = new Queue(factory.getDelayQueueName(), factory.isDurable(), factory.isExclusive(),
						factory.isAutoDelete(), arguments);

				this.delayBinding = BindingBuilder.bind(this.delayQueue).to(this.delayExchange)
						.with(factory.getRoutingKey());
			} else {
				this.delayQueue = null;
				this.delayBinding = BindingBuilder.bind(this.delayExchange).to(this.delayExchange)
						.with(factory.getRoutingKey());
			}
		}

		@Override
		public Exchange getExchange() {
			return this.exchange;
		}

		@Override
		public Queue getDelayQueue() {
			return this.delayQueue;
		}

		@Override
		public Exchange getDelayExchange() {
			return delayExchange;
		}

		@Override
		public Binding getBinding() {
			return binding;
		}

		@Override
		public Binding getDelayBinding() {
			return delayBinding;
		}
	}
}
