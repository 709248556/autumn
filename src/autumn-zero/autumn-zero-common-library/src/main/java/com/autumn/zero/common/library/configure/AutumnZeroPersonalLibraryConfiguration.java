package com.autumn.zero.common.library.configure;

import com.autumn.zero.common.library.application.services.personal.UserInvoiceAddressService;
import com.autumn.zero.common.library.application.services.personal.UserReceivingAddressService;
import com.autumn.zero.common.library.application.services.personal.impl.UserInvoiceAddressServiceImpl;
import com.autumn.zero.common.library.application.services.personal.impl.UserReceivingAddressServiceImpl;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * <p>
 * </p>
 *
 * @description TODO
 * @author: 老码农
 * @create: 2020-03-26 21:10
 **/
@Configuration
public class AutumnZeroPersonalLibraryConfiguration {

    /**
     * 用户发票地址管理服务
     *
     * @return
     */
    @Bean
    @ConditionalOnMissingBean(UserInvoiceAddressService.class)
    public UserInvoiceAddressService zeroUserInvoiceAddressService() {
        return new UserInvoiceAddressServiceImpl();
    }

    /**
     * 用户收货地址管理服务
     *
     * @return
     */
    @Bean
    @ConditionalOnMissingBean(UserReceivingAddressService.class)
    public UserReceivingAddressService zeroUserReceivingAddressService() {
        return new UserReceivingAddressServiceImpl();
    }
}
