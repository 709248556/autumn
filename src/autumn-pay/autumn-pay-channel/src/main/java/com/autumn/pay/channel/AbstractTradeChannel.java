package com.autumn.pay.channel;

import com.autumn.exception.ExceptionUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;

/**
 * 交易通道抽象
 *
 * @Description TODO
 * @Author 老码农
 * @Date 2019-07-12 15:14
 */
public abstract class AbstractTradeChannel<TChannelAccount extends TradeChannelAccount, TNotify>
        implements TradeChannel {

    /**
     * 获取编码
     *
     * @return
     */
    @Override
    public Charset getCharset() {
        return StandardCharsets.UTF_8;
    }

    /**
     * 日志
     */
    protected final Log logger = LogFactory.getLog(this.getClass());

    /**
     * 是否是协议支付
     *
     * @return
     */
    @Override
    public boolean isProtocolPay() {
        return false;
    }

    @Override
    public boolean isPayNewRequest() {
        return true;
    }

    private TradeChannelConfigure tradeChannelConfigure;
    private TradeChannelAccount defaultChannelAccount;

    @Override
    public TradeChannelConfigure getChannelConfigure() {
        return this.tradeChannelConfigure;
    }

    public void setChannelConfigure(TradeChannelConfigure channelConfigure) {
        this.tradeChannelConfigure = channelConfigure;
    }

    @Override
    public TradeChannelAccount getDefaultChannelAccount() {
        return this.defaultChannelAccount;
    }

    public void setDefaultChannelAccount(TradeChannelAccount defaultChannelAccount) {
        this.defaultChannelAccount = defaultChannelAccount;
    }

    /**
     * 配置类型
     */
    protected final Class<TChannelAccount> tradeChannelAccountType;

    public AbstractTradeChannel(Class<TChannelAccount> tradeChannelAccountType) {
        this.tradeChannelAccountType = tradeChannelAccountType;
    }

    @SuppressWarnings("unchecked")
    protected TChannelAccount createTradeChannelAccount(TradeChannelAccount tradeChannelAccount) {
        ExceptionUtils.checkNotNull(tradeChannelAccount, "tradeChannelAccount");
        if (!this.getTradeChannelAccountType().isAssignableFrom(tradeChannelAccount.getClass())) {
            throw this.createTradeChannelException("指定的配置类型无效，无法将类型[" + tradeChannelAccount.getClass().getName()
                    + "]转换为[" + this.getTradeChannelAccountType().getName() + "]类型。");
        }
        return (TChannelAccount) tradeChannelAccount;
    }

    @Override
    public final void checkTradeChannelAccount(TradeChannelAccount tradeChannelAccount) {
        this.internalCheckTradeChannelAccount(this.createTradeChannelAccount(tradeChannelAccount));
    }

    /**
     * 内部检查账户信息
     *
     * @param configureInfo
     */
    protected abstract void internalCheckTradeChannelAccount(TChannelAccount configureInfo);

    @Override
    public final Class<TChannelAccount> getTradeChannelAccountType() {
        return this.tradeChannelAccountType;
    }

    @Override
    public final TradePayApplyResult payApply(OrderPayApply order) {
        return this.payApply(order, this.getDefaultChannelAccount());
    }

    @Override
    public final TradePayApplyResult payApply(OrderPayApply order, TradeChannelAccount tradeChannelAccount) {
        ExceptionUtils.checkNotNull(order, "order");
        return this.internalPayApply(order, this.createTradeChannelAccount(tradeChannelAccount));
    }

    /**
     * 内部付款申请
     *
     * @param order          订单
     * @param channelAccount 账户
     * @return
     */
    protected abstract TradePayApplyResult internalPayApply(OrderPayApply order, TChannelAccount channelAccount);

    @Override
    public final TradeRefundApplyResult refundApply(OrderRefundApply apply) {
        return this.refundApply(apply, this.getDefaultChannelAccount());
    }

    @Override
    public final TradeRefundApplyResult refundApply(OrderRefundApply apply, TradeChannelAccount channelAccount) {
        ExceptionUtils.checkNotNull(apply, "apply");
        return this.internalRefundApply(apply, this.createTradeChannelAccount(channelAccount));
    }

    /**
     * 内部退款申请
     *
     * @param apply          申请
     * @param channelAccount 账户
     * @return
     */
    protected abstract TradeRefundApplyResult internalRefundApply(OrderRefundApply apply, TChannelAccount channelAccount);

    @Override
    public final TradeOrderInfo queryOrderInfo(OrderQueryApply apply) {
        return this.queryOrderInfo(apply, this.getDefaultChannelAccount());
    }

    @Override
    public final TradeOrderInfo queryOrderInfo(OrderQueryApply apply, TradeChannelAccount channelAccount) {
        ExceptionUtils.checkNotNull(apply, "apply");
        return this.internalQueryOrderInfo(apply, this.createTradeChannelAccount(channelAccount));
    }

    /**
     * 内部查询订单信息
     *
     * @param apply          申请
     * @param channelAccount 账户
     * @return
     */
    protected abstract TradeOrderInfo internalQueryOrderInfo(OrderQueryApply apply, TChannelAccount channelAccount);

    @Override
    public final TradeOrderRefundInfo queryOrderRefundInfo(OrderRefundQueryApply apply) {
        return this.queryOrderRefundInfo(apply, this.getDefaultChannelAccount());
    }

    @Override
    public final TradeOrderRefundInfo queryOrderRefundInfo(OrderRefundQueryApply apply, TradeChannelAccount channelAccount) {
        ExceptionUtils.checkNotNull(apply, "apply");
        return this.internalQueryOrderRefundInfo(apply, this.createTradeChannelAccount(channelAccount));
    }

    /**
     * 内部查询订单退款信息
     *
     * @param apply          申请
     * @param channelAccount 账户
     * @return
     */
    protected abstract TradeOrderRefundInfo internalQueryOrderRefundInfo(OrderRefundQueryApply apply, TChannelAccount channelAccount);

    @SuppressWarnings("unchecked")
    protected TNotify createNotify(Object notifyMessage) {
        ExceptionUtils.checkNotNull(notifyMessage, "notifyMessage");
        return (TNotify) notifyMessage;
    }

    @Override
    public final TradeOrderInfo createPayNotify(Object notifyMessage) {
        return this.createPayNotify(notifyMessage, this.getDefaultChannelAccount());
    }

    @Override
    public final TradeOrderInfo createPayNotify(Object notifyMessage, TradeChannelAccount channelAccount) {
        return this.internalCreatePayNotify(this.createNotify(notifyMessage), this.createTradeChannelAccount(channelAccount));
    }

    /**
     * 内部创建支付通知
     *
     * @param notify         通知
     * @param channelAccount 账户
     * @return
     */
    protected abstract TradeOrderInfo internalCreatePayNotify(TNotify notify, TChannelAccount channelAccount);

    @Override
    public final TradeRefundApplyResult createRefundNotify(Object notifyMessage) {
        return this.createRefundNotify(notifyMessage, this.getDefaultChannelAccount());
    }

    @Override
    public final TradeRefundApplyResult createRefundNotify(Object notifyMessage, TradeChannelAccount channelAccount) {
        return this.internalCreateRefundNotify(this.createNotify(notifyMessage), this.createTradeChannelAccount(channelAccount));
    }

    /**
     * 内部创建退款通知
     *
     * @param notify         通知
     * @param channelAccount 账户
     * @return
     */
    protected abstract TradeRefundApplyResult internalCreateRefundNotify(TNotify notify, TChannelAccount channelAccount);

    /**
     * 创建交易通道异常
     *
     * @param message 消息
     * @return
     */
    protected TradeChannelException createTradeChannelException(String message) {
        return new TradeChannelException(this, message);
    }

    /**
     * 创建交易通道异常
     *
     * @param message 消息
     * @param cause   原异常
     * @return
     */
    protected TradeChannelException createTradeChannelException(String message, Throwable cause) {
        return new TradeChannelException(this, message, cause);
    }
}
