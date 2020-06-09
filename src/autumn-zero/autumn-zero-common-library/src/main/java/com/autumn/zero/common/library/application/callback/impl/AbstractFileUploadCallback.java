package com.autumn.zero.common.library.application.callback.impl;

import com.autumn.zero.common.library.application.callback.FileUploadCallback;

import java.util.HashSet;
import java.util.Set;

/**
 * TODO
 *
 * @Description TODO
 * @Author 老码农
 * @Date 2019-07-25 19:32
 */
public abstract class AbstractFileUploadCallback implements FileUploadCallback {

    private Set<String> fileUploadLimitExtensions = new HashSet<>();

    @Override
    public String getFileUploadStartPath() {
        return "";
    }

    @Override
    public Set<String> getFileUploadLimitExtensions() {
        return this.fileUploadLimitExtensions;
    }

    @Override
    public Long getFileUploadLimitSize() {
        return DEFAULT_FILE_UPLOAD_LIMIT_SIZE;
    }
}
