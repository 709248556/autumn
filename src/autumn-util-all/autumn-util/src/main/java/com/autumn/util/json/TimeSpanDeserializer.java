package com.autumn.util.json;

import com.alibaba.fastjson.parser.DefaultJSONParser;
import com.alibaba.fastjson.parser.JSONLexer;
import com.alibaba.fastjson.parser.deserializer.ObjectDeserializer;
import com.autumn.util.TimeSpan;

import java.lang.reflect.Type;

/**
 * 时间间隔反序列化
 * 
 * @author 老码农
 *
 *         2017-11-02 11:18:43
 */
public class TimeSpanDeserializer implements ObjectDeserializer {

	public static final TimeSpanDeserializer INSTANCE = new TimeSpanDeserializer();

	@SuppressWarnings("unchecked")
	@Override
	public <T> T deserialze(DefaultJSONParser parser, Type type, Object fieldName) {
		JSONLexer lexer = parser.getLexer();
		if (lexer.isBlankInput()) {
			return null;
		}
		return (T) TimeSpan.parse(lexer.stringVal());
	}

	@Override
	public int getFastMatchToken() {
		return 0;
	}
}
