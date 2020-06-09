package com.autumn.mybatis.metadata;

import com.autumn.util.StringUtils;

import java.io.Serializable;
import java.util.List;

/**
 * 实体索引
 *
 * @author 老码农
 * <p>
 * Description
 * </p>
 * @date 2018-01-20 23:48:19
 */
public final class EntityIndex implements Serializable {

    private static final long serialVersionUID = -6505925123801137577L;

    private final EntityTable table;
    private final String name;
    private final boolean unique;
    private final List<EntityColumn> columns;

    /**
     * 实例化
     *
     * @param table   表
     * @param name    名称
     * @param unique  是否唯一
     * @param columns 列集合
     */
    EntityIndex(EntityTable table, String name, boolean unique, List<EntityColumn> columns) {
        super();
        this.table = table;
        this.name = name;
        this.unique = unique;
        this.columns = columns;
    }

    /**
     * 获取表
     *
     * @return
     */
    public EntityTable getTable() {
        return table;
    }

    /**
     * 获取索引名称(
     *
     * @return
     */
    public String getName() {
        return name;
    }

    /**
     * 获取自动名称
     *
     * @param prefix    前缀
     * @param tableName 表名
     * @return
     */
    public String getAutoName(String prefix, String tableName) {
        if (!StringUtils.isNullOrBlank(this.getName())) {
            return this.getName();
        }
        if (StringUtils.isNullOrBlank(prefix)) {
            prefix = "IX";
        }
        if (StringUtils.isNullOrBlank(tableName)) {
            tableName = this.getTable().getName();
        }
        StringBuilder builder = new StringBuilder();
        builder.append(prefix).append("_");
        builder.append(tableName).append("_");
        for (int i = 0; i < columns.size(); i++) {
            EntityColumn column = columns.get(i);
            if (i > 0) {
                builder.append("_");
            }
            builder.append(column.getColumnName());
        }
        return builder.toString();
    }

    /**
     * 获取是否唯一
     *
     * @return
     */
    public boolean isUnique() {
        return unique;
    }

    /**
     * 获取列集合
     *
     * @return
     */
    public List<EntityColumn> getColumns() {
        return columns;
    }

    @Override
    public String toString() {
        StringBuilder builder = new StringBuilder();
        builder.append(this.getAutoName(null, null));
        builder.append("(");
        for (int i = 0; i < this.getColumns().size(); i++) {
            EntityColumn column = this.getColumns().get(i);
            if (i > 0) {
                builder.append(",");
            }
            builder.append(column.getColumnName());
        }
        builder.append(")");
        return builder.toString();
    }
}
