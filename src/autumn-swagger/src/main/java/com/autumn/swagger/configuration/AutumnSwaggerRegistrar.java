package com.autumn.swagger.configuration;

import com.autumn.spring.boot.bean.AbstractImportBeanRegistrar;
import com.autumn.spring.boot.bean.BeanRegisterManager;
import com.autumn.spring.boot.bean.BeanScanUtils;
import com.autumn.swagger.annotation.*;
import com.autumn.util.PackageUtils;
import org.springframework.beans.factory.support.BeanDefinitionRegistry;
import org.springframework.beans.factory.support.GenericBeanDefinition;
import org.springframework.context.annotation.ImportBeanDefinitionRegistrar;
import org.springframework.core.annotation.AnnotationAttributes;
import org.springframework.core.type.AnnotationMetadata;

import java.lang.annotation.Annotation;
import java.util.*;

/**
 * Swagger 配置 扫描
 *
 * @author 老码农
 * <p>
 * 2017-12-01 10:25:01
 */
public class AutumnSwaggerRegistrar extends AbstractImportBeanRegistrar
        implements ImportBeanDefinitionRegistrar {

    private Map<String, AutumnSwaggerApiGroupInfo> createGroupMap(AnnotationMetadata importingClassMetadata) {
        Set<AutumnSwaggerScan> scanSubSet = BeanScanUtils.findStartupAnnotations(importingClassMetadata, AutumnSwaggerScan.class);
        Set<AutumnSwaggerScans> scanSet = BeanScanUtils.findStartupAnnotations(importingClassMetadata, AutumnSwaggerScans.class);
        Map<String, AutumnSwaggerApiGroupInfo> groupMap = new LinkedHashMap<>(scanSet.size());
        for (AutumnSwaggerScans scans : scanSet) {
            if (scans.value() != null) {
                for (AutumnSwaggerScan scan : scans.value()) {
                    this.swaggerScan(groupMap, scan);
                }
            }
        }
        for (AutumnSwaggerScan scan : scanSubSet) {
            this.swaggerScan(groupMap, scan);
        }
        return groupMap;
    }

    private void swaggerScan(Map<String, AutumnSwaggerApiGroupInfo> groupMap, AutumnSwaggerScan scan) {
        AutumnSwaggerApiGroupInfo groupInfo = groupMap.computeIfAbsent(scan.groupName(), key -> {
            AutumnSwaggerApiGroupInfo group = new AutumnSwaggerApiGroupInfo();
            if (scan.annotation() != null && !Annotation.class.equals(scan.annotation())) {
                group.setAnnotation(scan.annotation());
            } else {
                group.setAnnotation(null);
            }
            group.setGroupName(scan.groupName());
            group.setOrder(scan.order());
            return group;
        });
        if (scan.packages() != null && groupInfo.getAnnotation() == null) {
            String[] pakArray = PackageUtils.toPackages(scan.packages());
            for (String pak : pakArray) {
                groupInfo.getPackages().add(pak);
            }
        }
    }

    @Override
    public void registerBeanDefinitions(AnnotationMetadata importingClassMetadata, BeanDefinitionRegistry registry) {
        AnnotationAttributes annoAttrs = AnnotationAttributes
                .fromMap(importingClassMetadata.getAnnotationAttributes(EnableAutumnSwagger.class.getName()));
        Map<String, AutumnSwaggerApiGroupInfo> groupMap = createGroupMap(importingClassMetadata);
        BeanRegisterManager regManager = new BeanRegisterManager(this.getEnvironment(),
                this.getResourceLoader(), registry);

        Map<String, Object> propertyValues = new HashMap<>(16);

        propertyValues.put(EnableAutumnSwagger.FIELD_ENABLE_AUTHORIZE, annoAttrs.getBoolean(EnableAutumnSwagger.FIELD_ENABLE_AUTHORIZE));
        propertyValues.put(EnableAutumnSwagger.FIELD_AUTHORIZE_HEADER, annoAttrs.getString(EnableAutumnSwagger.FIELD_AUTHORIZE_HEADER));
        propertyValues.put(EnableAutumnSwagger.FIELD_TITLE, annoAttrs.getString(EnableAutumnSwagger.FIELD_TITLE));
        propertyValues.put(EnableAutumnSwagger.FIELD_DESCRIPTION, annoAttrs.getString(EnableAutumnSwagger.FIELD_DESCRIPTION));
        propertyValues.put(EnableAutumnSwagger.FIELD_VERSION, annoAttrs.getString(EnableAutumnSwagger.FIELD_VERSION));
        propertyValues.put(EnableAutumnSwagger.FIELD_LICENSE, annoAttrs.getString(EnableAutumnSwagger.FIELD_LICENSE));
        propertyValues.put(EnableAutumnSwagger.FIELD_LICENSE_URL, annoAttrs.getString(EnableAutumnSwagger.FIELD_LICENSE_URL));
        propertyValues.put(EnableAutumnSwagger.FIELD_AUTHOR_NAME, annoAttrs.getString(EnableAutumnSwagger.FIELD_AUTHOR_NAME));
        propertyValues.put(EnableAutumnSwagger.FIELD_AUTHOR_URL, annoAttrs.getString(EnableAutumnSwagger.FIELD_AUTHOR_URL));
        propertyValues.put(EnableAutumnSwagger.FIELD_AUTHOR_EMAIL, annoAttrs.getString(EnableAutumnSwagger.FIELD_AUTHOR_EMAIL));
        propertyValues.put(EnableAutumnSwagger.FIELD_GROUPS, createGroupInfos(annoAttrs, groupMap));
        propertyValues.put(EnableAutumnSwagger.FIELD_HEADER_PARAMETERS, createHeaderParameters(annoAttrs));

        GenericBeanDefinition beanDefinition = regManager.createBeanDefinition(AutumnSwaggerApiInfo.class, propertyValues, null, null);
        regManager.registerBean(null, beanDefinition);
    }

    /**
     * 创建 API 分组信息
     *
     * @param annotationAttributes
     * @param groupMap
     * @return
     */
    @SuppressWarnings("unchecked")
    private List<AutumnSwaggerApiGroupInfo> createGroupInfos(AnnotationAttributes annotationAttributes, Map<String, AutumnSwaggerApiGroupInfo> groupMap) {
        AnnotationAttributes[] groupAttributes = annotationAttributes.getAnnotationArray(EnableAutumnSwagger.FIELD_GROUPS);
        if (groupAttributes != null && groupAttributes.length > 0) {
            for (AnnotationAttributes groupAttribute : groupAttributes) {
                AutumnSwaggerApiGroupInfo groupInfo = groupMap.computeIfAbsent(groupAttribute.getString(ApiGroup.FIELD_GROUP_NAME), key -> {
                    Object annotation = groupAttribute.get(ApiGroup.FIELD_ANNOTATION);
                    AutumnSwaggerApiGroupInfo group = new AutumnSwaggerApiGroupInfo();
                    if (annotation != null) {
                        Class<? extends Annotation> ann = (Class<? extends Annotation>) annotation;
                        if (!ann.equals(Annotation.class)) {
                            group.setAnnotation(ann);
                        } else {
                            group.setAnnotation(null);
                        }
                    } else {
                        group.setAnnotation(null);
                    }
                    group.setGroupName(key);
                    group.setOrder(groupAttribute.getNumber(ApiGroup.FIELD_ORDER));
                    return group;
                });
                if (groupInfo.getAnnotation() == null) {
                    String[] arrays = PackageUtils.toPackages(groupAttribute.getStringArray(ApiGroup.FIELD_PACKAGES));
                    if (arrays != null) {
                        for (String array : arrays) {
                            groupInfo.getPackages().add(array);
                        }
                    }
                }
            }
        }
        List<AutumnSwaggerApiGroupInfo> list = new ArrayList<>(groupMap.values());
        list.sort(Comparator.comparingInt(AutumnSwaggerApiGroupInfo::getOrder));
        groupMap.clear();
        return list;
    }

    /**
     * 创建 API 分组信息
     *
     * @param annotationAttributes 注解属性
     * @return API 分组信息
     */
    private List<AutumnSwaggerApiHeaderParameterInfo> createHeaderParameters(AnnotationAttributes annotationAttributes) {
        List<AutumnSwaggerApiHeaderParameterInfo> list;
        AnnotationAttributes[] groupAttributes = annotationAttributes
                .getAnnotationArray(EnableAutumnSwagger.FIELD_HEADER_PARAMETERS);
        if (groupAttributes != null && groupAttributes.length > 0) {
            list = new ArrayList<>(groupAttributes.length);
            for (AnnotationAttributes groupAttribute : groupAttributes) {
                AutumnSwaggerApiHeaderParameterInfo headerParameter = new AutumnSwaggerApiHeaderParameterInfo();
                headerParameter.setName(groupAttribute.getString(ApiHeaderParameter.FIELD_NAME));
                headerParameter.setDataType(groupAttribute.getString(ApiHeaderParameter.FIELD_DATA_TYPE));
                headerParameter.setDescription(groupAttribute.getString(ApiHeaderParameter.FIELD_DESCRIPTION));
                headerParameter.setRequired(groupAttribute.getBoolean(ApiHeaderParameter.FIELD_REQUIRED));
                list.add(headerParameter);
            }
        } else {
            list = new ArrayList<>(0);
        }
        return list;
    }
}
