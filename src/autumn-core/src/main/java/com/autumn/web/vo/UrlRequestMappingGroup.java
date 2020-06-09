package com.autumn.web.vo;

import com.alibaba.fastjson.annotation.JSONField;
import com.autumn.annotation.FriendlyProperty;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

/**
 * Url请求映射组
 * <p>
 * </p>
 *
 * @description TODO
 * @author: 老码农
 * @create: 2020-01-01 01:03
 **/
@Getter
@Setter
public class UrlRequestMappingGroup<Mapping extends AbstractUrlRequestMappingInfo>
        extends AbstractUrlRequestMapping
        implements Comparable<UrlRequestMappingGroup<Mapping>> {

    /**
     * 映射集合
     */
    @FriendlyProperty(value = "映射集合")
    @JSONField(ordinal = 10)
    private List<Mapping> mappings;

    @Override
    public int compareTo(UrlRequestMappingGroup<Mapping> o) {
        return this.getControllerName().toLowerCase().compareTo(o.getControllerName().toLowerCase());
    }
}
