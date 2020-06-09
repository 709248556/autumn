package com.autumn.logistics.channel.sf;

import com.autumn.logistics.channel.AbstractLogisticsChannel;

/**
 * 顺丰快递通道
 * <p>
 * </p>
 *
 * @description TODO
 * @author: 老码农
 * @create: 2020-03-02 08:55
 **/
public class SFLogisticsChannel extends AbstractLogisticsChannel<SFLogisticsChannelProperties> {

    /**
     * 通道Id
     */
    public static final String CHANNEL_ID = "sf";

    /**
     * 通道名称
     */
    public static final String CHANNEL_NAME = "顺丰快递";

    /**
     * 实例化
     */
    public SFLogisticsChannel() {
        super(SFLogisticsChannelProperties.class);
    }

    /**
     * 实例化
     *
     * @param channelDefaultProperties 通道默认属性
     */
    public SFLogisticsChannel(SFLogisticsChannelProperties channelDefaultProperties) {
        super(SFLogisticsChannelProperties.class, channelDefaultProperties);
    }

    @Override
    public String getChannelId() {
        return CHANNEL_ID;
    }

    @Override
    public String getChannelName() {
        return CHANNEL_NAME;
    }

}
