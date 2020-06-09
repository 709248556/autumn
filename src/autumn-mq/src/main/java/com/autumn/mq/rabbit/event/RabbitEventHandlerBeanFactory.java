package com.autumn.mq.rabbit.event;

import com.autumn.exception.ExceptionUtils;
import com.autumn.mq.AutumnMqDelayQueue;
import com.autumn.mq.event.*;
import com.autumn.mq.rabbit.RabbitDirectQueueDeclareFactory;
import com.autumn.mq.rabbit.RabbitFanoutQueueDeclareFactory;
import com.autumn.mq.rabbit.RabbitTopicQueueDeclareFactory;
import com.autumn.util.CollectionUtils;
import com.autumn.util.StringUtils;
import com.autumn.util.reflect.ReflectUtils;
import com.rabbitmq.client.Channel;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.amqp.core.AcknowledgeMode;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.connection.CachingConnectionFactory;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.core.ChannelAwareMessageListener;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.rabbit.listener.SimpleMessageListenerContainer;
import org.springframework.beans.factory.DisposableBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationEvent;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextClosedEvent;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.context.event.ContextStartedEvent;
import org.springframework.context.event.ContextStoppedEvent;

import java.util.*;
import java.util.concurrent.ConcurrentHashMap;

/**
 * Rabbit 事件处理程 Bean 工厂
 *
 * @author 老码农
 * <p>
 * 2017-12-16 14:35:09
 */
public class RabbitEventHandlerBeanFactory extends AbstractAutumnMqEventHandlerBeanFactory
        implements ApplicationListener<ApplicationEvent>, DisposableBean {

    private final static Log logger = LogFactory.getLog(RabbitEventHandlerBeanFactory.class);

    private ConnectionFactory connectionFactory;
    @Autowired
    private ApplicationContext applicationContext;
    private RabbitTemplate rabbitTemplate;

    /**
     * 实例
     *
     * @param connectionFactory 连接工厂
     * @param rabbitTemplate    模板
     */
    public RabbitEventHandlerBeanFactory(ConnectionFactory connectionFactory, RabbitTemplate rabbitTemplate) {
        this.connectionFactory = connectionFactory;
        this.rabbitTemplate = rabbitTemplate;
        if (this.connectionFactory instanceof CachingConnectionFactory) {
            ((CachingConnectionFactory) this.connectionFactory).getRabbitConnectionFactory()
                    .setAutomaticRecoveryEnabled(true);
        }
    }

    /**
     * 创建事件监听
     *
     * @param queue            队列
     * @param eventHandlerType 事件处理类型
     * @param eventDataType    事件数据类型
     * @return
     */
    @SuppressWarnings({"rawtypes", "unchecked"})
    private ChannelAwareMessageListener createChannelAwareMessageListener(final AutumnMqDelayQueue queue,
                                                                          final Class<?> eventHandlerType, final Class<?> eventDataType) {
        ChannelAwareMessageListener channelAware = new ChannelAwareMessageListener() {
            @Override
            public void onMessage(Message message, Channel channel) throws Exception {
                AutumnMqEventMessage eventMessage = null;
                AutumnMqEventHandler eventHandler = (AutumnMqEventHandler) applicationContext.getBean(eventHandlerType);
                Log logger = eventHandler.getLogger();
                if (logger == null) {
                    logger = LogFactory.getLog(eventHandler.getClass());
                }
                String eventName = "事件 - " + queue.getName();
                try {
                    eventMessage = new AutumnMqEventMessage(message, channel, queue, eventDataType);
                    if (logger.isDebugEnabled()) {
                        logger.debug(eventName + " 触发:数据->" + eventMessage.getBodyString());
                    }
                    eventHandler.onHandler(eventMessage);
                } catch (Exception e) {
                    if (eventMessage != null) {
                        logger.error(eventName + " 异常:数据->" + eventMessage.getBodyString() + " error:" + e.getMessage(),
                                e);
                        if (eventHandler.errorAutoRepeatSend()) {
                            Long interval = eventHandler.getAutoRepeatSendInterval();
                            if (interval == null) {
                                interval = 0L;
                            }
                            long count = eventMessage.getRepeatSendCount();
                            count++;
                            if (logger.isInfoEnabled()) {

                                logger.info(eventName + " 异常,间隔(" + interval + "毫秒)自动第(" + count + ")次重发,数据->"
                                        + eventMessage.getBodyString());
                            }
                            queue.setRepeatSendCount(message.getMessageProperties(), count);
                            eventMessage.getQueue().send(eventMessage.getData(), message.getMessageProperties(),
                                    interval);
                        }

                    } else {
                        logger.error(eventName + " error:" + e.getMessage(), e);
                    }
                } finally {
                    if (eventHandler.autoAck()) {
                        eventMessage.basicAck();
                    }
                }
            }
        };
        return channelAware;
    }

    /**
     * 创建延迟队列
     *
     * @param name           名称
     * @param queueType      队列类型
     * @param exchangeSuffix 交换器后缀
     * @param routingKey     路油Key
     * @return
     */
    private AutumnMqDelayQueue createDelayQueue(String name, AutumnMqEventTypeEnum queueType, String exchangeSuffix,
                                                String routingKey) {
        if (queueType.equals(AutumnMqEventTypeEnum.TOPIC)) {
            if (StringUtils.isNullOrBlank(exchangeSuffix)) {
                ExceptionUtils.throwFormatException("exchangeSuffix 交换机后缀不能为 null 或空白值。");
            }
            RabbitTopicQueueDeclareFactory factory = new RabbitTopicQueueDeclareFactory(name, exchangeSuffix,
                    routingKey);
            return factory.delayDeclare(this.rabbitTemplate);
        } else if (queueType.equals(AutumnMqEventTypeEnum.BROADCAST)) {
            if (StringUtils.isNullOrBlank(exchangeSuffix)) {
                ExceptionUtils.throwFormatException("exchangeSuffix 交换机后缀不能为 null 或空白值。");
            }
            RabbitFanoutQueueDeclareFactory factory = new RabbitFanoutQueueDeclareFactory(name, exchangeSuffix, false);
            return factory.delayDeclare(this.rabbitTemplate);
        } else {
            if (StringUtils.isNullOrBlank(name)) {
                ExceptionUtils.throwFormatException("queueName 队列名称不能为 null 或空白值。");
            }
            RabbitDirectQueueDeclareFactory factory = new RabbitDirectQueueDeclareFactory(name);
            return factory.delayDeclare(this.rabbitTemplate);
        }
    }

    private Map<AutumnMqDelayQueue, SimpleMessageListenerContainer> listenerContainerMap = new ConcurrentHashMap<>();

    /**
     * @param registry
     * @param queue
     * @param eventHandlerType
     * @param eventDataType
     * @param concurrentConsumers
     */
    private void registerMessageListenerContainer(AutumnMqDelayQueue queue, Class<?> eventHandlerType,
                                                  Class<?> eventDataType, int concurrentConsumers) {
        if (concurrentConsumers < 1) {
            concurrentConsumers = 1;
        }
        ChannelAwareMessageListener channelAware = createChannelAwareMessageListener(queue, eventHandlerType,
                eventDataType);
        SimpleMessageListenerContainer listenerContainer = new SimpleMessageListenerContainer();
        listenerContainer.setConnectionFactory(this.connectionFactory);
        listenerContainer.setQueueNames(queue.getName());
        listenerContainer.setAcknowledgeMode(AcknowledgeMode.MANUAL);
        listenerContainer.setExposeListenerChannel(true);
        listenerContainer.setMaxConcurrentConsumers(concurrentConsumers);
        listenerContainer.setConcurrentConsumers(1);
        listenerContainer.setMessageListener(channelAware);
        listenerContainer.setAutoStartup(false);
        listenerContainerMap.put(queue, listenerContainer);
    }

    private List<String> eventHandlerPackageList = new ArrayList<>();

    @Override
    public void setEventHandlerPackages(String[] eventHandlerPackages) {
        if (eventHandlerPackages != null) {
            for (String value : eventHandlerPackages) {
                if (!StringUtils.isNullOrBlank(value)) {
                    this.eventHandlerPackageList.add(value);
                }
            }
        }
    }

    private List<String> eventDataPackagesList = new ArrayList<>();

    @Override
    public void setEventDataPackages(String[] eventDataPackages) {
        if (eventDataPackages != null) {
            for (String value : eventDataPackages) {
                if (!StringUtils.isNullOrBlank(value)) {
                    this.eventDataPackagesList.add(value);
                }
            }
        }
    }

    private List<AutumnMqEventHandlerConfigureInfo> eventHandlerConfigureInfoList = new ArrayList<>();

    @Override
    public void setEventHandlerInfos(Collection<AutumnMqEventHandlerConfigureInfo> eventHandlerInfos) {
        if (eventHandlerInfos != null) {
            this.eventHandlerConfigureInfoList.addAll(eventHandlerInfos);
        }
    }

    private List<AutumnMqEventDataConfigureInfo> eventDataConfigureInfoList = new ArrayList<>();

    @Override
    public void setEventDataInfos(Collection<AutumnMqEventDataConfigureInfo> eventDataInfos) {
        if (eventDataInfos != null) {
            this.eventDataConfigureInfoList.addAll(eventDataInfos);
        }
    }

    @Override
    public void initialize() {
        this.registerConfigure(this.eventHandlerConfigureInfoList, this.eventDataConfigureInfoList);
        this.register(CollectionUtils.toArray(this.eventHandlerPackageList, String.class),
                CollectionUtils.toArray(this.eventDataPackagesList, String.class));
    }

    /**
     * 绑定
     *
     * @param eventHandlerPackages 事件处理器包集合
     * @param eventDataPackages    事件数据包集合
     */
    @Override
    public void register(String[] eventHandlerPackages, String[] eventDataPackages) {
        synchronized (this) {
            this.registerEventHandler(eventHandlerPackages);
            this.registerEventData(eventDataPackages);
            if (isStartEvent) {
                Thread eventThread = new Thread(() -> {
                    startEvent();
                });
                eventThread.start();
            }
        }
    }

    @Override
    public void registerConfigure(Collection<AutumnMqEventHandlerConfigureInfo> eventHandlerInfos,
                                  Collection<AutumnMqEventDataConfigureInfo> eventDataInfos) {
        synchronized (this) {
            this.registerEventHandler(eventHandlerInfos);
            this.registerEventData(eventDataInfos);
            if (isStartEvent) {
                Thread eventThread = new Thread(() -> {
                    startEvent();
                });
                eventThread.start();
            }
        }
    }

    /**
     * 注册事件处理器(生产者与消费者)
     *
     * @param eventHandlerPackages 事件处理器包路径
     */
    private void registerEventHandler(String[] eventHandlerPackages) {
        Set<Class<?>> handlerSet = this.getEventHandlers(eventHandlerPackages);
        for (Class<?> type : handlerSet) {
            AutumnMqEventHandlerConfigure config = type.getAnnotation(AutumnMqEventHandlerConfigure.class);
            if (!AutumnMqEventHandler.class.isAssignableFrom(type)) {
                ExceptionUtils.throwConfigureException("类型 " + type.getName() + " 未实现 IEventHandler 接口。");
            }
            Map<String, Class<?>> typeMap = ReflectUtils.getGenericActualArgumentsTypeMap(type);
            final Class<?> eventDataType = typeMap.get("TEventData");
            if (eventDataType == null) {
                ExceptionUtils.throwConfigureException("类型 " + type.getName() + " 未实现 IEventHandler 接口。");
            }
            if (!AutumnMqEventData.class.isAssignableFrom(eventDataType)) {
                ExceptionUtils.throwConfigureException("事件数据类型 " + eventDataType.getName() + " 未实现 IEventData 接口。");
            }
            AutumnMqEventDataConfigure dataConfig = eventDataType.getAnnotation(AutumnMqEventDataConfigure.class);
            if (dataConfig != null && !StringUtils.isNullOrBlank(dataConfig.name())
                    && !config.name().equals(dataConfig.name())) {
                ExceptionUtils.throwConfigureException("事件处理类型(" + type.getName() + ")与对应的事件数据("
                        + eventDataType.getName() + ")队列名称不一致，两者都配置时，队列(事件名称)必须一致。");
            }
            if (delayQueueNameMap.containsKey(config.name())) {
                continue;
            }
            if (delayQueueTypeMap.containsKey(type)) {
                continue;
            }
            AutumnMqDelayQueue queue = createDelayQueue(config.name(), config.eventType(), config.exchangeSuffix(),
                    config.routingKey());
            registerMessageListenerContainer(queue, type, eventDataType, config.concurrentConsumers());
            if (queue.getName() != null) {
                delayQueueNameMap.put(queue.getName(), queue);
            }
            delayQueueTypeMap.put(eventDataType, queue);
        }
    }

    /**
     * 注册事件处理器(生产者与消费者)
     *
     * @param eventHandlerInfos 事件处理器信息集合
     */
    private void registerEventHandler(Collection<AutumnMqEventHandlerConfigureInfo> eventHandlerInfos) {
        if (eventHandlerInfos == null) {
            return;
        }
        for (AutumnMqEventHandlerConfigureInfo handlerInfo : eventHandlerInfos) {
            final Class<?> eventDataType = handlerInfo.getEventDataType();
            if (delayQueueNameMap.containsKey(handlerInfo.getName())) {
                continue;
            }
            if (delayQueueTypeMap.containsKey(eventDataType)) {
                continue;
            }
            AutumnMqDelayQueue queue = createDelayQueue(handlerInfo.getName(), handlerInfo.getEventType(),
                    handlerInfo.getExchangeSuffix(), handlerInfo.getRoutingKey());
            registerMessageListenerContainer(queue, handlerInfo.getEventHandlerType(), eventDataType,
                    handlerInfo.getConcurrentConsumers());
            if (queue.getName() != null) {
                delayQueueNameMap.put(queue.getName(), queue);
            }
            delayQueueTypeMap.put(eventDataType, queue);
        }
    }

    /**
     * 注册事件数据(生产者)
     *
     * @param eventDataPackages 事件数据包路径
     */
    private void registerEventData(String[] eventDataPackages) {
        Set<Class<?>> dataSet = this.getEventDatas(eventDataPackages);
        for (Class<?> type : dataSet) {
            if (!delayQueueTypeMap.containsKey(type)) {
                AutumnMqEventDataConfigure config = type.getAnnotation(AutumnMqEventDataConfigure.class);
                AutumnMqDelayQueue queue = createDelayQueue(config.name(), config.eventType(), config.exchangeSuffix(),
                        config.routingKey());
                if (queue.getName() != null) {
                    delayQueueNameMap.put(queue.getName(), queue);
                }
                delayQueueTypeMap.put(type, queue);
            }
        }
    }

    /**
     * 注册事件数据(生产者)
     *
     * @param eventDataInfos 事件数据信息集合
     */
    private void registerEventData(Collection<AutumnMqEventDataConfigureInfo> eventDataInfos) {
        if (eventDataInfos == null) {
            return;
        }
        for (AutumnMqEventDataConfigureInfo eventInfo : eventDataInfos) {
            if (!delayQueueTypeMap.containsKey(eventInfo.getEventDataType())) {
                AutumnMqDelayQueue queue = createDelayQueue(eventInfo.getName(), eventInfo.getEventType(),
                        eventInfo.getExchangeSuffix(), eventInfo.getRoutingKey());
                if (queue.getName() != null) {
                    delayQueueNameMap.put(queue.getName(), queue);
                }
                delayQueueTypeMap.put(eventInfo.getEventDataType(), queue);
            }
        }
    }

    private Map<String, AutumnMqDelayQueue> delayQueueNameMap = new ConcurrentHashMap<>();

    private Map<Class<?>, AutumnMqDelayQueue> delayQueueTypeMap = new ConcurrentHashMap<>();

    @Override
    public AutumnMqDelayQueue getDelayQueue(String queunName) {
        AutumnMqDelayQueue queue = delayQueueNameMap.get(queunName);
        if (queue == null) {
            throw ExceptionUtils.throwSystemException("无队列 : " + queunName + " 的实例 Bean");
        }
        return queue;
    }

    @Override
    public AutumnMqDelayQueue getDelayQueue(Class<? extends AutumnMqEventData> eventDataType) {
        AutumnMqDelayQueue queue = delayQueueTypeMap.get(eventDataType);
        if (queue == null) {
            if (eventDataType == null) {
                ExceptionUtils.checkNotNull(eventDataType, "eventDataType");
            }
            throw ExceptionUtils.throwSystemException("无事件数据 : " + eventDataType.getName() + " 的实例 Bean");
        }
        return queue;
    }

    private Timer timer;
    private final static long TIMER_FREQUENCY = 1000 * 10L;
    private boolean isStartEvent = false;

    /**
     * 启动事件
     */
    private void startEvent() {
        synchronized (this) {
            String eventName = "";
            int count = listenerContainerMap.values().size();
            int successCount = 0;
            if (count > 0) {
                try {
                    for (Map.Entry<AutumnMqDelayQueue, SimpleMessageListenerContainer> entry : listenerContainerMap
                            .entrySet()) {
                        entry.getKey().initialize();
                        if (!entry.getValue().isRunning()) {
                            eventName = String.join(",", entry.getValue().getQueueNames());
                            logger.info("启动消费事件:" + eventName);
                            entry.getValue().start();
                        }
                        successCount++;
                    }
                    isStartEvent = true;
                    if (timer != null) {
                        timer.cancel();
                        timer = null;
                    }
                } catch (Exception e) {
                    logger.error("启动消费事件:" + eventName + " 出错:" + e.getMessage(), e);
                    int diff = count - successCount;
                    if (diff > 0) {
                        logger.info("后续 " + diff + " 个事件无法启动,10秒后重试。");
                    }
                    if (timer == null) {
                        timer = new Timer();
                        TimerTask timeTask = new TimerTask() {
                            @Override
                            public void run() {
                                logger.info("重新尝试启动消费事件。");
                                startEvent();
                            }
                        };
                        timer.schedule(timeTask, 0L, TIMER_FREQUENCY);
                    }
                }
            }
        }
    }

    @Override
    public void onApplicationEvent(ApplicationEvent event) {
        if (event instanceof ContextStartedEvent) {

        } else if (event instanceof ContextStoppedEvent) {

        } else if (event instanceof ContextRefreshedEvent) {
            Thread eventThread = new Thread(() -> {
                startEvent();
            });
            eventThread.start();
        } else if (event instanceof ContextClosedEvent) {
            this.close();
        }
    }

    /**
     * 关闭
     */
    public void close() {
        synchronized (this) {
            for (SimpleMessageListenerContainer listener : listenerContainerMap.values()) {
                if (listener.isRunning()) {
                    String eventName = String.join(",", listener.getQueueNames());
                    try {
                        logger.info("注销事件:" + eventName);
                        listener.stop();
                    } catch (Exception e) {
                        logger.error("注销事件:" + eventName + " 出错:" + e.getMessage(), e);
                    }
                }
            }
            // listenerContainerMap.clear();
        }
    }

    @Override
    public void destroy() throws Exception {
        close();
    }
}
