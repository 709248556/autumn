package com.autumn.zero.authorization.entities.common;

import com.autumn.audited.annotation.LogMessage;
import com.autumn.constants.SettingConstants;
import com.autumn.mybatis.mapper.annotation.*;
import com.autumn.validation.annotation.NotNullOrBlank;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.apache.ibatis.type.JdbcType;
import org.hibernate.validator.constraints.Length;

import javax.persistence.Column;
import javax.persistence.Table;

/**
 * 用户外部授权登录
 *
 * @author 老码农 2018-11-25 01:57:42
 */
@ToString(callSuper = true)
@Getter
@Setter
@Table(name = SettingConstants.SYS_TABLE_PREFIX + "_user_external_auth_login")
@TableDocument(value = "用户外部授权登录", group = "系统表", groupOrder = Integer.MAX_VALUE)
public class UserExternalAuthLogin extends AbstractUserChild {

    /**
     *
     */
    private static final long serialVersionUID = 6750966862538795503L;

    /**
     * 最大提供者长度
     */
    public static final int MAX_PROVIDER_LENGTH = 128;

    /**
     * 最大提供者键长度
     */
    public static final int MAX_PROVIDER_KEY_LENGTH = 256;

    /**
     * 字段 provider
     */
    public static final String FIELD_PROVIDER = "provider";

    /**
     * 字段 providerKey
     */
    public static final String FIELD_PROVIDER_KEY = "providerKey";

    /**
     * 第三方提供者
     */
    @NotNullOrBlank(message = "第三方提供者名称不能为空")
    @Length(max = MAX_PROVIDER_LENGTH, message = "第三方提供者名称长度不能超过" + MAX_PROVIDER_LENGTH + "个字。")
    @Column(name = "provider", nullable = false, length = MAX_PROVIDER_LENGTH)
    @ColumnType(jdbcType = JdbcType.VARCHAR)
    @Index
    @ColumnOrder(2)
    @LogMessage(name = "第三方提供者", order = 1)
    @ColumnDocument("第三方提供者")
    private String provider;

    /**
     * 第三方提供者键
     */
    @NotNullOrBlank(message = "第三方提供键不能为空")
    @Length(max = MAX_PROVIDER_KEY_LENGTH, message = "第三方提供键长度不能超过" + MAX_PROVIDER_KEY_LENGTH + "个字。")
    @Column(name = "provider_key", nullable = false, length = MAX_PROVIDER_KEY_LENGTH)
    @ColumnType(jdbcType = JdbcType.VARCHAR)
    @ColumnOrder(3)
    @LogMessage(name = "第三方提供键", order = 2)
    @ColumnDocument("第三方提供键")
    private String providerKey;

}
