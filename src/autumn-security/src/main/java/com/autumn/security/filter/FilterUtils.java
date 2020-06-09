package com.autumn.security.filter;

import com.autumn.runtime.session.AutumnUser;
import com.autumn.security.context.AutumnSecurityContextHolder;
import com.autumn.security.credential.AutumnUserCredentialsService;
import com.autumn.security.token.DeviceAuthenticationToken;
import com.autumn.util.StringUtils;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.subject.Subject;

import javax.servlet.http.HttpServletRequest;

/**
 * 过滤工具
 * <p>
 * </p>
 *
 * @description TODO
 * @author: 老码农
 * @create: 2020-04-20 23:01
 **/
public class FilterUtils {

    /**
     * 请求 token 键
     */
    public static final String REQUEST_TOKEN_KEY = "token";

    /**
     * 请求 设备id 键
     */
    public static final String REQUEST_DEVICE_ID = "deviceId";

    /**
     * 获取请求的参数
     *
     * @param request 请求
     * @param key     键
     * @return
     */
    public static String getRequestParameter(HttpServletRequest request, String key) {
        String token = request.getHeader(key);
        if (StringUtils.isNullOrBlank(token)) {
            token = request.getParameter(key);
        }
        return token;
    }

    /**
     * 获取请求的 token
     *
     * @param request
     * @return
     */
    public static String getRequestToken(HttpServletRequest request) {
        return getRequestParameter(request, REQUEST_TOKEN_KEY);
    }

    /**
     * 获取请求的 token
     *
     * @param request
     * @return
     */
    public static String getRequestDeviceId(HttpServletRequest request) {
        return getRequestParameter(request, REQUEST_DEVICE_ID);
    }

    /**
     * 创建设备Token
     *
     * @param request
     * @return
     */
    public static DeviceAuthenticationToken createDeviceToken(HttpServletRequest request) {
        String token = FilterUtils.getRequestToken(request);
        if (StringUtils.isNullOrBlank(token)) {
            return null;
        }
        String deviceId = FilterUtils.getRequestDeviceId(request);
        if (StringUtils.isNullOrBlank(deviceId)) {
            return null;
        }
        return new DeviceAuthenticationToken(token.trim(), deviceId.trim());
    }

    /**
     * 是否匹配设备
     *
     * @param credentialsService 认证服务
     * @param token              票据
     * @return
     */
    public static boolean matchesDevice(AutumnUserCredentialsService credentialsService, DeviceAuthenticationToken token) {
        AutumnUser autumnUser = AutumnSecurityContextHolder.getAutumnUser();
        if (autumnUser != null) {
            Subject subject = SecurityUtils.getSubject();
            if (autumnUser.getDeviceInfo() == null) {
                subject.logout();
                return false;
            }
            boolean result = credentialsService.matches(autumnUser, token);
            if (!result) {
                subject.logout();
            }
            return result;
        } else {
            return false;
        }
    }
}
