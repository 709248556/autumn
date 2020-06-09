package com.autumn.zero.common.library.application.callback.impl;

import com.autumn.domain.values.IntegerConstantItemValue;
import com.autumn.zero.common.library.application.callback.CommonDataDictionaryCallback;

import java.util.ArrayList;
import java.util.Collection;

/**
 * 默认公共字典数据回调
 *
 * @Description TODO
 * @Author 老码农
 * @Date 2019-07-25 19:24
 */
public class DefaultCommonDataDictionaryCallback extends AbstractFileUploadCallback implements CommonDataDictionaryCallback {

    @Override
    public int getFileUploadAttachmentType() {
        return FILE_UPLOAD_ATTACHMENT_TYPE_BEGIN + 1;
    }

    @Override
    public String getModuleId() {
        return "CommonDataDictionary";
    }

    @Override
    public String getModuleName() {
        return "公共数据字典";
    }

    @Override
    public boolean exist(Integer value) {
        return true;
    }

    @Override
    public IntegerConstantItemValue getItem(Integer value) {
        return new IntegerConstantItemValue(value, this.getName(value), "");
    }

    @Override
    public String getName(Integer value) {
        return "默认";
    }

    @Override
    public Collection<IntegerConstantItemValue> items() {
        return new ArrayList<>();
    }
}
