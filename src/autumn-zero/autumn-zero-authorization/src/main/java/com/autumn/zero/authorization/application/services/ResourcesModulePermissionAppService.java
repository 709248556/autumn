package com.autumn.zero.authorization.application.services;

import com.autumn.application.service.EditApplicationService;
import com.autumn.zero.authorization.application.dto.modules.PermissionNameCheckInput;
import com.autumn.zero.authorization.application.dto.modules.ResourcesInput;
import com.autumn.zero.authorization.application.dto.modules.ResourcesModulePermissionDto;
import com.autumn.zero.file.storage.application.services.FileExportAppService;

/**
 * 资源权限模块应用服务
 * 
 * @author 老码农 2018-12-09 13:28:47
 */
public interface ResourcesModulePermissionAppService extends
		EditApplicationService<Long, ResourcesModulePermissionDto, ResourcesModulePermissionDto, ResourcesModulePermissionDto, ResourcesModulePermissionDto>, FileExportAppService {

	/**
	 * 添加默认查询权限
	 * 
	 * @param input 输入
	 */
	void addDefaultQueryPermission(ResourcesInput input);

	/**
	 * 添加默认编辑权限
	 * 
	 * @param input 输入
	 */
	void addDefaultEditPermission(ResourcesInput input);

	/**
	 * 是否存在权限名称
	 * 
	 * @param input 输入
	 * @return
	 */
	boolean existPermissionName(PermissionNameCheckInput input);

}
