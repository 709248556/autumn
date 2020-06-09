package com.autumn.util.json;

import java.util.Map;

/**
 * Json对象反序化生成器
 *
 * @Description TODO
 * @Author 老码农
 * @Date 2019-07-28 17:14
 */
public interface JsonObjectDeserializerGenerator {

    /**
     * 生成
     *
     * @return
     */
    Map<Class<?>, Class<?>> generate();

}
