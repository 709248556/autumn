package com.autumn.util.json;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.TypeReference;
import com.alibaba.fastjson.parser.ParserConfig;
import com.alibaba.fastjson.parser.deserializer.ObjectDeserializer;
import com.alibaba.fastjson.serializer.ObjectSerializer;
import com.alibaba.fastjson.serializer.SerializeConfig;
import com.alibaba.fastjson.serializer.SerializerFeature;
import com.autumn.exception.ExceptionUtils;
import com.autumn.util.Time;
import com.autumn.util.TimeSpan;
import com.autumn.util.TypeUtils;

import java.lang.reflect.Type;
import java.math.BigInteger;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Json 帮助
 *
 * @author 老码农
 * <p>
 * 2017-11-01 19:04:55
 */
public class JsonUtils {

    /**
     * Json 日期默认格式
     */
    public static final String JSON_DEFAULT_FORMAT = "yyyy-MM-dd HH:mm:ss";

    /**
     * 对象反序列化Map
     */
    private static final Map<Class<?>, Class<?>> OBJECT_DESERIALIZER_MAP = new HashMap<>();

    /**
     * 标准的输出格式
     */
    public static final SerializerFeature[] SERIALIZER_FEATURE_STANDARD = new SerializerFeature[]{
            // 输出key时是否使用双引号
            SerializerFeature.QuoteFieldNames,
            // 值为 null 的字段也输出，统一接口处理
            SerializerFeature.WriteMapNullValue,
            // 全局日期默认格式(即 getJsonDateDefaultFormat() 的值)
            SerializerFeature.WriteDateUseDateFormat,
            // 如果字符格式为 null 时输出为空白值""
            SerializerFeature.WriteNullStringAsEmpty,
            // 如果 Boolean 为 null 时，输出为 false
            SerializerFeature.WriteNullBooleanAsFalse,
            // 如查 List 为 null 时输出 [],不加要这选项，否则对象为  null 时会变成[]
            //SerializerFeature.WriteNullListAsEmpty,
            // 禁止出现 $ref,但对象中不可存在循环引用现象
            SerializerFeature.DisableCircularReferenceDetect};

    /**
     * 格式化的输出格式
     */
    public static final SerializerFeature[] SERIALIZER_FEATURE_PRETTYFORMAT = new SerializerFeature[]{
            // 结果是否格式化
            SerializerFeature.PrettyFormat,
            // 输出key时是否使用双引号
            SerializerFeature.QuoteFieldNames,
            // 值为 null 的字段也输出，统一接口处理
            SerializerFeature.WriteMapNullValue,
            // 全局日期默认格式(即 getJsonDateDefaultFormat() 的值)
            SerializerFeature.WriteDateUseDateFormat,
            // 如果字符格式为 null 时输出为空白值""
            SerializerFeature.WriteNullStringAsEmpty,
            // 如果 Boolean 为 null 时，输出为 false
            SerializerFeature.WriteNullBooleanAsFalse,
            // 如查 List 为 null 时输出 [],不加要这选项，否则对象为  null 时会变成[]
            //SerializerFeature.WriteNullListAsEmpty,
            // 禁止出现 $ref,但对象中不可存在循环引用现象
            SerializerFeature.DisableCircularReferenceDetect};

    /**
     *
     */
    static {
        initialize();
    }

    /**
     * 获取对象反序列化对象
     *
     * @param objClass 对象类型
     * @return
     */
    public static Class<?> getObjectDeserializer(Class<?> objClass) {
        return OBJECT_DESERIALIZER_MAP.get(objClass);
    }

    /**
     * 注册对象反序列化
     *
     * @param objClass  对象类型
     * @param implClass 实现类型
     */
    public static void registerObjectDeserializer(Class<?> objClass, Class<?> implClass) {
        ExceptionUtils.checkNotNull(objClass, "objClass");
        ExceptionUtils.checkNotNull(implClass, "implClass");
        if (implClass.isInterface()) {
            ExceptionUtils.throwNotSupportException("类型[" + implClass.getName() + "]不能为接口类型。");
        }
        if (implClass.isArray()) {
            ExceptionUtils.throwNotSupportException("类型[" + implClass.getName() + "]不能为数组类型。");
        }
        if (TypeUtils.isAbstract(implClass)) {
            ExceptionUtils.throwNotSupportException("类型[" + implClass.getName() + "]不能为抽象类型。");
        }
        if (!TypeUtils.isPublic(implClass)) {
            ExceptionUtils.throwNotSupportException("类型[" + implClass.getName() + "]必须为公有类型。");
        }
        if (!OBJECT_DESERIALIZER_MAP.containsKey(objClass)) {
            registerDeserializer(objClass, JsonObjectDeserializer.DEFAULT);
        }
        OBJECT_DESERIALIZER_MAP.put(objClass, implClass);
    }

    /**
     * 注册对象反序列化
     *
     * @param generator 生成器
     */
    public static void registerObjectDeserializer(JsonObjectDeserializerGenerator generator) {
        if (generator != null) {
            Map<Class<?>, Class<?>> map = generator.generate();
            if (map != null) {
                for (Map.Entry<Class<?>, Class<?>> entry : map.entrySet()) {
                    if (entry.getKey() != null && entry.getValue() != null) {
                        registerObjectDeserializer(entry.getKey(), entry.getValue());
                    }
                }
            }
        }
    }


    private static boolean isInitialize = false;

    /**
     * 初始化,指定全局日期格式默认格式，自定义序列与返序列化
     * <p>
     * 2017-12-06 12:14:38
     */
    public synchronized static void initialize() {
        if (!isInitialize) {
            JSON.DEFFAULT_DATE_FORMAT = JSON_DEFAULT_FORMAT;
            registerSerialize(Time.class, TimeSerializer.INSTANCE);
            registerSerialize(TimeSpan.class, TimeSpanSerializer.INSTANCE);
            registerSerialize(BigInteger.class, ToStringSerializer.INSTANCE);
            registerDeserializer(Time.class, TimeDeserializer.INSTANCE);
            registerDeserializer(TimeSpan.class, TimeSpanDeserializer.INSTANCE);
            // 暂时禁止使用自定义日期解析，因为在某种条件下会出错
            // registerDeserializer(Date.class, new DateDeserializer());
            isInitialize = true;
        }
    }

    /**
     * 注册序列化器
     *
     * @param type       类型
     * @param serializer 序列化处理器
     */
    public static void registerSerialize(Type type, ObjectSerializer serializer) {
        SerializeConfig.getGlobalInstance().put(type, serializer);
    }

    /**
     * 注册反序列化器
     *
     * @param type         类型
     * @param deserializer 反序列化处理器
     */
    public static void registerDeserializer(Type type, ObjectDeserializer deserializer) {
        ParserConfig.getGlobalInstance().putDeserializer(type, deserializer);
    }

    /**
     * Json 转换
     *
     * @param value          值类型
     * @param isPrettyFormat 是否格式化
     * @return
     */
    public static String toJSONString(Object value, boolean isPrettyFormat) {
        if (isPrettyFormat) {
            return JSON.toJSONString(value, SERIALIZER_FEATURE_PRETTYFORMAT);
        }
        return JSON.toJSONString(value, SERIALIZER_FEATURE_STANDARD);
    }

    /**
     * Json 转换
     *
     * @param value 值类型
     * @return
     */
    public static String toJSONString(Object value) {
        return toJSONString(value, false);
    }

    /**
     * Json 转换数组
     *
     * @param object         对象
     * @param isPrettyFormat 是否格式化
     * @return
     */
    public static byte[] toJSONBytes(Object object, boolean isPrettyFormat) {
        if (isPrettyFormat) {
            return JSON.toJSONBytes(object, SERIALIZER_FEATURE_PRETTYFORMAT);
        }
        return JSON.toJSONBytes(object, SERIALIZER_FEATURE_STANDARD);
    }

    /**
     * Json 转换数组
     *
     * @param object 对象
     * @return
     */
    public static byte[] toJSONBytes(Object object) {
        return toJSONBytes(object, false);
    }

    /**
     * 解析对象
     *
     * @param json
     * @param clazz
     * @return
     */
    public static <T> T parseObject(String json, Class<T> clazz) {
        return JSON.parseObject(json, clazz);
    }

    /**
     * 解析列表
     *
     * @param json
     * @param clazz
     * @return
     */
    public static <T> List<T> parseList(String json, Class<T> clazz) {
        return JSON.parseArray(json, clazz);
    }

    /**
     * 解析对象
     *
     * @param json json对象
     * @param type 类型
     * @return
     */
    public static <T> T parseObject(String json, Type type) {
        return JSON.parseObject(json, type);
    }

    /**
     * 解析对象
     *
     * @param json json对象
     * @param type 类型
     * @return
     */
    public static <T> T parseObject(String json, TypeReference<T> type) {
        return JSON.parseObject(json, type);
    }

    /**
     * 解析Map对象
     *
     * @param json json对象
     * @return
     */
    @SuppressWarnings("unchecked")
    public static Map<String, Object> parseMap(String json) {
        return parseObject(json, Map.class);
    }
}
