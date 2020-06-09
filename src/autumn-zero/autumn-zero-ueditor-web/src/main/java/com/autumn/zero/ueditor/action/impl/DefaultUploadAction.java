package com.autumn.zero.ueditor.action.impl;

import com.autumn.zero.file.storage.application.services.FileUploadManager;
import com.autumn.zero.ueditor.model.*;
import com.autumn.zero.ueditor.upload.Base64Uploader;
import com.autumn.zero.ueditor.upload.FileStreamUploader;
import com.autumn.zero.ueditor.upload.ImageHunterUploader;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import javax.servlet.http.HttpServletRequest;

/**
 * 默认上传执行器
 *
 * @Description TODO
 * @Author 老码农
 * @Date 2019-10-16 17:28
 */
public class DefaultUploadAction extends AbstractUploadAction {

    /**
     * 默认附件类型
     */
    public static final int DEFAULT_FILE_ATTACHMENT_TYPE = 999999;

    /**
     * 获取文件上传管理
     */
    private final int fileAttachmentType;
    /**
     * Base格式上传
     */
    protected final Base64Uploader base64Uploader;
    /**
     * 二进制流上传
     */
    protected final FileStreamUploader fileStreamUploader;
    /**
     * 图片抓取器上传
     */
    protected final ImageHunterUploader imageHunterUploader;

    /**
     * 日志
     */
    protected final Log log = LogFactory.getLog(this.getClass());

    /**
     * @param fileUploadManager
     */
    public DefaultUploadAction(FileUploadManager fileUploadManager) {
        this(fileUploadManager, DEFAULT_FILE_ATTACHMENT_TYPE);
    }

    /**
     * @param fileUploadManager
     * @param fileAttachmentType
     */
    public DefaultUploadAction(FileUploadManager fileUploadManager, int fileAttachmentType) {
        this.fileAttachmentType = fileAttachmentType;
        this.base64Uploader = new Base64Uploader(fileUploadManager, fileAttachmentType);
        this.fileStreamUploader = new FileStreamUploader(fileUploadManager, fileAttachmentType);
        this.imageHunterUploader = new ImageHunterUploader(fileUploadManager, fileAttachmentType);
    }

    /**
     * @return
     */
    public int getFileAttachmentType() {
        return this.fileAttachmentType;
    }

    @Override
    protected ExecuteResult invoke(HttpServletRequest request, UeditorActionType actionType, boolean isBase64Format, UeditorConfig config) {
        switch (actionType.value()) {
            case UeditorActionType.CODE_CONFIG:
                return config;
            case UeditorActionType.CODE_UPLOAD_IMAGE:
                return this.save(request, actionType, config.getImageActionInfo(), isBase64Format);
            case UeditorActionType.CODE_UPLOAD_SCRAWL:
                return this.save(request, actionType, config.getScrawlActionInfo(), isBase64Format);
            case UeditorActionType.CODE_UPLOAD_VIDEO:
                return this.save(request, actionType, config.getVideoActionInfo(), isBase64Format);
            case UeditorActionType.CODE_UPLOAD_FILE:
                return this.save(request, actionType, config.getFileActionInfo(), isBase64Format);
            case UeditorActionType.CODE_CATCH_IMAGE:
                return this.imageHunterUploader.save(request, actionType, config.getCatcherActionInfo());
            default:
                return ExecuteStateResult.fail("action[" + actionType.getName() + "]不支持。");
        }
    }

    /**
     * 保存
     *
     * @param request
     * @param actionType     动作类型
     * @param actionInfo
     * @param isBase64Format
     * @return
     */
    private ExecuteStateResult save(HttpServletRequest request, UeditorActionType actionType, UeditorActionInfo actionInfo, boolean isBase64Format) {
        if (isBase64Format) {
            return this.base64Uploader.save(request, actionType, actionInfo);
        }
        return this.fileStreamUploader.save(request, actionType, actionInfo);
    }

}
