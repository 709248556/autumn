package com.autumn.cloud.uid;

import com.autumn.util.DateUtils;

import java.io.Serializable;
import java.util.Date;
import java.util.concurrent.TimeUnit;

/**
 * UID 信息
 *
 * @Description TODO
 * @Author 老码农
 * @Date 2019-08-25 21:16
 */
public final class UID implements Serializable, Comparable<UID> {

    private static final long serialVersionUID = 3861886919337921668L;

    /**
     * 全局唯一uid
     */
    private final long uid;

    /**
     * 工作id
     */
    private final long workerId;

    /**
     * 顺序号
     */
    private final long sequence;

    /**
     * 起步秒数
     */
    private final long epochSeconds;

    /**
     * 周期秒数
     */
    private final long deltaSeconds;

    /**
     * 时间戳
     */
    private final Date timestamp;

    /**
     * @param uid
     * @param workerId
     * @param sequence
     * @param epochSeconds
     * @param deltaSeconds
     */
    public UID(long uid, long workerId, long sequence, long epochSeconds, long deltaSeconds) {
        this.uid = uid;
        this.workerId = workerId;
        this.sequence = sequence;
        this.epochSeconds = epochSeconds;
        this.deltaSeconds = deltaSeconds;
        this.timestamp = new Date(TimeUnit.SECONDS.toMillis(epochSeconds + deltaSeconds));
    }

    /**
     * 获取全局唯一uid
     *
     * @return
     */
    public long getUid() {
        return this.uid;
    }

    /**
     * 获取工作机id
     *
     * @return
     */
    public long getWorkerId() {
        return this.workerId;
    }

    /**
     * 获取序号
     *
     * @return
     */
    public long getSequence() {
        return this.sequence;
    }

    /**
     * 获取起步秒数
     *
     * @return
     */
    public long getEpochSeconds() {
        return this.epochSeconds;
    }

    /**
     * 获取周期秒数
     *
     * @return
     */
    public long getDeltaSeconds() {
        return this.deltaSeconds;
    }

    /**
     * 获取时间戳
     *
     * @return
     */
    public Date getTimestamp() {
        return this.timestamp;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj instanceof UID) {
            UID uid = (UID) obj;
            return this.getUid() == uid.getUid();
        }
        return false;
    }

    @Override
    public int hashCode() {
        return Long.hashCode(this.getUid());
    }

    @Override
    public String toString() {
        return String.format("{\"UID\":\"%d\",\"timestamp\":\"%s\",\"workerId\":\"%d\",\"sequence\":\"%d\"}",
                this.getUid(), DateUtils.dateFormat(this.getTimestamp()), this.getWorkerId(), this.getSequence());
    }

    @Override
    public int compareTo(UID uid) {
        return Long.compare(this.getUid(), uid.getUid());
    }
}
