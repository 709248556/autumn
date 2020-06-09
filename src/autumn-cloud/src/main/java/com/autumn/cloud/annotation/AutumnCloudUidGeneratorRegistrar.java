package com.autumn.cloud.annotation;

import com.autumn.cloud.uid.UidGenerator;
import com.autumn.cloud.uid.buffer.BufferPaddingExecutor;
import com.autumn.cloud.uid.impl.CachedUidGenerator;
import com.autumn.cloud.uid.impl.DefaultUidGenerator;
import com.autumn.cloud.uid.impl.UidGeneratorTableIdAssigner;
import com.autumn.cloud.uid.worker.WorkerNodeAssigner;
import com.autumn.exception.ExceptionUtils;
import com.autumn.spring.boot.bean.AbstractImportBeanRegistrar;
import com.autumn.spring.boot.bean.BeanRegisterManager;
import com.autumn.util.DateUtils;
import com.autumn.util.StringUtils;
import org.springframework.beans.factory.config.RuntimeBeanReference;
import org.springframework.beans.factory.support.BeanDefinitionRegistry;
import org.springframework.beans.factory.support.GenericBeanDefinition;
import org.springframework.core.annotation.AnnotationAttributes;
import org.springframework.core.type.AnnotationMetadata;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * 分布式Uid生成器 配置
 *
 * @Description TODO
 * @Author 老码农
 * @Date 2019-08-26 5:46
 */
class AutumnCloudUidGeneratorRegistrar extends AbstractImportBeanRegistrar {

    private static final String ATTRIBUTE_TIME_BITS = "timeBits";
    private static final String ATTRIBUTE_WORKER_BITS = "workerBits";
    private static final String ATTRIBUTE_SEQ_BITS = "seqBits";
    private static final String ATTRIBUTE_BOOST_POWER = "boostPower";
    private static final String ATTRIBUTE_SCHEDULE_INTERVAL = "scheduleInterval";
    private static final String ATTRIBUTE_EPOCH_DATE_STRING = "epochDateString";
    private static final String ATTRIBUTE_WORKER_NODE_ASSIGNER_TYPE = "workerNodeAssignerType";
    private static final String ATTRIBUTE_UID_GENERATOR_TYPE = "uidGeneratorType";
    private static final String ATTRIBUTE_WORKER_NODE_ASSIGNER = "workerNodeAssigner";

    private static final String ATTRIBUTE_ENABLE_TABLE_ID_ASSIGNER = "enableTableIdAssigner";
    private static final String ATTRIBUTE_UID_GENERATOR = "uidGenerator";

    private static final int MAX_BITS = 63;
    private static final int MIN_VALUE = 1;
    private static final int MAX_SINGLE_BITS = MAX_BITS - 2;


    private int getBits(AnnotationAttributes annoAttrs, String name, int defaultValue) {
        int value = (int) annoAttrs.getOrDefault(name, defaultValue);
        if (value < MIN_VALUE) {
            ExceptionUtils.throwConfigureException("配置参数[" + name + "]不能小于" + MIN_VALUE + "。");
        }
        if (value > MAX_SINGLE_BITS) {
            ExceptionUtils.throwConfigureException("配置参数[" + name + "]不能大于" + MAX_SINGLE_BITS + "。");
        }
        return value;
    }

    @Override
    public void registerBeanDefinitions(AnnotationMetadata importingClassMetadata, BeanDefinitionRegistry registry) {
        AnnotationAttributes annoAttrs = AnnotationAttributes
                .fromMap(importingClassMetadata.getAnnotationAttributes(EnableAutumnCloudUidGenerator.class.getName()));
        int timeBits = this.getBits(annoAttrs, ATTRIBUTE_TIME_BITS, DefaultUidGenerator.DEFAULT_TIME_BITS);
        int workerBits = this.getBits(annoAttrs, ATTRIBUTE_WORKER_BITS, DefaultUidGenerator.DEFAULT_WORKER_BITS);
        int seqBits = this.getBits(annoAttrs, ATTRIBUTE_SEQ_BITS, DefaultUidGenerator.DEFAULT_SEQ_BITS);
        if (timeBits + workerBits + seqBits != 63) {
            ExceptionUtils.throwConfigureException("所有的字节参数配置合计必须等于 63 。");
        }

        int boostPower = (int) annoAttrs.getOrDefault(ATTRIBUTE_BOOST_POWER, CachedUidGenerator.DEFAULT_BOOST_POWER);
        if (boostPower < MIN_VALUE) {
            ExceptionUtils.throwConfigureException("配置参数[" + ATTRIBUTE_BOOST_POWER + "]不能小于" + MIN_VALUE + "。");
        }
        long scheduleInterval = (long) annoAttrs.getOrDefault(ATTRIBUTE_SCHEDULE_INTERVAL, BufferPaddingExecutor.DEFAULT_SCHEDULE_INTERVAL);
        if (scheduleInterval < 1) {
            ExceptionUtils.throwConfigureException("配置参数[" + ATTRIBUTE_SCHEDULE_INTERVAL + "]不能小于" + MIN_VALUE + "。");
        }

        String epochDateString = (String) annoAttrs.getOrDefault(ATTRIBUTE_EPOCH_DATE_STRING, WorkerNodeAssigner.DEFAULT_EPOCH_DATE_STRING);
        if (StringUtils.isNullOrBlank(epochDateString)) {
            epochDateString = WorkerNodeAssigner.DEFAULT_EPOCH_DATE_STRING;
        }
        Date epochDate = DateUtils.parseDate(epochDateString);
        if (epochDate.getTime() > System.currentTimeMillis()) {
            ExceptionUtils.throwConfigureException("配置参数[" + ATTRIBUTE_EPOCH_DATE_STRING + "]的日期不能大于当前时间。");
        }

        Class<? extends WorkerNodeAssigner> workerNodeAssignerType = annoAttrs.getClass(ATTRIBUTE_WORKER_NODE_ASSIGNER_TYPE);
        Class<? extends UidGenerator> uidGeneratorType = annoAttrs.getClass(ATTRIBUTE_UID_GENERATOR_TYPE);

        BeanRegisterManager regManager = new BeanRegisterManager(this.getEnvironment(), this.getResourceLoader(), registry);

        Map<String, Object> propertyValues = new HashMap<>(16);
        propertyValues.put(ATTRIBUTE_EPOCH_DATE_STRING, epochDateString);
        String workerNodeAssignerBeanName = regManager.generateBeanName(WorkerNodeAssigner.class);
        GenericBeanDefinition definition = regManager.createBeanDefinition(workerNodeAssignerType, propertyValues, null, null);
        definition.setPrimary(true);
        regManager.registerBean(workerNodeAssignerBeanName, definition);

        propertyValues = new HashMap<>(16);
        propertyValues.put(ATTRIBUTE_TIME_BITS, timeBits);
        propertyValues.put(ATTRIBUTE_WORKER_BITS, workerBits);
        propertyValues.put(ATTRIBUTE_SEQ_BITS, seqBits);
        propertyValues.put(ATTRIBUTE_BOOST_POWER, boostPower);
        propertyValues.put(ATTRIBUTE_SCHEDULE_INTERVAL, scheduleInterval);
        propertyValues.put(ATTRIBUTE_WORKER_NODE_ASSIGNER, new RuntimeBeanReference(workerNodeAssignerBeanName));
        String uidGeneratorBeanName = regManager.generateBeanName(UidGenerator.class);
        definition = regManager.createBeanDefinition(uidGeneratorType, propertyValues, null, null);
        definition.setPrimary(true);
        regManager.registerBean(uidGeneratorBeanName, definition);

        boolean enableTableIdAssigner = annoAttrs.getBoolean(ATTRIBUTE_ENABLE_TABLE_ID_ASSIGNER);
        if (enableTableIdAssigner) {
            propertyValues = new HashMap<>(16);
            propertyValues.put(ATTRIBUTE_UID_GENERATOR, new RuntimeBeanReference(uidGeneratorBeanName));
            String tableIdAssignerBeanName = regManager.generateBeanName(UidGeneratorTableIdAssigner.class);
            definition = regManager.createBeanDefinition(UidGeneratorTableIdAssigner.class, propertyValues, null, null);
            definition.setPrimary(true);
            regManager.registerBean(tableIdAssignerBeanName, definition);
        }
    }
}
