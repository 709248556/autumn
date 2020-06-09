package com.autumn.web.vo;

import com.alibaba.fastjson.annotation.JSONField;
import com.autumn.annotation.FriendlyProperty;
import lombok.Getter;
import lombok.Setter;

/**
 * Url请求抽象
 * <p>
 * </p>
 *
 * @description TODO
 * @author: 老码农
 * @create: 2020-01-01 01:04
 **/
@Getter
@Setter
public abstract class AbstractUrlRequestMapping {

    /**
     * 控制器名称
     */
    @FriendlyProperty(value = "控制器名称")
    @JSONField(ordinal = 1)
    private String controllerName;

    /**
     * 控制器说明
     */
    @FriendlyProperty(value = "控制器说明")
    @JSONField(ordinal = 2)
    private String controllerExplain;

}
