package com.autumn.mybatis.provider;

import com.autumn.mybatis.provider.mysql.MySqlProvider;
import com.autumn.mybatis.provider.postgresql.PostgreSqlProvider;
import com.autumn.mybatis.provider.sqlite.SqliteProvider;
import com.autumn.mybatis.provider.sqlserver.SqlServerProvider;
import com.autumn.mybatis.provider.sqlserver.SqlServerProvider2012;
import com.autumn.util.StringUtils;

/**
 * 数据驱动类型 描述：
 *
 * @author 老码农 2018-03-17 21:02:14
 */
public enum ProviderDriveType {

    /**
     * MySql 数据库 {@link com.mysql.cj.jdbc.Driver}
     */
    MY_SQL(MySqlProvider.class, "jdbc:mysql", "com.mysql.cj.jdbc.Driver"),

    /**
     * PostgreSql 数据库 {@link org.postgresql.Driver}
     */
    POSTGRE_SQL(PostgreSqlProvider.class, "jdbc:postgresql", "org.postgresql.Driver"),

    /**
     * Oracle 数据库 {@link oracle.jdbc.driver.OracleDriver}
     */
    ORACLE(null, "jdbc:oracle", "oracle.jdbc.driver.OracleDriver"),

    /**
     * Sql Server 数据库(2005-2008) {@link com.microsoft.sqlserver.jdbc.SQLServerDriver}
     */
    SQL_SERVER(SqlServerProvider.class, "jdbc:sqlserver", "com.microsoft.sqlserver.jdbc.SQLServerDriver"),

    /**
     * Sql Server 数据库(>=2012) {@link com.microsoft.sqlserver.jdbc.SQLServerDriver}
     */
    SQL_SERVER_2012(SqlServerProvider2012.class, "jdbc:sqlserver", "com.microsoft.sqlserver.jdbc.SQLServerDriver"),

    /**
     * DB2 数据库 {@link com.ibm.db2.jcc.DB2Driver}
     */
    DB2(null, "jdbc:db2", "com.ibm.db2.jcc.DB2Driver"),

    /**
     * MariaDB 数据库 {@link org.mariadb.jdbc.Driver}
     */
    MARIADB(MySqlProvider.class, "jdbc:mariadb", "org.mariadb.jdbc.Driver"),

    /**
     * Sybase 数据库 {@link com.sybase.jdbc4.jdbc.SybDriver}
     */
    SYBASE(null, "jdbc:sybase", "com.sybase.jdbc4.jdbc.SybDriver"),

    /**
     * SQLite 数据库 {@link org.sqlite.JDBC}
     */
    SQLITE(SqliteProvider.class, "jdbc:sqlite", "org.sqlite.JDBC"),

    /**
     * Access 连接类型 {@link sun.jdbc.odbc.JdbcOdbcDriver}
     */
    ACCESS(null, "jdbc:odbc", "sun.jdbc.odbc.JdbcOdbcDriver"),

    /**
     * OceanBase 分布式关系数据库 {@link com.mysql.cj.jdbc.Driver}
     */
    OCEAN_BASE(MySqlProvider.class, "jdbc:oceanbase", "com.mysql.cj.jdbc.Driver"),

    /**
     * 根据连接窜的 Url 自动查找
     */
    AUTO(null, "", ""),

    /**
     * 自定义或未知
     */
    CUSTOM(null, "", "");

    private final Class<? extends DbProvider> providerType;
    private final String urlPrefix;
    private final String driveClassName;

    /**
     * 实
     *
     * @param providerType
     * @param urlPrefix
     * @param driveClassName
     */
    ProviderDriveType(Class<? extends DbProvider> providerType, String urlPrefix, String driveClassName) {
        this.providerType = providerType;
        this.urlPrefix = urlPrefix;
        this.driveClassName = driveClassName;
    }

    /**
     * 获取驱动类型
     *
     * @return
     */
    public Class<? extends DbProvider> getProviderType() {
        return providerType;
    }

    /**
     * 获取 Url 前缀
     *
     * @return
     */
    public String getUrlPrefix() {
        return urlPrefix;
    }

    /**
     * 获取提供者类名称
     *
     * @return
     */
    public String getDriveClassName() {
        return this.driveClassName;
    }

    /**
     * 根据 Url 获取驱动类型
     *
     * @param url url 如 jdbc:mysql://127.0.0.1:3306/ds1?characterEncoding=UTF-8&zeroDateTimeBehavior=convertToNull
     * @return
     */
    public static ProviderDriveType getDriveTypeByUrl(String url) {
        if (StringUtils.isNullOrBlank(url)) {
            return ProviderDriveType.CUSTOM;
        }
        url = url.trim().toLowerCase();
        for (ProviderDriveType type : ProviderDriveType.values()) {
            if (url.startsWith(type.getUrlPrefix())) {
                return type;
            }
        }
        return ProviderDriveType.CUSTOM;
    }

}
