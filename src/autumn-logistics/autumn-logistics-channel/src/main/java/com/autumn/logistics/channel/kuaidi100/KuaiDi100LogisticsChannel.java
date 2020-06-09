package com.autumn.logistics.channel.kuaidi100;

import com.autumn.logistics.channel.AbstractLogisticsChannel;

/**
 * 快递100通道
 * <p>
 * </p>
 *
 * @description TODO
 * @author: 老码农
 * @create: 2020-03-02 08:41
 **/
public class KuaiDi100LogisticsChannel extends AbstractLogisticsChannel<KuaiDi100LogisticsChannelProperties> {

    /**
     * 通道Id
     */
    public static final String CHANNEL_ID = "kuaiDi100";

    /**
     * 通道名称
     */
    public static final String CHANNEL_NAME = "快递100";

    /**
     * 实例化
     */
    public KuaiDi100LogisticsChannel() {
        super(KuaiDi100LogisticsChannelProperties.class);
    }

    /**
     * 实例化
     *
     * @param channelDefaultProperties 通道默认属性
     */
    public KuaiDi100LogisticsChannel(KuaiDi100LogisticsChannelProperties channelDefaultProperties) {
        super(KuaiDi100LogisticsChannelProperties.class, channelDefaultProperties);
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
