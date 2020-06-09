package com.autumn.zero.common.library.entities.personal;

/**
 * 用户个人拥有默认数据
 * <p>
 * </p>
 *
 * @description TODO
 * @author: 老码农
 * @create: 2020-03-26 20:10
 **/
public interface UserPersonalDefaultEntity extends UserPersonalEntity {

    /**
     * 字段 defaulted (默认)
     */
    public static final String FIELD_DEFAULTED = "defaulted";

    /**
     * 获取理否为默认
     */
    boolean isDefaulted();

    /**
     * 设置默认
     *
     * @param defaulted
     */
    void setDefaulted(boolean defaulted);

}
