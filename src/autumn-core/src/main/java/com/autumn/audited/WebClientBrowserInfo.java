package com.autumn.audited;

import lombok.ToString;

/**
 * 客户端浏览器信息
 *
 * @author 老码农
 * <p>
 * 2017-10-20 13:37:30
 */
@ToString(callSuper = true)
public class WebClientBrowserInfo implements ClientBrowserInfo {

    /**
     *
     */
    public final static String BROWSER_EDGE = "Edge";
    public final static String BROWSER_MSIE = "MSIE";
    public final static String BROWSER_IE = "IE";
    public final static String BROWSER_RV = "RV";
    public final static String BROWSER_SAFARI = "Safari";
    public final static String BROWSER_OPERA = "Opera";
    public final static String BROWSER_OPR = "OPR";
    public final static String BROWSER_CHROME = "Chrome";
    public final static String BROWSER_FIREFOX = "Firefox";
    public final static String BROWSER_MOZILLA = "Mozilla";
    public final static String BROWSER_NETSCAPE = "Netscape";

    public final static String OPERATING_SYSTEM_WINDOWS = "Windows";
    public final static String OPERATING_SYSTEM_MAC = "Mac";
    public final static String OPERATING_SYSTEM_X11 = "X11";
    public final static String OPERATING_SYSTEM_ANDROID = "Android";
    public final static String OPERATING_SYSTEM_IPHONE = "iphone";
    public final static String OPERATING_SYSTEM_IPAD = "ipad";
    public final static String OPERATING_SYSTEM_LINUX = "linux";
    public final static String OPERATING_SYSTEM_UNIX = "Unix";

    /**
     * 实例
     */
    public static final ClientBrowserInfo INSTANCE = new WebClientBrowserInfo("");

    private final String browserInfo;

    /**
     * @param browserInfo
     */
    public WebClientBrowserInfo(String browserInfo) {
        this.browserInfo = browserInfo != null ? browserInfo : "";
    }

    /**
     * 获取浏览器信息
     *
     * @return
     */
    @Override
    public String getBrowserInfo() {
        return browserInfo;
    }

    private String browserName = null;

    private final static String BROWSER_VERSION = "version";

    /**
     * 获取版本
     *
     * @return
     */
    private String getVersion() {
        String[] split = browserInfo.substring(browserInfo.indexOf(BROWSER_VERSION)).split(" ");
        if (split.length > 0) {
            String[] split1 = split[0].split("/");
            if (split1.length > 1) {
                return split1[1];
            }
        }
        return "";
    }

    /**
     * 获取浏览器名称
     *
     * @return
     */
    @Override
    public String getBrowserName() {
        if (browserName != null) {
            return browserName;
        }
        if (browserInfo.contains(BROWSER_EDGE)) {
            browserName = (browserInfo.substring(browserInfo.indexOf(BROWSER_EDGE)).split(" ")[0]).replace("/", "-");
        } else if (browserInfo.contains(BROWSER_MSIE)) {
            String substring = browserInfo.substring(browserInfo.indexOf(BROWSER_MSIE)).split(";")[0];
            browserName = substring.split(" ")[0].replace(BROWSER_MSIE, BROWSER_IE) + "-" + substring.split(" ")[1];
        } else if (browserInfo.contains(BROWSER_SAFARI) && browserInfo.contains(BROWSER_VERSION)) {
            browserName = (browserInfo.substring(browserInfo.indexOf(BROWSER_SAFARI)).split(" ")[0]).split("/")[0] + "-" + getVersion();
        } else if (browserInfo.contains(BROWSER_OPR) || browserInfo.contains(BROWSER_OPERA)) {
            if (browserInfo.contains(BROWSER_OPERA)) {
                browserName = (browserInfo.substring(browserInfo.indexOf(BROWSER_OPERA)).split(" ")[0]).split("/")[0] + "-" + getVersion();
            } else if (browserInfo.contains(BROWSER_OPR)) {
                browserName = ((browserInfo.substring(browserInfo.indexOf(BROWSER_OPR.toUpperCase())).split(" ")[0])
                        .replace("/", "-")).replace(BROWSER_OPR.toUpperCase(), BROWSER_OPERA);
            }
        } else if (browserInfo.contains(BROWSER_CHROME)) {
            browserName = (browserInfo.substring(browserInfo.indexOf(BROWSER_CHROME)).split(" ")[0]).replace("/", "-");
        } else if ((browserInfo.contains(BROWSER_MOZILLA)) || (browserInfo.contains(BROWSER_NETSCAPE))) {
            browserName = "Netscape-?";
        } else if (browserInfo.contains(BROWSER_FIREFOX)) {
            browserName = (browserInfo.substring(browserInfo.indexOf(BROWSER_FIREFOX)).split(" ")[0]).replace("/", "-");
        } else if (browserInfo.contains(BROWSER_RV)) {
            String ieVersion = (browserInfo.substring(browserInfo.indexOf(BROWSER_RV)).split(" ")[0])
                    .replace(BROWSER_RV + ":", "-");
            browserName = BROWSER_IE + ieVersion.substring(0, ieVersion.length() - 1);
        } else {
            browserName = "";
        }
        return browserName;
    }

    private String browserPlatformName = null;


    /**
     * 获取浏览器对应的平台
     *
     * @return
     */
    @Override
    public String getBrowserPlatform() {
        if (browserPlatformName != null) {
            return browserPlatformName;
        }
        String value = browserInfo.toLowerCase();
        if (value.indexOf(OPERATING_SYSTEM_WINDOWS.toLowerCase()) > 0) {
            browserPlatformName = OPERATING_SYSTEM_WINDOWS;
        } else if (value.indexOf(OPERATING_SYSTEM_MAC.toLowerCase()) > 0) {
            browserPlatformName = OPERATING_SYSTEM_MAC;
        } else if (value.indexOf(OPERATING_SYSTEM_X11.toLowerCase()) > 0) {
            browserPlatformName = OPERATING_SYSTEM_UNIX;
        } else if (value.indexOf(OPERATING_SYSTEM_ANDROID) > 0) {
            browserPlatformName = OPERATING_SYSTEM_ANDROID;
        } else if (value.indexOf(OPERATING_SYSTEM_IPHONE) > 0) {
            browserPlatformName = OPERATING_SYSTEM_IPHONE;
        } else if (value.indexOf(OPERATING_SYSTEM_IPAD) > 0) {
            browserPlatformName = OPERATING_SYSTEM_IPAD;
        } else if (value.indexOf(OPERATING_SYSTEM_LINUX.toLowerCase()) > 0) {
            browserPlatformName = OPERATING_SYSTEM_LINUX;
        } else {
            browserPlatformName = "";
        }
        return browserPlatformName;
    }
}
