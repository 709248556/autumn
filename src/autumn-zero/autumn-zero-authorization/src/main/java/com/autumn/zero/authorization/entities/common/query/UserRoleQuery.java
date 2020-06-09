package com.autumn.zero.authorization.entities.common.query;

import com.autumn.mybatis.mapper.annotation.ViewTable;
import com.autumn.zero.authorization.entities.common.UserRole;
import lombok.ToString;

import javax.persistence.Table;

/**
 * 用户角色查询
 *
 * @author 老码农 2018-12-10 15:42:56
 */
@ToString(callSuper = true)
@Table
@ViewTable
public class UserRoleQuery extends UserRole {

    /**
     *
     */
    private static final long serialVersionUID = 993727036103814273L;

    /**
     * 字段 userName
     */
    public static final String FIELD_USER_NAME = "userName";

    /**
     * 字段 roleName
     */
    public static final String FIELD_ROLE_NAME = "roleName";

    /**
     * 用户名称
     */
    private String userName;
    /**
     * 角色名称
     */
    private String roleName;

    /**
     * 获取用户名称
     *
     * @return
     */
    public String getUserName() {
        return userName;
    }

    /**
     * 设置用户名称
     *
     * @param userName 用户名称
     */
    public void setUserName(String userName) {
        this.userName = userName;
    }

    /**
     * 获取角色名称
     *
     * @return
     */
    public String getRoleName() {
        return roleName;
    }

    /**
     * 设置角色名称
     *
     * @param roleName 角色名称
     */
    public void setRoleName(String roleName) {
        this.roleName = roleName;
    }

}
