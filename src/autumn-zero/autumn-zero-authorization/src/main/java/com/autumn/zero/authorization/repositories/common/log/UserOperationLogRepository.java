package com.autumn.zero.authorization.repositories.common.log;

import com.autumn.domain.repositories.DefaultEntityRepository;
import com.autumn.zero.authorization.entities.common.log.UserOperationLog;
import org.springframework.stereotype.Repository;

/**
 * 用户操作日志仓储
 *
 * @Description TODO
 * @Author 老码农
 * @Date 2019-07-28 2:17
 */
@Repository
public interface UserOperationLogRepository extends DefaultEntityRepository<UserOperationLog> {
}
