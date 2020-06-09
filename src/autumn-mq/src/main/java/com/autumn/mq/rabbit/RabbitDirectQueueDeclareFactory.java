package com.autumn.mq.rabbit;

import java.util.HashMap;
import java.util.Map;

import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.DirectExchange;
import org.springframework.amqp.core.Exchange;
import org.springframework.amqp.core.ExchangeTypes;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.rabbit.core.RabbitTemplate;

import com.autumn.mq.AutumnMqDelayQueue;
import com.autumn.mq.AutumnMqQueue;
import com.autumn.exception.ExceptionUtils;

/**
 * 队列 Direct 模式声明工厂
 * 
 * @author 老码农
 *         <p>
 *         Description
 *         </p>
 * @date 2017-12-10 15:20:33
 */
public class RabbitDirectQueueDeclareFactory extends AbstractRabbitQueueDeclareFactory {

	/**
	 * 实例化
	 * 
	 * @param name
	 *            名称
	 */
	public RabbitDirectQueueDeclareFactory(String name) {
		this(name, true);
	}

	/**
	 * 实例化
	 * 
	 * @param name
	 *            名称
	 * @param durable
	 *            是否持久化
	 */
	public RabbitDirectQueueDeclareFactory(String name, boolean durable) {
		this(name, durable, false, false);
	}

	/**
	 * 实例化
	 * 
	 * @param name
	 *            名称
	 * @param durable
	 *            是否持久化
	 * @param exclusive
	 *            是否排它性，即只有本实例能访问
	 * @param autoDelete
	 *            是否自动删除
	 */
	public RabbitDirectQueueDeclareFactory(String name, boolean durable, boolean exclusive, boolean autoDelete) {
		super(name, ExchangeTypes.DIRECT, name, durable, exclusive, autoDelete);
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
	 * Rabbit Queue direct 定义
	 * 
	 * @author 老码农
	 *         <p>
	 *         Description
	 *         </p>
	 * @date 2017-12-10 15:58:59
	 */
	public static class RabbitAutumnQueue extends AbstractRabbitAutumnQueue implements AutumnMqQueue {
		private final DirectExchange exchange;
		private final Binding binding;

		/**
		 * 
		 * @param factory
		 */
		public RabbitAutumnQueue(RabbitDirectQueueDeclareFactory factory) {
			super(factory);
			ExceptionUtils.checkNotNullOrBlank(factory.getExchangeName(), "exchange");
			this.exchange = new DirectExchange(factory.getExchangeName(), factory.isDurable(), factory.isAutoDelete());
			this.binding = BindingBuilder.bind(this.getQueue()).to(this.exchange).with(factory.getName());
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
	public static class RabbitAutumnDelayQueue extends AbstractRabbitAutumnDelayQueue {
		private final Queue delayQueue;
		private final DirectExchange exchange;
		private final DirectExchange delayExchange;
		private final Binding binding;
		private final Binding delayBinding;

		/**
		 * 
		 * @param factory
		 *            AbstractQueryDeclareBuilder
		 */
		public RabbitAutumnDelayQueue(RabbitDirectQueueDeclareFactory factory) {
			super(factory);
			ExceptionUtils.checkNotNullOrBlank(factory.getExchangeName(), "exchange");
			ExceptionUtils.checkNotNullOrBlank(factory.getDelayQueueName(), "delayQueueName");
			ExceptionUtils.checkNotNullOrBlank(factory.getDelayQueueExchangeName(), "delayQueueExchange");
			this.exchange = new DirectExchange(factory.getExchangeName(), factory.isDurable(), factory.isAutoDelete());
			this.delayExchange = new DirectExchange(factory.getDelayQueueExchangeName(), factory.isDurable(),
					factory.isAutoDelete());
			Map<String, Object> arguments = new HashMap<>();
			arguments.put("x-dead-letter-exchange", factory.getExchangeName());
			this.delayQueue = new Queue(factory.getDelayQueueName(), factory.isDurable(), factory.isExclusive(),
					factory.isAutoDelete(), arguments);
			this.binding = BindingBuilder.bind(this.getQueue()).to(this.exchange).with(factory.getName());
			this.delayBinding = BindingBuilder.bind(this.delayQueue).to(this.delayExchange).with(factory.getName());
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
