package com.autumn.domain.entities.auditing.gmt;

import com.autumn.annotation.FriendlyProperty;

import java.util.Date;

/**
 * 表示具有修改时间
 * 
 * @author 老码农
 *
 *         2017-10-30 16:58:39
 */
public interface GmtModifiedAuditing extends GmtAuditing {

	/**
	 * 字段 gmtModified
	 */
	public static final String FIELD_GMT_MODIFIED = "gmtModified";
	
	/**
	 * 修改时间列名称
	 */
	public final static String COLUMN_GMT_MODIFIED = "gmt_modified";
	
	/**
	 * 获取修改时间
	 * 
	 * @return
	 */
	@FriendlyProperty(value = "修改时间")
	Date getGmtModified();

	/**
	 * 设置修改时间
	 * 
	 * @param gmtModified
	 *            修改时间
	 */
	void setGmtModified(Date gmtModified);
}
