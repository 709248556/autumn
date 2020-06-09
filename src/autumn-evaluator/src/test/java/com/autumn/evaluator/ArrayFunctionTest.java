package com.autumn.evaluator;

public class ArrayFunctionTest extends AppTest {

    public ArrayFunctionTest() {
        super("数组测试");
    }

    /**
     * 根据列索引或列名称与行索引获取数组值
     */
    public void testArrayValue() {
        String expression = "ArrayValue([9, 4, 5], 1, 1)";
        test(expression);
    }

    /**
     * 根据列索引或列名称返回组数首行值
     */
    public void testArrayFirstValue() {
        String expression = "ArrayFirstValue([9, 4, 5], 1)";
        test(expression);
    }

    /**
     * 根据列索引或列名称返回组数最后行值
     */
    public void testArrayLastValue() {
        String expression = "ArrayLastValue([9, 4, 5], 1)";
        test(expression);
    }

    /**
     * 根据列索引或列名称返回连接 STRING 值
     */
    public void testArrayJoin() {
        String expression = "ArrayJoin([9, 4, 5], 1, \"-\")";
        test(expression);
    }

    /**
     * 获取数组列
     */
    public void testArray() {
        String expression = "ARRAY([9, 4, 5], 1,)";
        test(expression);
    }

    /**
     * 获取数组维数
     */
    public void testArrayLength() {
        String expression = "ArrayLength([9, 4, 5])";
        test(expression);
    }
}
