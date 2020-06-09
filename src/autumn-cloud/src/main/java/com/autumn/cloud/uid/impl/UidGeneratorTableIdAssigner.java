package com.autumn.cloud.uid.impl;

import com.autumn.cloud.uid.UidGenerator;
import com.autumn.mybatis.mapper.TableIdAssigner;
import com.autumn.mybatis.metadata.EntityColumn;
import com.autumn.mybatis.metadata.EntityTable;

/**
 * 通过{@link UidGenerator}分配表的主键
 *
 * @Description TODO
 * @Author 老码农
 * @Date 2019-08-26 19:24
 */
public class UidGeneratorTableIdAssigner implements TableIdAssigner {

    private UidGenerator uidGenerator;

    /**
     * 设置Uid分配器
     *
     * @param uidGenerator
     */
    public void setUidGenerator(UidGenerator uidGenerator) {
        this.uidGenerator = uidGenerator;
    }

    @Override
    public Object nextId(EntityTable table, EntityColumn keyColumn) {
        return uidGenerator.newUID().getUid();
    }
}
