package com.autumn.mq.rabbit.annotation;

import com.autumn.mq.configurre.AutumnRabbitBeanRegistrar;
import org.springframework.context.annotation.Import;

import java.lang.annotation.*;

/**
 * 启用 Autumn 的 RabbitMq
 * 
 * @author 老码农
 *         <p>
 *         Description
 *         </p>
 * @date 2017-12-09 19:01:19
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.TYPE)
@Documented
@Import({ AutumnRabbitBeanRegistrar.class })
public @interface EnableAutumnRabbit {

	/**
	 * 连接配置
	 * 
	 * @return
	 */
	AutumnRabbitConnection[] value() default {};
}
