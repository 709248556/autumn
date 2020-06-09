package com.autumn.domain.entities.auditing.ldt;

import com.autumn.annotation.FriendlyProperty;

import java.time.LocalDateTime;

/**
 * 本时修改时间审计
 * <p>
 * </p>
 *
 * @description TODO
 * @author: 老码农
 * @create: 2020-02-05 16:34
 **/
public interface LdtModifiedAuditing extends LdtAuditing {

    /**
     * 字段 ldtModified
     */
    public static final String FIELD_LDT_MODIFIED = "ldtModified";

    /**
     * 修改时间列名称
     */
    public final static String COLUMN_LDT_MODIFIED = "ldt_modified";

    /**
     * 获取修改时间
     *
     * @return
     */
    @FriendlyProperty(value = "修改时间")
    LocalDateTime getLdtModified();

    /**
     * 设置修改时间
     *
     * @param ldtModified 修改时间
     */
    void setLdtModified(LocalDateTime ldtModified);
}
