package com.autumn.web.vo;

import com.autumn.exception.AutumnError;

/**
 * Ajax 默认对象响应
 *
 * @author 老码农
 * <p>
 * 2017-10-31 10:52:21
 */
public class ApiObjectResponse extends ApiResponse<Object> {

    /**
     *
     */
    private static final long serialVersionUID = 5606170616241594987L;

    /**
     * 成功
     *
     * @param value 值
     * @return
     */
    public static ApiObjectResponse success(Object value) {
        ApiObjectResponse res = new ApiObjectResponse();
        res.setSuccess(true);
        res.setError(null);
        res.setResult(value);
        return res;
    }

    /**
     * 错误异常
     *
     * @param code    代码
     * @param message 消息
     * @param details 详情
     * @return
     */
    public static ApiObjectResponse error(int code, String message, String details) {
        ApiObjectResponse res = new ApiObjectResponse();
        res.setSuccess(false);
        res.setResult(null);
        ErrorInfo errorInfo = new ErrorInfo();
        errorInfo.setCode(code);
        errorInfo.setMessage(message);
        errorInfo.setDetails(details);
        res.setError(errorInfo);
        return res;
    }

    /**
     * @param error
     * @return
     */
    public static ApiObjectResponse error(Exception error) {
        if (error instanceof AutumnError) {
            AutumnError autumnError = (AutumnError) error;
            return error(autumnError.getCode(), error.getMessage(), "");
        }
        return error(0, error.getMessage(), "");
    }
}
