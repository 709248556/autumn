package com.autumn.web.vo;

import com.alibaba.fastjson.annotation.JSONField;
import com.autumn.annotation.FriendlyProperty;
import lombok.Getter;
import lombok.Setter;

/**
 * Url 请求信息抽象
 * <p>
 * </p>
 *
 * @description TODO
 * @author: 老码农
 * @create: 2019-12-31 19:15
 **/
@Getter
@Setter
public class AbstractUrlRequestMappingInfo extends AbstractUrlRequestMapping implements Comparable<AbstractUrlRequestMappingInfo> {

    /**
     * 操作
     */
    @FriendlyProperty(value = "操作")
    @JSONField(ordinal = 10)
    private String operation;

    /**
     * url地址
     */
    @FriendlyProperty(value = "url地址")
    @JSONField(ordinal = 11)
    private String url;

    /**
     * 方法列表
     */
    @FriendlyProperty(value = "方法列表")
    @JSONField(ordinal = 13)
    private String methods;

    /**
     * 说明
     */
    @FriendlyProperty(value = "说明")
    @JSONField(ordinal = 14)
    private String explain;

    @Override
    public int compareTo(AbstractUrlRequestMappingInfo o) {
        return this.getUrl().toLowerCase().compareTo(o.getUrl().toLowerCase());
    }
}
