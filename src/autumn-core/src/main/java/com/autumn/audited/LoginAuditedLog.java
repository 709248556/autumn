package com.autumn.audited;

/**
 * 登录审计
 *
 * @Description TODO
 * @Author 老码农
 * @Date 2019-07-27 18:59
 */
public interface LoginAuditedLog {

    /**
     * 添加日志
     *
     * @param message 消息
     */
    void addLog(LoginAuditedLogMessage message);

    /**
     * 添加基于账号成功日志
     *
     * @param userId      用户id
     * @param userName    用户名称
     * @param userAccount 用户账号
     */
    void addSuccessLogByAccount(Long userId, String userName, String userAccount);

    /**
     * 添加基于账号失账日志
     *
     * @param userAccount   用户账号
     * @param statusMessage 状态消息
     */
    void addFailLogByAccount(String userAccount, String statusMessage);

    /**
     * 添加基于第三方成功日志
     *
     * @param userId      用户id
     * @param userName    用户名称
     * @param provider    提供者
     * @param providerKey 提供键
     */
    void addSuccessLogByProvider(Long userId, String userName, String provider, String providerKey);

    /**
     * 添加基于第三方失账日志
     *
     * @param provider      提供者
     * @param providerKey   提供键
     * @param statusMessage 状态消息
     */
    void addFailLogByProvider(String provider, String providerKey, String statusMessage);

}
