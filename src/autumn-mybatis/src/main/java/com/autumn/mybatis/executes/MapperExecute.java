package com.autumn.mybatis.executes;

/**
 * 映射执行器
 * 
 * @author 老码农 2019-06-11 21:40:27
 * @param <TEntity>
 *            实体类型
 */
public interface MapperExecute<TEntity> {

	/**
	 * 删除
	 * 
	 * @return
	 */
	int delete();

	/**
	 * 获取记录数
	 * 
	 * @return
	 */
	int count();

	/**
	 * 存在记录数
	 * 
	 * @return
	 */
	boolean exist();
}
