package com.autumn.zero.authorization.application.services.impl;

import com.autumn.application.dto.input.AdvancedQueryInput;
import com.autumn.application.service.AbstractQueryApplicationService;
import com.autumn.domain.repositories.EntityRepository;
import com.autumn.exception.ExceptionUtils;
import com.autumn.mybatis.wrapper.EntityQueryWrapper;
import com.autumn.security.constants.UserStatusConstants;
import com.autumn.util.AutoMapUtils;
import com.autumn.util.excel.utils.ExcelUtils;
import com.autumn.zero.authorization.application.dto.PermissionDto;
import com.autumn.zero.authorization.application.dto.PermissionGrantedInput;
import com.autumn.zero.authorization.application.dto.PermissionResourcesModuleDto;
import com.autumn.zero.authorization.application.dto.modules.ResourcesTypeInput;
import com.autumn.zero.authorization.application.dto.users.*;
import com.autumn.zero.authorization.application.services.UserAppServiceBase;
import com.autumn.zero.authorization.entities.common.AbstractRole;
import com.autumn.zero.authorization.entities.common.AbstractUser;
import com.autumn.zero.authorization.entities.common.UserPermission;
import com.autumn.zero.authorization.entities.common.query.UserRoleQuery;
import com.autumn.zero.authorization.entities.defaulted.DefaultUser;
import com.autumn.zero.authorization.repositories.common.query.UserRoleQueryRepository;
import com.autumn.zero.authorization.services.AuthorizationServiceBase;
import com.autumn.zero.authorization.services.ResourcesService;
import com.autumn.zero.authorization.values.ResourcesModulePermissionTreeValue;
import com.autumn.zero.authorization.values.ResourcesModuleTreeValue;
import com.autumn.zero.file.storage.application.dto.TemporaryFileInformationDto;
import com.autumn.zero.file.storage.application.services.FileUploadManager;
import org.apache.poi.ss.usermodel.Workbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;


/**
 * 用户抽象
 *
 * @param <TUser>           用户类型
 * @param <TRole>           角色类型
 * @param <TUserRepository> 用户仓储类型
 * @param <TAddInput>       添加输入类型
 * @param <TUpdateInput>    更新输入类型
 * @param <TOutputItem>     输出项目类型
 * @param <TOutputDetails>  输出详情类型
 * @Description TODO
 * @Author 老码农
 * @Date 2019-07-27 12:30
 */
public abstract class AbstractUserAppService<TUser extends AbstractUser,
        TRole extends AbstractRole,
        TUserRepository extends EntityRepository<TUser, Long>,
        TAddInput extends UserAddInput,
        TUpdateInput extends UserInput,
        TOutputItem extends UserOutput,
        TOutputDetails extends UserDetailsOutput>
        extends AbstractQueryApplicationService<Long, TUser, TUserRepository, TOutputItem, TOutputDetails>
        implements UserAppServiceBase<TAddInput, TUpdateInput, TOutputItem, TOutputDetails> {

    @Autowired
    private UserRoleQueryRepository userRoleQueryRepository;

    @Autowired
    private AuthorizationServiceBase<TUser, TRole> authorizationService;

    @Autowired
    private ResourcesService resourcesService;

    @Autowired
    protected FileUploadManager fileUploadManager;

    /**
     *
     */
    public AbstractUserAppService() {
        super("TUser", "TOutputItem", "TOutputDetails");
        this.init();
    }

    /**
     * 实例化
     *
     * @param userEntityClass    用户实体类型
     * @param outputItemClass    输出项目类型
     * @param outputDetailsClass 输出详情类型
     */
    public AbstractUserAppService(Class<TUser> userEntityClass,
                                  Class<TOutputItem> outputItemClass,
                                  Class<TOutputDetails> outputDetailsClass) {
        super(userEntityClass, outputItemClass, outputDetailsClass);
        this.init();
    }

    private void init() {
        this.getSearchMembers().add(DefaultUser.FIELD_USER_NAME);
        this.getSearchMembers().add(DefaultUser.FIELD_REAL_NAME);
        this.getSearchMembers().add(DefaultUser.FIELD_PHONE_NUMBER);
        this.getSearchMembers().add(DefaultUser.FIELD_EMAIL_ADDRESS);
    }

    @Override
    public String getModuleName() {
        return "用户管理";
    }

    /**
     *
     */
    @Override
    protected void queryByOrder(EntityQueryWrapper<TUser> query) {
        query.orderBy(AbstractUser.FIELD_ID);
    }

    private List<UserRoleOutput> queryRoles(long userId) {
        EntityQueryWrapper<UserRoleQuery> query = new EntityQueryWrapper<>(UserRoleQuery.class);
        query.where().eq(UserRoleQuery.FIELD_USER_ID, userId).of().orderBy(UserRoleQuery.FIELD_ID);
        List<UserRoleQuery> roles = this.userRoleQueryRepository.selectForList(query);
        return AutoMapUtils.mapForList(roles, UserRoleOutput.class);
    }

    /**
     * 设置默认值
     *
     * @param entity 实体
     */
    protected void setUserDefault(TUser entity) {

    }

    /**
     * 检查管理权限
     *
     * @param user
     */
    protected void checkManagerPermission(TUser user) {

    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public TOutputDetails add(TAddInput input) {
        ExceptionUtils.checkNotNull(input, "input");
        input.valid();
        TUser entity = AutoMapUtils.map(input, this.getQueryEntityClass());
        entity.setStatus(UserStatusConstants.NORMAL);
        this.setUserDefault(entity);
        entity.setIsSysUser(false);
        authorizationService.addUser(entity, true);
        TOutputDetails result = AutoMapUtils.map(entity, this.getOutputDetailsClass());
        result.setRoles(this.queryRoles(entity.getId()));
        this.getAuditedLogger().addLog(this, "添加", entity);
        return result;
    }


    @Override
    @Transactional(rollbackFor = Exception.class)
    public TOutputDetails update(TUpdateInput input) {
        ExceptionUtils.checkNotNull(input, "input");
        input.valid();
        TUser old = this.getQueryEntityByUpdateLock(input.getId());
        if (old == null) {
            ExceptionUtils.throwValidationException("无法修改不存在的用户。");
        }
        TUser entity = AutoMapUtils.map(input, this.getQueryEntityClass());
        entity.setStatus(UserStatusConstants.NORMAL);
        this.setUserDefault(entity);
        authorizationService.updateUser(entity);
        TOutputDetails result = AutoMapUtils.map(entity, this.getOutputDetailsClass());
        result.setRoles(this.queryRoles(entity.getId()));
        this.getAuditedLogger().addLog(this, "修改", entity);
        return result;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void resetPassword(ResetPasswordInput input) {
        ExceptionUtils.checkNotNull(input, "input");
        input.valid();
        authorizationService.resetPassword(input.getId(), input.getNewPassword());
    }

    @Override
    public TOutputDetails queryById(Long id) {
        TUser entity = this.getQueryEntityById(id);
        TOutputDetails result = null;
        if (entity != null) {
            result = AutoMapUtils.map(entity, this.getOutputDetailsClass());
            result.setRoles(this.queryRoles(entity.getId()));
        }
        return result;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void deleteById(Long id) {
        if (this.getSession().getUserId().equals(id)) {
            ExceptionUtils.throwValidationException("用户不能删除自身。");
        }
        TUser user = this.getQueryEntityByUpdateLock(id);
        if (user == null) {
            ExceptionUtils.throwValidationException("指定的用户不存在。");
        }
        this.authorizationService.deleteUserById(id);
        this.getAuditedLogger().addLog(this, "删除", user);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public UserPermissionOutput authorize(PermissionGrantedInput input) {
        ExceptionUtils.checkNotNull(input, "input");
        input.valid();
        List<UserPermission> permissions = AutoMapUtils.mapForList(input.getPermissions(), UserPermission.class,
                (s, t) -> {
                    t.setName(s.getPermissionName());
                });
        TUser user = authorizationService.authorizeByUser(input.getId(), input.getResourcesType(), permissions);
        UserPermissionOutput result = AutoMapUtils.map(user, UserPermissionOutput.class);
        result.setPermissions(AutoMapUtils.mapForList(permissions, PermissionResourcesModuleDto.class, (s, t) -> {
            t.setPermissionName(s.getName());
        }));
        this.getAuditedLogger().addLog(this, "授权", this.getOpLog(user));
        return result;
    }

    @Override
    public UserPermissionOutput authorizeByQuery(PermissionDto input) {
        ExceptionUtils.checkNotNull(input, "input");
        input.valid();
        TUser user = this.getQueryEntityById(input.getId());
        if (user == null) {
            return null;
        }
        UserPermissionOutput result = AutoMapUtils.map(user, UserPermissionOutput.class);
        List<UserPermission> permissions = authorizationService.queryUserPermissions(input.getId());
        result.setPermissions(AutoMapUtils.mapForList(permissions, PermissionResourcesModuleDto.class, (s, t) -> {
            t.setPermissionName(s.getName());
        }));
        return result;
    }

    @Override
    public List<ResourcesModulePermissionTreeValue> authorizeByModulePermissionTree(PermissionDto input) {
        List<UserPermission> permissions = authorizationService.queryUserPermissions(input.getId());
        return resourcesService.matchByPermissionTree(input.getResourcesType(), permissions);
    }

    @Override
    public List<ResourcesModuleTreeValue> queryUserByMenuTree(ResourcesTypeInput input) {
        return authorizationService.queryUserByMenuTree(input.getResourcesType(), this.getSession().getUserId());
    }

    private String getOpLog(TUser user) {
        return "用户名称[" + user.getUserName() + "]，真实名称[" + (user.getRealName() != null ? user.getRealName() : "") + "]";
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void authorizeByAllPermission(PermissionDto input) {
        ExceptionUtils.checkNotNull(input, "input");
        input.valid();
        TUser user = this.getQueryEntityById(input.getId());
        if (user == null) {
            ExceptionUtils.throwValidationException("指定的用户不存在。");
        }
        authorizationService.authorizeByUserAllPermission(input.getId(), input.getResourcesType());
        this.getAuditedLogger().addLog(this, "授权全部权限", this.getOpLog(user));
    }

    @Override
    public TemporaryFileInformationDto exportFileByExcel(AdvancedQueryInput input) {
        Workbook workbook = this.exportByExcel(input);
        try {
            return fileUploadManager.saveTemporaryFileByWorkbook(this.getModuleName() + ExcelUtils.EXCEL_JOIN_EXTENSION_NAME, workbook);
        } catch (Exception e) {
            throw ExceptionUtils.throwApplicationException(e.getMessage(), e);
        }
    }
}
