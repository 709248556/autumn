package com.autumn.logistics.channel.ttk;

import com.autumn.logistics.channel.AbstractLogisticsChannel;

/**
 * 天天快递物流通道
 * <p>
 * </p>
 *
 * @description TODO
 * @author: 老码农
 * @create: 2020-03-02 09:37
 **/
public class TTKLogisticsChannel extends AbstractLogisticsChannel<TTKLogisticsChannelProperties> {

    /**
     * 通道Id
     */
    public static final String CHANNEL_ID = "ttk";

    /**
     * 通道名称
     */
    public static final String CHANNEL_NAME = "天天快递物流";

    /**
     * 实例化
     */
    public TTKLogisticsChannel() {
        super(TTKLogisticsChannelProperties.class);
    }

    /**
     * 实例化
     *
     * @param channelDefaultProperties 通道默认属性
     */
    public TTKLogisticsChannel(TTKLogisticsChannelProperties channelDefaultProperties) {
        super(TTKLogisticsChannelProperties.class, channelDefaultProperties);
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
