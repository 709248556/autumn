package com.autumn.mybatis.mapper;

import com.autumn.mybatis.function.StoredProcedure;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.SelectProvider;

import java.util.List;
import java.util.Map;

/**
 * 自定义 Mapper 基础
 * <p>
 * 未能自动实现接口，需要在目标包中实现子级接口，原因为当多数据源时，若默认实现了该接口则无法定位数据源。
 * </p>
 *
 * @author 老码农 2018-04-01 12:49:58
 */
public interface CustomBaseMapper extends Mapper {

    /**
     * 过程\函数参数名称
     */
    public final static String ARG_NAME_FUNCTION = "function";

    /**
     * 命令参数名称
     */
    public final static String ARG_NAME_COMMANDTEXT = "commandText";

    /**
     * 调用存储过程
     */
    public final static String EXECUTE_PROCEDURE = "executeProcedure";

    /**
     * 执行命令
     */
    public final static String EXECUTE_COMMAND = "executeCommand";

    /**
     * 执行命令
     *
     * @param commandText 命令
     * @return
     */
    @SelectProvider(type = CustomMapperBaseProvider.class, method = EXECUTE_COMMAND)
    List<Map<String, Object>> executeCommand(@Param(ARG_NAME_COMMANDTEXT) String commandText);

    /**
     * 调用存储过程
     *
     * @param storedProcedure 过程信息
     * @return 2017-12-06 13:26:52
     */
    @SelectProvider(type = CustomMapperBaseProvider.class, method = EXECUTE_PROCEDURE)
    List<Map<String, Object>> executeProcedure(@Param(ARG_NAME_FUNCTION) StoredProcedure storedProcedure);

}
