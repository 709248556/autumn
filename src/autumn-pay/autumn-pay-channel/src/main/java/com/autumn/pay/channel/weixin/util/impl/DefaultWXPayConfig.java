package com.autumn.pay.channel.weixin.util.impl;

import com.autumn.pay.channel.weixin.util.IWXPayDomain;
import com.autumn.pay.channel.weixin.util.WXPayConfig;

import java.io.InputStream;

public class DefaultWXPayConfig extends WXPayConfig {

    /**
     * App ID
     */
    private String appID;

    /**
     * Mch ID
     */
    private String mchID;

    /**
     * API 密钥
     */
    private String key;

    /**
     * 商户证书内容
     */
    private InputStream certStream;

    /**
     * HTTP(S) 连接超时时间，单位毫秒
     */
    private int httpConnectTimeoutMs;

    /**
     * HTTP(S) 读数据超时时间，单位毫秒
     */
    private int httpReadTimeoutMs;

    /**
     * WXPayDomain, 用于多域名容灾自动切换
     */
    private IWXPayDomain wxPayDomain;


    @Override
    public String getAppID() {
        return appID;
    }

    public void setAppID(String appID) {
        this.appID = appID;
    }

    @Override
    public String getMchID() {
        return mchID;
    }

    public void setMchID(String mchID) {
        this.mchID = mchID;
    }

    @Override
    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    @Override
    public InputStream getCertStream() {
        return certStream;
    }

    public void setCertStream(InputStream certStream) {
        this.certStream = certStream;
    }

    @Override
    public int getHttpConnectTimeoutMs() {
        return httpConnectTimeoutMs;
    }

    public void setHttpConnectTimeoutMs(int httpConnectTimeoutMs) {
        this.httpConnectTimeoutMs = httpConnectTimeoutMs;
    }

    @Override
    public int getHttpReadTimeoutMs() {
        return httpReadTimeoutMs;
    }

    public void setHttpReadTimeoutMs(int httpReadTimeoutMs) {
        this.httpReadTimeoutMs = httpReadTimeoutMs;
    }

    @Override
    public IWXPayDomain getWXPayDomain() {
        return wxPayDomain;
    }

    public void setWXPayDomain(IWXPayDomain wxPayDomain) {
        this.wxPayDomain = wxPayDomain;
    }
}
