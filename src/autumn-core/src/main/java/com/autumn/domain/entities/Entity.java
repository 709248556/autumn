package com.autumn.domain.entities;

import com.autumn.annotation.FriendlyProperty;

import java.io.Serializable;

/**
 * 
 * @author 老码农
 *         <p>
 *         Description
 *         </p>
 * @date 2017-12-31 21:19:13
 * @param <TKey>
 *            主键类型
 */
public interface Entity<TKey extends Serializable> extends Serializable {

	/**
	 * 字段id
	 */
	public static final String FIELD_ID = "id";

	/**
	 * 列id
	 */
	public static final String COLUMN_ID = "id";
	
	/**
	 * 获取Id
	 * 
	 * @return
	 */
	@FriendlyProperty(value = "Id")
	TKey getId();

	/**
	 * 设置Id
	 * 
	 * @param id
	 *            id值
	 */
	void setId(TKey id);

}
