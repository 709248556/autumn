package com.autumn.logistics.channel;

import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;

/**
 * 物流通道属性抽象
 * <p>
 * </p>
 *
 * @description TODO
 * @author: 老码农
 * @create: 2020-03-02 10:49
 **/
@Getter
@Setter
public class AbstractLogisticsChannelProperties implements Serializable {

    private static final long serialVersionUID = -5761630923819693576L;

    /**
     * 属性前缀
     */
    public final static String PREFIX = "autumn.logistics.channel";

    /**
     * 通道 Bean 前缀
     */
    public static final String CHANNEL_BEAN_PREFIX = "autumn";

    /**
     * 通道 Bean 后缀
     */
    public static final String CHANNEL_BEAN_SUFFIX = "LogisticsChannel";

    /**
     * 是否启用
     */
    private boolean enable = false;
}
