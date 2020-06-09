package com.autumn.application.dto.input;

import com.autumn.annotation.FriendlyProperty;
import lombok.ToString;

/**
 * 高级分页查询
 * 
 * @author 老码农 2018-11-16 16:36:23
 */
@ToString(callSuper = true)
public class DefaultAdvancedPageQueryInput extends DefaultPageQueryInput implements AdvancedPageQueryInput {

	/**
	 * 
	 */
	private static final long serialVersionUID = -5218438917101065879L;

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
