package com.autumn.evaluator;

//
// * 可变的数据类型,表示解析结果、传入参数、变量、数组、数组集合等，即任何与解析引擎相关的数据类型
// * 在运算时自动变换类型，如整型与实数运算时，自动转换为实数
//

import com.autumn.exception.ArgumentNullException;
import com.autumn.exception.ExceptionUtils;
import com.autumn.exception.NotSupportException;
import com.autumn.exception.OverflowException;
import com.autumn.util.DateUtils;
import com.autumn.util.Time;
import com.autumn.util.TypeUtils;
import com.autumn.util.reflect.BeanProperty;
import com.autumn.util.reflect.ReflectUtils;

import java.io.Serializable;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.math.BigDecimal;
import java.util.*;

import static java.lang.String.valueOf;

/**
 * 表示可变数据类型
 *
 * @author ycg
 */
public final class Variant implements Comparable<Variant>, Serializable {

    /**
     * 序列号
     */
    private static final long serialVersionUID = 5517290767856977754L;

    private Object value;
    private int type;
    private String name;

    private Object tag;

    /**
     * 获取 Tag
     *
     * @return
     */
    public Object getTag() {
        return tag;
    }

    /**
     * 设置 Tag
     *
     * @param tag
     */
    public void setTag(Object tag) {
        this.tag = tag;
    }

    /**
     * 基础类型
     */
    private static Map<Class<?>, Object> BASIC_TYPES = new HashMap<>();

    /**
     * 获取是否是基础类型
     *
     * @param type
     * @return
     */
    public static boolean isBasicType(Class<?> type) {
        return BASIC_TYPES.containsKey(type);
    }

    static {
        BASIC_TYPES.put(String.class, "");
        BASIC_TYPES.put(boolean.class, false);
        BASIC_TYPES.put(Boolean.class, null);
        BASIC_TYPES.put(byte.class, 0);
        BASIC_TYPES.put(Byte.class, null);
        BASIC_TYPES.put(short.class, 0);
        BASIC_TYPES.put(Short.class, null);
        BASIC_TYPES.put(int.class, 0);
        BASIC_TYPES.put(Integer.class, null);
        BASIC_TYPES.put(long.class, 0L);
        BASIC_TYPES.put(Long.class, null);
        BASIC_TYPES.put(Date.class, new Date());
        BASIC_TYPES.put(BigDecimal.class, 0);
        BASIC_TYPES.put(float.class, 0.0f);
        BASIC_TYPES.put(Float.class, null);
        BASIC_TYPES.put(double.class, 0.0d);
        BASIC_TYPES.put(Double.class, null);
        BASIC_TYPES.put(UUID.class, UUID.randomUUID());
    }

    /**
     * 获取 Variant 默认值, IsNull 属性为 true;
     */
    public static final Variant DEFAULT = new Variant();

    /**
     * 实例化 Variant 类新实例
     */
    public Variant() {
        this.name = null;
        this.value = null;
        this.type = VariantType.NULL;
    }

    /**
     * 实例化 Variant 类新实例
     *
     * @param value 值
     */
    public Variant(Object value) {
        this.name = null;
        if (value == null) {
            this.value = null;
            this.type = VariantType.NULL;
        } else {
            Class<?> vType = value.getClass();
            if (vType == Boolean.class || vType == boolean.class) {
                this.value = value;
                this.type = VariantType.BOOLEAN;
            } else if (vType == Byte.class || vType == byte.class) {
                this.value = value;
                this.type = VariantType.INTEGER;
            } else if (vType == Short.class || vType == short.class) {
                this.value = value;
                this.type = VariantType.INTEGER;
            } else if (vType == Integer.class || vType == int.class) {
                this.value = value;
                this.type = VariantType.INTEGER;
            } else if (vType == Long.class || vType == long.class) {
                long v = (long) value;
                if (v >= Long.MAX_VALUE) {
                    this.value = value;
                    this.type = VariantType.DOUBLE;
                } else {
                    this.value = v;
                    this.type = VariantType.INTEGER;
                }
            } else if (vType == Date.class) {
                this.value = value;
                this.type = VariantType.DATETIME;
            } else if (vType == BigDecimal.class) {
                this.value = value;
                this.type = VariantType.DECIMAL;
            } else if (vType == Float.class || vType == float.class) {
                this.value = value;
                this.type = VariantType.DOUBLE;
            } else if (vType == Double.class || vType == double.class) {
                this.value = value;
                this.type = VariantType.DOUBLE;
            } else if (vType == UUID.class) {
                this.value = value;
                this.type = VariantType.UUID;
            } else if (vType == String.class) {
                this.value = valueOf(value);
                this.type = VariantType.STRING;
            } else if (vType == Variant.class) {
                Variant v = (Variant) value;
                this.name = v.getName();
                this.value = v.getValue();
                this.type = v.getVariantType();
            } else {
                Variant[] vars = (Variant[]) ((value instanceof Variant[]) ? value : null);
                if (vars != null) {
                    this.value = vars;
                    this.type = VariantType.ARRAY;
                } else {
                    this.value = null;
                    this.type = VariantType.NULL;
                }
            }
        }
    }

    /**
     * 实例化 Variant 类新实例
     *
     * @param name  名称
     * @param value 值
     */
    public Variant(String name, Variant[] value) {
        this.name = name;
        if (value != null) {
            this.value = value;
            this.type = VariantType.ARRAY;
        } else {
            this.value = null;
            this.type = VariantType.NULL;
        }
    }

    /**
     * 获取或设置名称(一般主要应用于数组标识或数据库列名)
     */
    public String getName() {
        return this.name;
    }

    public void setName(String value) {
        this.name = value;
    }

    /**
     * 获取值
     */
    public Object getValue() {
        return this.value;
    }

    /**
     * 获取类型
     *
     * @return
     */
    public int getVariantType() {
        return this.type;
    }

    /**
     * 获取是否是空值
     *
     * @return
     */
    public boolean isNull() {
        return this.getVariantType() == VariantType.NULL;
    }

    /**
     * 获取是否是数值
     *
     * @return
     */
    public boolean isNumber() {
        return this.getVariantType() == VariantType.INTEGER || this.getVariantType() == VariantType.DECIMAL
                || this.getVariantType() == VariantType.DOUBLE;
    }

    /**
     * 获取是否是字符窜
     *
     * @return
     */
    public boolean isString() {
        return this.getVariantType() == VariantType.STRING;
    }

    /**
     * 获取是否是日期时间
     */
    public boolean isDateTime() {
        return this.getVariantType() == VariantType.DATETIME;
    }

    /**
     * 获取是否是布尔值
     *
     * @return
     */
    public boolean isBoolean() {
        return this.getVariantType() == VariantType.BOOLEAN;
    }

    /**
     * 获取是否是UUID
     *
     * @return
     */
    public boolean isUUID() {
        return this.getVariantType() == VariantType.UUID;
    }

    /**
     * 获取是否是数组
     *
     * @return
     */
    public boolean isArray() {
        return this.getVariantType() == VariantType.ARRAY;
    }

    /**
     * 获取数字类型
     *
     * @param left  左值
     * @param right 右值
     * @return
     */
    public static int getNumberType(Variant left, Variant right) {
        if (left.getVariantType() == VariantType.INTEGER && right.getVariantType() == VariantType.INTEGER) {
            return VariantType.INTEGER;
        } else if (left.getVariantType() == VariantType.DOUBLE || right.getVariantType() == VariantType.DOUBLE) {
            int leftType;
            int rightType;
            if (left.getVariantType() == VariantType.DOUBLE) {
                leftType = doubleConvert((Double) left.getValue());
            } else {
                leftType = left.getVariantType();
            }
            if (right.getVariantType() == VariantType.DOUBLE) {
                rightType = doubleConvert((Double) right.getValue());
            } else {
                rightType = right.getVariantType();
            }
            if (leftType == VariantType.DECIMAL && rightType == VariantType.DECIMAL) {
                return VariantType.DECIMAL;
            } else if (leftType == VariantType.INTEGER && rightType == VariantType.INTEGER) {
                return VariantType.INTEGER;
            } else if ((leftType == VariantType.DECIMAL && rightType == VariantType.INTEGER)
                    || (leftType == VariantType.INTEGER && rightType == VariantType.DECIMAL)) {
                return VariantType.DECIMAL;
            } else {
                return VariantType.DOUBLE;
            }
        } else {
            return VariantType.DECIMAL;
        }
    }

    /**
     * 获取数字类型
     *
     * @param value 值
     * @return
     */
    public static int getNumberType(String value) {
        if (value.contains("E") || value.contains("e")) {
            return VariantType.DOUBLE;
        }
        String[] values = value.split("\\.");
        if (values.length > 1) {
            try {
                new BigDecimal(values[0]);
                return VariantType.DECIMAL;
            } catch (OverflowException e) {
                return VariantType.DOUBLE;
            }
        } else {
            try {
                Long.parseLong(values[0]);
                return VariantType.INTEGER;
            } catch (OverflowException e2) {
                try {
                    new BigDecimal(values[0]);
                    return VariantType.DECIMAL;
                } catch (OverflowException e3) {
                    return VariantType.DOUBLE;
                }
            }
        }
    }

    /**
     * DOUBLE 类型转换
     *
     * @param value 值
     * @return
     */
    private static int doubleConvert(double value) {
        if (Double.isNaN(value) || Double.isInfinite(value)) {
            return VariantType.DOUBLE;
        }
        return getNumberType(Double.toString(value));
    }

    /**
     * 运算
     *
     * @param left      左值
     * @param right     右值
     * @param delimiter 运算符
     * @return
     * @throws NotSupportException 不支持的操作
     */
    private static Variant calc(double left, double right, String delimiter) {
        Variant value;
        if ("+".equals(delimiter)) {
            value = new Variant(left + right);
        } else if ("-".equals(delimiter)) {
            value = new Variant(left - right);
        } else if ("^".equals(delimiter)) {
            value = new Variant(Math.pow(left, right));
        } else if ("*".equals(delimiter)) {
            value = new Variant(left * right);
        } else {
            throw new NotSupportException();
        }
        return value;
    }

    /**
     * 运算
     *
     * @param left      左值
     * @param right     右值
     * @param delimiter 运算符
     * @return
     * @throws NotSupportException 不支持的操作
     */
    private static Variant calc(BigDecimal left, BigDecimal right, String delimiter) {
        Variant value;
        try {
            if ("+".equals(delimiter)) {
                value = new Variant(left.add(right));
            } else if ("-".equals(delimiter)) {
                value = new Variant(left.subtract(right));
            } else if ("^".equals(delimiter)) {
                value = new Variant(new BigDecimal(Math.pow(left.doubleValue(), right.doubleValue())));
            } else if ("*".equals(delimiter)) {
                value = new Variant(left.multiply(right));
            } else {
                throw new NotSupportException();
            }
        } catch (OverflowException e) {
            value = calc(left.doubleValue(), right.doubleValue(), delimiter);
        }
        return value;
    }

    /**
     * 运算
     *
     * @param left      左值
     * @param right     右值
     * @param delimiter 运算符
     * @return
     * @throws NotSupportException 不支持的操作
     */
    private static Variant calc(long left, long right, String delimiter) {
        Variant value;
        try {
            if ("+".equals(delimiter)) {
                value = new Variant(left + right);
            } else if ("-".equals(delimiter)) {
                value = new Variant(left - right);
            } else if ("^".equals(delimiter)) {
                value = new Variant(Math.round(Math.pow(left, right)));
            } else if ("*".equals(delimiter)) {
                value = new Variant(left * right);
            } else {
                throw new NotSupportException();
            }
        } catch (OverflowException e) {
            value = calc(new BigDecimal(left), new BigDecimal(right), delimiter);
        }
        return value;
    }

    /**
     * 重载+运算符
     *
     * @param left  左值
     * @param right 右值
     * @return
     */
    public static Variant add(Variant left, Variant right) {
        if (left.isNumber() && right.isNumber()) {
            int vt = getNumberType(left, right);
            if (vt == VariantType.INTEGER) {
                return calc(TypeUtils.toConvert(long.class, left.getValue()),
                        TypeUtils.toConvert(long.class, right.getValue()), "+");
            } else if (vt == VariantType.DECIMAL) {
                return calc(new BigDecimal(left.getValue().toString()), new BigDecimal(right.getValue().toString()),
                        "+");
            } else {
                return calc(TypeUtils.toConvert(double.class, left.getValue()),
                        TypeUtils.toConvert(double.class, right.getValue()), "+");
            }
        } else {
            if (left.isDateTime() || right.isDateTime()) {
                // int dayms = 24 * 60 * 60 * 1000;
                if (left.isDateTime() && right.isNumber()) {
                    return new Variant(new Date(((Date) left.getValue()).getTime() + ((long) right.getValue())));
                } else if (left.isNumber() && right.isDateTime()) {
                    return new Variant(new Date((long) left.getValue() + ((Date) right.getValue()).getTime()));
                } else if (left.isDateTime() && right.isDateTime()) {
                    return new Variant(
                            new Date(((Date) left.getValue()).getTime() + ((Date) right.getValue()).getTime()));
                } else {
                    return new Variant(left + valueOf(right));
                }
            } else {
                return new Variant(left + valueOf(right));
            }
        }
    }

    /**
     * 重载++运算符
     *
     * @param value 值
     * @return
     * @throws NotSupportException 不支持的操作
     */
    public static Variant increment(Variant value) {
        if (value.isNumber()) {
            if (value.getVariantType() == VariantType.INTEGER) {
                value = calc(TypeUtils.toConvert(long.class, value.getValue()), 1, "+");
            } else if (value.getVariantType() == VariantType.DECIMAL) {
                value = calc(new BigDecimal(value.getValue().toString()), new BigDecimal(1), "+");
            } else {
                value = calc(TypeUtils.toConvert(double.class, value.getValue()), 1.0, "+");
            }
        } else {
            throw new NotSupportException("非数值或日期时间类型不能进行减法运算。");
        }
        return value;
    }

    /**
     * 重载 字符窜连接 运算符,VB连接表达式
     *
     * @param left  左值
     * @param right 右值
     * @return
     * @throws NotSupportException 不支持的操作
     */
    public static Variant join(Variant left, Variant right) {
        if ((left.isString() || left.isUUID() || left.isDateTime())
                && (right.isString() || right.isUUID() || right.isDateTime())) {
            String v1 = dateString(left);
            String v2 = dateString(right);
            return new Variant(v1 + v2);
        }
        throw new NotSupportException("连接操作符只能用于字符串或日期时间连接。");
    }

    private static String dateString(Variant value) {
        if (value.isDateTime()) {
            Date d = (Date) value.getValue();
            Time t = DateUtils.getTime(d);
            if (t.getHour() == 0 && t.getMinute() == 0 && t.getSecond() == 0) {
                return DateUtils.dateFormat(d, "yyyy-MM-dd");
            } else {
                return DateUtils.dateFormat(d, "yyyy-MM-dd HH:mm:ss");
            }
        } else {
            return value.toString();
        }
    }


    /**
     * 重载-运算符
     *
     * @param left  左值
     * @param right 右值
     * @return
     * @throws NotSupportException/
     */
    public static Variant subtract(Variant left, Variant right) {
        if (left.isNumber() && right.isNumber()) {
            int vt = getNumberType(left, right);
            if (vt == VariantType.INTEGER) {
                return calc(TypeUtils.toConvert(long.class, left.getValue()),
                        TypeUtils.toConvert(long.class, right.getValue()), "-");
            } else if (vt == VariantType.DECIMAL) {
                return calc(new BigDecimal(left.getValue().toString()), new BigDecimal(right.getValue().toString()),
                        "-");
            } else {
                return calc(TypeUtils.toConvert(double.class, left.getValue()),
                        TypeUtils.toConvert(double.class, right.getValue()), "-");
            }
        } else {
            if (left.isDateTime() || right.isDateTime()) {
                if (left.isDateTime() && right.isNumber()) {
                    return new Variant(new Date(
                            ((Date) left.getValue()).getTime() - (TypeUtils.toConvert(long.class, right.getValue()))));
                } else if (left.isNumber() && right.isDateTime()) {
                    return new Variant(new Date(
                            (TypeUtils.toConvert(long.class, left.getValue())) - ((Date) right.getValue()).getTime()));
                } else if (left.isDateTime() && right.isDateTime()) {
                    return new Variant(
                            new Date(((Date) left.getValue()).getTime() - ((Date) right.getValue()).getTime()));
                } else {
                    throw new NotSupportException("非数值或日期时间类型不能进行减法运算。");
                }
            } else {
                throw new NotSupportException("非数值或日期时间类型不能进行减法运算。");
            }
        }
    }

    /**
     * 重载--运算符
     *
     * @param value 值
     * @return
     * @throws NotSupportException 不支持的操作
     */
    public static Variant decrement(Variant value) {
        if (value.isNumber()) {
            if (value.getVariantType() == VariantType.INTEGER) {
                value = calc(TypeUtils.toConvert(long.class, value.getValue()), 1, "-");
            } else if (value.getVariantType() == VariantType.DECIMAL) {
                value = calc(new BigDecimal(value.getValue().toString()), new BigDecimal(1), "-");
            } else {
                value = calc(TypeUtils.toConvert(double.class, value.getValue()), 1.0, "-");
            }
        } else {
            throw new NotSupportException("非数值或日期时间类型不能进行减法运算。");
        }
        return value;
    }

    /**
     * 重载-运算符
     *
     * @param value 值
     * @return
     * @throws NotSupportException 不支持的操作
     */
    public static Variant unaryNegation(Variant value) {
        if (value.isNumber()) {
            if (value.getVariantType() == VariantType.INTEGER) {
                value.value = ((long) value.getValue()) * -1;
            } else if (value.getVariantType() == VariantType.DECIMAL) {
                value.value = ((BigDecimal) value.getValue()).multiply(new BigDecimal(-1));
            } else {
                value.value = ((double) value.getValue()) * -1.0;
            }
        } else {
            throw new NotSupportException("非数值或日期时间类型不能进行减法运算。");
        }
        return value;
    }

    /**
     * 重载^运算符(乘方)
     *
     * @param left  左值
     * @param right 右值
     * @return
     * @throws NotSupportException 不支持的操作
     */
    public static Variant power(Variant left, Variant right) {
        if (left.isNumber() && right.isNumber()) {
            int vt = getNumberType(left, right);
            if (vt == VariantType.INTEGER) {
                return calc(TypeUtils.toConvert(long.class, left.getValue()),
                        TypeUtils.toConvert(long.class, right.getValue()), "^");
            } else if (vt == VariantType.DECIMAL) {
                return calc(new BigDecimal(left.getValue().toString()), new BigDecimal(right.getValue().toString()),
                        "^");
            } else {
                return calc(TypeUtils.toConvert(double.class, left.getValue()),
                        TypeUtils.toConvert(double.class, right.getValue()), "^");
            }
        } else {
            throw new NotSupportException("非数值不能进行乘方运算。");
        }
    }

    /**
     * 重载%运算符(取模)
     *
     * @param left  左值
     * @param right 右值
     * @return
     * @throws NotSupportException 不支持的操作
     */
    public static Variant modulo(Variant left, Variant right) {
        if (left.isNumber() && right.isNumber()) {
            int vt = getNumberType(left, right);
            if (vt == VariantType.INTEGER) {
                return new Variant(TypeUtils.toConvert(long.class, left.getValue())
                        % TypeUtils.toConvert(long.class, right.getValue()));
            } else if (vt == VariantType.DECIMAL) {
                BigDecimal[] divideAndRemainder = (new BigDecimal(left.getValue().toString()))
                        .divideAndRemainder(new BigDecimal(right.getValue().toString()));
                return new Variant(divideAndRemainder[1]);
            } else {
                return new Variant(TypeUtils.toConvert(double.class, left.getValue())
                        % TypeUtils.toConvert(double.class, right.getValue()));
            }
        } else {
            throw new NotSupportException("非数值不能进行取模运算。");
        }
    }

    /**
     * 重载*运算符
     *
     * @param left  左值
     * @param right 右值
     * @return
     * @throws NotSupportException 不支持的操作
     */
    public static Variant multiply(Variant left, Variant right) {
        if (left.isNumber() && right.isNumber()) {
            int vt = getNumberType(left, right);
            if (vt == VariantType.INTEGER) {
                return calc(TypeUtils.toConvert(long.class, left.getValue()),
                        TypeUtils.toConvert(long.class, right.getValue()), "*");
            } else if (vt == VariantType.DECIMAL) {
                return calc(new BigDecimal(left.getValue().toString()), new BigDecimal(right.getValue().toString()),
                        "*");
            } else {
                return calc(TypeUtils.toConvert(double.class, left.getValue()),
                        TypeUtils.toConvert(double.class, right.getValue()), "*");
            }
        } else {
            if (left.isDateTime() || right.isDateTime()) {
                if (left.isDateTime() && right.isNumber()) {
                    return new Variant(new Date(
                            ((Date) left.getValue()).getTime() * (TypeUtils.toConvert(long.class, right.getValue()))));
                } else if (left.isNumber() && right.isDateTime()) {
                    return new Variant(new Date(
                            (TypeUtils.toConvert(long.class, left.getValue())) * ((Date) right.getValue()).getTime()));
                } else if (left.isDateTime() && right.isDateTime()) {
                    return new Variant(
                            new Date(((Date) left.getValue()).getTime() * ((Date) right.getValue()).getTime()));
                } else {
                    throw new NotSupportException("非数值或日期时间类型不能进行乘法运算。");
                }
            } else {
                throw new NotSupportException("非数值或日期时间类型不能进行乘法运算。");
            }
        }
    }

    /**
     * 重载/运算符
     *
     * @param left  左值
     * @param right 右值
     * @return
     * @throws NotSupportException 不支持的操作
     */
    public static Variant divide(Variant left, Variant right) {
        if (left.isNumber() && right.isNumber()) {
            int vt = getNumberType(left, right);
            if (vt == VariantType.DOUBLE) {
                return new Variant(TypeUtils.toConvert(double.class, left.getValue())
                        / TypeUtils.toConvert(double.class, right.getValue()));
            } else {
                return new Variant((new BigDecimal(left.getValue().toString()))
                        .divide(new BigDecimal(right.getValue().toString()), 30, BigDecimal.ROUND_HALF_UP));
            }
        } else {
            if (left.isDateTime() || right.isDateTime()) {
                if (left.isDateTime() && right.isNumber()) {
                    return new Variant(new Date(
                            ((Date) left.getValue()).getTime() / (TypeUtils.toConvert(long.class, right.getValue()))));
                } else if (left.isNumber() && right.isDateTime()) {
                    return new Variant(new Date(
                            (TypeUtils.toConvert(long.class, left.getValue())) / ((Date) right.getValue()).getTime()));
                } else if (left.isDateTime() && right.isDateTime()) {
                    return new Variant(
                            new Date(((Date) left.getValue()).getTime() / ((Date) right.getValue()).getTime()));
                } else {
                    throw new NotSupportException("非数值或日期时间类型不能进行除法运算。");
                }
            } else {
                throw new NotSupportException("非数值或日期时间类型不能进行除法运算。");
            }
        }
    }

    /**
     * 重载==运算符
     *
     * @param left  左值
     * @param right 右值
     * @return
     * @throws NotSupportException 不支持的操作
     */
    public static boolean isEquality(Variant left, Variant right) {
        return left.compareTo(right) == 0;
    }

    /**
     * 重载!=运算符
     *
     * @param left  左值
     * @param right 右值
     * @return
     * @throws NotSupportException 不支持的操作
     */
    public static boolean isInequality(Variant left, Variant right) {
        return left.compareTo(right) != 0;
    }

    /**
     * 重载>运算符
     *
     * @param left  左值
     * @param right 右值
     * @return
     * @throws NotSupportException 不支持的操作
     */
    public static boolean greaterThan(Variant left, Variant right) {
        return left.compareTo(right) > 0;
    }

    /**
     * 重载 小于 运算符
     *
     * @param left  左值
     * @param right 右值
     * @return
     * @throws NotSupportException 不支持的操作
     */
    public static boolean lessThan(Variant left, Variant right) {
        return left.compareTo(right) < 0;
    }

    /**
     * 重载 >= 运算符
     *
     * @param left  左值
     * @param right 右值
     * @return
     * @throws NotSupportException 不支持的操作
     */
    public static boolean opGreaterThanOrEqual(Variant left, Variant right) {
        return left.compareTo(right) >= 0;
    }

    /**
     * 重载 小于或等于 运算符
     *
     * @param left  左值
     * @param right 右值
     * @return
     * @throws NotSupportException 不支持的操作
     */
    public static boolean lessThanOrEqual(Variant left, Variant right) {
        return left.compareTo(right) <= 0;
    }

    /**
     * 返回当前实例的 System.STRING
     *
     * @return
     */
    @Override
    public String toString() {
        return this.toString(true);
    }

    /**
     * 返回当前实例的 System.STRING
     *
     * @param removeZero 移除零
     * @return
     */
    public String toString(boolean removeZero) {
        if (this.getValue() instanceof String) {
            return this.value.toString();
        }
        if (this.isDateTime()) {
            Date d = (Date) this.value;
            Time t = DateUtils.getTime(d);
            // G 此处要换有日志
            if (t.getHour() == 0 && t.getMinute() == 0 && t.getSecond() == 0) {
                return DateUtils.dateFormat(d, "yyyy-MM-dd");
            } else {
                return DateUtils.dateFormat(d, "yyyy-MM-dd HH:mm:ss");
            }
        } else if (this.isNumber() && removeZero && this.getVariantType() == VariantType.DECIMAL) {
            String[] values = this.value.toString().split("\\.");
            StringBuilder v = new StringBuilder();
            v.append(values[0]);
            if (values.length > 1) {
                StringBuilder r = new StringBuilder();
                r.append(values[1]);
                while (r.length() > 0 && r.charAt(r.length() - 1) == '0') {
                    r.deleteCharAt(r.length() - 1);
                }
                if (r.length() > 0) {
                    v.append(".").append(r.toString());
                }
            }
            return v.toString();
        } else if (this.isNull()) {
            return "NULL";
        } else if (this.isArray()) {
            if (com.autumn.util.StringUtils.isNullOrBlank(this.getName())) {
                return "ARRAY[]";
            } else {
                return this.getName() + "(ARRAY[])";
            }
        }
        return this.value.toString();
    }

    /**
     * 显示转换 为 UUID,等同于 (UUID)value
     *
     * @return
     */
    public UUID toUUID() {
        if (this.getValue() instanceof UUID) {
            return (UUID) this.getValue();
        }
        if (this.getValue() == null) {
            return null;
        }
        return UUID.fromString(this.getValue().toString());
    }

    private <T> T objectValue(T value, Class<T> valueClass) {
        if (value == null) {
            throw ExceptionUtils.throwInvalidCastException("null 无法转为 " + valueClass.getSimpleName() + "类型。");
        }
        return value;
    }

    /**
     * 转换为 Boolean 类型
     *
     * @return
     */
    public Boolean toBoolean() {
        if (this.getValue() instanceof Boolean) {
            return (Boolean) this.getValue();
        }
        if (this.getValue() == null) {
            return null;
        }
        return TypeUtils.toConvert(Boolean.class, this.getValue());
    }

    /**
     * 转换为 Boolean 类型
     *
     * @return
     */
    public boolean booleanValue() {
        return this.objectValue(toBoolean(), Boolean.class);
    }

    /**
     * 转换为 byte 类型
     *
     * @return
     */
    public Byte toByte() {
        if (this.getValue() instanceof Byte) {
            return (Byte) this.getValue();
        }
        if (this.getValue() == null) {
            return null;
        }
        return TypeUtils.toConvert(Byte.class, this.getValue());
    }

    /**
     * 转换为 byte 类型
     *
     * @return
     */
    public byte byteValue() {
        return this.objectValue(toByte(), Byte.class);
    }

    /**
     * 转换为 short 类型
     *
     * @return
     */
    public Short toShort() {
        if (this.getValue() instanceof Short) {
            return (Short) this.getValue();
        }
        if (this.getValue() == null) {
            return null;
        }
        return TypeUtils.toConvert(Short.class, this.getValue());
    }

    /**
     * 转换为 short 类型
     *
     * @return
     */
    public short shortValue() {
        return this.objectValue(toShort(), Short.class);
    }

    /**
     * 转换为 Integer 类型
     *
     * @return
     */
    public Integer toInteger() {
        if (this.getValue() instanceof Integer) {
            return (Integer) this.getValue();
        }
        if (this.getValue() == null) {
            return null;
        }
        return TypeUtils.toConvert(Integer.class, this.getValue());
    }

    /**
     * 转换为 int 类型
     *
     * @return
     */
    public int intValue() {
        return this.objectValue(toInteger(), Integer.class);
    }

    /**
     * 转换为 Long 类型
     *
     * @return
     */
    public Long toLong() {
        if (this.getValue() instanceof Long) {
            return (Long) this.getValue();
        }
        if (this.getValue() == null) {
            return null;
        }
        return TypeUtils.toConvert(Long.class, this.getValue());
    }

    /**
     * 转换为 long 类型
     *
     * @return
     */
    public long longValue() {
        return this.objectValue(toLong(), Long.class);
    }

    /**
     * 转换为 float 类型
     *
     * @return
     */
    public Float toFloat() {
        if (this.getValue() instanceof Float) {
            return (Float) this.getValue();
        }
        if (this.getValue() == null) {
            return null;
        }
        return TypeUtils.toConvert(Float.class, this.getValue());
    }

    /**
     * 转换为 float 类型
     *
     * @return
     */
    public float floatValue() {
        return this.objectValue(toFloat(), Float.class);
    }

    /**
     * 转换为 double 类型
     *
     * @return
     */
    public Double toDouble() {
        if (this.getValue() instanceof Double) {
            return (Double) this.getValue();
        }
        if (this.getValue() == null) {
            return null;
        }
        return TypeUtils.toConvert(Double.class, this.getValue());
    }

    /**
     * 转换为 double 类型
     *
     * @return
     */
    public double doubleValue() {
        return this.objectValue(toDouble(), Double.class);
    }

    /**
     * 转换为 Date 类型
     *
     * @return
     */
    public Date toDate() {
        if (this.getValue() instanceof Date) {
            return (Date) this.getValue();
        }
        if (this.getValue() == null) {
            return null;
        }
        return TypeUtils.toConvert(Date.class, this.getValue());
    }

    /**
     * 转换为 Date 类型
     *
     * @return
     */
    public BigDecimal toBigDecimal() {
        if (this.getValue() instanceof BigDecimal) {
            return (BigDecimal) this.getValue();
        }
        if (this.getValue() == null) {
            return null;
        }
        return TypeUtils.toConvert(BigDecimal.class, this.getValue());
    }

    /**
     * 显示转换为 Variant 数组,等同于 (Variant[])value
     *
     * @return
     */
    public Variant[] toArray() {
        if (this.getValue() instanceof Variant[]) {
            return (Variant[]) this.getValue();
        }
        if (this.getValue() == null) {
            return null;
        }
        throw ExceptionUtils.throwInvalidCastException(this.getValue() + " 无法转为 Variant[] 。");
    }

    /**
     * 返回循环访问 Variant 的枚举数。
     *
     * @return
     */
    @SuppressWarnings("unchecked")
    public Iterator<Variant> getEnumerator() {
        if (this.isArray()) {
            Iterable<Variant> vars = (Iterable<Variant>) ((this.value instanceof Iterable) ? this.value : null);
            return vars.iterator();
        } else {
            throw new NotSupportException("非数组不支持迭代。");
        }
    }



    /**
     * 返回一个值，该值指示此实例是否与指定的 Variant 值相等。
     *
     * @param value 要比较的对象，或为 null。
     * @return
     */
    @Override
    public boolean equals(Object value) {
        if (value == null) {
            return false;
        }
        if (value.getClass().equals(Variant.class)) {
            Variant v = (Variant) value;
            return this.equals(v);
        } else {
            return false;
        }
    }

    /**
     * 返回一个值，该值指示此实例是否与指定的 Variant 值相等。
     *
     * @param value 要与此实例进行比较的 Variant 值。
     * @return
     */
    public boolean equals(Variant value) {
        if (value.isNull() && this.isNull()) {
            return true;
        } else {
            if (value.value != null && this.value != null) {
                try {
                    boolean result = this.compareTo(value) == 0;
                    if (result) {
                        if (this.getTag() != null && value.getTag() != null) {
                            return this.getTag().equals(value.getTag());
                        } else {
                            return this.getTag() == null && value.getTag() == null;
                        }
                    }
                    return false;
                } catch (Exception e) {
                    return false;
                }
            } else {
                return false;
            }
        }
    }

    @Override
    public int hashCode() {
        int code = Integer.hashCode(this.getVariantType());
        if (this.isNull()) {
            if (this.getTag() == null) {
                return code;
            }
            return code ^ this.getTag().hashCode();
        }
        code = code ^ this.getValue().hashCode();
        if (this.getTag() == null) {
            return code;
        }
        return code ^ this.getTag().hashCode();
    }

    /**
     * 将此实例与指定的 Variant 位有符号整数进行比较并返回对其相对值的指示。
     *
     * @param value 要比较的 Variant 。
     * @return 一个有符号数字，指示此实例和 value 的相对值。返回值说明小于零此实例小于 value。零此实例等于 value。大于零此实例大于
     * value。
     * @throws RuntimeException 不支持的比较引发的异常。
     */
    @Override
    public int compareTo(Variant value) {
        int to;
        if (this.isNull() && value.isNull()) {
            to = 0;
        } else if (this.isNull() && !value.isNull()) {
            to = -1;
        } else if (!this.isNull() && value.isNull()) {
            to = 1;
        } else if (this.isArray() && !value.isArray()) {
            to = 1;
        } else if (!this.isArray() && value.isArray()) {
            to = -1;
        } else if (this.isArray() && value.isArray()) {
            to = arrayCompareTo(this, value);
        } else if (this.isDateTime() && value.isDateTime()) {
            to = ((Date) this.getValue()).compareTo((Date) value.getValue());
        } else if (this.isString() && value.isString()) {
            to = this.toString().toLowerCase().compareTo(value.toString().toLowerCase());
        } else if (this.isUUID() && value.isUUID()) {
            to = ((UUID) this.getValue()).compareTo((UUID) value.getValue());
        } else if (this.isBoolean() && value.isBoolean()) {
            to = (TypeUtils.toConvert(boolean.class, this.getValue()))
                    .compareTo(TypeUtils.toConvert(boolean.class, value.getValue()));
        } else if (this.isNumber() && value.isNumber()) // 数字
        {
            if (this.getVariantType() == VariantType.INTEGER && value.getVariantType() == VariantType.INTEGER) {
                to = (TypeUtils.toConvert(int.class, this.getValue()))
                        .compareTo((TypeUtils.toConvert(int.class, value.getValue())));
            } else if (this.getVariantType() == VariantType.DECIMAL && value.getVariantType() == VariantType.DECIMAL) {
                to = (new BigDecimal(this.getValue().toString()))
                        .compareTo((new BigDecimal(value.getValue().toString())));
            } else if (this.getVariantType() == VariantType.DOUBLE && value.getVariantType() == VariantType.DOUBLE) {
                to = (TypeUtils.toConvert(double.class, this.getValue()))
                        .compareTo((TypeUtils.toConvert(double.class, value.getValue())));
            } else {
                int vt = Variant.getNumberType(this, value);
                if (vt == VariantType.INTEGER) {
                    to = (TypeUtils.toConvert(long.class, this.getValue()))
                            .compareTo((TypeUtils.toConvert(long.class, value.getValue())));
                } else if (vt == VariantType.DOUBLE) {
                    to = (TypeUtils.toConvert(double.class, this.getValue()))
                            .compareTo((TypeUtils.toConvert(double.class, value.getValue())));
                } else {
                    to = (new BigDecimal(this.getValue().toString()))
                            .compareTo(new BigDecimal(value.getValue().toString()));
                }
            }
        } else if ((this.isDateTime() && value.isString()) || (this.isString() && value.isDateTime())) {
            to = this.toString().toLowerCase().compareTo(value.toString().toLowerCase());
        } else if ((this.isNumber() && value.isDateTime()) || (this.isDateTime() && value.isNumber())
                || (this.isBoolean() && value.isDateTime()) || (this.isDateTime() && value.isBoolean())) {
            if ((this.isNumber() && value.isDateTime()) || (this.isBoolean() && value.isDateTime())) {
                if (TypeUtils.toConvert(long.class, this.getValue()) == ((Date) value.getValue()).getTime()) {
                    to = 0;
                } else if (TypeUtils.toConvert(long.class, this.getValue()) > ((Date) value.getValue()).getTime()) {
                    to = 1;
                } else {
                    to = -1;
                }
            } else {
                if (((Date) this.getValue()).getTime() == TypeUtils.toConvert(long.class, value.getValue())) {
                    to = 0;
                } else if (((Date) this.getValue()).getTime() > TypeUtils.toConvert(long.class, value.getValue())) {
                    to = 1;
                } else {
                    to = -1;
                }
            }
        } else if ((this.isNumber() && value.isBoolean()) || (this.isBoolean() && value.isNumber())) {
            to = Integer.compare((TypeUtils.toConvert(double.class, this.getValue())).compareTo(TypeUtils.toConvert(double.class, value.getValue())), 0);
        } else {
            if (!this.isArray() && !this.isNull() && !value.isNull() && !value.isArray()) {
                to = this.toString().toLowerCase().compareTo(value.toString().toLowerCase());
            } else {
                throw new NotSupportException("不支持的比较。");
            }
        }
        return to;
    }

    /**
     * 数组比较
     *
     * @param left  左值
     * @param right 右值
     * @return
     */
    private int arrayCompareTo(Variant left, Variant right) {
        if (left.value == null && right.value == null) {
            return 0;
        } else if (left.value != null && right.value == null) {
            return 1;
        } else if (left.value == null && right.value != null) {
            return -1;
        } else {
            int length = left.getArrayLength();
            int arrayLength = right.getArrayLength();
            // 维数大于
            if (length > arrayLength) {
                return 1;
            } else if (length < arrayLength) { // 维数小于
                return -1;
            } else { // 维数相同
                if (length > 0 && arrayLength > 0) {
                    Variant[] lVars = (Variant[]) left.value;
                    Variant[] rVars = (Variant[]) right.value;
                    int compareto = 0;
                    for (int i = 0; i < length; i++) {
                        compareto = lVars[i].compareTo(rVars[i]);
                        if (compareto != 0) {
                            return compareto;
                        }
                    }
                    return compareto;
                } else {
                    return 0;
                }
            }
        }
    }

    /**
     * 将 object 对象集合 转换为 Variant 数组
     *
     * @param items 项目集合
     * @return
     */
    public static Variant toVariant(Iterable<?> items) {
        if (items == null) {
            return new Variant(new Variant[]{});
        }
        Class<?> myType = null;
        if (items.iterator().hasNext()) {
            myType = items.iterator().next().getClass();
        }
        if (myType == null) {
            return new Variant(new Variant[]{});
        }
        return toVariant(null, null, myType, items, true);
    }

    /**
     * 将 T 对象集合 转换为 Variant 数组
     * <p>
     * <typeparam name="T">类型</typeparam>
     *
     * @param items 项目集合
     * @return
     */
    public static <T> Variant toVariant(Iterable<T> items, Class<T> clazz) {
        if (items == null) {
            throw new ArgumentNullException("Items");
        }
        return toVariant(null, null, clazz, items, true);
    }

    /**
     * 将 object 对象集合 转换为 Variant 数组
     *
     * @param iterableItemType 对象的实际类型
     * @param genericType      泛型Type
     * @param type             类型
     * @param items            项目集合
     * @param throwError       抛异常
     * @return
     */
    public static Variant toVariant(Type iterableItemType, Type genericType, Class<?> type, Iterable<?> items,
                                    boolean throwError) {
        if (type == null) {
            throw new ArgumentNullException("type");
        }
        if (items == null) {
            return new Variant(new Variant[]{});
        }
        ArrayList<Variant> vCols = new ArrayList<>();
        if (Variant.isBasicType(type)) {
            for (Object item : items) {
                if (item == null && type == String.class) {
                    vCols.add(new Variant(""));
                } else {
                    vCols.add(new Variant(item));
                }
            }
        } else {
            Type t = type.getGenericSuperclass();
            if (t == null) {
                t = genericType;
            }
            if (t instanceof ParameterizedType) {
                Type[] types = ((ParameterizedType) t).getActualTypeArguments();
                if (types.length == 1) {
                    type = (Class<?>) types[0];
                } else {
                    if (throwError) {
                        throw new NotSupportException("只支持1个参数的泛型类型。");
                    } else {
                        type = null;
                    }
                }
            }
            if (type == null) {
                type = (Class<?>) iterableItemType;
            }
            if (type != null) {
                Collection<BeanProperty> properties = ReflectUtils.getBeanPropertyMap(type).values();
                for (BeanProperty p : properties) {
                    if (p.canRead()) {
                        ArrayList<Variant> vRows = new ArrayList<>();
                        for (Object item : items) {
                            Object value = p.getValue(item);
                            if (Variant.isBasicType(p.getType())) {
                                if (value == null && p.getType() == String.class) {
                                    vRows.add(new Variant(""));
                                } else {
                                    vRows.add(new Variant(value));
                                }
                            } else {
                                if (p.isIterableType()) {
                                    vRows.add(toVariant(null, p.getReadMethod().getGenericReturnType(), p.getType(),
                                            (value instanceof Iterable) ? (Iterable<?>) value : null, false));

                                }
                            }
                        }
                        vCols.add(new Variant(p.getName(), vRows.toArray(new Variant[0])));
                    }
                }
            }
        }
        return new Variant(vCols.toArray(new Variant[0]));
    }

    /**
     * 获取数组中的 Variant 对象
     *
     * @param index 索引
     * @return
     * @throws RuntimeException 完法转换。
     */
    public Variant getArrayValue(int index) {
        if (this.isArray()) {
            Variant[] vars = (Variant[]) this.value;
            return vars[index];
        } else {
            throw new RuntimeException("非数组不支持索引。");
        }
    }

    /**
     * 获取数组中的 Variant 维数(非数组返回 -1)
     *
     * @return
     */
    public int getArrayLength() {
        if (this.isArray()) {
            return ((Variant[]) this.value).length;
        } else {
            return -1;
        }
    }

    @Override
    public Variant clone() {
        Variant varCopy = new Variant();
        varCopy.value = this.value;
        varCopy.type = this.type;
        varCopy.name = this.name;
        varCopy.tag = this.tag;
        return varCopy;
    }
}
