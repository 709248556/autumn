package com.autumn.audited;

/**
 * 客户端信息提供者
 *
 * @author 老码农
 * <p>
 * 2017-10-20 10:49:22
 */
public interface ClientInfoProvider {

    /**
     * 客户端平台名称键
     */
    public static final String CLIENT_PLATFORM_NAME_KEY = "client-platform-name";

    /**
     * 客户端平台版本键
     */
    public static final String CLIENT_PLATFORM_VERSION_KEY = "client-platform-version";

    /**
     * 获取浏览器信息
     *
     * @return
     */
    ClientBrowserInfo getBrowserInfo();

    /**
     * 获取客户Ip地址
     *
     * @return
     */
    String getClientIpAddress();

    /**
     * 获取平台版本（WebServer默认取 client-platform-version）
     *
     * @return
     */
    String getClientPlatformVersion();

    /**
     * 获取平台名称（WebServer默认取 client-platform-name）
     *
     * @return
     */
    String getClientPlatformName();

}
