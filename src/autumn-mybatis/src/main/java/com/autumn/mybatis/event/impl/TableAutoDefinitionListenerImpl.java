package com.autumn.mybatis.event.impl;

import com.autumn.exception.ExceptionUtils;
import com.autumn.mybatis.event.TableAutoDefinitionListener;
import com.autumn.mybatis.factory.DynamicDataSourceRouting;
import com.autumn.mybatis.metadata.EntityTable;
import com.autumn.mybatis.provider.DefinitionBuilder;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.core.Ordered;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.Collection;

/**
 * 表定义事件
 * <p>
 * </p>
 *
 * @description TODO
 * @author: 老码农
 * @create: 2020-04-05 14:21
 **/
public class TableAutoDefinitionListenerImpl implements TableAutoDefinitionListener, Ordered {

    private final Log log = LogFactory.getLog(TableAutoDefinitionListenerImpl.class);

    @Override
    public void init(DataSource dataSource, DynamicDataSourceRouting dataSourceRouting) {
        Connection conn = null;
        try {
            String dataSourceUrl = dataSourceRouting
                    .getProvider()
                    .getDefinitionBuilder()
                    .getDataSourceUrl(dataSource);
            log.info("开始检查未生成表的实体...,数据源[" + dataSourceUrl + "]");
            int count = 0;
            int okCount = 0;
            Collection<EntityTable> tables = EntityTable.getTables();
            conn = dataSource.getConnection();
            for (EntityTable table : tables) {
                if (!table.isView() && dataSourceRouting.isIncludeTable(table)) {
                    count++;
                    if (this.checkOrCreateTable(conn, dataSourceRouting.getProvider().getDefinitionBuilder(), table)) {
                        okCount++;
                    }
                }
            }
            log.info("生成完成，实体表总数[" + count + "]个，本次生成[" + okCount + "]个，无需生成[" + (count - okCount) + "]个，数据源[" + dataSourceUrl + "]");
        } catch (Exception err) {
            // log.error(err.getMessage(), err);
            throw ExceptionUtils.throwConfigureException(err.getMessage(), err);
        } finally {
            if (conn != null) {
                try {
                    conn.close();
                } catch (SQLException e) {

                }
            }
        }
    }

    /**
     * @param conn
     * @param definitionBuilder
     * @param database
     * @param table
     * @return
     * @throws SQLException
     */
    private boolean checkOrCreateTable(Connection conn, DefinitionBuilder definitionBuilder, EntityTable table) throws SQLException {
        if (!definitionBuilder.existTable(conn, table)) {
            definitionBuilder.createTable(conn, table);
            return true;
        }
        return false;
    }

    @Override
    public void close(DataSource dataSource, DynamicDataSourceRouting dataSourceRouting) {

    }

    @Override
    public int getOrder() {
        return TableAutoDefinitionListener.BEAN_BEGIN_ORDER;
    }
}
