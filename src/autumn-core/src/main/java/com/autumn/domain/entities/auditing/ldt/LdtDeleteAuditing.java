package com.autumn.domain.entities.auditing.ldt;

import com.autumn.annotation.FriendlyProperty;
import com.autumn.domain.entities.auditing.SoftDelete;

import java.time.LocalDateTime;

/**
 * 本地时间删除审计
 * <p>
 * </p>
 *
 * @description TODO
 * @author: 老码农
 * @create: 2020-02-05 16:36
 **/
public interface LdtDeleteAuditing extends SoftDelete, LdtAuditing {

    /**
     * 字段 ldtDelete
     */
    public static final String FIELD_LDT_DELETE = "ldtDelete";

    /**
     * 删除时间列名称
     */
    public final static String COLUMN_LDT_DELETE = "ldt_delete";

    /**
     * 获取删除时间
     *
     * @return
     */
    @FriendlyProperty(value = "删除时间")
    LocalDateTime getLdtDelete();

    /**
     * 设置删除时间
     *
     * @param ldtDelete 删除时间
     */
    void setLdtDelete(LocalDateTime ldtDelete);
}
