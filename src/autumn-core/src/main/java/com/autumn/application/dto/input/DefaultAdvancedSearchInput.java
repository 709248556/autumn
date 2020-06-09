package com.autumn.application.dto.input;

import com.autumn.annotation.FriendlyProperty;
import lombok.ToString;

/**
 * 高级搜索输入
 * 
 * @author 老码农 2018-11-16 16:37:47
 */
@ToString(callSuper = true)
public class DefaultAdvancedSearchInput implements AdvancedSearchInput {

	/**
	 * 
	 */
	private static final long serialVersionUID = -858226695222480463L;

	@FriendlyProperty(value = "搜索关键字")
	private String searchKeyword;

	@Override
	public String getSearchKeyword() {
		return this.searchKeyword;
	}

	@Override
	public void setSearchKeyword(String searchKeyword) {
		this.searchKeyword = searchKeyword;
	}

}
