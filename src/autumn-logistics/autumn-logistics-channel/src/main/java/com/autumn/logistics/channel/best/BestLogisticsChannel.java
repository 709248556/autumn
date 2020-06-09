package com.autumn.logistics.channel.best;

import com.autumn.logistics.channel.AbstractLogisticsChannel;

/**
 * 百世达物流通道
 * <p>
 * </p>
 *
 * @description TODO
 * @author: 老码农
 * @create: 2020-03-02 09:36
 **/
public class BestLogisticsChannel extends AbstractLogisticsChannel<BestLogisticsChannelProperties> {

    /**
     * 通道Id
     */
    public static final String CHANNEL_ID = "bestLogistics";

    /**
     * 通道名称
     */
    public static final String CHANNEL_NAME = "百世达物流";

    /**
     * 实例化
     */
    public BestLogisticsChannel() {
        super(BestLogisticsChannelProperties.class);
    }

    /**
     * 实例化
     *
     * @param channelDefaultProperties 通道默认属性
     */
    public BestLogisticsChannel(BestLogisticsChannelProperties channelDefaultProperties) {
        super(BestLogisticsChannelProperties.class, channelDefaultProperties);
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
