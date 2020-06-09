package com.autumn.security.token;

import com.autumn.validation.DefaultDataValidation;
import com.autumn.validation.annotation.NotNullOrBlank;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.hibernate.validator.constraints.Length;

/**
 * <p>
 * </p>
 *
 * @description TODO
 * @author: 老码农
 * @create: 2020-04-20 13:45
 **/
@Getter
@Setter
@ToString
@EqualsAndHashCode
public class DefaultCredentialsDeviceInfo extends DefaultDataValidation implements CredentialsDeviceInfo {

    private static final long serialVersionUID = 4170621371858421548L;

    /**
     * 设备类型
     * <p>
     * 如手机、平板、电视、其他物联设备
     * </p>
     */
    @Length(max = MAX_LENGTH_DEVICE_TYPE, message = "设备类型 不能超过 " + MAX_LENGTH_DEVICE_TYPE + " 个字。")
    @NotNullOrBlank(message = "设备类型不能为空。")
    private String deviceType;

    /**
     * 平台名称
     * <p>
     * 如IOS、Android、小程序等
     * </p>
     */
    @Length(max = MAX_LENGTH_PLATFORM_NAME, message = "平台名称 不能超过 " + MAX_LENGTH_PLATFORM_NAME + " 个字。")
    @NotNullOrBlank(message = "平台名称不能为空。")
    private String platformName;

    /**
     * 平台版本
     */
    @Length(max = MAX_LENGTH_PLATFORM_VERSION, message = "平台版本 不能超过 " + MAX_LENGTH_PLATFORM_VERSION + " 个字。")
    private String platformVersion;

    /**
     * 应用名称
     */
    @Length(max = MAX_LENGTH_APPLICATION_NAME, message = "应用名称 不能超过 " + MAX_LENGTH_APPLICATION_NAME + " 个字。")
    @NotNullOrBlank(message = "应用名称不能为空。")
    private String applicationName;

    /**
     * 应用名版本
     */
    @Length(max = MAX_LENGTH_APPLICATION_VERSION, message = "应用版本 不能超过 " + MAX_LENGTH_APPLICATION_VERSION + " 个字。")
    private String applicationVersion;

    /**
     * 设备标识
     * <p>
     * 如设备唯一编号或其他组合
     * </p>
     */
    @NotNullOrBlank(message = "设备标识不能为空。")
    // @Length(max = MAX_LENGTH_DEVICE_IDENTIFICATION, message = "设备标识 不能超过 " + MAX_LENGTH_DEVICE_IDENTIFICATION + " 个字。")
    private String deviceIdentification;

}
