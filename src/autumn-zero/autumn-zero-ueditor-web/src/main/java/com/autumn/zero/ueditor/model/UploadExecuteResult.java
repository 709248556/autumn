package com.autumn.zero.ueditor.model;

import com.alibaba.fastjson.annotation.JSONField;
import lombok.Getter;
import lombok.Setter;

/**
 * Html 编辑器上传响应
 *
 * @Description TODO
 * @Author 老码农
 * @Date 2019-10-15 13:23
 */
@Getter
@Setter
public class UploadExecuteResult extends ExecuteStateResult {

    private static final long serialVersionUID = 7451836976963048682L;

    /**
     * 成功
     *
     * @param url      url 地址
     * @param title    标题
     * @param original 文件
     * @param type     类型
     * @return
     */
    public static UploadExecuteResult success(String url, String title, String original, String type) {
        return createResponse(STATE_SUCCESS, url, title, original, type);
    }

    /**
     * 创建响应
     *
     * @param state    状态
     * @param url      url 地址
     * @param title    标题
     * @param original 文件
     * @param type     类型
     * @return
     */
    private static UploadExecuteResult createResponse(String state, String url, String title, String original, String type) {
        UploadExecuteResult response = new UploadExecuteResult();
        response.setState(state);
        response.setUrl(url == null ? "" : url.trim());
        response.setTitle(title == null ? "" : title.trim());
        response.setOriginal(original == null ? "" : original.trim());
        response.setType(type == null ? "" : type.trim());
        return response;
    }

    /**
     * url
     */
    @JSONField(ordinal = 2)
    private String url;

    /**
     * 标题
     */
    @JSONField(ordinal = 3)
    private String title;

    /**
     * 文件
     */
    @JSONField(ordinal = 4)
    private String original;

    /**
     * 类型
     */
    @JSONField(ordinal = 5)
    private String type;
}
