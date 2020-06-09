package com.autumn.mybatis.metadata;

import com.autumn.util.ValuedEnum;

import java.io.Serializable;

/**
 * 实体关联模式
 *
 * @Description TODO
 * @Author 老码农
 * @Date 2019-08-28 2:19
 */
public enum EntityRelationMode implements ValuedEnum<Integer>, Serializable {

    /**
     * 一对一
     */
    ONE_TO_ONE(1),
    /**
     * 一对多
     */
    ONE_TO_MANY(2);

    private int value;

    private EntityRelationMode(int value) {
        this.value = value;
    }

    @Override
    public Integer value() {
        return this.value;
    }
}
