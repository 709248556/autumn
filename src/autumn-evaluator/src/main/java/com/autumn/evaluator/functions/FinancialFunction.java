package com.autumn.evaluator.functions;

import com.autumn.evaluator.*;
import com.autumn.evaluator.annotation.FunctionRegister;
import com.autumn.evaluator.annotation.ParamRegister;
import com.autumn.evaluator.exception.FunctionParamException;
import com.autumn.util.TypeUtils;

import java.math.BigDecimal;
import java.math.RoundingMode;

/**
 * 财务金融函数
 */
public class FinancialFunction {

    /**
     * 基于固定利率及等额分期付款方式，返回贷款的每期付款额。
     */
    @FunctionRegister(name = "PMT", category = "财务", caption = "基于固定利率及等额分期付款方式，返回贷款的每期付款额。", minParamCount = 3)
    @ParamRegister(order = 1, name = "Rate", caption = "贷款利率。（月利率）", paramType = VariantType.NUMBER)
    @ParamRegister(order = 2, name = "Nper", caption = "贷款期数。", paramType = VariantType.NUMBER)
    @ParamRegister(order = 3, name = "Pv", caption = "贷款金额。", paramType = VariantType.NUMBER)
    public static class PMT extends AbstractFunctionBase implements Callable {
        @Override
        public Variant call(String name, FunctionParamContext paramContext, Context context) {
            double rate = paramContext.getParam(1).getValue().doubleValue();
            double nper = paramContext.getParam(2).getValue().doubleValue();
            double pv = paramContext.getParam(3).getValue().doubleValue();
            if (rate == 0) {
                throw new FunctionParamException(name, paramContext.getParam(1).getName(), "利率不能等于0。");
            }
            if (nper == 0) {
                throw new FunctionParamException(name, paramContext.getParam(2).getName(), "贷款期数不能等于0。");
            }
            if (pv == 0) {
                throw new FunctionParamException(name, paramContext.getParam(3).getName(), "贷款金额不能等于0。");
            }
            return new Variant(pv * rate * (1 + (1 / (Math.pow((1.0 + rate), nper) - 1))));
        }
    }


    /**
     * 用固定余额递减速法，返回指定期间内某项固定资产的折旧值。
     */
    @FunctionRegister(name = "DB", category = "财务", caption = "用固定余额递减速法，返回指定期间内某项固定资产的折旧值。", minParamCount = 4)
    @ParamRegister(order = 1, name = "Cost", caption = "固定资产原值。", paramType = VariantType.NUMBER)
    @ParamRegister(order = 2, name = "Salvage", caption = "资产使用年限结束时的估计残值。", paramType = VariantType.NUMBER)
    @ParamRegister(order = 3, name = "Life", caption = "进行折旧计算的周期总数。", paramType = VariantType.INTEGER)
    @ParamRegister(order = 4, name = "Period", caption = "进行折旧计算的期次。", paramType = VariantType.INTEGER)
    @ParamRegister(order = 5, name = "Month", caption = "第一年的月份数，默认为12。", paramType = VariantType.INTEGER, defaultValue = "12")
    public static class DB extends AbstractFunctionBase implements Callable {
        @Override
        public Variant call(String name, FunctionParamContext paramContext, Context context) {
            Variant p1 = paramContext.getParam(1).getValue();
            Variant p2 = paramContext.getParam(2).getValue();
            Variant p3 = paramContext.getParam(3).getValue();
            Variant p4 = paramContext.getParam(4).getValue();
            Variant p5 = paramContext.getParam(5).getValue();
            BigDecimal cost = p1.toBigDecimal();
            BigDecimal salvage = p2.toBigDecimal();
            int life = p3.intValue();
            int period = p4.intValue();
            int month = p5.intValue();
            if (life < 1) {
                throw new FunctionParamException(name, paramContext.getParam(3).getName(), "总期数不能小于1。");
            }
            if (period < 1) {
                throw new FunctionParamException(name, paramContext.getParam(4).getName(), "期间数不能小于1。");
            }
            if (month < 1) {
                throw new FunctionParamException(name, paramContext.getParam(5).getName(), "第一年的月份数不能小于1。");
            }
            if (month > 12) {
                throw new FunctionParamException(name, paramContext.getParam(5).getName(), "第一年的月份数不能大于12。");
            }
            if (period > life) {
                throw new FunctionParamException(name, paramContext.getParam(4).getName(), "期数不能大于总期数。");
            }
            BigDecimal rate = new BigDecimal(1 - Math.pow(salvage.divide(cost, 3, RoundingMode.HALF_UP).doubleValue(), 1.0 / (double) life)).setScale(3, BigDecimal.ROUND_HALF_UP); //保留三位小数，与Excel一样
            if (period == 1) {
                return new Variant(cost.multiply(rate).multiply(new BigDecimal(month / 12.0)));
            } else {
                java.math.BigDecimal sumDb = dbSum(cost, period, month, rate);
                if (period == life) {
                    java.math.BigDecimal value = cost.subtract(salvage).subtract(sumDb);
                    if (value.compareTo(new BigDecimal(0)) > 0) {
                        return new Variant(value);
                    } else {
                        return new Variant(0);
                    }
                } else {
                    return new Variant((cost.subtract(sumDb)).multiply(rate));
                }
            }
        }
    }

    //用固定余额递减速法前期折旧总额
    private static BigDecimal dbSum(BigDecimal cost, int period, int month, BigDecimal rate) {
        BigDecimal value = cost.multiply(rate).multiply(new BigDecimal(month)).divide(new BigDecimal(12), 2, RoundingMode.HALF_UP);
        for (int p = 2; p < period; p++) {
            value.add(cost.subtract(value)).multiply(rate);
        }
        return value;
    }


    /**
     * 双倍余额递减法或其他指定方法，返回指定期间内某项固定资产的折旧值。
     */
    @FunctionRegister(name = "DDB", category = "财务", caption = "双倍余额递减法或其他指定方法，返回指定期间内某项固定资产的折旧值。", minParamCount = 4)
    @ParamRegister(order = 1, name = "Cost", caption = "固定资产原值。", paramType = VariantType.NUMBER)
    @ParamRegister(order = 2, name = "Salvage", caption = "资产使用年限结束时的估计残值。", paramType = VariantType.NUMBER)
    @ParamRegister(order = 3, name = "Life", caption = "进行折旧计算的周期总数。", paramType = VariantType.INTEGER)
    @ParamRegister(order = 4, name = "Period", caption = "进行折旧计算的期次。", paramType = VariantType.INTEGER)
    @ParamRegister(order = 5, name = "Factor", caption = "倍数，默认为2(双倍)。", paramType = VariantType.INTEGER, defaultValue = "2")
    public static class DDB extends AbstractFunctionBase implements Callable {
        @Override
        public Variant call(String name, FunctionParamContext paramContext, Context context) {
            Variant p1 = paramContext.getParam(1).getValue();
            Variant p2 = paramContext.getParam(2).getValue();
            Variant p3 = paramContext.getParam(3).getValue();
            Variant p4 = paramContext.getParam(4).getValue();
            Variant p5 = paramContext.getParam(5).getValue();
            BigDecimal cost = p1.toBigDecimal();
            BigDecimal salvage = p2.toBigDecimal();
            int life = p3.intValue();
            int period = p4.intValue();
            BigDecimal factor = p5.toBigDecimal();
            if (cost.compareTo(new BigDecimal(0)) < 0) {
                throw new FunctionParamException(name, paramContext.getParam(1).getName(), "资产原值不能小于0。");
            }
            if (salvage.compareTo(new BigDecimal(0)) < 0) {
                throw new FunctionParamException(name, paramContext.getParam(2).getName(), "资产残值不能小于0。");
            }
            if (life < 3) {
                throw new FunctionParamException(name, paramContext.getParam(3).getName(), "总期数不能小于3。");
            }
            if (period < 1) {
                throw new FunctionParamException(name, paramContext.getParam(4).getName(), "期间数不能小于1。");
            }
            if (factor.compareTo(new BigDecimal(0)) <= 0) {
                throw new FunctionParamException(name, paramContext.getParam(5).getName(), "倍数不能小于或等于0。");
            }
            if (period > life) {
                throw new FunctionParamException(name, paramContext.getParam(4).getName(), "期数不能大于总期数。");
            }
            if (life - 1 <= factor.doubleValue()) //最后二期
            {
                double v = (cost.doubleValue() * Math.pow((1.0 - factor.doubleValue() / life), (period - 2.0)) - cost.doubleValue()) / factor.doubleValue();
                return new Variant(new BigDecimal(v));
            } else {
                double v = cost.doubleValue() * Math.pow((1.0 - factor.doubleValue() / life), (period - 1.0)) * factor.doubleValue() / life;
                return new Variant(new BigDecimal(v));
            }
        }
    }


    /**
     * 返回某项资产按年限总和折旧法计算的指定期间的折旧额
     */
    @FunctionRegister(name = "SYD", category = "财务", caption = "返回某项资产按年限总和折旧法计算的指定期间的折旧额。", minParamCount = 4)
    @ParamRegister(order = 1, name = "Cost", caption = "固定资产原值。", paramType = VariantType.NUMBER)
    @ParamRegister(order = 2, name = "Salvage", caption = "资产使用年限结束时的估计残值。", paramType = VariantType.NUMBER)
    @ParamRegister(order = 3, name = "Life", caption = "进行折旧计算的周期总数。", paramType = VariantType.INTEGER)
    @ParamRegister(order = 4, name = "Per", caption = "进行折旧计算的期次。", paramType = VariantType.INTEGER)
    public static class SYD extends AbstractFunctionBase implements Callable {
        @Override
        public Variant call(String name, FunctionParamContext paramContext, Context context) {
            BigDecimal cost = paramContext.getParam(1).getValue().toBigDecimal();
            BigDecimal salvage = paramContext.getParam(2).getValue().toBigDecimal();
            BigDecimal life = paramContext.getParam(3).getValue().toBigDecimal();
            BigDecimal per = paramContext.getParam(4).getValue().toBigDecimal();
            return new Variant((cost.subtract(salvage).multiply((life.subtract(per).add(new BigDecimal(1)))).multiply(new BigDecimal(2)))
                    .divide((life.multiply((life.add(new BigDecimal(1))))), 2, RoundingMode.HALF_UP));
        }
    }


    /**
     * 返回某项资产在一个期间中的线性折旧额
     */
    @FunctionRegister(name = "SLN", category = "财务", caption = "返回某项资产在一个期间中的线性折旧额。", minParamCount = 3)
    @ParamRegister(order = 1, name = "Cost", caption = "固定资产原值。", paramType = VariantType.NUMBER)
    @ParamRegister(order = 2, name = "Salvage", caption = "资产使用年限结束时的估计残值。", paramType = VariantType.NUMBER)
    @ParamRegister(order = 3, name = "Life", caption = "进行折旧计算的周期总数。", paramType = VariantType.INTEGER)
    public static class SLN extends AbstractFunctionBase implements Callable {
        @Override
        public Variant call(String name, FunctionParamContext paramContext, Context context) {
            java.math.BigDecimal cost = paramContext.getParam(1).getValue().toBigDecimal();
            java.math.BigDecimal salvage = paramContext.getParam(2).getValue().toBigDecimal();
            java.math.BigDecimal life = paramContext.getParam(3).getValue().toBigDecimal();
            return new Variant((cost.subtract(salvage)).divide(life, 2, RoundingMode.HALF_UP));
        }
    }

}