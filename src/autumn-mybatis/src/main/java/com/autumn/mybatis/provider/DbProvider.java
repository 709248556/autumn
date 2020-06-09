package com.autumn.mybatis.provider;

import com.autumn.mybatis.metadata.EntityTable;

/**
 * 数据库提供者
 *
 * @author 老码农
 * <p>
 * 2017-10-11 09:38:36
 */
public interface DbProvider {

    /**
     * 获取驱动类型
     *
     * @return
     */
    ProviderDriveType getDriveType();

    /**
     * 获取驱动名称
     *
     * @return
     */
    String getDriveName();

    /**
     * 获取插入生成器
     *
     * @return
     */
    InsertBuilder getInsertBuilder();

    /**
     * 获取删除生成器
     *
     * @return
     */
    DeleteBuilder getDeleteBuilder();

    /**
     * 获取更新生成器
     *
     * @return
     */
    UpdateBuilder getUpdateBuilder();

    /**
     * 获取查询生成器
     *
     * @return
     */
    QueryBuilder getQueryBuilder();

    /**
     * 获取 Xml 生成器
     *
     * @return
     */
    XmlBuilder getXmlBuilder();

    /**
     * 获取定义生成器
     *
     * @return
     */
    DefinitionBuilder getDefinitionBuilder();

    /**
     * 获取命令生成器
     *
     * @return
     */
    CommandBuilder getCommandBuilder();

    /**
     * 获取安全的表名称
     *
     * @param table 表
     * @return
     */
    String getSafeTableName(EntityTable table);

    /**
     * 获取安全的表名称
     *
     * @param tableName 名称
     * @return
     */
    String getSafeTableName(String tableName);

    /**
     * 获取安全名称
     *
     * @param name 名称
     * @return
     */
    String getSafeName(String name);

    /**
     * 获取安全名称前缀
     *
     * @return
     */
    String getSafeNamePrefix();

    /**
     * 获取安全名称后缀
     *
     * @return
     */
    String getSafeNameSuffix();

}
