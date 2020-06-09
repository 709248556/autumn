package com.autumn.evaluator;

import java.util.ArrayList;
import java.util.function.Function;

/**
 * 扩展
 */
public final class Extension {

    /**
     * 去掉两头空格，并返回大写
     *
     * @param value
     * @return
     */
    public static String toTrimUpper(String value) {
        return value.trim().toUpperCase();
    }

    /**
     * 是否是英文字母
     *
     * @param value 值
     * @return
     */
    public static boolean isEnglishLetter(String value) {
        for (int i = 0; i < value.length(); i++) {
            if (Extension.isEnglishLetter(value.charAt(i))) {
                return false;
            }
        }
        return true;
    }

    /**
     * 是否是英文字母
     *
     * @param value 值
     * @return
     */
    public static boolean isEnglishLetter(char value) {
        return ((value < 'A' || value > 'Z') && (value < 'a' || value > 'z'));
    }

    /**
     * 比较位置是否相等
     *
     * @param left  左
     * @param right 右
     * @return
     */
    public static boolean comparablePosition(ArrayPosition left, ArrayPosition right) {
        if (left != null && right != null) {
            return left.getRowNumber() == right.getRowNumber() && left.getColNumber() == right.getColNumber();
        } else {
            return left == null && right == null;
        }
    }

    /**
     * 读取函数所有参数
     *
     * @param paramContext 函数上下文
     * @param predicate 条件，null 表达所有条件
     * @return
     */
    public static ArrayList<Variant> readFunctionParams(FunctionParamContext paramContext, Function<Variant, Boolean> predicate) {
        ArrayList<Variant> items = new ArrayList<>();
        for (FunctionParam v : paramContext.getParams()) {
            items.addAll(readFunParams(v.getValue(), predicate));
        }
        return items;
    }

    /**
     * 读取所有数字参数
     *
     * @param arg       参数
     * @param predicate 条件
     * @return
     */
    public static ArrayList<Variant> readFunParams(Variant arg, Function<Variant, Boolean> predicate) {
        ArrayList<Variant> items = new ArrayList<>();
        if (arg.isArray()) {
            for (Variant arr : arg.toArray()) {
                items.addAll(readFunParams(arr, predicate));
            }
        } else {
            if (predicate != null) {
                if (predicate.apply(arg)) {
                    items.add(arg);
                }
            } else {
                items.add(arg);
            }
        }
        return items;
    }

    /**
     * 统计函数所有参数
     *
     * @param paramContext 函数上下文
     * @param predicate 条件，null 表达所有条件
     * @return
     */
    public static int countFunctionParams(FunctionParamContext paramContext, Function<Variant, Boolean> predicate) {
        int value = 0;
        for (FunctionParam v : paramContext.getParams()) {
            value += countFunParams(v.getValue(), predicate);
        }
        return value;
    }

    /**
     * 读取所有数字参数
     *
     * @param arg       参数
     * @param predicate 条件
     * @return
     */
    public static int countFunParams(Variant arg, Function<Variant, Boolean> predicate) {
        int value = 0;
        if (arg.isArray()) {
            for (Variant arr : arg.toArray()) {
                value += countFunParams(arr, predicate);
            }
        } else {
            if (predicate != null) {
                if (predicate.apply(arg)) {
                    value++;
                }
            } else {
                value++;
            }
        }
        return value;
    }
}