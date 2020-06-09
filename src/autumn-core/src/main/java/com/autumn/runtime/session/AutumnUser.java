package com.autumn.runtime.session;

import com.autumn.runtime.session.claims.IdentityClaims;

import java.io.Serializable;
import java.util.Set;

/**
 * 用户详情抽象
 *
 * @author 老码农
 * <p>
 * 2018-04-09 14:05:49
 */
public interface AutumnUser extends Serializable {

    /**
     * 获取用户id
     *
     * @return
     */
    Long getId();

    /**
     * 获取用户名称
     *
     * @return
     */
    String getUserName();

    /**
     * 获取用户状态
     *
     * @return {@link com.autumn.security.constants.UserStatusConstants}
     */
    Integer getStatus();

    /**
     * 获取密码
     *
     * @return
     */
    String getPassword();

    /**
     * 获取身份类型
     *
     * @return
     */
    String getIdentityType();

    /**
     * 获取设备信息
     *
     * @return
     */
    AutumnUserDeviceInfo getDeviceInfo();

    /**
     * 获取身份声明
     *
     * @return
     */
    IdentityClaims getIdentityClaims();

    /**
     * 获取角色集合
     *
     * @return
     */
    Set<String> getRoles();

    /**
     * 获取权限集合
     *
     * @return
     */
    Set<String> getPermissions();
}
