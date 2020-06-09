package com.autumn.mybatis.mapper.annotation;

import com.autumn.mybatis.mapper.SpecificationDefinition;
import com.autumn.mybatis.mapper.impl.StandardSpecificationDefinitionImpl;
import org.springframework.context.annotation.Import;

import java.lang.annotation.*;

/**
 * 全局的规范类型
 * <p>
 * 如果特定表指定了 {@link TableSpecification} 则按表规范配置
 * </p>
 *
 * @Description TODO
 * @Author 老码农
 * @Date 2019-08-25 16:42
 */
@Target({ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Import({GlobalSpecificationRegistrar.class})
public @interface GlobalSpecification {

    /**
     * 规范类型
     *
     * @return
     */
    Class<? extends SpecificationDefinition> specificationType() default StandardSpecificationDefinitionImpl.class;
}
