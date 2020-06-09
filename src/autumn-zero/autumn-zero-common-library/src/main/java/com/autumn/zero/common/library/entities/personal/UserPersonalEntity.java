package com.autumn.zero.common.library.entities.personal;

import com.autumn.domain.entities.Entity;

/**
 * 用户实体
 * <p>
 * </p>
 *
 * @description TODO
 * @author: 老码农
 * @create: 2020-03-26 18:15
 **/
public interface UserPersonalEntity extends Entity<Long> {

    /**
     * 字段 userId (用户id)
     */
    public static final String FIELD_USER_ID = "userId";

    /**
     * 获取用户id
     *
     * @return
     */
    Long getUserId();

    /**
     * 设置用户id
     *
     * @param userId 用户id
     */
    void setUserId(Long userId);

}
