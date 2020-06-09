package com.autumn.util.excel.utils;

import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

/**
 * 判断 是 或 否 的字符串集合
 */
public class YesOrNoConst {

    /**
     * 可以表示true的字符串集合
     */
    public static final Set<String> TRUE_SET = new HashSet<String>(16);

    /**
     * 可以表示False的字符串集合
     */
    public static final Set<String> FALSE_SET = new HashSet<String>(16);

    static {
        Collections.addAll(TRUE_SET, "true", "yes", "t", "y", "1", "是");
        Collections.addAll(FALSE_SET, "false", "f", "no", "0", "否", "n");
    }
}
