package com.autumn.evaluator.functions;

import com.autumn.evaluator.*;
import com.autumn.evaluator.annotation.FunctionRegister;
import com.autumn.evaluator.annotation.ParamRegister;
import com.autumn.util.TypeUtils;

import java.util.ArrayList;

/**
 * 统计函数
 */
public class StatisticsFunction {

    /**
     * 返回所有参数的算术平均数(至需要一个参数，但不限制参数上限)
     */
    @FunctionRegister(name = "Average", category = "统计", caption = "返回所有参数的算术平均数(至需要1个参数，但不限制参数上限) 。", isDynamicParam = true, dynamicParamType = VariantType.NUMBER | VariantType.ARRAY, minParamCount = 1)
    public static class Average extends AbstractFunctionBase implements Callable {
        @Override
        public Variant call(String name, FunctionParamContext paramContext, Context context) {
            ArrayList<Variant> items = Extension.readFunctionParams(paramContext, Variant::isNumber);
            if (items.size() == 1) {
                return items.get(0);
            }
            Variant value = new Variant(0);
            for (Variant item : items) {
                value = Variant.add(value, item);
            }
            return Variant.divide(value, new Variant(items.size()));
        }
    }


    /**
     * 返回所有参数的几何平均数(至需要一个参数，但不限制参数上限)
     */
    @FunctionRegister(name = "GeoMean", category = "统计", caption = "返回所有参数的几何平均数(至需要1个参数，但不限制参数上限) 。", isDynamicParam = true, dynamicParamType = VariantType.NUMBER | VariantType.ARRAY, minParamCount = 1)
    public static class GeoMean extends AbstractFunctionBase implements Callable {
        @Override
        public Variant call(String name, FunctionParamContext paramContext, Context context) {
            ArrayList<Variant> items = Extension.readFunctionParams(paramContext, Variant::isNumber);
            if (items.size() == 1) {
                return items.get(0);
            }
            Variant result = new Variant(1);
            for (Variant item : items) {
                result = Variant.multiply(result, new Variant(Math.pow(TypeUtils.toConvert(double.class, item.getValue()), 1.0 / (double) items.size())));
            }
            return result;
        }
    }


    /**
     * 返回所有参数的调和平均数，所有参数倒数平均值的倒数(至需要一个参数，但不限制参数上限)
     */
    @FunctionRegister(name = "HarMean", category = "统计", caption = "返回所有参数的调和平均数，所有参数倒数平均值的倒数(至需要1个参数，但不限制参数上限) 。", isDynamicParam = true, dynamicParamType = VariantType.NUMBER | VariantType.ARRAY, minParamCount = 1)
    public static class HarMean extends AbstractFunctionBase implements Callable {
        @Override
        public Variant call(String name, FunctionParamContext paramContext, Context context) {
            ArrayList<Variant> items = Extension.readFunctionParams(paramContext, Variant::isNumber);
            if (items.size() == 1) {
                return items.get(0);
            }
            Variant sum = new Variant(0);
            for (Variant item : items) {
                sum = Variant.add(sum, (Variant.divide(new Variant(1.0), item)));
            }
            return Variant.divide(new Variant(items.size()), sum);
        }
    }


    /**
     * 返回所有参数的平方平均数(至需要一个参数，但不限制参数上限)
     */
    @FunctionRegister(name = "SquareMean", category = "统计", caption = "返回所有参数的平方平均数(至需要1个参数，但不限制参数上限) 。", isDynamicParam = true, dynamicParamType = VariantType.NUMBER | VariantType.ARRAY, minParamCount = 1)
    public static class SquareMean extends AbstractFunctionBase implements Callable {
        @Override
        public Variant call(String name, FunctionParamContext paramContext, Context context) {
            ArrayList<Variant> items = Extension.readFunctionParams(paramContext, Variant::isNumber);
            Variant sum = new Variant(0);
            for (Variant item : items) {
                sum = Variant.add(sum, Variant.multiply(item, item));
            }
            return new Variant(Math.sqrt(TypeUtils.toConvert(double.class, sum.getValue()) / items.size()));
        }
    }

    /**
     * 返回所有参数的总体样本方差(至需要一个参数，但不限制参数上限)
     */
    @FunctionRegister(name = "Varp", category = "统计", caption = "返回所有参数的总体样本方差(至需要一个参数，但不限制参数上限) 。", isDynamicParam = true, dynamicParamType = VariantType.NUMBER | VariantType.ARRAY, minParamCount = 1)
    public static class Varp extends AbstractFunctionBase implements Callable {
        @Override
        public Variant call(String name, FunctionParamContext paramContext, Context context) {
            ArrayList<Variant> items = Extension.readFunctionParams(paramContext, Variant::isNumber);
            if (items.size() == 1) {
                return items.get(0);
            }
            Variant pow = new Variant(0);
            Variant sum = new Variant(0);
            for (Variant item : items) {
                pow = Variant.add(pow, new Variant(Math.pow(TypeUtils.toConvert(double.class, item.getValue()), 2.0)));
                sum = Variant.add(sum, item);
            }
            return Variant.subtract(Variant.divide(pow, new Variant(items.size())), new Variant(Math.pow(TypeUtils.toConvert(double.class, sum.getValue()) / items.size(), 2)));
        }
    }


    /**
     * 返回所有参数方差(至需要一个参数，但不限制参数上限)
     */
    @FunctionRegister(name = "Var", category = "统计", caption = "返回所有参数方差(至需要一个参数，但不限制参数上限) 。", isDynamicParam = true, dynamicParamType = VariantType.NUMBER | VariantType.ARRAY, minParamCount = 1)
    public static class Var extends AbstractFunctionBase implements Callable {
        @Override
        public Variant call(String name, FunctionParamContext paramContext, Context context) {
            ArrayList<Variant> items = Extension.readFunctionParams(paramContext, Variant::isNumber);
            if (items.size() == 1) {
                return items.get(0);
            }
            Variant pow = new Variant(0);
            Variant sum = new Variant(0);
            for (Variant item : items) {
                pow = Variant.add(pow, new Variant(Math.pow(TypeUtils.toConvert(double.class, item.getValue()), 2.0)));
                sum = Variant.add(sum, item);
            }
            return Variant.multiply(Variant.divide(Variant.subtract(pow, Variant.multiply(new Variant(sum), new Variant(2))), new Variant(items.size() * (items.size() - 1))), new Variant(2));
        }
    }


    /**
     * 计算所有包含数字的个数(至需要一个参数，但不限制参数上限)
     */
    @FunctionRegister(name = "Count", category = "统计", caption = "计算所有参数包含数字的个数(至需要一个参数，但不限制参数上限) 。", isDynamicParam = true, dynamicParamType = VariantType.NULL, minParamCount = 1)
    public static class Count extends AbstractFunctionBase implements Callable {
        @Override
        public Variant call(String name, FunctionParamContext paramContext, Context context) {
            return new Variant(Extension.countFunctionParams(paramContext, Variant::isNumber));
        }
    }


    /**
     * 计算符合条件所有参数个数
     */
    @FunctionRegister(name = "CountIf", category = "统计", caption = "计算符合条件所有参数个数。", minParamCount = 2)
    @ParamRegister(order = 1, name = "Range", caption = "进行行计算的值或数组。", paramType = VariantType.NUMBER | VariantType.ARRAY)
    @ParamRegister(order = 2, name = "Criteria", caption = "定义的条件、数字或表达式。", paramType = VariantType.STRING | VariantType.NUMBER)
    public static class CountIf extends AbstractFunctionBase implements Callable {
        @Override
        public Variant call(String name, FunctionParamContext paramContext, Context context) {
            Variant range = paramContext.getParam(1).getValue();
            Variant criteria = paramContext.getParam(2).getValue();
            java.util.function.Function<Variant, Boolean> predicate = createCriteria(name, criteria.getName(), true, criteria);
            return new Variant(Extension.readFunParams(range, predicate).size());
        }
    }


    /**
     * 计算所有参数的非空个数(至需要一个参数，但不限制参数上限)
     */
    @FunctionRegister(name = "CountA", category = "统计", caption = "计算所有参数的非空个数(至需要一个参数，但不限制参数上限) 。", isDynamicParam = true, dynamicParamType = VariantType.NULL, minParamCount = 1)
    public static class CountA extends AbstractFunctionBase implements Callable {
        @Override
        public Variant call(String name, FunctionParamContext paramContext, Context context) {
            return new Variant(Extension.countFunctionParams(paramContext, (s) -> !s.isNull() || (s.isString() && !"".equals(s.toString()))));
        }
    }


    /**
     * 计算所有参数的空白个数(至需要一个参数，但不限制参数上限)
     */
    @FunctionRegister(name = "CountBlank", category = "统计", caption = "计算所有参数的空白个数(至需要一个参数，但不限制参数上限) 。", isDynamicParam = true, dynamicParamType = VariantType.NULL, minParamCount = 1)
    public static class CountBlank extends AbstractFunctionBase implements Callable {
        @Override
        public Variant call(String name, FunctionParamContext paramContext, Context context) {
            return new Variant(Extension.countFunctionParams(paramContext, (s) -> s.isNull() || (s.isString() && "".equals(s.toString()))));
        }
    }

}