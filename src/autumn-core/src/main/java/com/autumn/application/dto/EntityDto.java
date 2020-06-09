package com.autumn.application.dto;


import com.autumn.annotation.FriendlyProperty;
import com.autumn.domain.entities.Entity;
import com.autumn.validation.DataValidation;
import com.autumn.validation.ValidationUtils;
import lombok.ToString;

import java.io.Serializable;

/**
 * 实体Dto
 * 
 * @author 老码农
 *         <p>
 *         Description
 *         </p>
 * @date 2017-12-31 21:35:05
 * @param <TKey> 主键类型
 */
@ToString(callSuper = true)
public class EntityDto<TKey extends Serializable> implements Entity<TKey>, DataValidation {

	/**
	 * 
	 */
	private static final long serialVersionUID = -984688656832255716L;
	@FriendlyProperty(value = "主键id")
	private TKey id;

	/**
	 * 获取Id
	 * 
	 * @return
	 */
	@Override
	public TKey getId() {
		return id;
	}

	/**
	 * 设置Id
	 * 
	 * @param id id值
	 */
	@Override
	public void setId(TKey id) {
		this.id = id;
	}

	/**
	 * 
	 */
	@Override
	public void valid() {
		ValidationUtils.validation(this);
	}
}
