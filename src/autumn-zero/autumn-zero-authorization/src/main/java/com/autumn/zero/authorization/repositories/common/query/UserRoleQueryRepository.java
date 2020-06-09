package com.autumn.zero.authorization.repositories.common.query;

import com.autumn.domain.repositories.DefaultEntityRepository;
import com.autumn.mybatis.mapper.annotation.MapperViewSelect;
import com.autumn.zero.authorization.entities.common.query.UserRoleQuery;
import org.springframework.stereotype.Repository;

/**
 * 用户角色查询仓储
 *
 * @author 老码农 2018-12-10 15:50:43
 */
@Repository
@MapperViewSelect("SELECT a.id,a.user_id,b.user_name,a.role_id,c.name AS role_name"
        + " FROM sys_user_role AS a INNER JOIN sys_user AS b ON a.user_id = b.id"
        + " INNER JOIN sys_role AS c ON a.role_id = c.id")
public interface UserRoleQueryRepository extends DefaultEntityRepository<UserRoleQuery> {

}
