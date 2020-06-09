package com.autumn.cloud.uid.worker.entities;

import com.autumn.util.ValuedEnum;

/**
 * 工作节点类型
 *
 * @Description TODO
 * @Author 老码农
 * @Date 2019-08-25 19:28
 */
public enum WorkerNodeType implements ValuedEnum<Integer> {

    /**
     * 容器
     */
    CONTAINER(1),
    /**
     *
     */
    ACTUAL(2);

    /**
     * Lock type
     */
    private final int type;

    /**
     * Constructor with field of type
     */
    private WorkerNodeType(int type) {
        this.type = type;
    }

    @Override
    public Integer value() {
        return type;
    }
}
