package com.autumn.evaluator;

public class StatisticsFunctionTest extends AppTest {

    public StatisticsFunctionTest() {
        super("统计数据测试");
    }

    /**
     * 返回所有参数的算术平均数(至需要一个参数，但不限制参数上限)
     */
    public void testAverage() {
        String expression = "Average(1, 2, 3)";
        test(expression);
    }

    /**
     * 返回所有参数的几何平均数(至需要一个参数，但不限制参数上限)
     */
    public void testGeoMean() {
        String expression = "GeoMean(1, 2, 3)";
        test(expression);
    }

    /**
     * 返回所有参数的调和平均数，所有参数倒数平均值的倒数(至需要一个参数，但不限制参数上限)
     */
    public void testHarMean() {
        String expression = "HarMean(1, 2, 3)";
        test(expression);
    }

    /**
     * 返回所有参数的平方平均数(至需要一个参数，但不限制参数上限)
     */
    public void testSquareMean() {
        String expression = "SquareMean(1, 2, 3)";
        test(expression);
    }

    /**
     * 返回所有参数的总体样本方差(至需要一个参数，但不限制参数上限)
     */
    public void testVarp() {
        String expression = "Varp(1, 2, 3)";
        test(expression);
    }

    /**
     * 返回所有参数方差(至需要一个参数，但不限制参数上限)
     */
    public void testVar() {
        String expression = "Var(1, 2, 3)";
        test(expression);
    }

    /**
     * 计算所有包含数字的个数(至需要一个参数，但不限制参数上限)
     */
    public void testCount() {
        String expression = "Count(1, 2, 3, #2015-12-12#)";
        test(expression);
    }

    /**
     * 计算符合条件所有参数个数
     */
    public void testCountIf() {
        String expression = "countif(a1:a8,\">40\")>=8";
        System.out.println(ArrayPosition.getArrayColName(27));
        test(expression);

    }

    /**
     * 计算所有参数的非空个数(至需要一个参数，但不限制参数上限)
     */
    public void testCountA() {
        String expression = "CountA(1, 3, \"\")";
        test(expression);
    }

    /**
     * 计算所有参数的空白个数(至需要一个参数，但不限制参数上限)
     */
    public void testCountBlank() {
        String expression = "CountBlank(1, 3, \"\")";
        test(expression);
    }
}
