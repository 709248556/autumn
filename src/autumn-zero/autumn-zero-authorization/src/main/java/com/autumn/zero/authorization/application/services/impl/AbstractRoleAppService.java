package com.autumn.zero.authorization.application.services.impl;

import com.autumn.application.dto.input.AdvancedQueryInput;
import com.autumn.application.service.AbstractQueryApplicationService;
import com.autumn.domain.repositories.EntityRepository;
import com.autumn.exception.ExceptionUtils;
import com.autumn.mybatis.wrapper.EntityQueryWrapper;
import com.autumn.util.AutoMapUtils;
import com.autumn.util.excel.utils.ExcelUtils;
import com.autumn.zero.authorization.application.dto.PermissionDto;
import com.autumn.zero.authorization.application.dto.PermissionGrantedInput;
import com.autumn.zero.authorization.application.dto.PermissionResourcesModuleDto;
import com.autumn.zero.authorization.application.dto.roles.*;
import com.autumn.zero.authorization.application.services.RoleAppServiceBase;
import com.autumn.zero.authorization.entities.common.AbstractRole;
import com.autumn.zero.authorization.entities.common.AbstractUser;
import com.autumn.zero.authorization.entities.common.RolePermission;
import com.autumn.zero.authorization.entities.common.query.UserRoleQuery;
import com.autumn.zero.authorization.entities.defaulted.DefaultRole;
import com.autumn.zero.authorization.repositories.common.query.UserRoleQueryRepository;
import com.autumn.zero.authorization.services.AuthorizationServiceBase;
import com.autumn.zero.authorization.services.ResourcesService;
import com.autumn.zero.authorization.values.ResourcesModulePermissionTreeValue;
import com.autumn.zero.file.storage.application.dto.TemporaryFileInformationDto;
import com.autumn.zero.file.storage.application.services.FileUploadManager;
import org.apache.poi.ss.usermodel.Workbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * 角色抽象
 *
 * @param <TRole>           角色类型
 * @param <TUser>           用户类型
 * @param <TUserRepository> 用户仓储类型
 * @param <TAddInput>       添加输入类型
 * @param <TUpdateInput>    更新输入类型
 * @param <TOutputItem>     输出项目类型
 * @param <TOutputDetails>  输出详情类型
 * @Description TODO
 * @Author 老码农
 * @Date 2019-07-27 13:05
 */
public abstract class AbstractRoleAppService<TRole extends AbstractRole,
        TUser extends AbstractUser, TRoleRepository
        extends EntityRepository<TRole, Long>,
        TAddInput extends RoleInput,
        TUpdateInput extends RoleInput,
        TOutputItem extends RoleDto,
        TOutputDetails extends RoleOutput>
        extends AbstractQueryApplicationService<Long, TRole, TRoleRepository, TOutputItem, TOutputDetails>
        implements RoleAppServiceBase<TAddInput, TUpdateInput, TOutputItem, TOutputDetails> {

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
    public AbstractRoleAppService() {
        super("TRole", "TOutputItem", "TOutputDetails");
        this.init();
    }

    /**
     * 实例化
     *
     * @param roleEntityClass    角色实体类型
     * @param outputItemClass    输出项目类型
     * @param outputDetailsClass 输出详情类型
     */
    public AbstractRoleAppService(Class<TRole> roleEntityClass,
                                  Class<TOutputItem> outputItemClass,
                                  Class<TOutputDetails> outputDetailsClass) {
        super(roleEntityClass, outputItemClass, outputDetailsClass);
        this.init();
    }

    private void init() {
        this.getSearchMembers().add(DefaultRole.FIELD_NAME);
    }

    private static void apply(PermissionResourcesModuleDto s, RolePermission t) {
        t.setName(s.getPermissionName());
    }

    @Override
    public String getModuleName() {
        return "角色管理";
    }

    /**
     * 设置默认值
     *
     * @param entity 实体
     */
    protected void setRoleDefault(TRole entity) {

    }

    /**
     *
     */
    @Override
    protected void queryByOrder(EntityQueryWrapper<TRole> query) {
        query.orderBy(DefaultRole.FIELD_SORT_ID).orderBy(DefaultRole.FIELD_ID);
    }

    private List<RoleUserOutput> queryUsers(long roleId) {
        EntityQueryWrapper<UserRoleQuery> query = new EntityQueryWrapper<>(UserRoleQuery.class);
        query.where().eq(UserRoleQuery.FIELD_ROLE_ID, roleId).of().orderBy(UserRoleQuery.FIELD_ID);
        List<UserRoleQuery> roles = this.userRoleQueryRepository.selectForList(query);
        return AutoMapUtils.mapForList(roles, RoleUserOutput.class);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public TOutputDetails add(TAddInput input) {
        ExceptionUtils.checkNotNull(input, "input");
        input.valid();
        TRole entity = AutoMapUtils.map(input, this.getQueryEntityClass());
        this.setRoleDefault(entity);
        authorizationService.addRole(entity);
        TOutputDetails result = AutoMapUtils.map(entity, this.getOutputDetailsClass());
        result.setUsers(this.queryUsers(entity.getId()));
        this.getAuditedLogger().addLog(this, "添加", entity);
        return result;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public TOutputDetails update(TUpdateInput input) {
        ExceptionUtils.checkNotNull(input, "input");
        input.valid();
        TRole old = this.getQueryEntityByUpdateLock(input.getId());
        if (old == null) {
            ExceptionUtils.throwValidationException("无法修改不存在的角色。");
        }
        TRole entity = AutoMapUtils.map(input, this.getQueryEntityClass());
        this.setRoleDefault(entity);
        authorizationService.updateRole(entity);
        TOutputDetails result = AutoMapUtils.map(entity, this.getOutputDetailsClass());
        result.setUsers(this.queryUsers(entity.getId()));
        this.getAuditedLogger().addLog(this, "修改", entity);
        return result;
    }

    @Override
    public TOutputDetails queryById(Long id) {
        TRole entity = this.getQueryEntityById(id);
        TOutputDetails result = null;
        if (entity != null) {
            result = AutoMapUtils.map(entity, this.getOutputDetailsClass());
            result.setUsers(this.queryUsers(entity.getId()));
        }
        return result;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void deleteById(Long id) {
        TRole entity = this.getQueryEntityByUpdateLock(id);
        if (entity == null) {
            ExceptionUtils.throwValidationException("指定的角色不存在。");
        }
        authorizationService.deleteRoleById(id);
        this.getAuditedLogger().addLog(this, "删除", entity);
    }

    private String getOpLog(TRole role) {
        return "角色[" + role.getName() + "]";
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public RolePermissionOutput authorize(PermissionGrantedInput input) {
        ExceptionUtils.checkNotNull(input, "input");
        input.valid();
        List<RolePermission> permissions = AutoMapUtils.mapForList(input.getPermissions(), RolePermission.class,
                AbstractRoleAppService::apply);
        TRole role = authorizationService.authorizeByRole(input.getId(), input.getResourcesType(), permissions);
        RolePermissionOutput result = AutoMapUtils.map(role, RolePermissionOutput.class);
        result.setPermissions(AutoMapUtils.mapForList(permissions, PermissionResourcesModuleDto.class, (s, t) -> {
            t.setPermissionName(s.getName());
        }));
        this.getAuditedLogger().addLog(this, "授权", this.getOpLog(role));
        return result;
    }

    @Override
    public RolePermissionOutput authorizeByQuery(PermissionDto input) {
        TRole role = authorizationService.getRole(input.getId(), false);
        if (role == null) {
            return null;
        }
        RolePermissionOutput result = AutoMapUtils.map(role, RolePermissionOutput.class);
        List<RolePermission> permissions = authorizationService.queryRolePermissions(input.getId());
        result.setPermissions(AutoMapUtils.mapForList(permissions, PermissionResourcesModuleDto.class, (s, t) -> {
            t.setPermissionName(s.getName());
        }));
        return result;
    }

    @Override
    public List<ResourcesModulePermissionTreeValue> authorizeByModulePermissionTree(PermissionDto input) {
        List<RolePermission> permissions = authorizationService.queryRolePermissions(input.getId());
        return resourcesService.matchByPermissionTree(input.getResourcesType(), permissions);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void authorizeByAllPermission(PermissionDto input) {
        ExceptionUtils.checkNotNull(input, "input");
        input.valid();
        TRole entity = this.getQueryEntityById(input.getId());
        if (entity == null) {
            ExceptionUtils.throwValidationException("指定的角色不存在。");
        }
        authorizationService.authorizeByRoleAllPermission(input.getId(), input.getResourcesType());
        this.getAuditedLogger().addLog(this, "授权全部权限", this.getOpLog(entity));
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
