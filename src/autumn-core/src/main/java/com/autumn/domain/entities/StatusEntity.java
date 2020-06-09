package com.autumn.domain.entities;

import com.autumn.annotation.FriendlyProperty;

import java.io.Serializable;

/**
 * 具有状态实体
 * 
 * @author 老码农 2018-11-25 20:34:57
 * @param <TKey> 实体类型
 */
public interface StatusEntity<TKey extends Serializable> extends Entity<TKey> {

	/**
	 * 字段 status
	 */
	public static final String FIELD_STATUS = "status";

	/**
	 * 获取状态
	 * 
	 * @return
	 */
	@FriendlyProperty(value = "状态")
	Integer getStatus();

	/**
	 * 设置状态
	 * 
	 * @param status
	 * 
	 *               状态
	 */
	void setStatus(Integer status);
}
