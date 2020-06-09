package com.autumn.zero.authorization.entities.common;

import com.autumn.constants.SettingConstants;
import com.autumn.domain.entities.AbstractDefaultEntity;
import com.autumn.mybatis.mapper.annotation.ColumnDocument;
import com.autumn.mybatis.mapper.annotation.ColumnOrder;
import com.autumn.mybatis.mapper.annotation.Index;
import com.autumn.mybatis.mapper.annotation.TableDocument;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.Column;
import javax.persistence.Table;

/**
 * 用户角色
 *
 * @author 老码农 2018-11-25 00:37:55
 */
@ToString(callSuper = true)
@Setter
@Getter
@Table(name = SettingConstants.SYS_TABLE_PREFIX + "_user_role")
@TableDocument(value = "用户角色", group = "系统表", groupOrder = Integer.MAX_VALUE)
public class UserRole extends AbstractDefaultEntity {

    /**
     *
     */
    private static final long serialVersionUID = -809690005089160799L;

    /**
     * 字段 userId
     */
    public static final String FIELD_USER_ID = "userId";

    /**
     * 字段 roleId
     */
    public static final String FIELD_ROLE_ID = "roleId";

    /**
     * 用户id
     */
    @Column(name = "user_id", nullable = false)
    @Index
    @ColumnOrder(1)
    @ColumnDocument("用户id")
    private Long userId;

    /**
     * 角色Id
     */
    @Column(name = "role_id", nullable = false)
    @Index
    @ColumnOrder(2)
    @ColumnDocument("角色Id")
    private Long roleId;

}
