package com.autumn.mq.event;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import org.springframework.core.annotation.AliasFor;

/**
 * 事件处理器配置
 * 
 * @author 老码农
 *
 *         2017-12-16 14:27:18
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.TYPE)
@Documented
public @interface AutumnMqEventHandlerConfigure {

	/**
	 * 队列名称
	 * 
	 * @return
	 *
	 */
	@AliasFor("name")
	String value() default "";

	/**
	 * 队列名称
	 * 
	 * @return
	 *
	 */
	@AliasFor("value")
	String name() default "";

	/**
	 * 路油Id {@link AutumnMqEventTypeEnum.TOPIC} 必须指定
	 * 
	 * @return
	 *
	 */
	String routingKey() default "";

	/**
	 * 交换器后缀根据生产者与消费者进行相应的配置 *
	 * <p>
	 * {@link com.autumn.mq.event.AutumnMqEventTypeEnum TOPIC 或 BROADCAST}
	 * </p>
	 * 
	 * @return
	 *
	 */
	String exchangeSuffix() default "";	

	/**
	 * 每次并发处理的消费数量,
	 * 
	 * @return 返回，默认为 1
	 */
	int concurrentConsumers() default 1;

	/**
	 * 事件类型
	 * 
	 * @return
	 *
	 */
	AutumnMqEventTypeEnum eventType() default AutumnMqEventTypeEnum.DIRECT;
}
