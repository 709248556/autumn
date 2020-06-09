package com.autumn.runtime.deserializer.json;

import com.autumn.application.dto.input.*;
import com.autumn.util.json.JsonObjectDeserializerGenerator;

import java.util.HashMap;
import java.util.Map;

/**
 * TODO
 *
 * @Description TODO
 * @Author 老码农
 * @Date 2019-07-28 17:17
 */
public class InputObjectDeserializerGenerator implements JsonObjectDeserializerGenerator {

    @Override
    public Map<Class<?>, Class<?>> generate() {
        Map<Class<?>, Class<?>> generate_map = new HashMap<>(16);
        generate_map.put(AdvancedPageQueryInput.class, DefaultAdvancedPageQueryInput.class);
        generate_map.put(AdvancedQueryInput.class, DefaultAdvancedQueryInput.class);
        generate_map.put(AdvancedSearchInput.class, DefaultAdvancedSearchInput.class);
        generate_map.put(NextQueryInput.class, DefaultNextQueryInput.class);
        generate_map.put(PageQueryInput.class, DefaultPageQueryInput.class);
        generate_map.put(QueryCriteriaInput.class, DefaultQueryCriteriaInput.class);
        generate_map.put(StatusInput.class, DefaultStatusInput.class);
        return generate_map;
    }
}
