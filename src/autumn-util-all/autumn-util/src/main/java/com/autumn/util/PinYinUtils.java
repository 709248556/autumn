package com.autumn.util;

import com.autumn.validation.MatchesUtils;
import net.sourceforge.pinyin4j.PinyinHelper;
import net.sourceforge.pinyin4j.format.HanyuPinyinCaseType;
import net.sourceforge.pinyin4j.format.HanyuPinyinOutputFormat;
import net.sourceforge.pinyin4j.format.HanyuPinyinToneType;
import net.sourceforge.pinyin4j.format.exception.BadHanyuPinyinOutputFormatCombination;

import java.util.function.Function;

/**
 * 拼音帮助工具
 *
 * @Description TODO
 * @Author 老码农
 * @Date 2019-08-05 2:46
 */
public class PinYinUtils {

    /**
     * 获取汉字的首个拼音
     *
     * @param chinese  中文
     * @param isNumber 是否解析数字
     * @param isLetter 是否解析字母
     * @return
     */
    public static String getFirstPinyinString(String chinese, boolean isNumber, boolean isLetter) {
        return getFirstPinyinString(chinese, isNumber, isLetter, HanyuPinyinCaseType.UPPERCASE, HanyuPinyinToneType.WITHOUT_TONE);
    }

    /**
     * 获取汉字的首个拼音
     *
     * @param chinese  中文
     * @param isNumber 是否解析数字
     * @param isLetter 是否解析字母
     * @param caseType 转换类型
     * @param toneType 声调类型
     * @return
     */
    public static String getFirstPinyinString(String chinese, boolean isNumber, boolean isLetter,
                                              HanyuPinyinCaseType caseType, HanyuPinyinToneType toneType) {
        return getPinyinString(chinese, isNumber, isLetter, caseType, toneType, array -> array[0].substring(0, 1));
    }

    /**
     * 获取汉字的拼音
     *
     * @param chinese  中文
     * @param isNumber 是否解析数字
     * @param isLetter 是否解析字母
     * @return
     */
    public static String getPinyinString(String chinese, boolean isNumber, boolean isLetter) {
        return getPinyinString(chinese, isNumber, isLetter, HanyuPinyinToneType.WITHOUT_TONE);
    }

    /**
     * 获取汉字的拼音
     *
     * @param chinese  中文
     * @param isNumber 是否解析数字
     * @param isLetter 是否解析字母
     * @param toneType 声调类型
     * @return
     */
    public static String getPinyinString(String chinese, boolean isNumber, boolean isLetter, HanyuPinyinToneType toneType) {
        return getPinyinString(chinese, isNumber, isLetter, HanyuPinyinCaseType.LOWERCASE, toneType, array -> StringUtils.upperCaseCapitalize(array[0]));
    }

    /**
     * 获取汉字拼音
     *
     * @param chinese       中文
     * @param isNumber      是否解析数字
     * @param isLetter      是否解析字母
     * @param caseType      转换类型
     * @param toneType      声调类型
     * @param valueFunction 值函数
     * @return
     */
    public static String getPinyinString(String chinese, boolean isNumber, boolean isLetter,
                                         HanyuPinyinCaseType caseType, HanyuPinyinToneType toneType, Function<String[], String> valueFunction) {
        HanyuPinyinOutputFormat format = new HanyuPinyinOutputFormat();
        if (caseType != null) {
            format.setCaseType(caseType);
        } else {
            format.setCaseType(HanyuPinyinCaseType.LOWERCASE);
        }
        if (toneType != null) {
            format.setToneType(toneType);
        } else {
            format.setToneType(HanyuPinyinToneType.WITHOUT_TONE);
        }
        return getPinyinString(chinese, isNumber, isLetter, format, valueFunction);
    }

    /**
     * 获取汉字拼音
     *
     * @param chinese       中文
     * @param isNumber      是否解析数字
     * @param isLetter      是否解析字母
     * @param format        格式
     * @param valueFunction 值函数
     * @return
     */
    public static String getPinyinString(String chinese, boolean isNumber, boolean isLetter,
                                         HanyuPinyinOutputFormat format, Function<String[], String> valueFunction) {
        if (StringUtils.isNullOrBlank(chinese)) {
            return "";
        }
        if (format == null) {
            format = new HanyuPinyinOutputFormat();
            format.setCaseType(HanyuPinyinCaseType.LOWERCASE);
            format.setToneType(HanyuPinyinToneType.WITHOUT_TONE);
        }
        char[] cl_chars = chinese.trim().toCharArray();
        StringBuilder builder = new StringBuilder();
        try {
            for (char cl_char : cl_chars) {
                String str = String.valueOf(cl_char);
                if (str.matches(MatchesUtils.CHINESE)) {
                    if (valueFunction != null) {
                        String[] array = PinyinHelper.toHanyuPinyinStringArray(cl_char, format);
                        if (array.length > 0) {
                            builder.append(valueFunction.apply(array));
                        }
                    }
                } else if (isNumber && str.matches(MatchesUtils.NUMBER)) {
                    builder.append(cl_char);
                } else if (isLetter && str.matches(MatchesUtils.LETTER)) {
                    builder.append(cl_char);
                }
            }
        } catch (BadHanyuPinyinOutputFormatCombination e) {
            e.printStackTrace();
        }
        return builder.toString();
    }

}
