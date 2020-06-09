package com.autumn.zero.common.library.deserializer.json;

import com.autumn.util.json.JsonObjectDeserializerGenerator;
import com.autumn.zero.common.library.application.dto.tree.input.ChildrenPinyinSortQueryInput;
import com.autumn.zero.common.library.application.dto.tree.input.ChildrenQueryInput;
import com.autumn.zero.common.library.application.dto.tree.input.DefaultChildrenPinyinSortQueryInput;
import com.autumn.zero.common.library.application.dto.tree.input.DefaultChildrenQueryInput;

import java.util.HashMap;
import java.util.Map;

/**
 * 公共输入对象序列化
 *
 * @Description TODO
 * @Author 老码农
 * @Date 2019-08-05 5:01
 */
public class CommonInputObjectDeserializerGenerator implements JsonObjectDeserializerGenerator {

    @Override
    public Map<Class<?>, Class<?>> generate() {
        Map<Class<?>, Class<?>> generateMap = new HashMap<>(16);
        generateMap.put(ChildrenQueryInput.class, DefaultChildrenQueryInput.class);
        generateMap.put(ChildrenPinyinSortQueryInput.class, DefaultChildrenPinyinSortQueryInput.class);
        return generateMap;
    }
}
