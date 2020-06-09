package com.autumn.zero.ueditor.action.impl;

import com.autumn.util.StringUtils;
import com.autumn.zero.ueditor.action.UploadAction;
import com.autumn.zero.ueditor.model.ExecuteResult;
import com.autumn.zero.ueditor.model.ExecuteStateResult;
import com.autumn.zero.ueditor.model.UeditorActionType;
import com.autumn.zero.ueditor.model.UeditorConfig;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import javax.servlet.http.HttpServletRequest;

/**
 * 上传执行器抽象
 * <p>
 * </p>
 *
 * @description TODO
 * @author: 老码农
 * @create: 2020-03-24 17:43
 **/
public abstract class AbstractUploadAction extends AbstractAction implements UploadAction {

    /**
     * 日志
     */
    private final Log log = LogFactory.getLog(this.getClass());

    /**
     * 默认Base64格式参数名称
     */
    public static final String DEFAULT_BASE_64_FORMAT_PARAMETER_NAME = "isBase64";

    /**
     * 获取日志
     *
     * @return
     */
    public final Log getLog() {
        return this.log;
    }


    /**
     * callback参数验证
     *
     * @param name 名称
     * @return
     */
    protected boolean validCallbackName(String name) {
        return name.matches("^[a-zA-Z_]+[\\w0-9_]*$");
    }

    /**
     * 获取Base64格式参数名称乐
     *
     * @return
     */
    public String getBase64FormatParameterName() {
        return DEFAULT_BASE_64_FORMAT_PARAMETER_NAME;
    }


    /**
     * 是否为Base64格式请求
     *
     * @param request
     * @return
     */
    protected boolean isBase64FormatRequest(HttpServletRequest request) {
        String isBase64Foramt = request.getParameter(this.getBase64FormatParameterName());
        if (StringUtils.isNotNullOrBlank(isBase64Foramt)) {
            isBase64Foramt = isBase64Foramt.trim();
            return "true".equalsIgnoreCase(isBase64Foramt)
                    || "1".equalsIgnoreCase(isBase64Foramt)
                    || "yes".equalsIgnoreCase(isBase64Foramt);
        } else {
            return false;
        }
    }

    @Override
    public String execute(HttpServletRequest request, UeditorActionType actionType, UeditorConfig config) {
        if (actionType.equals(UeditorActionType.CONFIG)) {
            return config.toJson();
        }
        if (this.isCheckLogin() && !this.isLogin()) {
            return ExecuteStateResult.fail("未登录，无法操作").toJson();
        }
        try {
            String callbackName = request.getParameter("callback");
            if (StringUtils.isNotNullOrBlank(callbackName) && !validCallbackName(callbackName)) {
                return ExecuteStateResult.fail("无效的回调参数。").toJson();
            }
            ExecuteResult result = this.invoke(request, actionType, this.isBase64FormatRequest(request), config);
            if (result == null) {
                return ExecuteStateResult.fail("响应结果未知").toJson();
            }
            if (StringUtils.isNotNullOrBlank(callbackName)) {
                return callbackName + "(" + result.toJson() + ");";
            }
            return result.toJson();
        } catch (Exception err) {
            log.error("请求出错：" + err.getMessage(), err);
            return ExecuteStateResult.fail("请求失败").toString();
        }
    }

    /**
     * 调用
     *
     * @param request        请求
     * @param actionType     执行类型
     * @param isBase64Format 是否为Base64格式
     * @param config         配置
     * @return
     */
    protected abstract ExecuteResult invoke(HttpServletRequest request, UeditorActionType actionType, boolean isBase64Format, UeditorConfig config);
}
