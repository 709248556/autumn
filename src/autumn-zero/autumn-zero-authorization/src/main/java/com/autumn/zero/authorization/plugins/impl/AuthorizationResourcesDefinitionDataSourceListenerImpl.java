package com.autumn.zero.authorization.plugins.impl;

import com.autumn.exception.ExceptionUtils;
import com.autumn.mybatis.event.TableAutoDefinitionListener;
import com.autumn.mybatis.factory.DynamicDataSourceRouting;
import com.autumn.mybatis.metadata.EntityTable;
import com.autumn.mybatis.wrapper.EntityQueryWrapper;
import com.autumn.util.AutoMapUtils;
import com.autumn.util.StringUtils;
import com.autumn.zero.authorization.entities.common.modules.ResourcesModule;
import com.autumn.zero.authorization.entities.common.modules.ResourcesModulePermission;
import com.autumn.zero.authorization.entities.common.modules.ResourcesModuleType;
import com.autumn.zero.authorization.plugins.AuthorizationResourcesDataSourceListener;
import com.autumn.zero.authorization.plugins.ResourcesModulePlugin;
import com.autumn.zero.authorization.plugins.ResourcesModulePluginContext;
import com.autumn.zero.authorization.plugins.data.ResourcesModuleData;
import com.autumn.zero.authorization.plugins.data.ResourcesModulePermissionData;
import com.autumn.zero.authorization.repositories.common.modules.ResourcesModulePermissionRepository;
import com.autumn.zero.authorization.repositories.common.modules.ResourcesModuleRepository;
import com.autumn.zero.authorization.repositories.common.modules.ResourcesModuleTypeRepository;
import com.autumn.zero.authorization.services.AuthorizationServiceBase;
import com.autumn.zero.authorization.services.ResourcesService;
import com.autumn.zero.authorization.services.UserRoleDefinition;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import javax.sql.DataSource;
import java.util.*;

/**
 * 授权资源数所源监听
 * <p>
 * 自动创建资源、菜单、权限、超级管理员
 * </p>
 *
 * @description TODO
 * @author: 老码农
 * @create: 2020-04-18 20:18
 **/
public class AuthorizationResourcesDefinitionDataSourceListenerImpl
        implements AuthorizationResourcesDataSourceListener {

    private final ResourcesModulePluginContext resourcesModulePluginContext;

    public AuthorizationResourcesDefinitionDataSourceListenerImpl(ResourcesModulePluginContext resourcesModulePluginContext) {
        this.resourcesModulePluginContext = resourcesModulePluginContext;
    }

    @Autowired
    private ResourcesModuleTypeRepository typeRepository;

    @Autowired
    private ResourcesModuleRepository moduleRepository;

    @Autowired
    private ResourcesModulePermissionRepository permissionRepository;

    @Autowired
    private ResourcesService resourcesService;

    @Autowired
    private AuthorizationServiceBase authorizationService;

    @Transactional(rollbackFor = Exception.class)
    @Override
    public void init(DataSource dataSource, DynamicDataSourceRouting dataSourceRouting) {
        if (this.resourcesModulePluginContext.getPlugins().size() > 0) {
            EntityTable moduleTypeTable = EntityTable.getTable(ResourcesModuleType.class);
            EntityTable moduleTable = EntityTable.getTable(ResourcesModule.class);
            EntityTable modulePermissionTable = EntityTable.getTable(ResourcesModulePermission.class);
            if (dataSourceRouting.isIncludeTable(moduleTypeTable)
                    && dataSourceRouting.isIncludeTable(moduleTable)
                    && dataSourceRouting.isIncludeTable(modulePermissionTable)) {
                Map<Long, ResourcesModuleType> moduleTypeMap = this.getModuleTypeMap();
                int count = 0;
                if (moduleTypeMap.size() > 0) {
                    count += this.addResourcesModuleTypes(moduleTypeMap.values());
                }
                List<ResourcesModuleData> moduleData = getResourcesModuleDataList(moduleTypeMap);
                if (moduleData.size() > 0) {
                    for (ResourcesModuleData moduleDatum : moduleData) {
                        count += this.addResourcesModuleData(moduleDatum, null);
                    }
                }
                if (count > 0) {
                    this.resourcesService.clearAllCache();
                }
            }
        }
        this.checkOrCreateAdminAndRole();
    }

    /**
     * @param moduleTypes
     */
    private int addResourcesModuleTypes(Collection<ResourcesModuleType> moduleTypes) {
        int count = 0;
        for (ResourcesModuleType moduleType : moduleTypes) {
            EntityQueryWrapper<ResourcesModuleType> wrapper = new EntityQueryWrapper<>(ResourcesModuleType.class);
            wrapper.where().eq(ResourcesModuleType.FIELD_ID, moduleType.getId()).of().lockByUpdate();
            if (typeRepository.countByWhere(wrapper) == 0) {
                moduleType.forNullToDefault();
                typeRepository.insert(moduleType);
                count++;
            }
        }
        return count;
    }

    private int addResourcesModuleData(ResourcesModuleData moduleData, String parentId) {
        int count = 0;
        EntityQueryWrapper<ResourcesModule> wrapper = new EntityQueryWrapper<>(ResourcesModule.class);
        wrapper.where().eq(ResourcesModule.FIELD_ID, moduleData.getId()).of().lockByUpdate();
        if (moduleRepository.countByWhere(wrapper) == 0) {
            ResourcesModule module = AutoMapUtils.map(moduleData, ResourcesModule.class);
            module.setIsAuthorize(moduleData.isAuthorize());
            module.setIsMenu(moduleData.isMenu());
            module.setIsSysModule(moduleData.isSysModule());
            module.setParentId(parentId);
            if (StringUtils.isNullOrBlank(module.getCustomName())) {
                module.setCustomName(module.getName());
            }
            module.forNullToDefault();
            moduleRepository.insert(module);
            count++;
        }
        if (moduleData.getChildren() != null) {
            for (ResourcesModuleData child : moduleData.getChildren()) {
                child.setResourcesType(moduleData.getResourcesType());
                count += this.addResourcesModuleData(child, moduleData.getId());
            }
        }
        if (moduleData.getPermissions() != null) {
            for (ResourcesModulePermissionData permissionData : moduleData.getPermissions()) {
                EntityQueryWrapper<ResourcesModulePermission> permissionWrapper = new EntityQueryWrapper<>(ResourcesModulePermission.class);
                permissionWrapper
                        .where()
                        .eq(ResourcesModulePermission.FIELD_RESOURCES_ID, moduleData.getId())
                        .eq(ResourcesModulePermission.FIELD_NAME, permissionData.getName())
                        .of().lockByUpdate();

                if (permissionRepository.countByWhere(permissionWrapper) == 0) {
                    ResourcesModulePermission modulePermission = AutoMapUtils.map(permissionData, ResourcesModulePermission.class);
                    modulePermission.setResourcesId(moduleData.getId());
                    modulePermission.forNullToDefault();
                    permissionRepository.insert(modulePermission);
                    count++;
                }
            }
        }
        return count;
    }

    /**
     * 检查或创建超级管理员
     */
    private void checkOrCreateAdminAndRole() {
        UserRoleDefinition definition = this.authorizationService.createAdministratorDefinition();
        if (definition != null) {
            this.authorizationService.checkOrCreateUserAndRole(definition);
        }
    }

    private Map<Long, ResourcesModuleType> getModuleTypeMap() {
        Map<Long, ResourcesModuleType> moduleTypeMap = new HashMap<>(16);
        for (ResourcesModulePlugin plugin : this.resourcesModulePluginContext.getPlugins()) {
            List<ResourcesModuleType> moduleTypes = plugin.createModuleTypes();
            if (moduleTypes != null && moduleTypes.size() > 0) {
                for (ResourcesModuleType moduleType : moduleTypes) {
                    moduleType.valid();
                    if (!moduleTypeMap.containsKey(moduleType.getId())) {
                        moduleTypeMap.put(moduleType.getId(), moduleType);
                    }
                }
            }
        }
        return moduleTypeMap;
    }

    private List<ResourcesModuleData> getResourcesModuleDataList(Map<Long, ResourcesModuleType> moduleTypeMap) {
        List<ResourcesModuleData> dataList = new ArrayList<>(16);
        for (ResourcesModulePlugin plugin : this.resourcesModulePluginContext.getPlugins()) {
            List<ResourcesModuleData> modules = plugin.createModules();
            if (modules != null && modules.size() > 0) {
                for (ResourcesModuleData module : modules) {
                    module.valid();
                    if (!moduleTypeMap.containsKey((long) module.getResourcesType())) {
                        ExceptionUtils.throwConfigureException("不存在模板资源类型[" + module.getResourcesType() + "]，未在插件方法 createModuleTypes 中定义 。");
                    }
                    dataList.add(module);
                }
            }
        }
        return dataList;
    }

    @Override
    public void close(DataSource dataSource, DynamicDataSourceRouting dataSourceRouting) {

    }

    @Override
    public int getOrder() {
        return TableAutoDefinitionListener.BEAN_BEGIN_ORDER + 20;
    }

}
