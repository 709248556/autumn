package com.autumn.pay.channel.weixin.util.impl;

import com.autumn.pay.channel.weixin.util.IWXPayDomain;
import com.autumn.pay.channel.weixin.util.WXPayConfig;

import static com.autumn.pay.channel.weixin.util.WeixinPayConstants.DOMAIN_API;

public class DefaultWXPayDomain implements IWXPayDomain {
    /**
     * 获取域名
     *
     * @param config 配置
     * @return 域名
     */
    @Override
    public DomainInfo getDomain(WXPayConfig config) {
        return new DomainInfo(DOMAIN_API, true);
    }
}
