package com.autumn.cloud.annotation;

import com.autumn.cloud.uid.UidGenerator;
import com.autumn.cloud.uid.buffer.BufferPaddingExecutor;
import com.autumn.cloud.uid.impl.CachedUidGenerator;
import com.autumn.cloud.uid.impl.DefaultUidGenerator;
import com.autumn.cloud.uid.impl.UidGeneratorTableIdAssigner;
import com.autumn.cloud.uid.worker.DefaultWorkerNodeAssigner;
import com.autumn.cloud.uid.worker.WorkerNodeAssigner;
import com.autumn.mybatis.annotation.AutumnMybatisScan;
import org.springframework.context.annotation.Import;

import java.lang.annotation.*;

/**
 * 启用分布式Uid生成器
 *
 * @Description TODO
 * @Author 老码农
 * @Date 2019-08-25 19:31
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.TYPE)
@Documented
@AutumnMybatisScan(value = {EnableAutumnCloudUidGenerator.REPOSITORY_PACKAGE_PATH}, typeAliasesPackages = {EnableAutumnCloudUidGenerator.ENTITY_PACKAGE_PATH})
@Import({AutumnCloudUidGeneratorRegistrar.class})
public @interface EnableAutumnCloudUidGenerator {

    /**
     * 实体包路径
     */
    public static final String ENTITY_PACKAGE_PATH = "com.autumn.cloud.uid.worker.entities";

    /**
     * 仓储包路径
     */
    public static final String REPOSITORY_PACKAGE_PATH = "com.autumn.cloud.uid.worker.repositories";

    /**
     * 时间字节数
     * <pre>{@code
     *  timeBits + workerBits + seqBits = 63
     * }</pre>
     *
     * @return
     */
    int timeBits() default DefaultUidGenerator.DEFAULT_TIME_BITS;

    /**
     * 工作机字节数
     * <pre>{@code
     *  timeBits + workerBits + seqBits = 63
     * }</pre>
     *
     * @return
     */
    int workerBits() default DefaultUidGenerator.DEFAULT_WORKER_BITS;

    /**
     * 序列号字节数
     * <pre>{@code
     *  timeBits + workerBits + seqBits = 63
     * }</pre>
     *
     * @return
     */
    int seqBits() default DefaultUidGenerator.DEFAULT_SEQ_BITS;

    /**
     * 缓存自动填充缓冲区队列数
     *
     * @return
     */
    int boostPower() default CachedUidGenerator.DEFAULT_BOOST_POWER;

    /**
     * 缓存自动填充的调度时间（秒)
     *
     * @return
     */
    long scheduleInterval() default BufferPaddingExecutor.DEFAULT_SCHEDULE_INTERVAL;

    /**
     * 起步日期
     *
     * @return
     */
    String epochDateString() default WorkerNodeAssigner.DEFAULT_EPOCH_DATE_STRING;

    /**
     * 启用表id分配器 {@link UidGeneratorTableIdAssigner}
     *
     * @return
     */
    boolean enableTableIdAssigner() default true;

    /**
     * 工作节点分配类型
     *
     * @return
     */
    Class<? extends WorkerNodeAssigner> workerNodeAssignerType() default DefaultWorkerNodeAssigner.class;

    /**
     * Uid生成类型
     *
     * @return
     */
    Class<? extends UidGenerator> uidGeneratorType() default DefaultUidGenerator.class;
}
