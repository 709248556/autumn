package com.autumn.mybatis.mapper;

import org.apache.ibatis.mapping.MappedStatement;

/**
 * 自定义 Mapper 提供者基础
 * 
 * @author 老码农 2018-04-01 12:51:34
 */
public class CustomMapperBaseProvider extends MapperProvider {

	/**
	 * 
	 * @param mapperClass
	 * @param rapperRegister
	 */
	public CustomMapperBaseProvider(Class<?> mapperClass, MapperRegister rapperRegister) {
		super(mapperClass, rapperRegister);
	}

	/**
	 * 调用自定义命令
	 *
	 * @param ms
	 * @return
	 */
	public String executeCommand(MappedStatement ms) {
		return this.getDbProvider().getCommandBuilder().getExecuteCommand(CustomBaseMapper.ARG_NAME_COMMANDTEXT);
	}

	/**
	 * 调用存储过程
	 *
	 * @param ms
	 * @return
	 */
	public String executeProcedure(MappedStatement ms) {
		return this.getDbProvider().getCommandBuilder().getProcedureCommand(CustomBaseMapper.ARG_NAME_FUNCTION);
	}
}
