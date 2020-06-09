package com.autumn.zero.authorization.utils;

import com.autumn.util.EnvironmentConstants;
import com.autumn.util.StringUtils;

/**
 * 授权服务工具
 * <p>
 * </p>
 *
 * @description TODO
 * @author: 老码农
 * @create: 2020-04-22 21:06
 **/
public class AuthServiceUtils {

    /**
     * 将 权限 Url 重置为有效的地址
     *
     * @param url 按换行符处理后返回
     * @return
     */
    public static String resetPermissionUrl(String url) {
        if (StringUtils.isNullOrBlank(url)) {
            return "";
        }
        String[] urlPaths = StringUtils.urlOrPackageToStringArray(url.trim());
        StringBuilder builder = new StringBuilder(url.length());
        for (String urlPath : urlPaths) {
            if (builder.length() > 0) {
                builder.append(EnvironmentConstants.LINE_SEPARATOR);
            }
            builder.append(urlPath);
        }
        return builder.toString();
    }

}
