package com.autumn.zero.ueditor.upload;

import com.autumn.util.Base64Utils;
import com.autumn.util.StringUtils;
import com.autumn.zero.file.storage.application.services.FileUploadManager;
import com.autumn.zero.ueditor.model.ExecuteStateResult;
import com.autumn.zero.ueditor.model.UeditorActionInfo;
import com.autumn.zero.ueditor.model.UeditorActionType;

import javax.servlet.http.HttpServletRequest;
import java.io.ByteArrayInputStream;

/**
 * Base 64 上传
 *
 * @Description TODO
 * @Author 老码农
 * @Date 2019-10-16 15:03
 */
public class Base64Uploader extends AbstractUploader {

    /**
     * @param fileUploadManager
     * @param fileAttachmentType
     */
    public Base64Uploader(FileUploadManager fileUploadManager, int fileAttachmentType) {
        super(fileUploadManager, fileAttachmentType);
    }

    @Override
    public ExecuteStateResult save(HttpServletRequest request, UeditorActionType actionType, UeditorActionInfo actionInfo) {
        String content = request.getParameter(actionInfo.getFieldName());
        if (StringUtils.isNullOrBlank(content)) {
            return ExecuteStateResult.fail("Base64 值为空。");
        }
        try {
            byte[] data = Base64Utils.decodeToBytes(content);
            if (actionInfo.getMaxSize() > 0 && data.length > actionInfo.getMaxSize()) {
                return ExecuteStateResult.fail("上传内容超过限制。");
            }
            return this.saveStream(new ByteArrayInputStream(data),
                    this.createImageOriginalName(actionInfo), (long) data.length, actionInfo);
        } catch (Exception err) {
            this.log.error("上传 html ueditor Base64 文件出错[" + actionType.toString() + "]：" + err.getMessage(), err);
            return ExecuteStateResult.fail("上传失败。");
        }
    }

}
