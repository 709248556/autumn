package com.autumn.runtime.session;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

/**
 * 默认设备信息
 * <p>
 * </p>
 *
 * @description TODO
 * @author: 老码农
 * @create: 2020-04-19 23:43
 **/
@Getter
@Setter
@ToString
@EqualsAndHashCode
public class DefaultAutumnUserDeviceInfo implements AutumnUserDeviceInfo {

    private static final long serialVersionUID = 1660471584383595072L;

    /**
     * 设备 Token
     */
    private String deviceToken;

    /**
     * 设备类型
     * <p>
     * 如手机、平板、电视、其他物联设备
     * </p>
     */
    private String deviceType;

    /**
     * 平台名称
     * <p>
     * 如IOS、Android、小程序等
     * </p>
     */
    private String platformName;

    /**
     * 平台版本
     */

    private String platformVersion;

    /**
     * 应用名称
     */
    private String applicationName;

    /**
     * 应用名版本
     */
    private String applicationVersion;

    /**
     * 设备标识
     * <p>
     * 如设备唯一编号或其他组合
     * </p>
     */
    private String deviceIdentification;

    /**
     * 过期时间
     */
    private Integer expires;
}
