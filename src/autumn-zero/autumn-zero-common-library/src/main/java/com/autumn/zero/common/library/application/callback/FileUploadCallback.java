package com.autumn.zero.common.library.application.callback;

import com.autumn.application.ApplicationModule;

import java.util.Set;

/**
 * 文件上传回调
 *
 * @Description TODO
 * @Author 老码农
 * @Date 2019-07-25 19:29
 */
public interface FileUploadCallback extends ApplicationModule {

    /**
     * 文件上传附件
     */
    public static final int FILE_UPLOAD_ATTACHMENT_TYPE_BEGIN = 100000;

    /**
     * 默认文件上传限制大小
     */
    public static final long DEFAULT_FILE_UPLOAD_LIMIT_SIZE = -1L;

    /**
     * 获取文件上传附件类型
     *
     * @return
     */
    int getFileUploadAttachmentType();

    /**
     * 获取文件上传开始路径
     *
     * @return
     */
    String getFileUploadStartPath();

    /**
     * 获取文件传限制的扩展名类型
     *
     * @return
     */
    Set<String> getFileUploadLimitExtensions();

    /**
     * 获取文件上传的限制大小
     *
     * @return
     */
    Long getFileUploadLimitSize();
}
