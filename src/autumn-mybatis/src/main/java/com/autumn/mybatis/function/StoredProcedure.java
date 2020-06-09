package com.autumn.mybatis.function;

/**
 * 存储过程调用
 * @author 老码农
 *
 * 2017-12-06 13:24:28
 */
public class StoredProcedure {

    private final String functionName;
    private final Object[] param;

    /**
     *
     * @param functionName
     * @param param
     */
    public StoredProcedure(String functionName, Object...param) {
        this.functionName = functionName;
        this.param = param;
    }

    public String getFunctionName() {
        return functionName;
    }

    public Object[] getParam() {
        return param;
    }
}
