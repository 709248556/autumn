package com.autumn.pay.channel;


import com.autumn.pay.channel.impl.DefaultTradeChannelConfigure;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;

/**
 * 支付属性
 *
 * @author 老码农 2019-9-14 18:15
 */
@Getter
@Setter
public abstract class AbstractPayChannelProperties extends DefaultTradeChannelConfigure implements Serializable {

    private static final long serialVersionUID = 916256558569075818L;

    /**
     * 属性前缀
     */
    public final static String PREFIX = "autumn.pay.channel";

    /**
     * 通道 Bean 前缀
     */
    public static final String CHANNEL_BEAN_PREFIX = "autumn";

    /**
     * 通道 Bean 后缀
     */
    public static final String CHANNEL_BEAN_SUFFIX = "PayChannel";


}
