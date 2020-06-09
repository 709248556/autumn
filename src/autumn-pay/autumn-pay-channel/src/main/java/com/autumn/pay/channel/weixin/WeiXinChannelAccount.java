package com.autumn.pay.channel.weixin;

import com.autumn.pay.channel.AbstractTradeChannelAccount;
import lombok.Getter;
import lombok.Setter;

/**
 * 微信交易通道账户配置
 *
 * @Description TODO
 * @Author 老码农
 * @Date 2019-07-12 15:38
 */
@Getter
@Setter
public class WeiXinChannelAccount extends AbstractTradeChannelAccount {

    private static final long serialVersionUID = -2673385474174621311L;

    /**
     * 应用id
     */
    private String appId;

    /**
     * 商户id
     */
    private String mchId;

    /**
     * API 密钥
     */
    private String key;

    /**
     * 证书
     */
    private String cert;

}
