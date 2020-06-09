package com.autumn.mybatis.provider.mysql.builder;

import com.autumn.mybatis.metadata.EntityTable;
import com.autumn.mybatis.provider.builder.AbstractUpdateBuilder;
import com.autumn.mybatis.provider.mysql.MySqlProvider;
import com.autumn.mybatis.provider.util.MybatisSqlUtils;

/**
 * Mysql 更新生成器
 *
 * @author 老码农
 * <p>
 * 2017-10-19 08:35:21
 */
public class MySqlUpdateBuilder extends AbstractUpdateBuilder {

    /**
     * @param dbProvider
     */
    public MySqlUpdateBuilder(MySqlProvider dbProvider) {
        super(dbProvider);
    }

    @Override
    public String getUpdateByWhereCommand(EntityTable table, String parmaName) {
        StringBuilder sql = new StringBuilder();
        sql.append("UPDATE ");
        sql.append(MybatisSqlUtils.getTableOrViewName(table, this.getProvider(), parmaName));
        sql.append("<set>");
        sql.append("<foreach collection=\"" + parmaName + ".updateClauses\" item=\"item\" separator=\",\">");
        sql.append(String.format("%s${item.columnName}%s = #{item.value}", this.getProvider().getSafeNamePrefix(),
                this.getProvider().getSafeNameSuffix()));
        sql.append("</foreach>");
        sql.append("</set>");
        sql.append(this.getProvider().getXmlBuilder().getWhereCommand(parmaName));
        return sql.toString();
    }
}
