package com.autumn.zero.authorization.entities.common;

import com.autumn.constants.SettingConstants;
import com.autumn.mybatis.mapper.annotation.*;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.Column;
import javax.persistence.Table;

/**
 * 用户授权
 *
 * @author 老码农 2018-11-25 01:25:55
 */
@ToString(callSuper = true)
@Setter
@Getter
@Table(name = SettingConstants.SYS_TABLE_PREFIX + "_user_permission")
@ComplexIndexs(indexs = {@ComplexIndex(propertys = {"resourcesId", "userId", "name"}, unique = true)})
@TableDocument(value = "用户权限", group = "系统表", groupOrder = Integer.MAX_VALUE)
public class UserPermission extends AbstractPermission {

    /**
     *
     */
    private static final long serialVersionUID = 5815177856195962269L;

    /**
     * 字段 userId
     */
    public static final String FIELD_USER_ID = "userId";

    /**
     * 用户id
     */
    @Column(name = "user_id", nullable = false)
    @Index
    @ColumnOrder(1)
    @ColumnDocument("用户id")
    private Long userId;
}
