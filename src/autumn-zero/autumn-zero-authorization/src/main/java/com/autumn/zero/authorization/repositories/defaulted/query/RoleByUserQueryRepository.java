package com.autumn.zero.authorization.repositories.defaulted.query;

import com.autumn.domain.repositories.DefaultEntityRepository;
import com.autumn.mybatis.mapper.annotation.MapperViewSelect;
import com.autumn.zero.authorization.entities.defaulted.query.DefaultRoleByUserQuery;
import org.springframework.stereotype.Repository;

/**
 * 角色用户查询仓储
 * 
 * @author 老码农 2018-12-07 15:11:40
 */
@Repository
@MapperViewSelect("SELECT a.id,a.sort_id,a.is_sys_role,b.user_id,a.name,a.status,a.is_default,a.summary,a.gmt_create,a.gmt_modified"
        + " FROM sys_role AS a INNER JOIN sys_user_role AS b on a.id = b.role_id")
public interface RoleByUserQueryRepository extends DefaultEntityRepository<DefaultRoleByUserQuery> {

}
