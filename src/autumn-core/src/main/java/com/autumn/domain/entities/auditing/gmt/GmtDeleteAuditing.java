package com.autumn.domain.entities.auditing.gmt;

import com.autumn.annotation.FriendlyProperty;
import com.autumn.domain.entities.auditing.SoftDelete;

import java.util.Date;

/**
 * 表示具有删除时间
 * 
 * @author 老码农
 *
 *         2017-10-30 17:00:33
 */
public interface GmtDeleteAuditing extends SoftDelete, GmtAuditing {

	/**
	 * 字段 gmtDelete
	 */
	public static final String FIELD_GMT_DELETE = "gmtDelete";
	
	/**
	 * 删除时间列名称
	 */
	public final static String COLUMN_GMT_DELETE = "gmt_delete";

	/**
	 * 获取删除时间
	 * 
	 * @return
	 */
	@FriendlyProperty(value = "删除时间")
	Date getGmtDelete();

	/**
	 * 设置删除时间
	 * 
	 * @param gmtDelete
	 *            删除时间
	 */
	void setGmtDelete(Date gmtDelete);

}
