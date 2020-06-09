package com.autumn.zero.ueditor.action;

import com.autumn.zero.ueditor.model.UeditorActionType;
import com.autumn.zero.ueditor.model.UeditorConfig;

import javax.servlet.http.HttpServletRequest;

/**
 * 上传执行器
 * <p>
 * </p>
 *
 * @description TODO
 * @author: 老码农
 * @create: 2020-03-24 17:41
 **/
public interface UploadAction {

    /**
     * 执行
     *
     * @param request    请求
     * @param actionType 执行类型
     * @param config     配置
     * @return
     */
    String execute(HttpServletRequest request, UeditorActionType actionType, UeditorConfig config);
}
