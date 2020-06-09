package com.autumn.swagger.annotation;

import java.lang.annotation.*;

/**
 * Swagger组信息
 *
 * @author xinghua
 * @date 2018/12/20
 * @since 1.0.0
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.TYPE)
@Documented
public @interface ApiGroup {

    /**
     * 字段 groupName
     */
    public static final String FIELD_GROUP_NAME = "groupName";

	/**
	 * 字段 顺序
	 */
	public static final String FIELD_ORDER = "order";

    /**
     * 字段 packages
     */
    public static final String FIELD_PACKAGES = "packages";

    /**
     * 字段 annotation
     */
    public static final String FIELD_ANNOTATION = "annotation";

    /**
     * 组名称
     *
     * @return 组名称
     */
    String groupName() default "default";

    /**
     * 顺序
     *
     * @return
     */
    int order() default 0;

    /**
     * 组包含的 Api 的包集合
     *
     * @return 组包含的 Api 的包集合
     */
    String[] packages() default {};

    /**
     * 组包含的 API 的注解
     *
     * @return 组包含的 API 的注解
     */
    Class<? extends Annotation> annotation() default Annotation.class;
}
