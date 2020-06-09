package com.autumn.mybatis.wrapper.clauses;

import com.autumn.util.StringUtils;

/**
 * 排序语句
 *
 * @author 老码农
 * <p>
 * 2017-10-29 12:24:46
 */
public class OrderClause extends ColumnClause {

    private static final long serialVersionUID = 6380448715667456525L;
    private final String direction;
    private String funName;

    /**
     * 实例化
     *
     * @param columnName 列名称
     * @param direction  方向
     */
    public OrderClause(String columnName, String direction) {
        super(columnName);
        this.direction = direction;
    }

    /**
     * 方向
     *
     * @return
     */
    public String getDirection() {
        return direction;
    }

    /**
     * 获取函数名称
     *
     * @return
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

    @Override
    public String toString() {
        if (StringUtils.isNullOrBlank(this.getFunName())) {
            return this.getColumnName() + " " + this.getDirection();
        }
        return this.getFunName() + "(" + this.getColumnName() + ") " + this.getDirection();
    }

}
