package com.autumn.zero.common.library.application.callback.impl;

import com.autumn.domain.values.IntegerConstantItemValue;
import com.autumn.zero.common.library.application.callback.BusinessAgreementCallback;

import java.util.ArrayList;
import java.util.Collection;

/**
 * 默认用户协议回调
 * <p>
 * </p>
 *
 * @description: TODO
 * @author: 老码农
 * @create: 2019-12-06 17:32
 **/
public class DefaultBusinessAgreementCallback implements BusinessAgreementCallback {

    @Override
    public boolean exist(Integer value) {
        return true;
    }

    @Override
    public IntegerConstantItemValue getItem(Integer value) {
        return new IntegerConstantItemValue(value, "用户协议", "", value);
    }

    @Override
    public String getName(Integer value) {
        return "用户协议";
    }

    @Override
    public Collection<IntegerConstantItemValue> items() {
        return new ArrayList<>(1);
    }

    @Override
    public String getModuleId() {
        return "User-Agreement";
    }

    @Override
    public String getModuleName() {
        return "用户协议";
    }
}
