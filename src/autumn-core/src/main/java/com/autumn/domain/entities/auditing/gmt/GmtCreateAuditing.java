package com.autumn.domain.entities.auditing.gmt;

import com.autumn.annotation.FriendlyProperty;

import java.util.Date;

/**
 * 表示具有创建时间
 * 
 * @author 老码农
 *
 *         2017-10-30 16:53:46
 */
public interface GmtCreateAuditing extends GmtAuditing {

	/**
	 * 字段 gmtCreate
	 */
	public static final String FIELD_GMT_CREATE = "gmtCreate";
	
	/**
	 * 新建时间列名称
	 */
	public final static String COLUMN_GMT_CREATE = "gmt_create";
	
	/**
	 * 获取创建时间
	 * 
	 * @return
	 */
	@FriendlyProperty(value = "创建时间")
	Date getGmtCreate();

	/**
	 * 设置创建时间
	 * 
	 * @param gmtCreate
	 *            创建时间
	 */
	void setGmtCreate(Date gmtCreate);
}
