package com.autumn.zero.authorization.application.services.impl;

import com.autumn.application.dto.input.AdvancedQueryInput;
import com.autumn.application.service.AbstractSimpleEditApplicationService;
import com.autumn.domain.values.StringConstantItemValue;
import com.autumn.exception.ExceptionUtils;
import com.autumn.mybatis.wrapper.EntityQueryWrapper;
import com.autumn.mybatis.wrapper.LockModeEnum;
import com.autumn.mybatis.wrapper.QueryWrapper;
import com.autumn.mybatis.wrapper.UpdateWrapper;
import com.autumn.util.excel.utils.ExcelUtils;
import com.autumn.validation.MatchesUtils;
import com.autumn.zero.authorization.application.dto.modules.PermissionNameCheckInput;
import com.autumn.zero.authorization.application.dto.modules.ResourcesInput;
import com.autumn.zero.authorization.application.dto.modules.ResourcesModulePermissionDto;
import com.autumn.zero.authorization.application.services.ResourcesModulePermissionAppService;
import com.autumn.zero.authorization.constants.OperationPermissionConstants;
import com.autumn.zero.authorization.entities.common.RolePermission;
import com.autumn.zero.authorization.entities.common.UserPermission;
import com.autumn.zero.authorization.entities.common.modules.ResourcesModule;
import com.autumn.zero.authorization.entities.common.modules.ResourcesModulePermission;
import com.autumn.zero.authorization.repositories.common.RolePermissionRepository;
import com.autumn.zero.authorization.repositories.common.UserPermissionRepository;
import com.autumn.zero.authorization.repositories.common.modules.ResourcesModulePermissionRepository;
import com.autumn.zero.authorization.repositories.common.modules.ResourcesModuleRepository;
import com.autumn.zero.authorization.services.ResourcesService;
import com.autumn.zero.authorization.utils.AuthServiceUtils;
import com.autumn.zero.file.storage.application.dto.TemporaryFileInformationDto;
import com.autumn.zero.file.storage.application.services.FileUploadManager;
import org.apache.poi.ss.usermodel.Workbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

/**
 * 资源权限模块应用服务
 *
 * @author 老码农 2018-12-09 13:29:21
 */
public class ResourcesModulePermissionAppServiceImpl extends
        AbstractSimpleEditApplicationService<Long, ResourcesModulePermission, ResourcesModulePermissionRepository, ResourcesModulePermissionDto, ResourcesModulePermissionDto>
        implements ResourcesModulePermissionAppService {

    @Autowired
    protected ResourcesModulePermissionRepository resourcesModulePermissionRepository;

    @Autowired
    protected ResourcesModuleRepository resourcesModuleRepository;

    @Autowired
    protected UserPermissionRepository userPermissionRepository;

    @Autowired
    protected RolePermissionRepository rolePermissionRepository;

    @Autowired
    protected ResourcesService resourcesService;

    @Autowired
    protected FileUploadManager fileUploadManager;

    public ResourcesModulePermissionAppServiceImpl() {
        this.getSearchMembers().add(ResourcesModulePermission.FIELD_NAME);
        this.getSearchMembers().add(ResourcesModulePermission.FIELD_RESOURCES_ID);
    }

    @Override
    protected void queryByOrder(EntityQueryWrapper<ResourcesModulePermission> query) {
        query.orderBy(ResourcesModulePermission.FIELD_RESOURCES_ID).orderBy(ResourcesModulePermission.FIELD_SORT_ID)
                .orderBy(ResourcesModulePermission.FIELD_NAME);
    }

    @Override
    public boolean existPermissionName(PermissionNameCheckInput input) {
        ExceptionUtils.checkNotNull(input, "input");
        input.valid();
        EntityQueryWrapper<ResourcesModulePermission> query = new EntityQueryWrapper<>(ResourcesModulePermission.class);
        query.where().eq(ResourcesModulePermission.FIELD_RESOURCES_ID, input.getResourcesId().trim())
                .eq(ResourcesModulePermission.FIELD_NAME, input.getName().trim());
        return query.exist(this.resourcesModulePermissionRepository);
    }

    /**
     * 清除权限缓存
     *
     * @param resourcesId
     */
    private void clearPermissionCache(String resourcesId) {
        ResourcesModule module = resourcesModuleRepository.get(resourcesId);
        if (module != null) {
            resourcesService.clearModulePermissionCache(module.getResourcesType());
        }
    }


    /**
     * @param input
     * @param checkName
     * @param checkfriendlyName
     * @param checkSortId
     * @param query
     */
    private void checkEdit(ResourcesModulePermissionDto input, boolean checkResources, boolean checkName,
                           boolean checkfriendlyName, boolean checkSortId, EntityQueryWrapper<ResourcesModulePermission> query) {
        if (!MatchesUtils.isNumberOrLetterOrChinese(input.getName())) {
            ExceptionUtils.throwValidationException("权限名称只能包含中文、字母和数字。");
        }
        input.setPermissionUrl(AuthServiceUtils.resetPermissionUrl(input.getPermissionUrl()));
        ResourcesModule module = resourcesModuleRepository.get(input.getResourcesId());
        if (module == null) {
            ExceptionUtils.throwValidationException("引用的资源[" + input.getResourcesId() + "]不存在。");
        }
        if (!module.getIsAuthorize()) {
            ExceptionUtils.throwValidationException("引用的资源名称[" + module.getName() + "]不支持权限配置, isAuthorize 为 false。");
        }
        if (checkName) {
            query.reset();
            query.where().eq(ResourcesModulePermission.FIELD_RESOURCES_ID, input.getResourcesId())
                    .eq(ResourcesModulePermission.FIELD_NAME, input.getName()).of().lock(LockModeEnum.UPDATE);
            if (query.countByWhere(this.getRepository()) > 0) {
                ExceptionUtils.throwValidationException(
                        String.format("相同的资源id[%s]与权限名称[%s]已重复。", input.getResourcesId(), input.getName()));
            }
        }
        if (checkfriendlyName) {
            query.reset();
            query.where().eq(ResourcesModulePermission.FIELD_RESOURCES_ID, input.getResourcesId())
                    .eq(ResourcesModulePermission.FIELD_FRIENDLY_NAME, input.getFriendlyName())
                    .of().lock(LockModeEnum.UPDATE);
            if (query.countByWhere(this.getRepository()) > 0) {
                ExceptionUtils.throwValidationException(
                        String.format("相同的资源id[%s]与权限友好名称[%s]已重复。", input.getResourcesId(), input.getFriendlyName()));
            }
        }
        if (checkSortId) {
            query.reset();
            query.where().eq(ResourcesModulePermission.FIELD_RESOURCES_ID, input.getResourcesId())
                    .eq(ResourcesModulePermission.FIELD_SORT_ID, input.getSortId()).of().lock(LockModeEnum.UPDATE);
            if (query.countByWhere(this.getRepository()) > 0) {
                ExceptionUtils.throwValidationException(
                        String.format("相同的资源id[%s]与显示顺序[%s]已重复。", input.getResourcesId(), input.getFriendlyName()));
            }
        }

    }

    @Override
    protected ResourcesModulePermission addBefore(ResourcesModulePermissionDto input,
                                                  EntityQueryWrapper<ResourcesModulePermission> query) {
        this.checkEdit(input, true, true, true, true, query);
        return super.addBefore(input, query);
    }


    @Override
    protected ResourcesModulePermissionDto addAfter(ResourcesModulePermissionDto input,
                                                    ResourcesModulePermission entity, EntityQueryWrapper<ResourcesModulePermission> query) {
        this.clearPermissionCache(entity.getResourcesId());
        return super.addAfter(input, entity, query);
    }

    @Override
    protected void updateBefore(ResourcesModulePermissionDto input, ResourcesModulePermission entity,
                                EntityQueryWrapper<ResourcesModulePermission> query) {
        boolean equalsResources = entity.getResourcesId().equalsIgnoreCase(input.getResourcesId());
        boolean checkName = !(equalsResources && entity.getName().equalsIgnoreCase(input.getName()));
        boolean checkfriendlyName = !(equalsResources
                && entity.getFriendlyName().equalsIgnoreCase(input.getFriendlyName()));
        boolean checkSortId = !(equalsResources && entity.getSortId().equals(input.getSortId()));
        this.checkEdit(input, !equalsResources, checkName, checkfriendlyName, checkSortId, query);
        super.updateBefore(input, entity, query);
    }

    @Override
    protected ResourcesModulePermissionDto updateAfter(ResourcesModulePermissionDto input,
                                                       ResourcesModulePermission entity, ResourcesModulePermission oldEntity,
                                                       EntityQueryWrapper<ResourcesModulePermission> query) {
        // 是否更新用户与角色引用的权限名称
        boolean isUpdate = !(entity.getResourcesId().equalsIgnoreCase(oldEntity.getResourcesId())
                && entity.getName().equalsIgnoreCase(oldEntity.getName()));
        if (isUpdate) {
            // 更新用户引用的权限
            UpdateWrapper su = new UpdateWrapper(UserPermission.class);
            su.where().eq(UserPermission.FIELD_RESOURCES_ID, oldEntity.getResourcesId())
                    .eq(UserPermission.FIELD_NAME, oldEntity.getName())
                    .of().set(UserPermission.FIELD_RESOURCES_ID, entity.getResourcesId())
                    .set(UserPermission.FIELD_NAME, entity.getName());
            this.userPermissionRepository.updateByWhere(su);

            // 更新角色引用的权限
            su = new UpdateWrapper(RolePermission.class);
            su.where().eq(RolePermission.FIELD_RESOURCES_ID, oldEntity.getResourcesId())
                    .eq(RolePermission.FIELD_NAME, oldEntity.getName())
                    .of().set(RolePermission.FIELD_RESOURCES_ID, entity.getResourcesId())
                    .set(RolePermission.FIELD_NAME, entity.getName());
            this.rolePermissionRepository.updateByWhere(su);
        }
        this.clearPermissionCache(entity.getResourcesId());
        return super.updateAfter(input, entity, oldEntity, query);
    }

    @Override
    protected void deleteBefore(ResourcesModulePermission module) {
        super.deleteBefore(module);
        if (module != null) {
            // 删除用户引用的权限
            QueryWrapper query = new QueryWrapper(UserPermission.class);
            query.where().eq(UserPermission.FIELD_RESOURCES_ID, module.getResourcesId()).eq(UserPermission.FIELD_NAME,
                    module.getName());
            this.userPermissionRepository.deleteByWhere(query);

            // 删除角色引用的权限
            query = new QueryWrapper(RolePermission.class);
            query.where().eq(RolePermission.FIELD_RESOURCES_ID, module.getResourcesId()).eq(RolePermission.FIELD_NAME,
                    module.getName());
            this.resourcesModulePermissionRepository.deleteByWhere(query);
        }
    }


    @Override
    protected void deleteAfter(ResourcesModulePermission module, boolean isSoftDelete) {
        super.deleteAfter(module, isSoftDelete);
        this.clearPermissionCache(module.getResourcesId());
    }

    /**
     * 添加权限
     *
     * @param resourcesId
     * @param name          {@link com.autumn.zero.authorization.constants.OperationPermissionConstants}
     * @param sortId
     * @param permissionUrl
     */
    private void addPermission(String resourcesId, String name, int sortId, String permissionUrl) {
        EntityQueryWrapper<ResourcesModulePermission> query = new EntityQueryWrapper<>(ResourcesModulePermission.class);
        query.where().eq(ResourcesModulePermission.FIELD_RESOURCES_ID, resourcesId).eq(ResourcesModulePermission.FIELD_NAME,
                name);
        if (query.countByWhere(this.getRepository()) == 0) {
            StringConstantItemValue item = OperationPermissionConstants.getItem(name);
            ResourcesModulePermission module = new ResourcesModulePermission();
            module.setFriendlyName(item.getName());
            module.setName(item.getValue());
            module.setResourcesId(resourcesId);
            module.setSortId(sortId);
            module.setPermissionUrl(permissionUrl);
            module.setSummary(item.getExplain());
            this.getRepository().insert(module);
        }
    }

    private void checkResources(String resourcesId) {
        ResourcesModule module = resourcesModuleRepository.get(resourcesId);
        if (module == null) {
            ExceptionUtils.throwValidationException("引用的资源Id[" + resourcesId + "]不存在。");
        }
        if (!module.getIsAuthorize()) {
            ExceptionUtils.throwValidationException("引用的资源名称[" + module.getName() + "]不支持权限配置, isAuthorize 为 false。");
        }
    }

    private void addQueryPermission(String resourcesId) {
        this.addPermission(resourcesId, OperationPermissionConstants.QUERY, 100, "");
        this.addPermission(resourcesId, OperationPermissionConstants.EXPORT, 101, "");
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void addDefaultQueryPermission(ResourcesInput input) {
        ExceptionUtils.checkNotNull(input, "input");
        input.valid();
        this.checkResources(input.getResourcesId());
        this.addQueryPermission(input.getResourcesId());
        this.clearPermissionCache(input.getResourcesId());
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void addDefaultEditPermission(ResourcesInput input) {
        ExceptionUtils.checkNotNull(input, "input");
        input.valid();
        this.checkResources(input.getResourcesId());
        this.addPermission(input.getResourcesId(), OperationPermissionConstants.ADD, 50, "");
        this.addPermission(input.getResourcesId(), OperationPermissionConstants.UPDATE, 51, "");
        this.addPermission(input.getResourcesId(), OperationPermissionConstants.DELETE, 52, "");
        this.addQueryPermission(input.getResourcesId());
        this.clearPermissionCache(input.getResourcesId());
    }

    @Override
    public String getModuleName() {
        return "资源权限";
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
