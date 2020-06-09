package com.autumn.logistics.channel.cainiao;

import com.autumn.logistics.channel.AbstractLogisticsChannelProperties;
import lombok.Getter;
import lombok.Setter;

/**
 * 菜鸟物流通道属性
 * <p>
 * </p>
 *
 * @description TODO
 * @author: 老码农
 * @create: 2020-03-02 10:52
 **/
@Getter
@Setter
public class CaiNiaoLogisticsChannelProperties extends AbstractLogisticsChannelProperties {
    private static final long serialVersionUID = -5487724626972138386L;

    /**
     * bean条件属性
     */
    public static final String BEAN_CONDITIONAL_PROPERTY = AbstractLogisticsChannelProperties.PREFIX + ".cainiao.enable";

    /**
     * 通道 Bean 名称
     */
    public static final String CHANNEL_BEAN_NAME = CHANNEL_BEAN_PREFIX + "CaiNiao" + CHANNEL_BEAN_SUFFIX;
}
