package com.autumn.zero.file.storage.application.dto;

import com.autumn.zero.file.storage.services.vo.FileAttachmentInformationResponse;

import java.util.List;

/**
 * 上传文件附件输出
 *
 * @author 老码农 2019-03-20 09:44:59
 */
public interface FileUploadAttachmentOutput extends FileUploadOutput {

    /**
     * 获取上传的文件集合
     *
     * @return
     */
    List<FileAttachmentInformationResponse> getUploadFiles();

    /**
     * 设置上传的文件集合
     *
     * @param uploadFiles
     */
    void setUploadFiles(List<FileAttachmentInformationResponse> uploadFiles);
}
