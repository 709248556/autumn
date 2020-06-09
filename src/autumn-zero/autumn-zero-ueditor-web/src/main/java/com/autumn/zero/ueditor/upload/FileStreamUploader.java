package com.autumn.zero.ueditor.upload;

import com.autumn.zero.file.storage.application.services.FileUploadManager;
import com.autumn.zero.ueditor.model.ExecuteStateResult;
import com.autumn.zero.ueditor.model.UeditorActionInfo;
import com.autumn.zero.ueditor.model.UeditorActionType;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

/**
 * 文件流上传
 *
 * @Description TODO
 * @Author 老码农
 * @Date 2019-10-16 14:57
 */
public class FileStreamUploader extends AbstractUploader {

    /**
     * @param fileUploadManager
     * @param fileAttachmentType
     */
    public FileStreamUploader(FileUploadManager fileUploadManager, int fileAttachmentType) {
        super(fileUploadManager, fileAttachmentType);
    }

    @Override
    public ExecuteStateResult save(HttpServletRequest request, UeditorActionType actionType, UeditorActionInfo actionInfo) {
        if (!(request instanceof MultipartHttpServletRequest)) {
            return ExecuteStateResult.fail("Request 无效。");
        }
        List<MultipartFile> files = ((MultipartHttpServletRequest) request).getFiles(actionInfo.getFieldName());
        if (files == null || files.size() == 0) {
            return ExecuteStateResult.fail("无上传文件数据。");
        }
        try {
            MultipartFile file = files.get(0);
            return this.saveStream(file.getInputStream(), file.getOriginalFilename(), file.getSize(), actionInfo);
        } catch (Exception err) {
            this.log.error("上传 html ueditor FileStream 文件出错[" + actionType.toString() + "]：" + err.getMessage(), err);
            return ExecuteStateResult.fail("上传失败。");
        }
    }
}
