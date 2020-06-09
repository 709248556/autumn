package com.autumn.evaluator;

import com.autumn.evaluator.nodes.AbstractTreeNode;
import junit.framework.TestCase;

import java.util.ArrayList;

/**
 * Unit test for simple App.
 */
public class AppTest extends TestCase implements Context, ParseContext {

    public AppTest() {
        super("表达式测试");
    }

    public AppTest(String testName) {
        super(testName);
    }

    private static GeneralContext generalContext;

    static {
        generalContext = new GeneralContext();
        for (int i = 0; i <= 10; i++) {
            generalContext.setVariable("A" + i, new Variant(i));
        }
        for (int i = 0; i <= 10; i++) {
            generalContext.setVariable("B" + i, new Variant(i));
        }
    }

    @Override
    public Context getContext() {
        return this;
    }

    @Override
    public boolean hasVariable(String name) {
        return generalContext.hasVariable(name);
    }

    @Override
    public Variant getVariable(String name) {
        return generalContext.getVariable(name);
    }

    @Override
    public void setVariable(String name, Variant value) {
        generalContext.setVariable(name, value);
    }

    @Override
    public boolean delVariable(String name) {
        return generalContext.delVariable(name);
    }

    @Override
    public void lock() {
        generalContext.lock();
    }

    @Override
    public void unLock() {
        generalContext.unLock();
    }

    /**
     * 测试函数
     *
     * @param expression
     */
    public void test(String expression) {
        System.out.println("======================== test begin ========================");
        AbstractTreeNode node = EvaluatorUtils.nodeCompiled(expression);
        Variant value = node.parse(this);
        System.out.println(String.format("Expression:%s", node.toString()));
        System.out.println(String.format("Result:%s", value));
        ArrayList<String> variables = EvaluatorUtils.nodeVariables(node);
        if (variables.size() > 0) {
            boolean write = false;
            for (String name : variables) {
                Variant v = this.getVariable(name);

                if (v != null) {
                    if (!write) {
                        write = true;
                        System.out.println("Variables:");
                    }
                    System.out.println(String.format("{%s} = {%s}", name, v.toString()));
                }
            }
        }
        System.out.println("\n");
    }
}
