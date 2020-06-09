package com.autumn.evaluator;

public class FinancialFunctionTest extends AppTest {

    public FinancialFunctionTest() {
        super("财务函数测试");
    }

    /**
     * 基于固定利率及等额分期付款方式，返回贷款的每期付款额。
     */
    public void testPMT() {
        String expression = "PMT(1, 2, 100)";
        test(expression);
    }

    /**
     * 用固定余额递减速法，返回指定期间内某项固定资产的折旧值。
     */
    public void testDB() {
        String expression = "DB(10000, 1, 1, 1, 12)";
        test(expression);
    }

    /**
     * 双倍余额递减法或其他指定方法，返回指定期间内某项固定资产的折旧值。
     */
    public void testDDB() {
        String expression = "DDB(10000, 5000, 5, 3, 2)";
        test(expression);
    }

    /**
     * 返回某项资产按年限总和折旧法计算的指定期间的折旧额
     */
    public void testSYD() {
        String expression = "SYD(10000, 5000, 5, 3)";
        test(expression);
    }

    /**
     * 返回某项资产在一个期间中的线性折旧额
     */
    public void testSLN() {
        String expression = "SLN(10000, 5000, 5)";
        test(expression);
    }
}
