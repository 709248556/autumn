package com.autumn.mybatis.wrapper.clauses;

/**
 * 列对象
 *
 * @Description TODO
 * @Author 老码农
 * @Date 2019-06-12 0:22
 */
public class ColumnClause extends AbstractClause {

    private static final long serialVersionUID = 4020237093107895928L;

    private final String columnName;

    /**
     *
     * @param columnName
     */
    public ColumnClause(String columnName){
        this.columnName = columnName;
    }

    /**
     * 获取列名称
     * @return 返回 列名称
     */
    public final String getColumnName() {
        return this.columnName;
    }
}
