package com.autumn.zero.common.library.repositories.personal;

import com.autumn.domain.repositories.DefaultEntityRepository;
import com.autumn.zero.common.library.entities.personal.UserPersonalInvoiceAddress;
import org.springframework.stereotype.Repository;

/**
 * 用户发票地址
 * <p>
 * </p>
 *
 * @description TODO
 * @author: 老码农
 * @create: 2020-03-26 16:55
 **/
@Repository
public interface UserInvoiceAddressRepository extends DefaultEntityRepository<UserPersonalInvoiceAddress> {

}