package com.autumn.zero.authorization.entities.defaulted.query;

import com.autumn.mybatis.mapper.annotation.ViewTable;
import com.autumn.zero.authorization.entities.defaulted.DefaultRole;
import lombok.ToString;

import javax.persistence.Table;

/**
 * 角色用户查询
 *
 * @author 老码农 2018-12-07 14:51:34
 */
@ToString(callSuper = true)
@Table
@ViewTable
public class DefaultRoleByUserQuery extends DefaultRole {

    /**
     *
     */
    private static final long serialVersionUID = 7645861615713164082L;

    /**
     * 字段 userId
     */
    public static final String FIELD_USER_ID = "userId";

    /**
     * 用户id
     */
    private Long userId;

    /**
     * 获取用户id
     *
     * @return
     */
    public Long getUserId() {
        return userId;
    }

    /**
     * 设置用户id
     *
     * @param userId 用户id
     */
    public void setUserId(Long userId) {
        this.userId = userId;
    }
}
