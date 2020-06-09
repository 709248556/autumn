package com.autumn.zero.ueditor.upload;

import com.autumn.util.DateUtils;
import com.autumn.util.StringUtils;
import com.autumn.zero.file.storage.application.dto.FullFileInput;
import com.autumn.zero.file.storage.application.services.FileUploadManager;
import com.autumn.zero.file.storage.services.vo.FileAttachmentInformationResponse;
import com.autumn.zero.ueditor.model.UeditorActionInfo;
import com.autumn.zero.ueditor.model.ExecuteStateResult;
import com.autumn.zero.ueditor.model.UploadExecuteResult;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import java.io.InputStream;
import java.util.Date;

/**
 * 上传抽象
 *
 * @Description TODO
 * @Author 老码农
 * @Date 2019-10-16 15:04
 */
public abstract class AbstractUploader implements Uploader {

    /**
     * 获取文件上传管理
     */
    protected final FileUploadManager fileUploadManager;

    private final int fileAttachmentType;

    /**
     * 日志
     */
    protected final Log log = LogFactory.getLog(this.getClass());

    /**
     * @param fileUploadManager
     * @param fileAttachmentType
     */
    public AbstractUploader(FileUploadManager fileUploadManager, int fileAttachmentType) {
        this.fileUploadManager = fileUploadManager;
        this.fileAttachmentType = fileAttachmentType;
    }

    @Override
    public int getFileAttachmentType() {
        return this.fileAttachmentType;
    }

    /**
     * 创建图片文件名称
     *
     * @param actionInfo
     * @return
     */
    protected String createImageOriginalName(UeditorActionInfo actionInfo) {
        return DateUtils.dateFormat(new Date(), "yyyyMMddHHmmssSSS") + ".jpg";
    }


    /**
     * 保存流
     *
     * @param inputStream  输入流
     * @param originalName 文件名称
     * @param orginalSize  文件大小
     * @param actionInfo   活动信息
     * @return
     * @throws Exception
     */
    protected ExecuteStateResult saveStream(InputStream inputStream, String originalName, long orginalSize,
                                            UeditorActionInfo actionInfo) throws Exception {
        FullFileInput fuleInput = new FullFileInput();
        fuleInput.setFileAttachmentType(this.getFileAttachmentType());
        fuleInput.setInputStream(inputStream);
        fuleInput.setLimitExtensions(actionInfo.getLimitExtensions());
        fuleInput.setLimitSize(actionInfo.getMaxSize());
        fuleInput.setOriginalFilename(originalName);
        fuleInput.setOriginalFileSize(orginalSize);
        fuleInput.setModuleName("html编辑器");
        fuleInput.setUrlPath(StringUtils.removeStart(actionInfo.getUploadPath(), '/'));
        FileAttachmentInformationResponse informationResponse = fileUploadManager.saveUploadFile(fuleInput);
        return UploadExecuteResult.success(informationResponse.getAccessUrlPath(),
                informationResponse.getFileFriendlyName(),
                originalName,
                "." + informationResponse.getExtensionName()
        );
    }

}
