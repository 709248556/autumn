package com.autumn.zero.authorization.repositories.defaulted.query;

import com.autumn.domain.repositories.DefaultEntityRepository;
import com.autumn.mybatis.mapper.annotation.MapperViewSelect;
import com.autumn.zero.authorization.entities.defaulted.query.DefaultUserByRoleQuery;
import org.springframework.stereotype.Repository;

/**
 * 用户角色查询仓储
 *
 * @author 老码农 2018-12-07 15:13:20
 */
@Repository
@MapperViewSelect("SELECT a.id,b.role_id,a.user_name,a.nick_name,a.phone_number,a.email_address,"
        + "a.sex,a.head_portrait_path,a.birthday,a.status,a.is_sys_user,a.gmt_create,a.gmt_modified"
        + " FROM sys_user AS a INNER JOIN sys_user_role AS b on a.id = b.user_id")
public interface UserByRoleQueryRepository extends DefaultEntityRepository<DefaultUserByRoleQuery> {

}
