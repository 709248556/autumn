package com.autumn.application.dto.input;

import com.autumn.annotation.FriendlyProperty;

import java.io.Serializable;
import java.util.List;

/**
 * 查询条件输入
 * 
 * @author 老码农
 * 2018-11-16 16:27:23
 */
public interface QueryCriteriaInput extends Serializable {

	/**
	 * 获取条件集合
	 * 
	 * @return
	 */
	@FriendlyProperty(value = "条件集合")
	List<QueryCriteriaItem> getCriterias();

	/**
	 * 设置条件集合
	 * 
	 * @param criterias
	 */
	void setCriterias(List<QueryCriteriaItem> criterias);
}
