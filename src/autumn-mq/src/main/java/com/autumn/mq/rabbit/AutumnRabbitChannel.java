package com.autumn.mq.rabbit;

import java.io.IOException;
import java.util.concurrent.TimeoutException;

import org.springframework.amqp.core.Message;
import org.springframework.amqp.core.MessageProperties;
import org.springframework.amqp.support.converter.MessageConversionException;

import com.autumn.mq.AutumnMqChannel;
import com.autumn.mq.AutumnMqQueue;
import com.autumn.util.StringUtils;
import com.autumn.util.TypeUtils;
import com.autumn.util.json.JsonUtils;
import com.rabbitmq.client.Channel;

/**
 * 
 * @author 老码农
 *         <p>
 *         Description
 *         </p>
 * @date 2017-12-11 03:52:39
 */
class AutumnRabbitChannel<TQueue extends AutumnMqQueue> implements AutumnMqChannel<TQueue> {

	private final TQueue autumnQueue;
	private final Message message;
	private final Channel channel;

	public AutumnRabbitChannel(TQueue autumnQueue, Message message, Channel channel) {
		this.autumnQueue = autumnQueue;
		this.message = message;
		this.channel = channel;
	}

	@Override
	public void basicAck() throws IOException {
		channel.basicAck(this.message.getMessageProperties().getDeliveryTag(), false);
	}

	@Override
	public void basicNack(boolean requeue) throws IOException {
		this.channel.basicNack(this.message.getMessageProperties().getDeliveryTag(), false, requeue);
	}

	@Override
	public TQueue getAutumnQueue() {
		return this.autumnQueue;
	}

	@Override
	public void close() throws IOException, TimeoutException {
		this.channel.close();
	}

	@Override
	public void close(int closeCode, String closeMessage) throws IOException, TimeoutException {
		this.channel.close(closeCode, closeMessage);
	}

	@Override
	public void abort() throws IOException {
		this.channel.abort();
	}

	@Override
	public void abort(int closeCode, String closeMessage) throws IOException {
		this.channel.abort(closeCode, closeMessage);
	}

	@SuppressWarnings("unchecked")
	@Override
	public <TBody> TBody getMessageBody(Class<TBody> bodyType) throws Exception {
		if (message.getBody() == null || message.getBody().length == 0) {
			return null;
		}
		MessageProperties properties = message.getMessageProperties();
		String encoding = null;
		if (properties != null) {
			encoding = properties.getContentEncoding();
		}
		if (StringUtils.isNullOrBlank(encoding)) {
			encoding = "UTF-8";
		}
		try {
			if (TypeUtils.isBinaryType(bodyType)) {
				return (TBody) message.getBody();
			}
			String contentAsString = new String(message.getBody(), encoding);
			if (TypeUtils.isBaseType(bodyType)) {
				return TypeUtils.toConvert(bodyType, contentAsString);
			}
			return JsonUtils.parseObject(contentAsString, bodyType);
		} catch (Exception e) {
			throw new MessageConversionException("无效mq消息转换:" + e.getMessage(), e);
		}
	}
}
