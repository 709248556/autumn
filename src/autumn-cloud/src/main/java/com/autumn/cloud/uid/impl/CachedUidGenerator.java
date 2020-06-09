package com.autumn.cloud.uid.impl;

import com.autumn.cloud.uid.BitsAllocator;
import com.autumn.cloud.uid.UID;
import com.autumn.cloud.uid.UidGenerateException;
import com.autumn.cloud.uid.UidGenerator;
import com.autumn.cloud.uid.buffer.BufferPaddingExecutor;
import com.autumn.cloud.uid.buffer.RejectedPutBufferHandler;
import com.autumn.cloud.uid.buffer.RejectedTakeBufferHandler;
import com.autumn.cloud.uid.buffer.RingBuffer;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.DisposableBean;
import org.springframework.util.Assert;

import java.util.ArrayList;
import java.util.List;

/**
 * Represents a cached implementation of {@link UidGenerator} extends
 * from {@link DefaultUidGenerator}, based on a lock free {@link RingBuffer}<p>
 * <p>
 * The spring properties you can specified as below:<br>
 * <li><b>boostPower:</b> RingBuffer size boost for a power of 2, Sample: boostPower is 3, it means the buffer size
 * will be <code>({@link BitsAllocator#getMaxSequence()} + 1) &lt;&lt;
 * {@link #boostPower}</code>, Default as {@value #DEFAULT_BOOST_POWER}
 * <li><b>paddingFactor:</b> Represents a percent value of (0 - 100). When the count of rest available UIDs reach the
 * threshold, it will trigger padding buffer. Default as{@link RingBuffer#DEFAULT_PADDING_PERCENT}
 * Sample: paddingFactor=20, bufferSize=1000 -> threshold=1000 * 20 /100, padding buffer will be triggered when tail-cursor<threshold
 * <li><b>scheduleInterval:</b> Padding buffer in a schedule, specify padding buffer interval, Unit as second
 * <li><b>rejectedPutBufferHandler:</b> Policy for rejected put buffer. Default as discard put request, just do logging
 * <li><b>rejectedTakeBufferHandler:</b> Policy for rejected take buffer. Default as throwing up an exception
 *
 * @author yutianbao
 */
public class CachedUidGenerator
        extends DefaultUidGenerator implements UidGenerator, DisposableBean {

    /**
     * 日志
     */
    private static final Log LOGGER = LogFactory.getLog(CachedUidGenerator.class);
    /**
     * boostPower 默认值
     */
    public static final int DEFAULT_BOOST_POWER = 3;

    /**
     * Spring properties
     */
    private int boostPower = DEFAULT_BOOST_POWER;
    private static final int PADDING_FACTOR = RingBuffer.DEFAULT_PADDING_PERCENT;
    private Long scheduleInterval;

    private RejectedPutBufferHandler rejectedPutBufferHandler;
    private RejectedTakeBufferHandler rejectedTakeBufferHandler;

    /**
     * RingBuffer
     */
    private RingBuffer ringBuffer;
    private BufferPaddingExecutor bufferPaddingExecutor;

    /**
     * 实例化
     */
    public CachedUidGenerator() {

    }

    @Override
    public UID newUID() throws UidGenerateException {
        this.initializeNode();
        try {
            return ringBuffer.take();
        } catch (UidGenerateException e) {
            LOGGER.error(e.getMessage(), e);
            throw e;
        } catch (Exception e) {
            LOGGER.error("Generate unique id exception. " + e.getMessage(), e);
            throw new UidGenerateException(e);
        }
    }

    @Override
    public void destroy() throws Exception {
        bufferPaddingExecutor.shutdown();
    }

    /**
     * Get the UIDs in the same specified second under the max sequence
     *
     * @param currentSecond
     * @return UID list, size of {@link BitsAllocator#getMaxSequence()} + 1
     */
    protected List<UID> nextIdsForOneSecond(long currentSecond) {
        // Initialize result list size of (max sequence + 1)
        int listSize = (int) this.getBitsAllocator().getMaxSequence() + 1;
        List<UID> uidList = new ArrayList<>(listSize);
        long deltaSeconds = currentSecond - this.getEpochSeconds();
        // Allocate the first sequence of the second, the others can be calculated with the offset
        long firstSeqUid = this.getBitsAllocator().allocate(deltaSeconds, this.getWorkerId(), 0L);
        for (int offset = 0; offset < listSize; offset++) {
            uidList.add(new UID(firstSeqUid + offset, this.getWorkerId(), offset, this.getEpochSeconds(), deltaSeconds + offset));
        }
        return uidList;
    }

    @Override
    protected boolean initializeNode() {
        boolean result = super.initializeNode();
        if (result) {
            this.initRingBuffer();
        }
        return result;
    }

    /**
     * Initialize RingBuffer & RingBufferPaddingExecutor
     */
    private void initRingBuffer() {
        // initialize RingBuffer
        int bufferSize = ((int) this.getBitsAllocator().getMaxSequence() + 1) << boostPower;
        this.ringBuffer = new RingBuffer(bufferSize, PADDING_FACTOR);
        LOGGER.info(String.format("Initialized ring buffer size:%s, paddingFactor:%s", bufferSize, PADDING_FACTOR));

        // initialize RingBufferPaddingExecutor
        boolean usingSchedule = (scheduleInterval != null);
        this.bufferPaddingExecutor = new BufferPaddingExecutor(ringBuffer, this::nextIdsForOneSecond, usingSchedule);
        if (usingSchedule) {
            bufferPaddingExecutor.setScheduleInterval(scheduleInterval);
        }

        LOGGER.info(String.format("Initialized BufferPaddingExecutor. Using schdule:%s, interval:%s", usingSchedule, scheduleInterval));

        // set rejected put/take handle policy
        this.ringBuffer.setBufferPaddingExecutor(bufferPaddingExecutor);
        if (rejectedPutBufferHandler != null) {
            this.ringBuffer.setRejectedPutHandler(rejectedPutBufferHandler);
        }
        if (rejectedTakeBufferHandler != null) {
            this.ringBuffer.setRejectedTakeHandler(rejectedTakeBufferHandler);
        }

        // fill in all slots of the RingBuffer
        bufferPaddingExecutor.paddingBuffer();

        // start buffer padding threads
        bufferPaddingExecutor.start();
    }

    /**
     * Setters for spring property
     */
    public void setBoostPower(int boostPower) {
        Assert.isTrue(boostPower > 0, "Boost power must be positive!");
        this.boostPower = boostPower;
    }

    public void setRejectedPutBufferHandler(RejectedPutBufferHandler rejectedPutBufferHandler) {
        Assert.notNull(rejectedPutBufferHandler, "RejectedPutBufferHandler can't be null!");
        this.rejectedPutBufferHandler = rejectedPutBufferHandler;
    }

    public void setRejectedTakeBufferHandler(RejectedTakeBufferHandler rejectedTakeBufferHandler) {
        Assert.notNull(rejectedTakeBufferHandler, "RejectedTakeBufferHandler can't be null!");
        this.rejectedTakeBufferHandler = rejectedTakeBufferHandler;
    }

    public void setScheduleInterval(long scheduleInterval) {
        this.checkSetNotInitialize();
        Assert.isTrue(scheduleInterval > 0, "Schedule interval must positive!");
        this.scheduleInterval = scheduleInterval;
    }
}
