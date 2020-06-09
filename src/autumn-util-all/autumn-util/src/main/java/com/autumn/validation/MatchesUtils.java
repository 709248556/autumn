package com.autumn.validation;

import com.autumn.util.StringUtils;

import java.util.regex.Pattern;

/**
 * 规则匹配
 *
 * @author shao
 * @date 2017/12/18 17:03
 */
public class MatchesUtils {

    /**
     * 手机号正则
     */
    public final static String MOBILE_PHONE = "^((13[0-9])|(14[0-9])|(15[0-9])|(16[0-9])|(17[0-9])|(18[0-9])|(19[0-9]))\\d{8}$";
    /**
     * 邮箱正则
     */
    public final static String EMAIL = "^([a-z0-9A-Z\u4e00-\u9fa5]+[-|.]?)+[a-z0-9A-Z\u4e00-\u9fa5]@([a-z0-9A-Z]+(-[a-z0-9A-Z]+)?\\.)+[a-zA-Z]{2,}$";
    /**
     * 普通账户正则
     */
    public final static String ACCOUNT = "^[a-zA-Z]\\w{4,16}$";

    /**
     * 身份证正则
     */
    public final static String IDCARD = "^(^[1-9]\\d{7}((0\\d)|(1[0-2]))(([0|1|2]\\d)|3[0-1])\\d{3}$)|(^[1-9]\\d{5}[1-9]\\d{3}((0\\d)|(1[0-2]))(([0|1|2]\\d)|3[0-1])((\\d{4})|\\d{3}[Xx])$)$";

    /**
     * 数字
     */
    public final static String NUMBER = "[0-9]+";

    /**
     * 字母
     */
    public final static String LETTER = "[a-zA-Z]+";

    /**
     * 中文
     */
    public final static String CHINESE = "[\u4e00-\u9fa5]+";

    /**
     * 数字、字母、中文
     */
    public final static String NUMBER_LETTER_CHINESE = "[a-zA-Z\\u4e00-\\u9fa5][a-zA-Z0-9\\u4e00-\\u9fa5]+";

    /**
     * 是否数字
     *
     * @param str
     * @return
     */
    public static boolean isNumber(String str) {
        if (StringUtils.isNullOrBlank(str)) {
            return false;
        }
        return Pattern.matches(NUMBER, str);
    }

    /**
     * 是否字母
     *
     * @param str
     * @return
     */
    public static boolean isLetter(String str) {
        if (StringUtils.isNullOrBlank(str)) {
            return false;
        }
        return Pattern.matches(LETTER, str);
    }

    /**
     * 是否中文
     *
     * @param str
     * @return
     */
    public static boolean isChinese(String str) {
        if (StringUtils.isNullOrBlank(str)) {
            return false;
        }
        return Pattern.matches(CHINESE, str);
    }

    /**
     * 是否数字、字母或中文
     *
     * @param str
     * @return
     */
    public static boolean isNumberOrLetterOrChinese(String str) {
        if (StringUtils.isNullOrBlank(str)) {
            return false;
        }
        return Pattern.matches(NUMBER_LETTER_CHINESE, str);
    }

    /**
     * 匹配手机号
     *
     * @param str
     * @return
     */
    public static boolean isMobilePhone(String str) {
        if (StringUtils.isNullOrBlank(str)) {
            return false;
        }
        return Pattern.matches(MOBILE_PHONE, str);
    }

    /**
     * 匹配邮箱
     *
     * @param str
     * @return
     */
    public static boolean isEmail(String str) {
        if (StringUtils.isNullOrBlank(str)) {
            return false;
        }
        return Pattern.matches(EMAIL, str);
    }

    /**
     * 匹配普通账户格式
     *
     * @param str
     * @return
     */
    public static boolean isAccount(String str) {
        if (StringUtils.isNullOrBlank(str)) {
            return false;
        }
        return Pattern.matches(ACCOUNT, str);
    }

    /**
     * 匹配身份证格式
     *
     * @param str
     * @return
     */
    public static boolean isIdCard(String str) {
        if (StringUtils.isNullOrBlank(str)) {
            return false;
        }
        return Pattern.matches(IDCARD, str);
    }

}
