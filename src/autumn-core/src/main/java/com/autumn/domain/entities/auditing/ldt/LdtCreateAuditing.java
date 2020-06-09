package com.autumn.domain.entities.auditing.ldt;

import com.autumn.annotation.FriendlyProperty;

import java.time.LocalDateTime;

/**
 * 本地时间创建审计
 * <p>
 * </p>
 *
 * @description TODO
 * @author: 老码农
 * @create: 2020-02-05 16:28
 **/
public interface LdtCreateAuditing extends LdtAuditing {

    /**
     * 字段 ldtCreate
     */
    public static final String FIELD_LDT_CREATE = "ldtCreate";

    /**
     * 新建时间列名称
     */
    public final static String COLUMN_LDT_CREATE = "ldt_Create";

    /**
     * 获取创建时间
     *
     * @return
     */
    @FriendlyProperty(value = "创建时间")
    LocalDateTime getLdtCreate();

    /**
     * 设置创建时间
     *
     * @param ldtCreate 创建时间
     */
    void setLdtCreate(LocalDateTime ldtCreate);
}
