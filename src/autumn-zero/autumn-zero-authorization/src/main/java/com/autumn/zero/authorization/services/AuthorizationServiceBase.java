package com.autumn.zero.authorization.services;

import com.autumn.domain.services.DomainService;
import com.autumn.security.token.CredentialsDeviceInfo;
import com.autumn.zero.authorization.entities.common.*;
import com.autumn.zero.authorization.entities.common.query.UserClaimQuery;
import com.autumn.zero.authorization.entities.common.query.UserPermissionQuery;
import com.autumn.zero.authorization.values.ResourcesModuleTreeValue;

import java.util.List;

/**
 * 授权服务基础
 *
 * @param <TUser> 用户类型
 * @param <TRole> 角色类型
 * @author 老码农 2018-11-30 10:53:09
 */
public interface AuthorizationServiceBase<TUser extends AbstractUser, TRole extends AbstractRole>
        extends DomainService {

    /**
     * 添加用户
     *
     * @param user             用户
     * @param isAddDefaultRole 是否添加默认角色
     */
    void addUser(TUser user, boolean isAddDefaultRole);

    /**
     * 更新密码
     *
     * @param userId      用户id
     * @param oldPassword 旧密码
     * @param newPassword 新密码
     */
    void updatePassword(long userId, String oldPassword, String newPassword);

    /**
     * 重置密码
     *
     * @param userId      用户id
     * @param newPassword 新密码
     */
    void resetPassword(long userId, String newPassword);

    /**
     * 更新手机号
     *
     * @param userId        用户id
     * @param phoneNumber   手机号
     * @param isForceUpdate 是否强制更新，强制更新表示，如果该手机号原属于其他用户，则会将原用户置空。
     */
    void updatePhoneNumber(long userId, String phoneNumber, boolean isForceUpdate);

    /**
     * 更新邮件地址
     *
     * @param userId        用户id
     * @param emailAddress  手机号
     * @param isForceUpdate 是否强制更新，强制更新表示，如果该邮件地址原属于其他用户，则会将原用户置空。
     */
    void updateEmailAddress(long userId, String emailAddress, boolean isForceUpdate);

    /**
     * 更新用户
     *
     * @param user 用户
     */
    void updateUser(TUser user);

    /**
     * 根据用户id删除用户
     *
     * @param userId 用户id
     */
    void deleteUserById(long userId);

    /**
     * 根据用户
     *
     * @param userId       用户Id
     * @param isQueryChild 查询子级
     * @return
     */
    TUser getUser(long userId, boolean isQueryChild);

    /**
     * 添加角色
     *
     * @param role 角色
     */
    void addRole(TRole role);

    /**
     * 更新角色
     *
     * @param role 角色
     */
    void updateRole(TRole role);

    /**
     * 删除角色
     *
     * @param roleId 角色id
     */
    void deleteRoleById(long roleId);

    /**
     * 获取角色
     *
     * @param roleId       角色id
     * @param isQueryChild 查询子级
     * @return
     */
    TRole getRole(long roleId, boolean isQueryChild);

    /**
     * 授权到用户
     *
     * @param userId        用户id
     * @param resourcesType 资源类型
     * @param permissions   权限集合
     */
    TUser authorizeByUser(long userId, int resourcesType, List<UserPermission> permissions);

    /**
     * 授权到角色
     *
     * @param roleId        角色id
     * @param resourcesType 资源类型
     * @param permissions   权限集合
     */
    TRole authorizeByRole(long roleId, int resourcesType, List<RolePermission> permissions);

    /**
     * 授权到用户所有权限
     *
     * @param userId        用户id
     * @param resourcesType 资源类型
     */
    void authorizeByUserAllPermission(long userId, int resourcesType);

    /**
     * 授权到角色所有权限
     *
     * @param roleId        角色id
     * @param resourcesType 资源类型
     */
    void authorizeByRoleAllPermission(long roleId, int resourcesType);

    /**
     * 查询用户角色集合
     *
     * @param userId 用户id
     * @return
     */
    List<TRole> queryUserByRoles(long userId);

    /**
     * 查询角色的所有用户
     *
     * @param roleId 角色id
     * @return
     */
    List<TUser> queryRoleByUsers(long roleId);

    /**
     * 查询用户权限
     *
     * @param userId 用户id
     * @return
     */
    List<UserPermission> queryUserPermissions(long userId);

    /**
     * 查询用户所有权限
     *
     * @param userId 用户id
     * @return 返回用户所有权限，不重复，优先用户，再含所属角色
     */
    List<UserPermissionQuery> queryUserAllPermissions(long userId);

    /**
     * 查询用户的菜单模块
     *
     * @param resourcesType 资源类型
     * @param userId        用户id
     * @return
     */
    List<ResourcesModuleTreeValue> queryUserByMenuTree(int resourcesType, long userId);

    /**
     * 查询角色权限
     *
     * @param roleId 角色id
     * @return
     */
    List<RolePermission> queryRolePermissions(long roleId);

    /**
     * 查询用户声明
     *
     * @param userId 用户id
     * @return
     */
    List<UserClaim> queryUserClaims(long userId);

    /**
     * 查询用户所有声明
     *
     * @param userId 用户id
     * @return 返回用户所有声明，不重复，优先用户，再含所属角色
     */
    List<UserClaimQuery> queryUserAllClaims(long userId);

    /**
     * 查询角色声明
     *
     * @param roleId 角色id
     * @return
     */
    List<RoleClaim> queryRoleClaims(long roleId);

    /**
     * 添加或更新用户声明
     *
     * @param claim 声明
     */
    void addOrUpdateUserClaim(UserClaim claim);

    /**
     * 删除用户声明
     *
     * @param userId    用户id
     * @param claimType 声明类型
     */
    void deleteUserClaim(long userId, String claimType);

    /**
     * 添加或更新角色声明
     *
     * @param claim 声明
     */
    void addOrUpdateRoleClaim(RoleClaim claim);

    /**
     * 删除角色声明
     *
     * @param roleId    角色id
     * @param claimType 声明类型
     */
    void deleteRoleClaim(long roleId, String claimType);

    /**
     * 添加或更新用户第三方登录
     *
     * @param externalAuthLogin 第三方提供程序
     */
    void addOrUpdateUserExternalProvider(UserExternalAuthLogin externalAuthLogin);

    /**
     * 删除用户第三方登录
     *
     * @param userId   用户id
     * @param provider 提供程序
     */
    void deleteExternalUserProvider(long userId, String provider);

    /**
     * 根据账户查找用户
     *
     * @param account 账户(用户名称、手机号、邮箱)
     * @param isChild 是否查询子级
     * @return 返回用户信息
     */
    TUser findUserByAccount(String account, boolean isChild);

    /**
     * 根据用户名称查找用户
     *
     * @param userName 用户名称
     * @param isChild  是否查询子级
     * @return 返回用户信息
     */
    TUser findUserByUserName(String userName, boolean isChild);

    /**
     * 根据手机号查找用户
     *
     * @param phoneNumber 手机号
     * @param isChild     是否查询子级
     * @return 返回用户信息
     */
    TUser findUserByPhoneNumber(String phoneNumber, boolean isChild);

    /**
     * 根据邮箱地址查找用户
     *
     * @param emailAddress 邮箱地址
     * @param isChild      是否查询子级
     * @return 返回用户信息
     */
    TUser findUserByEmailAddress(String emailAddress, boolean isChild);

    /**
     * 根据账户判断用户是否存在
     *
     * @param account 账户(用户名称、手机号、邮箱)
     * @return 返回用户信息
     */
    boolean existUserByAccount(String account);

    /**
     * 根据用户名称判断用户是否存在
     *
     * @param userName 用户名称
     * @return 返回用户信息
     */
    boolean existUserByUserName(String userName);

    /**
     * 根据手机号判断用户是否存在
     *
     * @param phoneNumber 手机号
     * @return 返回用户信息
     */
    boolean existUserByPhoneNumber(String phoneNumber);

    /**
     * 根据邮箱地址判断用户是否存在
     *
     * @param emailAddress 邮箱地址
     * @return 返回用户信息
     */
    boolean existUserByEmailAddress(String emailAddress);

    /**
     * 根据第三方提供程序查找用户
     *
     * @param provider    提供程序
     * @param providerKey 提供程序key
     * @param isChild     是否查询子级
     * @return 返回用户信息
     */
    TUser findUserByExternalProvider(String provider, String providerKey, boolean isChild);

    /**
     * 根据第三方提供程序判断用户是否存在
     *
     * @param provider    提供程序
     * @param providerKey 提供程序key
     * @return
     */
    boolean existUserByExternalProvider(String provider, String providerKey);

    /**
     * 激活用户
     *
     * @param userId 用户id
     */
    boolean activeUserById(long userId);

    /**
     * 激活手机号
     *
     * @param user 用户信息
     */
    boolean activePhone(TUser user);

    /**
     * 激活手机号
     *
     * @param userId 用户id
     */
    boolean activePhoneById(long userId);

    /**
     * 激活邮件
     *
     * @param user 用户信息
     */
    boolean activeEmail(TUser user);

    /**
     * 激活邮件
     *
     * @param userId 用户id
     */
    boolean activeEmailById(long userId);

    /**
     * 根据用户id锁定用户
     *
     * @param userId 用户id
     * @return
     */
    boolean lockUserById(long userId);

    /**
     * 根据用户id取消锁定用户
     *
     * @param userId 用户id
     * @return
     */
    boolean cancelLockUserById(long userId);

    /**
     * 根据用户id执行用户过期
     *
     * @param userId 用户id
     * @return
     */
    boolean expireUserById(long userId);

    /**
     * 根据用户id取消过期用户
     *
     * @param userId 用户id
     * @return
     */
    boolean cancelExpireUserById(long userId);

    /**
     * 超级管理员定义
     *
     * @return
     */
    UserRoleDefinition<TUser, TRole> createAdministratorDefinition();

    /**
     * 检查或创建用户与角色
     * <p>
     * 如果用户存在，则不创建
     * </p>
     *
     * @param definitionInfo 定义信息
     */
    TUser checkOrCreateUserAndRole(UserRoleDefinition<TUser, TRole> definitionInfo);

    /**
     * 保存检查认证设备信息
     *
     * @param credentialsDeviceInfo 认证设备信息
     */
    void saveCheckCredentialsDeviceInfo(CredentialsDeviceInfo credentialsDeviceInfo);

    /**
     * 添加或更新用户设备
     *
     * @param deviceAuthLogin 授权
     * @return 如果不存在则返回传参对象，如果存在，则原已更新的源对象
     */
    UserDeviceAuthLogin addOrUpdateUserDeviceInfo(UserDeviceAuthLogin deviceAuthLogin);

    /**
     * 根据设备驱动查找用户
     *
     * @param token     设备票据
     * @param isChildti 是否查询子级
     * @return
     */
    UserDeviceAuthLogin findUserDeviceAuth(String token);

    /**
     * 根据设备Token查找用户
     *
     * @param token     设备票据
     * @param isChildti 是否查询子级
     * @return
     */
    TUser findUserByDeviceToken(String token, boolean isChild);

    /**
     * 删除用户票据
     *
     * @param userId 用户id
     * @return
     */
    int deleteUserDeviceTokenByUserId(long userId);

    /**
     * 删除用户票据
     *
     * @param token 设备票据
     * @return
     */
    boolean deleteUserDeviceToken(String token);

}
