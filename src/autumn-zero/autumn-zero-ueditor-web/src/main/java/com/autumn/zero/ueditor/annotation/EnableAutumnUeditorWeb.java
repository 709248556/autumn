package com.autumn.zero.ueditor.annotation;

import com.autumn.swagger.annotation.AutumnSwaggerScan;
import com.autumn.web.annotation.ApiResponseBodyScan;
import com.autumn.zero.file.storage.annotation.EnableAutumnZeroFileStorage;
import com.autumn.zero.ueditor.configure.AutumnUeditorWebConfiguration;
import org.springframework.context.annotation.Import;

import java.lang.annotation.*;

/**
 * 启用 UeditorWeb
 *
 * @Description TODO
 * @Author 老码农
 * @Date 2019-10-16 12:09
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.TYPE)
@Documented
@EnableAutumnZeroFileStorage
@Import({AutumnUeditorWebConfiguration.class})
@ApiResponseBodyScan({EnableAutumnUeditorWeb.PACKAGE_PATH})
@AutumnSwaggerScan(groupName = "公共", order = 0, packages = {EnableAutumnUeditorWeb.PACKAGE_PATH})
public @interface EnableAutumnUeditorWeb {

    /**
     * 包路径
     */
    public static final String PACKAGE_PATH = "com.autumn.zero.ueditor.web.controllers";

}
