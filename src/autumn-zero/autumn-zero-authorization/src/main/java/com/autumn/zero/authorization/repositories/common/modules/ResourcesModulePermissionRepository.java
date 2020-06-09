package com.autumn.zero.authorization.repositories.common.modules;

import com.autumn.domain.repositories.DefaultEntityRepository;
import com.autumn.zero.authorization.entities.common.modules.ResourcesModulePermission;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * 资源权限模块仓储
 * 
 * @author 老码农 2018-12-05 13:00:35
 */
@Repository
public interface ResourcesModulePermissionRepository extends DefaultEntityRepository<ResourcesModulePermission> {

	/**
	 * 根据资源类型查询资源模块
	 * 
	 * @param resourcesType
	 *            资源类型
	 * @return
	 */
	@Select("SELECT p.id,p.resources_id AS resourcesId,p.sort_id as sortId,p.name,p.friendly_name as friendlyName,p.permission_url as permissionUrl,p.summary "
			+ "FROM sys_res_module_permission AS p INNER JOIN sys_res_module as m on p.resources_id = m.id "
			+ "WHERE m.resources_type = #{resourcesType} ORDER BY m.name ASC, m.sort_id ASC,m.id ASC")
	List<ResourcesModulePermission> queryByResourcesTypes(@Param("resourcesType") int resourcesType);

}
