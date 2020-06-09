package com.autumn.zero.common.library.application.services.personal.impl;

import com.autumn.exception.ExceptionUtils;
import com.autumn.mybatis.wrapper.EntityQueryWrapper;
import com.autumn.zero.common.library.application.dto.common.region.RegionOutput;
import com.autumn.zero.common.library.application.dto.personal.UserReceivingAddressInput;
import com.autumn.zero.common.library.application.dto.personal.UserReceivingAddressOutput;
import com.autumn.zero.common.library.application.services.common.RegionAppService;
import com.autumn.zero.common.library.application.services.personal.UserReceivingAddressService;
import com.autumn.zero.common.library.entities.personal.UserPersonalReceivingAddress;
import com.autumn.zero.common.library.entities.personal.query.UserPersonalReceivingAddressQuery;
import com.autumn.zero.common.library.repositories.personal.UserReceivingAddressRepository;
import com.autumn.zero.common.library.repositories.personal.query.UserReceivingAddressQueryRepository;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * 用户收货地址服务实现
 * <p>
 * </p>
 *
 * @description TODO
 * @author: 老码农
 * @create: 2020-03-26 17:09
 **/
public class UserReceivingAddressServiceImpl
        extends AbstractUserPresonalDefaultEditAppService<UserPersonalReceivingAddress,
        UserReceivingAddressRepository,
        UserPersonalReceivingAddressQuery,
        UserReceivingAddressQueryRepository,
        UserReceivingAddressInput,
        UserReceivingAddressInput,
        UserReceivingAddressOutput,
        UserReceivingAddressOutput>
        implements UserReceivingAddressService {

    @Autowired
    private RegionAppService regionAppService;

    public UserReceivingAddressServiceImpl() {

    }

    /**
     * 编辑检查
     *
     * @param input 输入
     */
    private void editCheck(UserReceivingAddressInput input) {
        RegionOutput regionOutput = regionAppService.queryById(input.getRegionId());
        if (regionOutput == null) {
            ExceptionUtils.throwValidationException("行政区id无效。");
        }
        if (regionOutput.getLevel() != 3) {
            ExceptionUtils.throwValidationException("行政区必须选择县级。");
        }
    }

    @Override
    protected UserPersonalReceivingAddress addBefore(UserReceivingAddressInput input, EntityQueryWrapper<UserPersonalReceivingAddress> query) {
        this.editCheck(input);
        return super.addBefore(input, query);
    }

    @Override
    protected void updateBefore(UserReceivingAddressInput input, UserPersonalReceivingAddress entity, EntityQueryWrapper<UserPersonalReceivingAddress> query) {
        this.editCheck(input);
        super.updateBefore(input, entity, query);
    }

    @Override
    public String getModuleName() {
        return "用户收货地址";
    }
}