package com.autumn.domain.entities.auditing.user;

import com.autumn.annotation.FriendlyProperty;
import com.autumn.domain.entities.auditing.AuditingConstants;

/**
 * 用户创建审计
 * <p>
 * </p>
 *
 * @description TODO
 * @author: 老码农
 * @create: 2020-02-05 16:51
 **/
public interface UserCreateAuditing extends UserAuditing {

    /**
     * 字段 createdUserId
     */
    public static final String FIELD_CREATED_USER_ID = "createdUserId";

    /**
     * 字段 createdUserName
     */
    public static final String FIELD_CREATED_USER_NAME = "createdUserName";

    /**
     * 创建用户id列名称
     */
    public final static String COLUMN_CREATED_USER_ID = "created_user_id";

    /**
     * 创建用户名称列名称
     */
    public final static String COLUMN_CREATED_USER_NAME = "created_user_name";

    /**
     * 获取创建用户id
     *
     * @return
     */
    @FriendlyProperty(value = AuditingConstants.CN_NAME_CREATED_USER_ID)
    Long getCreatedUserId();

    /**
     * 设置创建用户id
     *
     * @param createdUserId 用户id
     */
    void setCreatedUserId(Long createdUserId);

    /**
     * 获取创建用户名称
     *
     * @return
     */
    @FriendlyProperty(value = AuditingConstants.CN_NAME_CREATED_USER_NAME)
    String getCreatedUserName();

    /**
     * 设置创建用户名称
     *
     * @param createdUserName 用户名称
     */
    void setCreatedUserName(String createdUserName);
}
