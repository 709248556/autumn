package com.autumn.evaluator;

import org.junit.Test;

public class VariableTest extends AppTest {
    public VariableTest() {
        super("变量测试");
    }

    @Test
    public void test1() {
        String expression = "#1+#2";
        test(expression.replace("#","a"));
    }
}
