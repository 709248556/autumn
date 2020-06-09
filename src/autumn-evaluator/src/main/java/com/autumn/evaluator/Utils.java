package com.autumn.evaluator;

import com.autumn.exception.ArgumentNullException;
import com.autumn.util.StringUtils;
import com.autumn.util.TypeUtils;

import java.lang.annotation.Annotation;
import java.util.HashSet;
import java.util.Set;

public class Utils {

    public static <T> T checkNotNull(String parameterName, T value) {
        if (value == null) {
            throw new ArgumentNullException(parameterName);
        }
        return value;
    }

    public static String checkNotNullOrEmpty(String parameterName, String value) {
        if (StringUtils.isNullOrBlank(value)) {
            checkNotNull(parameterName, value);
            throw new ArgumentNullException(parameterName);
        }
        return value;
    }

    /**
     * 获取某类中所有内部类包路径
     *
     * @param clazz
     * @return
     */
    public static Set<String> getClassName(Class<?> clazz) {
        Set<String> fileNames = new HashSet<>();
        // 获取该class对象的全部内部类
        Class<?>[] inners = clazz.getDeclaredClasses();
        for (Class<?> inner : inners) {
            fileNames.add(inner.getName());
        }
        return fileNames;
    }

    /**
     * 获取某类下特定注解的所有类型
     *
     * @param clazz
     * @param annotationClass
     * @return
     */
    public static Set<Class<?>> getPackageAnnotationClass(Class<?> clazz,
                                                          Class<? extends Annotation> annotationClass) {
        if (annotationClass == null) {
            throw new NullPointerException("annotationClass 为 null");
        }
        Set<String> className = getClassName(clazz);
        Set<Class<?>> types = new HashSet<>();
        for (String name : className) {
            try {
                Class<?> type = Class.forName(name);
                if (TypeUtils.isAnnotationClass(type, annotationClass)) {
                    types.add(type);
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return types;
    }

    /**
     * 字符串以固定字符串进行连接
     *
     * @param separator
     * @param stringArray
     * @return
     */
    public static String join(String separator, String[] stringArray) {
        if (stringArray == null) {
            return null;
        } else {
            return join(separator, stringArray, 0, stringArray.length);
        }
    }

    public static String join(String separator, String[] stringArray, int startIndex, int count) {
        StringBuilder result = new StringBuilder();
        if (stringArray == null) {
            return null;
        }
        for (int index = startIndex; index < stringArray.length && index - startIndex < count; index++) {
            if (separator != null && index > startIndex) {
                result.append(separator);
            }
            if (stringArray[index] != null) {
                result.append(stringArray[index]);
            }
        }
        return result.toString();
    }

}