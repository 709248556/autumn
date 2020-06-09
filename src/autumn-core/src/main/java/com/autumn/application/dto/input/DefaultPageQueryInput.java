package com.autumn.application.dto.input;

import com.autumn.annotation.FriendlyProperty;
import lombok.ToString;

/**
 * 页查询条件
 * 
 * @author 老码农 2018-08-14 13:25:21
 */
@ToString(callSuper = true)
public class DefaultPageQueryInput extends DefaultQueryCriteriaInput implements PageQueryInput {

	/**
	 * 
	 */
	private static final long serialVersionUID = 3970675354397683925L;

	private final static int MIN_PAGESIZE = 10;

	@FriendlyProperty(value = "当前页")
	private int currentPage;
	@FriendlyProperty(value = "页大小")
	private int pageSize;

	/**
	 * 获取当前页
	 * 
	 * @return
	 */
	@Override
	public int getCurrentPage() {
		if (currentPage < 1) {
			currentPage = 1;
		}
		return currentPage;
	}

	/**
	 * 设置当前页
	 * 
	 * @param currentPage
	 */
	@Override
	public void setCurrentPage(int currentPage) {
		this.currentPage = currentPage;
	}

	/**
	 * 获取页大小
	 * 
	 * @return
	 */
	@Override
	public int getPageSize() {
		if (pageSize < MIN_PAGESIZE) {
			pageSize = MIN_PAGESIZE;
		}
		return pageSize;
	}

	/**
	 * 设置页大小
	 * 
	 * @param pageSize
	 */
	@Override
	public void setPageSize(int pageSize) {
		this.pageSize = pageSize;
	}
}
