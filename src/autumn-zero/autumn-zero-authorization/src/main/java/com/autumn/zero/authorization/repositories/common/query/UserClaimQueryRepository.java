package com.autumn.zero.authorization.repositories.common.query;

import com.autumn.mybatis.mapper.EntityMapper;
import com.autumn.mybatis.mapper.annotation.MapperViewSelect;
import com.autumn.zero.authorization.entities.common.query.UserClaimQuery;
import org.springframework.stereotype.Repository;

/**
 * 用户声明查询仓储
 *
 * @author 老码农 2018-12-07 19:28:08
 */
@Repository
@MapperViewSelect("SELECT user_id,claim_type,claim_value FROM sys_user_claim"
        + " UNION ALL"
        + " SELECT b.user_id,a.claim_type,a.claim_value FROM sys_role_claim AS a"
        + " INNER JOIN sys_user_role AS b ON b.role_id = a.role_id")
public interface UserClaimQueryRepository extends EntityMapper<UserClaimQuery> {

}
