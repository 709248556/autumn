package com.autumn.mq.rabbit;

import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.DirectExchange;
import org.springframework.amqp.core.Exchange;
import org.springframework.amqp.core.ExchangeTypes;
import org.springframework.amqp.core.FanoutExchange;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.rabbit.core.RabbitTemplate;

import com.autumn.mq.AutumnMqDelayQueue;
import com.autumn.mq.AutumnMqQueue;
import com.autumn.exception.ExceptionUtils;

/**
 * 广播队列定义工厂
 * 
 * @author 老码农
 *         <p>
 *         Description
 *         </p>
 * @date 2018-01-25 20:37:33
 */
public class RabbitFanoutQueueDeclareFactory extends AbstractRabbitQueueDeclareFactory {

	/**
	 * 实例化
	 * 
	 * @param name
	 * @param exchangeSuffix
	 * @param durable
	 */
	public RabbitFanoutQueueDeclareFactory(String name, String exchangeSuffix, boolean durable) {
		super(name, ExchangeTypes.FANOUT, exchangeSuffix, durable, false, true);
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
		private final FanoutExchange exchange;
		private final Binding binding;

		/**
		 * 
		 * @param factory
		 */
		public RabbitAutumnQueue(RabbitFanoutQueueDeclareFactory factory) {
			super(factory);
			ExceptionUtils.checkNotNullOrBlank(factory.getExchangeName(), "exchange");
			this.exchange = new FanoutExchange(factory.getExchangeName(), factory.isDurable(), false);
			if (this.getQueue() != null) {
				this.binding = BindingBuilder.bind(this.getQueue()).to(this.exchange);
			} else {
				this.binding = BindingBuilder.bind(this.exchange).to(this.exchange);
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
	public static class RabbitAutumnDelayQueue extends AbstractRabbitAutumnDelayQueue {
		private final Queue delayQueue;
		private final FanoutExchange exchange;
		private final DirectExchange delayExchange;
		private final Binding binding;
		private final Binding delayBinding;

		/**
		 * 
		 * @param factory
		 *            AbstractQueryDeclareBuilder
		 */
		public RabbitAutumnDelayQueue(RabbitFanoutQueueDeclareFactory factory) {
			super(factory);
			ExceptionUtils.checkNotNullOrBlank(factory.getExchangeName(), "exchange");
			// ExceptionUtils.checkNotNullOrBlank(factory.getDelayQueueName(),
			// "delayQueueName");
			// ExceptionUtils.checkNotNullOrBlank(factory.getDelayQueueExchangeName(),
			// "delayQueueExchange");
			this.exchange = new FanoutExchange(factory.getExchangeName(), factory.isDurable(), false);
			if (this.getQueue() != null) {
				this.binding = BindingBuilder.bind(this.getQueue()).to(this.exchange);
			} else {
				this.binding = BindingBuilder.bind(this.exchange).to(this.exchange);
			}
			this.delayExchange = null;
			this.delayQueue = null;
			this.delayBinding = null;
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
