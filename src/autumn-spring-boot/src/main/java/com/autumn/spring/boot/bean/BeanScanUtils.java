package com.autumn.spring.boot.bean;

import com.autumn.exception.ExceptionUtils;
import com.autumn.util.PackageUtils;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.type.AnnotationMetadata;

import java.lang.annotation.Annotation;
import java.util.HashSet;
import java.util.LinkedHashSet;
import java.util.Set;

/**
 * Bean 扫描帮助
 * <p>
 * </p>
 *
 * @description TODO
 * @author: 老码农
 * @create: 2020-03-27 10:52
 **/
public class BeanScanUtils {

    /**
     * 根据注解元查找所在关联的注解
     *
     * @param annotationMetadata  解元
     * @param scanAnnotationClass 扫描注解类型
     * @param <TScan>             注解类型
     * @return
     */
    public static <TScan extends Annotation> Set<TScan> findStartupAnnotations(AnnotationMetadata annotationMetadata, Class<TScan> scanAnnotationClass) {
        try {
            return findStartupAnnotations(Class.forName(annotationMetadata.getClassName()), scanAnnotationClass);
        } catch (ClassNotFoundException e) {
            throw ExceptionUtils.throwConfigureException("扫描[" + annotationMetadata.getClassName() + "]出错:" + e.getMessage(), e);
        }
    }

    /**
     * 根据启动类型查找所在关联的注解
     *
     * @param startupClass        启动类型
     * @param scanAnnotationClass 扫描注解类型
     * @param <TScan>             注解类型
     * @return
     */
    public static <TScan extends Annotation> Set<TScan> findStartupAnnotations(Class<?> startupClass, Class<TScan> scanAnnotationClass) {
        Set<Annotation> annotationSet = new HashSet<>(100);
        try {
            Set<TScan> scans = new LinkedHashSet<>(annotationSet.size());
            //扫描启动类和类上引用的关联类
            findScanAnnotations(startupClass, annotationSet, scans, scanAnnotationClass);
            Set<Class<?>> typeSet = null;
            try {
                //查找启动所在的包，并检查对应的 Configuration 配置
                typeSet = PackageUtils.getPackageAnnotationClass(startupClass.getPackage().getName(), Configuration.class);
            } catch (Exception e) {
                throw ExceptionUtils.throwConfigureException("扫描[" + startupClass.getName() + "]出错:" + e.getMessage(), e);
            }
            for (Class<?> type : typeSet) {
                findScanAnnotations(type, annotationSet, scans, scanAnnotationClass);
            }
            return scans;
        } finally {
            annotationSet.clear();
        }
    }

    /**
     * 递归查找
     *
     * @param type                类型
     * @param annotationSet       重复处理排除集
     * @param scans               扫描集合
     * @param scanAnnotationClass 扫描注解类型
     * @param <TScan>             注解类型
     */
    private static <TScan extends Annotation> void findScanAnnotations(Class<?> type, Set<Annotation> annotationSet, Set<TScan> scans, Class<TScan> scanAnnotationClass) {
        TScan scanAnnotation = type.getAnnotation(scanAnnotationClass);
        if (scanAnnotation != null) {
            scans.add(scanAnnotation);
        }
        Annotation[] annotations = type.getAnnotations();
        if (annotations != null) {
            for (Annotation annotation : annotations) {
                if (annotationSet.add(annotation)) {
                    findScanAnnotations(annotation.annotationType(), annotationSet, scans, scanAnnotationClass);
                }
            }
        }
        if (type.getSuperclass() != null && !type.getSuperclass().equals(Object.class)) {
            findScanAnnotations(type.getSuperclass(), annotationSet, scans, scanAnnotationClass);
        }
    }
}
