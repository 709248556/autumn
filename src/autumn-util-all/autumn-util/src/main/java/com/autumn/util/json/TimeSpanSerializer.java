package com.autumn.util.json;

import com.alibaba.fastjson.serializer.JSONSerializer;
import com.alibaba.fastjson.serializer.ObjectSerializer;
import com.alibaba.fastjson.serializer.SerializeWriter;

import java.io.IOException;
import java.lang.reflect.Type;

/**
 * 时间间隔序列化
 * 
 * @author 老码农
 *
 *         2017-11-02 11:17:41
 */
public class TimeSpanSerializer implements ObjectSerializer {

	public static final TimeSpanSerializer INSTANCE = new TimeSpanSerializer();

	@Override
	public void write(JSONSerializer serializer, Object object, Object fieldName, Type fieldType, int features)
			throws IOException {
		if (object == null) {
			serializer.getWriter().writeNull();
			return;
		}
		SerializeWriter out = serializer.getWriter();
		out.write(object.toString());
	}
}
