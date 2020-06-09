package com.autumn.pay.channel.weixin.util;

import java.io.InputStream;

public abstract class WXPayConfig {

    /**
     * 获取 App ID
     *
     * @return App ID
     */
    public abstract String getAppID();


    /**
     * 获取 Mch ID
     *
     * @return Mch ID
     */
    public abstract String getMchID();


    /**
     * 获取 API 密钥
     *
     * @return API密钥
     */
    public abstract String getKey();


    /**
     * 获取商户证书内容
     *
     * @return 商户证书内容
     */
    public abstract InputStream getCertStream();

    /**
     * HTTP(S) 连接超时时间，单位毫秒
     *
     * @return
     */
    public abstract int getHttpConnectTimeoutMs();

    /**
     * HTTP(S) 读数据超时时间，单位毫秒
     *
     * @return
     */
    public abstract int getHttpReadTimeoutMs();

    /**
     * 获取WXPayDomain, 用于多域名容灾自动切换
     * @return
     */
    public abstract IWXPayDomain getWXPayDomain();

}
