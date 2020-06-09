package com.autumn.web.vo;

import com.autumn.annotation.FriendlyProperty;
import lombok.Getter;
import lombok.Setter;

import java.lang.reflect.Method;

/**
 * Url 请求方法信息
 * <p>
 * </p>
 *
 * @description: TODO
 * @author: 老码农
 * @create: 2020-01-10 02:57
 **/
@Getter
@Setter
public class UrlRequestMethodMappingInfo extends AbstractUrlRequestMappingInfo {

    /**
     * 方法
     */
    @FriendlyProperty(value = "方法")
    private Method method;
}
