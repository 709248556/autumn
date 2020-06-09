package com.autumn.audited;

import lombok.ToString;

import java.io.Serializable;
import java.util.Date;

/**
 * 审计消息
 *
 * @Description TODO
 * @Author 老码农
 * @Date 2019-07-27 18:52
 */
@ToString(callSuper = true)
public abstract class AbstractAuditedMessage implements Serializable {

    private static final long serialVersionUID = 5835520541748140623L;

    /**
     * 创建时间
     */
    private Date gmtCreate;

    /**
     * 用户id
     */
    private Long userId;

    /**
     * 用户名称
     */
    private String userName;

    /**
     * 客户端ip地址
     */
    private String clientIpAddress;

    /**
     * 客户端名称
     */
    private String clientName;

    /**
     * 客户端版本
     */
    private String clientVersion;

    /**
     * 浏览器名称
     */
    private String browserName;

    /**
     * 浏览器平台
     */
    private String browserPlatform;

    /**
     * 浏览信息
     */
    private String browserInfo;

    public Date getGmtCreate() {
        return gmtCreate;
    }

    public void setGmtCreate(Date gmtCreate) {
        this.gmtCreate = gmtCreate;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getClientIpAddress() {
        return clientIpAddress;
    }

    public void setClientIpAddress(String clientIpAddress) {
        this.clientIpAddress = clientIpAddress;
    }

    public String getClientName() {
        return clientName;
    }

    public void setClientName(String clientName) {
        this.clientName = clientName;
    }

    public String getClientVersion() {
        return clientVersion;
    }

    public void setClientVersion(String clientVersion) {
        this.clientVersion = clientVersion;
    }

    public String getBrowserName() {
        return browserName;
    }

    public void setBrowserName(String browserName) {
        this.browserName = browserName;
    }

    public String getBrowserPlatform() {
        return browserPlatform;
    }

    public void setBrowserPlatform(String browserPlatform) {
        this.browserPlatform = browserPlatform;
    }

    public String getBrowserInfo() {
        return browserInfo;
    }

    public void setBrowserInfo(String browserInfo) {
        this.browserInfo = browserInfo;
    }
}
