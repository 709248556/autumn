package com.autumn.zero.authorization.application.services.impl;

import com.autumn.application.dto.input.AdvancedQueryInput;
import com.autumn.application.service.AbstractSimpleEditApplicationService;
import com.autumn.domain.values.StringConstantItemValue;
import com.autumn.exception.ExceptionUtils;
import com.autumn.mybatis.wrapper.EntityQueryWrapper;
import com.autumn.mybatis.wrapper.EntityUpdateWrapper;
import com.autumn.mybatis.wrapper.LockModeEnum;
import com.autumn.mybatis.wrapper.QueryWrapper;
import com.autumn.util.AutoMapUtils;
import com.autumn.util.StringUtils;
import com.autumn.util.excel.utils.ExcelUtils;
import com.autumn.zero.authorization.application.dto.modules.PermissionNameCheckInput;
import com.autumn.zero.authorization.application.dto.modules.ResourcesInput;
import com.autumn.zero.authorization.application.dto.modules.ResourcesModulePermissionDto;
import com.autumn.zero.authorization.application.dto.modules.ResourcesModuleTypeDto;
import com.autumn.zero.authorization.application.services.ResourcesModuleAppService;
import com.autumn.zero.authorization.application.services.ResourcesModulePermissionAppService;
import com.autumn.zero.authorization.constants.OperationPermissionConstants;
import com.autumn.zero.authorization.constants.ResourcesModuleConstants;
import com.autumn.zero.authorization.entities.common.RolePermission;
import com.autumn.zero.authorization.entities.common.UserPermission;
import com.autumn.zero.authorization.entities.common.modules.ResourcesModule;
import com.autumn.zero.authorization.entities.common.modules.ResourcesModulePermission;
import com.autumn.zero.authorization.entities.common.modules.ResourcesModuleType;
import com.autumn.zero.authorization.repositories.common.RolePermissionRepository;
import com.autumn.zero.authorization.repositories.common.UserPermissionRepository;
import com.autumn.zero.authorization.repositories.common.modules.ResourcesModulePermissionRepository;
import com.autumn.zero.authorization.repositories.common.modules.ResourcesModuleRepository;
import com.autumn.zero.authorization.repositories.common.modules.ResourcesModuleTypeRepository;
import com.autumn.zero.authorization.services.ResourcesService;
import com.autumn.zero.authorization.utils.AuthServiceUtils;
import com.autumn.zero.authorization.values.ResourcesModulePermissionTreeValue;
import com.autumn.zero.authorization.values.ResourcesModuleTreeValue;
import com.autumn.zero.authorization.values.ResourcesModuleValue;
import com.autumn.zero.file.storage.application.dto.TemporaryFileInformationDto;
import com.autumn.zero.file.storage.application.services.FileUploadManager;
import org.apache.poi.ss.usermodel.Workbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collection;
import java.util.List;

/**
 * 资源模块应用服务
 *
 * @author 老码农 2018-12-08 23:24:27
 */
public class ResourcesModuleAppServiceImpl extends
        AbstractSimpleEditApplicationService<String, ResourcesModule, ResourcesModuleRepository, ResourcesModuleValue, ResourcesModuleValue>
        implements ResourcesModuleAppService {

    @Autowired
    protected ResourcesModuleRepository resourcesModuleRepository;

    @Autowired
    protected UserPermissionRepository userPermissionRepository;

    @Autowired
    protected RolePermissionRepository rolePermissionRepository;

    @Autowired
    protected ResourcesModulePermissionRepository resourcesModulePermissionRepository;

    @Autowired
    protected ResourcesService resourcesService;

    @Autowired
    protected ResourcesModulePermissionAppService resourcesModulePermissionAppService;

    @Autowired
    protected ResourcesModuleTypeRepository resourcesModuleTypeRepository;

    @Autowired
    protected FileUploadManager fileUploadManager;

    /**
     *
     */
    @Override
    protected void queryByOrder(EntityQueryWrapper<ResourcesModule> query) {
        query.orderBy(ResourcesModule.FIELD_PARENT_ID).orderBy(ResourcesModule.FIELD_SORT_ID)
                .orderBy(ResourcesModule.FIELD_ID);
    }

    public ResourcesModuleAppServiceImpl() {
        this.getSearchMembers().add(ResourcesModule.FIELD_NAME);
        this.getSearchMembers().add(ResourcesModule.FIELD_CUSTOM_NAME);
    }

    private void editChedk(ResourcesModuleValue input, EntityQueryWrapper<ResourcesModule> query, boolean checkId,
                           boolean checkName, boolean checkCustomName, boolean checkSort) {
        if (StringUtils.isNullOrBlank(input.getParentId())) {
            input.setParentId("");
        }
        if (input.getId().equalsIgnoreCase(input.getParentId())) {
            ExceptionUtils.throwValidationException("不能引用本身为父级。");
        }
        input.setPermissionUrl(AuthServiceUtils.resetPermissionUrl(input.getPermissionUrl()));
        if (checkId) {
            query.reset();
            query.where().eq(ResourcesModule.FIELD_ID, input.getId()).of().lock(LockModeEnum.UPDATE);
            if (query.countByWhere(this.resourcesModuleRepository) > 0) {
                ExceptionUtils.throwValidationException(this.getModuleName() + "id不能重复。");
            }
        }
        ResourcesModule parentModule;
        if (!StringUtils.isNullOrBlank(input.getParentId())) {
            parentModule = this.resourcesModuleRepository.get(input.getParentId());
            if (parentModule == null) {
                ExceptionUtils
                        .throwValidationException("指定的父级" + this.getModuleName() + "[" + input.getParentId() + "]不存在。");
            }
            input.setResourcesType(parentModule.getResourcesType());
        } else {
            if (input.getResourcesType() == null) {
                ExceptionUtils.throwValidationException(this.getModuleName() + "类型不能为空。");
            }
            if (!this.existResourcesType(input.getResourcesType())) {
                ExceptionUtils.throwValidationException(this.getModuleName() + " 的资源类型不存在。");
            }
        }
        String msgName;
        if (StringUtils.isNullOrBlank(input.getParentId())) {
            msgName = "";
        } else {
            msgName = "同一父级下的";
        }
        if (checkName) {
            query.reset();
            query.where().eq(ResourcesModule.FIELD_NAME, input.getName())
                    .eq(ResourcesModule.FIELD_RESOURCES_TYPE, input.getResourcesType())
                    .eq(ResourcesModule.FIELD_PARENT_ID, input.getParentId()).of().lock(LockModeEnum.UPDATE);
            if (query.countByWhere(this.resourcesModuleRepository) > 0) {
                ExceptionUtils
                        .throwValidationException(msgName + this.getModuleName() + "名称[" + input.getName() + "]已重复。");
            }
        }
        if (StringUtils.isNullOrBlank(input.getCustomName())) {
            input.setCustomName(input.getName());
        } else {
            if (checkCustomName) {
                query.reset();
                query.where().eq(ResourcesModule.FIELD_CUSTOM_NAME, input.getCustomName())
                        .eq(ResourcesModule.FIELD_RESOURCES_TYPE, input.getResourcesType())
                        .eq(ResourcesModule.FIELD_PARENT_ID, input.getParentId()).of().lock(LockModeEnum.UPDATE);
                if (query.countByWhere(this.resourcesModuleRepository) > 0) {
                    ExceptionUtils.throwValidationException(
                            msgName + "自定义" + this.getModuleName() + "名称[" + input.getCustomName() + "]已重复。");
                }
            }
        }
        if (checkSort) {
            query.reset();
            query.where().eq(ResourcesModule.FIELD_SORT_ID, input.getSortId())
                    .eq(ResourcesModule.FIELD_RESOURCES_TYPE, input.getResourcesType())
                    .eq(ResourcesModule.FIELD_PARENT_ID, input.getParentId()).of().lock(LockModeEnum.UPDATE);
            if (query.countByWhere(this.resourcesModuleRepository) > 0) {
                ExceptionUtils.throwValidationException(msgName + "顺序[" + input.getSortId() + "]已重复。");
            }
        }
    }

    @Override
    protected ResourcesModule addBefore(ResourcesModuleValue input, EntityQueryWrapper<ResourcesModule> query) {
        if (input.getParentId() == null) {
            input.setParentId("");
        }
        this.editChedk(input, query, true, true, true, true);
        return super.addBefore(input, query);
    }

    @Override
    protected ResourcesModuleValue addAfter(ResourcesModuleValue input, ResourcesModule entity,
                                            EntityQueryWrapper<ResourcesModule> query) {
        resourcesService.clearModuleCache(entity.getResourcesType());
        return super.addAfter(input, entity, query);
    }

    @Override
    protected void updateBefore(ResourcesModuleValue input, ResourcesModule entity,
                                EntityQueryWrapper<ResourcesModule> query) {
        if (input.getParentId() == null) {
            input.setParentId("");
        }
        boolean checkParent = !entity.getParentId().equalsIgnoreCase(input.getParentId());
        boolean checkSort = !entity.getSortId().equals(input.getSortId()) && checkParent;
        boolean checkName = !entity.getName().equalsIgnoreCase(input.getName()) && checkParent;
        boolean checkCustomName = !entity.getCustomName().equalsIgnoreCase(input.getCustomName()) && checkParent;
        this.editChedk(input, query, false, checkName, checkCustomName, checkSort);
        if (entity.getIsSysModule()) {
            input.setIsSysModule(true);
        }
        super.updateBefore(input, entity, query);
    }

    @Override
    protected ResourcesModuleValue updateAfter(ResourcesModuleValue input, ResourcesModule entity,
                                               ResourcesModule oldEntity, EntityQueryWrapper<ResourcesModule> query) {
        // 如果变更了资源类型
        if (!oldEntity.getResourcesType().equals(entity.getResourcesType())) {
            this.updateChildResourcesType(entity.getId(), entity.getResourcesType());
        }
        resourcesService.clearModuleCache(entity.getResourcesType());
        return super.updateAfter(input, entity, oldEntity, query);
    }

    /**
     * 递归更新子级资源类型
     *
     * @param resourcesType 资源类型
     */
    private void updateChildResourcesType(String parentId, int resourcesType) {
        EntityQueryWrapper<ResourcesModule> query = new EntityQueryWrapper<>(ResourcesModule.class);
        query.where().eq(ResourcesModule.FIELD_PARENT_ID, parentId);
        List<ResourcesModule> modules = this.resourcesModuleRepository.selectForList(query);
        if (modules.size() > 0) {
            EntityUpdateWrapper<ResourcesModule> esu = new EntityUpdateWrapper<>(ResourcesModule.class);
            esu.where().eq(ResourcesModule.FIELD_PARENT_ID, parentId).of().set(ResourcesModule.FIELD_RESOURCES_TYPE, resourcesType);
            this.resourcesModuleRepository.updateByWhere(esu);
            for (ResourcesModule module : modules) {
                this.updateChildResourcesType(module.getId(), resourcesType);
            }
        }
    }

    @Override
    protected void deleteBefore(ResourcesModule entity) {
        super.deleteBefore(entity);
        if (entity.getIsSysModule()) {
            ExceptionUtils.throwValidationException(entity.getName() + " 属系统模块，不能删除。");
        }
        // 父级引用判断
        QueryWrapper query = new QueryWrapper(this.getEntityClass());
        query.where().eq(ResourcesModule.FIELD_PARENT_ID, entity.getId()).of().lock(LockModeEnum.UPDATE);
        if (this.resourcesModuleRepository.countByWhere(query) > 0) {
            ExceptionUtils.throwValidationException(this.getModuleName() + "[" + entity.getName() + "]已有子级不能删除。");
        }

        // 删除用户权限引用的资源
        query = new QueryWrapper(UserPermission.class);
        query.where().eq(UserPermission.FIELD_RESOURCES_ID, entity.getId());
        this.userPermissionRepository.deleteByWhere(query);

        // 删除角色权限引用的资源
        query = new QueryWrapper(RolePermission.class);
        query.where().eq(RolePermission.FIELD_RESOURCES_ID, entity.getId());
        this.rolePermissionRepository.deleteByWhere(query);

        // 删除模块权限引用的资源
        query = new QueryWrapper(ResourcesModulePermission.class);
        query.where().eq(ResourcesModulePermission.FIELD_RESOURCES_ID, entity.getId());
        this.resourcesModulePermissionRepository.deleteByWhere(query);
    }

    @Override
    protected void deleteAfter(ResourcesModule entity, boolean isSoftDelete) {
        resourcesService.clearModuleCache(entity.getResourcesType());
        super.deleteAfter(entity, isSoftDelete);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public boolean addDefaultSystemModule() {
        EntityQueryWrapper<ResourcesModule> query = new EntityQueryWrapper<>(ResourcesModule.class);
        boolean isAdd = true;
        Collection<ResourcesModule> items = ResourcesModuleConstants.items();
        for (ResourcesModule item : items) {
            if (item.getParentId() == null) {
                item.setParentId("");
            }
            query.reset();
            query.where().eq(ResourcesModule.FIELD_ID, item.getId()).of().lock(LockModeEnum.UPDATE);
            if (query.countByWhere(this.getRepository()) > 0) {
                isAdd = false;
                break;
            }
            query.reset();
            query.where().eq(ResourcesModule.FIELD_NAME, item.getName()).eq(ResourcesModule.FIELD_PARENT_ID, item.getParentId())
                    .of().lock(LockModeEnum.UPDATE);
            if (query.countByWhere(this.getRepository()) > 0) {
                isAdd = false;
                break;
            }
        }
        if (isAdd) {
            for (ResourcesModule item : items) {
                ResourcesModule module = new ResourcesModule();
                AutoMapUtils.mapForLoad(item, module);
                module.forNullToDefault();
                this.getRepository().insert(module);
            }
            addDefaultPermission();
            resourcesService.clearModuleAllPermissionCache();
            return true;
        }
        return false;
    }

    /**
     * 添加默认权限
     */
    private void addDefaultPermission() {
        // 添加默认权限配置
        ResourcesInput input;
        input = new ResourcesInput(ResourcesModuleConstants.SYS_PERMISSION_ROLE);
        resourcesModulePermissionAppService.addDefaultEditPermission(input);
        input = new ResourcesInput(ResourcesModuleConstants.SYS_PERMISSION_USER);
        resourcesModulePermissionAppService.addDefaultEditPermission(input);

        input = new ResourcesInput(ResourcesModuleConstants.SYS_LOG_OPERATION);
        resourcesModulePermissionAppService.addDefaultQueryPermission(input);
        this.addClearPermission(ResourcesModuleConstants.SYS_LOG_OPERATION);

        input = new ResourcesInput(ResourcesModuleConstants.SYS_LOG_LONGIN);
        resourcesModulePermissionAppService.addDefaultQueryPermission(input);
        this.addClearPermission(ResourcesModuleConstants.SYS_LOG_LONGIN);

        input = new ResourcesInput(ResourcesModuleConstants.SYS_MODULE_RESOURCES);
        resourcesModulePermissionAppService.addDefaultEditPermission(input);
    }

    private void addClearPermission(String resourcesId) {
        PermissionNameCheckInput nameInput = new PermissionNameCheckInput();
        nameInput.setResourcesId(resourcesId);
        nameInput.setName(OperationPermissionConstants.CLEAR);
        if (resourcesModulePermissionAppService.existPermissionName(nameInput)) {
            return;
        }
        StringConstantItemValue item = OperationPermissionConstants.getItem(OperationPermissionConstants.CLEAR);
        ResourcesModulePermissionDto res = new ResourcesModulePermissionDto();
        res.setResourcesId(resourcesId);
        res.setFriendlyName(item.getName());
        res.setName(item.getValue());
        res.setPermissionUrl("/deleteAll");
        res.setSummary(item.getExplain());
        res.setSortId(102);
        resourcesModulePermissionAppService.add(res);
    }

    @Override
    public List<ResourcesModuleTreeValue> queryByTree() {
        return resourcesService.queryByTree();
    }

    @Override
    public List<ResourcesModuleTreeValue> queryByTree(int resourcesType) {
        return resourcesService.queryByTree(resourcesType);
    }

    @Override
    public List<ResourcesModulePermissionTreeValue> queryByPermissionTree() {
        return resourcesService.queryByPermissionTree();
    }

    @Override
    public List<ResourcesModulePermissionTreeValue> queryByPermissionTree(int resourcesType) {
        return resourcesService.queryByPermissionTree(resourcesType);
    }

    @Override
    public List<ResourcesModuleTreeValue> queryByMenuTree(int resourcesType) {
        return resourcesService.queryByMenuTree(resourcesType);
    }

    @Override
    public String getModuleName() {
        return "资源";
    }

    @Override
    public List<ResourcesModuleTypeDto> queryResourcesTypeList() {
        EntityQueryWrapper<ResourcesModuleType> query = new EntityQueryWrapper<>(ResourcesModuleType.class);
        query.orderBy(ResourcesModuleType.FIELD_ID);
        List<ResourcesModuleType> entitys = this.resourcesModuleTypeRepository.selectForList(query);
        return AutoMapUtils.mapForList(entitys, ResourcesModuleTypeDto.class);
    }

    @Override
    public ResourcesModuleTypeDto queryResourcesTypeById(long id) {
        ResourcesModuleType entity = this.resourcesModuleTypeRepository.get(id);
        return AutoMapUtils.map(entity, ResourcesModuleTypeDto.class);
    }

    @Override
    public boolean existResourcesType(long id) {
        EntityQueryWrapper<ResourcesModuleType> query = new EntityQueryWrapper<>(ResourcesModuleType.class);
        query.where().eq(ResourcesModuleType.FIELD_ID, id);
        return query.exist(this.resourcesModuleTypeRepository);
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
