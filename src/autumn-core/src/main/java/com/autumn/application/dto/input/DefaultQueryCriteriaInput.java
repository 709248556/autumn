package com.autumn.application.dto.input;

import com.autumn.annotation.FriendlyProperty;
import lombok.ToString;

import java.util.ArrayList;
import java.util.List;

/**
 * 
 * 查询条件输入
 * 
 * @author 老码农 2018-11-16 16:31:47
 */
@ToString(callSuper = true)
public class DefaultQueryCriteriaInput implements QueryCriteriaInput {

	/**
	 * 
	 */
	private static final long serialVersionUID = -7956894733115121574L;

	/**
	 * 
	 */
	public DefaultQueryCriteriaInput() {
		this.setCriterias(new ArrayList<>());
	}

	@FriendlyProperty(value = "条件集合")
	private List<QueryCriteriaItem> criterias;

	@Override
	public List<QueryCriteriaItem> getCriterias() {
		return this.criterias;
	}

	@Override
	public void setCriterias(List<QueryCriteriaItem> criterias) {
		this.criterias = criterias;
	}
}
