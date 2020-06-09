package com.autumn.util;

import org.apache.commons.io.IOUtils;
import org.springframework.web.context.request.RequestAttributes;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import java.io.BufferedReader;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

/**
 * Web 帮助
 *
 * @author 老码农
 * <p>
 * 2017-11-07 12:07:24
 */
public class WebUtils {

    /**
     * 请求 Get 方法
     */
    public static final String REQUEST_METHOD_GET = "GET";

    /**
     * 请求 Post 方法
     */
    public static final String REQUEST_METHOD_POST = "POST";

    /**
     * 请求 put 方法
     */
    public static final String REQUEST_METHOD_PUT = "PUT";

    /**
     * 请求 delete 方法
     */
    public static final String REQUEST_METHOD_DELETE = "DELETE";

    /**
     * 请求 head 方法
     */
    public static final String REQUEST_METHOD_HEAD = "HEAD";

    /**
     * 请求 options 方法
     */
    public static final String REQUEST_METHOD_OPTIONS = "OPTIONS";

    /**
     * 请求 trace 方法
     */
    public static final String REQUEST_METHOD_TRACE = "TRACE";

    /**
     * 请求 patch 方法
     */
    public static final String REQUEST_METHOD_PATCH = "PATCH";

    /**
     * 获取请求体 Body 字符串
     *
     * @param request 请
     * @return
     * @throws IOException
     */
    public static String getRequestBodyString(HttpServletRequest request) throws IOException {
        String method = request.getMethod();
        if (method.equalsIgnoreCase(REQUEST_METHOD_GET)) {
            return request.getQueryString();
        }
        BufferedReader reader = null;
        try {
            reader = request.getReader();
            return IOUtils.toString(reader);
        } finally {
            IOUtils.closeQuietly(reader);
        }
    }

    /**
     * 获取请求 Map
     *
     * @param request 请求 请求
     * @return
     */
    public static Map<String, String> getRequestMap(HttpServletRequest request) {
        if (request == null) {
            return CollectionUtils.newHashMap();
        }
        Map<String, String[]> map = request.getParameterMap();
        return getRequestMap(map);
    }

    /**
     * 转换请求 map
     *
     * @param parameterMap 请求Map
     * @return
     */
    public static Map<String, String> getRequestMap(Map<String, String[]> parameterMap) {
        Map<String, String> inputDic = new HashMap<>(16);
        if (parameterMap != null) {
            for (Map.Entry<String, String[]> entry : parameterMap.entrySet()) {
                if (entry.getValue() != null && entry.getValue().length > 0) {
                    String value = String.join(",", entry.getValue());
                    inputDic.put(entry.getKey(), value);
                }
            }
        }
        return inputDic;
    }

    /**
     * 获取当前请求
     *
     * @return
     */
    public static HttpServletRequest getCurrentRequest() {
        try {
            RequestAttributes attribute = RequestContextHolder.getRequestAttributes();
            if (attribute != null && (attribute instanceof ServletRequestAttributes)) {
                return ((ServletRequestAttributes) attribute).getRequest();
            }
            return null;
        } catch (Exception e) {
            return null;
        }
    }

    private final static String UNKNOWN = "unknown";
    private final static String STR_NULL = "null";
    private final static String COMMA = ",";

    /**
     * 是否为无效的字符（null、空白值、unknown、"null”则返回 true,反之)
     *
     * @param value
     * @return
     */
    public static boolean isInvalidWebString(String value) {
        return StringUtils.isNullOrBlank(value) || UNKNOWN.equalsIgnoreCase(value) || STR_NULL.equalsIgnoreCase(value);
    }

    /**
     * 获取请求的Ip地址
     *
     * @return
     */
    public static String getRequestAddress(HttpServletRequest request) {
        try {
            if (request == null) {
                return NetUtils.getLocalAddressString();
            }
            String ip;
            ip = request.getHeader("X-Forwarded-For");
            if (!isInvalidWebString(ip)) {
                if (ip.contains(COMMA)) {
                    ip = ip.split(",")[0];
                }
                return ip;
            }
            ip = request.getHeader("proxy-client-ip");
            if (!isInvalidWebString(ip)) {
                return ip;
            }
            ip = request.getHeader("X-Real-IP");
            if (!isInvalidWebString(ip)) {
                return ip;
            }
            ip = request.getHeader("wl-proxy-client-ip");
            if (!isInvalidWebString(ip)) {
                return ip;
            }
            ip = request.getRemoteAddr();
            if (!isInvalidWebString(ip)) {
                return ip;
            }
            return NetUtils.getLocalAddressString();
        } catch (Exception e) {
            return NetUtils.getLocalAddressString();
        }
    }

    /**
     * 获取当前请求的Ip地址
     *
     * @return
     */
    public static String getCurrentRequestAddress() {
        return getRequestAddress(getCurrentRequest());
    }
}
