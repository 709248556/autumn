package com.autumn.swagger.annotation;

import com.autumn.swagger.configuration.AutumnSwaggerConfiguration;
import com.autumn.swagger.configuration.AutumnSwaggerRegistrar;
import org.springframework.context.annotation.Import;

import java.lang.annotation.*;

/**
 * 启用 Autumn Swagger
 * <p>
 * 任何包含 @Api 的控制器均会被扫描
 * </P>
 *
 * @author 老码农
 * <p>
 * 2017-12-01 10:05:08
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.TYPE)
@Documented
@Import({AutumnSwaggerRegistrar.class, AutumnSwaggerConfiguration.class})
public @interface EnableAutumnSwagger {

    /**
     * 字段 title
     */
    public static final String FIELD_TITLE = "title";

    /**
     * 字段 description
     */
    public static final String FIELD_DESCRIPTION = "description";

    /**
     * 字段 version
     */
    public static final String FIELD_VERSION = "version";

    /**
     * 字段 license
     */
    public static final String FIELD_LICENSE = "license";

    /**
     * 字段 licenseUrl
     */
    public static final String FIELD_LICENSE_URL = "licenseUrl";

    /**
     * 字段 enableAuthorize
     */
    public static final String FIELD_ENABLE_AUTHORIZE = "enableAuthorize";

    /**
     * 字段 authorizeHeader
     */
    public static final String FIELD_AUTHORIZE_HEADER = "authorizeHeader";

    /**
     * 字段 authorName
     */
    public static final String FIELD_AUTHOR_NAME = "authorName";

    /**
     * 字段 authorUrl
     */
    public static final String FIELD_AUTHOR_URL = "authorUrl";

    /**
     * 字段 authorEmail
     */
    public static final String FIELD_AUTHOR_EMAIL = "authorEmail";

    /**
     * 字段 groups
     */
    public static final String FIELD_GROUPS = "groups";

    /**
     * 字段 headerParameters
     */
    public static final String FIELD_HEADER_PARAMETERS = "headerParameters";

    /**
     * 标题
     *
     * @return
     */
    String title() default "autumn framework api docs";

    /**
     * API文档描述
     *
     * @return API文档描述
     */
    String description() default "autumn framework api docs";

    /**
     * API文档版本
     *
     * @return API文档版本
     */
    String version() default "1.0";

    /**
     * API协议
     *
     * @return API协议
     */
    String license() default "";

    /**
     * API协议地址
     *
     * @return API协议地址
     */
    String licenseUrl() default "";

    /**
     * 是否启用授权
     *
     * @return 是否启用授权
     */
    boolean enableAuthorize() default false;

    /**
     * header 中的授权名称
     *
     * @return 中的授权名称
     */
    String authorizeHeader() default "authorization";

    /**
     * 作者名字
     *
     * @return 作者名字
     */
    String authorName() default "autumn";

    /**
     * 作者主页
     *
     * @return 作者主页
     */
    String authorUrl() default "https://www.autumn.com";

    /**
     * 作者邮件地址
     *
     * @return 作者邮件地址
     */
    String authorEmail() default "example@autumn.com";

    /**
     * API 组
     *
     * @return API 组
     */
    ApiGroup[] groups();

    /**
     * 请求头参数集合
     *
     * @return
     */
    ApiHeaderParameter[] headerParameters() default {};
}
