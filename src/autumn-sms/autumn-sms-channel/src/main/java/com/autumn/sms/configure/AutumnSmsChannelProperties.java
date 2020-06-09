package com.autumn.sms.configure;

import com.autumn.sms.channel.AbstractSmsChannelProperties;
import com.autumn.sms.channel.aliyun.AliyunSmsProperties;
import com.autumn.sms.channel.tencent.TencentSmsProperties;
import com.autumn.sms.channel.unicom.UnicomSmsProperties;
import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;

import java.io.Serializable;

/**
 * 短信属性
 *
 * @author 老码农 2018-12-07 23:58:35
 */
@Getter
@Setter
@ConfigurationProperties(prefix = AbstractSmsChannelProperties.PREFIX)
public class AutumnSmsChannelProperties implements Serializable {

    /**
     *
     */
    private static final long serialVersionUID = 6200443879549297812L;


    /**
     * 阿里云属性
     */
    private AliyunSmsProperties aliyun = new AliyunSmsProperties();

    /**
     * 腾讯云属性
     */
    private TencentSmsProperties tencent = new TencentSmsProperties();

    /**
     * 联通信使属性
     */
    private UnicomSmsProperties unicom = new UnicomSmsProperties();

    /**
     *
     */
    public AutumnSmsChannelProperties() {

    }

}
