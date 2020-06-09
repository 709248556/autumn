package com.autumn.logistics.channel.sto;

import com.autumn.logistics.channel.AbstractLogisticsChannel;

/**
 * 申通快递通道
 * <p>
 * </p>
 *
 * @description TODO
 * @author: 老码农
 * @create: 2020-03-02 09:01
 **/
public class STOLogisticsChannel extends AbstractLogisticsChannel<STOLogisticsChannelProperties> {

    /**
     * 通道Id
     */
    public static final String CHANNEL_ID = "sto";

    /**
     * 通道名称
     */
    public static final String CHANNEL_NAME = "申通快递";

    /**
     * 实例化
     */
    public STOLogisticsChannel() {
        super(STOLogisticsChannelProperties.class);
    }

    /**
     * 实例化
     *
     * @param channelDefaultProperties 通道默认属性
     */
    public STOLogisticsChannel(STOLogisticsChannelProperties channelDefaultProperties) {
        super(STOLogisticsChannelProperties.class, channelDefaultProperties);
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
