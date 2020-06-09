package com.autumn.zero.authorization.repositories.common.query;

import com.autumn.mybatis.mapper.EntityMapper;
import com.autumn.mybatis.mapper.annotation.MapperViewSelect;
import com.autumn.security.constants.RoleStatusConstants;
import com.autumn.zero.authorization.entities.common.query.UserPermissionQuery;
import org.springframework.stereotype.Repository;

/**
 * 用户权限查询仓储
 *
 * @author 老码农 2018-12-07 18:23:53
 */
@Repository
@MapperViewSelect("SELECT a.user_id,a.resources_id,a.name,a.is_granted FROM sys_user_permission AS a"
        + " UNION ALL "
        + " SELECT b.user_id,a.resources_id,a.name,a.is_granted FROM sys_role_permission AS a"
        + " INNER JOIN sys_user_role AS b ON b.role_id = a.role_id"
        + " INNER JOIN sys_role AS r on r.id = b.role_id AND r.status = " + RoleStatusConstants.STATUS_ENABLE)
public interface UserPermissionQueryRepository extends EntityMapper<UserPermissionQuery> {

}
