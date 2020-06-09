package com.autumn.cloud.uid.worker;

import com.autumn.domain.entities.Entity;

/**
 * 分配节点
 *
 * @Description TODO
 * @Author 老码农
 * @Date 2019-08-26 2:33
 */
public interface AssignNode extends Entity<Long> {

    /**
     * 获取起步时间秒数
     *
     * @return
     */
    Long getEpochSeconds();
}
