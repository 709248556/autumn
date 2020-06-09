package com.autumn.domain.entities.auditing.user;

import com.autumn.annotation.FriendlyProperty;
import com.autumn.domain.entities.auditing.AuditingConstants;
import com.autumn.domain.entities.auditing.SoftDelete;

/**
 * 用户删除审计
 * <p>
 * </p>
 *
 * @description TODO
 * @author: 老码农
 * @create: 2020-02-05 16:55
 **/
public interface UserDeleteAuditing extends UserAuditing, SoftDelete {

    /**
     * 字段 deletedUserId
     */
    public static final String FIELD_DELETED_USER_ID = "deletedUserId";

    /**
     * 字段 deletedUserName
     */
    public static final String FIELD_DELETED_USER_NAME = "deletedUserName";

    /**
     * 删除用户id列名称
     */
    public final static String COLUMN_DELETED_USER_ID = "deleted_user_id";

    /**
     * 删除用户名称列名称
     */
    public final static String COLUMN_DELETED_USER_NAME = "deleted_user_name";

    /**
     * 获取删除用户id
     *
     * @return
     */
    @FriendlyProperty(value = AuditingConstants.CN_NAME_DELETED_USER_ID)
    Long getDeletedUserId();

    /**
     * 设置删除用户id
     *
     * @param deletedUserId 删除用户id
     */
    void setDeletedUserId(Long deletedUserId);

    /**
     * 获取删除用户名称
     *
     * @return
     */
    @FriendlyProperty(value = AuditingConstants.CN_NAME_DELETED_USER_NAME)
    String getDeletedUserName();

    /**
     * 设置删除用户名称
     *
     * @param deletedUserName 删除的用户名称
     */
    void setDeletedUserName(String deletedUserName);
}
