package com.autumn.logistics.channel;

/**
 * 物流通道抽象
 * <p>
 * </p>
 *
 * @description TODO
 * @author: 老码农
 * @create: 2020-03-02 14:04
 **/
public abstract class AbstractLogisticsChannel<P extends AbstractLogisticsChannelProperties> implements LogisticsChannel {

    private final Class<P> channelPropertiesType;
    private P channelDefaultProperties;

    /**
     * 实例化
     *
     * @param channelPropertiesType 通道属性类型
     */
    public AbstractLogisticsChannel(Class<P> channelPropertiesType) {
        this.channelPropertiesType = channelPropertiesType;
        this.channelDefaultProperties = null;
    }

    /**
     * 实例化
     *
     * @param channelPropertiesType    通道属性类型
     * @param channelDefaultProperties 通道默认属性
     */
    public AbstractLogisticsChannel(Class<P> channelPropertiesType, P channelDefaultProperties) {
        this.channelPropertiesType = channelPropertiesType;
        this.channelDefaultProperties = channelDefaultProperties;
    }

    @Override
    public final Class<P> getChannelPropertiesType() {
        return this.channelPropertiesType;
    }

    /**
     * 获取通道默认属性
     *
     * @return
     */
    public P getChannelDefaultProperties() {
        return this.channelDefaultProperties;
    }

    /**
     * 设置通道默认属性
     *
     * @param channelDefaultProperties 通道默认属性
     */
    public void setChannelDefaultProperties(P channelDefaultProperties) {
        this.channelDefaultProperties = channelDefaultProperties;
    }




}
