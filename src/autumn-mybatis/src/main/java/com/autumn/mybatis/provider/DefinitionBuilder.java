package com.autumn.mybatis.provider;

import com.autumn.mybatis.metadata.EntityTable;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.Collection;

/**
 * 定义生成器
 *
 * @author 老码农
 * <p>
 * Description
 * </p>
 * @date 2018-01-20 11:53:02
 */
public interface DefinitionBuilder extends Builder {

    /**
     * 创建注释
     *
     * @param text 字符
     * @return
     */
    String createAnnotation(String text);

    /**
     * 是否存在表SQL命
     *
     * @param database 数据库名称
     * @param table    实体表
     * @return 执行后返回 >0 表示存在，否则不存在
     */
    String existTableSqlCommand(String database, EntityTable table);

    /**
     * 创建表脚本集合
     *
     * @param entityPackages 实体包集合
     * @return
     */
    String createTableScripts(String... entityPackages);

    /**
     * 创建表脚本
     *
     * @param table 表
     * @return
     */
    String createTableScript(EntityTable table);

    /**
     * 创建表脚本
     *
     * @param table     表
     * @param tableName 表名称
     * @return
     */
    String createTableScript(EntityTable table, String tableName);

    /**
     * 创建方案文档
     *
     * @param documentInfo   文档信息
     * @param entityPackages 包集合
     * @return
     */
    String createProjectDocument(DbDocumentInfo documentInfo, String... entityPackages);

    /**
     * 创建方案文档
     *
     * @param documentInfo 文档信息
     * @param tables       表集合
     * @return
     */
    String createProjectDocument(DbDocumentInfo documentInfo, Collection<EntityTable> tables);

    /**
     * 创建表文档
     *
     * @param table             表
     * @param isBindEntityClass 是否绑定实体类型
     * @return
     */
    String createTableDocument(EntityTable table, boolean isBindEntityClass);

    /**
     * 创建表文档
     *
     * @param table             表
     * @param tableName         表名称
     * @param isBindEntityClass 是否绑定实体类型
     * @return
     */
    String createTableDocument(EntityTable table, String tableName, boolean isBindEntityClass);

    /**
     * 获取数据源 url
     *
     * @param dataSource 数据源
     * @return
     */
    String getDataSourceUrl(DataSource dataSource);

    /**
     * 获取数据库
     *
     * @param dataSource 数据源
     * @return
     * @throws SQLException Sql异常
     */
    String getDatabase(DataSource dataSource) throws SQLException;

    /**
     * 是否存在
     *
     * @param connection 连接
     * @param table      表
     * @throws SQLException Sql异常
     */
    boolean existTable(Connection connection, EntityTable table) throws SQLException;

    /**
     * 创建表
     *
     * @param connection 连接
     * @param table      表
     * @throws SQLException Sql异常
     */
    void createTable(Connection connection, EntityTable table) throws SQLException;
}
