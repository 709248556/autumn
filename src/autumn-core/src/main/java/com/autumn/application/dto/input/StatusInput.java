package com.autumn.application.dto.input;

import com.autumn.annotation.FriendlyProperty;
import com.autumn.domain.entities.Entity;
import com.autumn.validation.DataValidation;

import java.io.Serializable;

/**
 * 状态输入
 * 
 * @author 老码农 2018-12-20 12:06:50
 * @param <TPrimaryKey>
 */
public interface StatusInput<TPrimaryKey extends Serializable> extends Entity<TPrimaryKey>, DataValidation {

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
	 * @param status 状态
	 */
	void setStatus(Integer status);
}
