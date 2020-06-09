package com.autumn.mybatis.metadata;

import com.autumn.mybatis.wrapper.OrderDirectionEnum;

import java.io.Serializable;

/**
 * 实体列排序
 *
 * @author 老码农
 * <p>
 * 2017-10-18 22:00:47
 */
public final class EntityColumnOrder implements Serializable {

    /**
     *
     */
    private static final long serialVersionUID = 1267706848223430661L;
    private final EntityColumn column;
    private final OrderDirectionEnum direction;

    /**
     * 获取列
     *
     * @return
     */
    public EntityColumn getColumn() {
        return column;
    }

    /**
     * 获取方向
     *
     * @return
     */
    public OrderDirectionEnum getDirection() {
        return direction;
    }

    /**
     * @param column
     * @param direction
     */
    EntityColumnOrder(EntityColumn column, OrderDirectionEnum direction) {
        this.column = column;
        this.direction = direction;
    }

    @Override
    public final String toString() {
        return column.getColumnName() + " " + direction.toString();
    }

}
