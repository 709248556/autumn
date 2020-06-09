package com.autumn.util.json;

import com.alibaba.fastjson.parser.DefaultJSONParser;
import com.alibaba.fastjson.parser.JSONLexer;
import com.alibaba.fastjson.parser.deserializer.ObjectDeserializer;
import com.autumn.util.Time;

import java.lang.reflect.Type;

/**
 * Time 反序列化
 * 
 * @author 老码农
 *
 *         2017-11-02 11:11:54
 */
public class TimeDeserializer implements ObjectDeserializer {

	public static final TimeDeserializer INSTANCE = new TimeDeserializer();

	@SuppressWarnings("unchecked")
	@Override
	public <T> T deserialze(DefaultJSONParser parser, Type type, Object fieldName) {
		JSONLexer lexer = parser.getLexer();
		if (lexer.isBlankInput()) {
			return null;
		}
		return (T) Time.parse(lexer.stringVal());
	}

	@Override
	public int getFastMatchToken() {
		return 0;
	}

}
