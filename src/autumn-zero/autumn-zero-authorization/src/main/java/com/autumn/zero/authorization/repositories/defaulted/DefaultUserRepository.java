package com.autumn.zero.authorization.repositories.defaulted;

import org.springframework.stereotype.Repository;

import com.autumn.domain.repositories.DefaultEntityRepository;
import com.autumn.zero.authorization.entities.defaulted.DefaultUser;

/**
 * 用户仓储
 * 
 * @author 老码农 2018-11-29 21:07:33
 */
@Repository
public interface DefaultUserRepository extends DefaultEntityRepository<DefaultUser> {

}
