package com.autumn.pay.channel;

import java.io.Serializable;
import java.util.Date;
import java.util.Map;

/**
 * 订单付款申请
 *
 * @Description TODO
 * @Author 老码农
 * @Date 2019-07-12 11:22
 */
public interface OrderPayApply extends Serializable {

    /**
     * 获取开始时间
     *
     * @return
     */
    Date getStartTime();

    /**
     * 获取过期时间
     *
     * @return
     */
    Date getExpireTime();

    /**
     * 获取客户端Ip
     *
     * @return
     */
    String getClientIp();

    /**
     * 获取订单号
     *
     * @return
     */
    String getOrderNo();

    /**
     * 获取标题
     *
     * @return
     */
    String getSubject();

    /**
     * 获取详情(体)
     *
     * @return
     */
    String getBody();

    /**
     * 获取扩展参数
     *
     * @return
     */
    String getExtraParam();

    /**
     * 获取订单金额(单位:分)
     *
     * @return
     */
    Long getOrderAmount();

    /**
     * 获取用户标识(如微信 openId等)
     *
     * @return
     */
    String getUserToken();

    /**
     * 获取参数集合
     *
     * @return
     */
    Map<String, String> getParams();
}
