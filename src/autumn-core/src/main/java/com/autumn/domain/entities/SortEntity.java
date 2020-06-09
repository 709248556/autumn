package com.autumn.domain.entities;

import com.autumn.annotation.FriendlyProperty;

import java.io.Serializable;

/**
 * 具有排序的实体
 *
 * @param <TKey> 主键类型
 */
public interface SortEntity<TKey extends Serializable> extends Entity<TKey> {

    /**
     * 字段 sortId
     */
    public static final String FIELD_SORT_ID = "sortId";

    /**
     * 获取顺序id
     *
     * @return
     */
    @FriendlyProperty(value = "顺序")
    Integer getSortId();

    /**
     * 设置顺序id
     *
     * @param sortId 顺序id
     */
    void setSortId(Integer sortId);
}
