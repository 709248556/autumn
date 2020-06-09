package com.autumn.swagger.json;

import com.alibaba.fastjson.serializer.JSONSerializer;
import com.alibaba.fastjson.serializer.ObjectSerializer;
import com.alibaba.fastjson.serializer.SerializeWriter;
import springfox.documentation.spring.web.json.Json;

import java.io.IOException;
import java.lang.reflect.Type;

/**
 * Json 序列化
 *
 * @Description TODO
 * @Author 老码农
 * @Date 2019-09-02 17:50
 */
public class SwaggerJsonSerializer implements ObjectSerializer {

    public final static SwaggerJsonSerializer INSTANCE = new SwaggerJsonSerializer();

    @Override
    public void write(JSONSerializer serializer, Object object, Object fieldName, Type fieldType, int features) {
        SerializeWriter out = serializer.getWriter();
        Json json = (Json) object;
        out.write(json.value());
    }
}
