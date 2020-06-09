package com.autumn.zero.authorization.application.dto.auth;

/**
 * 授权会话信息
 *
 * @Description TODO
 * @Author 老码农
 * @Date 2019-07-26 15:50
 */
public interface AuthSessionInfo {
    /**
     * 获取会话Key
     *
     * @return
     */
    String getSessionKey();

    /**
     * 设置会话Key
     *
     * @param sessionKey 会话Key
     */
    void setSessionKey(String sessionKey);

    /**
     * 获取会话id
     *
     * @return
     */
    String getSessionId();

    /**
     * 设置会话id
     *
     * @param sessionId 会话id
     */
    void setSessionId(String sessionId);

    /**
     * 获取会话过期时间
     *
     * @return
     */
    Long getSessionTimeout();

    /**
     * 设置会话过期时间
     *
     * @param sessionTimeout
     */
    void setSessionTimeout(Long sessionTimeout);
}
