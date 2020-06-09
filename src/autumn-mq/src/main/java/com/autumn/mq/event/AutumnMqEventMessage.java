package com.autumn.mq.event;

import java.io.IOException;
import java.nio.charset.Charset;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.core.MessageProperties;

import com.autumn.mq.AutumnMqDelayQueue;
import com.autumn.util.StringUtils;
import com.autumn.util.json.JsonUtils;
import com.rabbitmq.client.Channel;

/**
 * 事件消息
 * 
 * @author 老码农
 *
 *         2017-12-16 14:51:45
 */
public class AutumnMqEventMessage<T extends AutumnMqEventData> {

	private final Message message;
	private final Channel channel;
	private final Class<T> dataType;
	private final AutumnMqDelayQueue queue;
	private final T data;
	private final String bodyString;
	private final long repeatSendCount;

	private final static Log logger = LogFactory.getLog(AutumnMqEventMessage.class);

	/**
	 * 
	 * @param message
	 *            消息
	 * @param channel
	 *            通道
	 * @param dataType
	 *            类型
	 * @throws Exception
	 */
	public AutumnMqEventMessage(Message message, Channel channel, AutumnMqDelayQueue queue, Class<T> dataType) {
		this.message = message;
		this.channel = channel;
		this.queue = queue;
		this.dataType = dataType;
		if (message.getBody() == null || message.getBody().length == 0) {
			this.bodyString = null;
		} else {
			this.bodyString = new String(message.getBody(), getMessageCharset());
		}
		this.data = createData();
		this.repeatSendCount = queue.getOrInitializeRepeatSendCount(message.getMessageProperties());
	}

	private T createData() {
		try {
			if (this.bodyString == null) {
				return null;
			} else {
				return JsonUtils.parseObject(this.bodyString, dataType);
			}
		} catch (Exception err) {
			logger.error(err.getMessage(), err);
			return null;
		}
	}

	/**
	 * 获取消息
	 * 
	 * @return
	 *
	 */
	public Message getMessage() {
		return message;
	}

	private Charset getMessageCharset() {
		MessageProperties properties = message.getMessageProperties();
		String encoding = null;
		if (properties != null) {
			encoding = properties.getContentEncoding();
		}
		if (StringUtils.isNullOrBlank(encoding)) {
			encoding = "UTF-8";
		}
		try {
			return Charset.forName(encoding);
		} catch (Exception e) {
			return java.nio.charset.StandardCharsets.UTF_8;
		}
	}

	/**
	 * 获取数据
	 * 
	 * @return
	 *
	 */
	public T getData() {
		return this.data;
	}

	/**
	 * 确认消息
	 * 
	 * @throws IOException
	 */
	public void basicAck() throws IOException {
		channel.basicAck(this.message.getMessageProperties().getDeliveryTag(), false);
	}

	/**
	 * 拒绝
	 * 
	 * @param requeue
	 *            重新进入队列
	 * @throws IOException
	 */
	public void basicNack(boolean requeue) throws IOException {
		this.channel.basicNack(this.message.getMessageProperties().getDeliveryTag(), false, requeue);
	}

	/**
	 * 获取队列
	 * 
	 * @return
	 *
	 */
	public AutumnMqDelayQueue getQueue() {
		return queue;
	}

	/**
	 * 获取数据类型
	 * 
	 * @return
	 */
	public Class<T> getDataType() {
		return dataType;
	}

	/**
	 * 获取体字符
	 * 
	 * @return
	 */
	public String getBodyString() {
		return bodyString;
	}

	/**
	 * 获取重复发送次数(首次为0)
	 * 
	 * @return
	 */
	public long getRepeatSendCount() {
		return repeatSendCount;
	}

}
