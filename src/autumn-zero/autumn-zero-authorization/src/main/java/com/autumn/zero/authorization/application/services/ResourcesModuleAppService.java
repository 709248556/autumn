package com.autumn.zero.authorization.application.services;

import com.autumn.application.service.EditApplicationService;
import com.autumn.zero.authorization.application.dto.modules.ResourcesModuleTypeDto;
import com.autumn.zero.authorization.values.ResourcesModulePermissionTreeValue;
import com.autumn.zero.authorization.values.ResourcesModuleTreeValue;
import com.autumn.zero.authorization.values.ResourcesModuleValue;
import com.autumn.zero.file.storage.application.services.FileExportAppService;

import java.util.List;

/**
 * 资源模块应用服务
 *
 * @author 老码农 2018-12-08 23:23:32
 */
public interface ResourcesModuleAppService extends
        EditApplicationService<String, ResourcesModuleValue, ResourcesModuleValue, ResourcesModuleValue, ResourcesModuleValue>, FileExportAppService {

    /**
     * 添加默认系统模块
     *
     * @return
     */
    boolean addDefaultSystemModule();

    /**
     * 查询所有模块树
     *
     * @return
     */
    List<ResourcesModuleTreeValue> queryByTree();

    /**
     * 查询指定资源模块树
     *
     * @param resourcesType 资源类型
     * @return
     */
    List<ResourcesModuleTreeValue> queryByTree(int resourcesType);

    /**
     * 查询所有权限树
     *
     * @return
     */
    List<ResourcesModulePermissionTreeValue> queryByPermissionTree();

    /**
     * 查询指定资源权限树
     *
     * @param resourcesType 资源类型
     * @return
     */
    List<ResourcesModulePermissionTreeValue> queryByPermissionTree(int resourcesType);

    /**
     * 获取查询菜单树
     *
     * @param resourcesType 资源类型
     * @return
     */
    List<ResourcesModuleTreeValue> queryByMenuTree(int resourcesType);

    /**
     * 查询资源类型列表
     *
     * @return
     */
    List<ResourcesModuleTypeDto> queryResourcesTypeList();

    /**
     * 根据id查询资源类型
     *
     * @param id 主键
     * @return
     */
    ResourcesModuleTypeDto queryResourcesTypeById(long id);

    /**
     * 是否存在资源类型
     *
     * @param id 主键
     * @return
     */
    boolean existResourcesType(long id);

}
