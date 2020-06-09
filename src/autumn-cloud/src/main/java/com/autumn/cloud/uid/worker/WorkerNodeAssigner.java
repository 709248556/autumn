package com.autumn.cloud.uid.worker;

import com.autumn.runtime.RuntimeComponent;

/**
 * 工作Node分配
 *
 * @Description TODO
 * @Author 老码农
 * @Date 2019-08-25 19:53
 */
public interface WorkerNodeAssigner extends RuntimeComponent {

    /**
     * 默认起步日期
     */
    public static final String DEFAULT_EPOCH_DATE_STRING = "2019-01-01";

    /**
     * 分配工作节点
     *
     * @return
     */
    AssignNode assignWorkerNode();

    /**
     * 获取起步日期
     *
     * @return
     */
    String getEpochDateString();

    /**
     * 设置起步日期
     *
     * @param epochDateString
     */
    void setEpochDateString(String epochDateString);
}
