package com.autumn.zero.ueditor.upload;


import com.autumn.zero.ueditor.model.ExecuteStateResult;
import com.autumn.zero.ueditor.model.UeditorActionInfo;
import com.autumn.zero.ueditor.model.UeditorActionType;

import javax.servlet.http.HttpServletRequest;

/**
 * 上传器
 *
 * @Description TODO
 * @Author 老码农
 * @Date 2019-10-16 14:58
 */
public interface Uploader {

    /**
     * 获取附件类型
     *
     * @return
     */
    int getFileAttachmentType();

    /**
     * 保存
     *
     * @param request    请求
     * @param actionType 动作类型
     * @param actionInfo 活动信息
     * @return
     */
    ExecuteStateResult save(HttpServletRequest request, UeditorActionType actionType, UeditorActionInfo actionInfo);
}
