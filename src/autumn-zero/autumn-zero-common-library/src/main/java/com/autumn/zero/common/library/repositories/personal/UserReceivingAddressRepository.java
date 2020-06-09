package com.autumn.zero.common.library.repositories.personal;

import com.autumn.domain.repositories.DefaultEntityRepository;
import com.autumn.zero.common.library.entities.personal.UserPersonalReceivingAddress;
import org.springframework.stereotype.Repository;

/**
 * 用户收货地址仓储
 * <p>
 * </p>
 *
 * @description TODO
 * @author: 老码农
 * @create: 2020-03-26 16:29
 **/
@Repository
public interface UserReceivingAddressRepository extends DefaultEntityRepository<UserPersonalReceivingAddress> {

}

