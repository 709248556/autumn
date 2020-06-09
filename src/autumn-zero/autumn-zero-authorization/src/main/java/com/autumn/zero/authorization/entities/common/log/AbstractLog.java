package com.autumn.zero.authorization.entities.common.log;

import com.autumn.domain.entities.auditing.gmt.AbstractGmtCreateAuditingEntity;
import com.autumn.mybatis.mapper.annotation.ColumnDocument;
import com.autumn.mybatis.mapper.annotation.ColumnOrder;
import com.autumn.mybatis.mapper.annotation.ColumnType;
import com.autumn.mybatis.mapper.annotation.Index;
import com.autumn.util.StringUtils;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.apache.ibatis.type.JdbcType;

import javax.persistence.Column;

/**
 * 日志抽象
 *
 * @Description TODO
 * @Author 老码农
 * @Date 2019-07-28 1:49
 */
@Getter
@Setter
@ToString(callSuper = true)
public abstract class AbstractLog extends AbstractGmtCreateAuditingEntity<Long> {

    private static final long serialVersionUID = -5323091002196124042L;

    /**
     * 字段 userId (用户id)
     */
    public static final String FIELD_USER_ID = "userId";

    /**
     * 字段 userName (用户名称)
     */
    public static final String FIELD_USER_NAME = "userName";

    /**
     * 字段 clientIpAddress (客户端ip)
     */
    public static final String FIELD_CLIENT_IP_ADDRESS = "clientIpAddress";

    /**
     * 字段 clientName (客户端名称)
     */
    public static final String FIELD_CLIENT_NAME = "clientName";

    /**
     * 字段 clientVersion (客户端版本)
     */
    public static final String FIELD_CLIENT_VERSION = "clientVersion";

    /**
     * 字段 browserName (浏览器名称)
     */
    public static final String FIELD_BROWSER_NAME = "browserName";

    /**
     * 字段 browserPlatform (浏览器平台)
     */
    public static final String FIELD_BROWSER_PLATFORM = "browserPlatform";

    /**
     * 字段 browserInfo (浏览器信息)
     */
    public static final String FIELD_BROWSER_INFO = "browserInfo";

    /**
     * 字段 userName (用户名称) 最大长度
     */
    public static final int MAX_LENGTH_USER_NAME = 100;

    /**
     * 字段 clientIpAddress (客户端ip) 最大长度
     */
    public static final int MAX_LENGTH_CLIENT_IP_ADDRESS = 128;

    /**
     * 字段 clientName (客户端名称) 最大长度
     */
    public static final int MAX_LENGTH_CLIENT_NAME = 128;

    /**
     * 字段 clientVersion (客户端版本) 最大长度
     */
    public static final int MAX_LENGTH_CLIENT_VERSION = 64;

    /**
     * 字段 browserName (浏览器名称) 最大长度
     */
    public static final int MAX_LENGTH_BROWSER_NAME = 128;

    /**
     * 用户id
     */
    @Column(nullable = true)
    @ColumnOrder(1)
    @Index(unique = false)
    @ColumnDocument("用户id")
    private Long userId;

    /**
     * 用户名称
     */
    @Column(nullable = false, length = MAX_LENGTH_USER_NAME)
    @ColumnOrder(2)
    @Index(unique = false)
    @ColumnDocument("用户名称")
    private String userName;

    /**
     * 客户端ip
     */
    @Column(nullable = false, length = MAX_LENGTH_CLIENT_IP_ADDRESS)
    @ColumnOrder(100)
    @Index(unique = false)
    @ColumnDocument("客户端ip")
    private String clientIpAddress;

    /**
     * 客户端名称
     */
    @Column(nullable = false, length = MAX_LENGTH_CLIENT_NAME)
    @ColumnOrder(101)
    @Index(unique = false)
    @ColumnDocument("客户端名称")
    private String clientName;

    /**
     * 客户端版本
     */
    @Column(nullable = false, length = MAX_LENGTH_CLIENT_VERSION)
    @ColumnOrder(102)
    @ColumnDocument("客户端版本")
    private String clientVersion;

    /**
     * 浏览器名称
     */
    @Column(nullable = false, length = MAX_LENGTH_BROWSER_NAME)
    @ColumnOrder(103)
    @ColumnDocument("浏览器名称")
    private String browserName;

    /**
     * 浏览器平台
     */
    @ColumnType(jdbcType = JdbcType.NCLOB)
    @Column(nullable = false)
    @ColumnOrder(104)
    @ColumnDocument("浏览器平台")
    private String browserPlatform;

    /**
     * 浏览器信息
     */
    @ColumnType(jdbcType = JdbcType.NCLOB)
    @Column(nullable = false)
    @ColumnOrder(105)
    @ColumnDocument("浏览器信息")
    private String browserInfo;

    @Override
    public void forNullToDefault() {
        super.forNullToDefault();
        this.setUserName(StringUtils.getLeft(this.getUserName(), MAX_LENGTH_USER_NAME));
        this.setClientIpAddress(StringUtils.getLeft(this.getClientIpAddress(), MAX_LENGTH_CLIENT_IP_ADDRESS));
        this.setClientName(StringUtils.getLeft(this.getClientName(), MAX_LENGTH_CLIENT_NAME));
        this.setClientVersion(StringUtils.getLeft(this.getClientVersion(), MAX_LENGTH_CLIENT_VERSION));
        this.setBrowserName(StringUtils.getLeft(this.getBrowserName(), MAX_LENGTH_BROWSER_NAME));
        this.setBrowserPlatform(StringUtils.getLeft(this.getBrowserPlatform(), MAX_LENGTH_BROWSER_NAME));
    }
}
