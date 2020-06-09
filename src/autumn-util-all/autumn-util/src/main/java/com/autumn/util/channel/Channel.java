package com.autumn.util.channel;

/**
 * 通道
 * <p>
 * 提供各种通道管理
 * </p>
 *
 * @description TODO
 * @author: 老码农
 * @create: 2020-03-02 00:16
 **/
public interface Channel {

    /**
     * 获取通道id
     *
     * @return
     */
    String getChannelId();

    /**
     * 获取通道名称
     *
     * @return
     */
    String getChannelName();
}
