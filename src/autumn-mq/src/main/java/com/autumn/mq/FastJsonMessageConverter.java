package com.autumn.mq;

import java.io.UnsupportedEncodingException;
import java.util.Date;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.core.MessageProperties;
import org.springframework.amqp.support.converter.AbstractJsonMessageConverter;
import org.springframework.amqp.support.converter.ClassMapper;
import org.springframework.amqp.support.converter.DefaultClassMapper;
import org.springframework.amqp.support.converter.MessageConversionException;

import com.autumn.util.StringUtils;
import com.autumn.util.TypeUtils;
import com.autumn.util.json.JsonUtils;

/**
 * Fast Json 消息转换器
 * 
 * @author 老码农
 *         <p>
 *         Description
 *         </p>
 * @date 2017-12-10 03:43:01
 */
public class FastJsonMessageConverter extends AbstractJsonMessageConverter /* implements MessageConverter */ {

	private static Log log = LogFactory.getLog(FastJsonMessageConverter.class);

	private final static String JSON_CONTENT_TYPE = "json";

	private final static ClassMapper CLASS_MAPPER = new DefaultClassMapper();

	public FastJsonMessageConverter() {

	}

	@Override
	protected Message createMessage(Object object, MessageProperties messageProperties) {
		if (object == null) {
			messageProperties.setContentType(MessageProperties.CONTENT_TYPE_BYTES);
			messageProperties.setContentEncoding(getDefaultCharset());
			return new Message(null, messageProperties);
		}
		byte[] bytes = null;
		Class<?> objType = object.getClass();
		if (TypeUtils.isBaseType(objType)) {
			messageProperties.setContentType(MessageProperties.CONTENT_TYPE_TEXT_PLAIN);
			messageProperties.setContentEncoding(getDefaultCharset());
			try {
				if (Date.class.isAssignableFrom(objType)) {
					bytes = Long.toString(((Date) object).getTime()).getBytes(getDefaultCharset());
				} else {
					bytes = object.toString().getBytes(getDefaultCharset());
				}
				messageProperties.setContentLength(bytes.length);
				return new Message(bytes, messageProperties);
			} catch (Exception e) {
				throw new MessageConversionException("无效mq消息转换:" + e.getMessage(), e);
			}
		}
		// 二进制
		if (TypeUtils.isBinaryType(objType)) {
			messageProperties.setContentType(MessageProperties.CONTENT_TYPE_BYTES);
			messageProperties.setContentEncoding(getDefaultCharset());
			bytes = (byte[]) object;
			messageProperties.setContentLength(bytes.length);
			return new Message(bytes, messageProperties);
		}
		try {
			String jsonString = JsonUtils.toJSONString(object);
			bytes = jsonString.getBytes(getDefaultCharset());
		} catch (Exception e) {
			throw new MessageConversionException("无效mq消息转换:" + e.getMessage(), e);
		}
		messageProperties.setContentType(MessageProperties.CONTENT_TYPE_JSON);
		messageProperties.setContentEncoding(getDefaultCharset());
		if (bytes != null) {
			messageProperties.setContentLength(bytes.length);
		}
		CLASS_MAPPER.fromClass(object.getClass(), messageProperties);
		return new Message(bytes, messageProperties);
	}

	@Override
	public Object fromMessage(Message message) throws MessageConversionException {
		Object content = null;
		MessageProperties properties = message.getMessageProperties();
		if (properties != null) {
			String contentType = properties.getContentType();
			if (contentType != null && contentType.contains(JSON_CONTENT_TYPE)) {
				String encoding = properties.getContentEncoding();
				if (StringUtils.isNullOrBlank(encoding)) {
					encoding = getDefaultCharset();
				}
				try {
					Class<?> targetClass = CLASS_MAPPER.toClass(message.getMessageProperties());
					content = convertBytesToObject(message.getBody(), encoding, targetClass);
				} catch (Exception e) {
					log.error("无效的 mq 消息转换:" + e.getMessage(), e);
					content = message.getBody();
				}
			} else {
				log.warn("无法处理 mq 消息内容类型 content-type [" + contentType + "]");
			}
		}
		if (content == null) {
			content = message.getBody();
		}
		return content;
	}

	private Object convertBytesToObject(byte[] body, String encoding, Class<?> clazz)
			throws UnsupportedEncodingException {
		if (clazz == null) {
			return null;
		}
		String contentAsString = new String(body, encoding);
		if (TypeUtils.isBaseType(clazz)) {
			return TypeUtils.toConvert(clazz, contentAsString);
		}
		return JsonUtils.parseObject(contentAsString, clazz);
	}
}
