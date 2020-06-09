package com.autumn.pay.channel.enums;

import com.autumn.util.ValuedEnum;

/**
 * 通道类型
 *
 * @Description TODO
 * @Author 老码农
 * @Date 2019-07-12 11:17
 */
public enum ChannelType implements ValuedEnum<Integer> {

    /**
     * 空
     */
    NONE(0),

    /**
     * Web 应用
     */
    WEB(1),

    /**
     * 原生 App 应用
     */
    NATIVE_APP(2),

    /**
     * Web App
     */
    WEB_APP(4),

    /**
     * H5页面
     */
    H5(8),

    /**
     * 所有
     */
    ALL(15);

    /**
     * 获取值
     */
    private final int value;

    private ChannelType(int value) {
        this.value = value;
    }

    @Override
    public Integer value() {
        return this.value;
    }


    /**
     * 值解析
     *
     * @param value 值
     * @return
     */
    public static ChannelType valueOf(int value) {
        for (ChannelType channelType : ChannelType.values()) {
            if (channelType.value() == value) {
                return channelType;
            }
        }
        return null;
    }


}
