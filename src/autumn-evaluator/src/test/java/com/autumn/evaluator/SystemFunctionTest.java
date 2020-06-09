package com.autumn.evaluator;

import org.junit.Test;

public class SystemFunctionTest extends AppTest {

    public SystemFunctionTest() {
        super("系统函数测试");
    }


    /**
     * 返判断一个条件是否满足，如果满足返回一个值，如果不满则返回另一个值(此函数为系统与三元运算符由解析器特殊处理)
     */
    @Test
    public void testIf() {
        String expression = "If(1>0, 1, 2)";
        test(expression);
    }

    /**
     * 根据分支返回符合条件值,从第二个参数(第二个为表示奇数)开始(奇数参数为条件，偶数参数为返回值),最后一个参数为默认值。
     */
    @Test
    public void testSwitch() {
        String expression = "Switch(5 - 4, 1, 111, 2, 222)";
        test(expression);
    }

    /**
     * 检查是否是所有参数均为 True ,如果所有参数都是 True 则返回 True 否则为False
     */
    @Test
    public void testAnd() {
        String expression = "and(5 - 4 > 4, 1 > 0)";
        test(expression);
    }

    /**
     * 任何参数返回 True ，则返回 True ，只有所有参数都参数都是 False 时，才返回 False
     */
    @Test
    public void testOr() {
        String expression = "or(5 - 4 > 4, 1 > 0)";
        test(expression);
    }

    /**
     * 返回逻辑值 False
     */
    public void testFalse() {
        String expression = "False()";
        test(expression);
    }

    /**
     * 返回逻辑值 True
     */
    public void testTrue() {
        String expression = "True()";
        test(expression);
    }

    /**
     * 返回 NULL 值。
     */
    public void testTNull() {
        String expression = "NULL()";
        test(expression);
    }

    /**
     * 根据参数返回相反的逻辑值,如果参数为True 则返回 False , 否则返回 True
     */
    public void testNot() {
        String expression = "not(1 > 2)";
        test(expression);
    }

    /**
     * 判断是一值是为 NULL 或 DBNull.Value,符合则返回 True,否则返回 false
     */
    public void testIsNull() {
        String expression = "IsNull(NULL)";
        test(expression);
    }

    /**
     * 判断是一值是为 NULL 或 空白字符时,符合则返回 True,否则返回 false
     */
    public void testIsNullOrEmpty() {
        String expression = "IsNullOrEmpty(NULL)";
        test(expression);
    }

    /**
     * 判断是否存在一个变量,存在则返回 True,否则返回 false
     */
    public void testIsExistVariable() {
        String expression = "IsExistVariable(\"A1\")";
        test(expression);
    }

    /**
     * 设置变量(不存在测添加，存在则变更),并返回设置的变量
     */
    public void testSet() {
        String expression = "Set(\"company\", 1)";
        test(expression);
    }

    /**
     * 删除变量(成功则返回 True 否则返回 False)
     */
    public void testDel() {
        String expression = "Del(\"company\")";
        test(expression);
    }

    /**
     * 执行一个循环,并将表达式存入变量，返回变量的最终值。
     */
    public void testFor() {
        String expression = "For(1, 3, \"C\", 1, \"1\", 1)";
        test(expression);
    }
}
