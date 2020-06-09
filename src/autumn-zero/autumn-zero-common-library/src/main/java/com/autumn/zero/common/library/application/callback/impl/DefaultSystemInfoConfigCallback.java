package com.autumn.zero.common.library.application.callback.impl;

import com.autumn.zero.common.library.application.callback.SystemInfoConfigCallback;
import com.autumn.zero.file.storage.FileStorageUtils;

import java.util.Set;

/**
 * TODO
 *
 * @Description TODO
 * @Author 老码农
 * @Date 2019-07-31 4:23
 */
public class DefaultSystemInfoConfigCallback extends AbstractFileUploadCallback implements SystemInfoConfigCallback {

    @Override
    public int getFileUploadAttachmentType() {
        return FILE_UPLOAD_ATTACHMENT_TYPE_BEGIN + 2;
    }

    @Override
    public String getFileUploadStartPath() {
        return  "/config/system";
    }

    @Override
    public String getConfigType() {
        return "system_info";
    }

    @Override
    public String getModuleId() {
        return "system_info";
    }

    @Override
    public String getModuleName() {
        return "系统信息配置";
    }

    @Override
    public Set<String> getFileUploadLimitExtensions() {
        return FileStorageUtils.getImageUploadLimitExtensions();
    }


}
