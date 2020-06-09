package com.autumn.mybatis.factory;

import com.autumn.mybatis.metadata.EntityTable;
import com.autumn.mybatis.provider.DbProvider;

import javax.sql.DataSource;
import java.io.IOException;
import java.util.Set;

/**
 * 动态数据源路油
 *
 * @author 老码农
 * <p>
 * 2018-01-10 15:58:40
 */
public interface DynamicDataSourceRouting extends DataSource {

    /**
     * 获取提供者
     *
     * @return
     */
    DbProvider getProvider();

    /**
     * 获取实体包集
     *
     * @return
     */
    Set<String> getEntityPackages();

    /**
     * 获取 Mapper 接口包集
     *
     * @return
     */
    Set<String> getMapperInterfacePackages();

    /**
     * 指定的表是否包含在当前数据源中
     *
     * @param table 表
     * @return
     */
    boolean isIncludeTable(EntityTable table);

    /**
     * 获取路油Key
     *
     * @return
     */
    String getRoutingKey();

    /**
     * 关闭
     *
     * @throws IOException
     */
    void close() throws IOException;
}
