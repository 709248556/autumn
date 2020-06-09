package com.autumn.util;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import org.modelmapper.Converter;
import org.modelmapper.ModelMapper;
import org.modelmapper.PropertyMap;
import org.modelmapper.TypeMap;
import org.modelmapper.convention.MatchingStrategies;
import org.modelmapper.convention.NameTransformers;
import org.modelmapper.convention.NamingConventions;

import com.autumn.util.function.FunctionTwoAction;

/**
 * 提供 ModelMapper 帮助
 *
 * @author 老码农
 * <p>
 * 2017-11-17 11:26:46
 */
public class AutoMapUtils {

    private static final ModelMapper MODEL_MAPPER = new ModelMapper();

    static {
        MODEL_MAPPER.getConfiguration().setSourceNamingConvention(NamingConventions.JAVABEANS_ACCESSOR)
                .setDestinationNamingConvention(NamingConventions.JAVABEANS_MUTATOR)
                .setSourceNameTransformer(NameTransformers.JAVABEANS_ACCESSOR)
                .setDestinationNameTransformer(NameTransformers.JAVABEANS_MUTATOR)
                .setMatchingStrategy(MatchingStrategies.STRICT);
    }

    /**
     * 加载
     *
     * @param source      源对象
     * @param destination 被赋值的对象
     */
    public static void mapForLoad(Object source, Object destination) {
        if (source != null && destination != null) {
            MODEL_MAPPER.map(source, destination);
        }
    }


    /**
     * 转换为列表
     *
     * @param sourceList  源列表
     * @param targetClass 目标类型
     * @return
     * @author 老码农 2017-12-01 16:21:48
     */
    public static <Source, Target> List<Target> mapForList(Collection<Source> sourceList, Class<Target> targetClass) {
        return mapForList(sourceList, targetClass, null);
    }

    /**
     * 转换为列表
     *
     * @param sourceList
     * @param targetClass
     * @param convertAction
     * @return
     */
    public static <Source, Target> List<Target> mapForList(Collection<Source> sourceList, Class<Target> targetClass,
                                                           FunctionTwoAction<Source, Target> convertAction) {
        if (sourceList == null) {
            return new ArrayList<>(16);
        }
        List<Target> items = new ArrayList<Target>(sourceList.size());
        if (convertAction != null) {
            for (Source source : sourceList) {
                Target item = map(source, targetClass);
                convertAction.apply(source, item);
                items.add(item);
            }
        } else {
            for (Source source : sourceList) {
                Target item = map(source, targetClass);
                items.add(item);
            }
        }
        return items;
    }

    /**
     * 转换
     *
     * @param source      源
     * @param targetClass 目标类型
     * @return
     */
    public static <Target> Target map(Object source, Class<Target> targetClass) {
        if (source == null) {
            return null;
        }
        return MODEL_MAPPER.map(source, targetClass);
    }

    /**
     * 转换
     *
     * @param source      源
     * @param targetClass 目标类型
     * @param typeMapName
     * @return
     */
    public <Target> Target map(Object source, Class<Target> targetClass, String typeMapName) {
        if (source == null) {
            return null;
        }
        return MODEL_MAPPER.map(source, targetClass, typeMapName);
    }

    /**
     * 添加转换
     *
     * @param converter 转换器
     */
    public static <S, D> void addConverter(Converter<S, D> converter) {
        MODEL_MAPPER.addConverter(converter);
    }

    /**
     * 添加映射
     *
     * @param propertyMap
     * @return
     */
    public <S, D> TypeMap<S, D> addMappings(PropertyMap<S, D> propertyMap) {
        return MODEL_MAPPER.addMappings(propertyMap);
    }

}
