package com.autumn.pay.channel;


/**
 * 交易通道配置
 *
 * @Description TODO
 * @Author 老码农
 * @Date 2019-07-12 12:09
 */
public interface TradeChannelConfigure {

    /**
     * 获取收款通Url
     *
     * @return
     */
    String getPayNotifyUrl();

    /**
     * 获取支付成功Web返回地址
     *
     * @return
     */
    String getPayWebReturnUrl();

    /**
     * 获取支付成功H5返回地址
     *
     * @return
     */
    String getPayH5ReturnUrl();

    /**
     * 获取转账通知Url
     *
     * @return
     */
    String getTransfersNotifyUrl();

    /**
     * 获取退款通知Url
     *
     * @return
     */
    String getRefundNotifyUrl();

    /**
     * HTTP(S) 连接超时时间，单位毫秒
     *
     * @return
     */
    int getHttpConnectTimeoutMs();

    /**
     * 设置Http(S) 连接超时时间，单位毫秒
     *
     * @param httpConnectTimeoutMs 连接超时时间，单位毫秒
     */
    void setHttpConnectTimeoutMs(int httpConnectTimeoutMs);

    /**
     * HTTP(S) 读数据超时时间，单位毫秒
     *
     * @return
     */
    int getHttpReadTimeoutMs();

    /**
     * 设置HTTP(S) 读数据超时时间，单位毫秒
     *
     * @param httpReadTimeoutMs HTTP(S) 读数据超时时间，单位毫秒
     */
    void setHttpReadTimeoutMs(int httpReadTimeoutMs);

}
