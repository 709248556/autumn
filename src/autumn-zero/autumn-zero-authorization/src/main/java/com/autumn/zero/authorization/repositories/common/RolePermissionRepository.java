package com.autumn.zero.authorization.repositories.common;

import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import com.autumn.domain.repositories.DefaultEntityRepository;
import com.autumn.zero.authorization.entities.common.RolePermission;

/**
 * 角色授权仓储
 *
 * @author 老码农 2018-11-29 21:07:33
 */
@Repository
public interface RolePermissionRepository extends DefaultEntityRepository<RolePermission> {

    /**
     * 根据角色与资源类型删除权限
     *
     * @param roleId        角色id
     * @param resourcesType 资源类型
     * @return
     */
    @Delete("DELETE p FROM sys_role_permission AS p INNER JOIN sys_res_module as m on p.resources_id = m.id " +
            "WHERE p.role_id = #{roleId} and m.resources_type = #{resourcesType}")
    int deleteByRoleAndResourcesType(@Param("roleId") long roleId, @Param("resourcesType") int resourcesType);

    /**
     * 根据角色与资源类型删除权限(PostgreSQL数据库)
     *
     * @param roleId        角色id
     * @param resourcesType 资源类型
     * @return
     */
    @Delete("DELETE FROM sys_role_permission AS p USING sys_res_module AS m WHERE p.resources_id = m.id " +
            "AND p.role_id = #{roleId} AND m.resources_type = #{resourcesType}")
    int deleteByRoleAndResourcesTypeToPostgre(@Param("roleId") long roleId, @Param("resourcesType") int resourcesType);
}
