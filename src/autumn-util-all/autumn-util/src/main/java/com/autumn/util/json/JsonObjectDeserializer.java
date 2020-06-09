package com.autumn.util.json;

import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.parser.DefaultJSONParser;
import com.alibaba.fastjson.parser.JSONToken;
import com.alibaba.fastjson.parser.deserializer.ObjectDeserializer;
import com.autumn.exception.ExceptionUtils;

import java.lang.reflect.Type;

/**
 * Json对象接口反序列化
 *
 * @Description TODO
 * @Author 老码农
 * @Date 2019-07-28 16:47
 */
public class JsonObjectDeserializer implements ObjectDeserializer {

    /**
     * 默认实例
     */
    public static final ObjectDeserializer DEFAULT = new JsonObjectDeserializer();

    @SuppressWarnings({"unchecked", "rawtypes"})
    @Override
    public <T> T deserialze(DefaultJSONParser parser, Type type, Object fieldName) {
        if (parser.lexer.token() == JSONToken.NULL) {
            parser.lexer.nextToken(JSONToken.COMMA);
            return null;
        }
        if (type == JSONObject.class) {
            JSONObject obj = new JSONObject();
            parser.parseObject(obj);
            return (T) obj;
        }
        Class<?> rawClass = com.alibaba.fastjson.util.TypeUtils.getRawClass(type);
        Class<?> implClass = JsonUtils.getObjectDeserializer(rawClass);
        if (implClass == null) {
            throw ExceptionUtils.throwNotSupportException("不支持类型[" + rawClass.getName() + "]未配置对应的实现类型。");
        }
        return (T) parser.parseObject(implClass);
    }

    @Override
    public int getFastMatchToken() {
        return JSONToken.LBRACE;
    }
}
