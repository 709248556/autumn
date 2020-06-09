package com.autumn.util;

import com.autumn.exception.ExceptionUtils;

import java.util.Map;

/**
 * 服务帮肋
 * <p>
 * </p>
 *
 * @description TODO
 * @author: 老码农
 * @create: 2020-03-23 13:00
 **/
public class ServiceUtils {

    /**
     * 获取泛型实际类型
     *
     * @param genericArgTypeName 泛型参数类型名称
     * @return
     */
    @SuppressWarnings("unchecked")
    public static <TArg> Class<TArg> getGenericActualClass(Map<String, Class<?>> genericActualArgumentsTypeMap, String genericArgTypeName) {
        Class<?> genericType = genericActualArgumentsTypeMap.get(genericArgTypeName);
        if (genericType == null) {
            ExceptionUtils.throwSystemException("泛型参数[" + genericArgTypeName + "]无法获取类型。");
        }
        Class<TArg> resultType = null;
        try {
            resultType = (Class<TArg>) genericType;
        } catch (Exception err) {
            throw ExceptionUtils.throwSystemException("泛型参数[" + genericArgTypeName + "]获取的类型[" + genericType.getName() + "]无法转换为目标类型。");
        }
        return resultType;
    }
}
