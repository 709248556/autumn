package com.autumn.util;

import org.springframework.core.io.Resource;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;
import org.springframework.core.io.support.ResourcePatternResolver;
import org.springframework.core.type.classreading.CachingMetadataReaderFactory;
import org.springframework.core.type.classreading.MetadataReader;
import org.springframework.core.type.classreading.MetadataReaderFactory;
import org.springframework.util.ClassUtils;

import java.io.IOException;
import java.lang.annotation.Annotation;
import java.lang.reflect.Modifier;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.function.Predicate;

/**
 * 包帮助
 *
 * @author 老码农
 * @date 2017-06-06 16:00:04
 */
public class PackageUtils {
    static final String DEFAULT_RESOURCE_PATTERN = "**/*.class";

    /**
     * 符合条件
     *
     * @param type               类型
     * @param isIncludeInterface
     * @param isIncludeAbstract
     * @param isIncludePrivate
     * @return
     */
    private static boolean canPredicate(Class<?> type, boolean isIncludeInterface, boolean isIncludeAbstract, boolean isIncludePrivate) {
        int mod = type.getModifiers();
        if (!isIncludeInterface && Modifier.isInterface(mod)) {
            return false;
        }
        if (!isIncludeAbstract && Modifier.isAbstract(mod)) {
            return false;
        }
        if (Modifier.isPublic(mod)) {
            return true;
        } else {
            return isIncludePrivate;
        }
    }

    /**
     * 获取某包下的目标类型
     *
     * @param packageName        包名
     * @param targetClass        目标类型
     * @param isIncludeInterface 是否包含接口
     * @param isIncludeAbstract  是否包含抽象
     * @param isIncludePrivate   是否包含私有
     * @return 类集
     * @throws IOException            IO异常
     * @throws ClassNotFoundException 类不存在异常
     */
    public static Set<Class<?>> getPackageTargetClass(String packageName, Class<?> targetClass,
                                                      boolean isIncludeInterface, boolean isIncludeAbstract, boolean isIncludePrivate) throws IOException, ClassNotFoundException {
        if (targetClass == null) {
            throw new NullPointerException("targetClass 为 null");
        }
        return getPackageClass(packageName, type -> {
            if (targetClass.isAssignableFrom(type)) {
                return canPredicate(type, isIncludeInterface, isIncludeAbstract, isIncludePrivate);
            } else {
                return false;
            }
        });
    }

    /**
     * 获取某包下特定注解的所有类型
     *
     * @param packageName        包名称
     * @param annotationClass    注解类型
     * @param isIncludeInterface 包含接口
     * @param isIncludeAbstract  包含抽象
     * @param isIncludePrivate   包含私有
     * @return
     * @throws IOException            IO异常
     * @throws ClassNotFoundException 类不存在异常
     */
    public static Set<Class<?>> getPackageAnnotationClass(String packageName,
                                                          Class<? extends Annotation> annotationClass, boolean isIncludeInterface, boolean isIncludeAbstract,
                                                          boolean isIncludePrivate) throws IOException, ClassNotFoundException {
        if (annotationClass == null) {
            throw new NullPointerException("annotationClass 为 null");
        }
        return getPackageClass(packageName, type -> {
            if (TypeUtils.isAnnotationClass(type, annotationClass)) {
                return canPredicate(type, isIncludeInterface, isIncludeAbstract, isIncludePrivate);
            } else {
                return false;
            }
        });
    }

    /**
     * 获取某包下特定注解的所有类型
     *
     * @param packageName     包名
     * @param annotationClass 注解类型
     * @return 类集
     * @throws IOException            IO异常
     * @throws ClassNotFoundException 类不存在异常
     */
    public static Set<Class<?>> getPackageAnnotationClass(String packageName,
                                                          Class<? extends Annotation> annotationClass) throws IOException, ClassNotFoundException {
        if (annotationClass == null) {
            throw new NullPointerException("annotationClass 为 null");
        }
        return getPackageClass(packageName, type -> TypeUtils.isAnnotationClass(type, annotationClass));
    }

    /**
     * 获取某包下所有类型
     *
     * @param packageName 包名
     * @return 类集
     * @throws IOException            IO异常
     * @throws ClassNotFoundException 类不存在异常
     */
    public static Set<Class<?>> getPackageClass(String packageName) throws IOException, ClassNotFoundException {
        return getPackageClass(packageName, null);
    }

    /**
     * 获取某包下所有类型
     *
     * @param packageName 包名
     * @param predicate   条件
     * @return
     * @throws IOException            IO异常
     * @throws ClassNotFoundException 类不存在异常
     */
    public static Set<Class<?>> getPackageClass(String packageName, Predicate<Class<?>> predicate) throws IOException, ClassNotFoundException {
        Set<String> className = getClassName(packageName);
        Set<Class<?>> types = new HashSet<Class<?>>();
        for (String name : className) {
            Class<?> type = Class.forName(name);
            if (predicate == null || (predicate != null && predicate.test(type))) {
                types.add(type);
            }
        }
        return types;
    }

    /**
     * 获取某包下所有类
     *
     * @param packageName 包名
     * @return 类列表
     * @throws IOException
     */
    public static Set<String> getClassName(String packageName) throws IOException {
        if (StringUtils.isNullOrBlank(packageName)) {
            throw new NullPointerException("packageName 为 null 或空字符串");
        }
        Set<String> fileNames = new HashSet<String>();
        ResourcePatternResolver resolver = new PathMatchingResourcePatternResolver();
        MetadataReaderFactory metadataReaderFactory = new CachingMetadataReaderFactory(resolver);
        String fullPackage = ResourcePatternResolver.CLASSPATH_ALL_URL_PREFIX
                + ClassUtils.convertClassNameToResourcePath(packageName) + "/" + DEFAULT_RESOURCE_PATTERN;
        Resource[] resources = resolver.getResources(fullPackage);
        if (resources != null && resources.length > 0) {
            for (Resource resource : resources) {
                if (resource.isReadable()) {
                    MetadataReader metadataReader = metadataReaderFactory.getMetadataReader(resource);
                    if (metadataReader != null) {
                        fileNames.add(metadataReader.getClassMetadata().getClassName());
                    }
                }
            }
        }
        return fileNames;
    }

    /**
     * 处理包中的分隔符
     *
     * @param packages
     * @return
     */
    public static String[] toPackages(String[] packages) {
        if (packages == null) {
            return new String[0];
        }
        List<String> list = new ArrayList<>();
        for (String pak : packages) {
            if (!com.autumn.util.StringUtils.isNullOrBlank(pak)) {
                String[] paks = StringUtils.urlOrPackageToStringArray(pak);
                for (String s : paks) {
                    if (!list.contains(s)) {
                        list.add(s);
                    }
                }
            }
        }
        return list.toArray(new String[0]);
    }

}
