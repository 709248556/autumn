package com.autumn.logistics.channel.jd;

import com.autumn.logistics.channel.AbstractLogisticsChannelProperties;
import lombok.Getter;
import lombok.Setter;

/**
 * 京东物流通道属性
 * <p>
 * </p>
 *
 * @description TODO
 * @author: 老码农
 * @create: 2020-03-02 10:52
 **/
@Getter
@Setter
public class JDLogisticsChannelProperties extends AbstractLogisticsChannelProperties {
    private static final long serialVersionUID = -6259268949932679669L;

    /**
     * bean条件属性
     */
    public static final String BEAN_CONDITIONAL_PROPERTY = AbstractLogisticsChannelProperties.PREFIX + ".jd.enable";

    /**
     * 通道 Bean 名称
     */
    public static final String CHANNEL_BEAN_NAME = CHANNEL_BEAN_PREFIX + "JD" + CHANNEL_BEAN_SUFFIX;
}
