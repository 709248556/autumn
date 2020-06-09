package com.autumn.mybatis.mapper;

import com.autumn.mybatis.metadata.EntityColumn;
import com.autumn.mybatis.metadata.EntityTable;

/**
 * 表id分配器
 * <p>
 * 主键生成方式为 {@link javax.persistence.GenerationType#TABLE} 的表提供
 * </p>
 *
 * @Description TODO
 * @Author 老码农
 * @Date 2019-08-26 17:56
 */
public interface TableIdAssigner {

    /**
     * 生成下一个id
     *
     * @param table     表
     * @param keyColumn 主键列
     * @return 返回下一个主键id
     */
    Object nextId(EntityTable table, EntityColumn keyColumn);

}
