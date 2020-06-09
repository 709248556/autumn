package com.autumn.mybatis.wrapper;

import java.io.Serializable;

/**
 * 聚合类型
 *
 * @Description TODO
 * @Author 老码农
 * @Date 2019-06-13 23:38
 */
public enum PolymerizationEnum implements Serializable {

    /**
     * 统计
     */
    COUNT("COUNT"),

    /**
     * 平均数
     */
    AVG("AVG"),

    /**
     * 最小值
     */
    MIN("MIN"),

    /**
     * 最大值
     */
    MAX("MAX"),

    /**
     * 合计
     */
    SUM("SUM");

    private final String value;

    /**
     * 实例化
     *
     * @param value 值
     */
    private PolymerizationEnum(String value) {
        this.value = value;
    }

    /**
     * 获取值
     *
     * @return
     */
    public String getValue() {
        return value;
    }

    @Override
    public String toString() {
        return getValue();
    }

}
