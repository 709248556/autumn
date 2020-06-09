package com.autumn.evaluator.functions;

import com.autumn.evaluator.*;
import com.autumn.evaluator.annotation.FunctionRegister;
import com.autumn.evaluator.annotation.ParamRegister;
import com.autumn.evaluator.exception.FunctionParamException;
import com.autumn.exception.NotSupportException;
import com.autumn.exception.OverflowException;
import com.autumn.util.TypeUtils;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Random;
import java.util.function.Function;

/**
 * 数学与三角函数
 */
public class ArithFunction {

    /**
     * 转换为浮点
     */
    @FunctionRegister(name = "D", category = "数学与三角", caption = "返回任意数值的浮点数。", minParamCount = 1)
    @ParamRegister(order = 1, name = "number", caption = "要求转换为浮点的任意数值。", paramType = VariantType.NUMBER)
    public static class D extends AbstractFunctionBase implements Callable {
        @Override
        public Variant call(String name, FunctionParamContext paramContext, Context context) {
            Variant p1 = paramContext.getParam(1).getValue();
            return new Variant(p1.toDouble());
        }
    }


    /**
     * 取绝对值函数
     */
    @FunctionRegister(name = "Abs", category = "数学与三角", caption = "取绝对值函数 。", minParamCount = 1)
    @ParamRegister(order = 1, name = "number", caption = "要对其求绝对值的任意数值。", paramType = VariantType.NUMBER)
    public static class Abs extends AbstractFunctionBase implements Callable {
        @Override
        public Variant call(String name, FunctionParamContext paramContext, Context context) {
            Variant p1 = paramContext.getParam(1).getValue();
            if (p1.getVariantType() == VariantType.INTEGER) {
                return new Variant(Math.abs(p1.longValue()));
            } else if (p1.getVariantType() == VariantType.DECIMAL) {
                return new Variant(p1.toBigDecimal().abs());
            } else {
                return new Variant(Math.abs(p1.doubleValue()));
            }
        }
    }


    /**
     * 弧度的返余弦
     */
    @FunctionRegister(name = "Acos", category = "数学与三角", caption = "弧度的返余弦 。", minParamCount = 1)
    @ParamRegister(order = 1, name = "number", caption = "余弦值，必须在-1和1之间 。", paramType = VariantType.NUMBER)
    public static class Acos extends AbstractFunctionBase implements Callable {
        @Override
        public Variant call(String name, FunctionParamContext paramContext, Context context) {
            Variant p1 = paramContext.getParam(1).getValue();
            return new Variant(Math.acos(p1.doubleValue()));
        }
    }


    /**
     * 弧度的返正弦
     */
    @FunctionRegister(name = "Asin", category = "数学与三角", caption = "弧度的返正弦。", minParamCount = 1)
    @ParamRegister(order = 1, name = "number", caption = "正弦值，必须在-1和1之间。", paramType = VariantType.NUMBER)
    public static class Asin extends AbstractFunctionBase implements Callable {
        @Override
        public Variant call(String name, FunctionParamContext paramContext, Context context) {
            Variant p1 = paramContext.getParam(1).getValue();
            return new Variant(Math.asin(p1.doubleValue()));
        }
    }


    /**
     * 返回正切值
     */
    @FunctionRegister(name = "Atan", category = "数学与三角", caption = "返回正切值 。", minParamCount = 1)
    @ParamRegister(order = 1, name = "number", caption = "角度的正切值。", paramType = VariantType.NUMBER)
    public static class Atan extends AbstractFunctionBase implements Callable {
        @Override
        public Variant call(String name, FunctionParamContext paramContext, Context context) {
            Variant p1 = paramContext.getParam(1).getValue();
            return new Variant(Math.atan(p1.doubleValue()));
        }
    }


    /**
     * 返回正切值
     */
    @FunctionRegister(name = "Atan2", category = "数学与三角", caption = "返回正切值。", minParamCount = 1)
    @ParamRegister(order = 1, name = "x_Number", caption = "某点的X轴坐标值 。", paramType = VariantType.NUMBER)
    @ParamRegister(order = 2, name = "y_Number", caption = "某点的Y轴坐标值 。", paramType = VariantType.NUMBER)
    public static class Atan2 extends AbstractFunctionBase implements Callable {
        @Override
        public Variant call(String name, FunctionParamContext paramContext, Context context) {
            Variant p1 = paramContext.getParam(1).getValue();
            Variant p2 = paramContext.getParam(2).getValue();
            return new Variant(Math.atan2(p1.doubleValue(), p2.doubleValue()));
        }
    }


    /**
     * 角度的余弦值
     */
    @FunctionRegister(name = "Cos", category = "数学与三角", caption = "角度的余弦值 。", minParamCount = 1)
    @ParamRegister(order = 1, name = "number", caption = "以弧度表示的，准备求其余弦值的角度。", paramType = VariantType.NUMBER)
    public static class Cos extends AbstractFunctionBase implements Callable {
        @Override
        public Variant call(String name, FunctionParamContext paramContext, Context context) {
            Variant p1 = paramContext.getParam(1).getValue();
            return new Variant(Math.cos(p1.doubleValue()));
        }
    }


    /**
     * 返回双曲余弦值
     */
    @FunctionRegister(name = "Cosh", category = "数学与三角", caption = "返回双曲余弦值。", minParamCount = 1)
    @ParamRegister(order = 1, name = "number", caption = "任意实数。", paramType = VariantType.NUMBER)
    public static class Cosh extends AbstractFunctionBase implements Callable {
        @Override
        public Variant call(String name, FunctionParamContext paramContext, Context context) {
            Variant p1 = paramContext.getParam(1).getValue();
            return new Variant(Math.cosh(p1.doubleValue()));
        }
    }


    /**
     * 返回大于或等于指定的数值的最小整数值
     */
    @FunctionRegister(name = "Ceiling", category = "数学与三角", caption = "返回大于或等于指定的数值的最小整数值。", minParamCount = 1)
    @ParamRegister(order = 1, name = "number", caption = "需入舍入实数。", paramType = VariantType.NUMBER)
    public static class Ceiling extends AbstractFunctionBase implements Callable {
        @Override
        public Variant call(String name, FunctionParamContext paramContext, Context context) {
            Variant p1 = paramContext.getParam(1).getValue();
            if (p1.getVariantType() == VariantType.DOUBLE) {
                return new Variant(Math.ceil(p1.doubleValue()));
            } else {
                return new Variant(Math.ceil(p1.toBigDecimal().doubleValue()));
            }
        }
    }


    /**
     * 返回小于或等于指定数值的最大整数
     */
    @FunctionRegister(name = "Floor", category = "数学与三角", caption = "返回小于或等于指定数值的最大整数。", minParamCount = 1)
    @ParamRegister(order = 1, name = "number", caption = "需入舍入实数 。", paramType = VariantType.NUMBER)
    public static class Floor extends AbstractFunctionBase implements Callable {
        @Override
        public Variant call(String name, FunctionParamContext paramContext, Context context) {
            Variant p1 = paramContext.getParam(1).getValue();
            if (p1.getVariantType() == VariantType.DOUBLE) {
                return new Variant(Math.floor(p1.doubleValue()));
            } else {
                return new Variant(Math.floor(p1.toBigDecimal().doubleValue()));
            }
        }
    }


    /**
     * 返回自然对数的底
     */
    @FunctionRegister(name = "E", category = "数学与三角", caption = "返回自然对数的底。")
    public static class E extends AbstractFunctionBase implements Callable {
        @Override
        public Variant call(String name, FunctionParamContext paramContext, Context context) {
            return new Variant(Math.E);
        }
    }


    /**
     * 返回e的n次方
     */
    @FunctionRegister(name = "Exp", category = "数学与三角", caption = "返回e的n次方 。", minParamCount = 1)
    @ParamRegister(order = 1, name = "number", caption = "指数。常数e等于2.71828182845905,是自然对数的底 。", paramType = VariantType.NUMBER)
    public static class Exp extends AbstractFunctionBase implements Callable {
        @Override
        public Variant call(String name, FunctionParamContext paramContext, Context context) {
            Variant p1 = paramContext.getParam(1).getValue();
            return new Variant(Math.exp(p1.doubleValue()));
        }
    }


    /**
     * 根据给定的底数返回数字的对数
     */
    @FunctionRegister(name = "Log", category = "数学与三角", caption = "根据给定的底数返回数字的对数 。", minParamCount = 2)
    @ParamRegister(order = 1, name = "NUMBER", caption = "为用于计算对数的正实数 。", paramType = VariantType.NUMBER)
    @ParamRegister(order = 2, name = "Base", caption = "计算对数的底数 。", paramType = VariantType.NUMBER)
    public static class Log extends AbstractFunctionBase implements Callable {
        @Override
        public Variant call(String name, FunctionParamContext paramContext, Context context) {
            Variant p1 = paramContext.getParam(1).getValue();
            Variant p2 = paramContext.getParam(2).getValue();
            return new Variant(Math.log(p1.doubleValue()) / Math.log(p2.doubleValue()));
        }
    }


    /**
     * 返回给定数值以10为底的对数
     */
    @FunctionRegister(name = "Log10", category = "数学与三角", caption = "返回给定数值以10为底的对数 。", minParamCount = 1)
    @ParamRegister(order = 1, name = "NUMBER", caption = "要对其求以10为底的对数的正实数。", paramType = VariantType.NUMBER)
    public static class Log10 extends AbstractFunctionBase implements Callable {
        @Override
        public Variant call(String name, FunctionParamContext paramContext, Context context) {
            Variant p1 = paramContext.getParam(1).getValue();
            return new Variant(Math.log10(p1.doubleValue()));
        }
    }


    /**
     * 返回所有参数中最大的一个，如果相同则返回首个最大。
     */
    @FunctionRegister(name = "Max", category = "数学与三角", caption = "返回所有参数中最大的一个，如果相同则返回首个最大。", isDynamicParam = true, dynamicParamType = VariantType.NUMBER | VariantType.ARRAY, minParamCount = 1)
    public static class Max extends AbstractFunctionBase implements Callable {
        @Override
        public Variant call(String name, FunctionParamContext params, Context context) {
            ArrayList<Variant> items = Extension.readFunctionParams(params, (s) -> s.isNumber());
            Variant first = items.get(0);
            if (items.size() == 1) {
                return first;
            }
            for (int i = 1; i < items.size(); i++) {
                Variant variant = items.get(i);
                if (variant.compareTo(first) > 0) {
                    first = variant;
                }
            }
            return first;
        }
    }


    /**
     * 返回所有参数中最小的一个，如果相同则返回首个最小。
     */
    @FunctionRegister(name = "Min", category = "数学与三角", caption = "返回所有参数中最小的一个，如果相同则返回首个最小。", isDynamicParam = true, dynamicParamType = VariantType.NUMBER | VariantType.ARRAY, minParamCount = 1)
    public static class Min extends AbstractFunctionBase implements Callable {
        @Override
        public Variant call(String name, FunctionParamContext params, Context context) {
            ArrayList<Variant> items = Extension.readFunctionParams(params, (s) -> s.isNumber());
            Variant first = items.get(0);
            if (items.size() == 1) {
                return first;
            }
            for (int i = 1; i < items.size(); i++) {
                Variant variant = items.get(i);
                if (variant.compareTo(first) < 0) {
                    first = variant;
                }
            }
            return first;
        }
    }


    /**
     * 返回两数相除的余数
     */
    @FunctionRegister(name = "Mod", category = "数学与三角", caption = "返回两数相除的余数。", minParamCount = 2)
    @ParamRegister(order = 1, name = "NUMBER", caption = "被除数。", paramType = VariantType.NUMBER)
    @ParamRegister(order = 2, name = "Divisor", caption = "除数。", paramType = VariantType.NUMBER)
    public static class Mod extends AbstractFunctionBase implements Callable {
        @Override
        public Variant call(String name, FunctionParamContext paramContext, Context context) {
            Variant p1 = paramContext.getParam(1).getValue();
            Variant p2 = paramContext.getParam(2).getValue();
            return Variant.modulo(p1, p2);
        }
    }


    /**
     * 返回圆周率的Pi的值,3.14159265358979,精确到15位
     */
    @FunctionRegister(name = "Pi", category = "数学与三角", caption = "返回圆周率的Pi的值,3.14159265358979,精确到15位。")
    public static class Pi extends AbstractFunctionBase implements Callable {
        @Override
        public Variant call(String name, FunctionParamContext paramContext, Context context) {
            return new Variant(Math.PI);
        }
    }


    /**
     * 返回圆周率的Pi的指定长度值字符串
     */
    @FunctionRegister(name = "BigPi", category = "数学与三角", caption = "返回圆周率的Pi的指定长度值。", isDynamicParam = false, dynamicParamType = VariantType.NULL, minParamCount = 1)
    @ParamRegister(order = 1, name = "Digit", caption = "大于零的位数", paramType = VariantType.INTEGER)
    @ParamRegister(order = 2, name = "Separator", caption = "空格分隔", paramType = VariantType.BOOLEAN, defaultValue = "true")
    @ParamRegister(order = 3, name = "FormatLength", caption = "格式长度", paramType = VariantType.INTEGER, defaultValue = "5")
    public static class BigPi extends AbstractFunctionBase implements Callable {
        @Override
        public Variant call(String name, FunctionParamContext paramContext, Context context) {
            int digit = paramContext.getParam(1).getValue().intValue();
            boolean separator = paramContext.getParam(2).getValue().booleanValue();
            int length = paramContext.getParam(3).getValue().intValue();
            if (length < 2) {
                throw new FunctionParamException(name, paramContext.getParam(3).getName(), "FormatLength 小于 2 。");
            }
            if (digit < length) {
                throw new FunctionParamException(name, paramContext.getParam(1).getName(), "Digit 小于 FormatLength 。");
            }
            if (digit % length != 0) {
                throw new FunctionParamException(name, paramContext.getParam(1).getName(), "Digit 不能被 FormatLength 整除。");
            }
            com.autumn.evaluator.functions.Pi pi = new com.autumn.evaluator.functions.Pi(length);
            return new Variant(pi.computeString(digit, separator));
        }
    }


    /**
     * 返回某数的乘幂
     */
    @FunctionRegister(name = "power", category = "数学与三角", caption = "返回某数的乘幂 。", minParamCount = 2)
    @ParamRegister(order = 1, name = "NUMBER", caption = "底数。", paramType = VariantType.NUMBER)
    @ParamRegister(order = 2, name = "power", caption = "幂值。", paramType = VariantType.NUMBER)
    public static class Power extends AbstractFunctionBase implements Callable {
        @Override
        public Variant call(String name, FunctionParamContext paramContext, Context context) {
            Variant p1 = paramContext.getParam(1).getValue();
            Variant p2 = paramContext.getParam(2).getValue();
            return new Variant(Math.pow(p1.doubleValue(), p2.doubleValue()));
        }
    }


    /**
     * 按指定的位数对数值进行四舍五入
     */
    @FunctionRegister(name = "Round", category = "数学与三角", caption = "按指定的位数对数值进行四舍五入。", minParamCount = 2)
    @ParamRegister(order = 1, name = "NUMBER", caption = "要四舍五入的实数。", paramType = VariantType.NUMBER)
    @ParamRegister(order = 2, name = "Num_digits", caption = "执行四舍五入时采用的位数。", paramType = VariantType.NUMBER)
    public static class Round extends AbstractFunctionBase implements Callable {
        @Override
        public Variant call(String name, FunctionParamContext paramContext, Context context) {
            Variant p1 = paramContext.getParam(1).getValue();
            Variant p2 = paramContext.getParam(2).getValue();
            BigDecimal decimal = p1.toBigDecimal();
            decimal = decimal.setScale(TypeUtils.toConvert(int.class, p2.getValue()), BigDecimal.ROUND_HALF_UP);
            if (p1.getVariantType() == VariantType.DECIMAL) {
                return new Variant(decimal);
            } else if (p1.getVariantType() == VariantType.INTEGER) {
                return new Variant(decimal.intValue());
            } else {
                return new Variant(decimal.doubleValue());
            }
        }
    }


    /**
     * 返回给定角度的正切值
     */
    @FunctionRegister(name = "Sin", category = "数学与三角", caption = "返回给定角度的正切值。", minParamCount = 1)
    @ParamRegister(order = 1, name = "NUMBER", caption = "以弧度表示的，准备求其正弦值的角度。Degrees * PI()/180=radians。", paramType = VariantType.NUMBER)
    public static class Sin extends AbstractFunctionBase implements Callable {
        @Override
        public Variant call(String name, FunctionParamContext paramContext, Context context) {
            Variant p1 = paramContext.getParam(1).getValue();
            return new Variant(Math.sin(p1.doubleValue()));
        }
    }


    /**
     * 返回双曲正弦值
     */
    @FunctionRegister(name = "Sinh", category = "数学与三角", caption = "返回双曲正弦值。", minParamCount = 1)
    @ParamRegister(order = 1, name = "NUMBER", caption = "任意实数。", paramType = VariantType.NUMBER)
    public static class Sinh extends AbstractFunctionBase implements Callable {
        @Override
        public Variant call(String name, FunctionParamContext paramContext, Context context) {
            Variant p1 = paramContext.getParam(1).getValue();
            return new Variant(Math.sinh(p1.doubleValue()));
        }
    }


    /**
     * 返回数值的平方根
     */
    @FunctionRegister(name = "Sqrt", category = "数学与三角", caption = "返回数值的平方根 。", minParamCount = 1)
    @ParamRegister(order = 1, name = "NUMBER", caption = "要对其求平方根的数值。", paramType = VariantType.NUMBER)
    public static class Sqrt extends AbstractFunctionBase implements Callable {
        @Override
        public Variant call(String name, FunctionParamContext paramContext, Context context) {
            Variant p1 = paramContext.getParam(1).getValue();
            return new Variant(Math.sqrt(p1.doubleValue()));
        }
    }


    /**
     * 返回数字 * PI 的平方根
     */
    @FunctionRegister(name = "SqrtPi", category = "数学与三角", caption = "返回数字 * PI 的平方根 。", minParamCount = 1)
    @ParamRegister(order = 1, name = "NUMBER", caption = "与 Pi 乘积的数量。", paramType = VariantType.NUMBER)
    public static class SqrtPi extends AbstractFunctionBase implements Callable {
        @Override
        public Variant call(String name, FunctionParamContext paramContext, Context context) {
            Variant p1 = paramContext.getParam(1).getValue();
            return new Variant(Math.sqrt(p1.doubleValue() * Math.PI));
        }
    }


    /**
     * 返回给定角度的正切值
     */
    @FunctionRegister(name = "Tan", category = "数学与三角", caption = "返回给定角度的正切值 。", minParamCount = 1)
    @ParamRegister(order = 1, name = "NUMBER", caption = "以弧度表示的，准备求其正切值的角度。Degrees * PI()/180=radians。", paramType = VariantType.NUMBER)
    public static class Tan extends AbstractFunctionBase implements Callable {
        @Override
        public Variant call(String name, FunctionParamContext paramContext, Context context) {
            Variant p1 = paramContext.getParam(1).getValue();
            return new Variant(Math.tan(p1.doubleValue()));
        }
    }


    /**
     * 返回双曲正切值
     */
    @FunctionRegister(name = "Tanh", category = "数学与三角", caption = "返回双曲正切值 。", minParamCount = 1)
    @ParamRegister(order = 1, name = "NUMBER", caption = "任意实数。", paramType = VariantType.NUMBER)
    public static class Tanh extends AbstractFunctionBase implements Callable {
        @Override
        public Variant call(String name, FunctionParamContext paramContext, Context context) {
            Variant p1 = paramContext.getParam(1).getValue();
            return new Variant(Math.tanh(p1.doubleValue()));
        }
    }


    /**
     * 将弧度转角度
     */
    @FunctionRegister(name = "Degrees", category = "数学与三角", caption = "将弧度转角度 。", minParamCount = 1)
    @ParamRegister(order = 1, name = "Angle", caption = "以表示弧度的角。", paramType = VariantType.NUMBER)
    public static class Degrees extends AbstractFunctionBase implements Callable {
        @Override
        public Variant call(String name, FunctionParamContext paramContext, Context context) {
            Variant p1 = paramContext.getParam(1).getValue();
            return new Variant(180.0 / Math.PI * p1.doubleValue());
        }
    }


    /**
     * 将角度转弧度
     */
    @FunctionRegister(name = "RadiAns", category = "数学与三角", caption = "将角度转弧度 。", minParamCount = 1)
    @ParamRegister(order = 1, name = "Angle", caption = "要转成弧度的角度值。", paramType = VariantType.NUMBER)
    public static class RadiAns extends AbstractFunctionBase implements Callable {
        @Override
        public Variant call(String name, FunctionParamContext paramContext, Context context) {
            Variant p1 = paramContext.getParam(1).getValue();
            return new Variant(p1.doubleValue() * (Math.PI / 180.0));
        }
    }

    /**
     * 将数字截为整数
     */
    @FunctionRegister(name = "Int", category = "数学与三角", caption = "将数字截为整数 。", minParamCount = 1)
    @ParamRegister(order = 1, name = "NUMBER", caption = "要进行取整的实数。", paramType = VariantType.NUMBER)
    @ParamRegister(order = 2, name = "Length", caption = "指取截断的长度,超过总长度则返回0；默认为0(小于零则不变，0表示取整,1表示个位,2表示十位,以此类推)。", paramType = VariantType.INTEGER, defaultValue = "0")
    public static class Int extends AbstractFunctionBase implements Callable {
        @Override
        public Variant call(String name, FunctionParamContext paramContext, Context context) {
            Variant p1 = paramContext.getParam(1).getValue();
            int length = paramContext.getParam(2).getValue().intValue();
            if (length < 0) {
                return p1;
            }
            StringBuilder str = new StringBuilder("1");
            for (int i = 0; i < length; i++) {
                str.append("0");
            }
            BigDecimal v = new BigDecimal(str.toString());
            if (p1.getVariantType() == VariantType.INTEGER) {
                if (length <= 0) {
                    return p1;
                } else {
                    if (length > 28) {
                        throw new OverflowException("指定的参数 Length 太大，最高不能超过28位。");
                    }
                    BigDecimal value = p1.toBigDecimal();
                    value = new BigDecimal(Math.floor(value.divide(v, length, RoundingMode.HALF_UP).doubleValue()));
                    value = value.multiply(v);
                    return intCast(value);
                }
            } else if (p1.getVariantType() == VariantType.DECIMAL) {
                BigDecimal value = new BigDecimal(p1.toBigDecimal().toBigInteger());
                if (length <= 0) {
                    return intCast(value);
                } else {
                    if (length > 28) {
                        throw new OverflowException("指定的参数 Length 太大，最高不能超过28位。");
                    }
                    value = new BigDecimal(Math.floor(value.divide(v, length, RoundingMode.HALF_UP).doubleValue()));
                    value = value.multiply(v);
                    return intCast(value);
                }
            } else {
                double value = p1.doubleValue();
                if (length <= 0) {
                    return intCast(value);
                } else {
                    double v1 = v.doubleValue();
                    if (Double.isInfinite(v1) && v1 > 0) {
                        throw new OverflowException("指定的参数 Length 为正无穷大。");
                    }
                    value = Math.floor(value / v1);
                    value = value * v1;
                    return intCast(value);
                }
            }
        }
    }

    private static Variant intCast(java.math.BigDecimal value) {
        try {
            return new Variant(new BigDecimal(value.toBigInteger()));
        } catch (OverflowException e) {
            return new Variant(value);
        }
    }

    private static Variant intCast(double value) {
        try {
            return new Variant(TypeUtils.toConvert(long.class, value));
        } catch (OverflowException e) {
            return new Variant(value);
        }
    }


    /**
     * 返回数字的正负号 为正时=1,为零时=0,为负时=-1
     */
    @FunctionRegister(name = "Sign", category = "数学与三角", caption = "返回数字的正负号 为正时=1,为零时=0,为负时=-1 。", minParamCount = 1)
    @ParamRegister(order = 1, name = "NUMBER", caption = "任意实数。", paramType = VariantType.NUMBER)
    public static class Sign extends AbstractFunctionBase implements Callable {
        @Override
        public Variant call(String name, FunctionParamContext paramContext, Context context) {
            Variant p1 = paramContext.getParam(1).getValue();
            double d = p1.doubleValue();
            if (d == 0) {
                return new Variant(0);
            } else if (d > 0) {
                return new Variant(1);
            } else {
                return new Variant(-1);
            }
        }
    }


    /**
     * 合计所有参数(至需要一个参数，但不限制参数上限)
     */
    @FunctionRegister(name = "Sum", category = "数学与三角", caption = "合计所有参数(至需要一个参数，但不限制参数上限) 。", isDynamicParam = true, dynamicParamType = VariantType.NULL, minParamCount = 1)
    public static class Sum extends AbstractFunctionBase implements Callable {
        @Override
        public Variant call(String name, FunctionParamContext paramContext, Context context) {
            ArrayList<Variant> items = Extension.readFunctionParams(paramContext, Variant::isNumber);
            Variant value = new Variant(0);
            for (Variant item : items) {
                value = Variant.add(value, item);
            }
            return value;
        }
    }


    /**
     * 合计符合条件所有参数
     */
    @FunctionRegister(name = "SumIf", category = "数学与三角", caption = "合计符合条件所有参数。", isDynamicParam = false, dynamicParamType = VariantType.NULL, minParamCount = 2)
    @ParamRegister(order = 1, name = "Range", caption = "进行行计算的值或数组。", paramType = (VariantType.NUMBER | VariantType.ARRAY))
    @ParamRegister(order = 2, name = "Criteria", caption = "定义的条件、数字或表达式。", paramType = (VariantType.STRING | VariantType.NUMBER))
    @ParamRegister(order = 3, name = "SumRange", caption = "需要进行汇总的值或数组。", paramType = VariantType.NUMBER | VariantType.ARRAY)
    public static class SumIf extends AbstractFunctionBase implements Callable {
        @Override
        public Variant call(String name, FunctionParamContext paramContext, Context context) {
            Variant range = paramContext.getParam(1).getValue();
            Variant criteria = paramContext.getParam(2).getValue();
            Function<Variant, Boolean> predicate = createCriteria(name, paramContext.getParam(2).getName(), true, criteria);
            if (paramContext.getParamSize() > 2) {
                Variant sumRange = paramContext.getParam(3).getValue();
                ArrayList<Variant> rangeItems = Extension.readFunParams(range, null);
                ArrayList<Variant> sumItems = Extension.readFunParams(sumRange, null);
                if (rangeItems.size() != sumItems.size()) {
                    throw new FunctionParamException(name, paramContext.getParam(3).getName(), "汇总的元素数量不与条件元素数量不相等。");
                }
                Variant value = new Variant(0);
                for (int i = 0; i < rangeItems.size(); i++) {
                    if (predicate.apply(rangeItems.get(i))) {
                        Variant sumItem = sumItems.get(i);
                        if (sumItem.isNumber()) {
                            value = Variant.add(value, sumItem);
                        }
                    }
                }
                return value;
            } else {
                ArrayList<Variant> items = Extension.readFunParams(range, (s) -> s.isNumber() && predicate.apply(s));
                Variant value = new Variant(0);
                for (Variant item : items) {
                    value = Variant.add(value, item);
                }
                return value;
            }
        }
    }


    /**
     * 返回所有参数的平方和(至需要一个参数，但不限制参数上限)
     */
    @FunctionRegister(name = "SumSq", category = "数学与三角", caption = "返回所有参数的平方和(至需要一个参数，但不限制参数上限)。", isDynamicParam = true, dynamicParamType = VariantType.NULL, minParamCount = 1)
    public static class SumSq extends AbstractFunctionBase implements Callable {
        @Override
        public Variant call(String name, FunctionParamContext paramContext, Context context) {
            ArrayList<Variant> items = Extension.readFunctionParams(paramContext, Variant::isNumber);
            Variant pow = new Variant(0);
            for (Variant item : items) {
                pow = Variant.add(Variant.power(item, new Variant(2.0)), pow);
            }
            return pow;
        }
    }


    /**
     * 返回某数的阶乘
     */
    @FunctionRegister(name = "Fact", category = "数学与三角", caption = "返回某数的阶乘 。", minParamCount = 1)
    @ParamRegister(order = 1, name = "NUMBER", caption = "要进行阶乘的正实数。", paramType = VariantType.INTEGER)
    public static class Fact extends AbstractFunctionBase implements Callable {
        @Override
        public Variant call(String name, FunctionParamContext paramContext, Context context) {
            Variant p1 = paramContext.getParam(1).getValue();
            if (TypeUtils.toConvert(int.class, p1.getValue()) < 1) {
                throw new RuntimeException("阶乘数不能小于1。");
            }
            double value = fact(TypeUtils.toConvert(long.class, p1.getValue()));
            if (value >= Long.MAX_VALUE) {
                return new Variant(value);
            } else {
                return new Variant((long) value);
            }
        }
    }

    private static double fact(long n) {
        if (n == 1) {
            return 1;
        } else {
            return n * fact(n - 1);
        }
    }


    /**
     * 返回某数的长阶乘字符窜
     */
    @FunctionRegister(name = "bigFact", category = "数学与三角", caption = "返回某数的阶乘字符窜 。", minParamCount = 1)
    @ParamRegister(order = 1, name = "NUMBER", caption = "要进行阶乘的正实数。", paramType = VariantType.INTEGER)
    public static class BigFact extends AbstractFunctionBase implements Callable {
        @Override
        public Variant call(String name, FunctionParamContext paramContext, Context context) {
            Variant p1 = paramContext.getParam(1).getValue();
            if (TypeUtils.toConvert(long.class, p1.getValue()) < 1) {
                throw new RuntimeException("阶乘数不能小于1。");
            }
            return new Variant(bigFact(TypeUtils.toConvert(long.class, p1.getValue())).toString());
        }
    }


    /**
     * @param n
     * @return
     */
    private static BigInteger bigFact(long n) {
        if (n == 1) {
            return new BigInteger("1");
        } else {
            return bigFact(n - 1).multiply(new BigDecimal(n).toBigInteger());
        }
    }


    private static void writeLong(Variant value, ArrayList<Long> items, boolean writeZero) {
        if (value.isArray()) {
            for (Variant arr : value.toArray()) {
                writeLong(arr, items, writeZero);
            }
        } else {
            if (value.isNumber()) {
                if (Variant.lessThan(value, new Variant(0))) {
                    throw new NotSupportException("指定的参数不支持数负数。");
                } else {
                    if (writeZero || Variant.greaterThan(value, new Variant(0))) {
                        if (value.getVariantType() == VariantType.INTEGER) {
                            items.add(TypeUtils.toConvert(long.class, value.getValue()));
                        } else {
                            items.add(new BigDecimal(TypeUtils.toConvert(double.class, value.getValue())).longValue());
                        }
                    }
                }
            }
        }
    }


    /**
     * 返回最大公约数
     */
    @FunctionRegister(name = "Gcd", category = "数学与三角", caption = "返回最大公约数(至需要2个参数，不限制参数上限，除非超可算最大值)。", isDynamicParam = true, dynamicParamType = VariantType.NUMBER | VariantType.NULL, minParamCount = 2)
    public static class GCD extends AbstractFunctionBase implements Callable {
        @Override
        public Variant call(String name, FunctionParamContext paramContext, Context context) {
            ArrayList<Long> items = new ArrayList<>();
            for (FunctionParam v : paramContext.getParams()) {
                Variant pv = v.getValue().clone();
                if (!pv.isNull()) {
                    writeLong(pv, items, false);
                }
            }
            if (items.isEmpty()) {
                return new Variant(0);
            }
            if (items.size() == 1) {
                return new Variant(items.get(0));
            }
            Collections.sort(items);
            return new Variant(gcd(items));
        }
    }


    private static long gcd(ArrayList<Long> items) {
        long value = gcd(items.get(1), items.get(0));
        if (value > 1) {
            for (int i = 2; i < items.size(); i++) {
                value = gcd(items.get(i), value);
                if (value == 1) {
                    break;
                }
            }
        }
        return value;
    }

    private static long gcd(long a, long b) {
        long p = a;
        long q = b;
        long value;
        while (true) {
            if (p % q == 0) {
                value = q;
                break;
            } else {
                long r = p % q;
                p = q;
                q = r;
            }
        }
        return value;
    }


    /**
     * 返回最小公倍数
     */
    @FunctionRegister(name = "lcm", category = "数学与三角", caption = "返回最小公倍数(至需要2个参数，不限制参数上限，除非超可算最大值)。", isDynamicParam = true, dynamicParamType = VariantType.NUMBER | VariantType.NULL, minParamCount = 2)
    public static class LCM extends AbstractFunctionBase implements Callable {
        @Override
        public Variant call(String name, FunctionParamContext paramContext, Context context) {
            ArrayList<Long> items = new ArrayList<>();
            for (FunctionParam v : paramContext.getParams()) {
                Variant pv = v.getValue().clone();
                if (!pv.isNull()) {
                    writeLong(pv, items, true);
                    if (items.size() > 0 && items.get(items.size() - 1) == 0L) {
                        return new Variant(0);
                    }
                }
            }
            if (items.isEmpty()) {
                return new Variant(0);
            }
            if (items.size() == 1) {
                return new Variant(items.get(0));
            }
            long value = lcm(items.get(0), items.get(1));
            for (int i = 2; i < items.size(); i++) {
                value = lcm(value, items.get(i));
            }
            return new Variant(value);
        }
    }

    private static long lcm(long a, long b) {
        long gcd = gcd(a, b);
        return a * b / gcd;
    }


    /**
     * 返回0到1之间的随机数
     */
    @FunctionRegister(name = "Rand", category = "数学与三角", caption = "返回0到1之间的随机数。", minParamCount = 0)
    public static class Rand extends AbstractFunctionBase implements Callable {
        @Override
        public Variant call(String name, FunctionParamContext paramContext, Context context) {
            return new Variant(new Random().nextDouble());
        }
    }


    /**
     * 返回介于Bottom与Top之间的随机数
     */
    @FunctionRegister(name = "RandBetween", category = "数学与三角", caption = "返回介于Bottom与Top之间的随机数。", minParamCount = 2)
    @ParamRegister(order = 1, name = "Bottom", caption = "最小整数。", paramType = VariantType.INTEGER)
    @ParamRegister(order = 2, name = "Top", caption = "最大整数。", paramType = VariantType.INTEGER)
    public static class RandBetween extends AbstractFunctionBase implements Callable {
        @Override
        public Variant call(String name, FunctionParamContext paramContext, Context context) {
            long min = paramContext.getParam(1).getValue().longValue();
            long max = paramContext.getParam(2).getValue().longValue();
            if (min > max) {
                throw new FunctionParamException(name, paramContext.getParam(1).getName(), "Bottom 大于 Top 。");
            }
            return new Variant((long) (Math.random() * (max - min + 1)) + min);
        }
    }


    private static String[][] Romans = new String[][]{{"", "I", "II", "III", "IV", "V", "VI", "VII", "VIII", "IX"}, {"", "X", "XX", "XXX", "XL", "L", "LX", "LXX", "LXXX", "XC"}, {"", "C", "CC", "CCC", "CD", "D", "DC", "DCC", "DCCC", "CM"}, {"", "M", "MM", "MMM", "", "", "", "", "", ""}};


    /**
     * 将正整数转换为罗马数字
     */
    @FunctionRegister(name = "Roman", category = "数学与三角", caption = "将正整数转换为罗马数字。", minParamCount = 1)
    @ParamRegister(order = 1, name = "number", caption = "介于(1-3999)的正整数。", paramType = VariantType.INTEGER)
    public static class Roman extends AbstractFunctionBase implements Callable {
        @Override
        public Variant call(String name, FunctionParamContext paramContext, Context context) {
            int num = paramContext.getParam(1).getValue().intValue();
            if (num < 1 || num > 3999) {
                throw new FunctionParamException(name, paramContext.getParam(1).getName(), "指定的参数小于 0 或 大于 3999。");
            }
            int i, j, n;
            StringBuilder lst = new StringBuilder();
            for (j = 0, i = 10000; j < 4; ++j, i /= 10) {
                n = (num % i) / (i / 10);
                String value = Romans[3 - j][n];
                if (!"".equals(value)) {
                    lst.append(value);
                }
            }
            return new Variant(lst.toString());
        }
    }


    /**
     * 返回所有参数的中位数(至需要一个参数，但不限制参数上限)
     */
    @FunctionRegister(name = "Median", category = "数学与三角", caption = "返回所有参数的中位数(至需要1个参数，但不限制参数上限) 。", isDynamicParam = true, dynamicParamType = VariantType.NUMBER | VariantType.ARRAY, minParamCount = 1)
    public static class Median extends AbstractFunctionBase implements Callable {
        @Override
        public Variant call(String name, FunctionParamContext paramContext, Context context) {
            ArrayList<Variant> items = Extension.readFunctionParams(paramContext, Variant::isNumber);
            if (items.size() == 1) {
                return items.get(0);
            }
            Collections.sort(items);
            if (items.size() % 2 != 0) {
                return items.get(items.size() / 2);
            } else {
                return Variant.divide(Variant.add(items.get(items.size() / 2 - 1), items.get(items.size() / 2)), new Variant(2));
            }
        }
    }

    private static Variant ceilingValue(Variant value) {
        if (value.getVariantType() == VariantType.INTEGER) {
            return new Variant(TypeUtils.toConvert(long.class, value.getValue()));
        } else if (value.getVariantType() == VariantType.DECIMAL) {
            BigDecimal v = new BigDecimal(value.getValue().toString());
            if (v.doubleValue() > 0) {
                return new Variant(new BigDecimal(v.toBigInteger().add(new BigInteger("1"))));
            } else if (v.doubleValue() < 0) {
                return new Variant(new BigDecimal(v.toBigInteger()));
            } else {
                return new Variant(v);
            }
        } else {
            return new Variant(Math.ceil(TypeUtils.toConvert(double.class, value.getValue())));
        }
    }


    /**
     * 将正数向上舍入最接近的偶数，负数向下舍入最接近的偶数
     */
    @FunctionRegister(name = "Even", category = "数学与三角", caption = "将正数向上舍入最接近的偶数，负数向下舍入最接近的偶数。", minParamCount = 1)
    @ParamRegister(order = 1, name = "number", caption = "舍入的任意数字。", paramType = VariantType.NUMBER)
    public static class Even extends AbstractFunctionBase implements Callable {
        @Override
        public Variant call(String name, FunctionParamContext paramContext, Context context) {
            Variant p = paramContext.getParam(1).getValue();
            if (Variant.isEquality(p, new Variant(0))) {
                return p;
            }
            p = ceilingValue(p);
            if (Variant.greaterThan(p, new Variant(0))) {
                if (Variant.isInequality(Variant.modulo(p, new Variant(2)), new Variant(0))) {
                    p = Variant.increment(p);
                }
            } else {
                if (Variant.isInequality(Variant.modulo(p, new Variant(2)), new Variant(0))) {
                    p = Variant.decrement(p);
                }
            }
            return p;
        }
    }


    /**
     * 将正数向上舍入最接近的奇数，负数向下舍入最接近的奇数
     */
    @FunctionRegister(name = "ODD", category = "数学与三角", caption = "将正数向上舍入最接近的奇数，负数向下舍入最接近的奇数。", minParamCount = 1)
    @ParamRegister(order = 1, name = "number", caption = "舍入的任意数字。", paramType = VariantType.NUMBER)
    public static class ODD extends AbstractFunctionBase implements Callable {
        @Override
        public Variant call(String name, FunctionParamContext paramContext, Context context) {
            Variant p = paramContext.getParam(1).getValue();
            if (Variant.isEquality(p, new Variant(0))) {
                return p;
            }
            p = ceilingValue(p);
            if (Variant.greaterThan(p, new Variant(0))) {
                if (Variant.isEquality(Variant.modulo(p, new Variant(2)), new Variant(0))) {
                    p = Variant.increment(p);
                }
            } else {
                if (Variant.isEquality(Variant.modulo(p, new Variant(2)), new Variant(0))) {
                    p = Variant.decrement(p);
                }
            }
            return p;
        }
    }


    /**
     * 返回所有参数的乘积(至需要一个参数，但不限制参数上限)
     */
    @FunctionRegister(name = "Product", category = "统计", caption = "返回所有参数的乘积(至需要1个参数，但不限制参数上限) 。", isDynamicParam = true, dynamicParamType = VariantType.NUMBER | VariantType.ARRAY, minParamCount = 1)
    public static class Product extends AbstractFunctionBase implements Callable {
        @Override
        public Variant call(String name, FunctionParamContext paramContext, Context context) {
            ArrayList<Variant> items = Extension.readFunctionParams(paramContext, Variant::isNumber);
            if (items.size() == 1) {
                return items.get(0);
            }
            Variant value = new Variant(1);
            for (Variant item : items) {
                value = Variant.multiply(value, item);
            }
            return value;
        }
    }
}