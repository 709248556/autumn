package com.autumn.mybatis.mapper;

import com.autumn.annotation.FriendlyProperty;

import java.io.Serializable;
import java.util.List;

/**
 * 分页结果
 * 
 * @author 老码农 2019-06-11 02:13:19
 * @param <T>
 *            类型
 */
public interface PageResult<T> extends Serializable {

	/**
	 * 获取当前页
	 * 
	 * @return
	 */
	@FriendlyProperty(value = "当前页")
	int getCurrentPage();

	/**
	 * 获取页大小
	 * 
	 * @return
	 */
	@FriendlyProperty(value = "页大小")
	int getPageSize();

	/**
	 * 获取页总数
	 * 
	 * @return
	 */
	@FriendlyProperty(value = "页总数")
	int getPageTotal();
	
	/**
	 * 获取页记录数
	 * 
	 * @return
	 */
	@FriendlyProperty(value = "页记录数")
	int getPageRecords();

	/**
	 * 获取行总数
	 * 
	 * @return
	 */
	@FriendlyProperty(value = "行总数")
	int getRowTotal();
	
	/**
	 * 获取开始行
	 * 
	 * @return
	 *
	 */
	@FriendlyProperty(value = "开始行")
	int getStartRow();

	/**
	 * 获取结束行
	 * 
	 * @return
	 *
	 */
	@FriendlyProperty(value = "结束行")
	int getEndRow();

	/**
	 * 获取项目集合
	 * 
	 * @return
	 */
	@FriendlyProperty(value = "项目集合")
	List<T> getItems();
}
