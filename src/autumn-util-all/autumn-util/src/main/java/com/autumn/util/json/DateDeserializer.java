package com.autumn.util.json;

import java.lang.reflect.Type;

import com.alibaba.fastjson.parser.DefaultJSONParser;
import com.alibaba.fastjson.parser.JSONLexer;
import com.alibaba.fastjson.parser.deserializer.ObjectDeserializer;
import com.autumn.util.DateUtils;

/**
 * 日期反序列化
 * 
 * @author 老码农
 *
 *         2017-12-14 18:26:46
 */
public class DateDeserializer implements ObjectDeserializer {

	@SuppressWarnings("unchecked")
	@Override
	public <T> T deserialze(DefaultJSONParser parser, Type type, Object fieldName) {
		JSONLexer lexer = parser.getLexer();
		if (lexer.isBlankInput()) {
			return null;
		}
		return (T) DateUtils.parseDate(lexer.stringVal());
	}

	@Override
	public int getFastMatchToken() {
		return 0;
	}

}
