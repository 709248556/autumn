package com.autumn.zero.common.library.application.services.personal.impl;

import com.autumn.exception.ExceptionUtils;
import com.autumn.mybatis.wrapper.EntityQueryWrapper;
import com.autumn.zero.common.library.application.dto.personal.UserInvoiceAddressDto;
import com.autumn.zero.common.library.application.services.personal.UserInvoiceAddressService;
import com.autumn.zero.common.library.entities.personal.UserPersonalInvoiceAddress;
import com.autumn.zero.common.library.repositories.personal.UserInvoiceAddressRepository;

/**
 * 用户发票地址服务实现
 * <p>
 * </p>
 *
 * @description TODO
 * @author: 老码农
 * @create: 2020-03-26 17:09
 **/
public class UserInvoiceAddressServiceImpl
        extends AbstractUserPresonalDefaultEditAppService<UserPersonalInvoiceAddress,
        UserInvoiceAddressRepository,
        UserPersonalInvoiceAddress,
        UserInvoiceAddressRepository,
        UserInvoiceAddressDto,
        UserInvoiceAddressDto,
        UserInvoiceAddressDto,
        UserInvoiceAddressDto>
        implements UserInvoiceAddressService {

    public UserInvoiceAddressServiceImpl() {

    }

    @Override
    public String getModuleName() {
        return "用户发票地址";
    }

    private void checkName(UserInvoiceAddressDto input, boolean repeatCheck) {
        if (repeatCheck) {
            EntityQueryWrapper<UserPersonalInvoiceAddress> wrapper = new EntityQueryWrapper<>(UserPersonalInvoiceAddress.class);
            this.systemByEntityCriteria(wrapper);
            wrapper.where().eq(UserPersonalInvoiceAddress.FIELD_NAME, input.getName().trim()).of().lockByUpdate();
            if (this.getRepository().countByWhere(wrapper) > 0) {
                ExceptionUtils.throwValidationException("名称(抬头)[" + input.getName() + "]已重复。");
            }
        }
    }


    @Override
    protected UserPersonalInvoiceAddress addBefore(UserInvoiceAddressDto input, EntityQueryWrapper<UserPersonalInvoiceAddress> query) {
        this.checkName(input, true);
        return super.addBefore(input, query);
    }

    @Override
    protected void updateBefore(UserInvoiceAddressDto input, UserPersonalInvoiceAddress entity, EntityQueryWrapper<UserPersonalInvoiceAddress> query) {
        this.checkName(input, !entity.getName().trim().equalsIgnoreCase(input.getName().trim()));
        super.updateBefore(input, entity, query);
    }


}

