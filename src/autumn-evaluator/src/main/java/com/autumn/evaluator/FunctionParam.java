package com.autumn.evaluator;

/**
 * 函数参数
 *
 * @Description TODO
 * @Author 老码农
 * @Date 2019-08-16 23:37
 */
public interface FunctionParam {

    /**
     * 获取顺序
     *
     * @return
     */
    int getOrder();

    /**
     * 获取参数名称
     *
     * @return
     */
    String getName();

    /**
     * 获取参数说明
     *
     * @return
     */
    String getCaption();

    /**
     * 获取参数类型
     *
     * @return {@link com.autumn.evaluator.VariantType}
     */
    int getParamType();

    /**
     * 获取解析参数值
     *
     * @return
     */
    Variant getValue();

    /**
     * 获取默认值
     *
     * @return
     */
    Variant getDefaultValue();

    /**
     * 获取参数上下文
     *
     * @return
     */
    FunctionParamContext getParamContext();

}
