package com.autumn.application.dto.input;

import com.autumn.annotation.FriendlyProperty;

/**
 * 页查询条件
 * 
 * @author 老码农 2018-09-02 10:37:17
 */
public interface PageQueryInput extends QueryCriteriaInput {

	/**
	 * 获取当前页
	 * 
	 * @return
	 */
	@FriendlyProperty(value = "当前页")
	int getCurrentPage();

	/**
	 * 设置当前页
	 * 
	 * @param currentPage
	 *            当前页
	 */
	void setCurrentPage(int currentPage);

	/**
	 * 获取页大小
	 * 
	 * @return
	 */
	@FriendlyProperty(value = "页大小")
	int getPageSize();

	/**
	 * 设置页大小
	 * 
	 * @param pageSize
	 *            页大小
	 */
	void setPageSize(int pageSize);
	
}
