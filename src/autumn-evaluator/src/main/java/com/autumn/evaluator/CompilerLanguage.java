package com.autumn.evaluator;

/**
 * 编译语言
 */
public enum CompilerLanguage {

    /**
     * C#
     */
    CSharp,

    /**
     * VB.NET
     */
    VB;

    public int getValue() {
        return this.ordinal();
    }

    public static CompilerLanguage forValue(int value) {
        return values()[value];
    }
}