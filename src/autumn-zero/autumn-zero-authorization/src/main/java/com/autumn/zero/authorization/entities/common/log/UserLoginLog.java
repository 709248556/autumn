package com.autumn.zero.authorization.entities.common.log;

import com.autumn.constants.SettingConstants;
import com.autumn.mybatis.mapper.annotation.*;
import com.autumn.util.StringUtils;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.Column;
import javax.persistence.Table;

/**
 * 用户登录日志
 *
 * @author 老码农 2018-11-29 23:00:18
 */
@Getter
@Setter
@ToString(callSuper = true)
@Table(name = SettingConstants.SYS_TABLE_PREFIX + "_user_login_log")
@TableEngine("MyISAM")
@TableDocument(value = "用户登录日志", group = "系统表", groupOrder = Integer.MAX_VALUE)
public class UserLoginLog extends AbstractLog {

    /**
     *
     */
    private static final long serialVersionUID = -1865539580666139525L;

    /**
     * 字段 userAccount (登录账号)
     */
    public static final String FIELD_USER_ACCOUNT = "userAccount";

    /**
     * 字段 provider (第三方提供者)
     */
    public static final String FIELD_PROVIDER = "provider";

    /**
     * 字段 providerKey (第三方提供键)
     */
    public static final String FIELD_PROVIDER_KEY = "providerKey";

    /**
     * 字段 success (成功)
     */
    public static final String FIELD_SUCCESS = "success";

    /**
     * 字段 statusMessage (状态消息)
     */
    public static final String FIELD_STATUS_MESSAGE = "statusMessage";

    /**
     * 字段 userAccount (登录账号) 最大长度
     */
    public static final int MAX_LENGTH_USER_ACCOUNT = 100;

    /**
     * 字段 provider (第三方提供者) 最大长度
     */
    public static final int MAX_LENGTH_PROVIDER = 100;

    /**
     * 字段 providerKey (第三方提供键) 最大长度
     */
    public static final int MAX_LENGTH_PROVIDER_KEY = 255;

    /**
     * 字段 statusMessage (状态消息) 最大长度
     */
    public static final int MAX_LENGTH_STATUS_MESSAGE = 255;

    /**
     * 登录账号
     */
    @Column(nullable = false, length = MAX_LENGTH_USER_ACCOUNT)
    @ColumnOrder(10)
    @Index(unique = false)
    @ColumnDocument("登录账号")
    private String userAccount;

    /**
     * 第三方提供者
     */
    @Column(nullable = false, length = MAX_LENGTH_PROVIDER)
    @ColumnOrder(11)
    @Index(unique = false)
    @ColumnDocument("第三方提供者")
    private String provider;

    /**
     * 第三方提供键
     */
    @Column(nullable = false, length = MAX_LENGTH_PROVIDER_KEY)
    @ColumnOrder(12)
    @Index(unique = false)
    @ColumnDocument("第三方提供键")
    private String providerKey;

    /**
     * 成功
     */
    @Column(name = "is_success", nullable = false)
    @ColumnOrder(13)
    @ColumnDocument("是否成功")
    private boolean success;

    /**
     * 状态消息
     */
    @Column(nullable = false, length = MAX_LENGTH_STATUS_MESSAGE)
    @ColumnOrder(14)
    @ColumnDocument("状态消息")
    private String statusMessage;

    @Override
    public void forNullToDefault() {
        super.forNullToDefault();
        this.setUserAccount(StringUtils.getLeft(this.getUserAccount(), MAX_LENGTH_USER_ACCOUNT));
        this.setProvider(StringUtils.getLeft(this.getProvider(), MAX_LENGTH_PROVIDER));
        this.setProviderKey(StringUtils.getLeft(this.getProviderKey(), MAX_LENGTH_PROVIDER_KEY));
        this.setStatusMessage(StringUtils.getLeft(this.getStatusMessage(), MAX_LENGTH_STATUS_MESSAGE));
    }
}
