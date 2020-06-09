package com.autumn.zero.common.library.web.controllers.user.personal;

import com.autumn.zero.common.library.application.dto.personal.UserReceivingAddressInput;
import com.autumn.zero.common.library.application.dto.personal.UserReceivingAddressOutput;
import com.autumn.zero.common.library.application.services.personal.UserReceivingAddressService;
import io.swagger.annotations.Api;
import org.apache.shiro.authz.annotation.RequiresUser;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * 用户收货地址管理
 * <p>
 * </p>
 *
 * @description TODO
 * @author: 老码农
 * @create: 2020-03-28 13:29
 **/
@RestController
@RequestMapping("/user/personal/address/receiving")
@Api(tags = "用户收货地址管理")
@RequiresUser
public class UserPersonalReceivingAddressController
        extends AbstractUserPresonalDefaultController<UserReceivingAddressInput, UserReceivingAddressInput, UserReceivingAddressOutput, UserReceivingAddressOutput, UserReceivingAddressService> {

    public UserPersonalReceivingAddressController(UserReceivingAddressService service) {
        super(service);
    }
}
