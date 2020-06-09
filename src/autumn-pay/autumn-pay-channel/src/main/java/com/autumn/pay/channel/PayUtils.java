package com.autumn.pay.channel;

import com.autumn.util.BigDecimalUtils;

import java.math.BigDecimal;
import java.math.RoundingMode;

/**
 * 支付帮助
 *
 * @Description TODO
 * @Author 老码农
 * @Date 2019-07-12 16:55
 */
public class PayUtils {

    /**
     * 获取百分制金额
     *
     * @param value
     * @return
     */
    public static long getPercentMoney(BigDecimal value) {
        if (value == null) {
            value = new BigDecimal(0L);
        }
        return BigDecimalUtils.roundUp(value, 2).multiply(new BigDecimal(100)).longValue();
    }

    /**
     * 获取百分制实际金额
     *
     * @param value
     * @return
     */
    public static BigDecimal getPercentActualMoney(long value) {
        return new BigDecimal(value).divide(new BigDecimal(100), 2, RoundingMode.HALF_UP);
    }
}
