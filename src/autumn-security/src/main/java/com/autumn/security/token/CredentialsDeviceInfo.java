package com.autumn.security.token;

import java.io.Serializable;

/**
 * 认证设备信息
 * <p>
 * </p>
 *
 * @description TODO
 * @author: 老码农
 * @create: 2020-04-20 13:33
 **/
public interface CredentialsDeviceInfo extends Serializable {

    /**
     * 字段 deviceType (设备Token) 最大长度
     */
    public static final int MAX_LENGTH_DEVICE_TOKEN = 128;

    /**
     * 字段 deviceType (设备类型) 最大长度
     */
    public static final int MAX_LENGTH_DEVICE_TYPE = 32;

    /**
     * 字段 platformName (平台名称) 最大长度
     */
    public static final int MAX_LENGTH_PLATFORM_NAME = 32;

    /**
     * 字段 platformVersion (平台版本) 最大长度
     */
    public static final int MAX_LENGTH_PLATFORM_VERSION = 32;

    /**
     * 字段 applicationName (应用名称) 最大长度
     */
    public static final int MAX_LENGTH_APPLICATION_NAME = 32;

    /**
     * 字段 applicationVersion (应用版本) 最大长度
     */
    public static final int MAX_LENGTH_APPLICATION_VERSION = 32;

    /**
     * 字段 deviceIdentification (设备标识) 最大长度
     */
    public static final int MAX_LENGTH_DEVICE_IDENTIFICATION = 256;

    /**
     * 获取设备类型
     * <p>
     * 如手机、平板、电视、其他物联设备
     * </p>
     *
     * @return
     */
    String getDeviceType();

    /**
     * 获取平台名称
     * <p>
     * 如IOS、Android、小程序等
     * </p>
     *
     * @return
     */
    String getPlatformName();

    /**
     * 获取平台版本
     *
     * @return
     */
    String getPlatformVersion();

    /**
     * 获取应用名称
     *
     * @return
     */
    String getApplicationName();

    /**
     * 获取应用名版本
     *
     * @return
     */
    String getApplicationVersion();

    /**
     * 获取设备标识
     * <p>
     * 如设备唯一编号或其他组合
     * </p>
     *
     * @return
     */
    String getDeviceIdentification();
}
