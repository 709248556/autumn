package com.autumn.zero.ueditor.upload;


import com.autumn.util.StringUtils;
import com.autumn.zero.file.storage.application.services.FileUploadManager;
import com.autumn.zero.ueditor.model.ExecuteMultiStateResult;
import com.autumn.zero.ueditor.model.ExecuteStateResult;
import com.autumn.zero.ueditor.model.UeditorActionInfo;
import com.autumn.zero.ueditor.model.UeditorActionType;

import javax.servlet.http.HttpServletRequest;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;

/**
 * 图片抓取器上传
 *
 * @Description TODO
 * @Author 老码农
 * @Date 2019-10-16 16:23
 */
public class ImageHunterUploader extends AbstractUploader {

    /**
     *
     */
    private static final Map<String, String> MIME_TYPE = new HashMap<String, String>() {
        private static final long serialVersionUID = -7284715778432755192L;

        {
            put("image/gif", ".gif");
            put("image/jpeg", ".jpg");
            put("image/jpg", ".jpg");
            put("image/png", ".png");
            put("image/bmp", ".bmp");
        }
    };

    /**
     * @param fileUploadManager
     * @param fileAttachmentType
     */
    public ImageHunterUploader(FileUploadManager fileUploadManager, int fileAttachmentType) {
        super(fileUploadManager, fileAttachmentType);
    }

    @Override
    public ExecuteStateResult save(HttpServletRequest request, UeditorActionType actionType, UeditorActionInfo actionInfo) {
        String[] list = request.getParameterValues(actionInfo.getFieldName());
        if (list == null || list.length == 0) {
            return ExecuteStateResult.fail("没有任何url地址。");
        }
        ExecuteMultiStateResult state = new ExecuteMultiStateResult();
        state.setState(ExecuteMultiStateResult.STATE_SUCCESS);
        for (String url : list) {
            state.getList().add(captureRemoteData(url, actionType, actionInfo));
        }
        return state;
    }


    private ExecuteStateResult captureRemoteData(String urlStr, UeditorActionType actionType, UeditorActionInfo actionInfo) {
        try {
            URL url = new URL(urlStr);
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setInstanceFollowRedirects(true);
            connection.setUseCaches(true);
            if (HttpURLConnection.HTTP_OK != connection.getResponseCode()) {
                return ExecuteStateResult.fail("无法下载远程图片。");
            }
            String suffix = MIME_TYPE.get(connection.getContentType());
            if (StringUtils.isNullOrBlank(suffix)) {
                return ExecuteStateResult.fail("不支持的文件类型。");
            }
            suffix = suffix.replace(".", "").toLowerCase();
            if (actionInfo.getLimitExtensions() != null
                    && actionInfo.getLimitExtensions().size() > 0) {
                if (actionInfo.getLimitExtensions().contains(suffix)) {
                    return ExecuteStateResult.fail("不支持的文件类型。");
                }
            }
            if (actionInfo.getMaxSize() > 0 && connection.getContentLength() > actionInfo.getMaxSize()) {
                return ExecuteStateResult.fail("上传内容超过限制。");
            }
            return this.saveStream(connection.getInputStream(),
                    this.createImageOriginalName(actionInfo),
                    connection.getContentLength(), actionInfo);
        } catch (Exception e) {
            this.log.error("上传 html ueditor FileStream 文件出错[" + actionType.toString() + "]：" + e.getMessage(), e);
            return ExecuteStateResult.fail("上传失败。");
        }
    }

}
