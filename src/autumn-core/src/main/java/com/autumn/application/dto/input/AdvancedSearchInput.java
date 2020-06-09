package com.autumn.application.dto.input;

import com.autumn.annotation.FriendlyProperty;

import java.io.Serializable;

/**
 * 查询搜索
 * 
 * @author 老码农 2018-11-16 16:30:20
 */
public interface AdvancedSearchInput extends Serializable {
	
	/**
	 * 获取搜索关键字
	 * 
	 * @return
	 */
	@FriendlyProperty(value = "搜索关键字")
	String getSearchKeyword();

	/**
	 * 设置搜索关键字
	 * 
	 * @param searchKeyword
	 */
	void setSearchKeyword(String searchKeyword);
}
