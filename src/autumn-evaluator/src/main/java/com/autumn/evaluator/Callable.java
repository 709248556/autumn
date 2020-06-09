package com.autumn.evaluator;

/**
 * 回调
 *
 * @author 老码农
 * <p>
 * 2018-04-25 12:25:57
 */
@FunctionalInterface
public interface Callable {

    /**
     * 调用
     *
     * @param name         函数名称
     * @param paramContext 参数上下文
     * @param context      解析上下文
     * @return
     */
    Variant call(String name, FunctionParamContext paramContext, Context context);
}