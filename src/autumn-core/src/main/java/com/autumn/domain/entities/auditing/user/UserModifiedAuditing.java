package com.autumn.domain.entities.auditing.user;

import com.autumn.annotation.FriendlyProperty;
import com.autumn.domain.entities.auditing.AuditingConstants;

/**
 * 用户修改审计
 * <p>
 * </p>
 *
 * @description TODO
 * @author: 老码农
 * @create: 2020-02-05 16:56
 **/
public interface UserModifiedAuditing extends UserAuditing {

    /**
     * 字段 modifiedUserId
     */
    public static final String FIELD_MODIFIED_USER_ID = "modifiedUserId";

    /**
     * 字段 modifiedUserName
     */
    public static final String FIELD_MODIFIED_USER_NAME = "modifiedUserName";

    /**
     * 修改用户id列名称
     */
    public final static String COLUMN_MODIFIED_USER_ID = "modified_user_id";

    /**
     * 修改用户名称列名称
     */
    public final static String COLUMN_MODIFIED_USER_NAME = "modified_user_name";

    /**
     * 获取修改用户id
     *
     * @return
     */
    @FriendlyProperty(value = AuditingConstants.CN_NAME_MODIFIED_USER_ID)
    Long getModifiedUserId();

    /**
     * 设置修改用户id
     *
     * @param modifiedUserId 修改用户id
     */
    void setModifiedUserId(Long modifiedUserId);

    /**
     * 获取修改用户名称
     *
     * @return
     */
    @FriendlyProperty(value = AuditingConstants.CN_NAME_DELETED_USER_NAME)
    String getModifiedUserName();

    /**
     * 设置修改用户名称
     *
     * @param modifiedUserName 修改的用户名称
     */
    void setModifiedUserName(String modifiedUserName);
}
