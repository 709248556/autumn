package com.autumn.application.dto.input;

import com.autumn.annotation.FriendlyProperty;
import lombok.ToString;

/**
 * 高级查询输入
 * 
 * @author 老码农 2018-11-16 16:40:39
 */
@ToString(callSuper = true)
public class DefaultAdvancedQueryInput extends DefaultQueryCriteriaInput implements AdvancedQueryInput {

	/**
	 * 
	 */
	private static final long serialVersionUID = 3638429532727818533L;
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
