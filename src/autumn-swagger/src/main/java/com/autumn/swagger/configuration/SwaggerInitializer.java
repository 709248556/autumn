package com.autumn.swagger.configuration;

import com.autumn.swagger.annotation.ApiGroup;
import com.autumn.swagger.annotation.ApiHeaderParameter;
import com.autumn.swagger.annotation.EnableAutumnSwagger;
import org.springframework.core.annotation.AnnotationAttributes;

import java.lang.annotation.Annotation;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

/**
 * 属性配置工厂
 *
 * @author xinghua
 * @date 2018/12/21
 * @since 1.0.0
 */
class SwaggerInitializer {

    /**
     * 文档信息
     */
    static AutumnSwaggerApiInfo SWAGGER_API_INFO = null;

    /**
     * 构建 API 分组信息
     *
     * @param annotationAttributes 注解属性
     * @return API 分组信息
     */
    private static List<AutumnSwaggerApiHeaderParameterInfo> buildHeaderParameters(AnnotationAttributes annotationAttributes) {
        List<AutumnSwaggerApiHeaderParameterInfo> list = new ArrayList<>();
        AnnotationAttributes[] groupAttributes = annotationAttributes
                .getAnnotationArray(EnableAutumnSwagger.FIELD_HEADER_PARAMETERS);
        if (groupAttributes != null && groupAttributes.length > 0) {
            for (AnnotationAttributes groupAttribute : groupAttributes) {
                AutumnSwaggerApiHeaderParameterInfo headerParameter = new AutumnSwaggerApiHeaderParameterInfo();
                headerParameter.setName(groupAttribute.getString(ApiHeaderParameter.FIELD_NAME));
                headerParameter.setDataType(groupAttribute.getString(ApiHeaderParameter.FIELD_DATA_TYPE));
                headerParameter.setDescription(groupAttribute.getString(ApiHeaderParameter.FIELD_DESCRIPTION));
                headerParameter.setRequired(groupAttribute.getBoolean(ApiHeaderParameter.FIELD_REQUIRED));
                list.add(headerParameter);
            }
        }
        return list;
    }

    /**
     * 构建 API 分组信息
     *
     * @param annotationAttributes 注解属性
     * @return API 分组信息
     */
    @SuppressWarnings("unchecked")
    private static List<AutumnSwaggerApiGroupInfo> buildGroupInfos(AnnotationAttributes annotationAttributes) {
        List<AutumnSwaggerApiGroupInfo> list = new ArrayList<>();
        AnnotationAttributes[] groupAttributes = annotationAttributes
                .getAnnotationArray(EnableAutumnSwagger.FIELD_GROUPS);
        if (groupAttributes != null && groupAttributes.length > 0) {
            for (AnnotationAttributes groupAttribute : groupAttributes) {
                AutumnSwaggerApiGroupInfo group = new AutumnSwaggerApiGroupInfo();
                group.setGroupName(groupAttribute.getString(ApiGroup.FIELD_GROUP_NAME));
                Object annotation = groupAttribute.get(ApiGroup.FIELD_ANNOTATION);
                String defaultAnnotationClassName = Annotation.class.getName();
                Class<Annotation> annotationClass = null;
                String annotationClassName = "";
                if (annotation != null) {
                    annotationClass = (Class<Annotation>) annotation;
                    annotationClassName = annotationClass.getName();
                }
                if (annotationClass != null && !defaultAnnotationClassName.equals(annotationClassName)) {
                    group.setAnnotation(annotationClass);
                } else {
                    String[] arrays = groupAttribute.getStringArray(ApiGroup.FIELD_PACKAGES);
                    if (arrays != null) {
                        for (String array : arrays) {
                            group.getPackages().add(array);
                        }
                    }
                }
                list.add(group);
            }
        }
        return list;
    }

    /**
     * 构建 API 信息
     *
     * @param annotationAttributes 注解属性
     * @return API 信息
     */
    private static AutumnSwaggerApiInfo buildApiInfo(AnnotationAttributes annotationAttributes) {
        AutumnSwaggerApiInfo api = new AutumnSwaggerApiInfo();
        api.setEnableAuthorize(annotationAttributes.getBoolean(EnableAutumnSwagger.FIELD_ENABLE_AUTHORIZE));
        api.setAuthorizeHeader(annotationAttributes.getString(EnableAutumnSwagger.FIELD_AUTHORIZE_HEADER));
        api.setTitle(annotationAttributes.getString(EnableAutumnSwagger.FIELD_TITLE));
        api.setDescription(annotationAttributes.getString(EnableAutumnSwagger.FIELD_DESCRIPTION));
        api.setVersion(annotationAttributes.getString(EnableAutumnSwagger.FIELD_VERSION));
        api.setLicense(annotationAttributes.getString(EnableAutumnSwagger.FIELD_LICENSE));
        api.setLicenseUrl(annotationAttributes.getString(EnableAutumnSwagger.FIELD_LICENSE_URL));
        api.setAuthorName(annotationAttributes.getString(EnableAutumnSwagger.FIELD_AUTHOR_NAME));
        api.setAuthorUrl(annotationAttributes.getString(EnableAutumnSwagger.FIELD_AUTHOR_URL));
        api.setAuthorEmail(annotationAttributes.getString(EnableAutumnSwagger.FIELD_AUTHOR_EMAIL));

        List<AutumnSwaggerApiGroupInfo> apiGroups = buildGroupInfos(annotationAttributes);
        List<AutumnSwaggerApiHeaderParameterInfo> apiHeaderParameters = buildHeaderParameters(annotationAttributes);
        apiGroups.sort(Comparator.comparingInt(AutumnSwaggerApiGroupInfo::getOrder));
        api.setGroups(apiGroups);
        api.setHeaderParameters(apiHeaderParameters);
        return api;
    }

    /**
     * 初始化 Swagger 文档信息
     *
     * @param annotationAttributes 配置
     */
    public static void initialization(AnnotationAttributes annotationAttributes) {
        SWAGGER_API_INFO = buildApiInfo(annotationAttributes);
    }
}
