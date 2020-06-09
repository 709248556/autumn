package com.autumn.mybatis.mapper;

import org.apache.ibatis.mapping.MappedStatement;

import java.lang.reflect.Method;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * Mapper 缓存
 *
 * @author 老码农 2018-04-01 13:40:13
 */
class MapperCache {

    /**
     * mapper 实体缓存
     */
    private final Map<Class<?>, Class<?>> mapperEntityClassMap = new ConcurrentHashMap<>();

    /**
     * mapped 方法缓存
     */
    private final Map<String, Method> mappedMethodMap = new ConcurrentHashMap<>();

    private final Class<?> parentMapperClass;

    /*
     *
     */
    public MapperCache(Class<?> parentMapperClass) {
        this.parentMapperClass = parentMapperClass;
    }

    /**
     * 获取实体类型
     *
     * @param ms
     * @return
     */
    public Class<?> getEntityClass(MappedStatement ms) {
        return this.getEntityClass(ms.getId());
    }

    /**
     * 获取实体类型
     *
     * @param mapperClass
     * @return
     */
    public Class<?> getEntityClassByMapper(Class<?> mapperClass) {
        Class<?> entityClass = this.mapperEntityClassMap.get(mapperClass);
        if (entityClass != null) {
            return entityClass;
        }
        entityClass = this.getMapperEntityClass(mapperClass);
        if (entityClass != null) {
            this.mapperEntityClassMap.put(mapperClass, entityClass);
        }
        return entityClass;
    }

    /**
     * 获取实体类型
     *
     * @param msId
     * @return
     */
    public Class<?> getEntityClass(String msId) {
        Class<?> mapperClass = MapperUtils.getMapperClass(msId);
        return this.getEntityClassByMapper(mapperClass);
    }

    /**
     * 注册方法
     *
     * @param name   名称
     * @param method 方法
     */
    public void registerMethod(String name, Method method) {
        this.mappedMethodMap.put(name, method);
    }

    /**
     * 获取方法
     *
     * @param ms
     * @return
     */
    public Method getMethod(MappedStatement ms) {
        String methodName = MapperUtils.getMethodName(ms.getId());
        return this.getMethodByName(methodName);
    }

    /**
     * 获取方法
     *
     * @param name 名称
     * @return
     */
    public Method getMethodByName(String name) {
        return this.mappedMethodMap.get(name);
    }

    /**
     * 获取 Mapper 的实体类理
     *
     * @param mapperClass mapper类型
     * @return
     */
    private Class<?> getMapperEntityClass(Class<?> mapperClass) {
        Type[] types = mapperClass.getGenericInterfaces();
        for (Type type : types) {
            if (type instanceof ParameterizedType) {
                ParameterizedType t = (ParameterizedType) type;
                if (t.getRawType().equals(this.parentMapperClass) || this.parentMapperClass.isAssignableFrom((Class<?>) t.getRawType())) {
                    return (Class<?>) t.getActualTypeArguments()[0];
                }
            }
        }
        return null;
    }

    /**
     * 获取父级 MapperClass 类型
     *
     * @return
     */
    public final Class<?> getParentMapperClass() {
        return this.parentMapperClass;
    }
}
