package com.autumn.zero.authorization.repositories.common;

import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import com.autumn.domain.repositories.DefaultEntityRepository;
import com.autumn.zero.authorization.entities.common.UserPermission;

/**
 * 用户授权仓储
 *
 * @author 老码农 2018-11-29 21:07:33
 */
@Repository
public interface UserPermissionRepository extends DefaultEntityRepository<UserPermission> {

    /**
     * 根据用户与资源类型删除权限
     *
     * @param userId        用户id
     * @param resourcesType 资源类型
     * @return
     */
    @Delete("DELETE p FROM sys_user_permission AS p INNER JOIN sys_res_module as m on p.resources_id = m.id " +
            "WHERE p.user_id = #{userId} and m.resources_type = #{resourcesType}")
    int deleteByUserAndResourcesType(@Param("userId") long userId, @Param("resourcesType") int resourcesType);

    /**
     * 根据用户与资源类型删除权限(PostgreSQL数据库)
     *
     * @param userId        用户id
     * @param resourcesType 资源类型
     * @return
     */
    @Delete("DELETE FROM sys_user_permission AS p USING sys_res_module AS m WHERE p.resources_id = m.id " +
            "and p.user_id = #{userId} and m.resources_type = #{resourcesType}")
    int deleteByUserAndResourcesTypeToPostgre(@Param("userId") long userId, @Param("resourcesType") int resourcesType);
}
