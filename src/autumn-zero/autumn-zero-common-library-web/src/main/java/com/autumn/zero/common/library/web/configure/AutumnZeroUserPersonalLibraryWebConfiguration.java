package com.autumn.zero.common.library.web.configure;

import com.autumn.zero.common.library.application.services.personal.UserInvoiceAddressService;
import com.autumn.zero.common.library.application.services.personal.UserReceivingAddressService;
import com.autumn.zero.common.library.web.controllers.user.personal.UserPersonalInvoiceAddressController;
import com.autumn.zero.common.library.web.controllers.user.personal.UserPersonalReceivingAddressController;
import org.springframework.boot.autoconfigure.condition.ConditionalOnBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * 用户个人库Web配置
 * <p>
 * </p>
 *
 * @description TODO
 * @author: 老码农
 * @create: 2020-03-28 13:14
 **/
@Configuration
public class AutumnZeroUserPersonalLibraryWebConfiguration {

    /**
     * 用户收货地址管理
     *
     * @param service
     * @return
     */
    @Bean
    @ConditionalOnMissingBean(UserPersonalReceivingAddressController.class)
    @ConditionalOnBean(value = {UserReceivingAddressService.class})
    public UserPersonalReceivingAddressController autumnZeroUserPersonalReceivingAddressController(UserReceivingAddressService service) {
        return new UserPersonalReceivingAddressController(service);
    }

    /**
     * 用户发票地址管理
     *
     * @param service
     * @return
     */
    @Bean
    @ConditionalOnMissingBean(UserPersonalInvoiceAddressController.class)
    @ConditionalOnBean(value = {UserInvoiceAddressService.class})
    public UserPersonalInvoiceAddressController autumnZeroUserPersonalInvoiceAddressController(UserInvoiceAddressService service) {
        return new UserPersonalInvoiceAddressController(service);
    }

}
