package com.autumn.runtime.session;

import com.autumn.runtime.session.claims.IdentityClaims;

import java.io.Serializable;
import java.util.Collection;
import java.util.Date;
import java.util.Set;

/**
 * 会话
 *
 * @author 老码农
 * <p>
 * 2017-11-03 10:26:08
 */
public interface AutumnSession {

    /**
     * 获取会话id
     *
     * @return
     */
    Serializable getId();

    /**
     * 获取会话开始时间
     *
     * @return
     */
    Date getStartTime();

    /**
     * 获取最后访问时间
     *
     * @return
     */
    Date getLastAccessTime();

    /**
     * 获取会话的主机名或ip
     *
     * @return
     */
    String getHost();

    /**
     * 获取属性集合
     *
     * @return
     */
    Collection<Object> getAttributeKeys();

    /**
     * 获取属性
     *
     * @param key 键
     * @return
     */
    Object getAttribute(Object key);

    /**
     * 设置属性
     *
     * @param key   键
     * @param value 值
     */
    void setAttribute(Object key, Object value);

    /**
     * 移除属性
     *
     * @param key
     * @return
     */
    Object removeAttribute(Object key);

    /**
     * 获取用户Id(如 果是客户端，则为客户端id)
     *
     * @return
     */
    Long getUserId();

    /**
     * 是否已身份认证
     *
     * @return
     */
    boolean isAuthenticated();

    /**
     * 获取用户名称
     *
     * @return
     */
    String getUserName();

    /**
     * 获取身份类型
     *
     * @return
     */
    String getIdentityType();

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

    /**
     * 存在角色
     *
     * @param role 角色
     * @return
     */
    boolean hasRole(String role);

    /**
     * 存在所有角色
     *
     * @param roles 角色组
     * @return
     */
    boolean hasAllRoles(String... roles);

    /**
     * 存在所有角色
     *
     * @param roles 角色集合
     * @return
     */
    boolean hasAllRoles(Collection<String> roles);

    /**
     * 存在至少一个角色
     *
     * @param roles 角色组
     * @return
     */
    boolean hasOrAllRoles(String... roles);

    /**
     * 存在至少一个角色
     *
     * @param roles 角色集合
     * @return
     */
    boolean hasOrAllRoles(Collection<String> roles);

    /**
     * 是否允许访问权限
     *
     * @param permission 权限
     * @return
     */
    boolean isPermitted(String permission);

    /**
     * 是否存在所有访问权限
     *
     * @param permissions 检查的权限组
     * @return
     */
    boolean isPermittedAll(String... permissions);

    /**
     * 是否存在所有访问权限
     *
     * @param permissions 检查的权限集合
     * @return
     */
    boolean isPermittedAll(Collection<String> permissions);

    /**
     * 是否存在至少一项访问权限
     *
     * @param permissions 检查的权限组
     * @return
     */
    boolean isOrPermittedAll(String... permissions);

    /**
     * 是否存在至少一项访问权限
     *
     * @param permissions 检查的权限集合
     * @return
     */
    boolean isOrPermittedAll(Collection<String> permissions);

    /**
     * 注销
     */
    void logout();

    /**
     * 更新用户信息
     */
    void updateUserInfo();

    /**
     * 获取已认证的用户信息
     *
     * @return
     */
    AutumnUser getUserInfo();
}
