package com.autumn.mybatis.wrapper.clauses;

/**
 * 查询列对象
 *
 * @Description TODO
 * @Author 老码农
 * @Date 2019-06-12 0:15
 */
public class SelectColumnClause extends ColumnClause {

    private static final long serialVersionUID = -3847456409560943918L;

    private String alias;

    private String funName;

    /**
     * @param columnName
     */
    public SelectColumnClause(String columnName) {
        super(columnName);
    }

    /**
     * 获取别名
     *
     * @return
     */
    public String getAlias() {
        return alias;
    }

    /**
     * 设置别名
     *
     * @param alias 别名
     */
    public void setAlias(String alias) {
        this.alias = alias;
    }

    /**
     * 获取函数名称
     *
     * @return 函数名称
     */
    public String getFunName() {
        return funName;
    }

    /**
     * 设置函数名称
     *
     * @param funName 函数名称
     */
    public void setFunName(String funName) {
        this.funName = funName;
    }

}
