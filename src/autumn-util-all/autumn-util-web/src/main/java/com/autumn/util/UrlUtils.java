package com.autumn.util;

/**
 * Url工具
 * <p>
 * </p>
 *
 * @description TODO
 * @author: 老码农
 * @create: 2020-04-11 00:08
 **/
public class UrlUtils {

    /**
     * URL请求分隔符
     */
    public static final char URL_SEPARATE = '/';

    /**
     * 获取小写转换的请求Url
     *
     * @param url 返回合法的url地址
     * @return
     */
    public static String getLowerCaseRequestUrl(String url) {
        return getLowerCaseRequestUrl(null, url);
    }

    /**
     * 获取小写转换的请求Url
     *
     * @param prefix 前缀
     * @param url    地址
     * @return
     */
    public static String getLowerCaseRequestUrl(String prefix, String url) {
        //可采用替换的办法更简单，但考虑性能，因一次可解决替换、小大写转换、去掉空格
        StringBuilder builder = new StringBuilder(255);
        builder.append(URL_SEPARATE);
        if (prefix != null) {
            addChar(builder, prefix);
        }
        if (url != null) {
            if (builder.length() > 1) {
                builder.append(URL_SEPARATE);
            }
            addChar(builder, url);
        }
        return builder.toString();
    }

    /**
     * 添加字符
     *
     * @param builder
     * @param str
     * @return
     */
    private static void addChar(StringBuilder builder, String str) {
        char last = URL_SEPARATE;
        for (int i = 0; i < str.length(); i++) {
            char c = str.charAt(i);
            if (!StringUtils.isWhiteChar(c)) {
                if (c != URL_SEPARATE || last != URL_SEPARATE) {
                    builder.append(Character.toLowerCase(c));
                }
                last = c;
            }
        }
        int lastIndex = builder.length() - 1;
        if (lastIndex > 0 && builder.charAt(lastIndex) == URL_SEPARATE) {
            builder.deleteCharAt(lastIndex);
        }
    }

}
