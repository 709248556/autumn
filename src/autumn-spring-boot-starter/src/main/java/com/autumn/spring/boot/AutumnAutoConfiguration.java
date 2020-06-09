package com.autumn.spring.boot;

import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;

import com.autumn.spring.boot.context.AutumnApplicationContext;

/**
 * Autumn 自动配置
 * 
 * @author 老码农
 *
 *         2018-06-26 17:36:08
 */
@Configuration
@Order(Ordered.HIGHEST_PRECEDENCE)
// @EnableConfigurationProperties(AutumnProperties.class)
public class AutumnAutoConfiguration {

	/**
	 * 
	* @param applicationContext
	* @return
	*
	 */
	@Bean
	@Primary
	@ConditionalOnMissingBean(AutumnApplicationContext.class)
	public AutumnApplicationContext autumnApplicationContext(ApplicationContext applicationContext) {
		return new AutumnApplicationContext(applicationContext);
	}
	
}
