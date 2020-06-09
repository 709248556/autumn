package com.autumn.util.json;

import com.alibaba.fastjson.serializer.JSONSerializer;
import com.alibaba.fastjson.serializer.ObjectSerializer;
import com.alibaba.fastjson.serializer.SerializeWriter;

import java.io.IOException;
import java.lang.reflect.Type;

/**
 * 转换为字符序列化
 *
 * @Description TODO
 * @Author 老码农
 * @Date 2019-08-25 23:50
 */
public class ToStringSerializer implements ObjectSerializer {

    public static final ToStringSerializer INSTANCE = new ToStringSerializer();

    @Override
    public void write(JSONSerializer serializer, Object object, Object fieldName, Type fieldType,
                      int features) throws IOException {
        SerializeWriter out = serializer.out;
        if (object == null) {
            out.writeNull();
            return;
        }
        String strVal = object.toString();
        out.writeString(strVal);
    }

}
