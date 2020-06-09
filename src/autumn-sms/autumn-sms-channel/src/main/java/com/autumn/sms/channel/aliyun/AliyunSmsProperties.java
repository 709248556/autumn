package com.autumn.sms.channel.aliyun;

import com.autumn.sms.channel.AbstractSmsChannelProperties;
import lombok.Getter;
import lombok.Setter;

/**
 * 阿里云短信通道属性
 * <p>
 * </p>
 *
 * @description TODO
 * @author: 老码农
 * @create: 2020-03-02 09:40
 **/
@Getter
@Setter
public class AliyunSmsProperties extends AbstractSmsChannelProperties {

    private static final long serialVersionUID = -7570294304741839591L;

    /**
     * bean条件属性
     */
    public static final String BEAN_CONDITIONAL_PROPERTY = AbstractSmsChannelProperties.PREFIX + ".aliyun.enable";

    /**
     * 通道 Bean 名称
     */
    public static final String CHANNEL_BEAN_NAME = CHANNEL_BEAN_PREFIX + "Aliyun" + CHANNEL_BEAN_SUFFIX;

    /**
     * 访问键id
     */
    private String accessKeyId;
    /**
     * 访问密钥
     */
    private String accessKeySecret;
    /**
     * 默认签名
     */
    private String defaultSignName;
}
