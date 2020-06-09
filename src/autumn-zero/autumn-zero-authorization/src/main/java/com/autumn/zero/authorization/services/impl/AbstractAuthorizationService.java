package com.autumn.zero.authorization.services.impl;

import com.autumn.domain.entities.auditing.SoftDelete;
import com.autumn.domain.repositories.DefaultEntityRepository;
import com.autumn.domain.services.AbstractDomainService;
import com.autumn.exception.ExceptionUtils;
import com.autumn.mybatis.provider.DbProvider;
import com.autumn.mybatis.provider.postgresql.PostgreSqlProvider;
import com.autumn.mybatis.wrapper.EntityQueryWrapper;
import com.autumn.mybatis.wrapper.EntityUpdateWrapper;
import com.autumn.mybatis.wrapper.LockModeEnum;
import com.autumn.mybatis.wrapper.QueryWrapper;
import com.autumn.runtime.session.AutumnUser;
import com.autumn.runtime.session.DefaultAutumnUser;
import com.autumn.security.constants.UserStatusConstants;
import com.autumn.security.crypto.AutumnPasswordEncode;
import com.autumn.security.token.CredentialsDeviceInfo;
import com.autumn.util.StringUtils;
import com.autumn.validation.DataValidation;
import com.autumn.validation.MatchesUtils;
import com.autumn.zero.authorization.entities.common.*;
import com.autumn.zero.authorization.entities.common.modules.ResourcesModule;
import com.autumn.zero.authorization.entities.common.modules.ResourcesModulePermission;
import com.autumn.zero.authorization.entities.common.query.UserClaimQuery;
import com.autumn.zero.authorization.entities.common.query.UserPermissionQuery;
import com.autumn.zero.authorization.entities.defaulted.DefaultRole;
import com.autumn.zero.authorization.repositories.common.*;
import com.autumn.zero.authorization.repositories.common.modules.ResourcesModulePermissionRepository;
import com.autumn.zero.authorization.repositories.common.modules.ResourcesModuleRepository;
import com.autumn.zero.authorization.repositories.common.query.UserClaimQueryRepository;
import com.autumn.zero.authorization.repositories.common.query.UserPermissionQueryRepository;
import com.autumn.zero.authorization.services.AuthorizationServiceBase;
import com.autumn.zero.authorization.services.ResourcesService;
import com.autumn.zero.authorization.services.UserRoleDefinition;
import com.autumn.zero.authorization.values.ResourcesModuleTreeValue;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.*;
import java.util.function.Supplier;

/**
 * 授权抽象服务
 *
 * @param <TUser>           用户类型
 * @param <TUserRepository> 用户仓储
 * @param <TRole>           角色类型
 * @param <TRoleRepository> 角色仓储
 * @author 老码农 2018-11-30 11:09:13
 */
public abstract class AbstractAuthorizationService<TUser extends AbstractUser, TUserRepository extends DefaultEntityRepository<TUser>, TRole extends AbstractRole, TRoleRepository extends DefaultEntityRepository<TRole>>
        extends AbstractDomainService implements AuthorizationServiceBase<TUser, TRole> {

    /**
     * 获取用户仓储
     *
     * @return
     */
    protected abstract TUserRepository getUserRepository();

    /**
     * 获取角色仓储
     *
     * @return
     */
    protected abstract TRoleRepository getRoleRepository();

    /**
     * 用户角色仓储
     */
    @Autowired
    protected UserRoleRepository userRoleRepository;

    /**
     * 用户权限仓储
     */
    @Autowired
    protected UserPermissionRepository userPermissionRepository;

    /**
     * 用户声明仓储
     */
    @Autowired
    protected UserClaimRepository userClaimRepository;

    /**
     * 第三方登录仓储
     */
    @Autowired
    protected UserExternalAuthLoginRepository userExternalAuthLoginRepository;

    /**
     * 用户设备授权登录仓储
     */
    @Autowired
    protected UserDeviceAuthLoginRepository userDeviceAuthLoginRepository;

    /**
     * 角色声明仓储
     */
    @Autowired
    protected RoleClaimRepository roleClaimRepository;

    /**
     * 角色权限仓储
     */
    @Autowired
    protected RolePermissionRepository rolePermissionRepository;

    /**
     * 密码提供者
     */
    @Autowired
    protected AutumnPasswordEncode passwordProvider;

    /**
     * 用户权限查询
     */
    @Autowired
    protected UserPermissionQueryRepository userPermissionQueryRepository;

    /**
     * 用户声明查询
     */
    @Autowired
    protected UserClaimQueryRepository userClaimQueryRepository;

    /**
     * 资源模块仓储
     */
    @Autowired
    protected ResourcesModuleRepository resourcesModuleRepository;

    /**
     * 资源模块权限仓储
     */
    @Autowired
    protected ResourcesModulePermissionRepository resourcesModulePermissionRepository;

    /**
     * 资源服务
     */
    @Autowired
    protected ResourcesService resourcesService;

    /**
     * 用户实体类型
     */
    private final Class<TUser> userEntityClass;

    /**
     * 角色实体类型
     */
    private final Class<TRole> roleEntityClass;

    /**
     * 实例化 AbstractUserService 类
     *
     * @param userEntityClass 用户实体类型
     * @param roleEntityClass 角角实体类型
     */
    public AbstractAuthorizationService(Class<TUser> userEntityClass, Class<TRole> roleEntityClass) {
        this.userEntityClass = ExceptionUtils.checkNotNull(userEntityClass, "userEntityClass");
        this.roleEntityClass = ExceptionUtils.checkNotNull(roleEntityClass, "roleEntityClass");
    }

    /**
     * 获取用户实体类型
     *
     * @return
     */
    public final Class<TUser> getUserEntityClass() {
        return this.userEntityClass;
    }

    /**
     * 获取角色实体类型
     *
     * @return
     */
    public final Class<TRole> getRoleEntityClass() {
        return this.roleEntityClass;
    }

    /**
     * 检查用户
     *
     * @param user 用户
     */
    protected void checkUserEdit(TUser user, boolean checkUserName, boolean checkPhoneNumber,
                                 boolean checkEmailAddress) {
        if (user.getRoles() == null) {
            user.setRoles(new ArrayList<>());
        }
        EntityQueryWrapper<TUser> userQuery = new EntityQueryWrapper<>(this.getUserEntityClass());
        if (checkUserName) {
            userQuery.reset();
            userQuery.where().eq(AbstractUser.FIELD_USER_NAME, user.getUserName());
            if (userQuery.countByWhere(this.getUserRepository()) > 0) {
                ExceptionUtils.throwValidationException("用户名称 " + user.getUserName() + " 已存在。");
            }
        }
        if (checkPhoneNumber && !StringUtils.isNullOrBlank(user.getPhoneNumber())) {
            userQuery.reset();
            userQuery.where().eq(AbstractUser.FIELD_PHONE_NUMBER, user.getPhoneNumber().trim());
            if (userQuery.countByWhere(this.getUserRepository()) > 0) {
                ExceptionUtils.throwValidationException("手机号 " + user.getPhoneNumber() + " 已存在或已注册过。");
            }
        }
        if (checkEmailAddress && !StringUtils.isNullOrBlank(user.getEmailAddress())) {
            userQuery.reset();
            userQuery.where().eq(AbstractUser.FIELD_EMAIL_ADDRESS, user.getEmailAddress().trim());
            if (userQuery.countByWhere(this.getUserRepository()) > 0) {
                ExceptionUtils.throwValidationException("邮箱 " + user.getEmailAddress() + " 已存在或已注册过。");
            }
        }
        TRoleRepository roleRepository = this.getRoleRepository();
        int row = 1;
        for (UserRole role : user.getRoles()) {
            QueryWrapper query = new QueryWrapper(this.getRoleEntityClass());
            query.where().eq(AbstractRole.FIELD_ID, role.getRoleId());
            if (roleRepository.countByWhere(query) == 0) {
                ExceptionUtils.throwValidationException("第" + row + "行的角色不存在。");
            }
            row++;
        }
    }

    /**
     * 是否是软删除用户实体
     *
     * @return
     */
    protected final boolean isSoftDeleteUserEntity() {
        return SoftDelete.class.isAssignableFrom(this.getUserEntityClass());
    }

    /**
     * 删除用户详情
     *
     * @param userId 用户id
     */
    protected int deleteUserDetails(long userId) {
        int count = 0;
        QueryWrapper query = new QueryWrapper(UserRole.class);
        query.where().eq(UserRole.FIELD_USER_ID, userId);
        count += userRoleRepository.deleteByWhere(query);

        query = new QueryWrapper(UserClaim.class);
        query.where().eq(UserClaim.FIELD_USER_ID, userId);
        count += userClaimRepository.deleteByWhere(query);

        query = new QueryWrapper(UserExternalAuthLogin.class);
        query.where().eq(UserExternalAuthLogin.FIELD_USER_ID, userId);
        count += userExternalAuthLoginRepository.deleteByWhere(query);

        /*
         * query = new Query(UserLoginLog.class); query.where().eq(UserLoginLog.FIELD_USER_ID,
         * userId); count +=
         * userLoginLogRepository.deleteByWhere(query.builderSection());
         *
         * query = new Query(UserLoginAttempt.class);
         * query.where().eq(UserLoginAttempt.FIELD_USER_ID, userId); count +=
         * userLoginAttemptRepository.deleteByWhere(query.builderSection());
         */

        count += this.deleteUserPermission(userId, null);

        return count;
    }

    /**
     * 删除用户的资源授权
     *
     * @param userId        用户id
     * @param resourcesType 资源类型
     * @return
     */
    protected int deleteByUserAndResourcesType(long userId, Integer resourcesType) {
        DbProvider dbProvider = this.rolePermissionRepository.getDbProvider();
        if (dbProvider instanceof PostgreSqlProvider) {
            return this.userPermissionRepository.deleteByUserAndResourcesTypeToPostgre(userId, resourcesType);
        }
        return this.userPermissionRepository.deleteByUserAndResourcesType(userId, resourcesType);
    }

    /**
     * 删除用户权取
     *
     * @param userId        用户id
     * @param resourcesType 资源类型
     * @return
     */
    private int deleteUserPermission(long userId, Integer resourcesType) {
        int count = 0;
        if (resourcesType == null) {
            EntityQueryWrapper<UserPermission> query = new EntityQueryWrapper<>(UserPermission.class);
            query.where().eq(UserPermission.FIELD_USER_ID, userId);
            count += this.userPermissionRepository.deleteByWhere(query);
        } else {
            count += this.deleteByUserAndResourcesType(userId, resourcesType);
        }
        return count;
    }

    /**
     * 查找默认角色集合
     *
     * @return
     */
    protected List<TRole> findDefaultRoles() {
        QueryWrapper query = new QueryWrapper(this.getRoleEntityClass());
        query.where().eq(AbstractRole.FIELD_IS_DEFAULT, "true");
        return this.getRoleRepository().selectForList(query);
    }

    /**
     * 添加用户的角色
     *
     * @param user
     * @param roles
     */
    protected void addUserRoles(TUser user, List<TRole> roles) {
        if (roles != null) {
            for (TRole role : roles) {
                UserRole userRole = new UserRole();
                userRole.setUserId(user.getId());
                userRole.setRoleId(role.getId());
                this.userRoleRepository.insert(userRole);
            }
        }
        for (UserRole role : user.getRoles()) {
            role.setUserId(user.getId());
            if (roles != null) {
                boolean existRole = roles.stream().anyMatch(r -> r.getId().equals(role.getRoleId()));
                if (!existRole) {
                    this.userRoleRepository.insert(role);
                }
            } else {
                this.userRoleRepository.insert(role);
            }
        }
    }

    @Override
    public void addUser(TUser user, boolean isAddDefaultRole) {
        ExceptionUtils.checkNotNull(user, "user");
        user.valid();
        this.checkUserEdit(user, true, true, true);
        TUserRepository userRepository = this.getUserRepository();
        user.setStatus(UserStatusConstants.NORMAL);
        user.setGmtCreate(new Date());
        user.setGmtModified(null);
        user.setIsActivateEmail(false);
        user.setIsActivatePhone(false);
        user.forNullToDefault();
        String password = user.getPassword();
        user.setPassword("");
        userRepository.insert(user);
        List<TRole> roles = new ArrayList<>();
        if (isAddDefaultRole) {
            roles = this.findDefaultRoles();
        }
        this.addUserRoles(user, roles);
        this.updatePassword(user, password);
    }

    /**
     * 获取检查用户
     *
     * @param userId
     * @param msg
     * @return
     */
    private TUser getCheckUser(Long userId, String msg) {
        if (userId == null) {
            ExceptionUtils.throwValidationException("用户id不能为 null。");
        }
        TUser user = this.getUserRepository().getByLock(userId, LockModeEnum.UPDATE);
        if (user == null) {
            ExceptionUtils.throwValidationException("指定的用户不存在," + msg + "。");
        }
        return user;
    }

    /**
     * 更新密码
     *
     * @param userId      用户id
     * @param oldPassword 旧密码
     * @param newPassword 新密码
     */
    @Override
    public void updatePassword(long userId, String oldPassword, String newPassword) {
        ExceptionUtils.checkNotNullOrBlank(oldPassword, "oldPassword");
        ExceptionUtils.checkNotNullOrBlank(newPassword, "newPassword");
        TUser user = this.getCheckUser(userId, "无法更新密码");
        AutumnUser autumnUser = this.createAutumnUser(user);
        if (!passwordProvider.matches(autumnUser, oldPassword)) {
            ExceptionUtils.throwValidationException("旧密码不正确。");
        }
        this.updatePassword(user, newPassword);
    }

    @Override
    public void resetPassword(long userId, String newPassword) {
        ExceptionUtils.checkNotNullOrBlank(newPassword, "newPassword");
        TUser user = this.getCheckUser(userId, "无法重置密码");
        this.updatePassword(user, newPassword);
    }

    @Override
    public void updatePhoneNumber(long userId, String phoneNumber, boolean isForceUpdate) {
        TUser user = this.getCheckUser(userId, "无法更新手机号");
        if (StringUtils.isNullOrBlank(phoneNumber)) {
            phoneNumber = "";
        } else {
            phoneNumber = phoneNumber.trim();
            if (phoneNumber.equalsIgnoreCase(user.getPhoneNumber())) {
                return;
            }
            if (!MatchesUtils.isMobilePhone(phoneNumber)) {
                ExceptionUtils.throwValidationException("手机号[" + phoneNumber + "]格式不正确。");
            }
            if (this.existUserByPhoneNumber(phoneNumber)) {
                if (!isForceUpdate) {
                    ExceptionUtils.throwValidationException("手机号[" + phoneNumber + "]已属于其他用户。");
                }
                EntityUpdateWrapper<TUser> specify = new EntityUpdateWrapper<>(this.getUserEntityClass());
                specify.set(AbstractUser.FIELD_PHONE_NUMBER, "").where().eq(AbstractUser.FIELD_PHONE_NUMBER, phoneNumber);
                this.getUserRepository().updateByWhere(specify);
            }
        }
        this.updateByUserSpecify(userId, AbstractUser.FIELD_PHONE_NUMBER, phoneNumber);
    }

    @Override
    public void updateEmailAddress(long userId, String emailAddress, boolean isForceUpdate) {
        TUser user = this.getCheckUser(userId, "无法邮件地址");
        if (StringUtils.isNullOrBlank(emailAddress)) {
            emailAddress = "";
            this.updateByUserSpecify(userId, AbstractUser.FIELD_EMAIL_ADDRESS, emailAddress);
        } else {
            emailAddress = emailAddress.trim();
            if (emailAddress.equalsIgnoreCase(user.getEmailAddress())) {
                return;
            }
            if (!MatchesUtils.isEmail(emailAddress)) {
                ExceptionUtils.throwValidationException("邮件地址[" + emailAddress + "]格式不正确。");
            }
            if (this.existUserByPhoneNumber(emailAddress)) {
                if (!isForceUpdate) {
                    ExceptionUtils.throwValidationException("邮件地址[" + emailAddress + "]已属于其他用户。");
                }
                EntityUpdateWrapper<TUser> specify = new EntityUpdateWrapper<>(this.getUserEntityClass());
                specify.set(AbstractUser.FIELD_EMAIL_ADDRESS, "").where().eq(AbstractUser.FIELD_EMAIL_ADDRESS, emailAddress);
                this.getUserRepository().updateByWhere(specify);
            }
            this.updateByUserSpecify(userId, AbstractUser.FIELD_EMAIL_ADDRESS, emailAddress);
        }
    }

    /**
     * 创建用户
     *
     * @param user 用户
     * @return
     */
    private AutumnUser createAutumnUser(TUser user) {
        DefaultAutumnUser autumnUser = new DefaultAutumnUser();
        autumnUser.setId(user.getId());
        autumnUser.setUserName(user.getUserName());
        autumnUser.setPassword(user.getPassword());
        autumnUser.setStatus(user.getStatus());
        return autumnUser;
    }

    /**
     * 更新密码
     *
     * @param user        用户
     * @param newPassword 新密码
     */

    private void updatePassword(TUser user, String newPassword) {
        AutumnUser autumnUser = this.createAutumnUser(user);
        String password = passwordProvider.encode(autumnUser, newPassword);
        if (password == null) {
            password = "";
        }
        this.updateByUserSpecify(user.getId(), AbstractUser.FIELD_PASSWORD, password);
    }

    /**
     * 更新用户部份
     *
     * @param userId    用户id
     * @param filedName 字段名称
     * @param value     值
     */
    private int updateByUserSpecify(long userId, String filedName, Object value) {
        EntityUpdateWrapper<TUser> wrapper = new EntityUpdateWrapper<>(this.getUserEntityClass());
        wrapper.set(filedName, value)
                .where()
                .eq(AbstractUser.FIELD_ID, userId);
        return this.getUserRepository().updateByWhere(wrapper);
    }

    @Override
    public void updateUser(TUser user) {
        ExceptionUtils.checkNotNull(user, "user");
        // user.setStatus(UserStatusConstants.NORMAL);
        user.valid();
        user.forNullToDefault();
        TUserRepository userRepository = this.getUserRepository();
        TUser entity = userRepository.getByLock(user.getId(), LockModeEnum.UPDATE);
        if (entity == null) {
            ExceptionUtils.throwValidationException("无法更新不存在的用户。");
        }
        if (entity.getIsSysUser()) {
            ExceptionUtils.throwValidationException("无法更新系统用户。");
        }
        boolean checkUserName, checkPhoneNumber, checkEmailAddress;
        checkUserName = !entity.getUserName().equalsIgnoreCase(user.getUserName().trim());
        if (StringUtils.isNullOrBlank(user.getPhoneNumber())) {
            checkPhoneNumber = false;
        } else {
            checkPhoneNumber = !user.getPhoneNumber().trim().equalsIgnoreCase(entity.getPhoneNumber());
        }
        if (StringUtils.isNullOrBlank(user.getEmailAddress())) {
            checkEmailAddress = false;
        } else {
            checkEmailAddress = !user.getEmailAddress().trim().equalsIgnoreCase(entity.getEmailAddress());
        }
        this.checkUserEdit(user, checkUserName, checkPhoneNumber, checkEmailAddress);
        user.setGmtCreate(entity.getGmtCreate());
        user.setPassword(entity.getPassword());
        user.setIsActivateEmail(entity.getIsActivateEmail());
        user.setIsActivatePhone(entity.getIsActivatePhone());
        user.setIsSysUser(entity.getIsSysUser());
        user.setStatus(entity.getStatus());
        QueryWrapper query = new QueryWrapper(UserRole.class);
        query.where().eq(UserRole.FIELD_USER_ID, entity.getId());
        userRoleRepository.deleteByWhere(query);
        userRepository.update(user);
        this.addUserRoles(user, null);
    }

    /**
     * 删除用户
     *
     * @param userId
     */
    @Override
    public void deleteUserById(long userId) {
        TUserRepository userRepository = this.getUserRepository();
        TUser entity = userRepository.get(userId);
        if (entity == null) {
            return;
        }
        if (entity.getIsSysUser()) {
            ExceptionUtils.throwValidationException("不能删除系统用户");
        }
        if (!this.isSoftDeleteUserEntity()) {
            this.deleteUserDetails(userId);
        }
        this.getUserRepository().deleteById(userId);
    }

    /**
     * 查询用户角色集合
     *
     * @param userId
     * @return
     */
    private List<UserRole> queryUserRoles(long userId) {
        QueryWrapper query = new QueryWrapper(UserRole.class);
        query.where().eq(UserRole.FIELD_USER_ID, userId).of().orderBy(UserRole.FIELD_ID);
        return this.userRoleRepository.selectForList(query);
    }

    @Override
    public TUser getUser(long userId, boolean isQuerychild) {
        TUser entity = this.getUserRepository().get(userId);
        if (entity != null && isQuerychild) {
            entity.setRoles(this.queryUserRoles(entity.getId()));
        }
        return entity;
    }

    /**
     * 检查角色
     *
     * @param role
     */
    protected void checkRoleEdit(TRole role, boolean checkName) {
        if (role.getUsers() == null) {
            role.setUsers(new ArrayList<>());
        }
        EntityQueryWrapper<TRole> userQuery = new EntityQueryWrapper<>(this.getRoleEntityClass());
        if (checkName) {
            userQuery.reset();
            userQuery.where().eq(AbstractRole.FIELD_NAME, role.getName());
            if (userQuery.countByWhere(this.getRoleRepository()) > 0) {
                ExceptionUtils.throwValidationException("角色名称 " + role.getName() + " 已存在。");
            }
        }
        TUserRepository userRepository = this.getUserRepository();
        int row = 1;
        for (UserRole user : role.getUsers()) {
            QueryWrapper query = new QueryWrapper(this.getUserEntityClass());
            query.where().eq(AbstractUser.FIELD_ID, user.getUserId());
            if (userRepository.countByWhere(query) == 0) {
                ExceptionUtils.throwValidationException("第" + row + "行的用户不存在。");
            }
            row++;
        }
    }

    /**
     * 是否是软删除角色实体
     *
     * @return
     */
    protected final boolean isSoftDeleteRoleEntity() {
        return SoftDelete.class.isAssignableFrom(this.getRoleEntityClass());
    }

    /**
     * 添加角色的用户
     *
     * @param role
     */
    protected void addRoleUsers(TRole role) {
        for (UserRole user : role.getUsers()) {
            user.setRoleId(role.getId());
            this.userRoleRepository.insert(user);
        }
    }

    @Override
    public void addRole(TRole role) {
        ExceptionUtils.checkNotNull(role, "role");
        role.valid();
        role.forNullToDefault();
        this.checkRoleEdit(role, true);
        TRoleRepository roleRepository = this.getRoleRepository();
        role.setGmtCreate(new Date());
        role.setGmtModified(null);
        roleRepository.insert(role);
        this.addRoleUsers(role);
    }

    /**
     * 删除角色详情
     *
     * @param roleId 角色id
     */
    protected int deleteRoleDetails(long roleId) {
        int count = 0;
        QueryWrapper query = new QueryWrapper(UserRole.class);
        query.where().eq(UserRole.FIELD_ROLE_ID, roleId);
        count += this.userRoleRepository.deleteByWhere(query);

        query = new QueryWrapper(RoleClaim.class);
        query.where().eq(RoleClaim.FIELD_ROLE_ID, roleId);
        count += this.roleClaimRepository.deleteByWhere(query);

        count += this.deleteRolePermission(roleId, null);
        return count;
    }

    /**
     * 删除角色授权资源
     *
     * @param roleId        角色id
     * @param resourcesType 资源类型
     * @return
     */
    protected int deleteByRoleAndResourcesType(long roleId, Integer resourcesType) {
        DbProvider dbProvider = this.rolePermissionRepository.getDbProvider();
        if (dbProvider instanceof PostgreSqlProvider) {
            return this.rolePermissionRepository.deleteByRoleAndResourcesTypeToPostgre(roleId, resourcesType);
        }
        return this.rolePermissionRepository.deleteByRoleAndResourcesType(roleId, resourcesType);
    }

    /**
     * 删除角色权限
     *
     * @param roleId        角色id
     * @param resourcesType 资源类型
     * @return
     */
    private int deleteRolePermission(long roleId, Integer resourcesType) {
        int count = 0;
        if (resourcesType == null) {
            EntityQueryWrapper<RolePermission> query = new EntityQueryWrapper<>(RolePermission.class);
            query.where().eq(RolePermission.FIELD_ROLE_ID, roleId);
            count += this.rolePermissionRepository.deleteByWhere(query);
        } else {
            this.deleteByRoleAndResourcesType(roleId, resourcesType);
        }
        return count;
    }

    @Override
    public void updateRole(TRole role) {
        ExceptionUtils.checkNotNull(role, "role");
        role.valid();
        role.forNullToDefault();
        TRoleRepository roleRepository = this.getRoleRepository();
        TRole entity = roleRepository.getByLock(role.getId(), LockModeEnum.UPDATE);
        if (entity == null) {
            ExceptionUtils.throwValidationException("无法更新不存在的角色。");
        }
        if (entity.getIsSysRole()) {
            ExceptionUtils.throwValidationException("无法更新系统角色。");
        }
        this.checkRoleEdit(role, !entity.getName().equalsIgnoreCase(role.getName().trim()));
        role.setGmtCreate(entity.getGmtCreate());
        role.setIsSysRole(entity.getIsSysRole());
        roleRepository.update(role);
        QueryWrapper query = new QueryWrapper(UserRole.class);
        query.where().eq(UserRole.FIELD_ROLE_ID, entity.getId());
        userRoleRepository.deleteByWhere(query);
        this.addRoleUsers(role);
    }

    @Override
    public void deleteRoleById(long roleId) {
        TRoleRepository roleRepository = this.getRoleRepository();
        TRole entity = roleRepository.getByLock(roleId, LockModeEnum.UPDATE);
        if (entity == null) {
            return;
        }
        if (entity.getIsSysRole()) {
            ExceptionUtils.throwValidationException("不能删除系统角色。");
        }
        if (!this.isSoftDeleteRoleEntity()) {
            this.deleteRoleDetails(roleId);
        }
        this.getRoleRepository().deleteById(roleId);
    }

    @Override
    public TRole getRole(long roleId, boolean isQuerychild) {
        TRole entity = this.getRoleRepository().get(roleId);
        if (entity != null && isQuerychild) {
            QueryWrapper query = new QueryWrapper(UserRole.class);
            query.where().eq(UserRole.FIELD_ROLE_ID, entity.getId());
            List<UserRole> roles = this.userRoleRepository.selectForList(query);
            entity.setUsers(roles);
        }
        return entity;
    }

    /**
     * 检查权限
     *
     * @param resourcesType 资源类型
     * @param permissions   权限集合
     */
    private <E extends AbstractPermission> void checkPermission(int resourcesType, List<E> permissions) {
        Set<String> resourcesSet = new HashSet<>();
        Set<String> keySet = new HashSet<>();
        EntityQueryWrapper<ResourcesModulePermission> query = new EntityQueryWrapper<>(ResourcesModulePermission.class);
        for (E permission : permissions) {
            permission.forNullToDefault();
            permission.valid();
            String resKey = permission.getResourcesId().toLowerCase().trim();
            if (resourcesSet.add(resKey)) {
                ResourcesModule module = resourcesModuleRepository.get(resKey);
                if (module == null) {
                    ExceptionUtils.throwValidationException("指定的资源id[" + resKey + "]不存在或无效。");
                }
                if (!module.getResourcesType().equals(resourcesType)) {
                    ExceptionUtils.throwValidationException("指定的资源[" + module.getCustomName() + "]对应的资源类型不匹配。");
                }
            }
            if (StringUtils.isNullOrBlank(permission.getName())) {
                if (!keySet.add(permission.getResourcesId().toLowerCase())) {
                    ExceptionUtils.throwValidationException("指定的资源id[" + resKey + "]重复授权。");
                }
            } else {
                String name = permission.getName().trim();
                String key = String.format("%s:%s", resKey, name.toLowerCase());
                if (keySet.add(key)) {
                    query.reset();
                    query.where().eq(ResourcesModulePermission.FIELD_RESOURCES_ID, resKey)
                            .eq(ResourcesModulePermission.FIELD_NAME, name);
                    if (query.countByWhere(this.resourcesModulePermissionRepository) == 0) {
                        ExceptionUtils.throwValidationException("指定的资源id[" + resKey + "]与对应的权限[" + name + "]未定义。");
                    }

                } else {
                    ExceptionUtils.throwValidationException("指定的资源id[" + resKey + "]与对应的权限[" + name + "]重复授权。");
                }
            }
        }
    }

    @Override
    public TUser authorizeByUser(long userId, int resourcesType, List<UserPermission> permissions) {
        if (permissions == null) {
            permissions = new ArrayList<>();
        }
        TUser entity = this.getCheckUser(userId, "无法授权");
        this.checkPermission(resourcesType, permissions);
        this.deleteUserPermission(userId, resourcesType);
        for (UserPermission permission : permissions) {
            permission.setUserId(userId);
            this.userPermissionRepository.insert(permission);
        }
        return entity;
    }

    @Override
    public TRole authorizeByRole(long roleId, int resourcesType, List<RolePermission> permissions) {
        if (permissions == null) {
            permissions = new ArrayList<>();
        }
        TRole entity = this.getRole(roleId, false);
        if (entity == null) {
            ExceptionUtils.throwValidationException("指定的用户不存在，无法授权。");
        }
        this.checkPermission(resourcesType, permissions);
        this.deleteRolePermission(roleId, resourcesType);
        for (RolePermission permission : permissions) {
            permission.setRoleId(roleId);
            this.rolePermissionRepository.insert(permission);
        }
        return entity;
    }

    /**
     * 授用户或角色所有权限
     *
     * @param resourcesType   资源类型
     * @param repository      仓储
     * @param permissionClass 权限类型
     * @param supplier        返回处理
     */
    private <TPermissionEntity extends AbstractPermission, TPermissionRepository extends DefaultEntityRepository<TPermissionEntity>> void authorizeAllPermission(
            int resourcesType, TPermissionRepository repository, Class<TPermissionEntity> permissionClass,
            Supplier<TPermissionEntity> supplier) {
        EntityQueryWrapper<ResourcesModule> resourcesModuleQuery = new EntityQueryWrapper<>(ResourcesModule.class);
        resourcesModuleQuery.where().eq(ResourcesModule.FIELD_RESOURCES_TYPE, resourcesType)
                .eq(ResourcesModule.FIELD_IS_AUTHORIZE, true).of().orderBy(ResourcesModule.FIELD_PARENT_ID)
                .orderBy(ResourcesModule.FIELD_SORT_ID).orderBy(ResourcesModule.FIELD_ID);
        List<ResourcesModule> resourcesModules = this.resourcesModuleRepository.selectForList(resourcesModuleQuery);
        Map<String, List<ResourcesModulePermission>> resPermissionMap = this.queryByResourcesTypeMap(resourcesType);
        for (ResourcesModule resourcesModule : resourcesModules) {
            TPermissionEntity permission = supplier.get();
            permission.setIsGranted(true);
            permission.setResourcesId(resourcesModule.getId());
            permission.setName("");
            repository.insert(permission);
            List<ResourcesModulePermission> items = resPermissionMap.get(resourcesModule.getId().toLowerCase());
            if (items != null) {
                for (ResourcesModulePermission item : items) {
                    permission = supplier.get();
                    permission.setIsGranted(true);
                    permission.setResourcesId(resourcesModule.getId());
                    permission.setName(item.getName());
                    repository.insert(permission);
                }
            }
        }
    }

    /**
     * 获取资源权限 Map
     *
     * @param resourcesType
     * @return
     */
    private Map<String, List<ResourcesModulePermission>> queryByResourcesTypeMap(int resourcesType) {
        Map<String, List<ResourcesModulePermission>> resPermissionMap = new HashMap<>();
        List<ResourcesModulePermission> resPermissionList = this.resourcesModulePermissionRepository
                .queryByResourcesTypes(resourcesType);
        for (ResourcesModulePermission resourcesModulePermission : resPermissionList) {
            String key = resourcesModulePermission.getResourcesId().toLowerCase();
            List<ResourcesModulePermission> items = resPermissionMap.computeIfAbsent(key, k -> new ArrayList<>());
            items.add(resourcesModulePermission);
        }
        return resPermissionMap;
    }

    @Override
    public void authorizeByUserAllPermission(long userId, int resourcesType) {
        TUser user = this.getCheckUser(userId, "无法授权");
        this.deleteByUserAndResourcesType(userId, resourcesType);
        this.authorizeAllPermission(resourcesType, this.userPermissionRepository, UserPermission.class, () -> {
            UserPermission permission = new UserPermission();
            permission.setUserId(userId);
            return permission;
        });
        this.getUserRepository().update(user);
    }

    @Override
    public void authorizeByRoleAllPermission(long roleId, int resourcesType) {
        TRole role = this.getRole(roleId, false);
        if (role == null) {
            ExceptionUtils.throwValidationException("指定的角色不存在。");
        }
        this.deleteByRoleAndResourcesType(roleId, resourcesType);
        this.authorizeAllPermission(resourcesType, this.rolePermissionRepository, RolePermission.class, () -> {
            RolePermission permission = new RolePermission();
            permission.setRoleId(roleId);
            return permission;
        });
        this.getRoleRepository().update(role);
    }

    @Override
    public List<UserPermission> queryUserPermissions(long userId) {
        EntityQueryWrapper<UserPermission> query = new EntityQueryWrapper<>(UserPermission.class);
        query.where().eq(UserPermission.FIELD_USER_ID, userId).of().orderBy(UserPermission.FIELD_ID);
        return this.userPermissionRepository.selectForList(query);
    }

    /**
     * 查询用户所有权限
     *
     * @param userId 用户id
     * @return
     */
    protected List<UserPermissionQuery> onQueryUserAllPermissions(long userId) {
        EntityQueryWrapper<UserPermissionQuery> wrapper = new EntityQueryWrapper<>(UserPermissionQuery.class);
        wrapper.where()
                .eq(UserPermissionQuery.FIELD_USER_ID, userId)
                .of()
                .orderByDescending(UserPermissionQuery.FIELD_IS_GRANTED);
        return this.userPermissionQueryRepository.selectForList(wrapper);
    }

    @Override
    public List<UserPermissionQuery> queryUserAllPermissions(long userId) {
        List<UserPermissionQuery> items = this.onQueryUserAllPermissions(userId);
        List<UserPermissionQuery> results = new ArrayList<>(items.size());
        items.forEach(item -> {
            boolean exist = results.stream().anyMatch(c ->
                    c.getResourcesId().equalsIgnoreCase(item.getResourcesId())
                            && c.getName().equalsIgnoreCase(item.getName()));
            if (!exist) {
                results.add(item);
            }
        });
        return results;
    }

    /**
     * 查询用户的菜单模块
     *
     * @param resourcesType 资源类型
     * @param userId        用户id
     * @return
     */
    @Override
    public List<ResourcesModuleTreeValue> queryUserByMenuTree(int resourcesType, long userId) {
        List<UserPermissionQuery> permissions = this.queryUserAllPermissions(userId);
        List<ResourcesModuleTreeValue> moduleTree = resourcesService.queryByMenuTree(resourcesType);
        return resourcesService.filterModuleTree(moduleTree, permissions);
    }

    @Override
    public List<RolePermission> queryRolePermissions(long roleId) {
        EntityQueryWrapper<RolePermission> query = new EntityQueryWrapper<>(RolePermission.class);
        query.where().eq(RolePermission.FIELD_ROLE_ID, roleId).of().orderBy(RolePermission.FIELD_ID);
        return this.rolePermissionRepository.selectForList(query);
    }

    @Override
    public List<UserClaim> queryUserClaims(long userId) {
        EntityQueryWrapper<UserClaim> query = new EntityQueryWrapper<>(UserClaim.class);
        query.where().eq(UserClaim.FIELD_USER_ID, userId).of().orderBy(UserClaim.FIELD_ID);
        return this.userClaimRepository.selectForList(query);
    }

    /**
     * 查询用户的所有声明
     *
     * @param userId 用户id
     * @return
     */
    protected List<UserClaimQuery> onQueryUserAllClaims(long userId) {
        EntityQueryWrapper<UserClaimQuery> query = new EntityQueryWrapper<>(UserClaimQuery.class);
        query.where().eq(UserClaimQuery.FIELD_USER_ID, userId).of().orderBy(UserClaimQuery.FIELD_CLAIM_TYPE);
        return this.userClaimQueryRepository.selectForList(query);
    }

    @Override
    public List<UserClaimQuery> queryUserAllClaims(long userId) {
        List<UserClaimQuery> items = this.onQueryUserAllClaims(userId);
        List<UserClaimQuery> results = new ArrayList<>(items.size());
        items.forEach(item -> {
            boolean exist = results.stream().anyMatch(c ->
                    c.getClaimType().equalsIgnoreCase(item.getClaimType())
                            && c.getClaimValue().equalsIgnoreCase(item.getClaimValue()));
            if (!exist) {
                results.add(item);
            }
        });
        return results;
    }

    @Override
    public List<RoleClaim> queryRoleClaims(long roleId) {
        EntityQueryWrapper<RoleClaim> query = new EntityQueryWrapper<>(RoleClaim.class);
        query.where().eq(RoleClaim.FIELD_ROLE_ID, roleId).of().orderBy(RoleClaim.FIELD_ID);
        return this.roleClaimRepository.selectForList(query);
    }

    @Override
    public void addOrUpdateUserClaim(UserClaim claim) {
        ExceptionUtils.checkNotNull(claim, "claim");
        claim.forNullToDefault();
        claim.valid();
        EntityQueryWrapper<TUser> userQuery = new EntityQueryWrapper<>(this.getUserEntityClass());
        userQuery.where().eq(AbstractUser.FIELD_ID, claim.getUserId());
        if (userQuery.countByWhere(this.getUserRepository()) == 0) {
            ExceptionUtils.throwValidationException("无效的用户或用户不存在。");
        }
        EntityQueryWrapper<UserClaim> claimQuery = new EntityQueryWrapper<>(UserClaim.class);
        claimQuery.where().eq(UserClaim.FIELD_USER_ID, claim.getUserId()).eq(UserClaim.FIELD_CLAIM_TYPE, claim.getClaimType());
        UserClaim oldClaim = this.userClaimRepository.selectForFirst(claimQuery);
        if (oldClaim == null) {
            this.userClaimRepository.insert(claim);
        } else {
            claim.setId(oldClaim.getId());

            oldClaim.setClaimValue(claim.getClaimValue());
            this.userClaimRepository.update(oldClaim);
        }
    }

    @Override
    public void deleteUserClaim(long userId, String claimType) {
        ExceptionUtils.checkNotNullOrBlank(claimType, "claimType");
        EntityQueryWrapper<UserClaim> claimQuery = new EntityQueryWrapper<>(UserClaim.class);
        claimQuery.where().eq(UserClaim.FIELD_USER_ID, userId).eq(UserClaim.FIELD_CLAIM_TYPE, claimType);
        this.userClaimRepository.deleteByWhere(claimQuery);
    }

    @Override
    public void addOrUpdateUserExternalProvider(UserExternalAuthLogin externalAuthLogin) {
        ExceptionUtils.checkNotNull(externalAuthLogin, "externalAuthLogin");
        externalAuthLogin.forNullToDefault();
        externalAuthLogin.valid();
        EntityQueryWrapper<TUser> userQuery = new EntityQueryWrapper<>(this.getUserEntityClass());
        userQuery.where().eq(AbstractUser.FIELD_ID, externalAuthLogin.getUserId());
        if (userQuery.countByWhere(this.getUserRepository()) == 0) {
            ExceptionUtils.throwValidationException("无效的用户或用户不存在。");
        }
        EntityQueryWrapper<UserExternalAuthLogin> query = new EntityQueryWrapper<>(UserExternalAuthLogin.class);
        query.where().eq(UserExternalAuthLogin.FIELD_USER_ID, externalAuthLogin.getUserId())
                .eq(UserExternalAuthLogin.FIELD_PROVIDER, externalAuthLogin.getProvider());
        UserExternalAuthLogin old = this.userExternalAuthLoginRepository.selectForFirst(query);
        if (old == null) {
            this.userExternalAuthLoginRepository.insert(externalAuthLogin);
        } else {
            externalAuthLogin.setId(old.getId());
            old.setProviderKey(externalAuthLogin.getProviderKey());
            this.userExternalAuthLoginRepository.update(old);
        }
    }

    @Override
    public void deleteExternalUserProvider(long userId, String provider) {
        ExceptionUtils.checkNotNullOrBlank(provider, "provider");
        provider = provider.trim();
        EntityQueryWrapper<UserExternalAuthLogin> query = new EntityQueryWrapper<>(UserExternalAuthLogin.class);
        query.where().eq(UserExternalAuthLogin.FIELD_USER_ID, userId).eq(UserExternalAuthLogin.FIELD_PROVIDER, provider);
        this.userExternalAuthLoginRepository.deleteByWhere(query);
    }

    @Override
    public void addOrUpdateRoleClaim(RoleClaim claim) {
        ExceptionUtils.checkNotNull(claim, "claim");
        claim.forNullToDefault();
        claim.valid();
        EntityQueryWrapper<TRole> roleQuery = new EntityQueryWrapper<>(this.getRoleEntityClass());
        roleQuery.where().eq(AbstractRole.FIELD_ID, claim.getRoleId());
        if (roleQuery.countByWhere(this.getRoleRepository()) == 0) {
            ExceptionUtils.throwValidationException("无效的角色或角色不存在。");
        }
        EntityQueryWrapper<RoleClaim> claimQuery = new EntityQueryWrapper<>(RoleClaim.class);
        claimQuery.where().eq(RoleClaim.FIELD_ROLE_ID, claim.getRoleId()).eq(UserClaim.FIELD_CLAIM_TYPE, claim.getClaimType());
        RoleClaim oldClaim = this.roleClaimRepository.selectForFirst(claimQuery);
        if (oldClaim == null) {
            this.roleClaimRepository.insert(claim);
        } else {
            claim.setId(oldClaim.getId());
            oldClaim.setClaimValue(claim.getClaimValue());
            this.roleClaimRepository.update(oldClaim);
        }
    }

    @Override
    public void deleteRoleClaim(long roleId, String claimType) {
        ExceptionUtils.checkNotNullOrBlank(claimType, "claimType");
        EntityQueryWrapper<RoleClaim> claimQuery = new EntityQueryWrapper<>(RoleClaim.class);
        claimQuery.where().eq(RoleClaim.FIELD_ROLE_ID, roleId).eq(UserClaim.FIELD_CLAIM_TYPE, claimType);
        this.roleClaimRepository.deleteByWhere(claimQuery);
    }

    /**
     * 查找用户
     *
     * @param filedName
     * @param value
     * @param isChild
     * @return
     */
    private TUser findUser(String filedName, String value, boolean isChild) {
        EntityQueryWrapper<TUser> query = new EntityQueryWrapper<>(this.getUserEntityClass());
        query.where().eq(filedName, value.trim()).of().lockByUpdate();
        TUser user = this.getUserRepository().selectForFirst(query);
        if (isChild && user != null) {
            user.setRoles(this.queryUserRoles(user.getId()));
        }
        return user;
    }

    @Override
    public TUser findUserByAccount(String account, boolean isChild) {
        ExceptionUtils.checkNotNullOrBlank(account, "account");
        account = account.trim();
        if (MatchesUtils.isMobilePhone(account)) {
            return this.findUser(AbstractUser.FIELD_PHONE_NUMBER, account, isChild);
        } else if (MatchesUtils.isEmail(account)) {
            return this.findUser(AbstractUser.FIELD_EMAIL_ADDRESS, account, isChild);
        } else {
            return this.findUser(AbstractUser.FIELD_USER_NAME, account, isChild);
        }
    }

    @Override
    public TUser findUserByUserName(String userName, boolean isChild) {
        ExceptionUtils.checkNotNullOrBlank(userName, "userName");
        return this.findUser(AbstractUser.FIELD_USER_NAME, userName, isChild);
    }

    @Override
    public TUser findUserByPhoneNumber(String phoneNumber, boolean isChild) {
        ExceptionUtils.checkNotNullOrBlank(phoneNumber, "phoneNumber");
        return this.findUser(AbstractUser.FIELD_PHONE_NUMBER, phoneNumber, isChild);
    }

    @Override
    public TUser findUserByEmailAddress(String emailAddress, boolean isChild) {
        ExceptionUtils.checkNotNullOrBlank(emailAddress, "emailAddress");
        return this.findUser(AbstractUser.FIELD_EMAIL_ADDRESS, emailAddress, isChild);
    }

    /**
     * 判断用户是否存在
     *
     * @param filedName
     * @param value
     * @return
     */
    private boolean existUser(String filedName, String value) {
        EntityQueryWrapper<TUser> query = new EntityQueryWrapper<>(this.getUserEntityClass());
        query.where().eq(filedName, value.trim()).of().lockByUpdate();
        return query.exist(this.getUserRepository());
    }

    @Override
    public boolean existUserByAccount(String account) {
        ExceptionUtils.checkNotNullOrBlank(account, "account");
        account = account.trim();
        if (MatchesUtils.isMobilePhone(account)) {
            return this.existUser(AbstractUser.FIELD_PHONE_NUMBER, account);
        } else if (MatchesUtils.isEmail(account)) {
            return this.existUser(AbstractUser.FIELD_EMAIL_ADDRESS, account);
        } else {
            return this.existUser(AbstractUser.FIELD_USER_NAME, account);
        }
    }

    @Override
    public boolean existUserByUserName(String userName) {
        ExceptionUtils.checkNotNullOrBlank(userName, "userName");
        return this.existUser(AbstractUser.FIELD_USER_NAME, userName);
    }

    @Override
    public boolean existUserByPhoneNumber(String phoneNumber) {
        ExceptionUtils.checkNotNullOrBlank(phoneNumber, "phoneNumber");
        return this.existUser(AbstractUser.FIELD_PHONE_NUMBER, phoneNumber);
    }

    @Override
    public boolean existUserByEmailAddress(String emailAddress) {
        ExceptionUtils.checkNotNullOrBlank(emailAddress, "emailAddress");
        return this.existUser(AbstractUser.FIELD_EMAIL_ADDRESS, emailAddress);
    }

    private EntityQueryWrapper<UserExternalAuthLogin> createExternalProviderQuery(String provider, String providerKey) {
        ExceptionUtils.checkNotNullOrBlank(provider, "provider");
        ExceptionUtils.checkNotNullOrBlank(providerKey, "providerKey");
        provider = provider.trim();
        providerKey = providerKey.trim();
        EntityQueryWrapper<UserExternalAuthLogin> query = new EntityQueryWrapper<>(UserExternalAuthLogin.class);
        query.where().eq(UserExternalAuthLogin.FIELD_PROVIDER, provider)
                .eq(UserExternalAuthLogin.FIELD_PROVIDER_KEY, providerKey).of().lockByUpdate();
        return query;
    }

    @Override
    public TUser findUserByExternalProvider(String provider, String providerKey, boolean isChild) {
        EntityQueryWrapper<UserExternalAuthLogin> query = this.createExternalProviderQuery(provider, providerKey);
        UserExternalAuthLogin ueal = this.userExternalAuthLoginRepository.selectForFirst(query);
        if (ueal == null) {
            return null;
        }
        TUser user = this.getUserRepository().getByLock(ueal.getUserId(), LockModeEnum.UPDATE);
        if (isChild && user != null) {
            user.setRoles(this.queryUserRoles(user.getId()));
        }
        return user;
    }

    @Override
    public boolean existUserByExternalProvider(String provider, String providerKey) {
        EntityQueryWrapper<UserExternalAuthLogin> query = this.createExternalProviderQuery(provider, providerKey);
        return query.exist(this.userExternalAuthLoginRepository);
    }

    @Override
    public boolean activeUserById(long userId) {
        TUser user = this.getCheckUser(userId, "无法激活");
        if (!user.getStatus().equals(UserStatusConstants.NOT_ACTIVATE)) {
            ExceptionUtils.throwValidationException("用户未处于待激活状态，无法激活。");
        }
        return this.updateByUserSpecify(user.getId(), AbstractUser.FIELD_STATUS, UserStatusConstants.NORMAL) > 0;
    }

    @Override
    public boolean activePhone(TUser user) {
        ExceptionUtils.checkNotNull(user, "user");
        if (user.getId() == null) {
            ExceptionUtils.throwValidationException("无法激活不存在的用户。");
        }
        if (StringUtils.isNullOrBlank(user.getPhoneNumber())) {
            ExceptionUtils.throwValidationException("电话号码为空，无法激活。");
        }
        if (!user.getIsActivatePhone()) {
            user.setIsActivatePhone(true);
        }

        return this.updateByUserSpecify(user.getId(), AbstractUser.FIELD_IS_ACTIVATE_PHONE, true) > 0;
    }

    @Override
    public boolean activePhoneById(long userId) {
        TUser user = this.getCheckUser(userId, "无法激活手机");
        if (user.getIsActivatePhone()) {
            return true;
        }
        return this.updateByUserSpecify(user.getId(), AbstractUser.FIELD_IS_ACTIVATE_PHONE, true) > 0;
    }

    @Override
    public boolean activeEmail(TUser user) {
        ExceptionUtils.checkNotNull(user, "user");
        if (user.getId() == null) {
            ExceptionUtils.throwValidationException("无法激活不存在的用户。");
        }
        if (StringUtils.isNullOrBlank(user.getEmailAddress())) {
            ExceptionUtils.throwValidationException("邮箱为空，无法激活。");
        }
        if (!user.getIsActivatePhone()) {
            user.setIsActivatePhone(true);
        }
        return this.updateByUserSpecify(user.getId(), AbstractUser.FIELD_IS_ACTIVATE_EMAIL, true) > 0;
    }

    @Override
    public boolean activeEmailById(long userId) {
        TUser user = this.getCheckUser(userId, "无法激活邮件");
        if (user.getIsActivateEmail()) {
            return true;
        }
        return this.updateByUserSpecify(user.getId(), AbstractUser.FIELD_IS_ACTIVATE_EMAIL, true) > 0;
    }


    @Override
    public boolean lockUserById(long userId) {
        TUser user = this.getCheckUser(userId, "无法锁定");
        if (user.getStatus().equals(UserStatusConstants.LOCKING)) {
            return true;
        }
        return this.updateByUserSpecify(user.getId(), AbstractUser.FIELD_STATUS, UserStatusConstants.LOCKING) > 0;
    }

    /**
     * 根据用户id取消锁定用户
     *
     * @param userId 用户id
     * @return
     */
    @Override
    public boolean cancelLockUserById(long userId) {
        TUser user = this.getCheckUser(userId, "无法撤销锁定");
        if (!user.getStatus().equals(UserStatusConstants.LOCKING)) {
            ExceptionUtils.throwValidationException("用户未处于锁定状态，无法撤销。");
        }
        return this.updateByUserSpecify(user.getId(), AbstractUser.FIELD_STATUS, UserStatusConstants.NORMAL) > 0;
    }

    /**
     * 根据用户id执行用户过期
     *
     * @param userId 用户id
     * @return
     */
    @Override
    public boolean expireUserById(long userId) {
        TUser user = this.getCheckUser(userId, "无法执行过期");
        if (user.getStatus().equals(UserStatusConstants.EXPIRED)) {
            return true;
        }
        return this.updateByUserSpecify(user.getId(), AbstractUser.FIELD_STATUS, UserStatusConstants.EXPIRED) > 0;
    }

    /**
     * 根据用户id取消过期用户
     *
     * @param userId 用户id
     * @return
     */
    @Override
    public boolean cancelExpireUserById(long userId) {
        TUser user = this.getCheckUser(userId, "无法撤销过期");
        if (!user.getStatus().equals(UserStatusConstants.EXPIRED)) {
            ExceptionUtils.throwValidationException("用户未处于过期状态，无法撤销。");
        }
        return this.updateByUserSpecify(user.getId(), AbstractUser.FIELD_STATUS, UserStatusConstants.NORMAL) > 0;
    }

    @Override
    public TUser checkOrCreateUserAndRole(UserRoleDefinition<TUser, TRole> definitionInfo) {
        TUser user = definitionInfo.createUser();
        if (!this.existUserByAccount(user.getUserName())) {
            TRole role = definitionInfo.createRole();
            EntityQueryWrapper<TRole> wrapper = new EntityQueryWrapper<>(this.getRoleEntityClass());
            wrapper.where().eq(DefaultRole.FIELD_NAME, role.getName())
                    .of()
                    .lockByUpdate();
            definitionInfo.setRoleRelationCondition(wrapper);
            TRole saveRole = this.getRoleRepository().selectForFirst(wrapper);
            if (saveRole == null) {
                this.addRole(role);
                this.authorizeByRoleAllPermission(role.getId(), definitionInfo.getModuleResourcesType());
                saveRole = role;
            }
            user.setStatus(UserStatusConstants.NORMAL);
            user.setRoles(new ArrayList<>(1));
            UserRole userRole = new UserRole();
            userRole.setRoleId(saveRole.getId());
            user.getRoles().add(userRole);
            this.addUser(user, false);
            return user;
        } else {
            return this.findUserByAccount(user.getUserName(), true);
        }
    }

    @Override
    public void saveCheckCredentialsDeviceInfo(CredentialsDeviceInfo credentialsDeviceInfo) {
        if (credentialsDeviceInfo instanceof DataValidation) {
            ((DataValidation) credentialsDeviceInfo).valid();
        }
    }

    @Override
    public UserDeviceAuthLogin addOrUpdateUserDeviceInfo(UserDeviceAuthLogin deviceAuthLogin) {
        deviceAuthLogin.forNullToDefault();
        deviceAuthLogin.valid();
        EntityQueryWrapper<UserDeviceAuthLogin> wrapper = new EntityQueryWrapper<>(UserDeviceAuthLogin.class);
        wrapper.where()
                .eq(UserDeviceAuthLogin.FIELD_USER_ID, deviceAuthLogin.getUserId())
                .eq(UserDeviceAuthLogin.FIELD_DEVICE_TYPE, deviceAuthLogin.getDeviceType())
                .of().lockByUpdate();
        UserDeviceAuthLogin oldAuthLogin = userDeviceAuthLoginRepository.selectForFirst(wrapper);
        if (oldAuthLogin == null) {
            this.userDeviceAuthLoginRepository.insert(deviceAuthLogin);
            return deviceAuthLogin;
        } else {
            if (oldAuthLogin.loadTarget(deviceAuthLogin) > 0) {
                this.userDeviceAuthLoginRepository.update(oldAuthLogin);
            }
            return oldAuthLogin;
        }
    }

    @Override
    public UserDeviceAuthLogin findUserDeviceAuth(String token) {
        EntityQueryWrapper<UserDeviceAuthLogin> query = new EntityQueryWrapper<>(UserDeviceAuthLogin.class);
        query.where().eq(UserDeviceAuthLogin.FIELD_TOKEN, token).of().lockByUpdate();
        UserDeviceAuthLogin authLogin = this.userDeviceAuthLoginRepository.selectForFirst(query);
        return authLogin;
    }

    @Override
    public TUser findUserByDeviceToken(String token, boolean isChild) {
        UserDeviceAuthLogin authLogin = this.findUserDeviceAuth(token);
        if (authLogin == null) {
            return null;
        }
        TUser user = this.getUserRepository().getByLock(authLogin.getUserId(), LockModeEnum.UPDATE);
        if (isChild && user != null) {
            user.setRoles(this.queryUserRoles(user.getId()));
        }
        return user;
    }

    @Override
    public int deleteUserDeviceTokenByUserId(long userId) {
        EntityQueryWrapper<UserDeviceAuthLogin> wrapper = new EntityQueryWrapper<>(UserDeviceAuthLogin.class);
        wrapper.where().eq(UserDeviceAuthLogin.FIELD_USER_ID, userId);
        return this.userDeviceAuthLoginRepository.deleteByWhere(wrapper);
    }

    @Override
    public boolean deleteUserDeviceToken(String token) {
        EntityQueryWrapper<UserDeviceAuthLogin> wrapper = new EntityQueryWrapper<>(UserDeviceAuthLogin.class);
        wrapper.where().eq(UserDeviceAuthLogin.FIELD_TOKEN, token);
        return this.userDeviceAuthLoginRepository.deleteByWhere(wrapper) > 0;
    }
}
