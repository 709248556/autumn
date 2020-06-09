package com.autumn.mybatis.annotation;

import com.autumn.mybatis.configure.AutumnPerformanceBeanRegistrar;
import org.springframework.context.annotation.Import;

import java.lang.annotation.*;

/**
 * 启用性能监视
 * <p>
 * </p>
 *
 * @description TODO
 * @author: 老码农
 * @create: 2020-04-07 01:21
 **/
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.TYPE)
@Documented
@Import({AutumnPerformanceBeanRegistrar.class})
public @interface EnableAutumnPerformance {

    /**
     * 最大执行时间(毫秒)，超过则写入警告
     *
     * @return 只有大于0才有效
     */
    long maxExecuteTime() default 0L;

    /**
     * 是否写入日志
     *
     * @return
     */
    boolean writeInLog() default false;

    /**
     * 是否打印到控制台(生产环境应配置 false)
     *
     * @return
     */
    boolean consolePrint() default true;

}
