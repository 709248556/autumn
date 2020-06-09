package com.autumn.zero.authorization.entities.common;

import com.autumn.annotation.FriendlyProperty;
import com.autumn.audited.annotation.LogMessage;
import com.autumn.constants.SettingConstants;
import com.autumn.mybatis.mapper.annotation.ColumnDocument;
import com.autumn.mybatis.mapper.annotation.ColumnOrder;
import com.autumn.mybatis.mapper.annotation.Index;
import com.autumn.mybatis.mapper.annotation.TableDocument;
import com.autumn.validation.annotation.NotNullOrBlank;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.hibernate.validator.constraints.Length;

import javax.persistence.Column;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import java.util.Date;

/**
 * 用户授权登录
 * <p>
 * </p>
 *
 * @description TODO
 * @author: 老码农
 * @create: 2020-04-20 00:01
 **/
@ToString(callSuper = true)
@Setter
@Getter
@Table(name = SettingConstants.SYS_TABLE_PREFIX + "_user_device_auth_login")
@TableDocument(value = "用户设备授权登录", group = "系统表", groupOrder = Integer.MAX_VALUE)
public class UserDeviceAuthLogin extends AbstractUserChild {

    private static final long serialVersionUID = 4784619252839089542L;

    /**
     * 字段 token (票据)
     */
    public static final String FIELD_TOKEN = "token";

    /**
     * 字段 deviceType (设备类型)
     */
    public static final String FIELD_DEVICE_TYPE = "deviceType";

    /**
     * 字段 platformName (平台名称)
     */
    public static final String FIELD_PLATFORM_NAME = "platformName";

    /**
     * 字段 platformVersion (平台版本)
     */
    public static final String FIELD_PLATFORM_VERSION = "platformVersion";

    /**
     * 字段 applicationName (应用名称)
     */
    public static final String FIELD_APPLICATION_NAME = "applicationName";

    /**
     * 字段 applicationVersion (应用版本)
     */
    public static final String FIELD_APPLICATION_VERSION = "applicationVersion";

    /**
     * 字段 deviceIdentification (设备标识)
     */
    public static final String FIELD_DEVICE_IDENTIFICATION = "deviceIdentification";

    /**
     * 字段 firstLoginTime (首次登录时间)
     */
    public static final String FIELD_FIRST_LOGIN_TIME = "firstLoginTime";

    /**
     * 字段 expires (过期时间(秒))
     */
    public static final String FIELD_EXPIRES = "expires";

    /**
     * 字段 lastLoginTime (最后登录时间)
     */
    public static final String FIELD_LAST_LOGIN_TIME = "lastLoginTime";

    /**
     * 字段 token (票据) 最大长度
     */
    public static final int MAX_LENGTH_TOKEN = 128;

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
     * 票据
     */
    @NotNullOrBlank(message = "票据不能为空。")
    @Length(max = MAX_LENGTH_TOKEN, message = "票据 不能超过 " + MAX_LENGTH_TOKEN + " 个字。")
    @Column(nullable = false, length = MAX_LENGTH_TOKEN)
    @ColumnOrder(1)
    @Index(unique = false)
    @ColumnDocument("票据")
    @FriendlyProperty(value = "票据")
    @LogMessage(order = 1)
    private String token;

    /**
     * 设备类型
     */
    @NotNullOrBlank(message = "设备类型不能为空。")
    @Length(max = MAX_LENGTH_DEVICE_TYPE, message = "设备类型 不能超过 " + MAX_LENGTH_DEVICE_TYPE + " 个字。")
    @Column(nullable = false, length = MAX_LENGTH_DEVICE_TYPE)
    @ColumnOrder(2)
    @ColumnDocument("设备类型")
    @FriendlyProperty(value = "设备类型")
    @LogMessage(order = 2)
    private String deviceType;

    /**
     * 平台名称
     */
    @NotNullOrBlank(message = "平台名称不能为空。")
    @Length(max = MAX_LENGTH_PLATFORM_NAME, message = "平台名称 不能超过 " + MAX_LENGTH_PLATFORM_NAME + " 个字。")
    @Column(nullable = false, length = MAX_LENGTH_PLATFORM_NAME)
    @ColumnOrder(3)
    @ColumnDocument("平台名称")
    @FriendlyProperty(value = "平台名称")
    @LogMessage(order = 3)
    private String platformName;

    /**
     * 平台版本
     */
    @Length(max = MAX_LENGTH_PLATFORM_VERSION, message = "平台版本 不能超过 " + MAX_LENGTH_PLATFORM_VERSION + " 个字。")
    @Column(nullable = false, length = MAX_LENGTH_PLATFORM_VERSION)
    @ColumnOrder(4)
    @ColumnDocument("平台版本")
    @FriendlyProperty(value = "平台版本")
    @LogMessage(order = 4)
    private String platformVersion;

    /**
     * 应用名称
     */
    @NotNullOrBlank(message = "应用名称不能为空。")
    @Length(max = MAX_LENGTH_APPLICATION_NAME, message = "应用名称 不能超过 " + MAX_LENGTH_APPLICATION_NAME + " 个字。")
    @Column(nullable = false, length = MAX_LENGTH_APPLICATION_NAME)
    @ColumnOrder(5)
    @ColumnDocument("应用名称")
    @FriendlyProperty(value = "应用名称")
    @LogMessage(order = 5)
    private String applicationName;

    /**
     * 应用版本
     */
    @Length(max = MAX_LENGTH_APPLICATION_VERSION, message = "应用版本 不能超过 " + MAX_LENGTH_APPLICATION_VERSION + " 个字。")
    @Column(nullable = false, length = MAX_LENGTH_APPLICATION_VERSION)
    @ColumnOrder(6)
    @ColumnDocument("应用版本")
    @FriendlyProperty(value = "应用版本")
    @LogMessage(order = 6)
    private String applicationVersion;

    /**
     * 设备标识
     */
    @NotNullOrBlank(message = "设备标识不能为空。")
    @Length(max = MAX_LENGTH_DEVICE_IDENTIFICATION, message = "设备标识 不能超过 " + MAX_LENGTH_DEVICE_IDENTIFICATION + " 个字。")
    @Column(nullable = false, length = MAX_LENGTH_DEVICE_IDENTIFICATION)
    @ColumnOrder(7)
    @ColumnDocument("设备标识")
    @FriendlyProperty(value = "设备标识")
    @LogMessage(order = 7)
    private String deviceIdentification;

    /**
     * 首次登录时间
     */
    @Column(nullable = true)
    @ColumnOrder(8)
    @ColumnDocument("首次登录时间")
    @FriendlyProperty(value = "首次登录时间")
    @LogMessage(order = 8)
    private Date firstLoginTime;

    /**
     * 过期时间(秒)
     */
    @NotNull(message = "过期时间不能为空。")
    @Column(nullable = false)
    @ColumnOrder(9)
    @ColumnDocument("过期时间(秒)")
    @FriendlyProperty(value = "过期时间(秒)")
    @LogMessage(order = 9)
    private Integer expires;

    /**
     * 最后登录时间
     */
    @Column(nullable = true)
    @ColumnOrder(10)
    @ColumnDocument("最后登录时间")
    @FriendlyProperty(value = "最后登录时间")
    @LogMessage(order = 10)
    private Date lastLoginTime;

    /**
     * 加载目标
     *
     * @param target 目标
     * @return
     */
    public int loadTarget(UserDeviceAuthLogin target) {
        int count = 0;
        if (!this.getToken().equals(target.getToken())) {
            this.setToken(target.getToken());
            count++;
        }
        if (!this.getDeviceType().equals(target.getDeviceType())) {
            this.setDeviceType(target.getDeviceType());
            count++;
        }
        if (!this.getPlatformName().equals(target.getPlatformName())) {
            this.setPlatformName(target.getPlatformName());
            count++;
        }
        if (!this.getPlatformVersion().equals(target.getPlatformVersion())) {
            this.setPlatformVersion(target.getPlatformVersion());
            count++;
        }
        if (!this.getApplicationName().equals(target.getApplicationName())) {
            this.setApplicationName(target.getApplicationName());
            count++;
        }
        if (!this.getApplicationVersion().equals(target.getApplicationVersion())) {
            this.setApplicationVersion(target.getApplicationVersion());
            count++;
        }
        if (!this.getDeviceIdentification().equals(target.getDeviceIdentification())) {
            this.setDeviceIdentification(target.getDeviceIdentification());
            count++;
        }
        if (!this.getExpires().equals(target.getExpires())) {
            this.setExpires(target.getExpires());
            count++;
        }
        if (target.getFirstLoginTime() != null) {
            this.setFirstLoginTime(target.getFirstLoginTime());
            count++;
        }
        if (target.getLastLoginTime() != null) {
            this.setLastLoginTime(target.getLastLoginTime());
            count++;
        }
        return count;
    }

}
