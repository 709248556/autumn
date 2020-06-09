package com.autumn.mq.event;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import org.springframework.core.annotation.AliasFor;

/**
 * (事件数据)事件生产者配置
 * <p>
 * 表示只有生产者，无消费者的配置，只参配置在实体上
 * </p>
 * 
 * @author 老码农
 *         <p>
 *         Description
 *         </p>
 * @date 2017-12-16 19:59:24
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.TYPE)
@Documented
public @interface AutumnMqEventDataConfigure {

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
	 * 路油Id
	 * <p>
	 * {@link com.autumn.mq.event.AutumnMqEventTypeEnum TOPIC} 根据生产者与消费者进行相应的配置
	 * </p>
	 * 
	 * @return
	 *
	 */
	String routingKey() default "";

	/**
	 * 交换器后缀
	 * <p>
	 * {@link com.autumn.mq.event.AutumnMqEventTypeEnum TOPIC 或 BROADCAST}
	 * </p>
	 * 根据生产者与消费者进行相应的配置
	 * 
	 * @return
	 *
	 */
	String exchangeSuffix() default "";

	/**
	 * 事件类型
	 * 
	 * @return
	 *
	 */
	AutumnMqEventTypeEnum eventType() default AutumnMqEventTypeEnum.DIRECT;
}
