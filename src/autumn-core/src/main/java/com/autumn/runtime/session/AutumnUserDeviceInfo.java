package com.autumn.runtime.session;

import java.io.Serializable;

/**
 * 设备信息
 * <p>
 * </p>
 *
 * @description TODO
 * @author: 老码农
 * @create: 2020-04-19 23:12
 **/
public interface AutumnUserDeviceInfo extends Serializable {

    /**
     * 获取设备 Toekn
     *
     * @return
     */
    String getDeviceToken();

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

    /**
     * 获取过期时间(秒)
     *
     * @return
     */
    Integer getExpires();

}
