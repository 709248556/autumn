package com.autumn.logistics.channel.ems;

import com.autumn.logistics.channel.AbstractLogisticsChannel;

/**
 * 邮政Ems通道
 * <p>
 * </p>
 *
 * @description TODO
 * @author: 老码农
 * @create: 2020-03-02 08:49
 **/
public class EmsLogisticsChannel extends AbstractLogisticsChannel<EmsLogisticsChannelProperties> {

    /**
     * 通道Id
     */
    public static final String CHANNEL_ID = "ems";

    /**
     * 通道名称
     */
    public static final String CHANNEL_NAME = "邮政Ems";

    /**
     * 实例化
     */
    public EmsLogisticsChannel() {
        super(EmsLogisticsChannelProperties.class);
    }

    /**
     * 实例化
     *
     * @param channelDefaultProperties 通道默认属性
     */
    public EmsLogisticsChannel(EmsLogisticsChannelProperties channelDefaultProperties) {
        super(EmsLogisticsChannelProperties.class, channelDefaultProperties);
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
