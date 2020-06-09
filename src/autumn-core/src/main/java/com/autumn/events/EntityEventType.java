package com.autumn.events;

import com.autumn.util.ValuedEnum;

/**
 * 实体事件类型
 * <p>
 * </p>
 *
 * @description: TODO
 * @author: 老码农
 * @create: 2019-11-30 00:18
 **/
public enum EntityEventType implements ValuedEnum<Integer> {

    /**
     * 插入
     */
    INSERT(1),
    /**
     * 更新
     */
    UPDATE(2),
    /**
     * 删除
     */
    DELETE(3);

    private final int value;

    private EntityEventType(int value) {
        this.value = value;
    }

    @Override
    public Integer value() {
        return this.value;
    }
}
