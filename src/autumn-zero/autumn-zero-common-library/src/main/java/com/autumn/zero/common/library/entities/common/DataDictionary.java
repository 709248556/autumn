package com.autumn.zero.common.library.entities.common;

/**
 * 数据字典
 *
 * @Description TODO
 * @Author 老码农
 * @Date 2019-07-25 16:59
 */
public interface DataDictionary {

    /**
     * 字段 dictionaryType
     */
    public static final String FIELD_DICTIONARY_TYPE = "dictionaryType";

    /**
     * 获取字典类型
     *
     * @return
     */
    Integer getDictionaryType();

    /**
     * 设置字典类型
     *
     * @param dictionaryType 字典类型
     */
    void setDictionaryType(Integer dictionaryType);

}
