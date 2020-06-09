package com.autumn.web.auditing;

import com.autumn.audited.ClientBrowserInfo;
import com.autumn.audited.ClientInfoProvider;
import com.autumn.audited.WebClientBrowserInfo;
import com.autumn.util.WebUtils;

import javax.servlet.http.HttpServletRequest;

/**
 * Web 客户端信息提供者
 *
 * @author 老码农
 * <p>
 * 2017-10-20 12:02:45
 */
public class WebClientInfoProvider implements ClientInfoProvider {

    @Override
    public ClientBrowserInfo getBrowserInfo() {
        HttpServletRequest request = WebUtils.getCurrentRequest();
        if (request == null) {
            return WebClientBrowserInfo.INSTANCE;
        }
        String value = request.getHeader("User-Agent");
        return new WebClientBrowserInfo(value);
    }

    @Override
    public String getClientIpAddress() {
        return WebUtils.getCurrentRequestAddress();
    }

    @Override
    public String getClientPlatformVersion() {
        HttpServletRequest request = WebUtils.getCurrentRequest();
        if (request == null) {
            return "";
        }
        String value = request.getHeader(CLIENT_PLATFORM_VERSION_KEY);
        if (!WebUtils.isInvalidWebString(value)) {
            return value;
        }
        return "";
    }

    @Override
    public String getClientPlatformName() {
        HttpServletRequest request = WebUtils.getCurrentRequest();
        if (request == null) {
            return "";
        }
        String value = request.getHeader(CLIENT_PLATFORM_NAME_KEY);
        if (!WebUtils.isInvalidWebString(value)) {
            return value;
        }
        return "web";
    }
}
