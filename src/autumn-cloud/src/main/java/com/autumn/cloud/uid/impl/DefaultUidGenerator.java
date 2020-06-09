package com.autumn.cloud.uid.impl;

import com.autumn.cloud.uid.BitsAllocator;
import com.autumn.cloud.uid.UID;
import com.autumn.cloud.uid.UidGenerateException;
import com.autumn.cloud.uid.UidGenerator;
import com.autumn.cloud.uid.worker.AssignNode;
import com.autumn.cloud.uid.worker.WorkerNodeAssigner;
import com.autumn.exception.ArgumentOverflowException;
import com.autumn.exception.SystemException;
import com.autumn.mybatis.event.TableAutoDefinitionListener;
import com.autumn.timing.Clock;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.ApplicationListener;
import org.springframework.core.Ordered;

import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.ReentrantLock;

/**
 * Represents an implementation of {@link UidGenerator}
 * <p>
 * The unique id has 64bits (long), default allocated as blow:<br>
 * <li>sign: 最高位始终为 0，即正数
 * <li>delta seconds: 31个字节时，最多支持68年((2^31-1)/86400/365)
 * <li>worker id: 16个字节时，最多支持 65535(2^16 -1) worker id
 * <li>sequence: 16个字节时, 最高支持每秒可产生 65535(2^16 -1)个id<br><br>
 * <p>
 * The {@link DefaultUidGenerator#parseUID(long)} is a tool method to parse the bits
 *
 * <pre>{@code
 * +------+----------------------+----------------+-----------+
 * | sign |     delta seconds    | worker node id | sequence  |
 * +------+----------------------+----------------+-----------+
 *   1bit          31bits              16bits         16bits
 * }</pre>
 * <p>
 * 默认配置如下
 * <li>timeBits: 默认 31
 * <li>workerBits: 默认 16
 * <li>seqBits: 默认 16
 *
 * <b>Note that:</b> 总的字节数必须是 64 -1
 *
 * @author yutianbao
 */
public class DefaultUidGenerator
        implements UidGenerator,
        ApplicationListener<ApplicationReadyEvent>, Ordered {

    /**
     * 默认时间字节数
     */
    public static final int DEFAULT_TIME_BITS = 31;

    /**
     * 默认工作机字节数
     */
    public static final int DEFAULT_WORKER_BITS = 16;

    /**
     * 默认序列号字节数
     */
    public static final int DEFAULT_SEQ_BITS = 16;

    /**
     * 日志
     */
    protected final Log logger = LogFactory.getLog(this.getClass());

    /**
     * 锁
     */
    private final ReentrantLock reentrantLock = new ReentrantLock();

    /**
     * Bits allocate
     */
    private int timeBits = DEFAULT_TIME_BITS;
    private int workerBits = DEFAULT_WORKER_BITS;
    private int seqBits = DEFAULT_SEQ_BITS;

    private boolean initialize;

    private long epochSeconds;
    private BitsAllocator bitsAllocator;
    private long workerId;

    /**
     * Volatile fields caused by nextId()
     */
    private long sequence = 0L;
    private long lastSecond = -1L;

    /**
     * Spring property
     */
    private WorkerNodeAssigner workerNodeAssigner;

    /**
     * 实例化
     */
    public DefaultUidGenerator() {
        this.initialize = false;
    }

    /**
     * 加锁
     */
    protected final void lock() {
        this.reentrantLock.lock();
    }

    /**
     * 解锁
     */
    protected final void unlock() {
        this.reentrantLock.unlock();
    }

    /**
     * 初始化节点
     */
    protected boolean initializeNode() {
        if (!this.initialize) {
            try {
                this.lock();
                if (!this.initialize) {
                    this.initialize = true;
                    // initialize bits allocator
                    bitsAllocator = new BitsAllocator(timeBits, workerBits, seqBits);
                    // initialize worker id
                    AssignNode assignNode = this.workerNodeAssigner.assignWorkerNode();
                    this.workerId = assignNode.getId();
                    this.epochSeconds = assignNode.getEpochSeconds();
                    if (this.workerId > bitsAllocator.getMaxWorkerId()) {
                        throw new SystemException("Worker id " + workerId + " exceeds the max " + bitsAllocator.getMaxWorkerId());
                    }
                    this.initialize = true;
                    logger.info(String.format("Initialized bits(1, %s, %s, %s) for workerID:%s", timeBits, workerBits, seqBits, workerId));
                    return true;
                }
            } catch (Exception err) {
                this.initialize = false;
                throw err;
            } finally {
                this.unlock();
            }
        }
        return false;
    }

    @Override
    public UID newUID() throws UidGenerateException {
        this.initializeNode();
        try {
            this.lock();
            return this.nextUID();
        } catch (UidGenerateException e) {
            logger.error(e.getMessage(), e);
            throw e;
        } catch (Exception e) {
            logger.error("Generate unique id exception. " + e.getMessage(), e);
            throw new UidGenerateException(e);
        } finally {
            this.unlock();
        }
    }

    @Override
    public UID parseUID(long uid) {
        this.initializeNode();

        long totalBits = BitsAllocator.TOTAL_BITS;
        long signBits = bitsAllocator.getSignBits();
        long timestampBits = bitsAllocator.getTimestampBits();
        long workerIdBits = bitsAllocator.getWorkerIdBits();
        long sequenceBits = bitsAllocator.getSequenceBits();

        // parse UID
        long sequence = (uid << (totalBits - sequenceBits)) >>> (totalBits - sequenceBits);
        long workerId = (uid << (timestampBits + signBits)) >>> (totalBits - workerIdBits);
        long deltaSeconds = uid >>> (workerIdBits + sequenceBits);

        return new UID(uid, workerId, sequence, this.getEpochSeconds(), deltaSeconds);
    }

    /**
     * Get UID
     *
     * @return UID
     * @throws UidGenerateException in the case: Clock moved backwards; Exceeds the max timestamp
     */
    private UID nextUID() {
        long currentSecond = getCurrentSecond();

        // Clock moved backwards, refuse to generate uid
        if (currentSecond < this.lastSecond) {
            long refusedSeconds = this.lastSecond - currentSecond;
            throw new UidGenerateException(String.format("Clock moved backwards. Refusing for %s seconds", refusedSeconds));
        }

        // At the same second, increase sequence
        if (currentSecond == this.lastSecond) {
            this.sequence = (this.sequence + 1) & bitsAllocator.getMaxSequence();
            // Exceed the max sequence, we wait the next second to generate uid
            if (this.sequence == 0) {
                currentSecond = getNextSecond(this.lastSecond);
            }
            // At the different second, sequence restart from zero
        } else {
            this.sequence = 0L;
        }
        this.lastSecond = currentSecond;
        // Allocate bits for UID
        long deltaSeconds = currentSecond - this.getEpochSeconds();
        long uid = bitsAllocator.allocate(deltaSeconds, this.workerId, this.sequence);
        return new UID(uid, this.workerId, this.sequence, this.getEpochSeconds(), deltaSeconds);
    }

    /**
     * 检查设置非初始化
     */
    protected void checkSetNotInitialize() {
        if (this.initialize) {
            throw new SystemException("已初始化，不能再设置属性。");
        }
    }

    /**
     * Get next millisecond
     */
    private long getNextSecond(long lastTimestamp) {
        long timestamp = getCurrentSecond();
        while (timestamp <= lastTimestamp) {
            timestamp = getCurrentSecond();
        }
        return timestamp;
    }

    /**
     * Get current second
     */
    private long getCurrentSecond() {
        long currentSecond = TimeUnit.MILLISECONDS.toSeconds(Clock.currentTimeMillis());
        if (currentSecond - this.getEpochSeconds() > bitsAllocator.getMaxDeltaSeconds()) {
            throw new UidGenerateException("起步时间(epochSeconds=" + this.getEpochSeconds() + ")太小，已超过支持的限定，当前时间秒为: " + currentSecond);
        }
        return currentSecond;
    }


    public int getTimeBits() {
        return this.timeBits;
    }

    public void setTimeBits(int timeBits) {
        this.checkSetNotInitialize();
        if (timeBits <= 0) {
            throw new ArgumentOverflowException("timeBits 必须大于0。");
        }
        this.timeBits = timeBits;
    }

    public int getWorkerBits() {
        return this.workerBits;
    }

    public void setWorkerBits(int workerBits) {
        this.checkSetNotInitialize();
        if (workerBits <= 0) {
            throw new ArgumentOverflowException("workerBits 必须大于0。");
        }
        this.workerBits = workerBits;
    }

    /**
     * @return
     */
    public int getSeqBits() {
        return this.seqBits;
    }

    public void setSeqBits(int seqBits) {
        this.checkSetNotInitialize();
        if (seqBits <= 0) {
            throw new ArgumentOverflowException("seqBits 必须大于0。");
        }
        this.seqBits = seqBits;
    }

    /**
     * 设置工作分配节点
     *
     * @param workerNodeAssigner
     */
    public void setWorkerNodeAssigner(WorkerNodeAssigner workerNodeAssigner) {
        this.workerNodeAssigner = workerNodeAssigner;
    }

    /**
     * 是否初始化
     *
     * @return
     */
    public final boolean isInitialize() {
        return this.initialize;
    }

    /**
     * 获取分配器
     *
     * @return
     */
    public final BitsAllocator getBitsAllocator() {
        return this.bitsAllocator;
    }

    /**
     * 获取工作id
     *
     * @return
     */
    public final long getWorkerId() {
        return this.workerId;
    }

    /**
     * 获取起步日期秒
     *
     * @return
     */
    public final long getEpochSeconds() {
        return this.epochSeconds;
    }

    @Override
    public int getOrder() {
        return TableAutoDefinitionListener.BEAN_BEGIN_ORDER + 10;
    }

    @Override
    public void onApplicationEvent(ApplicationReadyEvent event) {
        this.initializeNode();
    }
}
