package com.autumn.domain.entities;

import com.autumn.annotation.FriendlyProperty;

import java.io.Serializable;

/**
 * 名称实体
 *
 * @param <TKey> 主键类型
 * @Description TODO
 * @Author 老码农
 * @Date 2019-07-28 13:13
 */
public interface NameEntity<TKey extends Serializable> extends Entity<TKey> {

    /**
     * 最大 name 长度
     */
    public static final int MAX_NAME_LENGTH = 100;

    /**
     * 字段 name
     */
    public static final String FIELD_NAME = "name";

    /**
     * 获取名称
     *
     * @return
     */
    @FriendlyProperty(value = "名称")
    String getName();

    /**
     * 设置名称
     *
     * @param name 名称
     */
    void setName(String name);
}
