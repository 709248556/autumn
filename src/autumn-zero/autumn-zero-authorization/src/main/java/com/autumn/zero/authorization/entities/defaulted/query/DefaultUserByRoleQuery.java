package com.autumn.zero.authorization.entities.defaulted.query;

import com.autumn.mybatis.mapper.annotation.ViewTable;
import com.autumn.zero.authorization.entities.defaulted.DefaultUser;
import lombok.ToString;

import javax.persistence.Table;

/**
 * 用户角色查询
 *
 * @author 老码农 2018-12-07 15:03:39
 */
@ToString(callSuper = true)
@Table
@ViewTable
public class DefaultUserByRoleQuery extends DefaultUser {
    /**
     *
     */
    private static final long serialVersionUID = 3109977005847586527L;

    /**
     * 字段 roleId
     */
    public static final String FIELD_ROLE_ID = "roleId";

    /**
     * 角色Id
     */
    private Long roleId;

    /**
     * 获取角色Id
     *
     * @return
     */
    public Long getRoleId() {
        return roleId;
    }

    /**
     * 设置角色Id
     *
     * @param roleId 角色Id
     */
    public void setRoleId(Long roleId) {
        this.roleId = roleId;
    }
}
