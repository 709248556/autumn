package com.autumn.mybatis.event;

import com.autumn.mybatis.factory.DynamicDataSourceRouting;
import org.springframework.core.Ordered;

import javax.sql.DataSource;

/**
 * 数据源监听
 * <p>
 * 在Spring boot 启动完成后，才会开始触发，若之前已产生数据源连接则会推迟到启动完成再触发。
 * </p>
 *
 * @description TODO
 * @author: 老码农
 * @create: 2020-04-05 13:33
 **/
public interface DataSourceListener extends Ordered {

    /**
     * 初始化
     *
     * @param dataSource        数据源
     * @param dataSourceRouting 数据源路油
     */
    void init(DataSource dataSource, DynamicDataSourceRouting dataSourceRouting);

    /**
     * 关闭
     *
     * @param dataSource        数据源
     * @param dataSourceRouting 数据源路油
     */
    void close(DataSource dataSource, DynamicDataSourceRouting dataSourceRouting);
}
