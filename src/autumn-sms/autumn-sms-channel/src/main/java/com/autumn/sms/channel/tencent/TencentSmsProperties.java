package com.autumn.sms.channel.tencent;

import com.autumn.sms.channel.AbstractSmsChannelProperties;
import lombok.Getter;
import lombok.Setter;

/**
 * 腾讯云短信通道属性
 * <p>
 * </p>
 *
 * @description TODO
 * @author: 老码农
 * @create: 2020-03-02 09:41
 **/
@Getter
@Setter
public class TencentSmsProperties extends AbstractSmsChannelProperties {

    private static final long serialVersionUID = 1638659515277870630L;

    /**
     * Bean条件属性
     */
    public static final String BEAN_CONDITIONAL_PROPERTY = AbstractSmsChannelProperties.PREFIX + ".tencent.enable";

    /**
     * 通道 Bean 名称
     */
    public static final String CHANNEL_BEAN_NAME = CHANNEL_BEAN_PREFIX + "Tencent" + CHANNEL_BEAN_SUFFIX;

    /**
     * 应用id
     */
    private Integer appId;
    /**
     * 应用Key
     */
    private String appKey;
}
