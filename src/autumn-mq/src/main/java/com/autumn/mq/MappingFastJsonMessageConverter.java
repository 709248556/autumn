package com.autumn.mq;

import org.springframework.messaging.Message;
import org.springframework.messaging.MessageHeaders;
import org.springframework.messaging.converter.MessageConverter;

/**
 * 
 * @author 老码农
 *         <p>
 * 		Description
 *         </p>
 * @date 2017-12-10 05:55:34
 */
public class MappingFastJsonMessageConverter implements MessageConverter {

	
	@Override
	public Object fromMessage(Message<?> message, Class<?> targetClass) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Message<?> toMessage(Object payload, MessageHeaders headers) {
		// TODO Auto-generated method stub
		return null;
	}
	
	
	
	

	
}
