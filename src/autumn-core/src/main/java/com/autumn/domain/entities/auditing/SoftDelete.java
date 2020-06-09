package com.autumn.domain.entities.auditing;

import com.autumn.annotation.FriendlyProperty;

/**
 * 软删除
 * 
 * @author 老码农
 *
 *         2017-10-30 16:14:53
 */
public interface SoftDelete extends Auditing {

	/**
	 * 字段 delete
	 */
	public static final String FIELD_IS_DELETE = "delete";
	
	/**
	 * 是否删除列名称
	 */
	public final static String COLUMN_IS_DELETE = "is_delete";
	
	/**
	 * 获取删除状态
	 * 
	 * @return 返回 true 表示已删除
	 */
	@FriendlyProperty(value = "是否删除")
	boolean isDelete();

	/**
	 * 设置删除状态
	 * 
	 * @param delete
	 *            true 表示已删除
	 */
	void setDelete(boolean delete);
	
}
