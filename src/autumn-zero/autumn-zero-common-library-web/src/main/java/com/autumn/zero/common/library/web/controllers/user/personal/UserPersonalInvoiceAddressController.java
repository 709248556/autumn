package com.autumn.zero.common.library.web.controllers.user.personal;

import com.autumn.zero.common.library.application.dto.personal.UserInvoiceAddressDto;
import com.autumn.zero.common.library.application.services.personal.UserInvoiceAddressService;
import io.swagger.annotations.Api;
import org.apache.shiro.authz.annotation.RequiresUser;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * 用户发票地址管理
 * <p>
 * </p>
 *
 * @description TODO
 * @author: 老码农
 * @create: 2020-03-28 13:29
 **/
@RestController
@RequestMapping("/user/personal/address/invoice")
@Api(tags = "用户发票地址管理")
@RequiresUser
public class UserPersonalInvoiceAddressController
        extends AbstractUserPresonalDefaultController<UserInvoiceAddressDto, UserInvoiceAddressDto, UserInvoiceAddressDto, UserInvoiceAddressDto, UserInvoiceAddressService> {

    public UserPersonalInvoiceAddressController(UserInvoiceAddressService service) {
        super(service);
    }

}
