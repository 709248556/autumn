package com.autumn.zero.common.library.repositories.personal.query;

import com.autumn.domain.repositories.DefaultEntityRepository;
import com.autumn.mybatis.mapper.annotation.MapperViewSelect;
import com.autumn.zero.common.library.entities.personal.query.UserPersonalReceivingAddressQuery;
import org.springframework.stereotype.Repository;

/**
 * 用户收货地址查询仓储
 * <p>
 * </p>
 *
 * @description TODO
 * @author: 老码农
 * @create: 2020-03-26 17:37
 **/
@Repository
@MapperViewSelect("SELECT a.id,a.contacts_name,a.mobile_phone,a.region_id,a.details_address,a.is_default,a.label,a.user_id," +
        "b.code as region_code,b.name as region_name,b.full_id as region_full_id,b.full_code as region_full_code,b.full_name as region_full_name " +
        "FROM user_receiving_address AS a INNER JOIN common_region as b on a.region_id = b.id")
public interface UserReceivingAddressQueryRepository extends DefaultEntityRepository<UserPersonalReceivingAddressQuery> {

}
