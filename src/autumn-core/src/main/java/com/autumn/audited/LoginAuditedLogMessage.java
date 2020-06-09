package com.autumn.audited;

import lombok.ToString;

/**
 * 登录审计消息
 *
 * @Description TODO
 * @Author 老码农
 * @Date 2019-07-27 18:51
 */
@ToString(callSuper = true)
public class LoginAuditedLogMessage extends AbstractAuditedMessage {

    private static final long serialVersionUID = 9152977236440147561L;

    /**
     * 用户账号
     */
    private String userAccount;

    /**
     * 第三方提供者
     */
    private String provider;

    /**
     * 第三方提供者键
     */
    private String providerKey;

    /**
     * 是否成功
     */
    private boolean success;

    /**
     * 状态消息
     */
    private String statusMessage;

    public String getUserAccount() {
        return userAccount;
    }

    public void setUserAccount(String userAccount) {
        this.userAccount = userAccount;
    }

    public String getProvider() {
        return provider;
    }

    public void setProvider(String provider) {
        this.provider = provider;
    }

    public String getProviderKey() {
        return providerKey;
    }

    public void setProviderKey(String providerKey) {
        this.providerKey = providerKey;
    }

    public boolean isSuccess() {
        return success;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }

    public String getStatusMessage() {
        return statusMessage;
    }

    public void setStatusMessage(String statusMessage) {
        this.statusMessage = statusMessage;
    }
}
