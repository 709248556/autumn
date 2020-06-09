package com.autumn.mybatis.provider;

/**
 * 命令生成器
 * 
 * @author 老码农 2018-04-01 00:27:39
 */
public interface CommandBuilder extends Builder {

	/**
	 * 获取执行命令
	 * 
	 * @param commandParmaName
	 *            命令参数名称
	 * @return
	 */
	String getExecuteCommand(String commandParmaName);
	
	/**
	 * 调用存储过程
	 * 
	 * @param functionParmaName 函数参数名称
	 * @return
	 */
	String getProcedureCommand(String functionParmaName);
}
