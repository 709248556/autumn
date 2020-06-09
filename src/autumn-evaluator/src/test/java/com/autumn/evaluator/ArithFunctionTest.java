package com.autumn.evaluator;

import org.junit.Test;

public class ArithFunctionTest extends AppTest{

    public ArithFunctionTest() {
        super("数字函数测试");
    }

    /**
     * 转换为浮点
     */
    @Test
    public void testD() {
        String expression = "D(11.022)";
        test(expression);
    }

    /**
     * 取绝对值
     */
    @Test
    public void testAbs() {
        String expression = "abs(-58)";
        test(expression);
    }

    /**
     * 反余弦
     */
    @Test
    public void testAcos() {
        String expression = "Acos(0.5)";
        test(expression);
    }

    /**
     * 反正弦
     */
    @Test
    public void testAsin() {
        String expression = "Asin(1)";
        test(expression);
    }

    /**
     * 反正切
     */
    @Test
    public void testAtan() {
        String expression = "Atan(-1)";
        test(expression);
    }

    /**
     * 反正切
     */
    public void testAtan2() {
        String expression = "Atan2(-1, 1)";
        test(expression);
    }

    /**
     * 余弦
     */
    public void testCos() {
        String expression = "Cos(45)";
        test(expression);
    }

    /**
     * 返回双曲余弦值
     */
    public void testCosh() {
        String expression = "Cosh(45)";
        test(expression);
    }

    /**
     * 返回大于或等于指定的数值的最小整数值
     */
    public void testCeiling() {
        String expression = "Ceiling(-452.56)";
        test(expression);
    }

    /**
     * 返回小于或等于指定数值的最大整数
     */
    public void testFloor() {
        String expression = "Floor(452.56)";
        test(expression);
    }

    /**
     * 返回自然对数的底
     */
    public void testE() {
        String expression = "E()";
        test(expression);
    }

    /**
     * 返回e的n次方
     */
    public void testExp() {
        String expression = "Exp(-2)";
        test(expression);
    }

    /**
     * 根据给定的底数返回数字的对数
     */
    public void testLog() {
        String expression = "Log(2, 10)";
        test(expression);
    }

    /**
     * 返回给定数值以10为底的对数
     */
    public void testLog10() {
        String expression = "Log10(20)";
        test(expression);
    }

    /**
     * 返回两数中较大的一个
     */
    public void testMax() {
        String expression = "Max(-200, -100)";
        test(expression);
    }

    /**
     * 返回两数中较小的一个
     */
    public void testMin() {
        String expression = "Min(-200, -100)";
        test(expression);
    }

    /**
     * 返回两数相除的余数
     */
    public void testMod() {
        String expression = "Mod(9, 5)";
        test(expression);
    }

    /**
     * 返回圆周率的Pi的值,3.14159265358979,精确到15位
     */
    public void testPi() {
        String expression = "Pi()";
        test(expression);
    }

    /**
     * 返回圆周率的Pi的指定长度值字符串
     */
    public void testBigPi() {
        String expression = "BigPi(30, true, 3)";
        test(expression);
        expression = "BigPi(30, false, 3)";
        test(expression);
    }

    /**
     * 返回某数的乘幂
     */
    public void testPower() {
        String expression = "power(2, 3)";
        test(expression);
    }

    /**
     * 按指定的位数对数值进行四舍五入
     */
    public void testRound() {
        String expression = "Round(2.326545, 3)";
        test(expression);
    }

    /**
     * 返回给定角度的正切值
     */
    public void testSin() {
        String expression = "Sin(30)";
        test(expression);
    }

    /**
     * 返回双曲正弦值
     */
    public void testSinh() {
        String expression = "Sinh(30)";
        test(expression);
    }

    /**
     * 返回数值的平方根
     */
    public void testSqrt() {
        String expression = "Sqrt(9)";
        test(expression);
    }

    /**
     * 返回数字 * PI 的平方根
     */
    public void testSqrtPi() {
        String expression = "SqrtPi(9)";
        test(expression);
    }

    /**
     * 返回给定角度的正切值
     */
    public void testTan() {
        String expression = "Tan(45)";
        test(expression);
    }

    /**
     * 返回双曲正切值
     */
    public void testTanh() {
        String expression = "Tanh(45)";
        test(expression);
    }

    /**
     * 将弧度转角度
     */
    public void testDegrees() {
        String expression = "Degrees(45)";
        test(expression);
    }

    /**
     * 将角度转弧度
     */
    public void testRadiAns() {
        String expression = "RadiAns(45)";
        test(expression);
    }

    /**
     * 将数字截为整数
     */
    public void testInt() {
        String expression = "Int(-465465.665,0)";
        test(expression);
    }

    /**
     * 返回数字的正负号 为正时=1,为零时=0,为负时=-1
     */
    public void testSign() {
        String expression = "Sign(-46546.365)";
        test(expression);
    }

    /**
     * 合计所有参数(至需要一个参数，但不限制参数上限)
     */
    public void testSum() {
        String expression = "Sum(-4, 5,3.21)";
        test(expression);
    }

    /**
     * 合计符合条件所有参数
     */
    public void testSumIf() {
        String expression = "SumIf([1,2,3],\">=2\", [4, 2, 3])";
        test(expression);
    }

    /**
     * 返回所有参数的平方和(至需要一个参数，但不限制参数上限)
     */
    public void testSumSq() {
        String expression = "SumSq(1, 2)";
        test(expression);
    }

    /**
     * 返回某数的阶乘
     */
    public void testFact() {
        String expression = "Fact(3)";
        test(expression);
    }

    /**
     * 返回数的阶乘（范围大）
     */
    public void testBigFact() {
        String expression = "BigFact(3)";
        test(expression);
    }

    /**
     * 返回最大公约数
     */
    public void testGCD() {
        String expression = "GCD(3, 6)";
        test(expression);
    }

    /**
     * 返回最小公倍数
     */
    public void testLCM() {
        String expression = "LCM(3, 6)";
        test(expression);
    }

    /**
     * 返回0到1之间的随机数
     */
    public void testRand() {
        String expression = "Rand()";
        test(expression);
    }

    /**
     * 返回介于Bottom与Top之间的随机数
     */
    public void testRandBetween() {
        String expression = "RandBetween(1, 9)";
        test(expression);
    }

    /**
     * 将正整数转换为罗马数字
     */
    public void testRoman() {
        String expression = "Roman(3998)";
        test(expression);
    }

    /**
     * 返回所有参数的中位数(至需要一个参数，但不限制参数上限)
     */
    public void testMedian() {
        String expression = "Median(12, 4, 6)";
        test(expression);
    }

    /**
     * 将正数向上舍入最接近的偶数，负数向下舍入最接近的偶数
     */
    public void testEven() {
        String expression = "Even(12.26)";
        test(expression);
    }

    /**
     * 将正数向上舍入最接近的奇数，负数向下舍入最接近的奇数
     */
    public void testODD() {
        String expression = "ODD(12.26)";
        test(expression);
    }

    /**
     * 返回所有参数的乘积(至需要一个参数，但不限制参数上限)
     */
    public void testProduct() {
        String expression = "Product(2, 3, 4)";
        test(expression);
    }
}
