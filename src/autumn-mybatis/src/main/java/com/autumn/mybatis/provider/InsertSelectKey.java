package com.autumn.mybatis.provider;

/**
 * 插入 SelectKey
 * 
 * @author 老码农
 *
 *         2017-10-19 17:17:16
 */
public class InsertSelectKey {
	private final boolean isExecuteBefore;
	private final String selectKeySql;

	public InsertSelectKey(boolean isExecuteBefore, String selectKeySql) {
		this.isExecuteBefore = isExecuteBefore;
		this.selectKeySql = selectKeySql;
	}

	/**
	 * 获取是否执行前
	 * 
	 * @return
	 */
	public boolean isExecuteBefore() {
		return isExecuteBefore;
	}

	/**
	 * 获取查询Key的 Sql
	 * 
	 * @return
	 */
	public String getSelectKeySql() {
		return selectKeySql;
	}
}
