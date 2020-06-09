package com.autumn.evaluator.functions;

import com.autumn.exception.ArgumentOverflowException;

/**
 * 圆周率
 */
public class Pi {

    /**
     * 获取格式长度
     */
    private int formatLength;

    public final int getFormatLength() {
        return formatLength;
    }

    private void setFormatLength(int value) {
        formatLength = value;
    }

    /**
     * 获取基数
     */
    private long base;

    public final long getBase() {
        return base;
    }

    private void setBase(long value) {
        base = value;
    }

    /**
     * 实例化 Pi 类新实例
     *
     * @param formatLength 格式长度
     */
    public Pi(int formatLength) {
        if (formatLength < 1) {
            throw new ArgumentOverflowException("formatLength", "formatLength 小于 1 。");
        }
        this.setFormatLength(formatLength);
        this.setBase((long) Math.pow(10, formatLength));
    }

    /**
     * 格式化
     *
     * @param pi
     */
    private void format(long[] pi) {
        long quotient = 0;
        for (int i = 0; i < pi.length; i++) {
            long numerator = pi[i] + quotient;
            quotient = numerator / this.getBase();
            long remainder = numerator % this.getBase();
            if (remainder < 0) {
                remainder += this.getBase();
                quotient--;
            }
            pi[i] = remainder;
        }
    }

    /**
     * 除
     *
     * @param updateSum
     * @param positive
     * @param updateDividend
     * @param digits
     * @param sum
     * @param dividend
     * @param divisor
     * @return
     */
    private int divide(boolean updateSum, boolean positive, boolean updateDividend, int digits, long[] sum, long[] dividend, int divisor) {
        long remainder = 0;
        for (int i = digits; i >= 0; i--) {
            long quotient = this.getBase() * remainder + dividend[i];
            remainder = quotient % divisor;
            quotient /= divisor;
            if (updateDividend) {
                dividend[i] = quotient;
            }
            if (!updateSum) {
                continue;
            }
            if (positive) {
                sum[i] += quotient;
            } else {
                sum[i] -= quotient;
            }
        }
        if (updateDividend) {
            while (digits > 0 && dividend[digits] == 0) {
                digits--;
            }
        }
        return digits;
    }

    /**
     * 计算参数
     */
    private static int[] arguments = {176, 57, 28, 239, -48, 682, 96, 12943};

    /**
     * 计算,注长度小于 FormatLength 时，需在前面补零
     *
     * @param digit 位数
     * @return
     */
    public final long[] compute(int digit) {
        if (digit < this.getFormatLength()) {
            throw new ArgumentOverflowException("digit", "digit 不能小于 FormatLength 。");
        }
        if (digit % this.getFormatLength() != 0) {
            throw new ArgumentOverflowException("digit", "digit 不能被 FormatLength 整除。");
        }
        digit = digit / this.getFormatLength() + 1;
        long[] pi = new long[digit + 1];
        long[] tmp = new long[digit + 1];
        for (int i = 0; i < arguments.length; i += 2) {
            for (int index = 0; index < tmp.length; index++) {
                tmp[index] = 0;
            }
            tmp[digit] = arguments[i];
            int divisor = arguments[i + 1];
            int digits2 = this.divide(true, true, true, digit, pi, tmp, divisor);
            boolean positive = false;
            divisor *= divisor;
            for (int step = 3; digits2 > 0; positive = !positive, step += 2) {
                digits2 = this.divide(false, true, true, digits2, null, tmp, divisor);
                digits2 = this.divide(true, positive, false, digits2, pi, tmp, step);
            }
        }
        this.format(pi);
        return pi;
    }

    /**
     * 计算
     *
     * @param digit          位数
     * @param spaceSeparator 添加空格分隔符
     * @return
     */
    public final String computeString(int digit, boolean spaceSeparator) {
        long[] coms = this.compute(digit);
        StringBuilder str = new StringBuilder();
        str.append(String.format("%s.", coms[coms.length - 1] + ""));
        int length = coms.length - 2;
        for (int i = length; i > 0; i--) {
            String num = coms[i] + "";
            StringBuilder zeroPrefix = new StringBuilder();
            for (int index = 0; index < this.getFormatLength() - num.length(); index++) {
                zeroPrefix.append("0");
            }
            str.append(zeroPrefix + num);
            if (spaceSeparator && i > 1) {
                str.append(" ");
            }
        }
        return str.toString();
    }
}