package com.autumn.evaluator.annotation;

import com.autumn.evaluator.configure.AutumnEvaluatorConfiguration;
import org.springframework.context.annotation.Import;

import java.lang.annotation.*;

/**
 * 启用解析
 *
 * @Description TODO
 * @Author 老码农
 * @Date 2019-08-12 17:42
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.TYPE)
@Documented
@Import({AutumnEvaluatorConfiguration.class})
public @interface EnableAutumnEvaluator {


}
