package com.autumn.logistics.channel.cainiao;

import com.autumn.logistics.channel.AbstractLogisticsChannel;

/**
 * 菜鸟物流通道
 * <p>
 * </p>
 *
 * @description TODO
 * @author: 老码农
 * @create: 2020-03-02 08:44
 **/
public class CaiNiaoLogisticsChannel extends AbstractLogisticsChannel<CaiNiaoLogisticsChannelProperties> {

    /**
     * 通道Id
     */
    public static final String CHANNEL_ID = "caiNiao";

    /**
     * 通道名称
     */
    public static final String CHANNEL_NAME = "菜鸟物流";

    /**
     * 实例化
     */
    public CaiNiaoLogisticsChannel() {
        super(CaiNiaoLogisticsChannelProperties.class);
    }

    /**
     * 实例化
     *
     * @param channelDefaultProperties 通道默认属性
     */
    public CaiNiaoLogisticsChannel(CaiNiaoLogisticsChannelProperties channelDefaultProperties) {
        super(CaiNiaoLogisticsChannelProperties.class, channelDefaultProperties);
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
