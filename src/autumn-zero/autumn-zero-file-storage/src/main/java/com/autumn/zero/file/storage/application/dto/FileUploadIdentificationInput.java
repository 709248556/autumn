package com.autumn.zero.file.storage.application.dto;

import com.autumn.zero.file.storage.services.vo.DefaultUseUploadFileRequest;

import java.util.List;

/**
 * 上传文件标识输入
 *
 * @author 老码农 2019-03-20 09:51:43 标识类型
 */
public interface FileUploadIdentificationInput extends FileUploadInput {

    /**
     * 获取上传的文件集合
     *
     * @return
     */
    List<DefaultUseUploadFileRequest> getUploadFiles();

    /**
     * 设置上传的文件集合
     *
     * @param uploadFiles
     */
    void setUploadFiles(List<DefaultUseUploadFileRequest> uploadFiles);
}
