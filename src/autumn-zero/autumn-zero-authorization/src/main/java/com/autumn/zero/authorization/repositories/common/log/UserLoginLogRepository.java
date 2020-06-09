package com.autumn.zero.authorization.repositories.common.log;

import org.springframework.stereotype.Repository;

import com.autumn.domain.repositories.DefaultEntityRepository;
import com.autumn.zero.authorization.entities.common.log.UserLoginLog;

/**
 * 用户登录日志
 * 
 * @author 老码农 2018-11-29 22:29:08
 */
@Repository
public interface UserLoginLogRepository extends DefaultEntityRepository<UserLoginLog> {

}
