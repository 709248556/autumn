package com.autumn.logistics.channel.yto;

import com.autumn.logistics.channel.AbstractLogisticsChannelProperties;
import lombok.Getter;
import lombok.Setter;

/**
 * 圆通快递通道属性
 * <p>
 * </p>
 *
 * @description TODO
 * @author: 老码农
 * @create: 2020-03-02 10:53
 **/
@Getter
@Setter
public class YTOLogisticsChannelProperties extends AbstractLogisticsChannelProperties {
    private static final long serialVersionUID = -2183053685958237351L;
    /**
     * bean条件属性
     */
    public static final String BEAN_CONDITIONAL_PROPERTY = AbstractLogisticsChannelProperties.PREFIX + ".yto.enable";

    /**
     * 通道 Bean 名称
     */
    public static final String CHANNEL_BEAN_NAME = CHANNEL_BEAN_PREFIX + "YTO" + CHANNEL_BEAN_SUFFIX;
}
