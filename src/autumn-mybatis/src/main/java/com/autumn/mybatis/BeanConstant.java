package com.autumn.mybatis;

import com.autumn.util.StringUtils;

/**
 * Bean 常量
 * 
 * @author 老码农
 *         <p>
 *         Description
 *         </p>
 * @date 2017-12-03 19:06:57
 */
public class BeanConstant {

	/**
	 * 数据源后缀
	 */
	public final static String SUFFIX_DATA_SOURCE = "DataSource";

	/**
	 * 数据源工厂后缀
	 */
	public final static String SUFFIX_DATA_SOURCE_FACTORY = "DataSourceFactory";

	/**
	 * 事务管理后缀
	 */
	public final static String SUFFIX_TRANSACTION_MANAGER = "TransactionManager";

	/**
	 * Sql会话工厂后缀
	 */
	public final static String SUFFIX_SQL_SESSION_FACTORY = "SqlSessionFactory";

	/**
	 * Sql会话模板后缀
	 */
	public final static String SUFFIX_SQL_SESSION_TEMPLATE = "SqlSessionTemplate";

	/**
	 * Db 提供者后缀
	 */
	public final static String SUFFIX_DB_PROVIDER = "DbProvider";

	/**
	 * MapperRegister 注册后缀
	 */
	public final static String SUFFIX_MAPPER_REGISTER = "MapperRegister";

	/**
	 * 数据源默认Bean名称
	 */
	public final static String DEFAULT_DATA_SOURCE = "dataSource";

	/**
	 * 数据源工厂默认Bean名称
	 */
	public final static String DEFAULT_DATA_SOURCE_FACTORY = "dataSourceFactory";

	/**
	 * 事务管理默认Bean名称
	 */
	public final static String DEFAULT_TRANSACTION_MANAGER = "transactionManager";

	/**
	 * Sql会话工厂默认Bean名称
	 */
	public final static String DEFAULT_SQL_SESSION_FACTORY = "sqlSessionFactory";

	/**
	 * Sql会话模板默认Bean名称
	 */
	public final static String DEFAULT_SQL_SESSION_TEMPLATE = "sqlSessionTemplate";

	/**
	 * Db 提供者默认Bean名称
	 */
	public final static String DEFAULT_DB_PROVIDER = "dbProvider";

	/**
	 * MapperRegister 注册默认Bean名称
	 */
	public final static String DEFAULT_MAPPER_REGISTER = "mapperRegister";

	/**
	 * 动态
	 * 
	 * @author 老码农
	 *         <p>
	 *         Description
	 *         </p>
	 * @date 2017-12-29 23:52:49
	 */
	public static class Dynamic {

		/**
		 * 默认动态名称前缀
		 */
		public final static String DEFAULT_DYNAMIC_NAME_PREFIX = "dynamic";

		/**
		 * 数据源默认动态 Bean名称
		 */
		public final static String DEFAULT_DYNAMIC_DATA_SOURCE = DEFAULT_DYNAMIC_NAME_PREFIX + SUFFIX_DATA_SOURCE;

		/**
		 * 数据源工厂默认动态 Bean名称
		 */
		public final static String DEFAULT_DYNAMIC_DATA_SOURCE_FACTORY = DEFAULT_DYNAMIC_NAME_PREFIX
				+ SUFFIX_DATA_SOURCE_FACTORY;

		/**
		 * 事务管理默认动态 Bean名称
		 */
		public final static String DEFAULT_DYNAMIC_TRANSACTION_MANAGER = DEFAULT_DYNAMIC_NAME_PREFIX
				+ SUFFIX_TRANSACTION_MANAGER;

		/**
		 * Sql会话工厂默认动态 Bean名称
		 */
		public final static String DEFAULT_DYNAMIC_SQL_SESSION_FACTORY = DEFAULT_DYNAMIC_NAME_PREFIX
				+ SUFFIX_SQL_SESSION_FACTORY;

		/**
		 * Sql会话模板默认动态 Bean名称
		 */
		public final static String DEFAULT_DYNAMIC_SQL_SESSION_TEMPLATE = DEFAULT_DYNAMIC_NAME_PREFIX
				+ SUFFIX_SQL_SESSION_TEMPLATE;

		/**
		 * Db 提供者默认动态 Bean名称
		 */
		public final static String DEFAULT_DYNAMIC_DB_PROVIDER = DEFAULT_DYNAMIC_NAME_PREFIX + SUFFIX_DB_PROVIDER;

		/**
		 * MapperRegister 注册默认 动态Bean名称
		 */
		public final static String DEFAULT_DYNAMIC_MAPPER_REGISTER = DEFAULT_DYNAMIC_NAME_PREFIX
				+ SUFFIX_MAPPER_REGISTER;

	}

	/**
	 * 获取数据源 Bean 名称
	 * 
	 * @param namePrefix
	 *            名称前缀
	 * @return
	 */
	public static String getDataSourceBeanName(String namePrefix) {
		if (StringUtils.isNullOrBlank(namePrefix)) {
			return DEFAULT_DATA_SOURCE;
		}
		return namePrefix + SUFFIX_DATA_SOURCE;
	}

	/**
	 * 获取数据源工厂 Bean 名称
	 * 
	 * @param namePrefix
	 *            名称前缀
	 * @return
	 */
	public static String getDataSourceFactoryBeanName(String namePrefix) {
		if (StringUtils.isNullOrBlank(namePrefix)) {
			return DEFAULT_DATA_SOURCE_FACTORY;
		}
		return namePrefix + SUFFIX_DATA_SOURCE_FACTORY;
	}

	/**
	 * 获取事务管理器 Bean 名称
	 * 
	 * @param namePrefix
	 *            名称前缀
	 * @return
	 */
	public static String getTransactionManagerBeanName(String namePrefix) {
		if (StringUtils.isNullOrBlank(namePrefix)) {
			return DEFAULT_TRANSACTION_MANAGER;
		}
		return namePrefix + SUFFIX_TRANSACTION_MANAGER;
	}

	/**
	 * 获取会话工厂 Bean 名称
	 * 
	 * @param namePrefix
	 *            名称前缀
	 * @return
	 */
	public static String getSqlSessionFactoryBeanName(String namePrefix) {
		if (StringUtils.isNullOrBlank(namePrefix)) {
			return DEFAULT_SQL_SESSION_FACTORY;
		}
		return namePrefix + SUFFIX_SQL_SESSION_FACTORY;
	}

	/**
	 * 获取会话模板 Bean 名称
	 * 
	 * @param namePrefix
	 *            名称前缀
	 * @return
	 */
	public static String getSqlSessionTemplateBeanName(String namePrefix) {
		if (StringUtils.isNullOrBlank(namePrefix)) {
			return DEFAULT_SQL_SESSION_TEMPLATE;
		}
		return namePrefix + SUFFIX_SQL_SESSION_TEMPLATE;
	}

	/**
	 * 获取数据提供者 Bean 名称
	 * 
	 * @param namePrefix
	 *            名称前缀
	 * @return
	 */
	public static String getDbProviderBeanName(String namePrefix) {
		if (StringUtils.isNullOrBlank(namePrefix)) {
			return DEFAULT_DB_PROVIDER;
		}
		return namePrefix + SUFFIX_DB_PROVIDER;
	}

	/**
	 * 获取MapperRegister Bean 名称
	 * 
	 * @param namePrefix
	 *            名称前缀
	 * @return
	 */
	public static String getMapperRegisterBeanName(String namePrefix) {
		if (StringUtils.isNullOrBlank(namePrefix)) {
			return DEFAULT_MAPPER_REGISTER;
		}
		return namePrefix + SUFFIX_MAPPER_REGISTER;
	}
}
