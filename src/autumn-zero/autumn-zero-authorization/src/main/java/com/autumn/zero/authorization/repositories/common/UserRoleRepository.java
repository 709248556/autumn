package com.autumn.zero.authorization.repositories.common;

import org.springframework.stereotype.Repository;

import com.autumn.domain.repositories.DefaultEntityRepository;
import com.autumn.zero.authorization.entities.common.UserRole;

/**
 * 用户角色仓储
 * 
 * @author 老码农 2018-11-29 21:07:33
 */
@Repository
public interface UserRoleRepository extends DefaultEntityRepository<UserRole> {

}
